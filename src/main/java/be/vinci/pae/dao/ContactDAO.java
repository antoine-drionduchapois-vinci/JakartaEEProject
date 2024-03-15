package be.vinci.pae.dao;

import be.vinci.pae.domain.ContactDTO;
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
  List<ContactDTO> getAllUsersContact(int id);
}
