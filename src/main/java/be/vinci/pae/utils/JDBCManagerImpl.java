package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Implementation of JDBCManager interface for managing database connections.
 */
public class JDBCManagerImpl implements JDBCManager {

  private Connection connection;
  private final String URL = Config.getProperty("db.url"); // URL for the database
  private final String USERNAME = Config.getProperty("db.username"); // Username for database access
  private final String PASSWORD = Config.getProperty("db.password"); // Password for database access

  /**
   * Retrieves a connection to the database.
   *
   * @return The database connection.
   * @throws SQLException if a database access error occurs or the URL is null
   */
  @Override
  public Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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
