package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

  private final Connection connection;

  public UserDaoJDBCImpl() {
    connection = Util.getConnection();
  }

  public void createUsersTable() {
    String sql = "CREATE TABLE IF NOT EXISTS users (" +
        "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
        "name VARCHAR(255) NOT NULL, " +
        "lastName VARCHAR(255) NOT NULL, " +
        "age TINYINT NOT NULL" +
        ")";

    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.err.println(
          "UserDaoJDBCImpl createUsersTable() " + e.getMessage()
      );
    }
  }

  public void dropUsersTable() {
    String sql = "DROP TABLE IF EXISTS users";

    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.err.println(
          "UserDaoJDBCImpl dropUsersTable() " + e.getMessage()
      );
    }
  }

  public void saveUser(
      String name,
      String lastName,
      byte age
  ) {
    String sql = "INSERT INTO users "
        + "(name, lastName, age) "
        + "VALUES (?, ?, ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, lastName);
      preparedStatement.setByte(3, age);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.err.println(
          "UserDaoJDBCImpl saveUser() " + e.getMessage()
      );
    }
  }

  public void removeUserById(long id) {
    String sql = "DELETE FROM users WHERE id = ?";

    try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
      preparedStatement.setLong(1, id);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.err.println(
          "UserDaoJDBCImpl removeUserById() " + e.getMessage()
      );
    }
  }

  public List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM users";

    try (
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql)
    ) {
      while (resultSet.next()) {
        User user = new User();
        user.setId(
            resultSet.getLong("id")
        );
        user.setName(
            resultSet.getString("name")
        );
        user.setLastName(
            resultSet.getString("lastName")
        );
        user.setAge(
            resultSet.getByte("age")
        );
        users.add(user);
      }
    } catch (SQLException e) {
      System.err.println(
          "UserDaoJDBCImpl getAllUsers() " + e.getMessage()
      );
    }
    return users;
  }

  public void cleanUsersTable() {
    String sql = "DELETE FROM users";

    try (Statement statement = connection.createStatement()) {
      statement.executeUpdate(sql);
    } catch (SQLException e) {
      System.err.println(
          "UserDaoJDBCImpl cleanUsersTable() " + e.getMessage()
      );
    }
  }
}
