package be.vinci.pae.ucc;

import be.vinci.pae.dao.ResponsibleDAO;
import be.vinci.pae.domain.ResponsibleDTO;
import jakarta.inject.Inject;

public class ResponsibleUCCImpl implements ResponsibleUCC {

  @Inject
  private ResponsibleDAO responsibleDAO;

  @Override
  public ResponsibleDTO getResponsibleByEnterpriseId(int id) {

    ResponsibleDTO responsible = responsibleDAO.getResponsibleByEnterpriseId(id);

    return responsible;
  }


}
