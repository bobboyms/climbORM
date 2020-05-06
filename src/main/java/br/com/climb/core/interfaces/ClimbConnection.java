package br.com.climb.core.interfaces;

public interface ClimbConnection {
    void save(Object object);
    void update(Object object);
    void update(String query);
    void delete(Object object);
    void delete(Class object, String where);
    void delete(String query);

    Transaction getTransaction();
    void close();

    Object findOne(Class classe, Long id);
    ResultIterator find(Class classe, String where);
    ResultIterator find(Class classe);
    ResultIterator findWithQuery(Class classe, String sql);

}
