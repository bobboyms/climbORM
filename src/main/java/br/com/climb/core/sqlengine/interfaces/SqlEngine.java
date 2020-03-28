package br.com.climb.core.sqlengine.interfaces;

import br.com.climb.configfile.interfaces.ConfigFile;

public interface SqlEngine {

    String generateInsert();
    String generateUpdate();
    String generateDelete();
    String generateDelete(String where);

}
