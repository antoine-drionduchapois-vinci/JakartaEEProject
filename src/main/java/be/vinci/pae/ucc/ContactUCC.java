package be.vinci.pae.ucc;

import be.vinci.pae.domain.ContactDTO;
import java.util.List;

/**
 * The ContactUCC interface provides methods for managing contact-related functionality.
 */
public interface ContactUCC {

  /**
   * Authenticates a user with the provided email and password.
   *
   * @param id The id of the user.
   * @return A List containing ContactDTO objects representing all contact stored in the
   *         system related to the user.
   */
  List<ContactDTO> getAllUsersContact(int id);
}
