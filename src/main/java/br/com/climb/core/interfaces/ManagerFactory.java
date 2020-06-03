package br.com.climb.core.interfaces;

import br.com.climb.exception.SgdbException;

import java.io.Serializable;

public interface ManagerFactory extends Serializable {

    ClimbConnection getConnection() throws SgdbException;
    ClimbConnection getConnection(String schemaName) throws SgdbException;

}
