package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * The UserUCC interface defines methods for user authentication and registration.
 */
public interface UserUCC {

  /**
   * Authenticates a user with the provided JSON credentials.
   *
   * @param json the JSON object containing user credentials
   * @return an ObjectNode representing the authenticated user
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode login(JsonNode json);

  /**
   * Authenticates a user with the provided email and password.
   *
   * @param email    the user's email address
   * @param password the user's password
   * @return an ObjectNode representing the authenticated user
   */
  ObjectNode login(String email, String password);

  /**
   * Registers a new user with the provided JSON data.
   *
   * @param json the JSON object containing user registration data
   * @return an ObjectNode representing the registered user
   */
  @POST
  @Path("register")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode register(JsonNode json);

  /**
   * Registers a new user with the provided User object.
   *
   * @param user1 the User object representing the user to be registered
   * @return an ObjectNode representing the registered user
   */
  ObjectNode register(User user1);

  /**
   * Creates a new user with the provided information and returns the user object.
   *
   * @param name      the user's name
   * @param firstname the user's first name
   * @param email     the user's email address
   * @param telephone the user's telephone number
   * @param password  the user's password
   * @param role      the user's role
   * @return the User object representing the created user
   */
  User createUserAndReturn(String name, String firstname, String email, String telephone,
      String password, String role);
}
