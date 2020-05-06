package br.com.climb.core;

import br.com.climb.configfile.FactoryConfigFile;
import br.com.climb.core.interfaces.ResultIterator;
import br.com.climb.core.mapping.*;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.exception.LoadLazyObjectException;
import br.com.climb.systemcache.CacheManager;
import br.com.climb.systemcache.CacheManagerImp;
import br.com.climb.utils.ReflectionUtil;
import br.com.climb.utils.SqlUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.logging.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;

public class LazyLoader implements ResultIterator {

    private Connection connection;
    private Class classe;
    private ResultSet resultSet;
    private Object object;
    private SqlEngine sqlEngine;

    private static final Logger logger = getLogger(LazyLoader.class);

    public LazyLoader(Connection connection, SqlEngine sqlEngine) {
        this.connection = connection;
        this.sqlEngine = sqlEngine;
    }

    public LazyLoader(Connection connection, SqlEngine sqlEngine, Class classe) {
        this.connection = connection;
        this.sqlEngine = sqlEngine;
        this.classe = classe;
    }

    @Override
    public Object getObject() {
        return this.object;
    }

    @Override
    public boolean next() {

        try {

            if (resultSet.next()) {
                object = newEnhancer(classe).create();
                Field[] fields = object.getClass().getSuperclass().getDeclaredFields();
                loadObject(fields, object, resultSet);

                return true;

            } else {
                resultSet.close();
            }
        } catch(Exception e) {
            logger.error("context", e);
        }

        return false;
    }


    public LazyLoader findWithQueryExecute(String sql) throws LoadLazyObjectException {

        final QueryResult queryResult = (QueryResult) classe.getAnnotation(QueryResult.class);

        if (queryResult == null) {
            throw new LoadLazyObjectException(classe.getName() + " not is QueryResult");
        }

        try {

            Statement stmt = this.connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 1);
            resultSet = stmt.executeQuery(sql);

            return this;

        } catch (Exception e) {
            logger.error("context", e);
        }

        return null;
    }

    public LazyLoader findWithWhereQueryExecute(String sql) throws LoadLazyObjectException {

        try {

            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 1);
            resultSet = stmt.executeQuery(sql);

            return this;

        } catch (Exception e) {
            logger.error("context", e);
        }

        return null;

    }

    public Object loadLazyObject(Class classe, Long id) throws Exception {

        CacheManager cacheManager = CacheManagerImp.build(FactoryConfigFile.getConfigFile());
        Object object = cacheManager.getValueCache(classe, id);

        if (object != null) {
            return object;
        }

        final Entity entity = (Entity) classe.getAnnotation(Entity.class);

        if (entity == null) {
            throw new LoadLazyObjectException(classe.getName() + " not is Entity");
        }

        try(Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, 1)) {
            try (ResultSet resultSet = statement.executeQuery(sqlEngine.generateSelectOne(classe, id))) {
                while (resultSet.next()) {

                    final long localid = resultSet.getLong("id");

                    object = newEnhancer(classe, entity.name(), localid).create();
                    ((PersistentEntity) object).setId(localid);
                    Field[] fields = object.getClass().getSuperclass().getDeclaredFields();
                    loadObject(fields, object, resultSet);

                }
            }
        }

        return object;
    }

    private Enhancer newEnhancer(Class classe) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(classe);
        enhancer.setCallback(new MethodInterceptor() {

            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                Object inst = proxy.invokeSuper(obj, args);

                if (inst != null && inst.getClass().getAnnotation(Entity.class) != null) {
                    Long id = ((PersistentEntity) inst).getId();
                    return loadLazyObject(inst.getClass(), id);
                }

                return proxy.invokeSuper(obj, args);
            }
        });

        return enhancer;
    }

    private Enhancer newEnhancer(Class classe, String entity, long id) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(classe);
        enhancer.setCallback(new MethodInterceptor() {

            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

                Object inst = proxy.invokeSuper(obj, args);

                if (inst != null) {

                    if (inst.getClass().getAnnotation(Entity.class) != null) {
                        Long id = ((PersistentEntity) inst).getId();
                        return loadLazyObject(inst.getClass(), id);
                    }

                    if (inst.getClass() == byte[].class) {
                        final String fieldName = new String((byte[]) inst);
                        return SqlUtil.getBinaryValue(connection, sqlEngine.generateSelectOneAtt(id, fieldName, entity), fieldName);
                    }

                }

                return proxy.invokeSuper(obj, args);
            }
        });

        return enhancer;
    }

    private void loadObject(Field[] fields, Object object, ResultSet resultSet) {

        for (Field field : fields) {

            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }

            if (field.isAnnotationPresent(Relation.class)) {

                try {

                    Object instance = Class.forName(field.getGenericType().getTypeName()).getConstructor().newInstance();

                    String fieldName = ReflectionUtil.getFieldName(field);
                    Long value = resultSet.getLong(fieldName);

                    PersistentEntity persistentEntity = (PersistentEntity) instance;
                    persistentEntity.setId(value);

                    new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                            .invoke(object, instance);

                } catch (Exception e) {
                    logger.error("context", e);
                }

                continue;
            }

            if (object.getClass().getSuperclass().getSuperclass() == PersistentEntity.class) {
                try {
                    ((PersistentEntity)object).setId(resultSet.getLong("id"));
                } catch (Exception e) {
                    logger.error("context", e);
                }
            }

            if (field.getType() == Long.class) {
                try {

                    String fieldName = ReflectionUtil.getFieldName(field);
                    Long value = resultSet.getLong(fieldName);
                    new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                            .invoke(object, value);

                } catch (Exception e) {
                    logger.error("context", e);
                }

            } else if (field.getType() == Integer.class || field.getType() == int.class) {
                try {

                    String fieldName = ReflectionUtil.getFieldName(field);
                    Integer value = resultSet.getInt(fieldName);
                    new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                            .invoke(object, value);

                } catch (Exception e) {
                    logger.error("context", e);
                }
            } else if (field.getType() == Float.class || field.getType() == float.class) {
                try {

                    String fieldName = ReflectionUtil.getFieldName(field);
                    Float value = resultSet.getFloat(fieldName);
                    new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                            .invoke(object, value);

                } catch (Exception e) {
                    logger.error("context", e);
                }
            } else if (field.getType() == Double.class || field.getType() == double.class) {

                try {

                    String fieldName = ReflectionUtil.getFieldName(field);
                    Double value = resultSet.getDouble(fieldName);
                    new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                            .invoke(object, value);

                } catch (Exception e) {
                    logger.error("context", e);
                }

            } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                try {

                    try {

                        String fieldName = ReflectionUtil.getFieldName(field);
                        Boolean value = resultSet.getBoolean(fieldName);
                        new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                                .invoke(object, value);

                    } catch (Exception e) {
                        logger.error("context", e);
                    }

                } catch (Exception e) {
                    logger.error("context", e);
                }
            } else if (field.getType() == String.class || field.getType() == char.class) {
                try {

                    try {

                        String fieldName = ReflectionUtil.getFieldName(field);
                        String value = resultSet.getString(fieldName);
                        new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                                .invoke(object, value);

                    } catch (Exception e) {
                        logger.error("context", e);
                    }

                } catch (Exception e) {
                    logger.error("context", e);
                }
            } else if (field.getType() == byte[].class) {
                try {

                    try {

                        String fieldName = ReflectionUtil.getFieldName(field);
                        byte[] value = fieldName.getBytes();
                        new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                                .invoke(object, value);

                    } catch (Exception e) {
                        logger.error("context", e);
                    }

                } catch (Exception e) {
                    logger.error("context", e);
                }
            } else if (field.getType() == List.class && field.isAnnotationPresent(Json.class)) {
                try {

                    try {
                        ObjectMapper mapper = new ObjectMapper();

                        Class fieldType = (Class) ((ParameterizedType) field.getGenericType())
                                .getActualTypeArguments()[0];

                        String fieldName = ReflectionUtil.getFieldName(field);
                        String json = resultSet.getString(fieldName);

                        if (json != null && json.trim().length() > 0) {
                            ArrayList value = mapper.readValue(json,
                                    mapper.getTypeFactory().constructCollectionType(List.class, fieldType));

                            new PropertyDescriptor(field.getName(), object.getClass()).getWriteMethod()
                                    .invoke(object, value);
                        }

                    } catch (Exception e) {
                        logger.error("context", e);
                    }

                } catch (Exception e) {
                    logger.error("context", e);
                }
            }

        }
    }

}
