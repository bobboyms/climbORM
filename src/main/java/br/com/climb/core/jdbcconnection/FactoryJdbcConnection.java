package br.com.climb.core.jdbcconnection;

import br.com.climb.configfile.interfaces.ConfigFile;

import static br.com.climb.utils.SuportedSgdb.MY_SQL;
import static br.com.climb.utils.SuportedSgdb.POSTGRES;

import br.com.climb.exception.SgdbException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class FactoryJdbcConnection {

    private static final Logger logger = LogManager.getLogger(FactoryJdbcConnection.class);

    private static HikariConfig hikariConfig = new HikariConfig();
    private static HikariDataSource hikariDataSource;

    private FactoryJdbcConnection() {}

    protected static String getJdbcUrl(ConfigFile configFile) throws SgdbException {

        if (configFile.getDriver().equals(POSTGRES)) {
            return "jdbc:postgresql://" + configFile.getUrl() + ":" + configFile.getPort() + "/" + configFile.getDatabase();
        }

        if (configFile.getDriver().equals(MY_SQL)) {
            return "jdbc:mysql://" + configFile.getUrl() + ":" + configFile.getPort() + "/" + configFile.getDatabase();
        }

        throw new SgdbException("unsupported database");
    }

    public static Connection createJdbcConnection(ConfigFile configFile) {

        Connection connection = null;

        try {

            hikariConfig.setJdbcUrl(getJdbcUrl(configFile));
            hikariConfig.setUsername(configFile.getUser());
            hikariConfig.setPassword(configFile.getPassword());
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            if (hikariDataSource == null || hikariDataSource.isClosed()) {
                hikariDataSource = new HikariDataSource(hikariConfig);
            }

            connection = hikariDataSource.getConnection();

        } catch (SQLException | SgdbException e) {
            logger.error("context", e);
        }

        return connection;

    }

}
