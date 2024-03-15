package be.vinci.pae.ucc;

import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.EnterpriseDAOImpl;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.algorithms.Algorithm;
import java.util.List;

/**
 * Implementation of the Enterprise interface.
 */

public class EnterpriseUCCImpl implements EnterpriseUCC {


  private EnterpriseDAO enterpriseDAO = new EnterpriseDAOImpl();


  @Override
  public List<Enterprise> getAllEnterprises() {

    List<Enterprise> enterprises = enterpriseDAO.getAllEnterprises();

    return enterprises;
  }


  @Override
  public Enterprise getEnterprisesByUserId(int userId) {

    //get entrprise that corresponds to user intership
    Enterprise enterprise = enterpriseDAO.getEnterpriseById(userId);


    return enterprise;
  }
}
