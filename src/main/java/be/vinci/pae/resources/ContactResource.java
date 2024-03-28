package be.vinci.pae.resources;

import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.ucc.ContactUCC;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.utils.JWTDecryptToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
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
 * Resource class for managing contacts between users and enterprises.
 */
@Singleton
@Path("/contact")
public class ContactResource {

  private JWTDecryptToken decryptToken = new JWTDecryptToken();
  private static final Logger logger = LogManager.getLogger(ContactResource.class);
  @Inject
  private JWT myJwt;
  @Inject
  private ContactUCC myContactUCC;
  @Inject
  private EnterpriseUCC myEnterpriseUCC;

  /**
   * Retrieves a contact by its ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @param token The authorization token.
   * @return The contact as JSON.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getOne(@DefaultValue("-1") @QueryParam("contactId") int contactId,
      @HeaderParam("Authorization") String token) {
    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "contactId:" + contactId);

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }

    if (contactId == -1) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }
    ObjectNode objectNode = convertDTOToJson(myContactUCC.getContact(userId, contactId));
    logger.info("Status: 200 {getOne}");
    ThreadContext.clearAll();
    return objectNode;
  }

  /**
   * Initiates a new contact between a user and an enterprise.
   *
   * @param json The JSON containing information about the contact.
   * @param token The authorization token.
   * @return The newly initiated contact as JSON.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode initiate(JsonNode json, @HeaderParam("Authorization") String token) {
    int userId = myJwt.getUserIdFromToken(token);
    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "Post");

    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }

    if (json.hasNonNull("enterpriseId")) {

      int enterpriseId = json.get("enterpriseId").asInt();
      ThreadContext.put("params", "userId:" + userId + "enterpriseId:" + enterpriseId);
      return convertDTOToJson(myContactUCC.initiateContact(userId,
          enterpriseId));
    }

    if (!json.hasNonNull("enterpriseName") || !json.hasNonNull("enterpriseLabel")
        || !json.hasNonNull("enterpriseAddress")
        || !json.hasNonNull("enterprisePhone") && !json.hasNonNull("enterpriseEmail")) {
      throw new WebApplicationException(
          "contactId, enterpriseName, enterpriseLabel, enterpriseAddress and"
              + " enterprisePhone or enterpriseEmail are required",
          Status.BAD_REQUEST);
    }
    ObjectNode objectNode = convertDTOToJson(
        myContactUCC.initiateContact(userId, json.get("enterpriseName").asText(),
            json.get("enterpriseLabel").asText(), json.get("enterpriseAddress").asText(),
            json.get("enterprisePhone").asText(), json.get("enterpriseEmail").asText()));
    logger.info("Status: 200 {initiate}");
    ThreadContext.clearAll();
    return objectNode;
  }

  /**
   * Marks a contact as having a meeting.
   *
   * @param json The JSON containing the contact ID and meeting point.
   * @param token The authorization token.
   * @return The updated contact as JSON.
   */
  @POST
  @Path("/meet")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode meet(JsonNode json, @HeaderParam("Authorization") String token) {
    ThreadContext.put("route", "/contact/meet");
    ThreadContext.put("method", "Post");

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }

    if (!json.hasNonNull("contactId") || !json.hasNonNull("meetingPoint")) {
      throw new WebApplicationException("contactId and meetingPoint required", Status.BAD_REQUEST);
    }
    int contactId = json.get("contactId").asInt();
    String meetingPoint = json.get("meetingPoint").asText();
    ThreadContext.put("params", "contactId:" + contactId + "meetingPoint:" + meetingPoint);
    ObjectNode objectNode = convertDTOToJson(myContactUCC.meetEnterprise(userId, contactId,
        meetingPoint));
    logger.info("Status: 200 {meet}");
    ThreadContext.clearAll();
    return objectNode;
  }

  /**
   * Indicates that a contact has been refused.
   *
   * @param json The JSON containing the contact ID and refusal reason.
   * @param token The authorization token.
   * @return The updated contact as JSON.
   */
  @POST
  @Path("/refuse")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode refuse(JsonNode json, @HeaderParam("Authorization") String token) {
    ThreadContext.put("route", "/contact/refuse");
    ThreadContext.put("method", "Post");

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }

    if (!json.hasNonNull("contactId") || !json.hasNonNull("refusalReason")) {
      throw new WebApplicationException("contactId and refusalReason required", Status.BAD_REQUEST);
    }
    int contactId = json.get("contactId").asInt();
    String refusalReason = json.get("refusalReason").asText();
    ThreadContext.put("params", "contactId:" + contactId + "refusalReason:" + refusalReason);
    ObjectNode objectNode = convertDTOToJson(myContactUCC.indicateAsRefused(userId, contactId,
        refusalReason));
    logger.info("Status: 200 {refuse}");
    ThreadContext.clearAll();
    return objectNode;
  }

  /**
   * Unfollows a contact.
   *
   * @param json The JSON containing the contact ID.
   * @param token The authorization token.
   * @return The result of the unfollow operation as JSON.
   */
  @POST
  @Path("/unfollow")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode unfollow(JsonNode json, @HeaderParam("Authorization") String token) {
    ThreadContext.put("route", "/contact/unfollow");
    ThreadContext.put("method", "Post");

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }
    if (!json.hasNonNull("contactId")) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }
    int contactId = json.get("contactId").asInt();
    ThreadContext.put("params", "contactId:" + contactId);
    ObjectNode objectNode = convertDTOToJson(myContactUCC.unfollow(userId, contactId));
    logger.info("Status: 200 {refuse}");
    ThreadContext.clearAll();
    return objectNode;
  }

  /**
   * Retrieves contacts for a specific user.
   *
   * @param json The JSON containing the user ID.
   * @return The user's contacts as JSON.
   */
  @POST
  @Path("getUserContacts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUsersByIdAsJson(JsonNode json) {
    ThreadContext.put("route", "/contact/getUserContacts");
    ThreadContext.put("method", "Post");
    int userId = decryptToken.getIdFromJsonToken(json);
    ThreadContext.put("params", "userId:" + userId);
    if (userId == 0) {
      throw new WebApplicationException("userId is required", Status.BAD_REQUEST);
    }

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode response = mapper.createObjectNode();
    ArrayNode contactArray = mapper.createArrayNode();

    try {
      List<ContactDTO> contacts = myContactUCC.getContacts(userId);
      List<EnterpriseDTO> enterprises = myEnterpriseUCC.getAllEnterprises();
      for (ContactDTO contactDTO : contacts) {
        contactArray.add(
            convertDTOToJson(contactDTO).put("enterprise_name",
                enterprises.get(contactDTO.getEnterprise() - 1)
                    .getName()));

      }

      // Add table enterprise to response
      response.set("contact", contactArray);
    } catch (Exception e) {
      // Handle error
      response.put("error", e.getMessage());
    }
    logger.info("Status: 200 {getUsersByIdAsJson}");
    ThreadContext.clearAll();
    return response;

  }

  /**
   * Converts a ContactDTO object to JSON format.
   *
   * @param contactDTO The ContactDTO object to convert.
   * @return The ContactDTO object as JSON.
   */
  private ObjectNode convertDTOToJson(ContactDTO contactDTO) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode enterpriseNode = mapper.createObjectNode()
        .put("enterpriseId", contactDTO.getEnterpriseDTO().getEnterpriseId())
        .put("name", contactDTO.getEnterpriseDTO().getName())
        .put("label", contactDTO.getEnterpriseDTO().getLabel())
        .put("adress", contactDTO.getEnterpriseDTO().getAddress())
        .put("contact", contactDTO.getEnterpriseDTO().getPhone())
        .put("email", contactDTO.getEnterpriseDTO().getEmail())
        .put("opinionTeacher", contactDTO.getEnterpriseDTO().getBlacklistedReason());

    ObjectNode contactNode = mapper.createObjectNode();
    contactNode.put("contact_id", contactDTO.getContactId());
    contactNode.put("meeting_point", contactDTO.getMeetingPoint());
    contactNode.put("state", contactDTO.getState());
    contactNode.put("refusal_reason", contactDTO.getRefusalReason());
    contactNode.put("year", contactDTO.getYear());
    contactNode.put("user", contactDTO.getUser());
    contactNode.put("enterpriseId", contactDTO.getEnterprise());
    contactNode.put("enterprise", enterpriseNode);
    return contactNode;
  }
}
