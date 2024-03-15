package be.vinci.pae.ucc;

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


  @Override
  public List<EnterpriseDTO> getAllEnterprises() {

    List<EnterpriseDTO> enterprises = enterpriseDAO.getAllEnterprises();

    return enterprises;
  }


  @Override
  public EnterpriseDTO getEnterprisesByUserId(int userId) {

    //get entrprise that corresponds to user intership
    EnterpriseDTO enterpriseDTO = enterpriseDAO.getEnterpriseById(userId);

    return enterpriseDTO;
  }
}
