package jm.task.core.jdbc.util;
import jm.task.core.jdbc.model.User;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static String url = "jdbc:mysql://localhost:3306/mysql";
    private static String user = "Artem";
    private static String password = "!QAZ2wsx";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    private static SessionFactory sessionFactory = createMySQLSessionFactory();
    public static Connection getMySQLConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    private static SessionFactory createMySQLSessionFactory() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", driver);
        properties.setProperty("hibernate.connection.url", url);
        properties.setProperty("hibernate.connection.username", user);
        properties.setProperty("hibernate.connection.password", password);
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.jdbc.log.warnings", "false");
        try {
            //ServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(properties).build();
            //Metadata metadata = new MetadataSources(standardRegistry).addAnnotatedClass(Util.class).getMetadataBuilder().build();
            //return metadata.getSessionFactoryBuilder().build();
            Configuration configuration = new Configuration().setProperties(properties);
            configuration.addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        } catch (Exception e) {
            System.out.println("Connection error:");
            e.printStackTrace();
            return null;
        }
    }

    public static SessionFactory getMySQLSessionFactory() {
        return sessionFactory;
    }

}
