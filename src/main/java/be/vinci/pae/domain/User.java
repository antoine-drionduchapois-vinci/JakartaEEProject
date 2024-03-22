package be.vinci.pae.domain;

/**
 * Interface representing a user entity.
 */
public interface User extends UserDTO {

  /**
   * Checks if the provided password matches the user's password.
   *
   * @param password The password to check.
   * @return True if the provided password matches the user's password, false otherwise.
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
