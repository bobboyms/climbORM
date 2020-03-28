package br.com.climb.core.sgdbconnection;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.PersistentEntity;
import br.com.climb.core.TransactionDB;
import br.com.climb.core.interfaces.ClimbConnection;

import br.com.climb.core.interfaces.Transaction;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.exception.SgdbException;
import br.com.climb.modelbean.ModelTableField;

import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static br.com.climb.utils.ReflectionUtil.generateModel;
import static org.apache.logging.log4j.LogManager.getLogger;
import static br.com.climb.utils.SqlUtil.preparedStatementInsert;
import static br.com.climb.core.jdbcconnection.FactoryJdbcConnection.createJdbcConnection;
import static br.com.climb.core.sqlengine.FactorySqlEngine.generateSqlEngine;

public class PostgresConnection implements ClimbConnection {

    private static final Logger logger = getLogger(PostgresConnection.class);

    private Connection connection;
    private ConfigFile configFile;
    private Transaction transaction;

    public PostgresConnection(ConfigFile configFile) {
        this.configFile = configFile;
        this.connection = createJdbcConnection(configFile);
        this.transaction = new TransactionDB(connection);
    }

    @Override
    public void save(Object object) {

        try {

            final List<ModelTableField> modelTableFields = generateModel(object);
            final SqlEngine sqlEngine = generateSqlEngine(configFile, modelTableFields, object);
            final PreparedStatement preparedStatement = preparedStatementInsert(connection,modelTableFields,sqlEngine.generateInsert());

            preparedStatement.executeUpdate();

            try (ResultSet rsID = preparedStatement.getGeneratedKeys();) {
                if (rsID.next()) {
                    Long id = rsID.getLong("id");
                    PersistentEntity persistentEntity = (PersistentEntity) object;
                    persistentEntity.setId(id);
                } else {
                    throw new SgdbException("FATAL ERROR: it was not possible to obtain the sequential code after the insert");
                }
            }

        } catch (Exception e) {
            logger.error("context", e);
        }

    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void delete(Object object) {

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
}
