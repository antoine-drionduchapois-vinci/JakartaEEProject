package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.domain.User;
import be.vinci.pae.utils.Config;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthUCCImplTest {

  private AuthUCC authUCC;
  private UserDAO userDAO;
  private ObjectMapper objectMapper = new ObjectMapper();

  static {
    Config.load("dev.properties");
  }

  @BeforeEach
  void setUp() {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.userDAO = locator.getService(UserDAO.class);
    this.authUCC = locator.getService(AuthUCC.class);
  }

  public static User createMockUser(int userId, String name, String surname, String email,
      String phone, String password, User.Role role) {
    User userMock = mock(User.class);
    when(userMock.getUserId()).thenReturn(userId);
    when(userMock.getName()).thenReturn(name);
    when(userMock.getSurname()).thenReturn(surname);
    when(userMock.getEmail()).thenReturn(email);
    when(userMock.getPhone()).thenReturn(phone);
    when(userMock.getPassword()).thenReturn(password);
    when(userMock.getRole()).thenReturn(role);
    return userMock;
  }

  @Test
  void login_ValidCredentials_ReturnsTokenAndUserInfo() {
    String email = "test@example.com";
    String password = "password";

    User user = createMockUser(1, "John", "Doe", email, "1234567890", password, User.Role.STUDENT);
    when(userDAO.getOneByEmail(email)).thenReturn(user);
    when(user.checkPassword(password)).thenReturn(true);

    Algorithm algorithm = Algorithm.HMAC256("bigsecret");

    // Simulez la création du JWT avec l'algorithme configuré
    String token = JWT.create().withIssuer("auth0")
        .withClaim("user", user.getUserId())
        .sign(algorithm);
    System.out.println(user.getUserId());
    // Configurez le mock pour retourner le token simulé
    when(JWT.create().withIssuer("auth0")
        .withClaim("user", user.getUserId())
        .sign(algorithm)).thenReturn(token);

    ObjectNode expectedNode = objectMapper.createObjectNode()
        .put("token", token)
        .put("id", user.getUserId())
        .put("email", user.getEmail())
        .put("name", user.getName())
        .put("telephone", user.getPhone())
        .put("annee", user.getYear())
        .put("role", user.getRole().name());

    ObjectNode resultNode = authUCC.login(email, password);

    assertNotNull(resultNode);
    assertEquals(expectedNode, resultNode);
  }

  @Test
  void register_ValidUser_ReturnsTokenAndUserInfo() {
    User user = createMockUser(1, "John", "Doe", "test@example.com", "1234567890",
        "password", User.Role.STUDENT);
    when(userDAO.addUser(user)).thenReturn(user);

    Algorithm algorithmMock = mock(Algorithm.class);

// Configurez le mock pour qu'il retourne une valeur valide
    when(algorithmMock.getName()).thenReturn("HMAC256");

    String token = "generated_token";
    when(JWT.create().withIssuer("auth0")
        .withClaim("user", user.getUserId()).sign(algorithmMock)).thenReturn(token);

    ObjectNode expectedNode = objectMapper.createObjectNode()
        .put("token", token)
        .put("id", user.getUserId())
        .put("email", user.getEmail())
        .put("name", user.getName())
        .put("telephone", user.getPhone())
        .put("annee", user.getYear())
        .put("role", user.getRole().name());

    ObjectNode resultNode = authUCC.register(user);

    assertNotNull(resultNode);
    assertEquals(expectedNode, resultNode);
  }

  @Test
  void createUserAndReturn_NewUser_ReturnsUser() {
    String email = "test@example.com";
    when(userDAO.getOneByEmail(email)).thenReturn(null);

    String name = "John";
    String firstname = "Doe";
    String telephone = "1234567890";
    String password = "password";
    String role = "STUDENT";
    User user = authUCC.createUserAndReturn(name, firstname, email, telephone, password, role);

    assertNotNull(user);
    assertEquals(name, user.getName());
    assertEquals(firstname, user.getSurname());
    assertEquals(email, user.getEmail());
    assertEquals(telephone, user.getPhone());
    assertEquals(User.Role.STUDENT, user.getRole());
    assertNotNull(user.getPassword()); // Assuming password hashing works correctly
  }
}