package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.domain.Internship;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.InternshipImpl;
import be.vinci.pae.utils.DALService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class InternshipUCCImplTest {

  @Mock
  private InternshipDAO internshipDAO;

  @Mock
  private DALService dalService;

  @InjectMocks
  private InternshipUCCImpl internshipUCC; // Assuming this is your class that implements InternshipUCC

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUserInternship() {
    // Setup - Define the expected properties of the InternshipDTO
    int userId = 9; // Assuming this user has an internship
    Internship expectedInternship = new InternshipImpl();
    expectedInternship.setInternshipId(1);
    expectedInternship.setSubject("Un ERP : Odoo");
    expectedInternship.setYear("2023-2024");
    expectedInternship.setUser(userId);
    expectedInternship.setEnterprise(2);
    expectedInternship.setSupervisor(1);
    expectedInternship.setContact(1);

    // Mocking - When the DAO is asked for the internship of the specified user, it returns the prepared object
    when(internshipDAO.getUserInternship(userId)).thenReturn(expectedInternship);

    // Execution - Call the method under test
    InternshipDTO result = internshipUCC.getUserInternship(userId);

    // Assertions - Verify the result is as expected
    assertNotNull(result, "The returned InternshipDTO should not be null.");
    assertEquals(expectedInternship.getInternshipId(), result.getInternshipId(),
        "The internship ID should match.");
    assertEquals(expectedInternship.getSubject(), result.getSubject(), "The subject should match.");
    assertEquals(expectedInternship.getYear(), result.getYear(), "The year should match.");
    assertEquals(expectedInternship.getUser(), result.getUser(), "The user ID should match.");
    assertEquals(expectedInternship.getEnterprise(), result.getEnterprise(),
        "The enterprise ID should match.");
    assertEquals(expectedInternship.getResponsible(), result.getResponsible(),
        "The supervisor ID should match.");
    assertEquals(expectedInternship.getContact(), result.getContact(),
        "The contact ID should match.");

    // Verify the interaction with the mock
    verify(internshipDAO).getUserInternship(userId);
    verify(dalService).start();
    verify(dalService).commit();
  }
}
