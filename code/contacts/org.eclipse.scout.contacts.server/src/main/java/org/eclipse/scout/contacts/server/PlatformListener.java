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

import java.util.Date;
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

  private static final String ORGANISATION1 = "Alice's Adventures in Wonderland";
  private static final String ORGANISATION2 = "The Hobbit";

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
            createOrganizationTable(tables);
            createPersonTable(tables);
          }
        });
      }
      catch (ProcessingException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  protected Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  private void createOrganizationTable(Set<String> tables) {
    if (!tables.contains("ORGANIZATION")) {
      SQL.insert(SQLs.ORGANIZATION_CREATE_TABLE);
      LOG.info("Database table 'ORGANIZATION' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createOrganizationEntry(ORGANISATION1, "London", "GB", "http://en.wikipedia.org/wiki/Alice%27s_Adventures_in_Wonderland", null);
        createOrganizationEntry("BSI Business Systems Integration AG", "Daettwil, Baden", "CH", "https://www.bsi-software.com", "https://www.eclipsecon.org/europe2015/sites/default/files/styles/medium/public/v2%20BSI%20logo.png?itok=Xf3NGTpD");
        createOrganizationEntry(ORGANISATION2, null, "NZ", "https://en.wikipedia.org/wiki/The_Hobbit", null);

        LOG.info("Database table 'ORGANIZATION' populated with sample data");
      }
    }
  }

  private void createOrganizationEntry(String name, String city, String country, String url, String logoUrl) {
    SQL.insert(SQLs.ORGANIZATION_INSERT_SAMPLE_DATA, new NVPair("organization_id", UUID.randomUUID().toString()), new NVPair("name", name), new NVPair("city", city), new NVPair("country", country), new NVPair("url", url), new NVPair("logoUrl", url));
  }

  private void createPersonTable(Set<String> tables) {
    if (!tables.contains("PERSON")) {
      SQL.insert(SQLs.PERSON_CREATE_TABLE);
      LOG.info("Database table 'PERSON' created");

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createPersonEntry("Alice", null, "http://www.uergsel.de/uploads/Alice.png", DateUtility.parse("26.11.1865", "dd.MM.yyyy"), "F", "Daresbury, Cheshire", "GB", "The curious girl", ORGANISATION1);
        createPersonEntry("Rabbit", "the White", "http://upload.wikimedia.org/wikipedia/commons/e/ec/Down_the_Rabbit_Hole.png", DateUtility.parse("26.11.1861", "dd.MM.yyyy"), "F", "Daresbury, Cheshire", "GB", "Follow me", ORGANISATION1);
        createPersonEntry("Bilbo", "Beutlin", null, null, "M", null, "NZ", null, ORGANISATION2);
        createPersonEntry("Gandalf", null, null, null, "M", null, "NZ", null, ORGANISATION2);
        createPersonEntry("Gollum", null, null, null, "M", null, "NZ", null, ORGANISATION2);
        createPersonEntry("Thorin", "Oakenshield", null, null, "M", null, "NZ", null, ORGANISATION2);
        createPersonEntry("Smaug", null, null, null, "M", null, "NZ", null, ORGANISATION2);
        createPersonEntry("Bard", null, null, null, "M", null, "NZ", null, ORGANISATION2);
        createPersonEntry("Elrond", null, null, null, "M", null, "NZ", null, ORGANISATION2);

        LOG.info("Database table 'PERSON' populated with sample data");
      }
    }
  }

  private void createPersonEntry(String firstName, String lastName, String pictureUrl, Date dob, String gender, String city, String country, String position, String organizationId) {
    SQL.insert(SQLs.PERSON_INSERT_SAMPLE_DATA,
        new NVPair("person_id", UUID.randomUUID().toString()),
        new NVPair("firstName", firstName),
        new NVPair("lastName", lastName),
        new NVPair("pictureUrl", pictureUrl),
        new NVPair("dob", dob),
        new NVPair("gender", gender),
        new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("position", position),
        new NVPair("organizationId", organizationId)
        );
  }
}
