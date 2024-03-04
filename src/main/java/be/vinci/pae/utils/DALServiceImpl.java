package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * DALServiceImpl is an implementation of the DALService interface. It provides methods for
 * managing database access using JDBC.
 */
public class DALServiceImpl implements DALService {

  private Connection connection;
  private final String url = Config.getProperty("db.url");
  private final String username = Config.getProperty("db.username");
  private final String password = Config.getProperty("db.password");
  private PreparedStatement ps;


  /**
   * Constructs a new DALServiceImpl instance and establishes a connection to the database.
   */
  public DALServiceImpl() {
    try {
      this.connection = DriverManager.getConnection(url, username, password);

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * Gets a prepared statement for the provided SQL query.
   *
   * @param sql the SQL query
   * @return a prepared statement for the SQL query
   * @throws RuntimeException if a database access error occurs
   */
  @Override
  public PreparedStatement getPSUser_email(String sql) {
    try {
      ps = connection.prepareStatement(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return ps;
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
