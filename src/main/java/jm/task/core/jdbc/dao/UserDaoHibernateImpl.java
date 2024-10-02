package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {

  public UserDaoHibernateImpl() {
  }

  @Override
  public void createUsersTable() {
    Transaction transaction = null;

    try (Session session = HibernateUtil.sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      String sql =
          "CREATE TABLE IF NOT EXISTS users (" +
              "id BIGINT NOT NULL AUTO_INCREMENT, " +
              "name VARCHAR(255), " +
              "lastName VARCHAR(255), " +
              "age TINYINT, " +
              "PRIMARY KEY (id)"
              + ")";
      session
          .createSQLQuery(sql)
          .executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl createUsersTable() error: " +
              e.getMessage()
      );
      rollbackTransaction(transaction);
    }
  }

  @Override
  public void dropUsersTable() {
    Transaction transaction = null;

    try (Session session = HibernateUtil.sessionFactory.openSession()) {
      String sql = "DROP TABLE IF EXISTS users";
      transaction = session.beginTransaction();
      session
          .createSQLQuery(sql)
          .executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl dropUsersTable() error: " +
              e.getMessage()
      );
      rollbackTransaction(transaction);
    }
  }

  @Override
  public void saveUser(
      String name,
      String lastName,
      byte age
  ) {
    Transaction transaction = null;

    try (Session session = HibernateUtil.sessionFactory.openSession()) {
      transaction = session.beginTransaction();

      session.save(
          new User(name, lastName, age)
      );
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl saveUser() error: " +
              e.getMessage()
      );
      rollbackTransaction(transaction);
    }
  }

  @Override
  public void removeUserById(long id) {
    Transaction transaction = null;

    try (Session session = HibernateUtil.sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      User user = session.get(User.class, id);

      if (user != null) {
        session.delete(user);
      }
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl removeUserById() error: " +
              e.getMessage()
      );
      rollbackTransaction(transaction);
    }
  }

  @Override
  public List<User> getAllUsers() {
    Transaction transaction = null;
    List<User> users = new ArrayList<>();

    try (Session session = HibernateUtil.sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      users.addAll(
          session.createQuery(
              "FROM User",
              User.class
          ).getResultList()
      );
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl.getAllUsers() error: " +
              e.getMessage()
      );
      rollbackTransaction(transaction);
    }
    return users;
  }

  @Override
  public void cleanUsersTable() {
    Transaction transaction = null;

    try (Session session = HibernateUtil.sessionFactory.openSession()) {
      transaction = session.beginTransaction();
      String sql = "DELETE FROM User";
      session
          .createQuery(sql)
          .executeUpdate();
      transaction.commit();
    } catch (Exception e) {
      System.err.println(
          "UserDaoHibernateImpl cleanUsersTable() error: " +
              e.getMessage()
      );
      rollbackTransaction(transaction);
    }
  }

  /*
  Private sector
   */

  private void rollbackTransaction(Transaction transaction) {
    if (transaction != null) {
      transaction.rollback();
    }
  }
}