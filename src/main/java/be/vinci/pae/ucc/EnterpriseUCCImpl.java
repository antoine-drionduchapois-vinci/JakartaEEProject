package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.EnterpriseDTO;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the Enterprise interface.
 */
public class EnterpriseUCCImpl implements EnterpriseUCC {

  @Inject
  private EnterpriseDAO enterpriseDAO;
  @Inject
  private DALService myDALService;

  @Override
  public List<EnterpriseDTO> getAllEnterprises() {
    try {
      myDALService.start();

      List<EnterpriseDTO> enterpriseDTOS = enterpriseDAO.getAllEnterprises();
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
      EnterpriseDTO enterpriseDTO = enterpriseDAO.getEnterpriseById(userId);
      myDALService.commit();

      return enterpriseDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }
}
