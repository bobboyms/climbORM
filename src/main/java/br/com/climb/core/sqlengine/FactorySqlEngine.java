package br.com.climb.core.sqlengine;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.exception.SgdbException;
import br.com.climb.modelbean.ModelTableField;

import java.util.List;

import static br.com.climb.utils.SuportedSgdb.MY_SQL;
import static br.com.climb.utils.SuportedSgdb.POSTGRES;

public final class FactorySqlEngine {

    private FactorySqlEngine(){}

    public static SqlEngine generateSqlEngine(ConfigFile configFile) throws SgdbException {

        if (configFile.getDriver().equals(POSTGRES)) {
            return new PostgresSqlEngine();
        }

        if (configFile.getDriver().equals(MY_SQL)) {
            return new MySqlEngine();
        }

        throw new SgdbException("unsupported database");

    }


}
