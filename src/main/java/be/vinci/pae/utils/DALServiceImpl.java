package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class DALServiceImpl implements DALService, DALBackService {

  private DataSource dataSource;
  private final String url = Config.getProperty("db.url");
  private final String username = Config.getProperty("db.username");
  private final String password = Config.getProperty("db.password");
  private final ThreadLocal<Connection> connectionThreadLocal = new ThreadLocal<>();
  private final int MAX_CONNECTIONS = 3;

  @Override
  public PreparedStatement getPS(String sql) {
    try {
      return getConnection().prepareStatement(sql);
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public void start() {
    if (dataSource == null) {
      dataSource = createDataSource();
    }
  }

  @Override
  public void commit() {
    Connection conn = connectionThreadLocal.get();
    if (conn == null) {
      throw new IllegalStateException("No active connection to commit.");
    }
    try {
      conn.commit();
    } catch (SQLException e) {
      rollbackAndClose(conn);
      throw new FatalErrorException(e);
    } finally {
      closeConnection(conn);
    }
  }

  private void rollbackAndClose(Connection conn) {
    try {
      if (conn != null) {
        conn.rollback();
      }
    } catch (SQLException rollbackEx) {
      throw new FatalErrorException(rollbackEx);
    } finally {
      closeConnection(conn);
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

  private Connection getConnection() {
    try {
      Connection conn = connectionThreadLocal.get();
      if (conn == null || conn.isClosed()) {
        conn = dataSource.getConnection();
        connectionThreadLocal.set(conn);
      }
      return conn;
    } catch (SQLException e) {
      throw new FatalErrorException(e);
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

