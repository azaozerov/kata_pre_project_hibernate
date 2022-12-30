package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.*;
import jm.task.core.jdbc.model.*;
import org.hibernate.Session;

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
        runNativeQuery(createTableSQL);
    }

    @Override
    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS my_users_h";
        runNativeQuery(dropTableSQL);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User newUser = new User(name, lastName, age);
        Session session = Util.getMySQLSessionFactory().openSession();
        session.beginTransaction();
        session.save(newUser);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        User deleteUser = new User();
        deleteUser.setId(id);
        Session session = Util.getMySQLSessionFactory().openSession();
        session.beginTransaction();
        session.delete(deleteUser);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        String getAllUsersSQL = "SELECT * FROM my_users_h";
        Session session = Util.getMySQLSessionFactory().openSession();
        List<User> usersList = session.createNativeQuery(getAllUsersSQL).addEntity(User.class).list();
        session.close();
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        String clearTableSQL = "TRUNCATE TABLE my_users_h";
        runNativeQuery(clearTableSQL);
    }
    private void runNativeQuery(String query) {
        Session session = Util.getMySQLSessionFactory().openSession();
        session.beginTransaction();
        session.createNativeQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
