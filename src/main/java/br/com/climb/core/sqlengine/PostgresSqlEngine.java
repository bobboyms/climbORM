package br.com.climb.core.sqlengine;

import br.com.climb.core.PersistentEntity;
import br.com.climb.core.TransactionDB;
import br.com.climb.core.mapping.Json;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.modelbean.ModelTableField;
import org.apache.logging.log4j.Logger;

import static br.com.climb.utils.ReflectionUtil.getTableName;
import static org.apache.logging.log4j.LogManager.getLogger;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class PostgresSqlEngine extends ModelEngine implements SqlEngine {

    private static final Logger logger = getLogger(PostgresSqlEngine.class);

    private List<ModelTableField> modelTableFields;
    private Object entity;
    private String schema = "public";

    public PostgresSqlEngine(List<ModelTableField> modelTableFields, Object entity) {
        this.modelTableFields = modelTableFields;
        this.entity = entity;
    }

    public PostgresSqlEngine() {}


    @Override
    public String generateInsert() {

        final var attributes = new StringBuilder();
        final var values = new StringBuilder();

        modelTableFields.stream().forEach((modelTableField) -> {
            attributes.append(modelTableField.getAttribute() + ",");
            if (modelTableField.getField().isAnnotationPresent(Json.class)) {
                Json json = modelTableField.getField().getAnnotation(Json.class);
                values.append("?::"+json.typeJson()+",");
            } else {
                values.append("?,");
            }
        });

        final String tableName = getTableName(entity);

        final String sql = "INSERT INTO " + schema + "." + tableName + "("
                + attributes.toString().substring(0, attributes.toString().length() - 1) + ") VALUES ("
                + values.toString().substring(0, values.toString().length() -1) + ") RETURNING ID";

        logger.info("SQL-POSTGRES: ", sql);
        return sql;
    }

    @Override
    public String generateUpdate() {

        final var values = new StringBuilder();

        modelTableFields.stream().forEach((modelTableField) -> {
            if (modelTableField.getField().isAnnotationPresent(Json.class)) {
                values.append(modelTableField.getAttribute() + "= ?::JSON,");
            } else {
                values.append(modelTableField.getAttribute() + "= ?,");
            }
        });

        final String tableName = getTableName(entity);
        final Long id = ((PersistentEntity)entity).getId();

        final String sql = "UPDATE " + schema + "." + tableName + " SET " +
                "" + values.toString().substring(0, values.toString().length() -1) + " " +
                "" + "WHERE id = " + id.toString();

        logger.info("SQL-POSTGRES: ", sql);

        return sql;
    }

    @Override
    public String generateDelete() {
        final String tableName = getTableName(entity);
        final Long id = ((PersistentEntity)entity).getId();
        final String sql = "DELETE FROM " + schema + "." + tableName + " WHERE id = " + id.toString();

        logger.info("SQL-POSTGRES: ", sql);

        return sql;
    }

    @Override
    public String generateDelete(String where) {
        final String tableName = getTableName(entity);
        final Long id = ((PersistentEntity)entity).getId();
        final String sql = "DELETE FROM " + schema + "." + tableName + " " + where;

        logger.info("SQL-POSTGRES: ", sql);

        return sql;
    }

    @Override
    public String generateSelectOne(Class classe, Long id) throws Exception {

        final String tableName = getTableName(classe.getDeclaredConstructor().newInstance());
        final var attributes = getAttributes(classe);

        final String sql = "SELECT id," + attributes.toString().substring(0, attributes.toString().length() - 1) + " FROM " +
                this.schema + "."+ tableName + " WHERE ID="+id.toString();

        logger.info("SQL-POSTGRES: ", sql);

        return sql;

    }

    public String generateSelectOneAtt(Long id, String field, String entity) {

        final String sql = "SELECT " + field + " FROM " + entity + " WHERE ID = " + id.toString();

        logger.info("SQL-POSTGRES: ", sql);

        return sql;

    }


}
