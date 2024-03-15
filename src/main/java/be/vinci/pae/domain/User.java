package be.vinci.pae.domain;

/**
 * The User interface represents a user entity with various attributes and methods.
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

  /**
   * Returns a string representation of the user.
   *
   * @return A string representation of the user, including the user's ID, name, surname, email,
   * phone, year, and role.
   */
  String toString();

  /**
   * Enumeration representing different roles a user can have.
   */
  enum Role {
    STUDENT("Student"), TEACHER("Teacher"), ADMIN("Admin");
    /**
     * Represents the value of a ROLE
     */
    private String value;

    Role(String value) {
      this.value = value;
    }
  }
}