package be.vinci.pae.api;

import be.vinci.pae.domain.Enterprise;
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
  List<Enterprise> getAllEnterprises();

  Enterprise getEnterpriseById(int id);
}
