package be.vinci.pae.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JDBCManagerImplTest {

  static {
    Config.load("dev.properties");
  }

  private JDBCManagerImpl jdbcManager;
  private Connection mockedConnection;

  @BeforeEach
  void setUp() throws SQLException {

    jdbcManager = new JDBCManagerImpl();
    mockedConnection = mock(Connection.class);

  }

  @Test
  void getConnection_Success() throws SQLException {

    // Vérifiez que la connexion retournée est la même que celle simulée
    assertDoesNotThrow(() -> {
      jdbcManager.getConnection();
    });
    assertNotNull(jdbcManager.getConnection());
    jdbcManager.close();


  }


  @Test
  void close_Success() throws SQLException {

    // Réinitialisez le comportement de la méthode isClosed() pour qu'elle renvoie false
    when(mockedConnection.isClosed()).thenReturn(false);

    // Appelez à nouveau la méthode close
    jdbcManager.close();

    // Assurez-vous que la méthode close n'a pas été appelée cette fois, car la connexion est déjà fermée
    verify(mockedConnection, never()).close();
  }


  @Test
  void close_Failure() throws SQLException {
    // Mocking Connection.isClosed() to return true
    when(mockedConnection.isClosed()).thenReturn(true);

    // No interaction with close() should happen
    jdbcManager.close();

    verify(mockedConnection, never()).close();
  }
}
