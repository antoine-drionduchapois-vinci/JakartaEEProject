package be.vinci.pae.api;

import be.vinci.pae.domain.Contact;
import java.util.List;

/**
 * Interface defining the contract for contacts Data Access Object.
 */
public interface ContactDAO {

  /**
   * Retrieves all contacts that the user has.
   *
   * @return A list of all contacts
   */
  List<Contact> getAllUsersContact(int id);
}
