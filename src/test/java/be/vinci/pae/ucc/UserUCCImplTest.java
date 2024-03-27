package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.UserDTO;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserUCCImplTest {

  private static UserUCC userUCC;
  private static UserDAO userDAO;
  private static ServiceLocator locator;

  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    userDAO = locator.getService(UserDAO.class);
    userUCC = locator.getService(UserUCC.class);
  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
    locator.shutdown();
  }


  @Test
  void countStudentsWithoutStage() {
    when(userDAO.getStudentsWithoutStage()).thenReturn(5);

    int result = userUCC.countStudentsWithoutStage();

    assertEquals(5, result);
    verify(userDAO, times(1)).getStudentsWithoutStage();
  }

  @Test
  void countStudents() {
    // Arrange
    when(userDAO.getTotalStudents()).thenReturn(10);

    // Act
    int result = userUCC.countStudents();

    // Assert
    assertEquals(10, result);
    verify(userDAO, times(1)).getTotalStudents();
  }

  @Test
  void getUsersAsJson() {
    // Arrange
    UserDTO user1 = mock(UserDTO.class);
    when(user1.getSurname()).thenReturn("John");
    when(user1.getName()).thenReturn("Doe");
    UserDTO user2 = mock(UserDTO.class);
    when(user2.getSurname()).thenReturn("Jane");
    when(user2.getName()).thenReturn("Smith");
    List<UserDTO> userList = new ArrayList<>();
    userList.add(user1);
    userList.add(user2);
    when(userDAO.getAllStudents()).thenReturn(userList);

    // Act
    List<UserDTO> result = userUCC.getUsersAsJson();

    // Assert
    assertEquals(userList, result);
    verify(userDAO, times(1)).getAllStudents();
  }

  @Test
  void getUsersByIdAsJson() {
    // Arrange
    int userId = 123;
    UserDTO user = mock(UserDTO.class);
    when(user.getSurname()).thenReturn("John");
    when(user.getName()).thenReturn("Doe");
    when(userDAO.getOneByID(userId)).thenReturn(user);

    // Act
    UserDTO result = userUCC.getUsersByIdAsJson(userId);

    // Assert
    assertEquals(user, result);
    verify(userDAO, times(1)).getOneByID(userId);
  }
}