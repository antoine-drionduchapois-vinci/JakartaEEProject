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

  @Override
  public ContactDTO create(String status, String year, int userId, int enterpriseId) {
    ContactDTO contact = myDomainFactory.getContactDTO();

    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.contacts (etat, annee, utilisateur, entreprise)"
            + "VALUES (?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, status);
      ps.setString(2, year);
      ps.setInt(3, userId);
      ps.setInt(4, enterpriseId);
      ps.execute();
      contact = convertRsToDTO(ps.getResultSet());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return contact;
  }

  @Override
  public ContactDTO readOne(int contactId) {
    ContactDTO contact = myDomainFactory.getContactDTO();

    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE contact_id = ?;")) {
      ps.setInt(1, contactId);
      ps.execute();
      contact = convertRsToDTO(ps.getResultSet());
    } catch (SQLException e) {
      System.err.println("PreparedStatement failed : " + e); // TODO: handle error
    }
    return contact;
  }

  @Override
  public ContactDTO readOne(int userId, int enterpriseId) {
    ContactDTO contact = myDomainFactory.getContactDTO();

    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE utilisateur = ? AND entreprise = ?;")) {
      ps.setInt(1, userId);
      ps.setInt(2, enterpriseId);
      ps.execute();
      contact = convertRsToDTO(ps.getResultSet());
    } catch (SQLException e) {
      System.err.println("PreparedStatement failed : " + e); // TODO: handle error
    }
    return contact;
  }

  @Override
  public ContactDTO update(ContactDTO newContact) {
    ContactDTO contact = myDomainFactory.getContactDTO();

    try (PreparedStatement ps = myDalService.getPS(
        "UPDATE projetae.contacts SET description = ?, etat = ?, motif_refus = ?,"
            + " annee = ?, utilisateur = ?, entreprise = ? WHERE contact_id = ? RETURNING *;")) {
      ps.setString(1, newContact.getDescription());
      ps.setString(2, newContact.getState());
      ps.setString(3, newContact.getReasonRefusal());
      ps.setString(4, newContact.getYear());
      ps.setInt(5, newContact.getUser());
      ps.setInt(6, newContact.getEntreprise());
      ps.setInt(7, newContact.getContactId());
      ps.execute();
      contact = convertRsToDTO(ps.getResultSet());
    } catch (SQLException e) {
      throw new RuntimeException(e); //TODO: handle error
    }

    return contact;
  }

  private ContactDTO convertRsToDTO(ResultSet rs) {
    ContactDTO contact = myDomainFactory.getContactDTO();
    try {
      if (!rs.next()) {
        return null;
      }
      contact.setContactId(rs.getInt("contact_id"));
      contact.setDescription(rs.getString("description"));
      contact.setState(rs.getString("etat"));
      contact.setReasonRefusal(rs.getString("motif_refus"));
      contact.setYear(rs.getString("annee"));
      contact.setUser(rs.getInt("utilisateur"));
      contact.setEntreprise(rs.getInt("entreprise"));
    } catch (SQLException e) {
      System.err.println(
          "conversion from ResultSet to DTO failed : " + e); // TODO: handle error
    }
    return contact;
  }
}
