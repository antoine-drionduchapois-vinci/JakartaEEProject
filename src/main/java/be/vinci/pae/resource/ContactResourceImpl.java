package be.vinci.pae.resource;

import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.ucc.ContactUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/contact")
public class ContactResourceImpl implements ContactResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  @Inject
  private ContactUCC myContactUCC;


  /**
   * Retrieves users info.
   *
   * @return an ObjectNode containing users info
   */
  @Override
  @POST
  @Path("getContacts")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUsersContacts(JsonNode json) {

    try {
      //Get token from JSON
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

      ObjectMapper mapper = new ObjectMapper();
      ObjectNode response = mapper.createObjectNode();
      ArrayNode contactArray = mapper.createArrayNode();

      try {
        // Retrieve all contacts from your DAO
        List<ContactDTO> contacts = myContactUCC.getAllUsersContact(userId);

        // Iterate through each contact and add them to the response
        for (ContactDTO contactDTO : contacts) {
          ObjectNode contactNode = mapper.createObjectNode();
          contactNode.put("contact_id", contactDTO.getContactId());
          contactNode.put("description", contactDTO.getDescription());
          contactNode.put("state", contactDTO.getState());
          contactNode.put("refusal_reason", contactDTO.getReasonRefusal());
          contactNode.put("year", contactDTO.getYear());
          contactNode.put("user_id", contactDTO.getUser());
          contactNode.put("enterprise_id", contactDTO.getEntreprise());
          contactArray.add(contactNode);
        }
        response.set("enterprises", contactArray);
        return response;
      } catch (Exception e) {
        // Handle exceptions
        e.printStackTrace();
        // Add your exception handling logic here
        response.put("error", e.getMessage());
        return response;
      }
    } catch (Exception e) {
      // Handle exceptions
      e.printStackTrace();
      // Add your exception handling logic here
      ObjectMapper mapper = new ObjectMapper();
      ObjectNode errorResponse = mapper.createObjectNode();
      errorResponse.put("error", e.getMessage());
      return errorResponse;
    }
  }


}
