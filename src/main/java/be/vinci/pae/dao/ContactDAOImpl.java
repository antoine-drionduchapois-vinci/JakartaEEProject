package be.vinci.pae.dao;

import be.vinci.pae.dal.DALBackService;
import be.vinci.pae.domain.ContactDTO;
import be.vinci.pae.domain.ContactImpl;
import be.vinci.pae.domain.DomainFactory;
import be.vinci.pae.utils.BusinessException;
import be.vinci.pae.utils.FatalErrorException;
import be.vinci.pae.utils.NotFoundException;
import be.vinci.pae.utils.ResultSetMapper;
import jakarta.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents the implementation of the ContactDAO interface.
 */
public class ContactDAOImpl implements ContactDAO {

  @Inject
  private DALBackService myDalService;

  @Inject
  private DomainFactory myDomainFactory;

  private ResultSetMapper<ContactDTO, ContactImpl> contactMapper = new ResultSetMapper<>();

  @Override
  public ContactDTO readOne(int contactId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE contact_id = ?;")) {
      ps.setInt(1, contactId);
      ps.execute();
      ContactDTO contact = contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
      if (contact == null) {
        throw new NotFoundException();
      }
      return contact;
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
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
      throw new FatalErrorException(e);
    }
  }

  @Override
  public ContactDTO create(int userId, int enterpriseId) {
    if (exists(userId, enterpriseId)) {
      throw new BusinessException(409,
          "contact with user: " + userId + " and enterprise " + enterpriseId + " already exists");
    }
    try (PreparedStatement ps = myDalService.getPS(
        "INSERT INTO projetae.contacts (state, year, \"user\", enterprise, version)"
            + "VALUES (?, ?, ?, ?, ?) RETURNING *;")) {
      ps.setString(1, "initiated");
      ps.setString(2, getCurrentYearString());
      ps.setInt(3, userId);
      ps.setInt(4, enterpriseId);
      ps.setInt(5, 1);

      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  @Override
  public ContactDTO update(ContactDTO newContactDTO) {
    try (PreparedStatement ps = myDalService.getPS(
        "UPDATE projetae.contacts SET meeting_point = ?, state = ?, "
            + "refusal_reason = ?, version = ?,"
            + " year = ?, \"user\" = ?, enterprise = ? WHERE contact_id = ? AND version = ? "
            + "RETURNING *;")) {
      ps.setString(1, newContactDTO.getMeetingPoint());
      ps.setString(2, newContactDTO.getState());
      ps.setString(3, newContactDTO.getRefusalReason());
      ps.setInt(4, newContactDTO.getVersion() + 1);
      ps.setString(5, newContactDTO.getYear());
      ps.setInt(6, newContactDTO.getUser());
      ps.setInt(7, newContactDTO.getEnterprise());
      ps.setInt(8, newContactDTO.getContactId());
      ps.setInt(9, newContactDTO.getVersion());
      ps.execute();
      return contactMapper.mapResultSetToObject(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      throw new FatalErrorException(e);
    }
  }

  private boolean exists(int userId, int enterpriseId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT COUNT(*) FROM projetae.contacts WHERE \"user\" = ? AND enterprise = ?")) {
      ps.setInt(1, userId);
      ps.setInt(2, enterpriseId);
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

  @Override
  public List<ContactDTO> readEnterpriseContacts(int enterpriseId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE enterprise  = ?")) {
      ps.setInt(1, enterpriseId);
      ps.execute();
      return contactMapper.mapResultSetToObjectList(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      System.out.println("DAO sql error");
      throw new FatalErrorException(e);
    }
  }

  @Override
  public List<ContactDTO> readEnterpriseInitiatedOrMeetContacts(int enterpriseId) {
    try (PreparedStatement ps = myDalService.getPS(
        "SELECT * FROM projetae.contacts WHERE (state = 'initiated' OR state = 'meet')"
            + " AND enterprise = ? AND year = ?")) {
      ps.setInt(1, enterpriseId);
      ps.setString(2, getCurrentYearString());
      ps.execute();
      return contactMapper.mapResultSetToObjectList(ps.getResultSet(), ContactImpl.class,
          myDomainFactory::getContact);
    } catch (SQLException | IllegalAccessException e) {
      System.out.println("DAO sql error");
      throw new FatalErrorException(e);
    }
  }

  private String getCurrentYearString() {
    LocalDate currentDate = LocalDate.now();
    LocalDate startDate = LocalDate.of(currentDate.getYear() - 1, 9, 1);
    LocalDate endDate = LocalDate.of(currentDate.getYear(), 9, 1);
    return startDate.getYear() + "-" + endDate.getYear();
  }
}
