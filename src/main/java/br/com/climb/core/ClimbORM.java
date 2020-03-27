package br.com.climb.core;

import br.com.climb.configfile.FactoryConfigFile;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.ConfigFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClimbORM {

    private static final Logger logger = LogManager.getLogger(ClimbORM.class);

    private ClimbORM(){}

    public static ManagerFactory createManagerFactory(String fileName) {

        ManagerFactory managerFactory = null;

        try {

            managerFactory = new Manager(new FactoryConfigFile().getConfigFile(fileName));

        } catch (ConfigFileException | IOException e) {
            logger.error("ERROR: {}", e.getMessage());
        }

        return managerFactory;
    }

}
