package jm.task.core.jdbc.util;

import java.util.Properties;
import jm.task.core.jdbc.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Util {

  public final static SessionFactory sessionFactory;
  private static ServiceRegistry serviceRegistry;

  static {
    sessionFactory = buildSessionFactory();
  }

  public static void shutdown() {
    if (serviceRegistry != null) {
      StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }
  }

  // Private sector
  private static SessionFactory buildSessionFactory() {
    try {
      Configuration configuration = hibernateConfiguration();
      serviceRegistry = buildRegistry(configuration.getProperties());
      return configuration.buildSessionFactory(serviceRegistry);
    } catch (Exception e) {
      System.err.println(
          "Initial SessionFactory creation failed." + e.getMessage()
      );
      shutdown();
      throw new ExceptionInInitializerError(e);
    }
  }

  private static ServiceRegistry buildRegistry(Properties properties) {
    return new StandardServiceRegistryBuilder()
        .applySettings(properties)
        .build();
  }

  private static Configuration hibernateConfiguration() {
    Configuration configuration = new Configuration();

    configuration.setProperty(
        "hibernate.connection.driver_class",
        "com.mysql.cj.jdbc.Driver"
    );
    configuration.setProperty(
        "hibernate.connection.url",
        "jdbc:mysql://localhost:3306/db"
    );
    configuration.setProperty(
        "hibernate.connection.username",
        "root"
    );
    configuration.setProperty(
        "hibernate.connection.password",
        "U5,vv>2GySXaE!_Z(=*4"
    );
    configuration.setProperty(
        "hibernate.hbm2ddl.auto",
        "create"
    );
    configuration.addAnnotatedClass(User.class);

    return configuration;
  }
}
