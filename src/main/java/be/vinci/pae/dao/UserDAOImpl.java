package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
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

  private DALService myDalService = new DALServiceImpl();

  @Inject
  private DomainFactory myDomainFactory;

  private final ResultSetMapper<User, UserImpl> userMapper = new ResultSetMapper<>();

  @Override
  public User getOneByEmail(String email) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.users WHERE email = ?");) {
      ps.setString(1, email);
      ps.execute();
      return userMapper.mapResultSetToObject(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public User getOneByID(int id) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.users WHERE user_id = ?");) {
      ps.setInt(1, id);
      ps.execute();
      return userMapper.mapResultSetToObject(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public User addUser(User user) {
    PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.users (name, surname, email, phone, password, year, role)"
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
        .getPS("SELECT COUNT(*) FROM projetae.users WHERE role='STUDENT'");
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
  public List<User> getAllStudents() {
    String sql = "SELECT * FROM projetae.users";
    try (PreparedStatement ps = myDalService.getPS(sql)) {
      ps.execute();
      return userMapper.mapResultSetToObjectList(ps.getResultSet(), UserImpl.class,
          myDomainFactory::getUser);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
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
      e.printStackTrace();
    }
    return 0; // Gérer le cas où il y a une erreur ou aucun résultat
  }
}
