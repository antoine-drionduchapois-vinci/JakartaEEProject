package be.vinci.pae.dao;

import be.vinci.pae.domain.EnterpriseDTO;
import java.util.List;

/**
 * The EnterpriseDAO interface provides methods for accessing enterprise
 * information from the database.
 */
public interface EnterpriseDAO {

  /**
   * Retrieves a list of all enterprises stored in the database.
   *
   * @return A List containing EnterpriseDTO objects representing all enterprises
   *         stored in the database.
   */
  List<EnterpriseDTO> getAllEnterprises();

  /**
   * Retrieves the enterprise information associated with the specified ID.
   *
   * @param id The ID of the enterprise to retrieve information for.
   * @return An EnterpriseDTO object representing the enterprise information
   *         associated with the
   *         specified ID, or null if no enterprise with the given ID is found.
   */
  EnterpriseDTO getEnterpriseById(int id);
}
