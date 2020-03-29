package br.com.climb.core.sqlengine;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.core.sqlengine.interfaces.SqlEngine;
import br.com.climb.exception.SgdbException;
import br.com.climb.modelbean.ModelTableField;

import java.util.List;

import static br.com.climb.utils.SuportedSgdb.POSTGRES;

public class FactorySqlEngine {

    private FactorySqlEngine(){}

    public static SqlEngine generateSqlEngine(ConfigFile configFile) throws SgdbException {

        if (configFile.getDriver().equals(POSTGRES)) {
            return new PostgresSqlEngine();
        }

        throw new SgdbException("unsupported database");

    }


}
