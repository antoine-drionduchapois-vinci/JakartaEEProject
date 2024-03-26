package be.vinci.pae.ucc;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.NotFoundException;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
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
  public ContactDTO getContact(int contactId) {
    myDALService.start();
    ContactDTO contact = myContactDAO.readOne(contactId);
    if (contact == null) {
      throw new NotFoundException();
    }
    Enterprise enterprise = myEnterpriseDAO.readOne(contact.getEnterprise());
    myDALService.commit();
    if (enterprise == null) {
      throw new NotFoundException();
    }
    contact.setEnterpriseDTO(enterprise);
    return contact;
  }

  @Override
  public ContactDTO initiateContact(int userId, int enterpriseId) {
    myDALService.start();
    ContactDTO contact = myContactDAO.create(userId, enterpriseId);
    Enterprise enterprise = myEnterpriseDAO.readOne(contact.getEnterprise());
    myDALService.commit();
    if (enterprise == null) {
      throw new NotFoundException();
    }
    contact.setEnterpriseDTO(enterprise);
    return contact;
  }

  @Override
  public ContactDTO initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAddress, String enterprisePhone, String enterpriseEmail) {
    myDALService.start();
    Enterprise enterprise = myEnterpriseDAO.create(enterpriseName, enterpriseLabel,
        enterpriseAddress, enterprisePhone, enterpriseEmail);
    ContactDTO contact = myContactDAO.create(userId, enterprise.getEnterpriseId());
    myDALService.commit();
    contact.setEnterpriseDTO(enterprise);
    return contact;
  }

  @Override
  public ContactDTO meetEnterprise(int contactId, String meetingPoint) {
    myDALService.start();
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      throw new NotFoundException();
    }

    if (!contact.meet(meetingPoint)) {
      throw new BusinessException(403, "contact must be initiated");
    }

    ContactDTO updatedContactDTO = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
    myDALService.commit();
    if (enterprise == null) {
      throw new NotFoundException();
    }
    updatedContactDTO.setEnterpriseDTO(enterprise);
    return updatedContactDTO;
  }

  @Override
  public ContactDTO indicateAsRefused(int contactId, String refusalReason) {
    myDALService.start();
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    if (!contact.indicateAsRefused(refusalReason)) {
      throw new BusinessException(403, "contact must be initiated or meet");
    }

    ContactDTO updatedContactDTO = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
    myDALService.commit();
    if (enterprise == null) {
      throw new NotFoundException();
    }
    updatedContactDTO.setEnterpriseDTO(enterprise);
    return updatedContactDTO;
  }

  @Override
  public ContactDTO unfollow(int contactId) {
    myDALService.start();
    Contact contact = (Contact) myContactDAO.readOne(contactId);
    if (contact == null) {
      return null;
    }

    if (!contact.unfollow()) {
      throw new BusinessException(403, "contact must be initiated or meet");
    }

    ContactDTO updatedContactDTO = myContactDAO.update(contact);
    Enterprise enterprise = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
    myDALService.commit();
    if (enterprise == null) {
      throw new NotFoundException();
    }
    updatedContactDTO.setEnterpriseDTO(enterprise);
    return updatedContactDTO;
  }
}
