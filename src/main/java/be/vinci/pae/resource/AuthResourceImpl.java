package be.vinci.pae.resource;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.services.AuthUCC;
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
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;


/**
 * Implementation of the UserDataService interface.
 */
@Singleton
@Path("/auths")
public class AuthResourceImpl implements AuthResource {


  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();
  @Inject
  private AuthUCC myAuthUCC;

  @Override
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("login or password required", Response.Status.BAD_REQUEST);
    }
    String email = json.get("email").asText();
    String password = json.get("password").asText();
    ObjectNode publicUser = myAuthUCC.login(email, password);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    return publicUser;
  }








  @Override
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("All fileds are required",
          Response.status(Response.Status.BAD_REQUEST)
              .entity("email or password required").type("text/plain").build());
    }
    String name = json.get("name").asText();
    String firstname = json.get("firstname").asText();
    String email = json.get("email").asText();
    String telephone = json.get("telephone").asText();
    String password = json.get("password").asText();
    String role = json.get("role").asText();

    User user = myAuthUCC.createUserAndReturn(name, firstname, email, telephone, password, role);

    // Try to register
    ObjectNode publicUser = myAuthUCC.register(user);
    if (publicUser == null) {
      throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
          .entity("this resource already exists").type(MediaType.TEXT_PLAIN)
          .build());
    }
    return publicUser;
  }




}