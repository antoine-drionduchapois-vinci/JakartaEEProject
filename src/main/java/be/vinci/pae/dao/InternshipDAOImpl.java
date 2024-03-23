package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.Internship;
import be.vinci.pae.domain.InternshipImpl;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.DALServiceImpl;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InternshipDAOImpl implements InternshipDAO {

  private final ResultSetMapper<Internship, InternshipImpl> userMapper = new ResultSetMapper<>();
  private DALBackService myDalService = new DALServiceImpl();
  @Inject
  private DomainFactory myDomainFactory;

  /**
   * Retrieves the entrprise that corresponds to the users internship.
   *
   * @return enterprises.
   */
  @Override
  public Internship getUserInternship(int id) {
    PreparedStatement ps = myDalService.getPS("SELECT * FROM projetae.internships WHERE user = ?");
    try {
      ps.setInt(1, id);
      ps.execute();
      return userMapper.mapResultSetToObject(ps.getResultSet(), InternshipImpl.class,
          myDomainFactory::getInternship);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

}


