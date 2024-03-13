package be.vinci.pae.api;

import be.vinci.pae.domain.Responsible;
import be.vinci.pae.domain.ResponsibleImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponsibleDAOImpl implements ResponsibleDAO {

  private DALService myDalService = new DALServiceImpl();

  /**
   * Retrieves the responible of the enterprise.
   *
   * @param id the ID of the enterprise
   * @return the user corresponding to the ID, or null if not found
   */
  @Override
  public Responsible getResponsibleByEnterpriseId(int id) {
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

      Responsible responsible = new ResponsibleImpl(responsibleId, name, surname, phone, email,
          enterprise);
      return responsible;
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null; // Handle the case where no user is found
  }
}
