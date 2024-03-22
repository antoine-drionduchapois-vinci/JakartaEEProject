package be.vinci.pae.dao;

import be.vinci.pae.domain.User;
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
  User getOneByEmail(String email);

  /**
   * Retrieves a user by their ID.
   *
   * @param id the ID of the user to retrieve
   * @return a UserDTO object representing the user with the specified ID
   */
  User getOneByID(int id);

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
  List<User> getAllStudents();

  /**
   * Retrieves the number of students without a stage.
   *
   * @return the number of students without a stage
   */
  int getStudentsWithoutStage();

  /**
   * Adds a new user to the database.
   *
   * @param user the User object representing the user to be added
   * @return the UserDTO object representing the added user
   */
  User addUser(User user);

}
