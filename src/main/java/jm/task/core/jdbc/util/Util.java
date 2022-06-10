package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static SessionFactory SESSION_FACTORY;
    private static Connection CONNECTIONS;

    private static String url = "jdbc:mysql://localhost:3306/pp_1_1_3-4_jdbc_hibernate";
    private static String userName = "user";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String password = "mysql";

    private Util(){}

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        if(CONNECTIONS == null) {
            Class.forName(driver);
            CONNECTIONS = DriverManager.getConnection(url, userName, password);
        }

        return CONNECTIONS;
    }

    public static void connectionClose() throws SQLException {

        CONNECTIONS.close();
    }


    public static SessionFactory getSessionFactory() {

        if(SESSION_FACTORY == null) {
            SESSION_FACTORY = new Configuration()
                    .addAnnotatedClass(User.class)
                    .setProperty("hibernate.driver_class", driver)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect")
                    .setProperty("hibernate.connection.url", url)
                    .setProperty("hibernate.connection.username", userName)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .buildSessionFactory();
        }

        return SESSION_FACTORY;
    }

    public static void closeSessionFactory(){
        SESSION_FACTORY.close();
    }
}
