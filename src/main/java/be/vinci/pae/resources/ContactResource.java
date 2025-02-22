package be.vinci.pae.resources;

import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.resources.filters.Authorize;
import be.vinci.pae.resources.filters.RoleId;
import be.vinci.pae.ucc.ContactUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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

  private static final Logger logger = LogManager.getLogger(ContactResource.class);

  @Inject
  private Jwt myJwt;
  @Inject
  private ContactUCC myContactUCC;

  @Inject
  private RoleId myRoleId;

  /**
   * Retrieves a contact by its ID.
   *
   * @param token     The authorization token.
   * @param contactId The ID of the contact to retrieve.
   * @return The contact as JSON.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize()
  public ContactDTO getOne(@HeaderParam("Authorization") String token,
      @DefaultValue("-1") @QueryParam("contactId") int contactId) {
    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "GET");
    ThreadContext.put("params", "contactId: " + contactId);

    int userId = myJwt.getUserIdFromToken(token);

    if (contactId == -1) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }

    ContactDTO contact = myContactUCC.getContact(userId, contactId);
    logger.info("Status: 200 {getOne}");
    ThreadContext.clearAll();
    return contact;
  }

  /**
   * Initiates a new contact between a user and an enterprise.
   *
   * @param token   The authorization token.
   * @param contact The contact information to be initiated.
   * @return The newly initiated contact.
   */
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(UserDTO.Role.STUDENT)
  public ContactDTO initiate(@HeaderParam("Authorization") String token, ContactDTO contact) {
    int userId = myJwt.getUserIdFromToken(token);

    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "POST");
    ThreadContext.put("params", contact.toString());

    if (contact.getEnterprise() != 0) {
      int enterpriseId = contact.getEnterprise();
      ThreadContext.put("params", "userId:" + userId + "enterpriseId:" + enterpriseId);

      contact = myContactUCC.initiateContact(userId, enterpriseId);
      logger.info("Status: 200 {initiate}");
      ThreadContext.clearAll();
      return contact;
    }

    String enterpriseName = contact.getEnterpriseDTO().getName();
    String enterpriseLabel = contact.getEnterpriseDTO().getLabel();
    String enterpriseAddress = contact.getEnterpriseDTO().getAddress();
    String enterprisePhone = contact.getEnterpriseDTO().getPhone();
    String enterpriseEmail = contact.getEnterpriseDTO().getEmail();

    ThreadContext.put("params",
        "userId:" + userId + "enterpriseName:" + enterpriseName + "enterpriseLabel:"
            + enterpriseLabel + "enterpriseAddress:" + enterpriseAddress + "enterprisePhone:"
            + enterprisePhone + "enterpriseEmail:" + enterpriseEmail);

    if (enterpriseName == null || enterpriseAddress == null
        || enterprisePhone == null && enterpriseEmail == null) {
      throw new WebApplicationException(
          "contactId, enterpriseName, enterpriseLabel, enterpriseAddress and"
              + " enterprisePhone or enterpriseEmail are required", Status.BAD_REQUEST);
    }

    contact = myContactUCC.initiateContact(userId, enterpriseName,
        enterpriseLabel, enterpriseAddress, enterprisePhone, enterpriseEmail);

    logger.info("Status: 200 {initiate}");
    ThreadContext.clearAll();

    return contact;
  }

  /**
   * Marks a contact as having a meeting.
   *
   * @param token   The authorization token.
   * @param contact The contact information indicating the meeting.
   * @return The updated contact after the meeting.
   */
  @POST
  @Path("/meet")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(UserDTO.Role.STUDENT)
  public ContactDTO meet(@HeaderParam("Authorization") String token, ContactDTO contact) {
    ThreadContext.put("route", "/contact/meet");
    ThreadContext.put("method", "POST");
    ThreadContext.put("params", contact.toString());

    int userId = myJwt.getUserIdFromToken(token);

    int contactId = contact.getContactId();
    String meetingPoint = contact.getMeetingPoint();
    if (contactId == 0 || meetingPoint == null) {
      throw new WebApplicationException("contactId and meetingPoint required", Status.BAD_REQUEST);
    }
    ThreadContext.put("params", "contactId:" + contactId + "meetingPoint:" + meetingPoint);
    contact = myContactUCC.meetEnterprise(userId, contactId, meetingPoint);
    logger.info("Status: 200 {meet}");
    ThreadContext.clearAll();
    return contact;
  }

  /**
   * Indicates that a contact has been refused.
   *
   * @param token   The authorization token.
   * @param contact The contact information to be refused.
   * @return The updated contact after indicating refusal.
   */
  @POST
  @Path("/refuse")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(UserDTO.Role.STUDENT)
  public ContactDTO refuse(@HeaderParam("Authorization") String token, ContactDTO contact) {
    ThreadContext.put("route", "/contact/refuse");
    ThreadContext.put("method", "POST");
    ThreadContext.put("params", contact.toString());

    int userId = myJwt.getUserIdFromToken(token);

    int contactId = contact.getContactId();
    String refusalReason = contact.getRefusalReason();

    if (contactId == 0 || refusalReason == null) {
      throw new WebApplicationException("contactId and refusalReason required", Status.BAD_REQUEST);
    }
    ThreadContext.put("params", "contactId:" + contactId + "refusalReason:" + refusalReason);
    contact = myContactUCC.indicateAsRefused(userId, contactId,
        refusalReason);
    logger.info("Status: 200 {refuse}");
    ThreadContext.clearAll();
    return contact;
  }

  /**
   * Unfollows a contact.
   *
   * @param token   The authorization token.
   * @param contact The contact information to be unfollowed.
   * @return The result of the unfollow operation.
   */
  @POST
  @Path("/unfollow")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(UserDTO.Role.STUDENT)
  public ContactDTO unfollow(@HeaderParam("Authorization") String token, ContactDTO contact) {
    ThreadContext.put("route", "/contact/unfollow");
    ThreadContext.put("method", "POST");
    ThreadContext.put("params", contact.toString());

    int userId = myJwt.getUserIdFromToken(token);

    int contactId = contact.getContactId();

    if (contactId == 0) {
      throw new WebApplicationException("contactId required", Status.BAD_REQUEST);
    }
    ThreadContext.put("params", "contactId:" + contactId);
    contact = myContactUCC.unfollow(userId, contactId);
    logger.info("Status: 200 {unfollow}");
    ThreadContext.clearAll();
    return contact;
  }

  /**
   * Retrieves contacts for a specific user.
   *
   * @param token The JSON containing the user ID.
   * @param id    the query id
   * @return The user's contacts as JSON.
   */
  @GET
  @Path("getUserContacts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize()
  public List<ContactDTO> getUsersByIdAsJson(@HeaderParam("Authorization") String token,
      @DefaultValue("-1") @QueryParam("id") int id) {
    ThreadContext.put("route", "/contact/getUserContacts");
    ThreadContext.put("method", "GET");
    ThreadContext.put("params", "id: " + id);

    int userId = myRoleId.chooseId(token, id);

    if (userId == 0) {
      throw new WebApplicationException("userId is required", Status.BAD_REQUEST);
    }

    // Creating custom ObjectNode with contacts and enterprise
    List<ContactDTO> contacts = myContactUCC.getContacts(userId);

    logger.info("Status: 200 {getUsersByIdAsJson}");
    ThreadContext.clearAll();
    return contacts;

  }

  /**
   * Retrieves contacts for a specific user.
   *
   * @param enterpriseId The enterprise ID.
   * @return The user's contacts as JSON.
   */
  @GET
  @Path("getEnterpriseContacts/{entrepriseId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize()
  public List<ContactDTO> getEnterpriseContact(
      @PathParam("entrepriseId") int enterpriseId) {
    ThreadContext.put("route", "/contact/getEnterpriseContacts");
    ThreadContext.put("method", "GET");
    ThreadContext.put("params", "entrepriseId: " + enterpriseId);

    List<ContactDTO> contacts = myContactUCC.getEnterpriseContacts(enterpriseId);

    logger.info("Status: 200 {getEnterpriseContact}");
    ThreadContext.clearAll();

    return contacts;
  }
}
