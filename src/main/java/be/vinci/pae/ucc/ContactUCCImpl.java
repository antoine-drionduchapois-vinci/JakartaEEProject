package be.vinci.pae.ucc;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.utils.DALService;
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

  @Inject
  private DALService myDALService;
  private final ObjectMapper jsonMapper = new ObjectMapper();

  @Override
  public List<ContactDTO> getContacts(int userId) {
    myDALService.start();
    List<ContactDTO> contactDTOS = myContactDAO.readMany(userId);
    myDALService.commit();
    if (contactDTOS == null) {
      return null;
    }
    return contactDTOS;
  }

  @Override
  public ObjectNode getContact(int contactId) {
    myDALService.start();
    ContactDTO contactDTO = myContactDAO.readOne(contactId);
    if (contactDTO == null) {
      return null;
    }
    EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(contactDTO.getEnterprise());
    myDALService.commit();
    if (enterpriseDTO == null) {
      return null; // TODO: handle error
    }
    contactDTO.setEnterpriseDTO(enterpriseDTO);
    return convertDTOToJson(contactDTO);
  }

  @Override
  public ObjectNode initiateContact(int userId, int enterpriseId) {
    myDALService.start();
    if (myContactDAO.readOne(userId, enterpriseId) != null) {
      return null;
      // TODO: handle conflict
    }
    ContactDTO contactDTO = myContactDAO.create("initié", getCurrentYearString(), userId,
        enterpriseId);
    EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(contactDTO.getEnterprise());
    myDALService.commit();
    if (enterpriseDTO == null) {
      return null; // TODO: handle error
    }
    contactDTO.setEnterpriseDTO(enterpriseDTO);
    return convertDTOToJson(contactDTO);
  }

  @Override
  public ObjectNode initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAddress, String enterpriseContact) {
    myDALService.start();
    if (myEnterpriseDAO.readOne(enterpriseName, enterpriseLabel) != null) {
      return null; // TODO: handle conflict
    }
    EnterpriseDTO enterpriseDTO = myEnterpriseDAO.create(enterpriseName, enterpriseLabel,
        enterpriseAddress, enterpriseContact);
    if (enterpriseDTO == null) {
      return null; // TODO: handle error
    }
    ContactDTO contactDTO = myContactDAO.create("initié", getCurrentYearString(), userId,
        enterpriseDTO.getEnterpriseId());
    myDALService.commit();
    contactDTO.setEnterpriseDTO(enterpriseDTO);
    return convertDTOToJson(contactDTO);
  }

  @Override
  public ObjectNode meetEnterprise(int contactId, String meetingPoint) {
    myDALService.start();
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    if (!contact.meet(meetingPoint)) {
      return null; // TODO: handle forbidden
    }

    return getJsonNodes(contact);
  }

  @Override
  public ObjectNode indicateAsRefused(int contactId, String refusalReason) {
    myDALService.start();
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    contact.inidcateAsRefused(refusalReason);

    return getJsonNodes(contact);
  }

  @Override
  public ObjectNode unfollow(int contactId) {
    myDALService.start();
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    contact.unfollow();

    return getJsonNodes(contact);
  }

  private ObjectNode getJsonNodes(Contact contact) {
    ContactDTO updatedContactDTO = myContactDAO.update(contact);
    EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
    myDALService.commit();
    if (enterpriseDTO == null) {
      return null; // TODO: handle error
    }

    updatedContactDTO.setEnterpriseDTO(enterpriseDTO);
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
        .put("contact", contactDTO.getEnterpriseDTO().getPhone())
        .put("email", contactDTO.getEnterpriseDTO().getEmail())
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
