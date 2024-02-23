package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import be.vinci.pae.services.utils.Json;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Implementation of the UserDataService interface.
 */
public class UserDataServiceImpl implements UserDataService {

  private static final String COLLECTION_NAME = "users";
  private static Json<User> jsonDB = new Json<>(User.class);

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Override
  public User getOne(String email) {
    var users = jsonDB.parse(COLLECTION_NAME);
    return users.stream().filter(user -> user.getEmail().equals(email)).findAny().orElse(null);
  }

  @Override
  public User getOne(int id) {

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
          .withClaim("user", user.getId()).sign(this.jwtAlgorithm);
      return jsonMapper.createObjectNode()
          .put("token", token)
          .put("id", user.getId())
          .put("email", user.getEmail());
    } catch (Exception e) {
      System.out.println("Unable to create token");
      return null;
    }
  }
}