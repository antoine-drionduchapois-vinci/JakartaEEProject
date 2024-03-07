package be.vinci.pae.api;

import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;

/**
 * Represents a Data Access Object (DAO) interface for interacting with user data.
 */
public interface UserDAO {

  /**
   * Retrieves a user by their email address.
   *
   * @param email the email address of the user to retrieve
   * @return the user corresponding to the email address, or null if not found
   */
  UserDTO getOneByEmail(String email);

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return the user corresponding to the ID, or null if not found
   */
  UserDTO getOneByID(int id);

  /**
   * Adds a new user to the database.
   *
   * @param user the User object representing the user to be added
   * @return the UserDTO object representing the added user
   */
  UserDTO addUser(User user);
}
