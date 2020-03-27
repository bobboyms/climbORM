package br.com.climb.core.jdbcconnection;

import br.com.climb.configfile.interfaces.ConfigFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FactoryJdbcConnection {

    private static final Logger logger = LogManager.getLogger(FactoryJdbcConnection.class);
    private static final String POSTGRES = "org.postgresql.Driver";

    private FactoryJdbcConnection() {}

    public static Connection createJdbcConnection(ConfigFile configFile) {

        Connection connection = null;

        if (configFile.getDriver().equals(POSTGRES)) {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://" +
                                configFile.getUrl() + ":" + configFile.getPort() + "/" + configFile.getDatabase());
            } catch (SQLException e) {
                logger.error("ERROR: {}" ,e.getMessage());
            }
        }

        return connection;
    }

}
