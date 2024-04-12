package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 * Implementation of the Enterprise interface.
 */
@Singleton
@Path("/ent")
public class EnterpriseResource {

  private static final Logger logger = LogManager.getLogger(EnterpriseResource.class);

  @Inject
  private EnterpriseUCC myEnterpriseUCC;

  @Inject
  private Jwt myJwt;

  /**
   * Retrieves all enterprise.
   *
   * @return an ObjectNode containing all enterprises
   */

  @GET
  @Path("enterprises")
  @Produces(MediaType.APPLICATION_JSON)
  public List<EnterpriseDTO> getAllEnterprises() {
    ThreadContext.put("route", "/contact");
    ThreadContext.put("method", "Get");
    ThreadContext.put("params", "NoParam");

    List<EnterpriseDTO> enterprises = myEnterpriseUCC.getAllEnterprises();

    logger.info("Status: 200 {getAllEnterprises}");
    ThreadContext.clearAll();
    return enterprises;
  }


}
