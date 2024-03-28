package be.vinci.pae.ucc;

import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.utils.DALService;
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
    myDALService.start();
    // get entrprise that corresponds to user intership
    InternshipDTO internshipDTO = internshipDAO.getUserInternship(userId);
    myDALService.commit();

    return internshipDTO;
  }
}
