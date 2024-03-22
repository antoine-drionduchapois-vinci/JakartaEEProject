package be.vinci.pae.ucc;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.Enterprise;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the EnterpriseUCC interface.
 */
@Singleton
public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO myContactDAO;
  @Inject
  EnterpriseDAO myEnterpriseDAO;

  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Override
  public List<ContactDTO> getContacts(int userId) {
    List<ContactDTO> contactDTOS = myContactDAO.readMany(userId);
    if (contactDTOS == null) {
      return null;
    }
    return contactDTOS;
  }

  @Override
  public ObjectNode getContact(int contactId) {
    ContactDTO contactDTO = myContactDAO.readOne(contactId);
    if (contactDTO == null) {
      return null;
    }
    Enterprise enterprise = myEnterpriseDAO.readOne(contactDTO.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }
    contactDTO.setEnterpriseDTO(enterprise);
    return convertDTOToJson(contactDTO);
  }

  @Override
  public ObjectNode initiateContact(int userId, int enterpriseId) {
    if (myContactDAO.readOne(userId, enterpriseId) != null) {
      return null;
      // TODO: handle conflict
    }
    ContactDTO contactDTO = myContactDAO.create("initié", getCurrentYearString(), userId,
        enterpriseId);
    Enterprise enterprise = myEnterpriseDAO.readOne(contactDTO.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }
    contactDTO.setEnterpriseDTO(enterprise);
    return convertDTOToJson(contactDTO);
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
    ContactDTO contactDTO = myContactDAO.create("initié", getCurrentYearString(), userId,
        enterprise.getEnterpriseId());
    contactDTO.setEnterpriseDTO(enterprise);
    return convertDTOToJson(contactDTO);
  }

  @Override
  public ObjectNode meetEnterprise(int contactId, String meetingPoint) {
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    if (!contact.meet(meetingPoint)) {
      return null; // TODO: handle forbidden
    }

    ContactDTO updatedContactDTO = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }
    updatedContactDTO.setEnterpriseDTO(enterprise);
    return convertDTOToJson(updatedContactDTO);
  }

  @Override
  public ObjectNode indicateAsRefused(int contactId, String refusalReason) {
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    contact.inidcateAsRefused(refusalReason);

    ContactDTO updatedContactDTO = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }

    updatedContactDTO.setEnterpriseDTO(enterprise);
    return convertDTOToJson(updatedContactDTO);
  }

  @Override
  public ObjectNode unfollow(int contactId) {
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    contact.unfollow();

    ContactDTO updatedContactDTO = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
    if (enterprise == null) {
      return null; // TODO: handle error
    }

    updatedContactDTO.setEnterpriseDTO(enterprise);
    return convertDTOToJson(updatedContactDTO);
  }

  private String getCurrentYearString() {
    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = LocalDate.of(currentDate.getYear() - 1, 9, 1);
    LocalDate endDate = LocalDate.of(currentDate.getYear(), 9, 1);
    return startDate.getYear() + "-" + endDate.getYear();
  }

  private ObjectNode convertDTOToJson(ContactDTO contactDTO) {
    ObjectNode enterpriseNode = jsonMapper.createObjectNode()
        .put("enterpriseId", contactDTO.getEnterpriseDTO().getEnterpriseId())
        .put("name", contactDTO.getEnterpriseDTO().getName())
        .put("label", contactDTO.getEnterpriseDTO().getLabel())
        .put("adress", contactDTO.getEnterpriseDTO().getAddress())
        .put("contact", contactDTO.getEnterpriseDTO().getContactInfos())
        .put("opinionTeacher", contactDTO.getEnterpriseDTO().getBlacklistedReason());

    return jsonMapper.createObjectNode()
        .put("contactId", contactDTO.getContactId())
        .put("description", contactDTO.getMeetingPoint())
        .put("state", contactDTO.getState())
        .put("reasonRefusal", contactDTO.getRefusalReason())
        .put("year", contactDTO.getYear())
        .put("useriD", contactDTO.getUser())
        .put("enterpriseId", contactDTO.getEnterprise())
        .putPOJO("enterprise", enterpriseNode);
  }
}
