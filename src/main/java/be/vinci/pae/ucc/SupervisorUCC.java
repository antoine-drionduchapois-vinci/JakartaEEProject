package be.vinci.pae.ucc;

import be.vinci.pae.domain.SupervisorDTO;
import java.util.List;

/**
 * The ResponsibleUCC interface provides methods for managing
 * responsible-related functionality.
 */
public interface SupervisorUCC {

  List<SupervisorDTO> getAll();

  /**
   * Retrieves responsible of an enterprise.
   *
   * @param id The ID of the enterprise
   * @return A ResponsbileDTO object representing the user in JSON format, or null
   *         if no user is
   *         found for
   */
  SupervisorDTO getResponsibleByEnterpriseId(int id);

}
