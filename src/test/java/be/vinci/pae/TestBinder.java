package be.vinci.pae;

import static org.mockito.Mockito.mock;

import be.vinci.pae.dao.UserDAO;
import be.vinci.pae.ucc.UserUCC;
import be.vinci.pae.ucc.UserUCCImpl;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * Binder used for configuring dependency injection in tests.
 */
public class TestBinder extends AbstractBinder {

  /**
   * Configures the binder for dependency injection in tests.
   */
  @Override
  protected void configure() {
    // Mock UserDAO avec Mockito et liez-le à UserDAO
    UserDAO userDAOMock = mock(UserDAO.class);
    bind(userDAOMock).to(UserDAO.class);
    bind(UserUCCImpl.class).to(UserUCC.class);

    // Vous pouvez ajouter d'autres liaisons pour d'autres mocks si nécessaire
  }
}