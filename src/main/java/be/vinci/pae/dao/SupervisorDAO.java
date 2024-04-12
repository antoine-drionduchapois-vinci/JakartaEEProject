package be.vinci.pae.dao;

import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.utils.FatalErrorException;

import java.util.List;

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
  SupervisorDTO getResponsibleByEnterpriseId(int id);

  /**
   * Creates a new supervisor entry in the database.
   *
   * @param supervisor The supervisor object to be created.
   * @return The created supervisor object.
   */
  SupervisorDTO create(SupervisorDTO supervisor);

  /**
   * Retrieves a single supervisor from the database based on the supervisor ID.
   *
   * @param supervisorId The ID of the supervisor to retrieve.
   * @return The supervisor object corresponding to the given ID.
   */
  SupervisorDTO readOne(int supervisorId);

  /**
   * Retrieves all supervisors from the database.
   *
   * @return A list containing all supervisor objects stored in the database.
   */
  List<SupervisorDTO> readAll();
}
