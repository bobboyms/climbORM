package br.com.climb.core;

import br.com.climb.core.interfaces.ManagerFactory;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClimbOrmTest {

    @Test
    public void test_createManagerFactory () {
        ManagerFactory managerFactory = ClimbORM.createManagerFactory("climb.properties");
        assertThat(false, is(managerFactory == null));
    }

    @Test
    public void test_createManagerFactory1 () {
        ManagerFactory managerFactory = ClimbORM.createManagerFactory("climbx.properties");
        assertThat(true, is(managerFactory == null));
    }

}
