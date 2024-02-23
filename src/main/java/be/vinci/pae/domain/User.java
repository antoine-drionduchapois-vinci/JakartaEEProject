package be.vinci.pae.domain;

/**
 * Interface representing a user.
 */
public interface User {

  /**
   * Retrieves the unique identifier of the user.
   *
   * @return The user's ID.
   */
  int getId();

  /**
   * Retrieves the email address of the user.
   *
   * @return The user's email.
   */
  String getEmail();

  /**
   * Checks if the provided password matches the user's password.
   *
   * @param password The password to check.
   * @return true if the password matches, false otherwise.
   */
  boolean checkPassword(String password);

  /**
   * Hashes the provided password.
   *
   * @param password The password to hash.
   * @return The hashed password.
   */
  String hashPassword(String password);
}
