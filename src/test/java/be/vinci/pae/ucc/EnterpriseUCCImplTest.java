package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.NotFoundException;
import be.vinci.pae.utils.TestBinder;
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
  private static ContactDAO contactDAO;
  private static DomainFactory domainFactory;
  private static ContactUCC contactUCC;

  @BeforeAll
  static void setUp() {
    // Création d'un ServiceLocator et ajout du TestBinder
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    // Récupération des instances des classes mockées à partir du ServiceLocator
    enterpriseDAO = locator.getService(EnterpriseDAO.class);
    enterpriseUCC = locator.getService(EnterpriseUCC.class);
    contactDAO = locator.getService((ContactDAO.class));
    domainFactory = locator.getService(DomainFactory.class);
    contactUCC = locator.getService(ContactUCC.class);

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
    verify(enterpriseDAO).getAllEnterprises();
  }

  @Test
  void testGetAllEnterprisesWithException() {
    // Simuler une exception lors de la récupération des entreprises
    when(enterpriseDAO.getAllEnterprises()).thenThrow(new RuntimeException());

    // Appeler la méthode à tester et vérifier qu'elle lance une exception
    assertThrows(RuntimeException.class, () -> enterpriseUCC.getAllEnterprises());

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
    verify(enterpriseDAO).getEnterpriseById(userId);
  }

  @Test
  void testGetEnterprisesByUserIdWithNoCorrespondingEnterprise() {
    when(enterpriseDAO.getEnterpriseById(1)).thenReturn(null);

    assertNull(enterpriseUCC.getEnterprisesByUserId(1));
    verify(enterpriseDAO).getEnterpriseById(1);
  }

  @Test
  void testGetEnterprisesByUserIdWithException() {
    // Simuler une exception lors de la récupération des entreprises
    when(enterpriseDAO.getEnterpriseById(1)).thenThrow(new RuntimeException());

    // Appeler la méthode à tester et vérifier qu'elle lance une exception
    assertThrows(RuntimeException.class, () -> enterpriseUCC.getEnterprisesByUserId(1));

  }

  @Test
  void testBlacklistEnterpriseWithValidData() {
    // Créer une entreprise valide
    Enterprise enterprise = domainFactory.getEnterprise();
    enterprise.setEnterpriseId(1);
    enterprise.setName("Test Enterprise");

    Enterprise enterprise1 = domainFactory.getEnterprise();
    enterprise1.setEnterpriseId(1);
    enterprise1.setBlacklisted(true);
    enterprise1.setBlacklistedReason("Reason");

    // Créer des contacts pour l'entreprise
    ContactDTO contact1 = domainFactory.getContact();
    contact1.setContactId(1);
    contact1.setState("meet");
    ContactDTO contact2 = domainFactory.getContact();
    contact2.setContactId(2);
    contact2.setState("meet");
    List<ContactDTO> contactDTOS = new ArrayList<>();
    contactDTOS.add(contact1);
    contactDTOS.add(contact2);

    ContactDTO contact1update = domainFactory.getContact();
    contact1update.setContactId(1);
    contact1update.setEnterprise(1);
    contact1update.setState("suspended");
    ContactDTO contact2update = domainFactory.getContact();
    contact2update.setContactId(2);
    contact2update.setEnterprise(1);
    contact2update.setState("suspended");

    // Simuler la récupération de l'entreprise
    when(enterpriseDAO.readOne(1)).thenReturn(enterprise);
    when(enterpriseDAO.update(enterprise)).thenReturn(enterprise1);

    // Simuler la récupération des contacts de l'entreprise
    when(contactDAO.readEnterpriseInitiatedOrMeetContacts(1)).thenReturn(contactDTOS);
    when(contactDAO.readOne(1)).thenReturn(contact1);
    when(contactDAO.readOne(2)).thenReturn(contact2);
    when(contactDAO.update(contact1)).thenReturn(contact1update);
    when(contactDAO.update(contact2)).thenReturn(contact2update);

    // Appeler la méthode à tester
    EnterpriseDTO result = enterpriseUCC.blacklistEnterprise(1, "Reason");

    // Vérifier que la méthode toBlacklist de enterpriseDAO a été appelée
    verify(enterpriseDAO).update(enterprise);

    // Vérifier que la méthode readEnterpriseInitiatedOrMeetContacts de contactDAO a été appelée
    verify(contactDAO).readEnterpriseInitiatedOrMeetContacts(1);

    //Vérifier les résultats
    assertEquals(enterprise.getEnterpriseId(), result.getEnterpriseId());
  }

  @Test
  void testBlacklistEnterpriseWithInvalidEnterpriseId() {
    Enterprise enterprise = domainFactory.getEnterprise();
    enterprise.setEnterpriseId(1);
    enterprise.setName("Test Enterprise");
    // Simuler la récupération d'une entreprise inexistante
    when(enterpriseDAO.readOne(2)).thenReturn(enterprise);

    // Vérifier que NotFoundException est levée lorsque l'entreprise n'existe pas
    assertThrows(NotFoundException.class, () -> enterpriseUCC.blacklistEnterprise(2, "Reason"));

  }

  @Test
  void testBlacklistEnterpriseWithAlreadyBlacklistedEnterprise() {
    // Créer une entreprise déjà blacklistée
    Enterprise enterprise = domainFactory.getEnterprise();
    enterprise.setEnterpriseId(1);
    enterprise.setBlacklisted(true);

    // Simuler la récupération de l'entreprise
    when(enterpriseDAO.readOne(1)).thenReturn(enterprise);

    // Vérifier que BusinessException est levée lorsque l'entreprise est déjà blacklistée
    assertThrows(BusinessException.class, () -> enterpriseUCC.blacklistEnterprise(1, "Reason"));
  }
}
