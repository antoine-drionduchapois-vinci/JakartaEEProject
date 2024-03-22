package be.vinci.pae.ucc;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.Enterprise;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.util.List;

@Singleton
public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO myContactDAO;
  @Inject
  EnterpriseDAO myEnterpriseDAO;

  private final ObjectMapper jsonMapper = new ObjectMapper();

  public List<Contact> getContacts(int userId) {
    List<Contact> contacts = myContactDAO.readMany(userId);
    if (contacts == null) {
      return null;
    }
    return contacts;
  }

  public ObjectNode getContact(int contactId) {
    Contact contact = myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }
    Enterprise enterprise = myEnterpriseDAO.readOne(contact.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }
    contact.setEnterpriseDTO(enterprise);
    return convertDTOToJson(contact);
  }

  public ObjectNode initiateContact(int userId, int enterpriseId) {
    if (myContactDAO.readOne(userId, enterpriseId) != null) {
      return null;
      // TODO: handle conflict
    }
    Contact contact = myContactDAO.create("initié", getCurrentYearString(), userId, enterpriseId);
    Enterprise enterprise = myEnterpriseDAO.readOne(contact.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }
    contact.setEnterpriseDTO(enterprise);
    return convertDTOToJson(contact);
  }

  @Override
  public ObjectNode initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAddress, String enterpriseContact) {
    if (myEnterpriseDAO.readOne(enterpriseName, enterpriseLabel) != null) {
      return null; // TODO: handle conflict
    }
    Enterprise enterprise = myEnterpriseDAO.create(enterpriseName, enterpriseLabel,
        enterpriseAddress, enterpriseContact);
    if (enterprise == null) {
      return null; // TODO: handle error
    }
    Contact contact = myContactDAO.create("initié", getCurrentYearString(), userId,
        enterprise.getEnterpriseId());
    contact.setEnterpriseDTO(enterprise);
    return convertDTOToJson(contact);
  }

  @Override
  public ObjectNode meetEnterprise(int contactId, String meetingPoint) {
    Contact contact = myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    if (!contact.meet(meetingPoint)) {
      return null; // TODO: handle forbidden
    }

    Contact updatedContact = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContact.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }
    updatedContact.setEnterpriseDTO(enterprise);
    return convertDTOToJson(updatedContact);
  }

  @Override
  public ObjectNode indicateAsRefused(int contactId, String refusalReason) {
    Contact contact = myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    contact.inidcateAsRefused(refusalReason);

    Contact updatedContact = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContact.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }

    updatedContact.setEnterpriseDTO(enterprise);
    return convertDTOToJson(updatedContact);
  }

  @Override
  public ObjectNode unfollow(int contactId) {
    Contact contact = myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    contact.unfollow();

    Contact updatedContact = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContact.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }

    updatedContact.setEnterpriseDTO(enterprise);
    return convertDTOToJson(updatedContact);
  }

  private String getCurrentYearString() {
    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = LocalDate.of(currentDate.getYear() - 1, 9, 1);
    LocalDate endDate = LocalDate.of(currentDate.getYear(), 9, 1);
    return startDate.getYear() + "-" + endDate.getYear();
  }

  private ObjectNode convertDTOToJson(Contact contact) {
    ObjectNode enterpriseNode = jsonMapper.createObjectNode()
        .put("enterpriseId", contact.getEnterpriseDTO().getEnterpriseId())
        .put("name", contact.getEnterpriseDTO().getName())
        .put("label", contact.getEnterpriseDTO().getLabel())
        .put("adress", contact.getEnterpriseDTO().getAddress())
        .put("contact", contact.getEnterpriseDTO().getContactInfos())
        .put("opinionTeacher", contact.getEnterpriseDTO().getBlacklistedReason());

    return jsonMapper.createObjectNode()
        .put("contactId", contact.getContactId())
        .put("description", contact.getMeetingPoint())
        .put("state", contact.getState())
        .put("reasonRefusal", contact.getRefusalReason())
        .put("year", contact.getYear())
        .put("useriD", contact.getUser())
        .put("enterpriseId", contact.getEnterprise())
        .putPOJO("enterprise", enterpriseNode);
  }
}
