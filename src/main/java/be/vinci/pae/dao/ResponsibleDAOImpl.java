package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.ResponsibleDTO;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementation of the Responsible Data Access Object.
 */
public class ResponsibleDAOImpl implements ResponsibleDAO {

  private DALService myDalService = new DALServiceImpl();

  @Inject
  private DomainFactory myDomainFactory;

  /**
   * Retrieves the responible of the enterprise.
   *
   * @param id the ID of the enterprise
   * @return the user corresponding to the ID, or null if not found
   */
  @Override
  public ResponsibleDTO getResponsibleByEnterpriseId(int id) {
    PreparedStatement ps = myDalService
        .getPS("SELECT * FROM projetae.responsables WHERE entreprise = ?");
    try {
      ps.setInt(1, id);
      ps.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try (ResultSet rs = ps.getResultSet()) {
      rs.next();
      int responsibleId = rs.getInt("responsable_id ");
      String name = rs.getString("nom");
      String surname = rs.getString("prenom");
      String phone = rs.getString("telephone");
      String email = rs.getString("email");
      int enterprise = rs.getInt("  entreprise");

      ResponsibleDTO responsibleDTO = myDomainFactory.getResponsibleDTO(responsibleId, name,
          surname, phone,
          email,
          enterprise);
      return responsibleDTO;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null; // Handle the case where no user is found
  }
}
