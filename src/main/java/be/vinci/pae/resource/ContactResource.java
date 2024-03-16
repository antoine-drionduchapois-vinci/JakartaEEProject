package be.vinci.pae.resource;

import be.vinci.pae.ucc.ContactUCC;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/contact")
public class ContactResource {

  @Inject
  private ContactUCC myContactUCC;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode addOne(JsonNode json) {
    if (!json.hasNonNull("userId") || !json.hasNonNull("enterpriseId")) {
      throw new WebApplicationException("Bad Request", Status.BAD_REQUEST);
    }
    return myContactUCC.initiateContact(json.get("userId").asInt(),
        json.get("enterpriseId").asInt());
  }
}
