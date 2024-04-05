package be.vinci.pae.dal;

import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.FatalErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 * Implementation of the Data Access Layer service interface providing methods for database
 * interaction.
 */
public class DALServiceImpl implements DALService, DALBackService {
  
  private DataSource dataSource;
  private final String url = Config.getProperty("db.url");
  private final String username = Config.getProperty("db.username");
  private final String password = Config.getProperty("db.password");
  private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
  private final int MAX_CONNECTIONS = 3;

  /**
   * Constructs a new DALServiceImpl object and initializes the data source.
   */
  public DALServiceImpl() {
    this.dataSource = createDataSource();
  }

  /**
   * Starts the data access layer service.
   */
  @Override
  public void start() {
    connection();
  }

  private void connection() {
    try {
      Connection conn = connectionThreadLocal.get();
      if (conn == null || conn.isClosed() || !conn.isValid(5)) {
        conn = dataSource.getConnection();
        connectionThreadLocal.set(conn);
      }

    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
  }

  /**
   * Gets a prepared statement for the given SQL query.
   *
   * @param sql the SQL query
   * @return a prepared statement
   */
  @Override
  public PreparedStatement getPS(String sql) {
    try {
      return connectionThreadLocal.get().prepareStatement(sql);
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
  }


  /**
   * Commits the active connection.
   */
  @Override
  public void commit() {
    Connection conn = connectionThreadLocal.get();
    if (conn == null) {
      throw new IllegalStateException("No active connection to commit.");
    }
    try {
      conn.commit();
    } catch (SQLException e) {
      rollback(conn);
      throw new FatalErrorException(e);
    } finally {
      closeConnection(conn);
    }
  }

  private void rollback(Connection conn) {
    try {
      if (conn != null) {
        conn.rollback();
      }
    } catch (SQLException rollbackEx) {
      throw new FatalErrorException(rollbackEx);
    }
  }

  private void closeConnection(Connection conn) {
    if (conn != null) {
      try {
        conn.setAutoCommit(true);
        conn.close();
      } catch (SQLException closeEx) {
        throw new FatalErrorException(closeEx);
      } finally {
        connectionThreadLocal.remove();
      }
    }
  }

  private DataSource createDataSource() {
    BasicDataSource ds = new BasicDataSource();
    ds.setMaxTotal(MAX_CONNECTIONS);
    ds.setDriverClassName("org.postgresql.Driver");
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setDefaultAutoCommit(false);
    return ds;
  }
}

