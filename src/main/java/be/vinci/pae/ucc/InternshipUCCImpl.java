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
  private ContactUCC contactUCC;

  @Inject
  private SupervisorDAO supervisorDAO;
  @Inject
  private SupervisorUCC supervisorUCC;

  @Inject
  private DALService myDALService;

  @Override
  public InternshipDTO getUserInternship(int userId) {
    try {
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
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public InternshipDTO acceptInternship(InternshipDTO internship) {
    try {
      myDALService.start();

      if (internshipDAO.getUserInternship(internship.getUser()) != null) {
        throw new BusinessException(409, "user already have an internship");
      }

      // Check contactDTO
      ContactDTO contact = contactUCC.accept(internship.getUser(), internship.getContact());

      if (contact.getUser() != internship.getUser()) {
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
        supervisor = supervisorUCC.getResponsibleByEnterpriseId(internship.getEnterprise());
        if (supervisor == null) {
          throw new NotFoundException();
        }
      } else {
        if (supervisorDAO.getResponsibleByEnterpriseId(internship.getEnterprise()) != null) {
          throw new BusinessException(409, "supervisor already exists for this enterprise");
        }
        supervisor = supervisorUCC.addOne(internship.getSupervisorDTO());
        internship.setSupervisorDTO(supervisor);
        internship.setSupervisor(supervisor.getSupervisorId());
      }

      // Set DTOs
      internship.setContactDTO(contact);
      internship.setSupervisorDTO(supervisor);

      // Accept
      ((Internship) internship).accept();

      internship = internshipDAO.create(internship);

      myDALService.commit();
      return internship;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }

  @Override
  public InternshipDTO modifySubject(int userId, String subject) {
    myDALService.start();
    InternshipDTO internship = internshipDAO.getUserInternship(userId);
    System.out.println(internship);
    if (internship == null) {
      throw new NotFoundException();
    }
    // TODO: add check contact state accepted
    internship.setSubject(subject);
    InternshipDTO updatedInternship = internshipDAO.update(internship);
    myDALService.commit();
    return updatedInternship;
  }
}
