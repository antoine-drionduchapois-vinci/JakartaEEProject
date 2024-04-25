package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the Enterprise interface.
 */
public class EnterpriseUCCImpl implements EnterpriseUCC {

  @Inject
  private EnterpriseDAO myEnterpriseDAO;
  @Inject
  private ContactDAO myContactDAO;
  @Inject
  private ContactUCC myContactUCC;
  @Inject
  private DALService myDALService;

  @Override
  public List<EnterpriseDTO> getAllEnterprises() {
    try {
      myDALService.start();

      List<EnterpriseDTO> enterpriseDTOS = myEnterpriseDAO.getAllEnterprises();
      myDALService.commit();

      return enterpriseDTOS;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public EnterpriseDTO getEnterprisesByUserId(int userId) {
    try {
      myDALService.start();
      // get entrprise that corresponds to user intership
      EnterpriseDTO enterpriseDTO = myEnterpriseDAO.getEnterpriseById(userId);
      myDALService.commit();

      return enterpriseDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public EnterpriseDTO blacklistEnterprise(int enterpriseId, String blacklistedReason) {
    try {
      myDALService.start();
      Enterprise enterprise = (Enterprise) myEnterpriseDAO.readOne(enterpriseId);

      if (enterprise.getEnterpriseId() != enterpriseId) {
        throw new NotFoundException();
      }

      if (!enterprise.indicateAsBlacklisted(blacklistedReason)) {
        throw new BusinessException(403,
            "This enterprise is already blacklisted");
      }

      Enterprise blacklistedEnterpriseDTO = (Enterprise) myEnterpriseDAO.update(enterprise);

      List<ContactDTO> contactDTOS = myContactDAO.readEnterpriseInitiatedOrMeetContacts(
          enterpriseId);
      if (contactDTOS != null) {
        for (ContactDTO contactDTO : contactDTOS) {
          myContactUCC.indicateAsSuspended(contactDTO.getContactId());
        }
      }

      myDALService.commit();
      return blacklistedEnterpriseDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }
}
