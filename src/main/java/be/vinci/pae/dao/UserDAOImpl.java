package be.vinci.pae.dao;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents the implementation of the UserDAO interface.
 */
public class UserDAOImpl implements UserDAO {

  //private DALService myDalService;
  private DALService myDalService = new DALServiceImpl();
  //private UserDTO myUserDTO;
  private UserDTO myUserDTO;

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
      rs.next();
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
      rs.next();
      return convertToDto(rs);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null; // Handle the case where no user is found
  }

  private UserDTO convertToDto(ResultSet rs) throws SQLException {
    // Create a new UserDTO object using the user's data
    myUserDTO = new UserImpl();

    myUserDTO.setUserId(rs.getInt(1));
    myUserDTO.setName(rs.getString(2));
    myUserDTO.setSurname(rs.getString(3));
    myUserDTO.setEmail(rs.getString(4));
    myUserDTO.setPhone(rs.getString(5));
    myUserDTO.setPassword(rs.getString(6));
    myUserDTO.setYear(rs.getString(7));
    myUserDTO.setRole(User.Role.valueOf(rs.getString(8)));

    // Convertit d'autres attributs si nécessaire
    return myUserDTO;

  }

  @Override
  public UserDTO addUser(User user) {
    PreparedStatement ps = myDalService
        .getPS(
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

  @Override
  public int getTotalStudents() {
    try (PreparedStatement ps = myDalService
        .getPS("SELECT COUNT(*) FROM projetae.utilisateurs WHERE role='STUDENT'");
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
  public List<UserDTO> getAllStudents() {
    List<UserDTO> users = new ArrayList<>();
    String sql ="SELECT * FROM projetae.utilisateurs AND projetae.utilisateurs.role='STUDENT'";
    try (PreparedStatement ps = myDalService.getPS(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        UserDTO userDTO = convertToDto(rs); // Convertir chaque ligne de résultat en UserDTO

        users.add(userDTO); // Ajouter le UserDTO à la liste des utilisateurs
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users; // Renvoyer la liste des utilisateurs
  }


  @Override
  public int getStudentsWithoutStage() {
    String sql = "SELECT COUNT(*) FROM projetae.utilisateurs "
        +
        "LEFT JOIN projetae.stages "
        +
        "ON projetae.utilisateurs.utilisateur_id = projetae.stages.utilisateur "
        +
        "WHERE projetae.stages.stage_id IS NULL AND projetae.utilisateurs.role='STUDENT'";

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
