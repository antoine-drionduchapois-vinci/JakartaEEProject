package be.vinci.pae.ucc;

import be.vinci.pae.dao.ResponsibleDAO;
import be.vinci.pae.domain.ResponsibleDTO;
import jakarta.inject.Inject;

/**
 * Implementation of the {@link ResponsibleUCC} interface that handles operations related to
 * responsibles. This class uses dependency injection to incorporate data access objects (DAOs) for
 * interacting with the underlying data storage.
 */
public class ResponsibleUCCImpl implements ResponsibleUCC {

  @Inject
  private ResponsibleDAO responsibleDAO;

  /**
   * Retrieves detailed information about the responsible associated with a given enterprise ID.
   * This method leverages the {@link ResponsibleDAO} to query the database for the responsible's
   * data based on the enterprise ID.
   *
   * @param id The unique identifier of the enterprise whose responsible is being queried. This ID
   *           should match an existing enterprise in the database.
   * @return A {@link ResponsibleDTO} object containing the responsible's detailed information if
   * found. The DTO includes personal details, contact information, and other relevant data
   * associated with the responsible.
   */
  @Override
  public ResponsibleDTO getResponsibleByEnterpriseId(int id) {
    ResponsibleDTO responsible = responsibleDAO.getResponsibleByEnterpriseId(id);
    return responsible;
  }

}
