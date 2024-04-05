package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
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
import jakarta.ws.rs.POST;
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

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private static final Logger logger = LogManager.getLogger(EnterpriseResource.class);
  @Inject
  private EnterpriseUCC myEnterpriseUCC;

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
   * @param json The JSON object containing the JWT token.
   * @return An ObjectNode representing the enterprise details.
   */
  @POST
  @Path("enterprises")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getEnterprisesByUserId(JsonNode json) {
    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "Get");
    // Get token from JSON

    String jsonToken = json.get("token").asText();
    // Decode Token
    DecodedJWT jwt = JWT.require(jwtAlgorithm)
        .withIssuer("auth0")
        .build() // create the JWTVerifier instance
        .verify(jsonToken); // verify the token
    // Het userId from decodedToken
    int userId = jwt.getClaim("user").asInt();
    ThreadContext.put("params", "userId:" + userId);
    // Assuming the token includes a "user" claim holding the user ID
    if (userId == -1) {
      throw new JWTVerificationException("User ID claim is missing");
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
