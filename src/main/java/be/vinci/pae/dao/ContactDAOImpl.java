package be.vinci.pae.dao;

import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the implementation of the ContactDAO interface.
 */
public class ContactDAOImpl implements ContactDAO {

  private DALService myDalService = new DALServiceImpl();

  @Inject
  private DomainFactory myDomainFactory;

  @Override
  public List<ContactDTO> getAllUsersContact(int id) {
    List<ContactDTO> contactDTOS = new ArrayList<>();

    String query = "SELECT * FROM projetae.contacts WHERE utilisateur = ?";
    try (PreparedStatement ps = myDalService.getPS(query)) {
      ps.setInt(1, id);
      ps.execute();

      try (ResultSet rs = ps.getResultSet()) {
        while (rs.next()) {
          int contactId = rs.getInt("contact_id");
          String description = rs.getString("description");
          String state = rs.getString("etat");
          String reasonRefusal = rs.getString("motif_refus");
          String year = rs.getString("annee");
          int user = rs.getInt("utilisateur");
          int enterprise = rs.getInt("entreprise");

          ContactDTO contactDTO = myDomainFactory.getContactDTO(contactId, description, state,
              reasonRefusal,
              year,
              user, enterprise);
          contactDTOS.add(contactDTO);
        }
      }
    } catch (SQLException e) {
      // Proper error handling
      throw new RuntimeException(e);
    }

    return contactDTOS;
  }


}
