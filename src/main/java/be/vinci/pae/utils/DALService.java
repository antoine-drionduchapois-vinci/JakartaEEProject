package be.vinci.pae.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DALService {

  PreparedStatement getPSUser_email(String sql);

  void close() throws SQLException;
}
