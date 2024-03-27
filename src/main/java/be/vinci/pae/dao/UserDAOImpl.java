package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.FatalErrorException;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents the implementation of the UserDAO interface.
 */
public class UserDAOImpl implements UserDAO {

  @Inject
  private DALBackService myDalService;

  @Inject
  private DomainFactory myDomainFactory;

  private final ResultSetMapper<UserDTO, UserImpl> userMapper = new ResultSetMapper<>();

  @Override
  public UserDTO getOneByEmail(String email) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.users WHERE email = ?");) {

      ps.setString(1, email);
      ps.execute();

      return userMapper.mapResultSetToObject(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public UserDTO getOneByID(int id) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.users WHERE user_id = ?")) {
      ps.setInt(1, id);
      ps.execute();
      return userMapper.mapResultSetToObject(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public UserDTO addUser(UserDTO userDTO) {
    PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.users (name, surname, email, phone, password, year, role)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *");
    LocalDate currentDate = LocalDate.now();
    int currentYear = currentDate.getYear();
    try {
      ps.setString(1, userDTO.getName());
      ps.setString(2, userDTO.getSurname());
      ps.setString(3, userDTO.getEmail());
      ps.setString(4, userDTO.getPhone());
      ps.setString(5, userDTO.getPassword());
      ps.setInt(6, currentYear);
      ps.setString(7, userDTO.getRole().name());
      ps.execute();
      return userMapper.mapResultSetToObject(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public int getTotalStudents() {
    try (PreparedStatement ps = myDalService
        .getPS("SELECT COUNT(*) FROM projetae.users WHERE role='STUDENT'");
        ResultSet rs = ps.executeQuery()) {

      if (rs.next()) {
        return rs.getInt(1); // Retourne le résultat du COUNT(*)
      }
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
    return 0; // Gérer le cas où il n'y a aucun résultat
  }

  @Override
  public List<UserDTO> getAllStudents() {
    String sql = "SELECT * FROM projetae.users";
    try (PreparedStatement ps = myDalService.getPS(sql)) {
      ps.execute();
      return userMapper.mapResultSetToObjectList(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }


  @Override
  public int getStudentsWithoutStage() {
    String sql = "SELECT COUNT(*) FROM projetae.users "
        +
        "LEFT JOIN projetae.internships "
        +
        "ON projetae.users.user_id = projetae.internships.user "
        +
        "WHERE projetae.internships.internship_id IS NULL AND projetae.users.role='STUDENT'";

    try (PreparedStatement ps = myDalService.getPS(sql);
        ResultSet rs = ps.executeQuery()) {

      if (rs.next()) {
        return rs.getInt(1); // Retourne le résultat du COUNT(*)
      }
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
    return 0; // Gérer le cas où il y a une erreur ou aucun résultat
  }
}
