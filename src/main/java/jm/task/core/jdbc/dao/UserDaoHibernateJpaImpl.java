package jm.task.core.jdbc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateJpaUtil;
import jm.task.core.jdbc.util.LoggingUtil;

public class UserDaoHibernateJpaImpl implements UserDao {

  public UserDaoHibernateJpaImpl() {

  }

  @Override
  public void createUsersTable() {
    String sql = "CREATE TABLE IF NOT EXISTS users (" +
        "id BIGINT NOT NULL AUTO_INCREMENT, " +
        "name VARCHAR(255), " +
        "lastName VARCHAR(255), " +
        "age TINYINT, " +
        "PRIMARY KEY (id)" +
        ")";

    run(entityManager -> entityManager
        .createNativeQuery(sql)
        .executeUpdate());
  }


  @Override
  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";
    run(entityManager -> entityManager
        .createNativeQuery(sql)
        .executeUpdate());
  }

  @Override
  public void saveUser(
      String name,
      String lastName,
      byte age
  ) {
    run(entityManager -> entityManager.persist(
        new User(name, lastName, age)
    ));
  }


  @Override
  public void removeUserById(long id) {
    run(entityManager -> {
      User user = entityManager.find(User.class, id);
      if (user != null) {
        entityManager.remove(user);
      }
    });
  }

  @Override
  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();

    run(entityManager -> users.addAll(
        entityManager.createQuery(
            "from User ",
            User.class
        ).getResultList()
    ));

    return users;
  }

  @Override
  public void cleanUsersTable() {
    String jpql = "DELETE FROM User";

    run(entityManager -> entityManager
        .createQuery(jpql)
        .executeUpdate());
  }

  /*
Private sector
 */
  private Consumer<EntityManager> jpqlExecutor(String jpql) {
    return entityManager -> entityManager
        .createQuery(jpql)
        .executeUpdate();
  }

  private Consumer<EntityManager> sqlExecutor(String sql) {
    return entityManager -> entityManager
        .createNativeQuery(sql)
        .executeUpdate();
  }

  private void run(Consumer<EntityManager> entityManagerConsumer) {
    if (entityManagerConsumer == null) {
      return;
    }
    EntityManager entityManager = null;
    EntityTransaction transaction = null;

    try {
      entityManager = HibernateJpaUtil.getEntityManager();
      transaction = entityManager.getTransaction();
      transaction.begin();
      entityManagerConsumer.accept(entityManager);
      transaction.commit();
    } catch (Exception e) {
      LoggingUtil.printExceptionInfo("run", e);
      if (transaction != null && transaction.isActive()) {
        transaction.rollback();
      }
    } finally {
      if (entityManager != null && entityManager.isOpen()) {
        try {
          entityManager.close();
        } catch (Exception e) {
          LoggingUtil.printExceptionInfo("run", e);
        }
      }
    }
  }
}
