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
package org.eclipse.scout.contacts.server.module.events;

import java.util.Date;
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
import org.eclipse.scout.contacts.server.ConfigProperties;
import org.eclipse.scout.contacts.server.SuperUserRunContextProvider;
import org.eclipse.scout.contacts.server.module.events.sql.SQLs;
import org.eclipse.scout.contacts.shared.module.events.person.PersonFormTabExtensionData;
import org.eclipse.scout.contacts.shared.module.events.person.PersonTablePageDataExtension;
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
    extensionRegistry.register(PersonTablePageDataExtension.class);
    extensionRegistry.register(PersonFormTabExtensionData.class);
  }

  protected Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  private void createEventTable(Set<String> tables) {
    if (!tables.contains("EVENT")) {
      SQL.insert(SQLs.EVENT_CREATE_TABLE);
      LOG.info("Database table 'EVENT' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createEventEntry("EclipseCon 2015", DateUtility.parse("09.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("12.03.2015 16:45", "dd.MM.yyyy HH:mm"), "San Francisco", "US", "https://www.eclipsecon.org/na2015/");
        createEventEntry("JavaLand 2015", DateUtility.parse("24.03.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("26.03.2015 17:00", "dd.MM.yyyy HH:mm"), "Bruehl", "DE", "http://www.javaland.eu/javaland-2015/");
        createEventEntry("EclipseCon Europe 2015", DateUtility.parse("02.11.2015 09:00", "dd.MM.yyyy HH:mm"), DateUtility.parse("05.11.2015 17:00", "dd.MM.yyyy HH:mm"), "Ludwigsburg", "DE", "https://www.eclipsecon.org/europe2015/");
        createEventEntry("Bilbo's Party", null, null, "Shire", "NZ", null);

        LOG.info("Database table 'EVENT' populated with sample data");
      }
    }
  }

  private void createEventEntry(String title, Date starts, Date ends, String city, String country, String url) {
    SQL.insert(SQLs.EVENT_INSERT_SAMPLE_DATA, new NVPair("eventId", UUID.randomUUID().toString()),
        new NVPair("title", title),
        new NVPair("starts", starts),
        new NVPair("ends", ends),
        new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("url", url));
  }

  private void createParticipantTable(Set<String> tables) {
    if (!tables.contains("PARTICIPANT")) {
      SQL.insert(SQLs.PARTICIPANT_CREATE_TABLE);
      LOG.info("Database table 'PARTICIPANT' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createParticipantEntry("EclipseCon Europe 2015", "Rabbit");
        createParticipantEntry("EclipseCon Europe 2015", "Alice");
        createParticipantEntry("JavaLand 2015", "Alice");
        createParticipantEntry("Bilbo's Party", "Alice");
        createParticipantEntry("Bilbo's Party", "Bilbo");
        createParticipantEntry("Bilbo's Party", "Gandalf");
        createParticipantEntry("EclipseCon Europe 2015", "Gandalf");
        createParticipantEntry("Bilbo's Party", "Thorin");

        LOG.info("Database table 'PARTICIPANT' populated with sample data");
      }
    }
  }

  private void createParticipantEntry(String eventTitle, String personFirstName) {
    SQL.insert(SQLs.PARTICIPANT_INSERT_SAMPLE_DATA,
        new NVPair("eventTitle", eventTitle),
        new NVPair("personFirstName", personFirstName));
  }
}
