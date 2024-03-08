package be.vinci.pae.api;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents the implementation of the UserDAO interface.
 */
public class UserDAOImpl implements UserDAO {

  //private DALService myDalService;
  private DALService myDalService = new DALServiceImpl();
  //private UserDTO myUserDTO;
  private UserDTO myUserDTO = new UserImpl();

  /**
   * Retrieves a user by their email address.
   *
   * @param email the email address of the user to retrieve
   * @return the user corresponding to the email address, or null if not found
   */
  @Override
  public UserDTO getOneByEmail(String email) {
    PreparedStatement ps = myDalService
        .getPS("SELECT * FROM projetae.utilisateurs WHERE email = ?");
    try {
      ps.setString(1, email);
      ps.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try (ResultSet rs = ps.getResultSet()) {
      return convertToDto(rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null; // Handle the case where no user is found
  }

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the user corresponding to the ID, or null if not found
   */
  @Override
  public UserDTO getOneByID(int id) {
    PreparedStatement ps = myDalService
        .getPS("SELECT * FROM projetae.utilisateurs WHERE utilisateur_id = ?");
    try {
      ps.setInt(1, id);
      ps.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try (ResultSet rs = ps.getResultSet()) {
      return convertToDto(rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null; // Handle the case where no user is found
  }

  private UserDTO convertToDto(ResultSet rs) throws SQLException {
    // Create a new UserDTO object using the user's data
    rs.next();
    myUserDTO.setUserId(rs.getInt(1));
    myUserDTO.setName(rs.getString(2));
    myUserDTO.setSurname(rs.getString(3));
    myUserDTO.setEmail(rs.getString(4));
    myUserDTO.setPhone(rs.getString(5));
    myUserDTO.setPassword(rs.getString(6));
    myUserDTO.setYear(rs.getString(7));
    myUserDTO.setRole(User.Role.valueOf(rs.getString(8)));

    // Close the result set
    rs.close();

    // Convert other attributes if necessary
    return myUserDTO;
  }

  @Override
  public int getTotalStudents() {
    try (PreparedStatement ps = myDalService
        .getPS("SELECT COUNT(*) FROM projetae.utilisateurs");
        ResultSet rs = ps.executeQuery()) {

      if (rs.next()) {
        return rs.getInt(1); // Retourne le résultat du COUNT(*)
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0; // Gérer le cas où il n'y a aucun résultat
  }

  @Override
  public int getStudentsWithoutStage() {
    String sql = "SELECT COUNT(*) FROM projetae.utilisateurs "
        +
        "LEFT JOIN projetae.stages "
        +
        "ON projetae.utilisateurs.utilisateur_id = projetae.stages.utilisateur "
        +
        "WHERE projetae.stages.stage_id IS NULL";

    try (PreparedStatement ps = myDalService.getPS(sql);
        ResultSet rs = ps.executeQuery()) {

      if (rs.next()) {
        return rs.getInt(1); // Retourne le résultat du COUNT(*)
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0; // Gérer le cas où il y a une erreur ou aucun résultat
  }
}
