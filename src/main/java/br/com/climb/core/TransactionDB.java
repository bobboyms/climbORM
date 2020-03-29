package br.com.climb.core;


import br.com.climb.core.interfaces.Transaction;
import br.com.climb.core.sgdbconnection.PostgresConnection;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

import static org.apache.logging.log4j.LogManager.getLogger;

public class TransactionDB implements Transaction {

    private static final Logger logger = getLogger(TransactionDB.class);

    private Connection connection;

    public TransactionDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void start() {
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("context", e);
        }
    }

    @Override
    public void commit() {
        try {
            this.connection.commit();
            this.connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("context", e);
        }
    }

    @Override
    public void rollback() {
        try {
            this.connection.rollback();
            this.connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("context", e);
        }
    }
}
