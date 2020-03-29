package br.com.climb.core;

import br.com.climb.configfile.FactoryConfigFile;
import br.com.climb.core.interfaces.ManagerFactory;
import br.com.climb.exception.ConfigFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ClimbORM {

    private static final Logger logger = LogManager.getLogger(ClimbORM.class);

    private ClimbORM(){}

    public static ManagerFactory createManagerFactory(String fileName) {

        ManagerFactory managerFactory = null;

        try {

            managerFactory = new Manager(new FactoryConfigFile().getConfigFile(fileName));

        } catch (ConfigFileException | IOException e) {
            logger.error("context", e);
        }

        return managerFactory;
    }

}
