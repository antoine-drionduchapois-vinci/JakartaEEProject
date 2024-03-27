package be.vinci.pae.resources;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.domain.UserDTO.Role;
import be.vinci.pae.ucc.AuthUCC;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * Resource class for handling authentication-related endpoints.
 */
@Singleton
@Path("/auths")
public class AuthResource {

  private final Algorithm jwtAlgorithm = Algorithm.HMAC256(Config.getProperty("JWTSecret"));
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private AuthUCC myAuthUCC;

  @Inject
  private JWT myJwt;

  /**
   * Endpoint for user login.
   *
   * @param json JSON containing user login credentials (email, password).
   * @return JSON representing the authenticated user.
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode login(JsonNode json) {
    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("Login or password required", Response.Status.BAD_REQUEST);
    }

    String email = json.get("email").asText();
    String password = json.get("password").asText();
    UserDTO userTemp = myDomainFactory.getUser();
    userTemp.setPassword(password);
    userTemp.setEmail(email);
    UserDTO userDTO = myAuthUCC.login(userTemp);

    ObjectNode publicUser = myJwt.createToken(userDTO);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    return publicUser;
  }

  /**
   * Endpoint for user registration.
   *
   * @param json JSON containing user registration data (name, firstname, email, telephone,
   *             password, role).
   * @return JSON representing the registered user.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(JsonNode json) {
    // Get and check credentials
    if (!json.hasNonNull("email") || !json.hasNonNull("password") || !json.hasNonNull("name")
        || !json.hasNonNull("firstname") || !json.hasNonNull("email") || !json.hasNonNull(
        "telephone") || !json.hasNonNull("role")) {
      throw new WebApplicationException("All fields are required", Status.BAD_REQUEST);
    }
    String name = json.get("name").asText();
    String firstname = json.get("firstname").asText();
    String email = json.get("email").asText();
    String telephone = json.get("telephone").asText();
    String password = json.get("password").asText();
    String role = json.get("role").asText();

    UserDTO userTemp = myDomainFactory.getUser();
    userTemp.setName(name);
    userTemp.setSurname(firstname);
    userTemp.setEmail(email);
    userTemp.setPhone(telephone);
    userTemp.setPassword(password);
    userTemp.setRole(Role.valueOf(role));

    UserDTO user = myAuthUCC.register(userTemp);

    // Try to register
    ObjectNode publicUser = myJwt.createToken(user);
    if (publicUser == null) {
      throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
          .entity("This resource already exists").type(MediaType.TEXT_PLAIN).build());
    }
    return publicUser;
  }
}
