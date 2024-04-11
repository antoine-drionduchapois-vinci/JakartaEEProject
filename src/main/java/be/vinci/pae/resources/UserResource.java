package be.vinci.pae.resources;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.resources.filters.RoleId;
import be.vinci.pae.ucc.AuthUCC;
import be.vinci.pae.ucc.UserUCC;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;


/**
 * Implementation of the UserDataService interface.
 */
@Singleton
@Path("/users")
public class UserResource {

  private static final Logger logger = LogManager.getLogger(UserResource.class);

  @Inject
  private JWT myJwt;
  @Inject
  private RoleId myRoleId;

  @Inject
  private DomainFactory domainFactory;
  @Inject
  private UserUCC myUserUCC;
  @Inject
  private AuthUCC authUCC;

  /**
   * Retrieves global statistics.
   *
   * @return an ObjectNode containing the global statistics
   */
  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getGlobalStats() {
    ThreadContext.put("route", "/users/stats");
    ThreadContext.put("method", "Post");
    ThreadContext.put("params", "NoParam");

    int studentsWithoutInternship = myUserUCC.countStudentsWithoutStage();
    int countStudents = myUserUCC.countStudents();

    // Create a JSON object to store the global statistics
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode stats = mapper.createObjectNode();
    stats.put("noStage", studentsWithoutInternship);
    stats.put("all", countStudents);

    logger.info("Status: 200 {Fetching global statistics}");
    ThreadContext.clearAll();

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
  public List<UserDTO> getUsersAsJson() {
    ThreadContext.put("route", "/users/All");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "NoParam");

    // Récupérer la liste complète des utilisateurs depuis votre DAO
    List<UserDTO> userList = myUserUCC.getUsersAsJson();

    logger.info("Status: 200 {Fetching all User}");
    ThreadContext.clearAll();
    return userList;
  }

  /**
   * Retrieves user information by user ID and returns it as JSON.
   *
   * @param token The user JWT token.
   * @return An ObjectNode representing the user's information.
   */
  @GET
  @Path("getUserInfoById")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUsersByIdAsJson(@HeaderParam("Authorization") String token,
      @DefaultValue("-1") @QueryParam("id") int id) {
    ThreadContext.put("route", "/users/getUserInfoById");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "id:" + id);

    int userId = myRoleId.chooseId(token, id);

    if (userId == 0) {
      throw new WebApplicationException("userId is required", Status.BAD_REQUEST);
    }

    UserDTO user = myUserUCC.getUsersByIdAsJson(userId);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode userInfo = mapper.createObjectNode();
    userInfo.put("name", user.getName());
    userInfo.put("surName", user.getSurname());
    userInfo.put("phone", user.getPhone());
    userInfo.put("year", user.getYear());
    userInfo.put("email", user.getEmail());
    logger.info("Status: 200 {getUsersByIdAsJson}");
    ThreadContext.clearAll();
    return userInfo;
  }

  /**
   * Modifies the password of the user.
   *
   * @param token The authorization token.
   * @param json  The JSON object containing user information including userId, email, password, and
   *              newPassword1.
   * @return The updated UserDTO object with the modified password.
   * @throws WebApplicationException Thrown if user authentication fails or if required user
   *                                 information is missing.
   */
  @POST
  @Path("changePassword")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO modifyPassword(@HeaderParam("Authorization") String token, JsonNode json) {
    ThreadContext.put("route", "/users/changePassword");
    ThreadContext.put("method", "Post");

    int userId = myJwt.getUserIdFromToken(token);
    System.out.println("blabla = " + token + " userId : " + userId);
    if (userId == 0) {
      throw new WebApplicationException("userID is required", Status.BAD_REQUEST);
    }

    if (!json.hasNonNull("userId") || !json.hasNonNull("email") || !json.hasNonNull("password")
        || !json.hasNonNull(
        "newPassword1")) {
      throw new WebApplicationException("user infos are required", Status.BAD_REQUEST);
    }
    UserDTO userDTO = domainFactory.getUser();
    userDTO.setUserId(json.get("userId").asInt());
    userDTO.setEmail(json.get("email").asText());
    userDTO.setPassword(json.get("password").asText());

    String newPassword = json.get("newPassword1").asText();

    //Check if mdp in front same as mdp in db
    authUCC.login(userDTO);
    //Modify password
    UserDTO userDTO1 = myUserUCC.modifyPassword(userDTO, newPassword);

    ThreadContext.put("params",
        "userId: " + userDTO.getUserId() + " oldPassword: " + userDTO.getPassword()
            + " newPassword: "
            + userDTO1.getPassword());
    logger.info("Status: 200 {passord changed}");

    return userDTO1;
  }

  /**
   * Changes the phone number of the user.
   *
   * @param token   The authorization token.
   * @param userDTO The UserDTO object containing user information including userId, email,
   *                password, and new phone number.
   * @return The updated UserDTO object with the modified phone number.
   * @throws WebApplicationException Thrown if user authentication fails.
   */
  @POST
  @Path("changePhoneNumber")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public UserDTO changePhoneNumber(@HeaderParam("Authorization") String token, UserDTO userDTO) {
    ThreadContext.put("route", "/users/changePhoneNumber");
    ThreadContext.put("method", "Post");

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }

    //Check if mdp in front same as mdp in db
    authUCC.login(userDTO);

    UserDTO userDTO1 = myUserUCC.changePhoneNumber(userDTO);

    ThreadContext.put("params",
        "userId: " + userDTO.getUserId() + " newPhoneNumber: " + userDTO1.getPhone());
    logger.info("Status: 200 {phone number changed}");

    return userDTO1;
  }
}