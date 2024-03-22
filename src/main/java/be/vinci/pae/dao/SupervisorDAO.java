package be.vinci.pae.dao;

import be.vinci.pae.domain.Supervisor;

/**
 * The ResponsibleDAO interface provides methods for accessing responsible
 * information from the
 * database.
 */
public interface SupervisorDAO {

  /**
   * Retrieves the responsible information associated with the enterprise ID.
   *
   * @param id The ID of the enterprise for which to retrieve the responsible
   *           information.
   * @return A ResponsibleDTO object representing the responsible information
   *         associated with the
   *         enterprise ID, or null if no responsible information is found for the
   *         given enterprise ID.
   */
  Supervisor getResponsibleByEnterpriseId(int id);
}
