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
package org.eclipse.scout.contacts.event.server;

import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.commons.CollectionUtility;
import org.eclipse.scout.commons.IRunnable;
import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.holders.NVPair;
import org.eclipse.scout.commons.holders.StringArrayHolder;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.contacts.event.server.sql.SQLs;
import org.eclipse.scout.contacts.event.shared.contact.ContactFormTabExtensionData;
import org.eclipse.scout.contacts.event.shared.contact.ContactTableDataExtension;
import org.eclipse.scout.contacts.server.ConfigProperties;
import org.eclipse.scout.contacts.server.SuperUserRunContextProvider;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.IPlatform.State;
import org.eclipse.scout.rt.platform.IPlatformListener;
import org.eclipse.scout.rt.platform.PlatformEvent;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.util.DateUtility;
import org.eclipse.scout.rt.server.context.ServerRunContext;
import org.eclipse.scout.rt.server.services.common.jdbc.SQL;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;

@Order(1000)
public class PlatformListener implements IPlatformListener {
  private static final IScoutLogger LOG = ScoutLogManager.getLogger(PlatformListener.class);

  @Override
  public void stateChanged(PlatformEvent event) {
    if (event.getState() == State.BeanManagerValid) {
      autoCreateDatabase();
      registerExtensions();
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
            createEventTable(tables);
            createParticipantTable(tables);
          }
        });
      }
      catch (ProcessingException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  private void registerExtensions() {
    IExtensionRegistry extensionRegistry = BEANS.get(IExtensionRegistry.class);

    // Register DTO core extensions
    extensionRegistry.register(ContactTableDataExtension.class);
    extensionRegistry.register(ContactFormTabExtensionData.class);
  }

  protected Set<String> getExistingTables() throws ProcessingException {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  private void createEventTable(Set<String> tables) throws ProcessingException {
    if (!tables.contains("EVENT")) {
      SQL.insert(SQLs.EVENT_CREATE_TABLE);
      LOG.info("Database table 'EVENT' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.COMPANY_INSERT_SAMPLE_DATA_1, new NVPair("eventId", UUID.randomUUID().toString()), new NVPair("starts", DateUtility.parse("09.03.2015 09:00", "dd.MM.yyyy HH:mm")),
            new NVPair("ends", DateUtility.parse("12.03.2015 16:45", "dd.MM.yyyy HH:mm")));
        SQL.insert(SQLs.COMPANY_INSERT_SAMPLE_DATA_2, new NVPair("eventId", UUID.randomUUID().toString()), new NVPair("starts", DateUtility.parse("24.03.2015 09:00", "dd.MM.yyyy HH:mm")),
            new NVPair("ends", DateUtility.parse("26.03.2015 17:00", "dd.MM.yyyy HH:mm")));
        SQL.insert(SQLs.COMPANY_INSERT_SAMPLE_DATA_3, new NVPair("eventId", UUID.randomUUID().toString()), new NVPair("starts", DateUtility.parse("23.06.2015 17:30", "dd.MM.yyyy HH:mm")),
            new NVPair("ends", DateUtility.parse("23.06.2015 23:00", "dd.MM.yyyy HH:mm")));

        LOG.info("Database table 'EVENT' populated with sample data");
      }
    }
  }

  private void createParticipantTable(Set<String> tables) throws ProcessingException {
    if (!tables.contains("PARTICIPANT")) {
      SQL.insert(SQLs.PARTICIPANT_CREATE_TABLE);
      LOG.info("Database table 'PARTICIPANT' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE_DATA_1);
        SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE_DATA_2);
        LOG.info("Database table 'PARTICIPANT' populated with sample data");
      }
    }
  }
}
