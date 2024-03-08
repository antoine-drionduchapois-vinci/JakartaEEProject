package be.vinci.pae.services;

import be.vinci.pae.api.UserDAO;
import be.vinci.pae.api.UserDAOImpl;
import be.vinci.pae.domain.User;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


/**
 * Implementation of the UserDataService interface.
 */
@Singleton
@Path("/auths")
public class UserUCCImpl implements UserUCC {


  private UserDAO myUserDAO = new UserDAOImpl();

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Override
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String email = json.get("email").asText();
    String password = json.get("password").asText();
    ObjectNode publicUser = login(email, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    return publicUser;
  }


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

  /**
   * Retrieves global statistics.
   *
   * @return an ObjectNode containing the global statistics
   */
  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getGlobalStats() {
    int totalStudents = myUserDAO.getTotalStudents();
    int studentsWithoutInternship = myUserDAO.getStudentsWithoutStage();

    // Create a JSON object to store the global statistics
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode stats = mapper.createObjectNode();
    stats.put("total", totalStudents);
    stats.put("noStage", studentsWithoutInternship);

    return stats;
  }
}