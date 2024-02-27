package be.vinci.pae.services;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.User.Role;
import be.vinci.pae.utils.Config;
import be.vinci.pae.utils.JDBCManager;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Implementation of the UserDataService interface.
 */
public class UserDataServiceImpl implements UserDataService {

  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private JDBCManager jdbcManager;

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  User createUser(ResultSet rs) throws SQLException {
    rs.next();
    User user = myDomainFactory.getUser();
    user.setUserId(rs.getInt(1));
    user.setSurname(rs.getString(2));
    user.setName(rs.getString(3));
    user.setEmail(rs.getString(4));
    user.setPhone(rs.getString(5));
    user.setPassword(rs.getString(6));
    user.setYear(rs.getString(7));
    user.setRole(Role.valueOf(rs.getString(8)));
    return user;
  }

  @Override
  public User getOne(String email) {
    try {
      PreparedStatement ps = jdbcManager.getConnection()
          .prepareStatement("SELECT * FROM projetae.utilisateurs WHERE email = ?");
      ps.setString(1, email);
      ps.execute();
      try (ResultSet rs = ps.getResultSet()) {
        return createUser(rs);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public User getOne(int id) {
    try {
      PreparedStatement ps = jdbcManager.getConnection()
          .prepareStatement("SELECT * FROM projetae.utilisateurs WHERE utilisateur_id = ?");
      ps.setInt(1, id);
      ps.execute();
      try (ResultSet rs = ps.getResultSet()) {
        return createUser(rs);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public ObjectNode login(String email, String password) {
    User user = getOne(email);
    if (user == null || !user.checkPassword(password)) {
      return null;
    }
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("user", user.getUserId()).sign(this.jwtAlgorithm);
      return jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", user.getUserId())
          .put("email", user.getEmail());
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }
}