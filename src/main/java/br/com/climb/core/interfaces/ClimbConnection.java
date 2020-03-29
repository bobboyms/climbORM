package br.com.climb.core.interfaces;

public interface ClimbConnection {
    void save(Object object);
    void update(Object object);
    void delete(Object object);

    void close();
    Transaction getTransaction();

    Object findOne(Class classe, Long id);
    ResultIterator find(Class classe, String where);
    ResultIterator findWithQuery(Class classe, String sql);

}
