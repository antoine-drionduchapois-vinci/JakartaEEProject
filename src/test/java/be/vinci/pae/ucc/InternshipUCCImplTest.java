package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.dao.SupervisorDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.utils.NotFoundException;
import be.vinci.pae.utils.TestBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class InternshipUCCImplTest {

  private static ServiceLocator locator;
  private static InternshipDAO internshipDAO;
  private static EnterpriseDAO enterpriseDAO;
  private static ContactDAO contactDAO;
  private static SupervisorDAO supervisorDAO;
  private static DomainFactory domainFactory;
  private static InternshipUCC internshipUCC;

  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    domainFactory = locator.getService(DomainFactory.class);
    internshipDAO = locator.getService(InternshipDAO.class);
    enterpriseDAO = locator.getService(EnterpriseDAO.class);
    contactDAO = locator.getService(ContactDAO.class);
    supervisorDAO = locator.getService(SupervisorDAO.class);
    internshipUCC = locator.getService(InternshipUCC.class);
  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
    verify(internshipDAO, times(2)).getUserInternship(1);
    locator.shutdown();
  }

  @Test
  void testGetUserInternshipWithExistingInternship() {

    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setInternshipId(1);
    internshipDTO.setUser(1);
    internshipDTO.setContact(1);
    internshipDTO.setYear("2024");
    internshipDTO.setSupervisor(1);
    internshipDTO.setEnterprise(1);
    internshipDTO.setSubject("Internship in consulting agency");
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    ContactDTO contactDTO = domainFactory.getContact();
    SupervisorDTO supervisorDTO = domainFactory.getSupervisor();
    int userId = 1;
    when(internshipDAO.getUserInternship(userId)).thenReturn(internshipDTO);
    when(enterpriseDAO.readOne(1)).thenReturn(enterpriseDTO);
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(supervisorDAO.readOne(1)).thenReturn(supervisorDTO);

    InternshipDTO result = internshipUCC.getUserInternship(userId);

    assertEquals(internshipDTO, result);
    verify(internshipDAO).getUserInternship(userId);
  }

  @Test
  void testGetUserInternshipWithNonExistingInternship() {
    int userId = 1;
    when(internshipDAO.getUserInternship(userId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> internshipUCC.getUserInternship(userId));
  }
}
