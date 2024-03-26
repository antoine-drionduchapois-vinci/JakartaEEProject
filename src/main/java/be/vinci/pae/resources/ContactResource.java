package be.vinci.pae.resources;

import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.Enterprise;
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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

/**
 * Resource class for managing contacts between users and enterprises.
 */
@Singleton
@Path("/contact")
public class ContactResource {

  private JWTDecryptToken decryptToken = new JWTDecryptToken();
  @Inject
  private ContactUCC myContactUCC;

  @Inject
  private EnterpriseUCC myEnterpriseUCC;

  /**
   * Retrieves a contact by its ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return The contact as JSON.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getOne(@DefaultValue("-1") @QueryParam("contactId") int contactId) {
    if (contactId == -1) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }

    return convertDTOToJson(myContactUCC.getContact(contactId));
  }

  /**
   * Initiates a new contact between a user and an enterprise.
   *
   * @param json The JSON containing information about the contact.
   * @return The newly initiated contact as JSON.
   */

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode initiate(JsonNode json) {
    if (!json.hasNonNull("userId")) {
      throw new WebApplicationException("Bad Request", Status.BAD_REQUEST);
    }

    if (json.hasNonNull("enterpriseId")) {
      return convertDTOToJson(myContactUCC.initiateContact(json.get("userId").asInt(),
          json.get("enterpriseId").asInt()));
    }

    if (!json.hasNonNull("enterpriseName") || !json.hasNonNull("enterpriseLabel")
        || !json.hasNonNull("enterpriseAddress")
        || !json.hasNonNull("enterprisePhone") && !json.hasNonNull("enterpriseEmail")) {
      throw new WebApplicationException(
          "contactId, enterpriseName, enterpriseLabel, enterpriseAddress and enterprisePhone or enterpriseEmail are required",
          Status.BAD_REQUEST);
    }

    return convertDTOToJson(myContactUCC.initiateContact(json.get("userId").asInt(),
        json.get("enterpriseName").asText(), json.get("enterpriseLabel").asText(),
        json.get("enterpriseAddress").asText(), json.get("enterprisePhone").asText(),
        json.get("enterpriseEmail").asText()));
  }

  /**
   * Marks a contact as having a meeting.
   *
   * @param json The JSON containing the contact ID and meeting point.
   * @return The updated contact as JSON.
   */
  @POST
  @Path("/meet")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode meet(JsonNode json) throws WebApplicationException {
    if (!json.hasNonNull("contactId") || !json.hasNonNull("meetingPoint")) {
      throw new WebApplicationException("contactId and meetingPoint required", Status.BAD_REQUEST);
    }

    return convertDTOToJson(myContactUCC.meetEnterprise(json.get("contactId").asInt(),
        json.get("meetingPoint").asText()));
  }

  /**
   * Indicates that a contact has been refused.
   *
   * @param json The JSON containing the contact ID and refusal reason.
   * @return The updated contact as JSON.
   */
  @POST
  @Path("/refuse")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode refuse(JsonNode json) {
    if (!json.hasNonNull("contactId") || !json.hasNonNull("refusalReason")) {
      throw new WebApplicationException("contactId and refusalReason required", Status.BAD_REQUEST);
    }

    return convertDTOToJson(myContactUCC.indicateAsRefused(json.get("contactId").asInt(),
        json.get("refusalReason").asText()));
  }

  /**
   * Unfollows a contact.
   *
   * @param json The JSON containing the contact ID.
   * @return The result of the unfollow operation as JSON.
   */
  @POST
  @Path("/unfollow")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode unfollow(JsonNode json) {
    if (!json.hasNonNull("contactId")) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }

    return convertDTOToJson(myContactUCC.unfollow(json.get("contactId").asInt()));
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
    System.out.println("getUserContact");
    int userId = decryptToken.getIdFromJsonToken(json);

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode response = mapper.createObjectNode();
    ArrayNode contactArray = mapper.createArrayNode();

    try {
      List<ContactDTO> contacts = myContactUCC.getContacts(userId);
      List<Enterprise> enterprises = myEnterpriseUCC.getAllEnterprises();
      for (ContactDTO contactDTO : contacts) {
        contactArray.add(
            convertDTOToJson(contactDTO).put("enterprise_name",
                enterprises.get(contactDTO.getEnterprise() - 1)
                    .getName()));

      }

      // Ajouter le tableau d'entreprises à la réponse
      response.set("contact", contactArray);
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      response.put("error", e.getMessage());
    }
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
