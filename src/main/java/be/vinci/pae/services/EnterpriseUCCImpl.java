package be.vinci.pae.services;

import be.vinci.pae.api.EnterpriseDAO;
import be.vinci.pae.api.EnterpriseDAOImpl;
import be.vinci.pae.domain.Enterprise;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Path("/ent")
public class EnterpriseUCCImpl implements EnterpriseUCC {
  private EnterpriseDAO enterpriseDAO = new EnterpriseDAOImpl();

  @Override
  @GET
  @Path("enterprises")
  @Produces(MediaType.APPLICATION_JSON)
  public ObjectNode getAllEnterprises() {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode response = mapper.createObjectNode();
    ArrayNode enterprisesArray = mapper.createArrayNode();

    try {
      // Récupérer toutes les entreprises depuis votre DAO
      List<Enterprise> enterprises = enterpriseDAO.getAllEnterprises();

      // Parcourir chaque entreprise et les ajouter à la réponse
      for (Enterprise enterprise : enterprises) {
        ObjectNode enterpriseNode = mapper.createObjectNode();
        enterpriseNode.put("entreprise_id", enterprise.getEntrepriseId());
        enterpriseNode.put("nom", enterprise.getNom());
        enterpriseNode.put("appellation", enterprise.getAppellation());
        enterpriseNode.put("adresse", enterprise.getAdresse());
        enterpriseNode.put("telephone", enterprise.getTelephone());
        enterpriseNode.put("is_blacklist", enterprise.isBlacklist());
        enterpriseNode.put("avis_professeur", enterprise.getAvisProfesseur());
        enterprisesArray.add(enterpriseNode);
      }

      // Ajouter le tableau d'entreprises à la réponse
      response.set("enterprises", enterprisesArray);
    } catch (Exception e) {
      // Gérer les erreurs éventuelles
      response.put("error", e.getMessage());
    }

    return response;
  }

}
