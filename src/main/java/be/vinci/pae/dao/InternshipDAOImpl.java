package be.vinci.pae.dao;

import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.InternshipDTO;
import be.vinci.pae.domain.InternshipImpl;
import be.vinci.pae.utils.FatalErrorException;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents the implementation of the internshipDAO interface.
 */
public class InternshipDAOImpl implements InternshipDAO {


  private final ResultSetMapper<InternshipDTO, InternshipImpl>
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
    try {
      PreparedStatement ps = myDalService.getPS(
          "SELECT * FROM projetae.internships WHERE \"user\"= ?");
      ps.setInt(1, id);
      ps.execute();
      return internshipMapper.mapResultSetToObject(ps.getResultSet(), InternshipImpl.class,
          myDomainFactory::getInternship);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public InternshipDTO create(InternshipDTO internship) {
    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.internships (subject, year, \"user\", enterprise, supervisor, contact, version)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, internship.getSubject());
      ps.setString(2, internship.getYear());
      ps.setInt(3, internship.getUser());
      ps.setInt(4, internship.getEnterprise());
      ps.setInt(5, internship.getSupervisor());
      ps.setInt(6, internship.getContact());
      ps.setInt(7, 1);
      ps.execute();
      return internshipMapper.mapResultSetToObject(ps.getResultSet(), InternshipImpl.class,
          myDomainFactory::getInternship);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }
}


