package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBCManagerImpl is an implementation of the JDBCManager interface. It provides methods for
 * managing JDBC connections to a database.
 */
public class DALServiceImpl implements DALService{

  private Connection connection;
  private final String url = Config.getProperty("db.url");
  private final String username = Config.getProperty("db.username");
  private final String password = Config.getProperty("db.password");
  private PreparedStatement PS;


  public DALServiceImpl() {
    try {
      this.connection = DriverManager.getConnection(url, username, password);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public PreparedStatement getPSUser_email(String sql) {
    try {
      PS = connection.prepareStatement(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return PS;
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
