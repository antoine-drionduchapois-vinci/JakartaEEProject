package be.vinci.pae.dao;

import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.Enterprise;
import be.vinci.pae.domain.EnterpriseImpl;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.FatalErrorException;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the implementation of the EnterpriseDAO interface.
 */
public class EnterpriseDAOImpl implements EnterpriseDAO {

  @Inject
  private DALBackService myDalService;

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
      throw new FatalErrorException(e);
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
      throw new FatalErrorException(e);
    }
  }

  @Override
  public Enterprise create(String name, String label, String adress, String phone, String email) {
    if (exists(name, label)) {
      throw new BusinessException(409,
          "enterprise with name: " + name + " and label: " + label + " already exists!");
    }
    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.enterprises (name, label, address, phone, email)"
            + "VALUES (?, ?, ?, ?,  ?) RETURNING *;")) {
      ps.setString(1, name);
      ps.setString(2, label);
      ps.setString(3, adress);
      ps.setString(4, phone);
      ps.setString(5, email);
      ps.execute();
      return enterpriseMapper.mapResultSetToObject(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public List<Enterprise> getAllEnterprises() {
    try (PreparedStatement ps = myDalService.getPS("SELECT * FROM projetae.enterprises")) {
      ps.execute();
      return enterpriseMapper.mapResultSetToObjectList(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
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
      throw new FatalErrorException(e);
    }
  }

  private boolean exists(String name, String label) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT COUNT(*) FROM projetae.enterprises WHERE name = ? AND label = ?")) {
      ps.setString(1, name);
      ps.setString(2, label);
      ps.execute();
      ResultSet rs = ps.getResultSet();
      rs.next();
      if (rs.getInt(1) == 0) {
        return false;
      }
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
    return true;
  }
}
