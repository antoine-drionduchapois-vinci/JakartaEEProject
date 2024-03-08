package be.vinci.pae.api;

import be.vinci.pae.domain.UserDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserDAO {

  UserDTO getOneByEmail(String email);

  UserDTO getOneByID(int id);

  int getTotalStudents();
  int getStudentsWithoutStage();
}
