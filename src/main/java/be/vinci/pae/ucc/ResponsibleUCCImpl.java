package be.vinci.pae.ucc;

import be.vinci.pae.dao.ResponsibleDAO;
import be.vinci.pae.domain.ResponsibleDTO;
import jakarta.inject.Inject;

/**
 * Implementation of the ResponsibleUCC interface.
 */
public class ResponsibleUCCImpl implements ResponsibleUCC {

  @Inject
  private ResponsibleDAO responsibleDAO;

  /**
   * Retrieves the responsible associated with the given enterprise ID.
   *
   * @param id The enterprise ID for which to retrieve the responsible.
   * @return The ResponsibleDTO object representing the responsible, or null if not found.
   */
  @Override
  public ResponsibleDTO getResponsibleByEnterpriseId(int id) {

    ResponsibleDTO responsible = responsibleDAO.getResponsibleByEnterpriseId(id);

    return responsible;
  }


}
