package br.com.climb.core.sgdb;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.jdbcconnection.FactoryJdbcConnection;

import java.sql.Connection;

public class PostgresConnection implements ClimbConnection {

    private Connection connection;

    public PostgresConnection(ConfigFile configFile) {
        this.connection = FactoryJdbcConnection.createJdbcConnection(configFile);
    }

    @Override
    public void save(Object object) {

    }

    @Override
    public void update(Object object) {

    }

    @Override
    public void delete(Object object) {

    }
}
