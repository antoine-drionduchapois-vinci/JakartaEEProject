package be.vinci.pae.api.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.User;
import be.vinci.pae.utils.Config;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AuthorizationRequestFilterTest {

  @Mock
  private ContainerRequestContext requestContext;

  @InjectMocks
  private AuthorizationRequestFilter authorizationRequestFilter;

  @Mock
  private DomainFactory domainFactory;


  @BeforeEach
  void setUp() {
    Config.load("dev.properties");
    MockitoAnnotations.openMocks(this);
  }


  @Test
  void filter_MissingToken_Unauthorized() {
    // Simuler l'absence du token
    when(requestContext.getHeaderString("Authorization")).thenReturn(null);

    // Exécuter la méthode à tester
    try {
      authorizationRequestFilter.filter(requestContext);
      // Vérifier que la méthode a bien lancé une WebApplicationException
      // avec une réponse d'erreur 401 Unauthorized
      fail("Expected WebApplicationException to be thrown");
    } catch (WebApplicationException e) {
      // Vérifier que la réponse de l'exception est bien une erreur 401 Unauthorized
      assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), e.getResponse().getStatus());
      // Vérifier que le message de l'exception est correct

    } catch (IOException e) {
      fail("Unexpected IOException thrown: " + e.getMessage());
    }
  }


  @Test
  void filter_InvalidToken_Unauthorized() {
    // Simuler un token invalide
    when(requestContext.getHeaderString("Authorization")).thenReturn("invalid_token");

    // Créer un mock de l'interface User
    User userMock = mock(User.class);

    // Lorsque DomainFactory.getUser() est appelé, retourner le mock de User
    when(domainFactory.getUser()).thenReturn(userMock);
    // Définir explicitement la propriété "user" sur l'objet requestContext avec le mock de User
    when(requestContext.getProperty("user")).thenReturn(userMock);

    // Exécuter la méthode à tester
    assertThrows(WebApplicationException.class, () -> {
      try {
        authorizationRequestFilter.filter(requestContext);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Test
  void filter_NullUser_Forbidden() {
    // Simuler un token valide mais un utilisateur non autorisé
    when(requestContext.getHeaderString("Authorization")).thenReturn("invalid_token");
    when(requestContext.getProperty("user")).thenReturn(
        null); // L'utilisateur n'est pas authentifié

    // Exécuter la méthode à tester
    assertThrows(WebApplicationException.class, () -> {
      try {
        authorizationRequestFilter.filter(requestContext);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
  }

}
