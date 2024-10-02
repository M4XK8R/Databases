package jm.task.core.jdbc.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateJpaUtil {

  private static final EntityManagerFactory entityManagerFactory;

  static {
    try {
      entityManagerFactory = Persistence.createEntityManagerFactory(
          "MyPersistenceUnit"
      );
    } catch (Throwable e) {
      throw new ExceptionInInitializerError(e);
    }
  }

  public static EntityManager getEntityManager() {
    return entityManagerFactory.createEntityManager();
  }

  public static void tearDown() {
    if (entityManagerFactory != null) {
      entityManagerFactory.close();
    }
  }
}
