package be.vinci.pae.dao;

import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.SupervisorDTO;
import be.vinci.pae.domain.SupervisorImpl;
import be.vinci.pae.utils.FatalErrorException;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the implementation of the ResponsibleDAO interface.
 */
public class SupervisorDAOImpl implements SupervisorDAO {

  private final ResultSetMapper<SupervisorDTO, SupervisorImpl> supervisorMapper =
      new ResultSetMapper<>();
  @Inject
  private DALBackService myDalService;
  @Inject
  private DomainFactory myDomainFactory;

  @Override
  public SupervisorDTO getResponsibleByEnterpriseId(int id) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.supervisors WHERE enterprise = ?")) {
      ps.setInt(1, id);
      ps.execute();
      return supervisorMapper.mapResultSetToObject(ps.getResultSet(), SupervisorImpl.class,
          myDomainFactory::getSupervisor);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public SupervisorDTO create(SupervisorDTO supervisor) {
    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.supervisors(name, surname, phone, email, enterprise, version)"
            + " VALUES (?, ?, ?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, supervisor.getName());
      ps.setString(2, supervisor.getSurname());
      ps.setString(3, supervisor.getPhone());
      ps.setString(4, supervisor.getEmail());
      ps.setInt(5, supervisor.getEnterprise());
      ps.setInt(6, 1);
      ps.execute();
      return supervisorMapper.mapResultSetToObject(ps.getResultSet(), SupervisorImpl.class,
          myDomainFactory::getSupervisor);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public SupervisorDTO readOne(int supervisorId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.supervisors WHERE supervisor_id = ?;")) {
      ps.setInt(1, supervisorId);
      ps.execute();
      return supervisorMapper.mapResultSetToObject(ps.getResultSet(),
          SupervisorImpl.class,
          myDomainFactory::getSupervisor);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public List<SupervisorDTO> readAll() {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.supervisors;")) {
      ps.execute();
      return supervisorMapper.mapResultSetToObjectList(ps.getResultSet(), SupervisorImpl.class,
          myDomainFactory::getSupervisor);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }
}
