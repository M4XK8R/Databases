package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {

  private static final String USER_NAME = "user_name";
  private static final String USER_SECOND_NAME = "user_second_name";
  private static final int MIN_AGE = 18;
  private static final int CAPACITY = 32;

  private final static UserService userService = new UserServiceImpl();

  public static void main(String[] args) {
    ArrayList<User> users = new ArrayList<>(CAPACITY);

    for (int i = 0; i < CAPACITY; i++) {
      users.add(
          new User(
              USER_NAME + i,
              USER_SECOND_NAME + i,
              (byte) (MIN_AGE + i)
          )
      );
    }

    userService.dropUsersTable();
    userService.createUsersTable();

    for (User user : users) {
      userService.saveUser(
          user.getName(),
          user.getLastName(),
          user.getAge()
      );
    }

    List<User> usersFromDatabase = userService.getAllUsers();
    usersFromDatabase.forEach(System.out::println);

    for (int i = usersFromDatabase.size(); i > 0; i -= 2) {
      userService.removeUserById(i);
    }
    System.out.println(
        "\n\t After removing some users"
    );

    userService.getAllUsers()
        .forEach(System.out::println);

    userService.cleanUsersTable();
    System.out.println(
        "\n\t After cleaning up"
    );

    System.out.println(
        "Is users table is empty: " +
            userService.getAllUsers()
                .isEmpty()
    );
  }
}
