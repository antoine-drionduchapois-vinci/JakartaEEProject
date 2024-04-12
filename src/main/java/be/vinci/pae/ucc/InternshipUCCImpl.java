package be.vinci.pae.ucc;

import be.vinci.pae.dal.DALService;
import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.domain.InternshipDTO;
import jakarta.inject.Inject;

/**
 * Implementation of the internshipUCC interface.
 */
public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO internshipDAO;

  @Inject
  private DALService myDALService;


  @Override
  public InternshipDTO getUserInternship(int userId) {
    try {
      myDALService.start();
      // get entrprise that corresponds to user intership
      InternshipDTO internshipDTO = internshipDAO.getUserInternship(userId);
      myDALService.commit();

      return internshipDTO;
    } catch (Throwable t) {
      myDALService.rollback();
      throw t;
    }
  }
}
