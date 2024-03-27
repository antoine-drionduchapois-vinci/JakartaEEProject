package be.vinci.pae.resources;

import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.ucc.EnterpriseUCC;
import be.vinci.pae.ucc.InternshipUCC;
import be.vinci.pae.ucc.SupervisorUCC;
import be.vinci.pae.utils.JWTDecryptToken;
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
import jakarta.ws.rs.core.Response.Status;

/**
 * Implementation of the internship interface.
 */
@Singleton
@Path("/int")
public class InternshipResource {


  private JWTDecryptToken decryptToken = new JWTDecryptToken();

  @Inject
  private InternshipUCC myInternshipUCC;

  @Inject
  private EnterpriseUCC myEnterpriseUCC;

  @Inject
  private SupervisorUCC myResponsbileUCC;


  /**
   * Retrieves users internship info.
   *
   * @return an ObjectNode containing users info
   */

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getUserInternship(JsonNode json) {
    System.out.println("GetUserInternship");
    int userId = decryptToken.getIdFromJsonToken(json);

    if (userId == 0) {
      throw new WebApplicationException("userId is required", Status.BAD_REQUEST);
    }

    //get entrprise that corresponds to user intership

    InternshipDTO internshipDTO = myInternshipUCC.getUserInternship(userId);
    EnterpriseDTO enterpriseDTO = myEnterpriseUCC.getEnterprisesByUserId(userId);
    if (enterpriseDTO == null) {
      return null;
    }
    SupervisorDTO responsibleDTO = myResponsbileUCC.getResponsibleByEnterpriseId(
        enterpriseDTO.getEnterpriseId());

    ObjectMapper mapper = new ObjectMapper();
    ObjectNode internshipNode = mapper.createObjectNode();
    internshipNode.put("enterprise", enterpriseDTO.getName());
    internshipNode.put("year", internshipDTO.getYear());
    internshipNode.put("responsbile", responsibleDTO.getName());
    internshipNode.put("phone", responsibleDTO.getPhone());
    internshipNode.put("contact", internshipDTO.getContact());

    return internshipNode;
  }
}
