package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.ContactDAO;
import be.vinci.pae.dao.EnterpriseDAO;
import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.dao.SupervisorDAO;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.Internship;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.NotFoundException;
import jakarta.inject.Inject;

/**
 * Implementation of the internshipUCC interface.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO internshipDAO;

  @Inject
  private EnterpriseDAO enterpriseDAO;
  @Inject
  private ContactDAO contactDAO;

  @Inject
  private SupervisorDAO supervisorDAO;

  @Inject
  private DALService myDALService;

  @Override
  public InternshipDTO getUserInternship(int userId) {
    myDALService.start();
    InternshipDTO internshipDTO = internshipDAO.getUserInternship(userId);
    if (internshipDTO == null) {
      throw new NotFoundException();
    }

    EnterpriseDTO enterpriseDTO = enterpriseDAO.readOne(internshipDTO.getEnterprise());
    if (enterpriseDTO == null) {
      throw new NotFoundException();
    }
    internshipDTO.setEnterpriseDTO(enterpriseDTO);

    ContactDTO contactDTO = contactDAO.readOne(internshipDTO.getContact());
    if (contactDTO == null) {
      throw new NotFoundException();
    }
    internshipDTO.setContactDTO(contactDTO);

    SupervisorDTO supervisorDTO = supervisorDAO.readOne(internshipDTO.getSupervisor());
    if (supervisorDTO == null) {
      throw new NotFoundException();
    }
    internshipDTO.setSupervisorDTO(supervisorDTO);

    myDALService.commit();
    return internshipDTO;
  }

  /**
   * Accepts the internship and performs necessary validations and updates.
   *
   * @param internship The InternshipDTO object representing the internship to
   *                   accept.
   * @return The updated InternshipDTO object after accepting the internship.
   */
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
