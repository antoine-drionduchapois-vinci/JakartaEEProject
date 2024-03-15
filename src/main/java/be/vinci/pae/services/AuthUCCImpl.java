package be.vinci.pae.services;

import be.vinci.pae.api.UserDAO;
import be.vinci.pae.api.UserDAOImpl;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.User.Role;
import be.vinci.pae.domain.UserImpl;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Implementation of the UserDataService interface.
 */

public class AuthUCCImpl implements AuthUCC {


  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  private UserDAO myUserDAO = new UserDAOImpl();

  @Override
  public ObjectNode login(String email, String password) {
    User user = (User) myUserDAO.getOneByEmail(email);
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


  @Override
  public ObjectNode register(User user1) {
    User user = (User) myUserDAO.addUser(user1);
    if (user == null) {
      return null;
    }
    String token;
    try {
      token = JWT.create().withIssuer("auth0")
          .withClaim("user", user.getUserId()).sign(this.jwtAlgorithm);
      ObjectNode publicUser = jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", user.getUserId())
          .put("email", user.getEmail());
      return publicUser;
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }

  @Override
  public User createUserAndReturn(String name, String firstname, String email, String telephone,
      String password, String role) {
    User tempUser = (User) myUserDAO.getOneByEmail(email);
    if (tempUser != null) {
      return null; // L'utilisateur existe déjà !
    }
    tempUser = new UserImpl();
    tempUser.setName(name);
    tempUser.setSurname(firstname);
    tempUser.setEmail(email);
    tempUser.setPhone(telephone);
    tempUser.setPassword(tempUser.hashPassword(password));
    tempUser.setRole(Role.valueOf(role));
    return tempUser;
  }


}