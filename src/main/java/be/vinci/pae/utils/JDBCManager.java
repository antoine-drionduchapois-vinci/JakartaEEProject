package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The JDBCManager interface defines methods for managing JDBC connections to a database.
 */
public interface JDBCManager {

  Connection getConnection() throws SQLException;

  void close() throws SQLException;
}
