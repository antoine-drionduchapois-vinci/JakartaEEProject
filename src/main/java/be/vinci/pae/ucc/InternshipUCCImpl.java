package be.vinci.pae.ucc;

import be.vinci.pae.dao.InternshipDAO;
import be.vinci.pae.domain.Internship;
import jakarta.inject.Inject;

public class InternshipUCCImpl implements InternshipUCC {

  @Inject
  private InternshipDAO InternshipDAO;

  @Override
  public Internship getUserInternship(int userId) {

    // get entrprise that corresponds to user intership
    Internship internship = InternshipDAO.getUserInternship(userId);

    return internship;
  }
}
