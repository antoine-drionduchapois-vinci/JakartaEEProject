package be.vinci.pae.api;

import be.vinci.pae.domain.UserDTO;

/**
 * Represents a Data Access Object (DAO) for User entities.
 */
public interface UserDAO {

  /**
   * Retrieves a user by their email.
   *
   * @param email the email of the user to retrieve
   * @return a UserDTO object representing the user with the specified email
   */
  UserDTO getOneByEmail(String email);

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return a UserDTO object representing the user with the specified ID
   */
  UserDTO getOneByID(int id);

  /**
   * Retrieves the total number of students.
   *
   * @return the total number of students
   */
  int getTotalStudents();

  /**
   * Retrieves the number of students without a stage.
   *
   * @return the number of students without a stage
   */
  int getStudentsWithoutStage();
}
