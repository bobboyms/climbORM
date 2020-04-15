package br.com.climb.core;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.core.sgdbconnection.MySqlConnection;
import br.com.climb.core.sgdbconnection.PostgresConnection;
import br.com.climb.core.sqlengine.interfaces.HasSchema;

import static br.com.climb.utils.SuportedSgdb.MY_SQL;
import static br.com.climb.utils.SuportedSgdb.POSTGRES;

public class Manager implements ManagerFactory {

    private ConfigFile configFile;

    public Manager(ConfigFile configFile) {
        this.configFile = configFile;
    }

    @Override
    public ClimbConnection getConnection() {

        ClimbConnection climbConnection = null;

        if (configFile.getDriver().equals(POSTGRES)) {
            climbConnection = new PostgresConnection(configFile);
        }

        if (configFile.getDriver().equals(MY_SQL)) {
            climbConnection = new MySqlConnection(configFile);
        }

        return climbConnection;
    }

    @Override
    public ClimbConnection getConnection(String schemaName) {

        ClimbConnection climbConnection = null;

        if (configFile.getDriver().equals(POSTGRES)) {
            climbConnection = new PostgresConnection(configFile);
            ((HasSchema)climbConnection).setSchema(schemaName);
        }

        if (configFile.getDriver().equals(MY_SQL)) {
            climbConnection = new MySqlConnection(configFile);
        }

        return climbConnection;
    }
}
