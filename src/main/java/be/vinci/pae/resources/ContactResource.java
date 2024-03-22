package be.vinci.pae.resources;

import be.vinci.pae.ucc.ContactUCC;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/contact")
public class ContactResource {

  @Inject
  private ContactUCC myContactUCC;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getOne(@DefaultValue("-1") @QueryParam("contactId") int contactId) {
    if (contactId == -1) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }

    ObjectNode contact = myContactUCC.getContact(contactId);
    if (contact == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return contact;
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode addOne(JsonNode json) {
    if (!json.hasNonNull("userId")) {
      throw new WebApplicationException("Bad Request", Status.BAD_REQUEST);
    }

    ObjectNode contact;

    if (json.hasNonNull("enterpriseId")) {
      contact = myContactUCC.initiateContact(json.get("userId").asInt(),
          json.get("enterpriseId").asInt());
      if (contact == null) {
        throw new WebApplicationException("not found", Status.NOT_FOUND);
      }

      return contact;

    }

    if (!json.hasNonNull("enterpriseName") || !json.hasNonNull("enterpriseLabel")
        || !json.hasNonNull("enterpriseAddress") || !json.hasNonNull("enterpriseContact")) {
      throw new WebApplicationException("Bad Request", Status.BAD_REQUEST);
    }

    contact = myContactUCC.initiateContact(json.get("userId").asInt(),
        json.get("enterpriseName").asText(), json.get("enterpriseLabel").asText(),
        json.get("enterpriseAddress").asText(), json.get("enterpriseContact").asText());

    if (contact == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return contact;
  }

  @POST
  @Path("/meet")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode meet(JsonNode json) throws WebApplicationException {
    if (!json.hasNonNull("contactId") || !json.hasNonNull("meetingPoint")) {
      throw new WebApplicationException("contactId, meetingPoint required", Status.BAD_REQUEST);
    }

    ObjectNode contact = myContactUCC.meetEnterprise(json.get("contactId").asInt(),
        json.get("meetingPoint").asText());

    if (contact == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return contact;
  }

  @POST
  @Path("/refuse")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode refuse(JsonNode json) {
    if (!json.hasNonNull("contactId") || !json.hasNonNull("reason")) {
      throw new WebApplicationException("contactId, reason required", Status.BAD_REQUEST);
    }

    ObjectNode contact = myContactUCC.indicateAsRefused(json.get("contactId").asInt(),
        json.get("reason").asText());

    if (contact == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return contact;
  }

  @POST
  @Path("/unfollow")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode unfollow(JsonNode json) {
    if (!json.hasNonNull("contactId")) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }

    ObjectNode contact = myContactUCC.unfollow(json.get("contactId").asInt());

    if (contact == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return contact;
  }
}
