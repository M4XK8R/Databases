package jm.task.core.jdbc.util;

public final class LoggingUtil {

  public static void printExceptionInfo(
      String methodName,
      Exception exception
  ) {
    System.err.printf(
        "UserDaoImp %s() "
            + "\n"
            + "\t caught: %s,"
            + "\n"
            + "\t message: \"%s\"."
            + "\n",
        methodName,
        exception.getClass(),
        exception.getMessage()
            .toLowerCase()
    );
  }
}
