package be.vinci.pae.resources;

import be.vinci.pae.domain.User;
import be.vinci.pae.ucc.UserUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Implementation of the UserDataService interface.
 */
@Singleton
@Path("/users")
public class UserResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  private static final Logger logger = LogManager.getLogger(UserResource.class);

  @Inject
  private UserUCC myUserUCC;

  /**
   * Retrieves global statistics.
   *
   * @return an ObjectNode containing the global statistics
   */
  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getGlobalStats() {

    int studentsWithoutInternship = myUserUCC.countStudentsWithoutStage();
    int countStudents = myUserUCC.countStudents();

    // Create a JSON object to store the global statistics
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode stats = mapper.createObjectNode();
    stats.put("noStage", studentsWithoutInternship);
    stats.put("all", countStudents);
    logger.info("Fetching global statistics...");
    return stats;
  }

  /**
   * Retrieves all users.
   *
   * @return an ObjectNode containing all users
   */
  @GET
  @Path("All")
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayNode getUsersAsJson() {
    ObjectMapper mapper = new ObjectMapper();
    ArrayNode usersArray = mapper.createArrayNode();

    try {
      // Récupérer la liste complète des utilisateurs depuis votre DAO
      List<User> userList = myUserUCC.getUsersAsJson();

      // Parcourir chaque utilisateur et les ajouter à l'ArrayNode
      for (User user : userList) {
        ObjectNode userNode = mapper.createObjectNode();
        userNode.put("userId", user.getUserId());
        userNode.put("name", user.getName());
        userNode.put("surname", user.getSurname());
        userNode.put("email", user.getEmail());
        userNode.put("role", user.getRole().name());
        userNode.put("annee", user.getYear());
        // Ajoutez d'autres attributs utilisateur au besoin
        usersArray.add(userNode);
      }
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
    }
    logger.info("Fetching all User...");
    return usersArray;
  }

  /**
   * Retrieves user information by user ID and returns it as JSON.
   *
   * @param json The JSON object containing the JWT token.
   * @return An ObjectNode representing the user's information.
   */
  @POST
  @Path("getUserInfoById")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUsersByIdAsJson(JsonNode json) {

    try {
      // Get token from JSON
      String jsonToken = json.get("token").asText();
      // Decode Token
      DecodedJWT jwt = JWT.require(jwtAlgorithm)
          .withIssuer("auth0")
          .build() // create the JWTVerifier instance
          .verify(jsonToken); // verify the token
      // Het userId from decodedToken
      int userId = jwt.getClaim("user").asInt();
      // Assuming the token includes a "user" claim holding the user ID
      if (userId == -1) {
        throw new JWTVerificationException("User ID claim is missing");
      }
      User user = myUserUCC.getUsersByIdAsJson(userId);

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode userInfo = mapper.createObjectNode();
      userInfo.put("name", user.getName());
      userInfo.put("surName", user.getSurname());
      userInfo.put("phone", user.getPhone());
      userInfo.put("year", user.getYear());
      userInfo.put("email", user.getEmail());
      return userInfo;
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
    }
    return null;
  }

}