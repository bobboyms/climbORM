package br.com.climb.core.sqlengine.interfaces;

import br.com.climb.configfile.interfaces.ConfigFile;

public interface SqlEngine {

    String generateInsert();
    String generateUpdate();
    String generateDelete();
    String generateDelete(String where);
    String generateSelectOne(Class classe, Long id) throws Exception;
    String generateSelectMany(Class classe, String where) throws Exception;
    String generateSelectOneAtt(Long id, String field, String entity);

}
