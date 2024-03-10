package be.vinci.pae.api;

import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.domain.EnterpriseImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Enterprise Data Access Object.
 */
public class EnterpriseDAOImpl implements EnterpriseDAO {

  private DALService myDalService = new DALServiceImpl();

  /**
   * Retrieves all enterprises from the database.
   *
   * @return A list of all enterprises.
   */
  @Override
  public List<Enterprise> getAllEnterprises() {
    List<Enterprise> enterprises = new ArrayList<>();

    try (PreparedStatement ps = myDalService
        .getPS("SELECT * FROM projetae.entreprises");
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        int entrepriseId = rs.getInt("entreprise_id");
        String nom = rs.getString("nom");
        String appellation = rs.getString("appellation");
        String adresse = rs.getString("adresse");
        String telephone = rs.getString("telephone");
        boolean isBlacklist = rs.getBoolean("is_blacklist");
        String avisProfesseur = rs.getString("avis_professeur");

        // Create a new EnterpriseImpl object and add it to the list
        Enterprise enterprise = new EnterpriseImpl(entrepriseId, nom, appellation, adresse, telephone, isBlacklist, avisProfesseur);
        enterprises.add(enterprise);
      }
    } catch (SQLException e) {
      // Print the stack trace if an SQLException occurs
      e.printStackTrace();
    }

    return enterprises;
  }
}
