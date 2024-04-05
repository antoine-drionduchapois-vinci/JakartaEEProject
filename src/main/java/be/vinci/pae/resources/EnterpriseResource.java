package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
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
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Implementation of the Enterprise interface.
 */
@Singleton
@Path("/ent")
public class EnterpriseResource {

  private static final Logger logger = LogManager.getLogger(EnterpriseResource.class);

  @Inject
  private EnterpriseUCC myEnterpriseUCC;

  @Inject
  private JWT myJwt;

  /**
   * Retrieves all enterprise.
   *
   * @return an ObjectNode containing all enterprises
   */

  @GET
  @Path("enterprises")
  @Produces(MediaType.APPLICATION_JSON)
  public List<EnterpriseDTO> getAllEnterprises() {
    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "NoParam");

    List<EnterpriseDTO> enterprises = myEnterpriseUCC.getAllEnterprises();

    logger.info("Status: 200 {getAllEnterprises}");
    ThreadContext.clearAll();
    return enterprises;
  }

  /**
   * Retrieves the enterprise associated with the user's internship by user ID.
   *
   * @param token The JSON object containing the JWT token.
   * @return An ObjectNode representing the enterprise details.
   */
  @GET
  @Path("enterprises")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getEnterprisesByUserId(@HeaderParam("Authorization") String token) {
    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "Get");
    // Get token from JSON
    int userId = myJwt.getUserIdFromToken(token);
    try {
      // get entrprise that corresponds to user intership
      EnterpriseDTO enterpriseDTO = myEnterpriseUCC.getEnterprisesByUserId(userId);
      ObjectNode objectNode = convertDTOToJson(enterpriseDTO);
      logger.info("Status: 200 {getEnterprisesByUserId}");
      ThreadContext.clearAll();
      return objectNode;
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
    }

    // get entrprise that corresponds to user intership
    EnterpriseDTO enterpriseDTO = myEnterpriseUCC.getEnterprisesByUserId(userId);
    ObjectNode objectNode = convertDTOToJson(enterpriseDTO);
    logger.info("Status: 200 {getEnterprisesByUserId}");
    ThreadContext.clearAll();
    return objectNode;
  }

  private ObjectNode convertDTOToJson(EnterpriseDTO enterpriseDTO) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode enterpriseNode = mapper.createObjectNode();
    enterpriseNode.put("entreprise_id", enterpriseDTO.getEnterpriseId());
    enterpriseNode.put("nom", enterpriseDTO.getName());
    enterpriseNode.put("appellation", enterpriseDTO.getLabel());
    enterpriseNode.put("adresse", enterpriseDTO.getAddress());
    enterpriseNode.put("telephone", enterpriseDTO.getPhone());
    enterpriseNode.put("email", enterpriseDTO.getEmail());
    enterpriseNode.put("is_blacklist", enterpriseDTO.isBlacklisted());
    enterpriseNode.put("avis_professeur", enterpriseDTO.getBlacklistedReason());
    return enterpriseNode;
  }

}
