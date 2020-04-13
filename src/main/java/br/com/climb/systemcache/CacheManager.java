package br.com.climb.systemcache;

import br.com.climb.systemcache.model.CommandDTO;

import java.io.IOException;

public interface CacheManager {

    void addToCache(Object value) throws IOException;
    Object getValueCache(Class classe, Long id) throws Exception;

}
