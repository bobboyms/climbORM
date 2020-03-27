package br.com.climb.core;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.core.sgdb.PostgresConnection;

public class Manager implements ManagerFactory {

    private static final String POSTGRES = "org.postgresql.Driver";

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

        return climbConnection;
    }
}
