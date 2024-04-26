package be.vinci.pae.resources;

import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.resources.filters.Authorize;
import be.vinci.pae.resources.filters.RoleId;
import be.vinci.pae.ucc.InternshipUCC;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Implementation of the internship interface.
 */
@Singleton
@Path("/int")
public class InternshipResource {

  @Inject
  private Jwt myJwt;

  private static final Logger logger = LogManager.getLogger(InternshipResource.class);

  @Inject
  private InternshipUCC myInternshipUCC;

  @Inject
  private RoleId myRoleId;

  /**
   * Accept an internship.
   *
   * @param token      The authorization token.
   * @param internship The internship request to be accepted.
   * @return The accepted internship.
   */
  @POST
  @Path("/accept")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(UserDTO.Role.STUDENT)
  public InternshipDTO accept(@HeaderParam("Authorization") String token,
      InternshipDTO internship) {
    ThreadContext.put("route", "/int/accept");
    ThreadContext.put("method", "POST");
    ThreadContext.put("params", internship.toString());

    int userId = myJwt.getUserIdFromToken(token);

    internship.setUser(userId);

    if (internship.getContact() == 0) {
      throw new WebApplicationException("contact field is required", Status.BAD_REQUEST);
    }

    if (internship.getSupervisor() != 0) {
      internship = myInternshipUCC.acceptInternship(internship);
      logger.info("Status: 200 {accept}");
      ThreadContext.clearAll();
      return internship;
    }

    String supervisorName = internship.getSupervisorDTO().getName();
    String supervisorSurname = internship.getSupervisorDTO().getSurname();
    String supervisorPhone = internship.getSupervisorDTO().getPhone();

    if (supervisorName == null || supervisorName.isBlank() || supervisorSurname == null
        || supervisorSurname.isBlank() || supervisorPhone == null || supervisorPhone.isBlank()) {
      throw new WebApplicationException(
          "name, surname, phone fields are required",
          Status.BAD_REQUEST);
    }

    internship = myInternshipUCC.acceptInternship(internship);

    logger.info("Status: 200 {accept}");
    ThreadContext.clearAll();

    return internship;
  }

  /**
   * Retrieves users internship info.
   *
   * @param token from the user
   * @param id    the query id
   * @return an ObjectNode containing users info
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize()
  public InternshipDTO getUserInternship(@HeaderParam("Authorization") String token,
      @DefaultValue("-1") @QueryParam("id") int id) {
    ThreadContext.put("route", "/int");
    ThreadContext.put("method", "GET");
    ThreadContext.put("params", "id: " + id);

    int userId = myRoleId.chooseId(token, id);

    InternshipDTO internship = myInternshipUCC.getUserInternship(userId);
    logger.info("Status: 200 {getUserInternship}");
    ThreadContext.clearAll();
    return internship;
  }

  /**
   * Modifies the subject of an internship.
   *
   * @param token      The authorization token for the user.
   * @param internship The InternshipDTO object containing the updated subject.
   * @return The updated InternshipDTO object with the modified subject.
   * @throws WebApplicationException If the user is not authenticated or if the
   *                                 subject field is missing or blank.
   */
  @POST
  @Path("/subject")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Authorize(UserDTO.Role.STUDENT)
  public InternshipDTO modifySubject(@HeaderParam("Authorization") String token,
      InternshipDTO internship) {
    ThreadContext.put("route", "/int");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "subject:" + internship.toString());

    int userId = myJwt.getUserIdFromToken(token);

    String subject = internship.getSubject();
    if (subject == null || subject.isBlank()) {
      throw new WebApplicationException("subject field is required", Status.BAD_REQUEST);
    }

    ThreadContext.put("params", "userId:" + userId);

    internship = myInternshipUCC.modifySubject(userId, subject);
    logger.info("Status: 200 {modifySubject}");
    ThreadContext.clearAll();
    return internship;
  }
}
