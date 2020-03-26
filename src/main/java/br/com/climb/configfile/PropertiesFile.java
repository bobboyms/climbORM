package br.com.climb.configfile;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.exception.ConfigFileException;
import br.com.climb.modelbean.ConfigFileBean;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile extends ConfigFileBean implements ConfigFile {

    protected InputStream getInputStream(final String fileName) throws ConfigFileException {

        InputStream inputStream = PropertiesFile.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new ConfigFileException("configuration file not found in resource");
        }

        return inputStream;
    }

    protected Properties loadProperties(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    protected void generateConfigData(Properties properties) {
        super.setDatabase(properties.getProperty("persistence.jdbc.database"));
        super.setUrl(properties.getProperty("persistence.jdbc.url"));
        super.setUser(properties.getProperty("persistence.jdbc.user"));
        super.setPassword(properties.getProperty("persistence.jdbc.password"));
        super.setPort(properties.getProperty("persistence.jdbc.driver"));
        super.setDriver(properties.getProperty("persistence.jdbc.port"));
    }

    public PropertiesFile(String fileName) throws ConfigFileException, IOException {
        generateConfigData(loadProperties(getInputStream(fileName)));
    }

}
