package be.vinci.pae.resource;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.services.UserUCC;
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
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


/**
 * Implementation of the UserDataService interface.
 */
@Singleton
@Path("/auths")
public class UserResourceImpl implements UserResource {


  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private UserUCC myUserUCC;

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
    ObjectNode publicUser = myUserUCC.login(email, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    return publicUser;
  }



  /**
   * Retrieves global statistics.
   *
   * @return an ObjectNode containing the global statistics
   */
  @Override
  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getGlobalStats() {
    int totalStudents = 1;
    int studentsWithoutInternship = myUserUCC.getGlobalStats();

    // Create a JSON object to store the global statistics
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode stats = mapper.createObjectNode();
    stats.put("total", totalStudents);
    stats.put("noStage", studentsWithoutInternship);

    return stats;
  }

  /**
   * Retrieves all users.
   *
   * @return an ObjectNode containing all users
   */
  @Override
  @GET
  @Path("All")
  @Produces(MediaType.APPLICATION_JSON)
  public ArrayNode getUsersAsJson() {
    ObjectMapper mapper = new ObjectMapper();
    ArrayNode usersArray = mapper.createArrayNode();

    try {
      // Récupérer la liste complète des utilisateurs depuis votre DAO
      List<UserDTO> userList = myUserUCC.getUsersAsJson();

      // Parcourir chaque utilisateur et les ajouter à l'ArrayNode
      for (UserDTO user : userList) {
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

    return usersArray;
  }


  @Override
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("All fileds are required",
          Response.status(Response.Status.BAD_REQUEST)
              .entity("email or password required").type("text/plain").build());
    }
    String name = json.get("name").asText();
    String firstname = json.get("firstname").asText();
    String email = json.get("email").asText();
    String telephone = json.get("telephone").asText();
    String password = json.get("password").asText();
    String role = json.get("role").asText();

    User user = myUserUCC.createUserAndReturn(name, firstname, email, telephone, password, role);

    // Try to register
    ObjectNode publicUser = myUserUCC.register(user);
    if (publicUser == null) {
      throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
          .entity("this resource already exists").type(MediaType.TEXT_PLAIN)
          .build());
    }
    return publicUser;
  }

  /**
   * Retrieves users info.
   *
   * @return an ObjectNode containing users info
   */
  @Override
  @POST
  @Path("getUserInfoById")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUsersByIdAsJson(JsonNode json) {

    try {
      //Get token from JSON
      String jsonToken = json.get("token").asText();
      //Decode Token
      DecodedJWT jwt = JWT.require(jwtAlgorithm)
          .withIssuer("auth0")
          .build() // create the JWTVerifier instance
          .verify(jsonToken); // verify the token
      //Het userId from decodedToken
      int userId = jwt.getClaim("user").asInt();
      // Assuming the token includes a "user" claim holding the user ID
      if (userId == -1) {
        throw new JWTVerificationException("User ID claim is missing");
      }
      UserDTO user = myUserUCC.getUsersByIdAsJson(userId);

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