package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the EnterpriseUCC interface.
 */
public class ContactUCCImpl implements ContactUCC {

  @Inject
  ContactDAO myContactDAO;
  @Inject
  EnterpriseDAO myEnterpriseDAO;

  @Inject
  UserDAO myUserDAO;

  @Inject
  private DALService myDALService;

  @Override
  public List<ContactDTO> getContacts(int userId) {
    try {
      myDALService.start();
      List<ContactDTO> contactDTOS = myContactDAO.readMany(userId);
      for (ContactDTO contact : contactDTOS) {
        contact.setEnterpriseDTO(myEnterpriseDAO.readOne(contact.getEnterprise()));
      }
      myDALService.commit();
      return contactDTOS;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO getContact(int userId, int contactId) {
    try {
      myDALService.start();
      ContactDTO contactDTO = myContactDAO.readOne(contactId);
      if (contactDTO.getUser() != userId) {
        throw new NotFoundException();
      }
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(contactDTO.getEnterprise());
      myDALService.commit();
      if (enterpriseDTO == null) {
        throw new NotFoundException();
      }
      contactDTO.setEnterpriseDTO(enterpriseDTO);
      return contactDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO initiateContact(int userId, int enterpriseId) {
    try {
      myDALService.start();
      ContactDTO contactDTO = myContactDAO.create(userId, enterpriseId);
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(contactDTO.getEnterprise());
      myDALService.commit();
      if (enterpriseDTO == null) {
        throw new NotFoundException();
      }
      contactDTO.setEnterpriseDTO(enterpriseDTO);
      return contactDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO initiateContact(int userId, String enterpriseName, String enterpriseLabel,
      String enterpriseAddress, String enterprisePhone, String enterpriseEmail) {
    try {
      myDALService.start();
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.create(enterpriseName, enterpriseLabel,
          enterpriseAddress, enterprisePhone, enterpriseEmail);
      ContactDTO contactDTO = myContactDAO.create(userId, enterpriseDTO.getEnterpriseId());
      myDALService.commit();
      contactDTO.setEnterpriseDTO(enterpriseDTO);
      return contactDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO meetEnterprise(int userId, int contactId, String meetingPoint) {
    try {
      myDALService.start();
      Contact contact = (Contact) myContactDAO.readOne(contactId);
      if (contact.getUser() != userId) {
        throw new NotFoundException();
      }

      if (!contact.meet(meetingPoint)) {
        throw new BusinessException(403, "contact must be initiated");
      }

      ContactDTO updatedContactDTO = myContactDAO.update(contact);
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
      myDALService.commit();
      if (enterpriseDTO == null) {
        throw new NotFoundException();
      }
      updatedContactDTO.setEnterpriseDTO(enterpriseDTO);
      return updatedContactDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO indicateAsRefused(int userId, int contactId, String refusalReason) {
    try {
      myDALService.start();
      Contact contact = (Contact) myContactDAO.readOne(contactId);
      if (contact.getUser() != userId) {
        throw new NotFoundException();
      }

      if (!contact.indicateAsRefused(refusalReason)) {
        throw new BusinessException(403, "contact must be initiated or meet");
      }

      ContactDTO updatedContactDTO = myContactDAO.update(contact);
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
      myDALService.commit();
      if (enterpriseDTO == null) {
        throw new NotFoundException();
      }

      updatedContactDTO.setEnterpriseDTO(enterpriseDTO);
      return updatedContactDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO indicateAsSuspended(int contactId) {
    try {
      myDALService.start();

      Contact contact = (Contact) myContactDAO.readOne(contactId);
      if (contact.getContactId() != contactId) {
        throw new NotFoundException();
      }

      if (!contact.indicateAsSuspended()) {
        throw new BusinessException(403, "contact must be initiated or meet");
      }

      ContactDTO updatedContactDTO = myContactDAO.update(contact);
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
      myDALService.commit();
      if (enterpriseDTO == null) {
        throw new NotFoundException();
      }
      updatedContactDTO.setEnterpriseDTO(enterpriseDTO);
      return updatedContactDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO unfollow(int userId, int contactId) {
    try {
      myDALService.start();
      Contact contact = (Contact) myContactDAO.readOne(contactId);
      if (contact.getUser() != userId) {
        throw new NotFoundException();
      }

      if (!contact.unfollow()) {
        throw new BusinessException(403, "contact must be initiated or meet");
      }

      ContactDTO updatedContactDTO = myContactDAO.update(contact);
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.readOne(updatedContactDTO.getEnterprise());
      myDALService.commit();
      if (enterpriseDTO == null) {
        throw new NotFoundException();
      }
      updatedContactDTO.setEnterpriseDTO(enterpriseDTO);
      return updatedContactDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public ContactDTO accept(int userId, int contactId) {
    try {
      myDALService.start();

      Contact contact = (Contact) myContactDAO.readOne(contactId);
      System.out.println(contact);
      if (contact == null) {
        throw new NotFoundException();
      }

      if (!contact.accept()) {
        throw new BusinessException(403, "contact must be in meet state");
      }

      for (ContactDTO c : myContactDAO.readMany(userId)) {
        if (c.getContactId() != contact.getContactId() && ((Contact) c).unfollow()) {
          myContactDAO.update(c);
        }
      }

      myContactDAO.update(contact);

      myDALService.commit();
      return contact;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public List<ContactDTO> getEnterpriseContacts(int enterpriseId) {
    try {
      myDALService.start();
      List<ContactDTO> contactDTOS = myContactDAO.readEnterpriseContacts(enterpriseId);
      if (contactDTOS == null) {
        throw new NotFoundException();
      }
      for (ContactDTO contact : contactDTOS) {
        contact.setEnterpriseDTO(myEnterpriseDAO.readOne(contact.getEnterprise()));
        contact.setUserDTO(myUserDAO.getOneByID(contact.getUser()));
      }
      myDALService.commit();
      return contactDTOS;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }
}
