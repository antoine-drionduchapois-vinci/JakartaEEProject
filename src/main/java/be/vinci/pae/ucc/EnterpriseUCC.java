package be.vinci.pae.ucc;

import be.vinci.pae.domain.EnterpriseDTO;
import java.util.List;

/**
 * The EnterpriseUCC interface provides methods for managing enterprise-related
 * functionality.
 */
public interface EnterpriseUCC {

  /**
   * Retrieves a list of all enterprises stored in the system.
   *
   * @return A List containing EnterpriseDTO objects representing all enterprises
   *         stored in the
   *         system.
   */
  List<EnterpriseDTO> getAllEnterprises();

  /**
   * Retrieves the enterprise associated with the specified user ID.
   *
   * @param userId The ID of the user for whom to retrieve the associated
   *               enterprise.
   * @return An EnterpriseDTO object representing the enterprise associated with
   *         the specified user
   *         ID, or null if no enterprise is found for the given user ID.
   */
  EnterpriseDTO getEnterprisesByUserId(int userId);

  /**
   * Blacklist an entreprise and justify the reason for blacklisting.
   *
   * @param enterpriseID      The ID of the enterprise to blacklist
   * @param blacklistedReason The justification for blacklisting
   * @return An EnterpriseDTO object representing the enterprise blacklisted with
   *         the reason for
   *         blacklisting, or null if no enterprise is found, or if the enterprise
   *         is already on blacklist
   */
  EnterpriseDTO blacklistEnterprise(int enterpriseID, String blacklistedReason);
}
