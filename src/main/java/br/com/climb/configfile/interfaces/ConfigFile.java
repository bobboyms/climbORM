package br.com.climb.configfile.interfaces;

public interface ConfigFile {

    public String getSgdb();
    public String getUrl();
    public String getUser();
    public String getPassword();
    public String getDatabase();
    public boolean isSsl();

}
