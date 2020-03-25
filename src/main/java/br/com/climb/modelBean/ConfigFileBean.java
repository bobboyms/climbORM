package br.com.climb.modelBean;

public abstract class ConfigFileBean {

    private String sgdb;
    private String url;
    private String user;
    private String password;
    private String database;
    private boolean ssl = false;

    public String getSgdb() {
        return sgdb;
    }

    public void setSgdb(String sgdb) {
        this.sgdb = sgdb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }



//    sgdb=postgres
//            url=127.0.0.1
//    user=postgres
//            password=123456789
//    database=Teste
//            port=5432
//    ssl=false

}
