package be.vinci.pae.ucc;

import be.vinci.pae.domain.EnterpriseDTO;
import java.util.List;

/**
 * Interface representing an enterpriseUCC.
 */
public interface EnterpriseUCC {

  List<EnterpriseDTO> getAllEnterprises();

  EnterpriseDTO getEnterprisesByUserId(int userId);
}
