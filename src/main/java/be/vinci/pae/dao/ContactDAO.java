package be.vinci.pae.dao;

import be.vinci.pae.domain.ContactDTO;
import java.util.List;

/**
 * The ContactDAO interface provides methods for accessing contact information from the database.
 */
public interface ContactDAO {

  /**
   * Retrieves a list of contact information for all users associated with the specified ID.
   *
   * @param id The ID of the user for whom to retrieve contact information.
   * @return A List containing ContactDTO objects representing the contact information of users
   *         associated with the specified ID.
   */
  List<ContactDTO> getAllUsersContact(int id);

  /**
   * Creates a new contact with the given status, year, user ID, and enterprise ID.
   *
   * @param status       The status of the contact.
   * @param year         The year of the contact.
   * @param userId       The ID of the user associated with the contact.
   * @param enterpriseId The ID of the enterprise associated with the contact.
   * @return A ContactDTO object representing the newly created contact.
   */
  ContactDTO create(String status, String year, int userId, int enterpriseId);

  /**
   * Retrieves the contact information associated with the specified contact ID.
   *
   * @param contactId The ID of the contact to retrieve.
   * @return A ContactDTO object representing the contact information, or null if no contact is
   *         found for the given contact ID.
   */
  ContactDTO readOne(int contactId);

  /**
   * Retrieves the contact information associated with the specified user ID and enterprise ID.
   *
   * @param userId       The ID of the user.
   * @param enterpriseId The ID of the enterprise.
   * @return A ContactDTO object representing the contact information, or null if no contact is
   *         found for the given user ID and enterprise ID.
   */
  ContactDTO readOne(int userId, int enterpriseId);
}
