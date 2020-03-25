package br.com.climb.configfile;

import br.com.climb.exception.ConfigFileException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FactoryConfigFileTest {

    private FactoryConfigFile factoryConfigFile = new FactoryConfigFile();

    @Test
    @DisplayName("verifica o retorno da extenção do arquivo")
    public void test_verifyTypeFile() throws ConfigFileException {
        assertThat(factoryConfigFile.getTypeFile("config.properties"), is("properties"));
    }

    @Test()
    @DisplayName("verifica o retorno a exeption \"unsupported configuration file\"")
    public void test_verifyException1() {
        try {
            assertThat(factoryConfigFile.getTypeFile("config"), is("properties"));
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("unsupported configuration file"));
        }
    }

    @Test()
    @DisplayName("verifica o retorno a exception \"the configuration file cannot be null or empty\" quando vazio")
    public void test_verifyException2() {
        try {
            assertThat(factoryConfigFile.getTypeFile(""), is("properties"));
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("the configuration file cannot be null or empty"));
        }
    }

    @Test()
    @DisplayName("verifica o retorno a exception \"the configuration file cannot be null or empty\" quando null")
    public void test_verifyException3() {
        try {
            assertThat(factoryConfigFile.getTypeFile(null), is("properties"));
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("the configuration file cannot be null or empty"));
        }
    }

}
