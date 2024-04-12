package be.vinci.pae.resources;

import be.vinci.pae.domain.InternshipDTO;
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
  private JWT myJwt;

  private static final Logger logger = LogManager.getLogger(InternshipResource.class);

  @Inject
  private InternshipUCC myInternshipUCC;

  @Inject
  private RoleId myRoleId;

  @POST
  @Path("/accept")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public InternshipDTO accept(@HeaderParam("Authorization") String token,
      InternshipDTO internship) {
    ThreadContext.put("route", "/int/accept");
    ThreadContext.put("method", "Post");

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }

    internship.setUser(userId);

    if (internship.getContact() == 0) {
      throw new WebApplicationException("contact field is required", Status.BAD_REQUEST);
    }

    if (internship.getSupervisor() != 0) {
      ThreadContext.put("params",
          "userId:" + userId + "responsibleId:" + internship.getSupervisor() + "subject "
              + internship.getSubject());
      internship = myInternshipUCC.acceptInternship(internship);
      logger.info("Status: 200 {accept}");
      return internship;
    }

    String supervisorName = internship.getSupervisorDTO().getName();
    String supervisorSurname = internship.getSupervisorDTO().getSurname();
    String supervisorEmail = internship.getSupervisorDTO().getEmail();
    String supervisorPhone = internship.getSupervisorDTO().getPhone();
    String subject = internship.getSubject();

    if (supervisorName == null || supervisorName.isBlank() || supervisorSurname == null
        || supervisorSurname.isBlank() || supervisorPhone == null || supervisorPhone.isBlank()) {
      throw new WebApplicationException(
          "name, surname, phone fields are required",
          Status.BAD_REQUEST);
    }

    ThreadContext.put("params",
        "userId:" + userId + "supervisorName:" + supervisorName + "supervisorSurname:"
            + supervisorSurname + "supervisorEmail:" + supervisorEmail + "supervisorPhone:"
            + supervisorPhone + "subject:" + subject);

    internship = myInternshipUCC.acceptInternship(internship);

    logger.info("Status: 200 {accept}");
    ThreadContext.clearAll();

    return internship;
  }

  /**
   * Retrieves users internship info.
   *
   * @param token from the user
   * @param id the query id
   * @return an ObjectNode containing users info
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public InternshipDTO getUserInternship(@HeaderParam("Authorization") String token, @DefaultValue("-1") @QueryParam("id") int id) {
    ThreadContext.put("route", "/int");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "id:" + id);

    int userId = myRoleId.chooseId(token, id);

    if (userId == 0) {
      throw new WebApplicationException("user must be authenticated", Status.BAD_REQUEST);
    }

    InternshipDTO internship = myInternshipUCC.getUserInternship(userId);
    logger.info("Status: 200 {getUserInternship}");
    ThreadContext.clearAll();
    return internship;
  }
}
