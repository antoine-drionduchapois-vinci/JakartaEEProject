package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.Supervisor;
import be.vinci.pae.domain.SupervisorImpl;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.FatalErrorException;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents the implementation of the ResponsibleDAO interface.
 */
public class SupervisorDAOImpl implements SupervisorDAO {

  @Inject
  private DALBackService myDalService;

  @Inject
  private DomainFactory myDomainFactory;

  private final ResultSetMapper<Supervisor, SupervisorImpl> supervisorMapper =
      new ResultSetMapper<>();

  @Override
  public Supervisor getResponsibleByEnterpriseId(int id) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.supervisor WHERE enterprise = ?")) {
      ps.setInt(1, id);
      ps.execute();
      return supervisorMapper.mapResultSetToObject(ps.getResultSet(), SupervisorImpl.class,
          myDomainFactory::getSupervisor);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }
}
