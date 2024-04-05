package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.NotFoundException;
import be.vinci.pae.utils.TestBinder;
import java.sql.SQLException;
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
  static void setUp() throws SQLException {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    contactDAO = locator.getService(ContactDAO.class);
    enterpriseDAO = locator.getService(EnterpriseDAO.class);
    domainFactory = locator.getService(DomainFactory.class);
    contactUCC = locator.getService(ContactUCC.class);

  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
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
  }

  @Test
  void testInitiateContactWithCorrespondingEnterprise() {
    ContactDTO contactDTO = domainFactory.getContact();
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();

    when(contactDAO.create(1, 1)).thenReturn(contactDTO);
    when(enterpriseDAO.readOne(enterpriseDTO.getEnterpriseId())).thenReturn(enterpriseDTO);

    ContactDTO result = contactUCC.initiateContact(1, 1);

    assertEquals(contactDTO, result);
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
  }
}