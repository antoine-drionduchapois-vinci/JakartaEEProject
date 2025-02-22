package be.vinci.pae.ucc;

import be.vinci.pae.domain.UserDTO;
import java.util.List;

/**
 * The UserUCC interface provides methods for managing user-related functionality.
 */
public interface UserUCC {

  /**
   * Retrieves global statistics related to users.
   *
   * @return An integer representing the global statistics related to users.
   */
  int countStudentsWithoutStage();

  /**
   * Retrieves count student.
   *
   * @return An integer representing the global statistics related to users.
   */
  int countStudents();

  /**
   * Retrieves a list of users in JSON format.
   *
   * @return A List containing UserDTO objects representing users in JSON format.
   */
  List<UserDTO> getUsersAsJson();

  /**
   * Retrieves a specific user in JSON format based on the provided user ID.
   *
   * @param userId The ID of the user to retrieve in JSON format.
   * @return A UserDTO object representing the user in JSON format, or null if no user is found for
   *         the given user ID.
   */
  UserDTO getUsersByIdAsJson(int userId);

  /**
   * Modifies the password of the user.
   *
   * @param userDTO The UserDTO object containing user information.
   * @param newMdp  The new password to be set for the user.
   * @return The modified UserDTO object with updated password.
   */
  UserDTO modifyPassword(UserDTO userDTO, String newMdp);

  /**
   * Changes the phone number of the user.
   *
   * @param userDTO The UserDTO object containing user information.
   * @return The modified UserDTO object with updated phone number.
   */
  UserDTO changePhoneNumber(UserDTO userDTO);
}
