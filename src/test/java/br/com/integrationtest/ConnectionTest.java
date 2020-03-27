package br.com.integrationtest;

import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;
import org.junit.jupiter.api.Test;

public class ConnectionTest {

    private ManagerFactory managerFactory = ClimbORM.createManagerFactory("climb.properties");

    @Test
    public void test_connection() {
        managerFactory = ClimbORM.createManagerFactory("climb.properties");
        ClimbConnection connection = managerFactory.getConnection();
    }
}
