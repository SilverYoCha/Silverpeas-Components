/*
 * Copyright (C) 2000 - 2018 Silverpeas
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * As a special exception to the terms and conditions of version 3.0 of the GPL, you may
 * redistribute this Program in connection with Free/Libre Open Source Software ("FLOSS")
 * applications as described in Silverpeas's FLOSS exception. You should have received a copy of the
 * text describing the FLOSS exception, and it is also available here:
 * "http://www.silverpeas.org/docs/core/legal/floss_exception.html"
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */
package com.silverpeas.mydb.control.ejb;

import com.silverpeas.mydb.model.MyDBConnectionInfoDetail;
import com.silverpeas.mydb.model.MyDBConnectionInfoPK;
import org.silverpeas.core.persistence.jdbc.bean.PersistenceException;
import org.silverpeas.core.persistence.jdbc.bean.SilverpeasBeanDAO;
import org.silverpeas.core.persistence.jdbc.bean.SilverpeasBeanDAOFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.Collection;

/**
 * MyDB EJB-tier controller of the MVC.
 *
 * @author Antoine HEDIN
 */
@Stateless(name="MyDB", description = "Stateless session bean to manage the MyDB component.")
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class MyDBBmEJB implements MyDBBm {

  private static final long serialVersionUID = -3635064500545742524L;
  private static final String BEAN_NAME = MyDBConnectionInfoDetail.class.getName();

  @Override
  public Collection<MyDBConnectionInfoDetail> getConnectionList(MyDBConnectionInfoPK pk)
      throws PersistenceException {
    return getMyDBConnectionDao().findByWhereClause(pk, "instanceId = " + "'" + pk.
        getComponentName() + "'");
  }

  @Override
  public MyDBConnectionInfoDetail getConnection(MyDBConnectionInfoPK pk) throws PersistenceException {
    return getMyDBConnectionDao().findByPrimaryKey(pk);
  }

  @Override
  public MyDBConnectionInfoPK addConnection(MyDBConnectionInfoDetail detail) throws
      PersistenceException {
    return (MyDBConnectionInfoPK) getMyDBConnectionDao().add(detail);
  }

  @Override
  public void updateConnection(MyDBConnectionInfoDetail detail) throws PersistenceException {
    getMyDBConnectionDao().update(detail);
  }

  @Override
  public void removeConnection(MyDBConnectionInfoPK pk) throws PersistenceException {
    getMyDBConnectionDao().remove(pk);
  }

  private SilverpeasBeanDAO<MyDBConnectionInfoDetail> getMyDBConnectionDao() throws
      PersistenceException {
    return SilverpeasBeanDAOFactory.<MyDBConnectionInfoDetail>getDAO(BEAN_NAME);
  }
}