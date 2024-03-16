package be.vinci.pae.ucc;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.EnterpriseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.LocalDate;

@Singleton
public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO myContactDAO;
  @Inject
  EnterpriseDAO myEnterpriseDAO;


  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Override
  public ObjectNode initiateContact(int userId, int enterpriseId) {
    if (myContactDAO.readOne(userId, enterpriseId) != null) {
      return null;
      // TODO: handle error
    }
    ContactDTO contact = myContactDAO.create("initié", getCurrentYearString(), userId, enterpriseId);
    EnterpriseDTO enterprise = myEnterpriseDAO.getEnterpriseById(contact.getEntreprise());

    ObjectNode enterpriseNode = jsonMapper.createObjectNode()
        .put("enterpriseId", enterprise.getEntrepriseId())
        .put("name", enterprise.getNom())
        .put("label", enterprise.getAppellation())
        .put("adress", enterprise.getAdresse())
        .put("contact", enterprise.getTelephone())
        .put("opinionTeacher", enterprise.getAvisProfesseur());

    return jsonMapper.createObjectNode()
        .put("contactId", contact.getContactId())
        .put("description", contact.getDescription())
        .put("state", contact.getState())
        .put("reasonRefusal", contact.getReasonRefusal())
        .put("year", contact.getYear())
        .put("useriD", contact.getUser())
        .put("enterpriseId", contact.getEntreprise())
        .putPOJO("enterprise", enterpriseNode);
  }

  private String getCurrentYearString() {
    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = LocalDate.of(currentDate.getYear() - 1, 9, 1);
    LocalDate endDate = LocalDate.of(currentDate.getYear(), 9, 1);
    return startDate.getYear() + "-" + endDate.getYear();
  }
}
