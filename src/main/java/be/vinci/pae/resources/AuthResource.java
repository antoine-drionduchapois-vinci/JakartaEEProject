package be.vinci.pae.resources;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.ucc.AuthUCC;
import com.fasterxml.jackson.databind.JsonNode;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Resource class for handling authentication-related endpoints.
 */
@Singleton
@Path("/auths")
public class AuthResource {

  private static final Logger logger = LogManager.getLogger(UserResource.class);

  @Inject
  private DomainFactory myDomainFactory;

  @Inject
  private AuthUCC myAuthUCC;

  @Inject
  private Jwt myJwt;

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
    ThreadContext.put("route", "/auths/login");
    ThreadContext.put("method", "Post");

    if (!json.hasNonNull("email") || !json.hasNonNull("password")) {
      throw new WebApplicationException("Login or password required", Response.Status.BAD_REQUEST);
    }

    String email = json.get("email").asText();
    String password = json.get("password").asText();
    if (email.isEmpty() || password.isEmpty()) {
      throw new WebApplicationException("Login or password cannot be empty",
          Response.Status.BAD_REQUEST);
    }
    ThreadContext.put("params", "email:" + email);

    UserDTO userTemp = myDomainFactory.getUser();
    userTemp.setPassword(password);
    userTemp.setEmail(email);
    UserDTO userDTO = myAuthUCC.login(userTemp);

    ObjectNode publicUser = myJwt.createToken(userDTO);
    if (publicUser == null) {
      throw new WebApplicationException("Login or password incorrect",
          Response.Status.UNAUTHORIZED);
    }
    logger.info("Status: 200 {login}");
    ThreadContext.clearAll();
    return publicUser;
  }

  /**
   * Endpoint for user registration.
   *
   * @param userDTO dto containing user registration data (name, firstname, email,
   *                telephone,
   *                password, role).
   * @return JSON representing the registered user.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode register(UserDTO userDTO) {
    ThreadContext.put("route", "/auths/register");
    ThreadContext.put("method", "Post");
    String name = userDTO.getName();
    String firstname = userDTO.getSurname();
    String email = userDTO.getEmail();
    String telephone = userDTO.getPhone();
    String role = userDTO.getRole().toString();
    String password = userDTO.getPassword();

    if (name == null ||
        name.trim().isEmpty() ||
        firstname == null ||
        firstname.trim().isEmpty() ||
        email == null ||
        email.trim().isEmpty() ||
        telephone == null ||
        telephone.trim().isEmpty() ||
        role == null ||
        role.trim().isEmpty() ||
        password == null ||
        password.trim().isEmpty()) {
      throw new WebApplicationException("All fields are required", Status.BAD_REQUEST);
    }

    ThreadContext.put("params",
        "name:" + name + "firstname:" + firstname + "email:" + email + "telephone:" + telephone
            + "role:" + role);

    UserDTO user = myAuthUCC.register(userDTO);

    // Try to register
    ObjectNode publicUser = myJwt.createToken(user);
    if (publicUser == null) {
      throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
          .entity("This resource already exists").type(MediaType.TEXT_PLAIN).build());
    }
    logger.info("Status: 200 {register}");
    ThreadContext.clearAll();
    return publicUser;
  }
}
