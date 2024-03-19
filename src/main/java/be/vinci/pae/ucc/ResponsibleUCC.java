package be.vinci.pae.ucc;

import be.vinci.pae.domain.ResponsibleDTO;

/**
 * The ResponsibleUCC interface provides methods for managing responsible-related functionality.
 */
public interface ResponsibleUCC {

  /**
   * Retrieves responsible of an enterprise.
   *
   * @param id The ID of the enterprise
   * @return A ResponsbileDTO object representing the user in JSON format, or null if no user is found for
   */
  ResponsibleDTO getResponsibleByEnterpriseId(int id);

}
