package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.EnterpriseDTOImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the implementation of the EnterpriseDAO interface.
 */
public class EnterpriseDAOImpl implements EnterpriseDAO {

  private DALService myDalService = new DALServiceImpl();

  @Inject
  private DomainFactory myDomainFactory;

  @Override
  public EnterpriseDTO readOne(int enterpriseId) {
    EnterpriseDTO enterprise = myDomainFactory.getEnterpriseDTO();

    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.entreprises WHERE entreprise_id = ?;")) {
      ps.setInt(1, enterpriseId);
      ps.execute();
      enterprise = convertRsToDTO(ps.getResultSet());
    } catch (SQLException e) {
      System.err.println(e); // TODO: handle error
    }
    return enterprise;
  }

  @Override
  public EnterpriseDTO create(String name, String label, String adress, String contact) {
    EnterpriseDTO enterprise = myDomainFactory.getEnterpriseDTO();

    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.entreprises (nom, appellation, adresse, telephone)"
            + "VALUES (?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, name);
      ps.setString(2, label);
      ps.setString(3, adress);
      ps.setString(4, contact);
      ps.execute();
      enterprise = convertRsToDTO(ps.getResultSet());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return enterprise;
  }

  @Override
  public List<EnterpriseDTO> getAllEnterprises() {
    List<EnterpriseDTO> enterprises = new ArrayList<>();

    try (PreparedStatement ps = myDalService
        .getPS("SELECT * FROM projetae.entreprises");
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        int entrepriseId = rs.getInt("entreprise_id");
        String nom = rs.getString("nom");
        String appellation = rs.getString("appellation");
        String addresse = rs.getString("adresse");
        String telephone = rs.getString("telephone");
        boolean isBlacklist = rs.getBoolean("is_blacklist");
        String avisProfesseur = rs.getString("avis_professeur");

        // Create a new EnterpriseImpl object and add it to the list
        EnterpriseDTO enterpriseDTO = myDomainFactory.getEnterpriseDTO(entrepriseId, nom,
            appellation, addresse,
            telephone, isBlacklist, avisProfesseur);
        enterprises.add(enterpriseDTO);
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
  public EnterpriseDTO getEnterpriseById(int id) {
    PreparedStatement ps = myDalService
        .getPS(
            "SELECT * FROM projetae.entreprises e,"
                + " projetae.stages s WHERE s.entreprise = e.entreprise_id"
                + " AND s.utilisateur = ?");
    try {
      ps.setInt(1, id);
      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        // Check if ResultSet has data
        if (rs.next()) {
          int entrepriseId = rs.getInt("entreprise_id");
          String nom = rs.getString("nom");
          String appellation = rs.getString("appellation");
          String addresse = rs.getString("adresse");
          String telephone = rs.getString("telephone");
          boolean isBlacklist = rs.getBoolean("is_blacklist");
          String avisProfesseur = rs.getString("avis_professeur");

          EnterpriseDTO enterpriseDTO = new EnterpriseDTOImpl(entrepriseId, nom, appellation,
              addresse,
              telephone, isBlacklist, avisProfesseur);
          if (enterpriseDTO == null) {
            System.out.println("Entreprise is NULL");
          }
          System.out.println("entreprise : ");
          System.out.println(enterpriseDTO);
          return enterpriseDTO;
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

  private EnterpriseDTO convertRsToDTO(ResultSet rs) {
    EnterpriseDTO enterprise = myDomainFactory.getEnterpriseDTO();
    try {
      if (!rs.next()) {
        return null;
      }
      enterprise.setEntrepriseId(rs.getInt("entreprise_id"));
      enterprise.setNom(rs.getString("nom"));
      enterprise.setAppellation(rs.getString("appellation"));
      enterprise.setAdresse(rs.getString("adresse"));
      enterprise.setTelephone(rs.getString("telephone"));
      enterprise.setBlacklist(rs.getBoolean("is_blacklist"));
      enterprise.setAvisProfesseur("avis_professeur");
    } catch (SQLException e) {
      System.err.println(
          "conversion from ResultSet to DTO failed : " + e); // TODO: handle error
    }
    return enterprise;
  }
}
