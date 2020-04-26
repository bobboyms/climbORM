package br.com.climb.core.sqlengine.interfaces;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.modelbean.ModelTableField;

import java.util.List;

public interface SqlEngine {
    String generateInsert(List<ModelTableField> modelTableFields, Object entity);
    String generateUpdate(List<ModelTableField> modelTableFields, Object entity);
    String generateDelete(Object entity);
    String generateDelete(Class classe, String where) throws Exception;
    String generateSelectOne(Class classe, Long id) throws Exception;
    String generateSelectMany(Class classe, String where) throws Exception;
    String generateSelectMany(Class classe) throws Exception;
    String generateSelectOneAtt(Long id, String field, String entity);

}
