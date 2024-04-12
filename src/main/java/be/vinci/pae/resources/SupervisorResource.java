package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.Supervisor;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.SupervisorUCC;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
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
  public ObjectNode getResponsableByUserId(@HeaderParam("Authorization") String token) {
    ThreadContext.put("route", "/res/responsable");
    ThreadContext.put("method", "GET");

    int userId = myJwt.getUserIdFromToken(token);
    if (userId == 0) {
      throw new JWTVerificationException("User ID claim is missing");
    }

    try {
      // get entrprise that corresponds to user intership
      EnterpriseDTO enterpriseDTO = entrepriseUCC.getEnterprisesByUserId(userId);
      Supervisor supervisorDTO = supervisorUCC.getResponsibleByEnterpriseId(
          enterpriseDTO.getEnterpriseId());

      // transform responsibleDTO to JSOn
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode responsibleNode = mapper.createObjectNode();
      responsibleNode.put("responsible_id", supervisorDTO.getResponsibleId());
      responsibleNode.put("name", supervisorDTO.getName());
      responsibleNode.put("surname", supervisorDTO.getSurname());
      responsibleNode.put("phone", supervisorDTO.getPhone());
      responsibleNode.put("email", supervisorDTO.getEmail());
      responsibleNode.put("enterprise_id", supervisorDTO.getEnterprise());
      logger.info("Status: 200 {getResponsableByUserId}");
      ThreadContext.clearAll();
      return responsibleNode;

    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
      return null;
    }

  }

}
