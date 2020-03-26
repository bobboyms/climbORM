package br.com.climb.configfile;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.exception.ConfigFileException;

import static java.util.Objects.isNull;
import static br.com.climb.utils.Utils.isStringNullOrEmpty;

public class FactoryConfigFile {

    private static final String FILE_PROPERTIES = "properties";
    private static final String FILE_YML =  "yml";

    protected String getTypeFile(final String fileName) throws ConfigFileException {

        if (isStringNullOrEmpty(fileName)) {
            throw new ConfigFileException("the configuration file cannot be null or empty");
        }

        String[] values =  fileName.split("\\.");
        if (isNull(values) || values.length == 1) {
            throw new ConfigFileException("unsupported configuration file");
        }

        return values[1].toLowerCase();
    }

    public ConfigFile getConfigFile(final String fileName) throws ConfigFileException {

        final String fileType = getTypeFile(fileName);

        if (fileType.equals(FILE_PROPERTIES)) {
            return PropertiesFile.generateConfigFile(fileName);
        }

        if (fileType.equals(FILE_YML)) {
            return null;
        }

        throw new ConfigFileException("unsupported configuration file " + fileName);

    }

}
