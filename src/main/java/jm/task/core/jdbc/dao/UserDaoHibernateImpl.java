package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.*;
import jm.task.core.jdbc.model.*;
import org.hibernate.Session;

import java.text.MessageFormat;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String createTableSQL = """
                                    CREATE TABLE IF NOT EXISTS my_users_h (
                                      id          bigint  NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                      name        varchar(100) NOT NULL,
                                      last_name   varchar(100) NOT NULL,
                                      age         tinyint NOT NULL)
                                """;
        Session session = Util.getMySQLSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(createTableSQL).executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS my_users_h";
        Session session = Util.getMySQLSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(dropTableSQL).executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User newUser = new User(name, lastName, age);
        Session session = Util.getMySQLSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        String deleteUserSQL = "DELETE FROM my_users_h WHERE id = :id";
        Session session = Util.getMySQLSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(deleteUserSQL).setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String getAllUsersSQL = "SELECT * FROM my_users_h";
        Session session = Util.getMySQLSessionFactory().openSession();
        try {
            List<User> usersList = session.createNativeQuery(getAllUsersSQL).addEntity(User.class).list();
            return usersList;
        } finally {
            session.close();
        }
    }

    @Override
    public void cleanUsersTable() {
        String clearTableSQL = "TRUNCATE TABLE my_users_h";
        Session session = Util.getMySQLSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.createNativeQuery(clearTableSQL).executeUpdate();
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }
}
