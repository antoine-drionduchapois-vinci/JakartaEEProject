package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCManagerImpl implements JDBCManager {

  private Connection connection;
  private final String URL = Config.getProperty("db.url");
  private final String USERNAME = Config.getProperty("db.username");
  private final String PASSWORD = Config.getProperty("db.password");

  @Override
  public Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
      System.out.println("Connection open");
    }
    return connection;
  }

  @Override
  public void close() throws SQLException {
    if (connection != null && !connection.isClosed()) {
      connection.close();
      System.out.println("Connection closed");
    }
  }
}
