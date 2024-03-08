package be.vinci.pae.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Represents a User Use Case Controller (UCC) for handling user-related operations.
 */
public interface UserUCC {

  /**
   * Logs in a user using JSON input.
   *
   * @param json the JSON node containing login information
   * @return an ObjectNode containing login details
   */
  @POST
  @Path("login")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode login(JsonNode json);

  /**
   * Logs in a user using email and password.
   *
   * @param email    the email of the user
   * @param password the password of the user
   * @return an ObjectNode containing login details
   */
  ObjectNode login(String email, String password);

  /**
   * get all user.
   *
   * @return all user
   */
  @GET
  @Path("All")
  @Produces(MediaType.APPLICATION_JSON)
  ArrayNode getUsersAsJson();

  /**
   * get statistique user stage.
   *
   * @return all stat
   */
  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getGlobalStats();
}
