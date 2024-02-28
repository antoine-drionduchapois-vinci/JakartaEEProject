package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * JDBCManagerImpl is an implementation of the JDBCManager interface. It provides methods for
 * managing JDBC connections to a database.
 */
public class JDBCManagerImpl implements JDBCManager {

  private Connection connection;
  private final String url = Config.getProperty("db.url");
  private final String username = Config.getProperty("db.username");
  private final String password = Config.getProperty("db.password");

  /**
   * Retrieves a connection to the database.
   *
   * @return The database connection.
   * @throws SQLException if a database access error occurs or the URL is null
   */
  @Override
  public Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = DriverManager.getConnection(url, username, password);
      System.out.println("Connection open");
    }
    return connection;
  }

  /**
   * Closes the database connection.
   *
   * @throws SQLException if a database access error occurs
   */
  @Override
  public void close() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
      System.out.println("Connection closed");
    }
  }
}
