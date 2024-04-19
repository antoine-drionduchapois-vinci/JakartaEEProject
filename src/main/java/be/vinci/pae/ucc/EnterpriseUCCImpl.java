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
    myDALService.start();

    List<EnterpriseDTO> enterpriseDTOS = myEnterpriseDAO.getAllEnterprises();
    myDALService.commit();

    return enterpriseDTOS;
  }

  @Override
  public EnterpriseDTO getEnterprisesByUserId(int userId) {
    myDALService.start();
    // get entrprise that corresponds to user intership
    EnterpriseDTO enterpriseDTO = myEnterpriseDAO.getEnterpriseById(userId);
    myDALService.commit();

    return enterpriseDTO;
  }

  @Override
  public EnterpriseDTO blacklistEnterprise(int enterpriseId, String blacklistedReason) {
    try {
      myDALService.start();

      Enterprise enterprise = (Enterprise) myEnterpriseDAO.readOne(enterpriseId);

      if (enterprise.getEnterpriseId() != enterpriseId) {
        throw new NotFoundException();
      }

      if (!enterprise.toBlacklist(blacklistedReason)) {
        throw new BusinessException(403, "enterprise must not be already blacklisted");
      }

      EnterpriseDTO blacklistedEnterpriseDTO = myEnterpriseDAO.toBlacklist(enterprise);

      //Solution avec UCC Imbriqués !!!
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
