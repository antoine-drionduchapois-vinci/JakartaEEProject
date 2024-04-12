package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.SupervisorDAO;
import be.vinci.pae.domain.Supervisor;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.utils.NotFoundException;
import jakarta.inject.Inject;
import java.util.List;

/**
 * Implementation of the {@link SupervisorUCC} interface that handles operations
 * related to
 * responsibles. This class uses dependency injection to incorporate data access
 * objects (DAOs) for
 * interacting with the underlying data storage.
 */
public class SupervisorUCCImpl implements SupervisorUCC {

  @Inject
  private SupervisorDAO supervisorDAO;

  @Inject
  private DALService myDALService;

  @Override
  public List<SupervisorDTO> getAll() {
    myDALService.start();
    List<SupervisorDTO> supervisors = supervisorDAO.readAll();
    myDALService.commit();
    return supervisors;
  }

  /**
   * Retrieves detailed information about the responsible associated with a given
   * enterprise ID.
   * This method leverages the {@link SupervisorDAO} to query the database for the
   * responsible's
   * data based on the enterprise ID.
   *
   * @param id The unique identifier of the enterprise whose responsible is being
   *           queried. This ID
   *           should match an existing enterprise in the database.
   * @return A {@link Supervisor} object containing the responsible's detailed
   *         information if found.
   *         The DTO includes personal details, contact information, and other
   *         relevant data associated with
   *         the responsible.
   */
  @Override
  public SupervisorDTO getResponsibleByEnterpriseId(int id) {
    try {
      try {
      myDALService.start();
      SupervisorDTO supervisor = supervisorDAO.getResponsibleByEnterpriseId(id);
      if (supervisor == null) {
        throw new NotFoundException();
      }
      myDALService.commit();
      return supervisor;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

}
