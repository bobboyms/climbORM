/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package br.com.climb;

import br.com.climb.core.ClimbORM;
import br.com.climb.core.interfaces.ClimbConnection;
import br.com.climb.core.interfaces.ManagerFactory;

public class App {

    public static void main(String[] args) {
        ManagerFactory managerFactory = ClimbORM.createManagerFactory("climb.properties");
        ClimbConnection connection = managerFactory.getConnection();

    }
}
