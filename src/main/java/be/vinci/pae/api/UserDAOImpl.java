package be.vinci.pae.api;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

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
        .getPSUser_email("SELECT * FROM projetae.utilisateurs WHERE email = ?");
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
        .getPSUser_email("SELECT * FROM projetae.utilisateurs WHERE utilisateur_id = ?");
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
    if (rs.next()) { // Vérifie s'il y a une ligne dans le ResultSet
      // Crée un nouvel objet UserDTO en utilisant les données de l'utilisateur
      myUserDTO.setUserId(rs.getInt(1));
      myUserDTO.setName(rs.getString(2));
      myUserDTO.setSurname(rs.getString(3));
      myUserDTO.setEmail(rs.getString(4));
      myUserDTO.setPhone(rs.getString(5));
      myUserDTO.setPassword(rs.getString(6));
      myUserDTO.setYear(rs.getString(7));
      myUserDTO.setRole(User.Role.valueOf(rs.getString(8)));

      // Ferme le ResultSet
      rs.close();

      // Convertit d'autres attributs si nécessaire
      return myUserDTO;
    } else {
      return null; // Aucun utilisateur trouvé, renvoie null ou effectue une autre action appropriée
    }
  }

  @Override
  public UserDTO addUser(User user) {
    PreparedStatement ps = myDalService
        .getPSUser_email(
            "INSERT INTO projetae.utilisateurs (nom, prenom, email, telephone, mdp, annee, role)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)");
    LocalDate currentDate = LocalDate.now();
    int currentYear = currentDate.getYear();
    try {
      ps.setString(1, user.getName());
      ps.setString(2, user.getSurname());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getPhone());
      ps.setString(5, user.getPassword());
      ps.setInt(6, currentYear);
      ps.setString(7, user.getRole().name());
      ps.executeUpdate();
      return getOneByEmail(user.getEmail());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
