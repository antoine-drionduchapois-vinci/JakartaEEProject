package be.vinci.pae.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * The UserDTO interface represents user informations.
 */
@JsonDeserialize(as = UserImpl.class)
public interface UserDTO {

  /**
   * Gets the ID of the user.
   *
   * @return The ID of the user.
   */
  int getUserId();

  /**
   * Sets the ID of the user.
   *
   * @param userId The ID of the user to set.
   */
  void setUserId(int userId);

  /**
   * Gets the name of the user.
   *
   * @return The name of the user.
   */
  String getName();

  /**
   * Sets the name of the user.
   *
   * @param name The name of the user to set.
   */
  void setName(String name);

  /**
   * Gets the surname of the user.
   *
   * @return The surname of the user.
   */
  String getSurname();

  /**
   * Sets the surname of the user.
   *
   * @param surname The surname of the user to set.
   */
  void setSurname(String surname);

  /**
   * Gets the email address of the user.
   *
   * @return The email address of the user.
   */
  String getEmail();

  /**
   * Sets the email address of the user.
   *
   * @param email The email address of the user to set.
   */
  void setEmail(String email);

  /**
   * Gets the phone number of the user.
   *
   * @return The phone number of the user.
   */
  String getPhone();

  /**
   * Sets the phone number of the user.
   *
   * @param phone The phone number of the user to set.
   */
  void setPhone(String phone);

  /**
   * Gets the password of the user.
   *
   * @return The password of the user.
   */
  String getPassword();

  /**
   * Sets the password of the user.
   *
   * @param password The password of the user to set.
   */
  void setPassword(String password);

  /**
   * Gets the year associated with the user.
   *
   * @return The year associated with the user.
   */
  String getYear();

  /**
   * Sets the year associated with the user.
   *
   * @param year The year associated with the user to set.
   */
  void setYear(String year);

  /**
   * Gets the role of the user.
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
   * Enum representing the roles that a user can have.
   */
  enum Role {
    /**
     * Student role.
     */
    STUDENT,
    /**
     * Teacher role.
     */
    TEACHER,
    /**
     * Admin role.
     */
    ADMIN
  }
}
