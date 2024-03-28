package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.NotFoundException;
import be.vinci.pae.utils.TestBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AuthUCCImplTest {

  private static AuthUCC authUCC;
  private static UserDAO userDAO;
  private static DomainFactory domainFactory;
  private static ServiceLocator locator;


  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    userDAO = locator.getService(UserDAO.class);
    authUCC = locator.getService(AuthUCC.class);
    domainFactory = locator.getService(DomainFactory.class);

  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
    locator.shutdown();
  }

  @Test
  void testLogin() {
    // Créer un utilisateur de test
    UserDTO userDTO = domainFactory.getUser();
    userDTO.setEmail("test@example.com");
    userDTO.setPassword("password");
    System.out.println("test userDTO password" + userDTO.getPassword());

    UserDTO userTemp = domainFactory.getUser();
    userTemp.setPassword("password");
    userTemp.setEmail("test@example.com");

    User user = (User) userDTO;
    user.hashPassword(userDTO.getPassword());

    // Configurer le comportement du mock userDAO pour retourner userDTO
    when(userDAO.getOneByEmail("test@example.com")).thenReturn(userDTO);

    // Appeler la méthode à tester
    UserDTO result = authUCC.login(userTemp);

    System.out.println(result);
    // Vérifier le résultat
    assertNotNull(result);
    assertEquals(userTemp.getEmail(), result.getEmail());
  }

  @Test
  void testLoginWithUserNotFound() {
    UserDTO userDTO = domainFactory.getUser();
    userDTO.setEmail("test@example.com");
    userDTO.setPassword("password123");
    when(userDAO.getOneByEmail("test@example.com")).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      authUCC.login(userDTO);
    });

  }

  @Test
  void testLoginWithIncorrectPassword() {
    UserDTO userDTO = domainFactory.getUser();
    userDTO.setEmail("test@example.com");
    userDTO.setPassword("password");
    System.out.println("test userDTO password" + userDTO.getPassword());

    UserDTO userTemp = domainFactory.getUser();
    userTemp.setPassword("passwordIncorrect");
    userTemp.setEmail("test@example.com");

    User user = (User) userDTO;
    user.hashPassword(userDTO.getPassword());

    when(userDAO.getOneByEmail("test@example.com")).thenReturn(userDTO);

    BusinessException exception = assertThrows(BusinessException.class, () -> {
      authUCC.login(userTemp);
    });

  }
  
  @Test
  void testRegister() {
    UserDTO userDTO = domainFactory.getUser();
    userDTO.setEmail("test@example.com");
    userDTO.setPassword("password123");

    when(userDAO.getOneByEmail("test@example.com")).thenReturn(null);
    when(userDAO.addUser(userDTO)).thenReturn(userDTO);

    UserDTO result = authUCC.register(userDTO);

    assertEquals(userDTO, result);
  }

  @Test
  void testRegisterWithExistingUser() {
    UserDTO existingUserDTO = domainFactory.getUser();
    existingUserDTO.setEmail("test@example.com");

    when(userDAO.getOneByEmail("test@example.com")).thenReturn(existingUserDTO);

    assertThrows(BusinessException.class, () -> {
      authUCC.register(existingUserDTO);
    });

  }
}