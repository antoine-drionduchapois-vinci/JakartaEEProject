package be.vinci.pae.dao;

import be.vinci.pae.domain.ContactDTO;
import java.util.List;

/**
 * The ContactDAO interface provides methods for accessing contact information from the database.
 */
public interface ContactDAO {

  /**
   * Retrieves a list of contacts associated with the specified user ID.
   *
   * @param userId The ID of the user for whom to retrieve contacts.
   * @return A List containing Contact objects representing the contacts associated with the
   * specified user ID.
   */
  List<ContactDTO> readMany(int userId);

  /**
   * Retrieves the contact information associated with the specified contact ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return The Contact object representing the contact information, or null if no contact with the
   * given ID is found.
   */
  ContactDTO readOne(int contactId);

  /**
   * Retrieves the contact information associated with the specified user ID and enterprise ID.
   *
   * @param userId       The ID of the user associated with the contact.
   * @param enterpriseId The ID of the enterprise associated with the contact.
   * @return The Contact object representing the contact information, or null if no contact with the
   * given user ID and enterprise ID is found.
   */
  ContactDTO readOne(int userId, int enterpriseId);

  /**
   * Creates a new contact between a user and an enterprise.
   *
   * @param userId       The ID of the user.
   * @param enterpriseId The ID of the enterprise.
   * @return The newly created contact.
   */
  ContactDTO create(int userId, int enterpriseId);

  /**
   * Updates the information of the specified contact.
   *
   * @param newContactDTO The updated Contact object.
   * @return The updated Contact object.
   */
  ContactDTO update(ContactDTO newContactDTO);


  /**
   * Retrieves a list of contacts associated with the specified enterprise.
   *
   * @param enterpriseId The ID of the enterprise for whom to retrieve contacts.
   * @return A List containing Contact objects representing the contacts associated with the
   * specified enterprise ID.
   */
  List<ContactDTO> readEnterpriseContacts(int enterpriseId);


  /**
   * Retrieves a list of "initiated" or "meet" contacts associated with the specified company.
   *
   * @param enterpriseId The ID of the enterprise for which the contacts are to be retrieved.
   * @return A List containing Contact objects representing the contacts "initiated" or "meet"
   * associated with the specified enterprise ID.
   */
  List<ContactDTO> readEnterpriseInitiatedOrMeetContacts(int enterpriseId);

}
