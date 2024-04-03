package be.vinci.pae.dal;

import java.sql.PreparedStatement;

/**
 * DALBackService is an interface providing a method for obtaining a prepared statement for the
 * provided SQL query.
 */
public interface DALBackService {

  /**
   * Gets a prepared statement for the provided SQL query.
   *
   * @param sql the SQL query
   * @return a prepared statement for the SQL query
   * @throws RuntimeException if a database access error occurs
   */
  PreparedStatement getPS(String sql);
}
