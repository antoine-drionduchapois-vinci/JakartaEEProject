package be.vinci.pae.utils;

import java.sql.Connection;
import java.sql.SQLException;

public interface JDBCManager {

  Connection getConnection() throws SQLException;

  void close() throws SQLException;
}
