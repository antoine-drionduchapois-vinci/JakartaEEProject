package be.vinci.pae.resources;

import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.ucc.UserUCC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
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
  private UserUCC myUserUCC;

  @Inject
  private JWT myJwt;

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
  public ObjectNode getUsersByIdAsJson(@HeaderParam("Authorization") String token) {

    int userId = myJwt.getUserIdFromToken(token);

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
    return userInfo;
  }
}