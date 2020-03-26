package br.com.climb.configfile;

import br.com.climb.configfile.interfaces.ConfigFile;
import br.com.climb.exception.ConfigFileException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FactoryConfigFileTest {

    private FactoryConfigFile factoryConfigFile = new FactoryConfigFile();

    @Test
    public void test_verifyTypeFile() throws ConfigFileException {
        assertThat(factoryConfigFile.getTypeFile("config.properties"), is("properties"));
    }

    @Test()
    public void test_verifyException1() {
        try {
            factoryConfigFile.getTypeFile("config");
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("unsupported configuration file"));
        }
    }

    @Test()
    public void test_verifyException2() {
        try {
            factoryConfigFile.getTypeFile("");
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("the configuration file cannot be null or empty"));
        }
    }

    @Test()
    public void test_verifyException3() {
        try {
            factoryConfigFile.getTypeFile(null);
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("the configuration file cannot be null or empty"));
        }
    }

    @Test()
    @DisplayName("verifica o retorno a exception \"the configuration file cannot be null or empty\" quando null")
    public void test_verifyException4() throws ConfigFileException, IOException {
        ConfigFile configFile = factoryConfigFile.getConfigFile("climb.properties");
        assert configFile != null;
        assertThat(false, is(configFile.getDatabase() == null));
        assertThat(false, is(configFile.getDriver() == null));
        assertThat(false, is(configFile.getPassword() == null));
        assertThat(false, is(configFile.getPort() == null));
        assertThat(false, is(configFile.getUrl() == null));
        assertThat(false, is(configFile.getUser() == null));
    }

    @Test()
    public void test_verifyException5() throws IOException {
        try {
            factoryConfigFile.getConfigFile("climbx.properties");
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("configuration file not found in resource"));
        }
    }

    @Test()
    public void test_verifyException6() throws IOException {
        try {
            factoryConfigFile.getConfigFile("climb.txt");
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("unsupported configuration file"));
        }
    }

}
