package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.NotFoundException;
import be.vinci.pae.utils.TestBinder;
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
  private static DomainFactory domainFactory;
  private static ServiceLocator locator;

  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    userDAO = locator.getService(UserDAO.class);
    userUCC = locator.getService(UserUCC.class);
    domainFactory = locator.getService(DomainFactory.class);
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
    UserDTO user1 = domainFactory.getUser();
    user1.setSurname("John");
    user1.setName("Doe");
    UserDTO user2 = domainFactory.getUser();
    user2.setSurname("Jane");
    user2.setName("Smith");
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
    UserDTO user = domainFactory.getUser();
    user.setSurname("John");
    user.setName("Doe");
    when(userDAO.getOneByID(userId)).thenReturn(user);

    // Act
    UserDTO result = userUCC.getUsersByIdAsJson(userId);

    // Assert
    assertEquals(user, result);
    verify(userDAO, times(1)).getOneByID(userId);
  }

  @Test
  void testGetUsersByIdAsJsonWithNoCorrespondingUser() {
    when(userDAO.getOneByID(1)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      userUCC.getUsersByIdAsJson(1);
    });
  }

  @Test
  void testModifyPassword() {
    // Arrange
    UserDTO user1 = domainFactory.getUser();
    user1.setSurname("John");
    user1.setName("Doe");
    user1.setPassword("password");
    String newPassword = "newPassword";
    UserDTO user2 = user1;
    user2.setPassword(newPassword);

    when(userDAO.modifyPassword(user1)).thenReturn(user2);

    // Act
    UserDTO result = userUCC.modifyPassword(user1, newPassword);

    // Assert
    assertEquals(user2, result);
    verify(userDAO, times(1)).modifyPassword(user1);
  }

  @Test
  void testChangePhoneNumber() {
    // Arrange
    UserDTO user1 = domainFactory.getUser();
    user1.setSurname("John");
    user1.setName("Doe");
    user1.setPhone("0484754512");
    UserDTO user2 = user1;
    user2.setPhone("0484000001");

    when(userDAO.changePhoneNumber(user1)).thenReturn(user2);

    // Act
    UserDTO result = userUCC.changePhoneNumber(user1);

    // Assert
    assertEquals(user2, result);
    verify(userDAO, times(1)).changePhoneNumber(user1);
  }
}