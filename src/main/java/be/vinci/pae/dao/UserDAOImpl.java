package be.vinci.pae.dao;

import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.FatalErrorException;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.Date;
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
        "SELECT * FROM projetae.users WHERE LOWER(email) = LOWER(?)")) {

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
        "INSERT INTO projetae.users (name, surname, email, phone, password,"
            + " year, inscription_date, role, version)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING *");
    LocalDate currentDate = LocalDate.now();
    Date curDate = Date.valueOf(currentDate);
    int currentYear = currentDate.getYear();
    int previousYear = currentYear - 1;
    String academicYear = previousYear + "-" + currentYear;

    try {
      ps.setString(1, userDTO.getName());
      ps.setString(2, userDTO.getSurname());
      ps.setString(3, userDTO.getEmail());
      ps.setString(4, userDTO.getPhone());
      ps.setString(5, userDTO.getPassword());
      ps.setString(6, academicYear);
      ps.setDate(7, curDate);
      ps.setString(8, userDTO.getRole().name());
      ps.setInt(9, 1);
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
        return rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
    return 0;
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
        return rs.getInt(1);
      }
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
    return 0;
  }

  @Override
  public UserDTO modifyPassword(UserDTO userDTO) {
    String sql = "UPDATE projetae.users  SET password = ?, version = ? WHERE user_id = ?, version = ? RETURNING *;";

    try (PreparedStatement ps = myDalService.getPS(sql)) {
      ps.setString(1, userDTO.getPassword());
      ps.setInt(2, userDTO.getVersion() + 1);
      ps.setInt(3, userDTO.getUserId());
      ps.setInt(4, userDTO.getVersion());
      ps.execute();
      return userMapper.mapResultSetToObject(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public UserDTO changePhoneNumber(UserDTO userDTO) {
    String sql = "UPDATE projetae.users SET phone = ?, version = ? WHERE user_id = ?, version = ? RETURNING *;";

    try (PreparedStatement ps = myDalService.getPS(sql)) {
      ps.setString(1, userDTO.getPhone());
      ps.setInt(2, userDTO.getVersion() + 1);
      ps.setInt(3, userDTO.getUserId());
      ps.setInt(4, userDTO.getVersion() + 1);
      ps.execute();
      return userMapper.mapResultSetToObject(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }
}
