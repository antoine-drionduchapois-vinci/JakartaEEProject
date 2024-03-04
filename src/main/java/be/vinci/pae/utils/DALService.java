package be.vinci.pae.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The DALService interface defines methods for managing database access.
 */
public interface DALService {

  /**
   * Gets a prepared statement for the provided SQL query.
   *
   * @param sql the SQL query
   * @return a prepared statement for the SQL query
   */
  PreparedStatement getPSUser_email(String sql);

  /**
   * Closes the database connection.
   *
   * @throws SQLException if a database access error occurs
   */
  void close() throws SQLException;
}
