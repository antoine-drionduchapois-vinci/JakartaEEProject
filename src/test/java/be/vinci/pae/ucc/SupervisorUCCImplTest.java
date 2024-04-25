package be.vinci.pae.ucc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import be.vinci.pae.dao.SupervisorDAO;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.utils.NotFoundException;
import be.vinci.pae.utils.TestBinder;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupervisorUCCImplTest {

  private static ServiceLocator locator;
  private static SupervisorDAO supervisorDAO;
  private static DomainFactory domainFactory;
  private static SupervisorUCC supervisorUCC;

  @BeforeAll
  static void setUp() {
    locator = ServiceLocatorUtilities.bind(new TestBinder());
    domainFactory = locator.getService(DomainFactory.class);
    supervisorDAO = locator.getService(SupervisorDAO.class);
    supervisorUCC = locator.getService(SupervisorUCC.class);
  }

  @AfterAll
  static void tearDown() {
    // Fermeture du ServiceLocator
    locator.shutdown();
  }

  @Test
  void testGetResponsibleByEnterpriseIdWithExistingSupervisor() {
    SupervisorDTO supervisor = domainFactory.getSupervisor();
    supervisor.setEnterprise(1);
    supervisor.setEmail("test@vinci.be");
    supervisor.setName("test");
    supervisor.setSurname("test");
    supervisor.setPhone("0484752145");
    supervisor.setEnterpriseDTO(domainFactory.getEnterprise());
    supervisor.setSupervisorId(1);
    when(supervisorDAO.getResponsibleByEnterpriseId(supervisor.getEnterprise())).thenReturn(
        supervisor);

    SupervisorDTO result = supervisorUCC.getResponsibleByEnterpriseId(supervisor.getEnterprise());

    assertEquals(supervisor, result);
    verify(supervisorDAO).getResponsibleByEnterpriseId(supervisor.getEnterprise());
  }

  @Test
  void testGetResponsibleByEnterpriseIdWithNonExistingSupervisor() {
    int enterpriseId = 1;
    when(supervisorDAO.getResponsibleByEnterpriseId(enterpriseId)).thenReturn(null);

    assertThrows(NotFoundException.class, () -> {
      supervisorUCC.getResponsibleByEnterpriseId(enterpriseId);
    });
    verify(supervisorDAO, times(2)).getResponsibleByEnterpriseId(enterpriseId);
  }

  @Test
  void testGetAll() {
    List<SupervisorDTO> array = new ArrayList<>();
    when(supervisorDAO.readAll()).thenReturn(array);
    assertEquals(array, supervisorUCC.getAll());
  }

  @Test
  void testAddOne() {
    SupervisorDTO supervisorDTO = domainFactory.getSupervisor();
    when(supervisorDAO.create(supervisorDTO)).thenReturn(supervisorDTO);
    assertEquals(supervisorDTO, supervisorUCC.addOne(supervisorDTO));
  }

  @Test
  void testAddOneWithException() {
    // Créer un DTO de superviseur
    SupervisorDTO supervisorDTO = domainFactory.getSupervisor();

    // Simuler une exception lors de la création du superviseur
    when(supervisorDAO.create(supervisorDTO)).thenThrow(
        new RuntimeException("Database connection error"));

    // Appeler la méthode à tester et vérifier que l'exception est levée
    assertThrows(RuntimeException.class, () -> supervisorUCC.addOne(supervisorDTO));
  }
}