package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
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
import be.vinci.pae.utils.BusinessException;
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
  private static ContactUCC contactUCC;
  private static SupervisorUCC supervisorUCC;

  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    domainFactory = locator.getService(DomainFactory.class);
    internshipDAO = locator.getService(InternshipDAO.class);
    enterpriseDAO = locator.getService(EnterpriseDAO.class);
    contactDAO = locator.getService(ContactDAO.class);
    supervisorDAO = locator.getService(SupervisorDAO.class);
    internshipUCC = locator.getService(InternshipUCC.class);
    contactUCC = locator.getService(ContactUCC.class);
    supervisorUCC = locator.getService(SupervisorUCC.class);
  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
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

  @Test
  void testGetUserInternshipWithNonExistingEnterprise() {
    int userId = 1;
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setEnterprise(1);

    // Configuration du comportement simulé du DAO pour retourner null
    when(internshipDAO.getUserInternship(userId)).thenReturn(internshipDTO);
    when(enterpriseDAO.readOne(1)).thenReturn(null);

    // Vérification qu'une NotFoundException est bien lancée
    assertThrows(NotFoundException.class, () -> internshipUCC.getUserInternship(userId));
    verify(internshipDAO, atLeastOnce()).getUserInternship(userId);
    verify(enterpriseDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testGetUserInternshipWithNonExistingContact() {
    int userId = 1;
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setEnterprise(1);
    internshipDTO.setContact(1);
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();

    // Configuration du comportement simulé du DAO pour retourner null
    when(internshipDAO.getUserInternship(userId)).thenReturn(internshipDTO);
    when(enterpriseDAO.readOne(1)).thenReturn(enterpriseDTO);
    when(contactDAO.readOne(1)).thenReturn(null);

    // Vérification qu'une NotFoundException est bien lancée
    assertThrows(NotFoundException.class, () -> internshipUCC.getUserInternship(userId));
    verify(internshipDAO, atLeastOnce()).getUserInternship(userId);
    verify(contactDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testGetUserInternshipWithNonExistingSupervisor() {
    int userId = 1;
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setEnterprise(1);
    internshipDTO.setContact(1);
    internshipDTO.setSupervisor(1);
    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    ContactDTO contactDTO = domainFactory.getContact();

    // Configuration du comportement simulé du DAO pour retourner null
    when(internshipDAO.getUserInternship(userId)).thenReturn(internshipDTO);
    when(enterpriseDAO.readOne(1)).thenReturn(enterpriseDTO);
    when(contactDAO.readOne(1)).thenReturn(contactDTO);
    when(supervisorDAO.readOne(1)).thenReturn(null);

    // Vérification qu'une NotFoundException est bien lancée
    assertThrows(NotFoundException.class, () -> internshipUCC.getUserInternship(userId));
    verify(internshipDAO, atLeastOnce()).getUserInternship(userId);
    verify(supervisorDAO, atLeastOnce()).readOne(1);
  }

  @Test
  void testAcceptInternshipWithUserAlreadyHavingInternship() {
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setUser(1);

    when(internshipDAO.getUserInternship(1)).thenReturn(internshipDTO);

    assertThrows(BusinessException.class, () -> internshipUCC.acceptInternship(internshipDTO));

    verify(internshipDAO, atLeastOnce()).getUserInternship(1);
  }

  @Test
  void testAcceptInternshipWithInvalidContactUserMismatch() {
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setUser(1);
    internshipDTO.setContact(2);

    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(2);
    contactDTO.setState("meet");
    contactDTO.setUser(3);

    when(internshipDAO.getUserInternship(1)).thenReturn(null);
    when(contactDAO.readOne(2)).thenReturn(contactDTO);

    assertThrows(NotFoundException.class, () -> internshipUCC.acceptInternship(internshipDTO));

    verify(internshipDAO).getUserInternship(1);
  }

  @Test
  void testAcceptInternshipWithExistingSupervisor() {
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setUser(1);
    internshipDTO.setSupervisor(1);
    internshipDTO.setContact(2);

    SupervisorDTO supervisorDTO = domainFactory.getSupervisor();
    supervisorDTO.setSupervisorId(1);

    EnterpriseDTO enterpriseDTO = domainFactory.getEnterprise();
    enterpriseDTO.setEnterpriseId(1);

    ContactDTO contactDTO = domainFactory.getContact();
    contactDTO.setContactId(2);
    contactDTO.setState("meet");
    contactDTO.setUser(1);
    contactDTO.setEnterprise(1);
    contactDTO.setEnterpriseDTO(enterpriseDTO);

    when(internshipDAO.getUserInternship(1)).thenReturn(null);
    when(contactDAO.readOne(2)).thenReturn(contactDTO);
    when(supervisorDAO.getResponsibleByEnterpriseId(1)).thenReturn(
        supervisorDTO);

    internshipUCC.acceptInternship(internshipDTO);

    assertEquals(supervisorDTO, internshipDTO.getSupervisorDTO());
  }

  @Test
  void testAcceptInternshipWithNewSupervisor() {
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setEnterprise(1);

    SupervisorDTO supervisorDTO = domainFactory.getSupervisor();

    when(internshipDAO.getUserInternship(0)).thenReturn(null);
    when(supervisorUCC.getResponsibleByEnterpriseId(anyInt())).thenReturn(null);
    when(supervisorUCC.addOne(any(SupervisorDTO.class))).thenReturn(supervisorDTO);

    internshipUCC.acceptInternship(internshipDTO);

    assertEquals(supervisorDTO, internshipDTO.getSupervisorDTO());
  }

  @Test
  void testAcceptInternship() {
    InternshipDTO internshipDTO = domainFactory.getInternship();

    when(internshipDAO.getUserInternship(0)).thenReturn(null);
    when(contactUCC.accept(anyInt(), anyInt())).thenReturn(domainFactory.getContact());
    when(supervisorUCC.getResponsibleByEnterpriseId(anyInt())).thenReturn(
        domainFactory.getSupervisor());

    InternshipDTO result = internshipUCC.acceptInternship(internshipDTO);

    assertNotNull(result);
    verify(internshipDAO).create(internshipDTO);
  }

  @Test
  void testModifySubjectWithNonExistingInternship() {
    // Simuler un stage inexistant pour l'utilisateur
    when(internshipDAO.getUserInternship(1)).thenReturn(null);

    // Vérifier que NotFoundException est levée lorsque le stage n'existe pas
    assertThrows(NotFoundException.class, () -> internshipUCC.modifySubject(1, "New subject"));
  }

  @Test
  void testModifySubjectWithExistingInternship() {
    // Créer un stage existant pour l'utilisateur
    InternshipDTO internshipDTO = domainFactory.getInternship();
    internshipDTO.setUser(1);
    internshipDTO.setSubject("Old subject");

    // Appeler la méthode modifySubject pour modifier le sujet
    InternshipDTO updatedInternship = domainFactory.getInternship();
    updatedInternship.setUser(1);
    updatedInternship.setSubject("New subject");

    // Simuler la récupération du stage existant
    when(internshipDAO.getUserInternship(1)).thenReturn(internshipDTO);
    when(internshipDAO.update(internshipDTO)).thenReturn(updatedInternship);

    InternshipDTO result = internshipUCC.modifySubject(1, "New subject");

    // Vérifier que le sujet a été correctement mis à jour
    assertEquals("New subject", result.getSubject());

    // Vérifier que la méthode update a été appelée avec le bon argument
    verify(internshipDAO, atLeastOnce()).update(internshipDTO);
  }
}
