package br.com.climb.configfile;

import br.com.climb.exception.ConfigFileException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PropertiesFileTest {

    private PropertiesFile propertiesFile = new PropertiesFile();

    @Test
    public void test_getInputStream() throws ConfigFileException {
        InputStream inputStream = propertiesFile.getInputStream("climb.properties");
        assertThat(false, is(inputStream == null));
    }

    @Test
    public void test_getInputStream1() {

        try {
            propertiesFile.getInputStream("climbx.properties");
        } catch (ConfigFileException e) {
            assertThat(e.getMessage(), is("configuration file not found in resource"));
        }

    }

}
