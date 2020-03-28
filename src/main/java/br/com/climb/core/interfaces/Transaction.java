package br.com.climb.core.interfaces;

public interface Transaction {
    public void start();
    public void commit();
    public void rollback();
}
