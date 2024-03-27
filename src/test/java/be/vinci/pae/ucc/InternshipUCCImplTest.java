package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.InternshipDTO;
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
  private static DomainFactory domainFactory;
  private static InternshipUCC internshipUCC;

  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    domainFactory = locator.getService(DomainFactory.class);
    internshipDAO = locator.getService(InternshipDAO.class);
    internshipUCC = locator.getService(InternshipUCC.class);
  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
    locator.shutdown();
  }

  @Test
  void testGetUserInternshipWithExistingInternship() {
    int userId = 1;
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setInternshipId(1);
    internshipDTO.setUser(1);
    internshipDTO.setContact(1);
    internshipDTO.setYear("2024");
    internshipDTO.setSupervisor(1);
    internshipDTO.setEnterprise(1);
    internshipDTO.setSubject("Internship in consulting agency");
    when(internshipDAO.getUserInternship(userId)).thenReturn(internshipDTO);

    InternshipDTO result = internshipUCC.getUserInternship(userId);

    assertEquals(internshipDTO, result);
  }

  @Test
  void testGetUserInternshipWithNonExistingInternship() {
    int userId = 1;
    when(internshipDAO.getUserInternship(userId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      internshipUCC.getUserInternship(userId);
    });
  }
}
