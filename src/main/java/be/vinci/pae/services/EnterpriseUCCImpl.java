package be.vinci.pae.services;

import be.vinci.pae.api.EnterpriseDAO;
import be.vinci.pae.api.EnterpriseDAOImpl;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * Implementation of the Enterprise interface.
 */
@Singleton
@Path("/ent")
public class EnterpriseUCCImpl implements EnterpriseUCC {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private EnterpriseDAO enterpriseDAO = new EnterpriseDAOImpl();

  @Override
  @GET
  @Path("enterprises")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllEnterprises() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode response = mapper.createObjectNode();
    ArrayNode enterprisesArray = mapper.createArrayNode();

    try {
      // Récupérer toutes les entreprises depuis votre DAO
      List<Enterprise> enterprises = enterpriseDAO.getAllEnterprises();

      // Parcourir chaque entreprise et les ajouter à la réponse
      for (Enterprise enterprise : enterprises) {
        ObjectNode enterpriseNode = mapper.createObjectNode();
        enterpriseNode.put("entreprise_id", enterprise.getEntrepriseId());
        enterpriseNode.put("nom", enterprise.getNom());
        enterpriseNode.put("appellation", enterprise.getAppellation());
        enterpriseNode.put("adresse", enterprise.getAdresse());
        enterpriseNode.put("telephone", enterprise.getTelephone());
        enterpriseNode.put("is_blacklist", enterprise.isBlacklist());
        enterpriseNode.put("avis_professeur", enterprise.getAvisProfesseur());
        enterprisesArray.add(enterpriseNode);
      }

      // Ajouter le tableau d'entreprises à la réponse
      response.set("enterprises", enterprisesArray);
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      response.put("error", e.getMessage());
    }

    return response;
  }

  /**
   * Retrieves users info.
   *
   * @return an ObjectNode containing users info
   */
  @Override
  @POST
  @Path("enterprises")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getEnterprisesByUserId(JsonNode json) {
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
      Enterprise enterprise = enterpriseDAO.getEnterpriseById(userId);

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode enterpriseNode = mapper.createObjectNode();
      enterpriseNode.put("entreprise_id", enterprise.getEntrepriseId());
      enterpriseNode.put("nom", enterprise.getNom());
      enterpriseNode.put("appellation", enterprise.getAppellation());
      enterpriseNode.put("adresse", enterprise.getAdresse());
      enterpriseNode.put("telephone", enterprise.getTelephone());
      enterpriseNode.put("is_blacklist", enterprise.isBlacklist());
      enterpriseNode.put("avis_professeur", enterprise.getAvisProfesseur());

      return enterpriseNode;
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      e.printStackTrace();
    }
    return null;
  }
}
