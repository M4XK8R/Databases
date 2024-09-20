package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

  private static final String URL = "jdbc:mysql://localhost:3306/db";
  private static final String USER = "root";
  private static final String PASSWORD = "U5,vv>2GySXaE!_Z(=*4";

  public static Connection getConnection() {
    Connection connection = null;

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(URL, USER, PASSWORD);
      System.out.println(
          "Connection to the database established successfully."
      );
    } catch (ClassNotFoundException e) {
      System.err.println(
          "Util getConnection() ClassNotFoundException: " + e.getMessage()
      );
    } catch (SQLException e) {
      System.err.println(
          "Util getConnection() SQL Exception: " + e.getMessage()
      );
    }
    return connection;
  }

  public static void closeConnection(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
        System.out.println(
            "Connection to the database closed successfully."
        );
      } catch (SQLException e) {
        System.err.println(
            "Error closing the connection: " + e.getMessage()
        );
      }
    }
  }
}