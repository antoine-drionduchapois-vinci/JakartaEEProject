package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    InternshipDTO expectedInternship = new InternshipImpl(); // Direct instantiation; ensure this is logically correct
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

    // More assertions as per your test design

    // Verify
    verify(internshipDAO).getUserInternship(userId);
    verify(dalService).start();
    verify(dalService).commit();
  }
}
