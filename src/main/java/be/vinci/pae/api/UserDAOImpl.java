package be.vinci.pae.api;

import be.vinci.pae.domain.User.Role;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.services.UserUCC;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Represents the authentication resource providing endpoints related to user authentication.
 */

public class UserDAOImpl implements UserDAO {


  //@Inject
  //private DALService myDalService;
  private DALService myDalService = new DALServiceImpl();
  //@Inject
  //private UserDTO myUserDTO;
  private UserDTO myUserDTO = new UserImpl();



  @Override
  public UserDTO getOneByEmail(String email) {
    PreparedStatement ps = myDalService.getPSUser_email("SELECT * FROM projetae.utilisateurs WHERE email = ?");
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

    return null; // Gérer le cas où aucun utilisateur n'est trouvé
  }

  @Override
  public UserDTO getOneByID(int id) {
    PreparedStatement ps = myDalService.getPSUser_email("SELECT * FROM projetae.utilisateurs WHERE utilisateur_id = ?");
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

    return null; // Gérer le cas où aucun utilisateur n'est trouvé
  }

  private UserDTO convertToDto(ResultSet rs)throws SQLException {
    // Créez un nouvel objet UserDto en utilisant les données de l'utilisateur
    rs.next();
    myUserDTO.setUserId(rs.getInt(1));
    myUserDTO.setName(rs.getString(2));
    myUserDTO.setSurname(rs.getString(3));
    myUserDTO.setEmail(rs.getString(4));
    myUserDTO.setPhone(rs.getString(5));
    myUserDTO.setPassword(rs.getString(6));
    myUserDTO.setYear(rs.getString(7));
    myUserDTO.setRole(Role.valueOf(rs.getString(8)));


    // Convertissez d'autres attributs si nécessaire
    return myUserDTO;
  }

}
