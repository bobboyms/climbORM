package br.com.climb.core;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.core.sgdbconnection.MySqlConnection;
import br.com.climb.core.sgdbconnection.PostgresConnection;
import br.com.climb.core.sqlengine.interfaces.HasSchema;
import br.com.climb.exception.SgdbException;

import static br.com.climb.utils.SuportedSgdb.MY_SQL;
import static br.com.climb.utils.SuportedSgdb.POSTGRES;

public class Manager implements ManagerFactory {

    private ConfigFile configFile;

    public Manager(ConfigFile configFile) {
        this.configFile = configFile;
    }

    @Override
    public ClimbConnection getConnection() throws SgdbException {

        if (configFile.getDriver().equals(POSTGRES)) {
            return new PostgresConnection(configFile);
        }

        if (configFile.getDriver().equals(MY_SQL)) {
            return new MySqlConnection(configFile);
        }

        throw new SgdbException("Connection driver not supported: "+ configFile.getDriver());

    }

    @Override
    public ClimbConnection getConnection(String schemaName) throws SgdbException {


        if (configFile.getDriver().equals(POSTGRES)) {
            final ClimbConnection climbConnection = new PostgresConnection(configFile);
            ((HasSchema)climbConnection).setSchema(schemaName);
            return climbConnection;
        }

        if (configFile.getDriver().equals(MY_SQL)) {
            return new MySqlConnection(configFile);
        }

        throw new SgdbException("Connection driver not supported: "+ configFile.getDriver());

    }
}
