package be.vinci.pae.dao;

import be.vinci.pae.domain.EnterpriseDTO;
import java.util.List;

/**
 * Interface defining the contract for Enterprise Data Access Object.
 */
public interface EnterpriseDAO {

  /**
   * Retrieves all enterprises from the database.
   *
   * @return A list of all enterprises.
   */
  List<EnterpriseDTO> getAllEnterprises();

  EnterpriseDTO getEnterpriseById(int id);
}
