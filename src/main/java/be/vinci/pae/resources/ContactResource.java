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
 * Resource class for handling contact-related endpoints.
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
   * @return JSON representation of the retrieved contact.
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
   * Adds a new contact.
   *
   * @param json JSON containing data for creating a new contact.
   * @return JSON representation of the added contact.
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
   * Handles meeting with an enterprise.
   *
   * @param json JSON containing contact ID and meeting point.
   * @return JSON representation of the updated contact after the meeting.
   * @throws WebApplicationException if the required parameters are missing.
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
   * Indicates refusal of contact.
   *
   * @param json JSON containing contact ID and reason for refusal.
   * @return JSON representation of the updated contact after refusal.
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
   * @param json JSON containing contact ID.
   * @return JSON representation of the updated contact after unfollowing.
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
   * Retrieves user information by user ID and returns it as JSON.
   *
   * @param json The JSON object containing the JWT token.
   * @return An ObjectNode representing the user's information.
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
