package be.vinci.pae.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface EnterpriseUCC {

  @GET
  @Path("enterprises")
  @Produces(MediaType.APPLICATION_JSON)
  ObjectNode getAllEnterprises();
}
