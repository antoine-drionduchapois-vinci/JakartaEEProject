package be.vinci.pae.ucc;

import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.Enterprise;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the Enterprise interface.
 */
public class EnterpriseUCCImpl implements EnterpriseUCC {

  @Inject
  private EnterpriseDAO enterpriseDAO;

  @Override
  public List<Enterprise> getAllEnterprises() {

    List<Enterprise> enterprises = enterpriseDAO.getAllEnterprises();

    return enterprises;
  }

  @Override
  public Enterprise getEnterprisesByUserId(int userId) {

    // get entrprise that corresponds to user intership
    Enterprise enterpriseDTO = enterpriseDAO.getEnterpriseById(userId);

    return enterpriseDTO;
  }
}
