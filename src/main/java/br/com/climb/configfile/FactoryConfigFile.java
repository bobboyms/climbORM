package br.com.climb.configfile;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.exception.ConfigFileException;

import java.io.IOException;

import static br.com.climb.utils.Utils.isStringNullOrEmpty;

public class FactoryConfigFile {

    private static final String FILE_PROPERTIES = "properties";

    protected String getTypeFile(final String fileName) throws ConfigFileException {

        if (isStringNullOrEmpty(fileName)) {
            throw new ConfigFileException("the configuration file cannot be null or empty");
        }

        String[] values =  fileName.split("\\.");
        if (values.length == 1) {
            throw new ConfigFileException("unsupported configuration file");
        }

        return values[1].toLowerCase();
    }

    public ConfigFile getConfigFile(final String fileName) throws ConfigFileException, IOException {

        final String fileType = getTypeFile(fileName);

        if (fileType.equals(FILE_PROPERTIES)) {
            return new PropertiesFile(fileName);
        }

        throw new ConfigFileException("unsupported configuration file");

    }

}
