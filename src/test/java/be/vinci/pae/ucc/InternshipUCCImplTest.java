package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.InternshipImpl;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.TestBinder;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InternshipUCCImplTest {

  private InternshipDAO internshipDAO;
  private DALService dalService;
  private InternshipUCC internshipUCC;

  @BeforeEach
  void setUp() {
    internshipDAO = mock(InternshipDAO.class);
    dalService = mock(DALService.class);

    ServiceLocator locator = ServiceLocatorUtilities.bind(new
        TestBinder(internshipDAO, dalService));
    this.internshipUCC = locator.getService(InternshipUCC.class);
    assertNotNull(internshipUCC, "InternshipUCCImpl should not be null");
  }

  @Test
  void getUserInternship() {
    int userId = 9; // Example user ID
    InternshipDTO expectedInternship = new InternshipImpl();
    // Assume setters are present and set the properties
    expectedInternship.setInternshipId(1);
    expectedInternship.setSubject("Un ERP : Odoo");
    expectedInternship.setYear("2023-2024");
    expectedInternship.setUser(userId);
    expectedInternship.setEnterprise(2);
    expectedInternship.setSupervisor(1);
    expectedInternship.setContact(1);
    // Mocking behavior
    when(internshipDAO.getUserInternship(userId)).thenReturn(expectedInternship);

    // Execution
    InternshipDTO result = internshipUCC.getUserInternship(userId);

    // Assertions
    assertNotNull(result, "The returned InternshipDTO should not be null.");
    assertEquals(expectedInternship.getInternshipId(), result.getInternshipId(),
        "The internship ID should match.");
    assertEquals(expectedInternship.getSubject(), result.getSubject(),
        "The subject should match.");
    assertEquals(expectedInternship.getYear(), result.getYear(),
        "The year should match.");
    assertEquals(expectedInternship.getUser(), result.getUser(),
        "The user ID should match.");
    assertEquals(expectedInternship.getEnterprise(), result.getEnterprise(),
        "The enterprise ID should match.");
    assertEquals(expectedInternship.getResponsible(), result.getResponsible(),
        "The supervisor ID should match.");
    assertEquals(expectedInternship.getContact(), result.getContact(),
        "The contact ID should match.");

    // More assertions as per your test design

    // Verify
    verify(internshipDAO).getUserInternship(userId);
    verify(dalService).start();
    verify(dalService).commit();
  }


  @Test
  void getUserInternship_nonExistentUser() {
    int nonExistentUserId = 999; // Assuming this ID does not exist
    when(internshipDAO.getUserInternship(nonExistentUserId)).thenReturn(null);

    InternshipDTO result = internshipUCC.getUserInternship(nonExistentUserId);

    assertNull(result, "Expected null when querying a non-existent user ID.");
    verify(internshipDAO).getUserInternship(nonExistentUserId);
    verify(dalService).start();
    verify(dalService).commit();
  }

  @Test
  void getUserInternship_emptySubject() {
    int userId = 10;
    InternshipDTO internshipWithEmptySubject = new InternshipImpl();
    internshipWithEmptySubject.setInternshipId(2);
    internshipWithEmptySubject.setSubject(""); // Empty subject
    internshipWithEmptySubject.setYear("2023-2024");
    internshipWithEmptySubject.setUser(userId);
    internshipWithEmptySubject.setEnterprise(3);
    internshipWithEmptySubject.setSupervisor(2);
    internshipWithEmptySubject.setContact(2);

    when(internshipDAO.getUserInternship(userId)).thenReturn(internshipWithEmptySubject);

    InternshipDTO result = internshipUCC.getUserInternship(userId);

    assertNotNull(result, "Internship should not be null even with an empty subject.");
    assertTrue(result.getSubject().isEmpty(), "Expected the subject to be empty.");
  }

  @Test
  void getUserInternship_commitsTransactionOnSuccess() {
    int userId = 9;
    InternshipDTO expectedInternship = new InternshipImpl();
    expectedInternship.setInternshipId(1);
    when(internshipDAO.getUserInternship(userId)).thenReturn(expectedInternship);

    internshipUCC.getUserInternship(userId);

    verify(dalService).start();
    verify(dalService).commit();
  }

  @Test
  void getUserInternship_withSpecificYear() {
    int userId = 11;
    InternshipDTO expectedInternship = new InternshipImpl();
    expectedInternship.setInternshipId(3);
    expectedInternship.setYear("2024-2025"); // A future year
    when(internshipDAO.getUserInternship(userId)).thenReturn(expectedInternship);

    InternshipDTO result = internshipUCC.getUserInternship(userId);

    assertNotNull(result, "Expected a valid InternshipDTO.");
    assertEquals("2024-2025", result.getYear(),
        "Expected the year to match the specific future year.");
  }


}
