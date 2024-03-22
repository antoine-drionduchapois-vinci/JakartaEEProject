package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.domain.EnterpriseImpl;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.DALServiceImpl;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the implementation of the EnterpriseDAO interface.
 */
public class EnterpriseDAOImpl implements EnterpriseDAO {

  private DALBackService myDalService = new DALServiceImpl();

  private final ResultSetMapper<Enterprise, EnterpriseImpl> enterpriseMapper =
      new ResultSetMapper<>();

  @Inject
  private DomainFactory myDomainFactory;

  @Override
  public Enterprise readOne(int enterpriseId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.enterprises WHERE enterprise_id = ?;")) {
      ps.setInt(1, enterpriseId);
      ps.execute();
      return enterpriseMapper.mapResultSetToObject(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public Enterprise readOne(String enterpriseName, String enterpriseLabel) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.enterprises WHERE name = ? AND label = ?;")) {
      ps.setString(1, enterpriseName);
      ps.setString(2, enterpriseLabel);
      ps.execute();
      return enterpriseMapper.mapResultSetToObject(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public Enterprise create(String name, String label, String adress, String contact) {
    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.enterprises (name, label, address, contact_infos)"
            + "VALUES (?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, name);
      ps.setString(2, label);
      ps.setString(3, adress);
      ps.setString(4, contact);
      ps.execute();
      return enterpriseMapper.mapResultSetToObject(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public List<Enterprise> getAllEnterprises() {
    try (PreparedStatement ps = myDalService.getPS("SELECT * FROM projetae.enterprises")) {
      ps.execute();
      return enterpriseMapper.mapResultSetToObjectList(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  /**
   * Retrieves the entrprise that corresponds to the users internship.
   *
   * @return enterprises.
   */
  @Override
  public Enterprise getEnterpriseById(int id) {
    PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.enterprises e, projetae.internships i "
            + "WHERE i.enterprise = e.enterprise_id AND i.user = ?");
    try {
      ps.setInt(1, id);
      ps.execute();
      return enterpriseMapper.mapResultSetToObject(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }
}