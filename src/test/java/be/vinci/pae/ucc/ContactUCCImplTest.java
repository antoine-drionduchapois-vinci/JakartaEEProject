package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.DomainFactory;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ContactUCCImplTest {

  private static ServiceLocator locator;
  private static ContactDAO contactDAO;
  private static EnterpriseDAO enterpriseDAO;
  private static DomainFactory domainFactory;
  private static ContactUCC contactUCC;

  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    contactDAO = locator.getService(ContactDAO.class);
    enterpriseDAO = locator.getService(EnterpriseDAO.class);
    domainFactory = locator.getService(DomainFactory.class);
    contactUCC = locator.getService(ContactUCC.class);

  }

  @AfterAll
  static void tearDown() {
    locator.shutdown();
  }

  private ContactDTO createcontactDTO(int id, int idUser, int idEnterprise) {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(id);
    contactDTO.setUser(idUser);
    contactDTO.setEnterprise(idEnterprise);
    return contactDTO;
  }

  @Test
  void testGetContactsWithUserId() {
    List<ContactDTO> contactDTOs = new ArrayList<>();
    ContactDTO contactDTO = createcontactDTO(1, 1, 1);
    ContactDTO contactDTO1 = createcontactDTO(2, 1, 2);
    ContactDTO contactDTO2 = createcontactDTO(3, 2, 1);
    contactDTOs.add(contactDTO);
    contactDTOs.add(contactDTO1);
    contactDTOs.add(contactDTO2);

    when(contactDAO.readMany(1)).thenReturn(contactDTOs);

    List<ContactDTO> result = contactUCC.getContacts(1);

    assertEquals(contactDTOs, result);
  }

  @Test
  void testGetContactWithNoCorrespondingContact() {
    ContactDTO contactDTO = createcontactDTO(2, 1, 2);
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.getContact(1, 1);
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testGetContactWithCorrespondingContact() {
    ContactDTO contactDTO = createcontactDTO(1, 1, 1);

    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    enterpriseDTO.setEnterpriseId(1);
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(enterpriseDTO);
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    ContactDTO result = contactUCC.getContact(1, 1);

    assertEquals(contactDTO, result);
    verify(enterpriseDAO).readOne(contactDTO.getEnterprise());
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testGetContactWithWrongUser() {
    ContactDTO contactDTO = createcontactDTO(1, 1, 1);

    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    enterpriseDTO.setEnterpriseId(1);
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(enterpriseDTO);
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    assertThrows(NotFoundException.class, () -> contactUCC.getContact(2, 1));
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testInitiateContactWithNoCorrespondingEnterprise() {
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    enterpriseDTO.setEnterpriseId(2);
    ContactDTO contactDTO = createcontactDTO(1, 1, 2);
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(enterpriseDAO.readOne(1)).thenReturn(enterpriseDTO);
    when(contactDAO.create(1, 1)).thenReturn(contactDTO);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.initiateContact(1, 1);
    });
    verify(enterpriseDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, atLeastOnce()).create(1, 1);
  }

  @Test
  void testInitiateContactWithCorrespondingEnterprise() {
    ContactDTO contactDTO = domainFactory.getContact();
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();

    when(contactDAO.create(1, 1)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(enterpriseDTO.getEnterpriseId())).thenReturn(enterpriseDTO);

    ContactDTO result = contactUCC.initiateContact(1, 1);

    assertEquals(contactDTO, result);
    verify(enterpriseDAO, atLeastOnce()).readOne(enterpriseDTO.getEnterpriseId());
    verify(contactDAO, atLeastOnce()).create(1, 1);
  }

  @Test
  void testInitiateContact() {
    ContactDTO contactDTO = domainFactory.getContact();
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();

    when(enterpriseDAO.create("name", "label", "address", "phone", "email")).thenReturn(
        enterpriseDTO);
    when(contactDAO.create(1, enterpriseDTO.getEnterpriseId())).thenReturn(contactDTO);

    ContactDTO result = contactUCC.initiateContact(1, "name", "label", "address", "phone", "email");

    assertEquals(contactDTO, result);
    verify(enterpriseDAO, atLeastOnce()).create("name", "label", "address", "phone", "email");
    verify(contactDAO, atLeastOnce()).create(1, enterpriseDTO.getEnterpriseId());
  }

  @Test
  @DisplayName("Checks that the version number of an initiated contact is 1 ")
  void testVersionInitiateContact() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setVersion(1);
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    enterpriseDTO.setVersion(1);

    when(enterpriseDAO.create("name", "label", "address", "phone", "email")).thenReturn(
        enterpriseDTO);
    when(contactDAO.create(1, enterpriseDTO.getEnterpriseId())).thenReturn(contactDTO);

    int versionResult = contactUCC.initiateContact(1, "name", "label", "address", "phone", "email")
        .getVersion();

    assertEquals(1, versionResult);
    verify(enterpriseDAO, atLeastOnce()).create("name", "label", "address", "phone", "email");
    verify(contactDAO, atLeastOnce()).create(1, enterpriseDTO.getEnterpriseId());
  }

  @Test
  void testMeetEnterpriseContactNotInitiated() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(1);
    contactDTO.setUser(1);
    contactDTO.setState("notinitiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    assertThrows(BusinessException.class, () -> {
      contactUCC.meetEnterprise(1, 1, "meetingPoint");
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testMeetEnterpriseWithWrongUser() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(1);
    contactDTO.setUser(1);
    contactDTO.setState("notinitiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.meetEnterprise(2, 1, "meetingPoint");
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testMeetEnterpriseWithNoCorrespondingEnterprise() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(1);
    contactDTO.setUser(1);
    contactDTO.setState("initiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.meetEnterprise(1, 1, "meetingPoint");
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, atLeastOnce()).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void testMeetEnterpriseenterpriseDTONF() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(1);
    contactDTO.setState("initiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.meetEnterprise(1, 1, "meetingPoint");
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, times(0)).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void testMeetEnterpriseWithCorrespondingContact() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("initiated");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(enterpriseDTO);

    ContactDTO result = contactUCC.meetEnterprise(1, 1, "meetingPoint");

    assertEquals(contactDTO, result);
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, atLeastOnce()).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void testIndicateAsRefusedContactNotInitiated() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("notinitiated");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(enterpriseDTO);

    assertThrows(BusinessException.class, () -> {
      contactUCC.indicateAsRefused(1, 1, "refusalReason");
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testIndicateAsRefusedWithWrongUser() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("notinitiated");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(enterpriseDTO);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.indicateAsRefused(2, 1, "refusalReason");
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testIndicateAsRefusedContactenterpriseDTONF() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setState("initiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.indicateAsRefused(1, 1, "refusalReason");
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, times(0)).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void testIndicateAsRefusedWithCorrespondingContact() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("initiated");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(enterpriseDTO);

    ContactDTO result = contactUCC.indicateAsRefused(1, 1, "refusalReason");

    assertEquals(contactDTO, result);
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, atLeastOnce()).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void testIndicateAsRefusedWithNoCorrespondingEnterprise() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("initiated");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(null);

    assertThrows(NotFoundException.class,
        () -> contactUCC.indicateAsRefused(1, 1, "refusalReason"));
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, atLeastOnce()).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void indicateAsSuspendedWithNoCorrespondingContact() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(2);
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    assertThrows(NotFoundException.class, () -> contactUCC.indicateAsSuspended(1));
  }

  @Test
  void indicateAsSuspendedWithWrongState() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(1);
    contactDTO.setState("accepted");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    assertThrows(BusinessException.class, () -> contactUCC.indicateAsSuspended(1));
  }

  @Test
  void indicateAsSuspendedWithNoCorrespondingEnterprise() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(1);
    contactDTO.setState("meet");
    contactDTO.setEnterprise(1);

    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> contactUCC.indicateAsSuspended(1));
  }

  @Test
  void testUnfollowContactNotInitiated() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("notinitiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    assertThrows(BusinessException.class, () -> {
      contactUCC.unfollow(1, 1);
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testUnfollowContactWithNoCorrespondingEnterprise() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("initiated");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.unfollow(1, 1);
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, atLeastOnce()).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }


  @Test
  void testUnfollowWithWrongUser() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("notinitiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.unfollow(2, 1);
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
  }


  @Test
  void testUnfollowEnterpriseDTONF() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setState("initiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      contactUCC.unfollow(1, 1);
    });
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, times(0)).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void testUnfollowWithCorrespondingContact() {
    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setUser(1);
    contactDTO.setState("meet");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(contactDTO.getEnterprise())).thenReturn(enterpriseDTO);

    ContactDTO result = contactUCC.unfollow(1, 1);

    assertEquals(contactDTO, result);
    verify(contactDAO, atLeastOnce()).readOne(1);
    verify(contactDAO, atLeastOnce()).update(contactDTO);
    verify(enterpriseDAO, atLeastOnce()).readOne(contactDTO.getEnterprise());
  }

  @Test
  void testAcceptContactNotFound() {
    when(contactDAO.readOne(1)).thenReturn(null);
    assertThrows(NotFoundException.class, () -> contactUCC.accept(1, 1));
  }

  @Test
  void testAcceptContactInWrongState() {
    Contact contactDTO = (Contact) domainFactory.getContact();
    contactDTO.setState("initiated");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    ContactDTO readContact = domainFactory.getContact();
    readContact.setState("unfollowed");
    ArrayList<ContactDTO> readArray = new ArrayList<>();

    when(contactDAO.readMany(1)).thenReturn(readArray);
    when(contactDAO.update(readContact)).thenReturn(readContact);
    assertThrows(BusinessException.class, () -> contactUCC.accept(1, 1));
  }

  @Test
  void testAcceptContactInCorrectState() {
    Contact contactDTO = (Contact) domainFactory.getContact();
    contactDTO.setState("meet");
    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    ContactDTO readContact = domainFactory.getContact();
    readContact.setState("unfollowed");
    ArrayList<ContactDTO> readArray = new ArrayList<>();

    when(contactDAO.readMany(1)).thenReturn(readArray);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(contactDAO.update(readContact)).thenReturn(readContact);

    assertEquals("accepted", contactUCC.accept(1, 1).getState());
  }

  @Test
  void testAcceptContactReadMany() {
    Contact contactDTO = (Contact) domainFactory.getContact();
    contactDTO.setState("meet");

    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    ContactDTO readContact = domainFactory.getContact();
    readContact.setState("unfollowed");
    ArrayList<ContactDTO> readArray = new ArrayList<>();
    readArray.add(readContact);

    when(contactDAO.readMany(1)).thenReturn(readArray);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);
    when(contactDAO.update(readContact)).thenReturn(readContact);

    assertEquals("accepted", contactUCC.accept(1, 1).getState());
  }
  @Test
  void testAcceptContactReadManyEmpty() {
    Contact contactDTO = (Contact) domainFactory.getContact();
    contactDTO.setState("meet");

    when(contactDAO.readOne(1)).thenReturn(contactDTO);

    ArrayList<ContactDTO> readArray = new ArrayList<>();
    readArray.add(contactDTO);

    when(contactDAO.readMany(1)).thenReturn(readArray);
    when(contactDAO.update(contactDTO)).thenReturn(contactDTO);

    assertEquals("accepted", contactUCC.accept(1, 1).getState());
  }

  @Test
  void testGetEnterpriseContacts_Success() {
    // Arrange
    int enterpriseId = 1;
    List<ContactDTO> expectedContacts = new ArrayList<>();
    ContactDTO contact1 = createcontactDTO(1, 1, 1);
    ContactDTO contact2 = createcontactDTO(2, 2, 1);
    expectedContacts.add(contact1);
    expectedContacts.add(contact2);
    when(contactDAO.readEnterpriseContacts(enterpriseId)).thenReturn(expectedContacts);

    // Act
    List<ContactDTO> result = contactUCC.getEnterpriseContacts(enterpriseId);

    // Assert
    assertEquals(expectedContacts, result);
    verify(contactDAO, times(1)).readEnterpriseContacts(enterpriseId);
  }

  @Test
  void testGetEnterpriseContacts_NotFound() {
    // Arrange
    int enterpriseId = 1;
    when(contactDAO.readEnterpriseContacts(enterpriseId)).thenReturn(null);

    // Act & Assert
    assertThrows(NotFoundException.class, () -> contactUCC.getEnterpriseContacts(enterpriseId));
    verify(contactDAO, times(2)).readEnterpriseContacts(enterpriseId);
  }
}