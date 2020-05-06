package br.com.climb.core.sqlengine;


import org.apache.logging.log4j.Logger;

import static br.com.climb.utils.ReflectionUtil.generateModel;
import static org.apache.logging.log4j.LogManager.getLogger;


public abstract class ModelEngine {

    private static final Logger logger = getLogger(ModelEngine.class);

    protected StringBuilder getAttributes(Class classe) {

        final StringBuilder attributes = new StringBuilder();

        try {
            generateModel(classe.getDeclaredConstructor().newInstance()).stream().forEach((modelTableField)->{
                if (modelTableField.getType() == byte[].class) {
                } else {
                    attributes.append(modelTableField.getAttribute() + ",");
                }

            });
        } catch (Exception e) {
            logger.error("context", e);
        }

        return attributes;
    }

}
