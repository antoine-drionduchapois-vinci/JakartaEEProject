package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interface for managing database connections.
 */
public interface JDBCManager {

  /**
   * Retrieves a connection to the database.
   *
   * @return The database connection.
   * @throws SQLException if a database access error occurs
   */
  Connection getConnection() throws SQLException;

  /**
   * Closes the database connection.
   *
   * @throws SQLException if a database access error occurs
   */
  void close() throws SQLException;
}
