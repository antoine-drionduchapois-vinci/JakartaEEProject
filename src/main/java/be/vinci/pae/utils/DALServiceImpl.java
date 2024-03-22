package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;


/**
 * DALServiceImpl is an implementation of the DALService interface. It provides methods for managing
 * database access using JDBC.
 */
public class DALServiceImpl implements DALService, DALBackService {

  private DataSource dataSource;
  private final String url = Config.getProperty("db.url");
  private final String username = Config.getProperty("db.username");
  private final String password = Config.getProperty("db.password");

  private ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();

  /**
   * Gets a prepared statement for the provided SQL query.
   *
   * @param sql the SQL query
   * @return a prepared statement for the SQL query
   * @throws RuntimeException if a database access error occurs
   */
  @Override
  public PreparedStatement getPS(String sql) {
    try (PreparedStatement ps = getConnection().prepareStatement(sql)) {
      return ps;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Initializes the data source for database connections.
   */
  @Override
  public void start() {
    this.dataSource = createDataSource();
  }

  /**
   * Commits the transaction.
   */
  @Override
  public void commit() {
    Connection conn = null;
    try {
      // Obtain the connection from the data source
      conn = dataSource.getConnection();

      // Begin the transaction
      conn.setAutoCommit(false);

      // Execute your transaction validation and processing operations here

      // Validate and commit the transaction
      conn.commit();
    } catch (SQLException e) {
      // Rollback the transaction in case of error
      if (conn != null) {
        try {
          conn.rollback();
        } catch (SQLException rollbackEx) {
          // Handle rollback errors
          rollbackEx.printStackTrace();
        }
      }
      // Handle transaction error
      e.printStackTrace();
    } finally {
      // Close the connection
      if (conn != null) {
        try {
          conn.setAutoCommit(true); // Restore automatic commit mode
          conn.close();
        } catch (SQLException closeEx) {
          // Handle connection closing errors
          closeEx.printStackTrace();
        }
      }
    }
  }


  /**
   * Closes the database connection.
   *
   * @throws SQLException if a database access error occurs
   */
  public void close() throws SQLException {
    Connection conn = connectionThreadLocal.get();
    if (conn != null && !conn.isClosed()) {
      conn.close();
      System.out.println("Connection closed");
      connectionThreadLocal.remove(); // Remove the connection from thread local
    }
  }

  /**
   * Creates and configures the data source for database connections.
   *
   * @return the data source configured with connection details
   */
  private DataSource createDataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setDriverClassName("org.h2.Driver");
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    return ds;
  }

  /**
   * Obtains a connection from the data source.
   *
   * @return a connection from the data source
   * @throws SQLException if a database access error occurs
   */
  private Connection getConnection() throws SQLException {
    Connection conn = connectionThreadLocal.get();
    if (conn == null || conn.isClosed()) {
      conn = dataSource.getConnection();
      connectionThreadLocal.set(conn);
    }
    return conn;
  }
}
