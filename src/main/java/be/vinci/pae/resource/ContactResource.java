package be.vinci.pae.resource;

import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.ucc.ContactUCC;
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
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

/**
 * The ContactResource class represents a RESTful web service endpoint for
 * managing contact-related
 * functionality.
 */
@Singleton
@Path("/contact")
public class ContactResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));

  @Inject
  private ContactUCC myContactUCC;

  /**
   * Retrieves the contacts associated with the authenticated user.
   * 
   * @param json The JSON object containing the authentication token.
   * @return An ObjectNode containing the contacts associated with the
   *         authenticated user,
   *         or an error response if an exception occurs during processing.
   */
  @POST
  @Path("getContacts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUsersContacts(JsonNode json) {

    try {
      // Get token from JSON
      String jsonToken = json.get("token").asText();
      // Decode Token
      DecodedJWT jwt = JWT.require(jwtAlgorithm)
          .withIssuer("auth0")
          .build() // create the JWTVerifier instance
          .verify(jsonToken); // verify the token
      // Het userId from decodedToken
      int userId = jwt.getClaim("user").asInt();
      // Assuming the token includes a "user" claim holding the user ID
      if (userId == -1) {
        throw new JWTVerificationException("User ID claim is missing");
      }

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode response = mapper.createObjectNode();
      ArrayNode contactArray = mapper.createArrayNode();

      try {
        // Retrieve all contacts from your DAO
        List<ContactDTO> contacts = myContactUCC.getAllUsersContact(userId);

        // Iterate through each contact and add them to the response
        for (ContactDTO contactDTO : contacts) {
          ObjectNode contactNode = mapper.createObjectNode();
          contactNode.put("contact_id", contactDTO.getContactId());
          contactNode.put("description", contactDTO.getDescription());
          contactNode.put("state", contactDTO.getState());
          contactNode.put("refusal_reason", contactDTO.getReasonRefusal());
          contactNode.put("year", contactDTO.getYear());
          contactNode.put("user_id", contactDTO.getUser());
          contactNode.put("enterprise_id", contactDTO.getEntreprise());
          contactArray.add(contactNode);
        }
        response.set("enterprises", contactArray);
        return response;
      } catch (Exception e) {
        // Handle exceptions
        e.printStackTrace();
        // Add your exception handling logic here
        response.put("error", e.getMessage());
        return response;
      }
    } catch (Exception e) {
      // Handle exceptions
      e.printStackTrace();
      // Add your exception handling logic here
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode errorResponse = mapper.createObjectNode();
      errorResponse.put("error", e.getMessage());
      return errorResponse;
    }
  }

  /**
   * Retrieves the contact information associated with the specified contact ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return An ObjectNode containing the contact information, or throws a
   *         WebApplicationException
   *         with status 400 (Bad Request) if the contactId is not provided, or
   *         404 (Not Found)
   *         if the contact is not found.
   */
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

  /**
   * Adds a new contact.
   *
   * @param json The JSON object representing the new contact to add.
   * @return An ObjectNode containing the newly added contact information, or
   *         throws a
   *         WebApplicationException with status 400 (Bad Request) if the userId
   *         is not provided
   *         or if required enterprise information is missing.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode addOne(JsonNode json) {
    if (!json.hasNonNull("userId")) {
      throw new WebApplicationException("Bad Request", Status.BAD_REQUEST);
    }

    if (json.hasNonNull("enterpriseId")) {
      return myContactUCC.initiateContact(json.get("userId").asInt(),
          json.get("enterpriseId").asInt());
    }

    if (!json.hasNonNull("enterpriseName") || !json.hasNonNull("enterpriseLabel")
        || !json.hasNonNull("enterpriseAddress") || !json.hasNonNull("enterpriseContact")) {
      throw new WebApplicationException("Bad Request", Status.BAD_REQUEST);
    }

    ObjectNode contact = myContactUCC.initiateContact(json.get("userId").asInt(),
        json.get("enterpriseName").asText(), json.get("enterpriseLabel").asText(),
        json.get("enterpriseAddress").asText(), json.get("enterpriseContact").asText());

    if (contact == null) {
      throw new WebApplicationException("not found", Status.NOT_FOUND);
    }

    return contact;
  }

  /**
   * Handles the meeting with an enterprise.
   *
   * @param json The JSON object containing the contact ID and meeting point.
   * @return An ObjectNode representing the updated contact information.
   * @throws WebApplicationException If the contactId or meetingPoint is not
   *                                 provided, or if the contact is not found.
   */
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

  /**
   * Handles indicating contact as refused.
   *
   * @param json The JSON object containing the contact ID and reason for refusal.
   * @return An ObjectNode representing the updated contact information.
   * @throws WebApplicationException If the contactId or reason is not provided,
   *                                 or if the contact is not found.
   */
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

  /**
   * Handles unfollowing a contact.
   *
   * @param json The JSON object containing the contact ID.
   * @return An ObjectNode representing the updated contact information.
   * @throws WebApplicationException If the contactId is not provided, or if the
   *                                 contact is not found.
   */
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
