package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.InternshipDTOImpl;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InternshipDAOImpl implements InternshipDAO {

  private final ResultSetMapper<InternshipDTO, InternshipDTOImpl>
      internshipMapper = new ResultSetMapper<>();
  @Inject
  private DALBackService myDalService;
  @Inject
  private DomainFactory myDomainFactory;

  /**
   * Retrieves the entrprise that corresponds to the users internship.
   *
   * @return enterprises.
   */
  @Override
  public InternshipDTO getUserInternship(int id) {
    System.out.println("DAO 1");
    System.out.println(id);
    try {
      PreparedStatement ps = myDalService.getPS(
          "SELECT * FROM projetae.internships WHERE \"user\"= ?");
      ps.setInt(1, id);
      ps.execute();
      System.out.println("DAO 2");
      return internshipMapper.mapResultSetToObject(ps.getResultSet(), InternshipDTOImpl.class,
          myDomainFactory::getInternship);
    } catch (SQLException | IllegalAccessException e) {
      System.out.println("error ici");
      throw new RuntimeException(e); // TODO: handle error
    }
  }

}


