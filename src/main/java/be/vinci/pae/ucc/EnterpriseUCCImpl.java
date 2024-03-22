package be.vinci.pae.ucc;

import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.utils.DALService;
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
  public List<Enterprise> getAllEnterprises() {
    myDALService.start();

    List<Enterprise> enterprises = enterpriseDAO.getAllEnterprises();
    myDALService.commit();

    return enterprises;
  }

  @Override
  public Enterprise getEnterprisesByUserId(int userId) {
    myDALService.start();
    // get entrprise that corresponds to user intership
    Enterprise enterpriseDTO = enterpriseDAO.getEnterpriseById(userId);
    myDALService.commit();

    return enterpriseDTO;
  }
}
