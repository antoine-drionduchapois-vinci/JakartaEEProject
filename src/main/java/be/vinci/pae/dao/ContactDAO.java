package be.vinci.pae.dao;

import be.vinci.pae.domain.ContactDTO;
import java.util.List;

/**
 * The ContactDAO interface provides methods for accessing contact information
 * from the database.
 */
public interface ContactDAO {

  /**
   * Retrieves a list of contact information for all users associated with the
   * specified ID.
   *
   * @param id The ID of the user for whom to retrieve contact information.
   * @return A List containing ContactDTO objects representing the contact
   *         information of users
   *         associated with the specified ID.
   */
  List<ContactDTO> getAllUsersContact(int id);
}
