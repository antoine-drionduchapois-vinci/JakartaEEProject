package be.vinci.pae.dao;

import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.ContactImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the contactDao.
 */
public class ContactDAOImpl implements ContactDAO {

  /**
   * Implementation of the Contact Data Access Object.
   */
  private DALService myDalService = new DALServiceImpl();

  @Override
  public List<Contact> getAllUsersContact(int id) {
    List<Contact> contacts = new ArrayList<>();

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

          Contact contact = new ContactImpl(contactId, description, state, reasonRefusal, year,
              user, enterprise);
          contacts.add(contact);
        }
      }
    } catch (SQLException e) {
      // Proper error handling
      throw new RuntimeException(e);
    }

    return contacts;
  }


}
