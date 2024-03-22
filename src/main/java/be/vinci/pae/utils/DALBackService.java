package be.vinci.pae.utils;

import java.sql.PreparedStatement;

public interface DALBackService {

  /**
   * Gets a prepared statement for the provided SQL query.
   *
   * @param sql the SQL query
   * @return a prepared statement for the SQL query
   */
  PreparedStatement getPS(String sql);

}
