package be.vinci.pae.dao;

import be.vinci.pae.domain.UserDTO;
import java.util.List;

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
   * Retrieves all of students.
   *
   * @return list of students
   */
  List<UserDTO> getAllStudents();

  /**
   * Retrieves the number of students without a stage.
   *
   * @return the number of students without a stage
   */
  int getStudentsWithoutStage();

  /**
   * Adds a new user to the database.
   *
   * @param userDTO the User object representing the user to be added
   * @return the UserDTO object representing the added user
   */
  UserDTO addUser(UserDTO userDTO);

  UserDTO modifyPassword(UserDTO userDTO);

}
