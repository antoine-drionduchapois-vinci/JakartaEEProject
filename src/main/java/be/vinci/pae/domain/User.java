package be.vinci.pae.domain;

/**
 * The User interface represents a user entity with various attributes and methods.
 */
public interface User {

  /**
   * Retrieves the user's unique identifier.
   *
   * @return The user's unique identifier.
   */
  int getUserId();

  /**
   * Sets the user's unique identifier.
   *
   * @param userId The user's unique identifier to set.
   */
  void setUserId(int userId);

  /**
   * Retrieves the user's name.
   *
   * @return The user's name.
   */
  String getName();

  /**
   * Sets the user's name.
   *
   * @param name The user's name to set.
   */
  void setName(String name);

  /**
   * Retrieves the user's surname.
   *
   * @return The user's surname.
   */
  String getSurname();

  /**
   * Sets the user's surname.
   *
   * @param surname The user's surname to set.
   */
  void setSurname(String surname);

  /**
   * Retrieves the user's email address.
   *
   * @return The user's email address.
   */
  String getEmail();

  /**
   * Sets the user's email address.
   *
   * @param email The user's email address to set.
   */
  void setEmail(String email);

  /**
   * Retrieves the user's phone number.
   *
   * @return The user's phone number.
   */
  String getPhone();

  /**
   * Sets the user's phone number.
   *
   * @param phone The user's phone number to set.
   */
  void setPhone(String phone);

  /**
   * Retrieves the user's password.
   *
   * @return The user's password.
   */
  String getPassword();

  /**
   * Sets the user's password.
   *
   * @param password The user's password to set.
   */
  void setPassword(String password);

  /**
   * Retrieves the user's year.
   *
   * @return The user's year.
   */
  String getYear();

  /**
   * Sets the user's year.
   *
   * @param year The user's year to set.
   */
  void setYear(String year);

  /**
   * Retrieves the role of the user.
   *
   * @return The role of the user.
   */
  Role getRole();

  /**
   * Sets the role of the user.
   *
   * @param role The role of the user to set.
   */
  void setRole(Role role);

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
   * Enumeration representing different roles a user can have.
   */
  enum Role {
    STUDENT("Student"), TEACHER("Teacher"), ADMIN("Admin");
    private String value;

    Role(String value) {
      this.value = value;
    }
  }
}