package be.vinci.pae;

import static org.mockito.Mockito.mock;

import be.vinci.pae.dao.UserDAO;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class TestBinder extends AbstractBinder {

  @Override
  protected void configure() {
    // Mock UserDAO avec Mockito et liez-le à UserDAO
    UserDAO userDAOMock = mock(UserDAO.class);
    bind(userDAOMock).to(UserDAO.class);

    // Vous pouvez ajouter d'autres liaisons pour d'autres mocks si nécessaire
  }
}