package br.com.climb.core.sgdbconnection;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.sqlengine.interfaces.HasSchema;

public class MySqlConnection extends ConnectionOperation {

    public MySqlConnection(ConfigFile configFile) {
        super(configFile);
    }

}
