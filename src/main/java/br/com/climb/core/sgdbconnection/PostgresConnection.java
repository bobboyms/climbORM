package br.com.climb.core.sgdbconnection;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.sqlengine.interfaces.HasSchema;

public class PostgresConnection extends ConnectionOperation implements HasSchema {

    private String schema = "public";

    public PostgresConnection(ConfigFile configFile) {
        super(configFile);
    }

    @Override
    public void setSchema(String schema) {
        this.schema = schema;
        ((HasSchema)getSqlEngine()).setSchema(schema);
    }

    @Override
    public String getSchema() {
        return this.schema;
    }
}
