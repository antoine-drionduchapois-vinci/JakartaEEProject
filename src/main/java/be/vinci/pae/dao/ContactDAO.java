package be.vinci.pae.dao;

import be.vinci.pae.domain.Contact;
import java.util.List;

/**
 * The ContactDAO interface provides methods for accessing contact information from the database.
 */
public interface ContactDAO {

  /**
   * Creates a new contact with the provided status, year, user ID, and enterprise ID.
   *
   * @param status       The status of the contact.
   * @param year         The year of the contact.
   * @param userId       The ID of the user associated with the contact.
   * @param enterpriseId The ID of the enterprise associated with the contact.
   * @return The newly created Contact object.
   */
  Contact create(String status, String year, int userId, int enterpriseId);

  /**
   * Retrieves a list of contacts associated with the specified user ID.
   *
   * @param userId The ID of the user for whom to retrieve contacts.
   * @return A List containing Contact objects representing the contacts associated with the
   * specified user ID.
   */
  List<Contact> readMany(int userId);

  /**
   * Retrieves the contact information associated with the specified contact ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return The Contact object representing the contact information, or null if no contact with the given ID is found.
   */
  Contact readOne(int contactId);

  /**
   * Retrieves the contact information associated with the specified user ID and enterprise ID.
   *
   * @param userId       The ID of the user associated with the contact.
   * @param enterpriseId The ID of the enterprise associated with the contact.
   * @return The Contact object representing the contact information, or null if no contact with the given user ID and enterprise ID is found.
   */
  Contact readOne(int userId, int enterpriseId);

  /**
   * Updates the information of the specified contact.
   *
   * @param newContact The updated Contact object.
   * @return The updated Contact object.
   */
  Contact update(Contact newContact);
}
