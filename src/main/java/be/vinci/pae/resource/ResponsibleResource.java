package be.vinci.pae.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Interface representing Responsible Resource.
 */
public interface ResponsibleResource {

  /**
   * Retrieves the responsible associated with the given user ID.
   *
   * @param json The JSON node containing the user ID for which to retrieve the responsible.
   * @return The JSON object representing the responsible, or null if not found.
   */
  @POST
  @Path("responsable")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getResponsableByUserId(JsonNode json);
}
