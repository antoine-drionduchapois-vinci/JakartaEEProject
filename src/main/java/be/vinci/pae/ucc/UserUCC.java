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
}
