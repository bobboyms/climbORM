package br.com.climb.configfile;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.exception.ConfigFileException;
import br.com.climb.modelBean.ConfigFileBean;

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

    public PropertiesFile(String fileName) {

    }
}
