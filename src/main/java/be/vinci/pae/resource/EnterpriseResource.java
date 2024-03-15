package be.vinci.pae.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * This interface defines EnterpriseResouce.
 */
public interface EnterpriseResource {

  /**
   * Retrieves all enterprises.
   *
   * @return An ObjectNode containing all the enterprises.
   */
  @GET
  @Path("enterprises")
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getAllEnterprises();

  /**
   * Retrieves enterprises associated with a specific user ID.
   * The user ID is expected to be passed in the request body as JSON.
   *
   * @param json A JsonNode containing the user ID.
   * @return An ObjectNode containing the enterprises associated with the user.
   */
  @POST
  @Path("enterprises")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getEnterprisesByUserId(JsonNode json);
}
