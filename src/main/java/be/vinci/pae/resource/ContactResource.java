package be.vinci.pae.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Defines the RESTful resource for authentication operations including login
 * and registration.
 */
public interface ContactResource {

  /**
   * Retrieves users all contacts.
   * @param json A JsonNode containing the user ID.
   * @return an ObjectNode containing contacts info
   */
  @POST
  @Path("getContacts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getUsersContacts(JsonNode json);
}
