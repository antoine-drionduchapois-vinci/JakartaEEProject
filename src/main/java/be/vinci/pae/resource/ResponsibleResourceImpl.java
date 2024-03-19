package be.vinci.pae.resource;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.ResponsibleDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.ResponsibleUCC;
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
 * Implementation of the Responsible interface.
 */
@Singleton
@Path("/res")
public class ResponsibleResourceImpl implements ResponsibleResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));


  @Inject
  private EnterpriseUCC entrepriseUCC;
  @Inject
  private ResponsibleUCC responsibleUCC;


  /**
   * Retrieves users info.
   *
   * @return an ObjectNode containing users info
   */
  @Override
  @POST
  @Path("responsable")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getResponsableByUserId(JsonNode json) {
    try {
      //Get token from JSON
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
      EnterpriseDTO enterpriseDTO = entrepriseUCC.getEnterprisesByUserId(userId);
      ResponsibleDTO responsibleDTO = responsibleUCC.getResponsibleByEnterpriseId(
          enterpriseDTO.getEntrepriseId());

      //transform responsibleDTO to JSOn
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode responsibleNode = mapper.createObjectNode();
      responsibleNode.put("responsible_id", responsibleDTO.getResponsibleId());
      responsibleNode.put("name", responsibleDTO.getName());
      responsibleNode.put("surname", responsibleDTO.getSurname());
      responsibleNode.put("phone", responsibleDTO.getPhone());
      responsibleNode.put("email", responsibleDTO.getEmail());
      responsibleNode.put("enterprise_id", responsibleDTO.getEnterprise());

      return responsibleNode;

    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
    }
    return null;
  }


}
