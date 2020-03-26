package br.com.climb.configfile;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.exception.ConfigFileException;
import br.com.climb.modelbean.ConfigFileBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile extends ConfigFileBean implements ConfigFile {

    private static final Logger logger = LogManager.getLogger(PropertiesFile.class);

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

    protected PropertiesFile(String fileName) throws ConfigFileException, IOException {
        generateConfigData(loadProperties(getInputStream(fileName)));
    }

    public static ConfigFile generateConfigFile(String fileName) {
        try {
            return new PropertiesFile(fileName);
        } catch (ConfigFileException | IOException e) {
            logger.error(e.getMessage());
        }

        return null;
    }


}
