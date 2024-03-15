package be.vinci.pae.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * This interface defines userResource.
 */
public interface UserResource {

  /**
   * Retrieves global statistics related to users.
   *
   * @return An ObjectNode containing the global statistics.
   */
  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getGlobalStats();

  /**
   * Fetches all users and returns them in a JSON array format.
   *
   * @return An ArrayNode containing the users.
   */
  @GET
  @Path("All")
  @Produces(MediaType.APPLICATION_JSON)
  ArrayNode getUsersAsJson();

  /**
   * Retrieves information for a specific user by their ID.
   * The user ID is expected to be passed in the request body as JSON.
   *
   * @param json A JsonNode containing the user ID in the format {"id": "userId"}.
   * @return An ObjectNode containing the user information.
   */
  @POST
  @Path("getUserInfoById")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getUsersByIdAsJson(JsonNode json);
}
