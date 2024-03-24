package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.InternshipUCC;
import be.vinci.pae.ucc.SupervisorUCC;
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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Implementation of the internship interface.
 */
@Singleton
@Path("/int")
public class InternshipResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  @Inject
  private InternshipUCC myInternshipUCC;

  @Inject
  private EnterpriseUCC myEnterpriseUCC;

  @Inject
  private SupervisorUCC myResponsbileUCC;


  /**
   * Retrieves users internship info.
   *
   * @return an ObjectNode containing users info
   */

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUserInternship(JsonNode json) {
    try {
      //Get token from JSON
      System.out.println("GetUsersInternship");
      System.out.println("here");
      String jsonToken = json.get("token").asText();
      //Decode Token
      DecodedJWT jwt = JWT.require(jwtAlgorithm)
          .withIssuer("auth0")
          .build() // create the JWTVerifier instance
          .verify(jsonToken); // verify the token
      //Het userId from decodedToken
      int userId = jwt.getClaim("user").asInt();
      // Assuming the token includes a "user" claim holding the user ID
      if (userId == -1) {
        throw new JWTVerificationException("User ID claim is missing");
      }
      System.out.println("user id : " + userId);
      //get entrprise that corresponds to user intership

      InternshipDTO internshipDTO = myInternshipUCC.getUserInternship(userId);
      EnterpriseDTO enterpriseDTO = myEnterpriseUCC.getEnterprisesByUserId(userId);
      SupervisorDTO responsibleDTO = myResponsbileUCC.getResponsibleByEnterpriseId(
          enterpriseDTO.getEnterpriseId());

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode internshipNode = mapper.createObjectNode();
      internshipNode.put("enterprise", enterpriseDTO.getName());
      internshipNode.put("year", internshipDTO.getYear());
      internshipNode.put("responsbile", responsibleDTO.getName());
      internshipNode.put("phone", responsibleDTO.getPhone());
      internshipNode.put("contact", internshipDTO.getContact());

      return internshipNode;
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
    }
    return null;
  }


}
