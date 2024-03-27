package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.utils.NotFoundException;
import java.util.ArrayList;
import java.util.List;
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

  @BeforeAll
  static void setUp() {
    // Création d'un ServiceLocator et ajout du TestBinder
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    // Récupération des instances des classes mockées à partir du ServiceLocator
    enterpriseDAO = locator.getService(EnterpriseDAO.class);
    enterpriseUCC = locator.getService(EnterpriseUCC.class);
    domainFactory = locator.getService(DomainFactory.class);

  }
  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
    locator.shutdown();
  }

  @Test
  void getAllEnterprises() {
    // Création de données de test
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    enterpriseDTO.setEnterpriseId(1);
    EnterpriseDTO enterpriseDTO1 = domainFactory.getEnterprise();
    enterpriseDTO1.setEnterpriseId(2);
    List<EnterpriseDTO> testData = new ArrayList<>();
    testData.add(enterpriseDTO);
    testData.add(enterpriseDTO1);

    when(enterpriseDAO.getAllEnterprises()).thenReturn(testData);

    // Appeler la méthode à tester
    List<EnterpriseDTO> result = enterpriseUCC.getAllEnterprises();

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

    when(enterpriseDAO.getEnterpriseById(userId)).thenReturn(testEnterprise);

    // Appeler la méthode à tester
    EnterpriseDTO result = enterpriseUCC.getEnterprisesByUserId(userId);

    // Vérifier le résultat
    assertEquals(testEnterprise, result);
  }

  @Test
  void testGetEnterprisesByUserIdWithNoCorrespondingEnterprise() {
    when(enterpriseDAO.getEnterpriseById(1)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      enterpriseUCC.getEnterprisesByUserId(1);
    });
  }
}
