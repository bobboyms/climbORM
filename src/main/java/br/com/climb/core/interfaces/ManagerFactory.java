package br.com.climb.core.interfaces;

public interface ManagerFactory {

    ClimbConnection getConnection();
    ClimbConnection getConnection(String schemaName);

}
