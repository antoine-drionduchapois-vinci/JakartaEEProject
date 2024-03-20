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
import java.util.List;

/**
 * Implementation of the ContactUCC interface providing methods for managing contact-related
 * functionality.
=======
import be.vinci.pae.domain.ContactDTO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.util.List;

/**
 * Implementation of the Contact UCC interface.
>>>>>>> master
 */
@Singleton
public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO myContactDAO;
  @Inject
  EnterpriseDAO myEnterpriseDAO;


  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Override
  public List<ContactDTO> getAllUsersContact(int id) {

    // Récupérer la liste complète des contacts de user depuis votre DAO
    List<ContactDTO> getAllUsersContact = myContactDAO.getAllUsersContact(id);

    return getAllUsersContact;
  }

  @Override
  public ObjectNode getContact(int contactid) {
    ContactDTO contact = myContactDAO.readOne(contactid);
    if (contact == null) {
      return null; // TODO: handle error 404
    }
    EnterpriseDTO enterprise = myEnterpriseDAO.readOne(contact.getEntreprise());
    return convertDTOsTOJson(contact, enterprise);
  }

  @Override
  public ObjectNode initiateContact(int userId, int enterpriseId) {
    if (myContactDAO.readOne(userId, enterpriseId) != null) {
      return null;
      // TODO: handle error
    }
    ContactDTO contact = myContactDAO.create("initié", getCurrentYearString(), userId, enterpriseId);
    EnterpriseDTO enterprise = myEnterpriseDAO.readOne(contact.getEntreprise());

    return convertDTOsTOJson(contact, enterprise);
  }

  @Override
  public ObjectNode initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAddress, String enterpriseContact) {
    EnterpriseDTO enterprise = myEnterpriseDAO.create(enterpriseName, enterpriseLabel,
        enterpriseAddress, enterpriseContact);
    ContactDTO contact = myContactDAO.create("initié", getCurrentYearString(), userId,
        enterprise.getEntrepriseId());

    return convertDTOsTOJson(contact, enterprise);
  }

  private String getCurrentYearString() {
    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = LocalDate.of(currentDate.getYear() - 1, 9, 1);
    LocalDate endDate = LocalDate.of(currentDate.getYear(), 9, 1);
    return startDate.getYear() + "-" + endDate.getYear();
  }

  private ObjectNode convertDTOsTOJson(ContactDTO contact, EnterpriseDTO enterprise) {
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
}
