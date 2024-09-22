package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {

  public UserDaoHibernateImpl() {
  }

  @Override
  public void createUsersTable() {
    try (Session session = Util.sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      String sql = "CREATE TABLE IF NOT EXISTS users (" +
          "id BIGINT NOT NULL AUTO_INCREMENT, " +
          "name VARCHAR(255), " +
          "lastName VARCHAR(255), " +
          "age TINYINT, " +
          "PRIMARY KEY (id))";
      session.createSQLQuery(sql)
          .executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl createUsersTable() error: " + e.getMessage()
      );
    }
  }

  @Override
  public void dropUsersTable() {
    try (Session session = Util.sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      String sql = "DROP TABLE IF EXISTS users";
      session.createSQLQuery(sql)
          .executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl dropUsersTable() error: " + e.getMessage()
      );
    }
  }

  @Override
  public void saveUser(String name, String lastName, byte age) {
    try (Session session = Util.sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      session.save(
          new User(name, lastName, age)
      );
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl saveUser() error: " + e.getMessage()
      );
    }
  }

  @Override
  public void removeUserById(long id) {
    try (Session session = Util.sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      User user = session.get(User.class, id);
      if (user != null) {
        session.delete(user);
      }
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl removeUserById() error: " + e.getMessage()
      );
    }
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();

    try (Session session = Util.sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      users.addAll(
          session.createQuery(
              "FROM User",
              User.class
          ).getResultList()
      );
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl.getAllUsers() error: " + e.getMessage()
      );
    }
    return users;
  }

  @Override
  public void cleanUsersTable() {
    try (Session session = Util.sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      String sql = "DELETE FROM User";
      session.createQuery(sql)
          .executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl cleanUsersTable() error: " + e.getMessage()
      );
    }
  }
}