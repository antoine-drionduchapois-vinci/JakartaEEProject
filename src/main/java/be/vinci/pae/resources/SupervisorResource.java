package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.SupervisorUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
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

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private static final Logger logger = LogManager.getLogger(EnterpriseResource.class);

  @Inject
  private EnterpriseUCC entrepriseUCC;
  @Inject
  private SupervisorUCC supervisorUCC;

  @Inject
  private JWT myJwt;

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
      SupervisorDTO supervisorDTO = supervisorUCC.getResponsibleByEnterpriseId(
          enterpriseDTO.getEnterpriseId());

      // transform responsibleDTO to JSOn
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode responsibleNode = mapper.createObjectNode();
      responsibleNode.put("responsible_id", supervisorDTO.getSupervisorId());
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

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<SupervisorDTO> getAll() {
    ThreadContext.put("route", "/ent");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "NoParam");

    List<SupervisorDTO> supervisors = supervisorUCC.getAll();

    logger.info("Status: 200 {getAllSupervisors}");
    ThreadContext.clearAll();

    return supervisors;
  }
}
