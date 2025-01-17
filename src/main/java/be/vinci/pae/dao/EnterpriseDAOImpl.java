package be.vinci.pae.dao;

import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.domain.EnterpriseDTO;
import be.vinci.pae.domain.EnterpriseImpl;
import be.vinci.pae.utils.BusinessException;
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

  private final ResultSetMapper<EnterpriseDTO, EnterpriseImpl> enterpriseMapper = new ResultSetMapper<>();

  @Inject
  private DomainFactory myDomainFactory;

  @Override
  public EnterpriseDTO readOne(int enterpriseId) {
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
  public EnterpriseDTO readOne(String enterpriseName, String enterpriseLabel) {
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
  public EnterpriseDTO create(String name, String label, String adress,
      String phone, String email) {
    if (exists(name, label)) {
      throw new BusinessException(409,
          "enterprise with name: " + name + " and label: " + label + " already exists!");
    }
    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.enterprises (name, label, address, phone, email, is_blacklisted, version)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, name);
      ps.setString(2, label);
      ps.setString(3, adress);
      ps.setString(4, phone);
      ps.setString(5, email);
      ps.setBoolean(6, false);
      ps.setInt(7, 1);

      ps.execute();
      return enterpriseMapper.mapResultSetToObject(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public List<EnterpriseDTO> getAllEnterprises() {
    try (PreparedStatement ps = myDalService.getPS("SELECT * FROM projetae.enterprises")) {
      ps.execute();
      return enterpriseMapper.mapResultSetToObjectList(ps.getResultSet(), EnterpriseImpl.class,
          myDomainFactory::getEnterprise);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public EnterpriseDTO getEnterpriseById(int id) {
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

  @Override
  public EnterpriseDTO update(EnterpriseDTO newEnterprise) {
    try (PreparedStatement ps = myDalService.getPS(
        "UPDATE projetae.enterprises SET name = ?, label = ?, address = ?, phone = ?,"
            + " email = ?, is_blacklisted = ?, blacklisted_reason = ?, version = ?"
            + " WHERE enterprise_id = ? AND version = ? RETURNING *;")) {
      ps.setString(1, newEnterprise.getName());
      ps.setString(2, newEnterprise.getLabel());
      ps.setString(3, newEnterprise.getAddress());
      ps.setString(4, newEnterprise.getPhone());
      ps.setString(5, newEnterprise.getEmail());
      ps.setBoolean(6, newEnterprise.isBlacklisted());
      ps.setString(7, newEnterprise.getBlacklistedReason());
      ps.setInt(8, newEnterprise.getVersion() + 1);
      ps.setInt(9, newEnterprise.getEnterpriseId());
      ps.setInt(10, newEnterprise.getVersion());
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
      if (rs.next() && rs.getInt(1) == 1) {
        return true;
      }
    } catch (SQLException e) {
      throw new FatalErrorException(e);
    }
    return false;
  }
}
