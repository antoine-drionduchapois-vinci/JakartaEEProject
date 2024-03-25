package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class EnterpriseUCCImplTest {

  private static ServiceLocator locator;
  private static EnterpriseUCC enterpriseUCC;
  private static EnterpriseDAO enterpriseDAO;
  private static DomainFactory domainFactory;
  private static DALServiceImpl dalService;

  @BeforeAll
  static void setUp() {
    // Création d'un ServiceLocator et ajout du TestBinder
    locator = ServiceLocatorUtilities.bind(new TestBinder());

    // Récupération des instances des classes mockées à partir du ServiceLocator
    enterpriseDAO = locator.getService(EnterpriseDAO.class);
    enterpriseUCC = locator.getService(EnterpriseUCC.class);
    domainFactory = locator.getService(DomainFactory.class);
    dalService = locator.getService(DALServiceImpl.class);

  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
    locator.shutdown();
  }

  @Test
  void getAllEnterprises() throws SQLException {
    // Création de données de test
    List<Enterprise> testData = new ArrayList<>();
    testData.add(domainFactory.getEnterprise());
    testData.add(domainFactory.getEnterprise());
    ResultSet rs = mock(ResultSet.class);
    PreparedStatement ps = mock(PreparedStatement.class);
    when(ps.executeQuery()).thenReturn(rs);
    Connection connection = mock(Connection.class);
    when(connection.prepareStatement("SQL_QUERY")).thenReturn(ps);
    DataSource dataSource = mock(DataSource.class);
    when(dataSource.getConnection()).thenReturn(connection);
    when(ps.execute()).thenReturn(true);
    when(dalService.getPS("SQL_QUERY")).thenReturn(ps);

    // Appeler la méthode à tester
    List<Enterprise> result = enterpriseUCC.getAllEnterprises();

    // Vérifier le résultat
    assertEquals(testData, result);
  }

  @Test
  void getEnterprisesByUserId() {
    // Création d'un ID de test
    int userId = 123;

    // Création d'un DTO d'entreprise simulé
    Enterprise testEnterprise = domainFactory.getEnterprise();
    testEnterprise.setEnterpriseId(userId);

    // Appeler la méthode à tester
    EnterpriseDTO result = enterpriseUCC.getEnterprisesByUserId(userId);

    // Vérifier le résultat
    assertEquals(testEnterprise, result);
  }
}
