package be.vinci.pae.dao;

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
        Enterprise enterprise = new EnterpriseImpl(entrepriseId, nom, appellation, adresse,
            telephone, isBlacklist, avisProfesseur);
        enterprises.add(enterprise);
      }
    } catch (SQLException e) {
      // Print the stack trace if an SQLException occurs
      e.printStackTrace();
    }

    return enterprises;
  }


  /**
   * Retrieves the entrprise that corresponds to the users internship.
   *
   * @return enterprises.
   */
  @Override
  public Enterprise getEnterpriseById(int id) {
    PreparedStatement ps = myDalService
        .getPS(
            "SELECT * FROM projetae.entreprises e, projetae.stages s WHERE s.entreprise = e.entreprise_id AND s.utilisateur = ?");
    try {
      ps.setInt(1, id);
      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        // Check if ResultSet has data
        if (rs.next()) {
          int entrepriseId = rs.getInt("entreprise_id");
          String nom = rs.getString("nom");
          String appellation = rs.getString("appellation");
          String adresse = rs.getString("adresse");
          String telephone = rs.getString("telephone");
          boolean isBlacklist = rs.getBoolean("is_blacklist");
          String avisProfesseur = rs.getString("avis_professeur");

          Enterprise enterprise = new EnterpriseImpl(entrepriseId, nom, appellation, adresse,
              telephone, isBlacklist, avisProfesseur);
          if (enterprise == null) {
            System.out.println("Entreprise is NULL");
          }
          System.out.println("entreprise : ");
          System.out.println(enterprise);
          return enterprise;
        } else {
          // Handle the case where no enterprise is found for the user
          System.out.println("RS.next == NULL");
          return null;
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    // If there's an exception or no data, return null
    return null;
  }

}
