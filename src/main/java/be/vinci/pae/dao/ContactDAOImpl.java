package be.vinci.pae.dao;

import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.ContactImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.utils.DALBackService;
import be.vinci.pae.utils.DALServiceImpl;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Represents the implementation of the ContactDAO interface.
 */
public class ContactDAOImpl implements ContactDAO {

  private DALBackService myDalService = new DALServiceImpl();

  @Inject
  private DomainFactory myDomainFactory;

  private ResultSetMapper<ContactDTO, ContactImpl> contactMapper = new ResultSetMapper<>();

  @Override
  public ContactDTO readOne(int contactId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE contact_id = ?;")) {
      ps.setInt(1, contactId);
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public ContactDTO readOne(int userId, int enterpriseId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE \"user\" = ? AND enterprise = ?;")) {
      ps.setInt(1, userId);
      ps.setInt(2, enterpriseId);
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public List<ContactDTO> readMany(int userId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE \"user\" = ?")) {
      ps.setInt(1, userId);
      ps.execute();
      return contactMapper.mapResultSetToObjectList(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public ContactDTO create(String status, String year, int userId, int enterpriseId) {
    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.contacts (state, year, \"user\", enterprise)"
            + "VALUES (?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, status);
      ps.setString(2, year);
      ps.setInt(3, userId);
      ps.setInt(4, enterpriseId);
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public ContactDTO update(ContactDTO newContactDTO) {
    try (PreparedStatement ps = myDalService.getPS(
        "UPDATE projetae.contacts SET meeting_point = ?, state = ?, refusal_reason = ?,"
            + " year = ?, \"user\" = ?, enterprise = ? WHERE contact_id = ? RETURNING *;")) {
      ps.setString(1, newContactDTO.getMeetingPoint());
      ps.setString(2, newContactDTO.getState());
      ps.setString(3, newContactDTO.getRefusalReason());
      ps.setString(4, newContactDTO.getYear());
      ps.setInt(5, newContactDTO.getUser());
      ps.setInt(6, newContactDTO.getEnterprise());
      ps.setInt(7, newContactDTO.getContactId());
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }
}