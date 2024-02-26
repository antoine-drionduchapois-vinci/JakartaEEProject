package be.vinci.pae.services;

import be.vinci.pae.domain.User;
import com.fasterxml.jackson.databind.node.ObjectNode;


/**
 * Interface for managing user data.
 */
public interface UserDataService {

  /**
   * Retrieves a user by email.
   *
   * @param email The email of the user to retrieve.
   * @return The user object if found, otherwise null.
   */
  User getOne(String email);

  /**
   * Retrieves a user by id.
   *
   * @param id The id of the user to retrieve.
   * @return The user object if found, otherwise null.
   */
  User getOne(int id);

  /**
   * Validates user login credentials.
   *
   * @param email    The email of the user.
   * @param password The password of the user.
   * @return A JSON object representing the public user information along with a JWT token if login
   * is successful, otherwise null.
   */
  ObjectNode login(String email, String password);
}