package be.vinci.pae.ucc;

import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.dao.SupervisorDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.Internship;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.DALService;
import be.vinci.pae.utils.NotFoundException;
import jakarta.inject.Inject;

/**
 * Implementation of the internshipUCC interface.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO internshipDAO;

  @Inject
  private ContactDAO contactDAO;

  @Inject
  private SupervisorDAO supervisorDAO;

  @Inject
  private DALService myDALService;


  @Override
  public InternshipDTO getUserInternship(int userId) {
    myDALService.start();
    // get entrprise that corresponds to user intership
    InternshipDTO internshipDTO = internshipDAO.getUserInternship(userId);
    myDALService.commit();

    return internshipDTO;
  }

  @Override
  public InternshipDTO acceptInternship(InternshipDTO internship) {
    myDALService.start();

    if (internshipDAO.getUserInternship(internship.getUser()) != null) {
      throw new BusinessException(409, "user already have an internship");
    }

    // Check contactDTO
    ContactDTO contact = contactDAO.readOne(internship.getContact());

    if (contact == null || contact.getUser() != internship.getUser()) {
      throw new NotFoundException();
    }

    // Set enterprise
    internship.setEnterprise(contact.getEnterprise());
    if (internship.getSupervisorDTO() != null) {
      internship.getSupervisorDTO().setEnterprise(contact.getEnterprise());
    }

    // Check supervisorDTO
    SupervisorDTO supervisor;
    if (internship.getSupervisor() != 0) {
      supervisor = supervisorDAO.readOne(internship.getSupervisor());
      if (supervisor == null) {
        throw new NotFoundException();
      }
      System.out.println(supervisor.getEnterprise());
      System.out.println(internship.getEnterprise());
      if (supervisor.getEnterprise() != internship.getEnterprise()) {
        throw new NotFoundException();
      }
    } else {
      if (supervisorDAO.getResponsibleByEnterpriseId(internship.getEnterprise()) != null) {
        throw new BusinessException(409, "supervisor already exists for this enterprise");
      }
      supervisor = supervisorDAO.create(internship.getSupervisorDTO());
      internship.setSupervisor(supervisor.getSupervisorId());
    }

    // Set DTOs
    internship.setContactDTO(contact);
    internship.setSupervisorDTO(supervisor);

    // Accept
    if (!((Internship) internship).accept()) {
      throw new BusinessException(403, "contact must be in meet state");
    }

    internship = internshipDAO.create(internship);
    contactDAO.update(contact);

    myDALService.commit();
    return internship;
  }
}
