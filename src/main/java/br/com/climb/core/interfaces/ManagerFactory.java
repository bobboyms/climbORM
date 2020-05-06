package br.com.climb.core.interfaces;

import br.com.climb.exception.SgdbException;

public interface ManagerFactory {

    ClimbConnection getConnection() throws SgdbException;
    ClimbConnection getConnection(String schemaName) throws SgdbException;

}
