package be.vinci.pae.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Defines the RESTful resource for managing responsables. This resource provides the functionality
 * to retrieve responsable information based on user IDs.
 */
public interface ResponsibleResource {

  /**
   * Retrieves the responsable associated with a given user ID. The user ID is expected to be
   * provided within the request body in JSON format.
   *
   * @param json A {@link JsonNode} containing the request data, including the user ID for which the
   *             responsable information is being requested. The user ID should be supplied under a
   *             specific key, e.g., {"userId": 123}.
   * @return An {@link ObjectNode} containing the responsable information associated with the
   *         provided user ID. The structure of the returned JSON object includes details specific to the
   *         responsable, such as name, contact details, and any other relevant information.
   */
  @POST
  @Path("responsable")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getResponsableByUserId(JsonNode json);
}
