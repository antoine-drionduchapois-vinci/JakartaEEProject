package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import be.vinci.pae.TestBinder;
import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.utils.DALServiceImpl;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactUCCImplTest {

  private ContactDAO contactDAO;
  private EnterpriseDAO enterpriseDAO;
  private DALServiceImpl dalService;
  private DomainFactory domainFactory;
  private ContactUCCImpl contactUCC;

  @BeforeEach
  void setUp() throws SQLException {
    ServiceLocator locator = ServiceLocatorUtilities.bind(new TestBinder());
    this.dalService = locator.getService(DALServiceImpl.class);
    this.contactDAO = locator.getService(ContactDAO.class);
    this.enterpriseDAO = locator.getService(EnterpriseDAO.class);
    this.domainFactory = locator.getService(DomainFactory.class);
    this.contactUCC = new ContactUCCImpl();

    PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
    ResultSet mockResultSet = mock(ResultSet.class);
    System.out.println(mockResultSet);
    when(mockResultSet.next()).thenReturn(true).thenReturn(false);
    // Définir le comportement de executeQuery() pour retourner un ResultSet simulé
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

    when(dalService.getPS(anyString())).thenReturn(mockPreparedStatement);
  }

  @Test
  void getContacts_shouldReturnListOfContactDTO_whenUserIdExists() {
    // Arrange
    int userId = 1;
    List<ContactDTO> expectedContacts = new ArrayList<>();
    expectedContacts.add(domainFactory.getContact());
    when(contactDAO.readMany(userId)).thenReturn(expectedContacts);
    when(enterpriseDAO.readOne(anyInt())).thenReturn(domainFactory.getEnterprise());

    // Act
    List<ContactDTO> actualContacts = contactUCC.getContacts(userId);

    // Assert
    assertNotNull(actualContacts);
    assertEquals(expectedContacts.size(), actualContacts.size());
  }

  @Test
  void getContact() {
  }

  @Test
  void initiateContact() {
  }

  @Test
  void testInitiateContact() {
  }

  @Test
  void meetEnterprise() {
  }

  @Test
  void indicateAsRefused() {
  }

  @Test
  void unfollow() {
  }
}