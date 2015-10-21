/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server;

import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringArrayHolder;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.contacts.server.sql.SQLs;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.util.DateUtility;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;

public class PlatformListener implements IPlatformListener {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(PlatformListener.class);

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      autoCreateDatabase();
    }
  }

  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoCreateProperty.class)) {

      try {
        ServerRunContext superUserRunContext = BEANS.get(SuperUserRunContextProvider.class).provide();
        superUserRunContext.run(new IRunnable() {

          @Override
          public void run() throws Exception {
            Set<String> tables = getExistingTables();
            createCompanyTable(tables);
            createContactTable(tables);
          }
        });
      }
      catch (ProcessingException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  protected Set<String> getExistingTables() throws ProcessingException {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  private void createCompanyTable(Set<String> tables) throws ProcessingException {
    if (!tables.contains("COMPANY")) {
      SQL.insert(SQLs.COMPANY_CREATE_TABLE);
      LOG.info("Database table 'COMPANY' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.COMPANY_INSERT_SAMPLE_DATA_1, new NVPair("company_id", UUID.randomUUID().toString()));
        SQL.insert(SQLs.COMPANY_INSERT_SAMPLE_DATA_2, new NVPair("company_id", UUID.randomUUID().toString()));

        LOG.info("Database table 'COMPANY' populated with sample data");
      }
    }
  }

  private void createContactTable(Set<String> tables) throws ProcessingException {
    if (!tables.contains("CONTACT")) {
      SQL.insert(SQLs.CONTACT_CREATE_TABLE);
      LOG.info("Database table 'CONTACT' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.CONTACT_INSERT_SAMPLE_DATA_1, new NVPair("person_id", UUID.randomUUID().toString()), new NVPair("dob", DateUtility.parse("26.11.1865", "dd.MM.yyyy")));
        SQL.insert(SQLs.CONTACT_INSERT_SAMPLE_DATA_2, new NVPair("person_id", UUID.randomUUID().toString()), new NVPair("dob", DateUtility.parse("26.11.1861", "dd.MM.yyyy")));
        LOG.info("Database table 'CONTACT' populated with sample data");
      }
    }
  }
}
