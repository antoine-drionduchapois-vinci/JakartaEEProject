package be.vinci.pae.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Defines the RESTful resource for authentication operations including login and registration.
 */
public interface AuthResource {

  /**
   * Attempts to log in a user with the provided credentials.
   *
   * @param json A JSON object containing the user's login credentials, typically including email and password.
   * @return An ObjectNode that includes authentication details, such as a token, upon successful authentication.
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode login(JsonNode json);

  /**
   * Registers a new user with the provided details.
   *
   * @param json A JSON object containing the details of the user to register. This typically includes
   *             the user's name, email, password, and potentially other registration-required information.
   * @return An ObjectNode that includes authentication details, such as a token, upon successful registration.
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode register(JsonNode json);
}
