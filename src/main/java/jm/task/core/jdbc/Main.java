package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {

  public static void main(String[] args) {
    UserService userService = new UserServiceImpl();

    userService.createUsersTable();

    for (int i = 0; i < 4; i++) {
      String userName = "name" + i;
      userService.saveUser(
          userName,
          "secondName" + i,
          (byte) i
      );
      System.out.println(
          "User с именем — " + userName + " добавлен в базу данных"
      );
    }

    userService.getAllUsers()
        .forEach(System.out::println);

    userService.cleanUsersTable();
    userService.dropUsersTable();
  }
}
















