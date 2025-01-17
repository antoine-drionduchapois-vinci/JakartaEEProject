package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.SupervisorUCC;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Implementation of the Responsible interface.
 */
@Singleton
@Path("/res")
public class SupervisorResource {

  private static final Logger logger = LogManager.getLogger(EnterpriseResource.class);

  @Inject
  private EnterpriseUCC entrepriseUCC;
  @Inject
  private SupervisorUCC supervisorUCC;

  @Inject
  private Jwt myJwt;

  /**
   * Retrieves the supervisor responsible for the user's internship enterprise by user ID.
   *
   * @param token the user token
   * @return An ObjectNode representing the supervisor details.
   */
  @GET
  @Path("responsable")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public SupervisorDTO getResponsableByUserId(@HeaderParam("Authorization") String token) {
    ThreadContext.put("route", "/res/responsable");
    ThreadContext.put("method", "GET");

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new JWTVerificationException("User ID claim is missing");
    }

    try {
      // get entrprise that corresponds to user intership
      EnterpriseDTO enterpriseDTO = entrepriseUCC.getEnterprisesByUserId(userId);
      SupervisorDTO supervisorDTO = supervisorUCC.getResponsibleByEnterpriseId(
          enterpriseDTO.getEnterpriseId());

      return supervisorDTO;

    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Retrieves the supervisor for a specific enterprise.
   *
   * @param enterpriseId The ID of the enterprise.
   * @return The supervisor associated with the enterprise.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public SupervisorDTO getOne(@QueryParam("enterprise") int enterpriseId) {
    ThreadContext.put("route", "/ent");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "enterprise: " + enterpriseId);

    SupervisorDTO supervisor = supervisorUCC.getResponsibleByEnterpriseId(enterpriseId);

    logger.info("Status: 200 {getOne}");
    ThreadContext.clearAll();

    return supervisor;

  }

  /**
   * Retrieves all supervisors.
   *
   * @return A list containing all supervisors.
   */
  @GET
  @Path("/all")
  @Produces(MediaType.APPLICATION_JSON)
  public List<SupervisorDTO> getAll() {
    ThreadContext.put("route", "/ent/all");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "NoParam");

    List<SupervisorDTO> supervisors = supervisorUCC.getAll();

    logger.info("Status: 200 {getAllSupervisors}");
    ThreadContext.clearAll();

    return supervisors;
  }
}
