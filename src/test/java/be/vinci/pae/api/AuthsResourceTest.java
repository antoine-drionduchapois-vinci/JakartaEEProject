package be.vinci.pae.api;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.services.UserDataService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.WebApplicationException;
import java.lang.reflect.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthsResourceTest {

  private AuthsResource authsResource;
  private ObjectMapper jsonMapper;
  UserDataService userDataServiceMock;
  String validEmail = "test@test.com";
  String validPassword = "securePassword";
  String invalidEmail = "test@test.com";
  String invalidPassword = "securePassword";

  @BeforeEach
  void setUp() throws NoSuchFieldException, IllegalAccessException {
    authsResource = new AuthsResource();
    jsonMapper = new ObjectMapper();

    userDataServiceMock = mock(UserDataService.class);
    Field field = AuthsResource.class.getDeclaredField("myUserDataService");
    field.setAccessible(true);
    field.set(authsResource, userDataServiceMock);
  }

  @Test
  void login_ValidJson_ReturnsPublicUser() {
    when(userDataServiceMock.login(validEmail, validPassword)).thenReturn(
        jsonMapper.createObjectNode());

    JsonNode validJson = jsonMapper.createObjectNode().put("email", validEmail)
        .put("password", validPassword);
    assertNotNull(authsResource.login(validJson));
  }

  @Test
  void login_EmptyJson_ThrowsException() {
    JsonNode emptyJson = jsonMapper.createObjectNode();
    assertThrows(WebApplicationException.class, () -> authsResource.login(emptyJson));
  }

  @Test
  void login_JsonNoPassword_ThrowsException() {
    JsonNode emptyJson = jsonMapper.createObjectNode().put("email", validEmail);
    assertThrows(WebApplicationException.class, () -> authsResource.login(emptyJson));
  }

  @Test
  void login_JsonNoEmail_ThrowsException() {
    JsonNode emptyJson = jsonMapper.createObjectNode().put("password", validPassword);
    assertThrows(WebApplicationException.class, () -> authsResource.login(emptyJson));
  }

  @Test
  void login_InvalidEmail_ThrowsException() {
    when(userDataServiceMock.login(invalidEmail, validPassword)).thenReturn(null);

    JsonNode emptyJson = jsonMapper.createObjectNode().put("email", invalidEmail)
        .put("password", validPassword);
    assertThrows(WebApplicationException.class, () -> authsResource.login(emptyJson));
  }

  @Test
  void login_InvalidPassword_ThrowsException() {
    when(userDataServiceMock.login(validEmail, validPassword)).thenReturn(
        null);

    JsonNode emptyJson = jsonMapper.createObjectNode().put("email", validEmail)
        .put("password", invalidPassword);
    assertThrows(WebApplicationException.class, () -> authsResource.login(emptyJson));
  }
}