package be.vinci.pae.ucc;

import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.EnterpriseDTO;
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
  public List<EnterpriseDTO> getAllEnterprises() {
    myDALService.start();

    List<EnterpriseDTO> enterpriseDTOS = enterpriseDAO.getAllEnterprises();
    myDALService.commit();

    return enterpriseDTOS;
  }

  @Override
  public EnterpriseDTO getEnterprisesByUserId(int userId) {
    myDALService.start();
    // get entrprise that corresponds to user intership
    EnterpriseDTO enterpriseDTO = enterpriseDAO.getEnterpriseById(userId);
    System.out.println(enterpriseDTO);
    myDALService.commit();

    return enterpriseDTO;
  }
}
