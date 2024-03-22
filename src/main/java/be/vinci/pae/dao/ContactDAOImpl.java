package be.vinci.pae.dao;

import be.vinci.pae.domain.Contact;
import be.vinci.pae.domain.ContactImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.utils.DALService;
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

  private DALService myDalService = new DALServiceImpl();

  @Inject
  private DomainFactory myDomainFactory;

  private final ResultSetMapper<Contact, ContactImpl> contactMapper = new ResultSetMapper<>();

  @Override
  public Contact readOne(int contactId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE contact_id = ?;")) {
      ps.setInt(1, contactId);
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException | InstantiationException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public Contact readOne(int userId, int enterpriseId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE \"user\" = ? AND enterprise = ?;")) {
      ps.setInt(1, userId);
      ps.setInt(2, enterpriseId);
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException | InstantiationException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public List<Contact> readMany(int userId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE \"user\" = ?")) {
      ps.setInt(1, userId);
      ps.execute();
      return contactMapper.mapResultSetToObjectList(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException | InstantiationException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public Contact create(String status, String year, int userId, int enterpriseId) {
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
    } catch (SQLException | IllegalAccessException | InstantiationException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }

  @Override
  public Contact update(Contact newContact) {
    try (PreparedStatement ps = myDalService.getPS(
        "UPDATE projetae.contacts SET meeting_point = ?, state = ?, refusal_reason = ?,"
            + " year = ?, \"user\" = ?, enterprise = ? WHERE contact_id = ? RETURNING *;")) {
      ps.setString(1, newContact.getMeetingPoint());
      ps.setString(2, newContact.getState());
      ps.setString(3, newContact.getRefusalReason());
      ps.setString(4, newContact.getYear());
      ps.setInt(5, newContact.getUser());
      ps.setInt(6, newContact.getEnterprise());
      ps.setInt(7, newContact.getContactId());
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException | InstantiationException e) {
      throw new RuntimeException(e); // TODO: handle error
    }
  }
}
