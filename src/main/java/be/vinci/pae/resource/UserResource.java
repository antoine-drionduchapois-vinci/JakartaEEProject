package be.vinci.pae.resource;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface UserResource {

  @GET
  @Path("stats")
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getGlobalStats();

  @GET
  @Path("All")
  @Produces(MediaType.APPLICATION_JSON)
  ArrayNode getUsersAsJson();

  @POST
  @Path("getUserInfoById")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getUsersByIdAsJson(JsonNode json);
}
