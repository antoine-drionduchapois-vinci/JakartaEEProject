package be.vinci.pae.api;

import be.vinci.pae.domain.Responsible;

/**
 * Represents a Data Access Object (DAO) for Responsible entities.
 */
public interface ResponsibleDAO {

  /**
   * Retrieves the responible of the enterprise.
   *
   * @param id the ID of the enterprise
   * @return the user corresponding to the ID, or null if not found
   */
  Responsible getResponsibleByEnterpriseId(int id);
}
