package be.vinci.pae.utils;

import be.vinci.pae.dao.InternshipDAO;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class TestBinder extends AbstractBinder {

  private final InternshipDAO internshipDAO;
  private final DALService dalService;

  public TestBinder(InternshipDAO internshipDAO, DALService dalService) {
    this.internshipDAO = internshipDAO;
    this.dalService = dalService;
  }

  @Override
  protected void configure() {
    bind(internshipDAO).to(InternshipDAO.class);
    bind(dalService).to(DALService.class);

  }
}
