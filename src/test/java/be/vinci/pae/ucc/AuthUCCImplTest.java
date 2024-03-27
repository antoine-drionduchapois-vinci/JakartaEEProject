package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.domain.UserDTO;
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
    User userDTO = domainFactory.getUser();
    User user = (User) userDTO;
    userDTO.setEmail("test@example.com");
    userDTO.setPassword("$2a$10$jifOCQv6CquRzCTsQYNLBeOetkXV52AIjKyi2tiOfBzNeibFGENK");
    System.out.println(user + " " + userDTO);

    UserDTO userTemp = domainFactory.getUser();
    userTemp.setPassword("$2a$10$jifOCQv6CquRzCTsQYNLBeOetkXV52AIjKyi2tiOfBzNeibFGENK");

    // Configurer le comportement du mock userDAO
    when(userDAO.getOneByEmail("test@example.com")).thenReturn(userDTO);

    // Appeler la méthode à tester
    UserDTO result = authUCC.login(userTemp);
    System.out.println(user.getPassword() + " " + userTemp.getPassword());
    // Vérifier le résultat
    assertEquals(user.getPassword(), userTemp.getPassword());
    assertTrue(user.checkPassword(userTemp.getPassword()));
  }

  @Test
  void testRegister() {
    // Créer un utilisateur de test
    UserDTO userDTO = domainFactory.getUser();
    userDTO.setEmail("test@example.com");
    userDTO.setPassword("password123");

    // Configurer le comportement du mock userDAO
    when(userDAO.addUser(userDTO)).thenReturn(userDTO);

    // Appeler la méthode à tester
    UserDTO result = authUCC.register(userDTO);

    // Vérifier le résultat
    assertEquals(userDTO, result);
  }
}