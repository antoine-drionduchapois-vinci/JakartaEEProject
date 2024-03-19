package be.vinci.pae.ucc;

import be.vinci.pae.domain.EnterpriseDTO;
import java.util.List;

/**
 * The EnterpriseUCC interface provides methods for managing enterprise-related functionality.
 */
public interface EnterpriseUCC {

  /**
   * Retrieves a list of all enterprises stored in the system.
   *
   * @return A List containing EnterpriseDTO objects representing all enterprises stored in the system.
   */
  List<EnterpriseDTO> getAllEnterprises();

  /**
   * Retrieves the enterprise associated with the specified user ID.
   *
   * @param userId The ID of the user for whom to retrieve the associated enterprise.
   * @return An EnterpriseDTO object representing the enterprise associated with the specified user ID, or null if no enterprise is found for the given user ID.
   */
  EnterpriseDTO getEnterprisesByUserId(int userId);
}
