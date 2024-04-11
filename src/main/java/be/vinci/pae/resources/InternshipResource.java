package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.resources.filters.RoleId;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.InternshipUCC;
import be.vinci.pae.ucc.SupervisorUCC;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
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


  private static final Logger logger = LogManager.getLogger(InternshipResource.class);

  @Inject
  private InternshipUCC myInternshipUCC;

  @Inject
  private EnterpriseUCC myEnterpriseUCC;

  @Inject
  private SupervisorUCC myResponsbileUCC;

  @Inject
  private JWT myJwt;

  @Inject
  private RoleId myRoleId;


  /**
   * Retrieves users internship info.
   *
   * @param token from the user
   * @param id the query id
   * @return an ObjectNode containing users info
   */
  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUserInternship(@HeaderParam("Authorization") String token,
      @DefaultValue("-1") @QueryParam("id") int id) {
    ThreadContext.put("route", "/int");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "id:" + id);

    int userId = myRoleId.chooseId(token, id);

    if (userId == 0) {
      throw new WebApplicationException("userId is required", Status.BAD_REQUEST);
    }

    //get entrprise that corresponds to user intership

    InternshipDTO internshipDTO = myInternshipUCC.getUserInternship(userId);
    EnterpriseDTO enterpriseDTO = myEnterpriseUCC.getEnterprisesByUserId(userId);
    if (enterpriseDTO == null) {
      return null;
    }
    SupervisorDTO responsibleDTO = myResponsbileUCC.getResponsibleByEnterpriseId(
        enterpriseDTO.getEnterpriseId());

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode internshipNode = mapper.createObjectNode();
    internshipNode.put("enterprise", enterpriseDTO.getName());
    internshipNode.put("year", internshipDTO.getYear());
    internshipNode.put("responsbile", responsibleDTO.getName());
    internshipNode.put("phone", responsibleDTO.getPhone());
    internshipNode.put("contact", internshipDTO.getContact());
    logger.info("Status: 200 {getUserInternship}");
    ThreadContext.clearAll();
    return internshipNode;
  }
}
