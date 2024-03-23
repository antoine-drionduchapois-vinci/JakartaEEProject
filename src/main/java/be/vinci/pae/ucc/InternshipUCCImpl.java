package be.vinci.pae.ucc;

import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.domain.Internship;
import be.vinci.pae.utils.DALService;
import jakarta.inject.Inject;

public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO InternshipDAO;

  @Inject
  private DALService myDALService;


  @Override
  public Internship getUserInternship(int userId) {
    myDALService.start();
    // get entrprise that corresponds to user intership
    Internship internship = InternshipDAO.getUserInternship(userId);
    myDALService.commit();

    return internship;
  }
}
