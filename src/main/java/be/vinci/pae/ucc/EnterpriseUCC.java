package be.vinci.pae.ucc;

import be.vinci.pae.domain.Enterprise;
import java.util.List;

/**
 * Interface representing an enterpriseUCC.
 */
public interface EnterpriseUCC {

  List<Enterprise> getAllEnterprises();

  Enterprise getEnterprisesByUserId(int userId);
}
