package br.com.climb.core.sqlengine;

import br.com.climb.core.PersistentEntity;
import br.com.climb.core.mapping.Json;
import br.com.climb.core.sqlengine.interfaces.HasSchema;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.modelbean.ModelTableField;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static br.com.climb.utils.ReflectionUtil.getTableName;
import static org.apache.logging.log4j.LogManager.getLogger;

public class MySqlEngine extends ModelEngine implements SqlEngine {

    private static final Logger logger = getLogger(MySqlEngine.class);
    public MySqlEngine() {}

    @Override
    public String generateInsert(List<ModelTableField> modelTableFields, Object entity) {

        final var attributes = new StringBuilder();
        final var values = new StringBuilder();

        modelTableFields.stream().forEach((modelTableField) -> {
            attributes.append(modelTableField.getAttribute() + ",");
            values.append("?,");
        });

        final String tableName = getTableName(entity);

        final String sql = "INSERT INTO " + tableName + "("
                + attributes.toString().substring(0, attributes.toString().length() - 1) + ") VALUES ("
                + values.toString().substring(0, values.toString().length() -1) + ")";

        logger.info("SQL-POSTGRES: ", sql);
        return sql;
    }

    @Override
    public String generateUpdate(List<ModelTableField> modelTableFields, Object entity) {

        final var values = new StringBuilder();

        modelTableFields.stream().forEach((modelTableField) -> {
            values.append(modelTableField.getAttribute() + "= ?,");
        });

        final String tableName = getTableName(entity);
        final Long id = ((PersistentEntity)entity).getId();

        final String sql = "UPDATE " + tableName + " SET " +
                "" + values.toString().substring(0, values.toString().length() -1) + " " +
                "" + "WHERE id = " + id.toString();

        logger.info("SQL-POSTGRES: ", sql);

//        System.out.println(sql);

        return sql;
    }

    @Override
    public String generateDelete(Object entity) {

        final String tableName = getTableName(entity);
        final Long id = ((PersistentEntity)entity).getId();
        final String sql = "DELETE FROM " + tableName + " WHERE id = " + id.toString();

        logger.info("SQL-POSTGRES: ", sql);

        return sql;
    }

    @Override
    public String generateDelete(Class classe, String where) throws Exception {
        final String tableName = getTableName(classe.getDeclaredConstructor().newInstance());
        final String sql = "DELETE FROM " +  tableName + " " + where;

        logger.info("SQL-POSTGRES: ", sql);

        return sql;
    }

    @Override
    public String generateSelectMany(Class classe, String where) throws Exception {

        final String tableName = getTableName(classe.getDeclaredConstructor().newInstance());
        final var attributes = getAttributes(classe);

        final String sql = "SELECT id," + attributes.toString().substring(0, attributes.toString().length() - 1) + " FROM "
             + tableName + " " + where;

        logger.info("SQL-POSTGRES: ", sql);

        return sql;

    }

    @Override
    public String generateSelectMany(Class classe) throws Exception {
        final String tableName = getTableName(classe.getDeclaredConstructor().newInstance());
        return "SELECT * FROM " + tableName;
    }

    @Override
    public String generateSelectOne(Class classe, Long id) throws Exception {

        final String tableName = getTableName(classe.getDeclaredConstructor().newInstance());
        final var attributes = getAttributes(classe);

        final String sql = "SELECT id," + attributes.toString().substring(0, attributes.toString().length() - 1) + " FROM " +
                tableName + " WHERE ID="+id.toString();

        logger.info("SQL-POSTGRES: ", sql);

        return sql;

    }

    public String generateSelectOneAtt(Long id, String field, String entity) {

        final String sql = "SELECT " + field + " FROM " + entity + " WHERE ID = " + id.toString();

        logger.info("SQL-POSTGRES: ", sql);

        return sql;

    }

}
