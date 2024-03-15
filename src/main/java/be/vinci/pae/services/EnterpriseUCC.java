package be.vinci.pae.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Represents a enterprise Use Case Controller (UCC) for handling enterprise-related operations.
 */
public interface EnterpriseUCC {

  @GET
  @Path("enterprises")
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getAllEnterprises();

  @POST
  @Path("enterprises")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getEnterprisesByUserId(JsonNode json);
}
