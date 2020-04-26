package br.com.climb.core.sgdbconnection;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.LazyLoader;
import br.com.climb.core.PersistentEntity;
import br.com.climb.core.TransactionDB;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ResultIterator;
import br.com.climb.core.interfaces.Transaction;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.exception.SgdbException;
import br.com.climb.modelbean.ModelTableField;
import br.com.climb.systemcache.CacheManager;
import br.com.climb.systemcache.CacheManagerImp;
import org.apache.logging.log4j.Logger;

import java.io.Closeable;
import java.sql.*;
import java.util.List;

import static br.com.climb.core.jdbcconnection.FactoryJdbcConnection.createJdbcConnection;
import static br.com.climb.core.sqlengine.FactorySqlEngine.generateSqlEngine;
import static br.com.climb.utils.ReflectionUtil.generateModel;
import static br.com.climb.utils.SqlUtil.preparedStatementInsert;
import static br.com.climb.utils.SqlUtil.preparedStatementUpdate;
import static org.apache.logging.log4j.LogManager.getLogger;

public abstract class ConnectionOperation implements ClimbConnection {

    private static final Logger logger = getLogger(ConnectionOperation.class);

    private Connection connection;
    private Transaction transaction;
    private SqlEngine sqlEngine;
    private CacheManager cacheManager;

    public ConnectionOperation(ConfigFile configFile) {

        try {
            this.connection = createJdbcConnection(configFile);
            this.transaction = new TransactionDB(connection);
            this.sqlEngine = generateSqlEngine(configFile);
            this.cacheManager = CacheManagerImp.build(configFile);

        } catch (Exception e) {
            logger.error("context", e);
        }
    }

    protected SqlEngine getSqlEngine() {
        return this.sqlEngine;
    }

    @Override
    public void save(Object object) {

        try {

            final List<ModelTableField> modelTableFields = generateModel(object);

            try (PreparedStatement preparedStatement = preparedStatementInsert(connection, modelTableFields, sqlEngine.generateInsert(modelTableFields, object))) {

                preparedStatement.executeUpdate();

                try (ResultSet rsID = preparedStatement.getGeneratedKeys()) {

                    if (rsID.next()) {
                        ((PersistentEntity)object).setId(rsID.getLong(1));
                        cacheManager.addToCache(object);
                    } else {
                        throw new SgdbException("FATAL ERROR: it was not possible to obtain the sequential code after the insert");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("context", e);
        }
    }

    @Override
    public void update(Object object) {
        try {

            final List<ModelTableField> modelTableFields = generateModel(object);

            try(PreparedStatement preparedStatement = preparedStatementUpdate(connection, modelTableFields, sqlEngine.generateUpdate(modelTableFields, object))) {
                preparedStatement.executeUpdate();
                cacheManager.addToCache(object);
            }

        } catch (Exception e) {
            logger.error("context", e);
        }
    }

    @Override
    public void delete(Object object) {

        try {

            final List<ModelTableField> modelTableFields = generateModel(object);

            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlEngine.generateDelete(object));
                ((PersistentEntity)object).setId(null);
            }

        } catch (Exception e) {
            logger.error("context", e);
        }
    }

    @Override
    public void delete(Class object, String where) {

        try {

            try (Statement statement = connection.createStatement()) {
                statement.execute(sqlEngine.generateDelete(object, where));
            }

        } catch (Exception e) {
            logger.error("context", e);
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("context", e);
        }
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public Object findOne(Class classe, Long id) {

        try {

            return new LazyLoader(connection, sqlEngine).loadLazyObject(classe, id);

        } catch (Exception e) {
            logger.error("context", e);
        }

        return null;
    }

    @Override
    public ResultIterator find(Class classe, String where) {
        try {

            return new LazyLoader(connection, sqlEngine, classe).findWithWhereQueryExecute(sqlEngine.generateSelectMany(classe,where));

        } catch (Exception e) {
            logger.error("context", e);
        }

        return null;
    }

    @Override
    public ResultIterator findWithQuery(Class classe, String sql) {
        try {

            return new LazyLoader(connection, sqlEngine, classe).findWithQueryExecute(sql);

        } catch (Exception e) {
            logger.error("context", e);
        }

        return null;
    }

}
