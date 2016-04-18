package org.eclipse.scout.contacts.server.sql;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import org.eclipse.scout.contacts.server.ConfigProperties;
import org.eclipse.scout.contacts.server.SuperUserRunContextProducer;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.holders.StringArrayHolder;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.concurrent.IRunnable;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//  tag::service[]
@ApplicationScoped
public class DBSetupService {
  private static final Logger LOG = LoggerFactory.getLogger(DBSetupService.class);
  // end::service[]
  public static final UUID ORGANISATION1 = UUID.randomUUID();
  public static final UUID ORGANISATION2 = UUID.randomUUID();

  public static final UUID PERSON01 = UUID.randomUUID();
  public static final UUID PERSON02 = UUID.randomUUID();
  //tag::service[]

  public void autoCreateDatabase() {
    if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoCreateProperty.class)) {

      try {
        BEANS.get(SuperUserRunContextProducer.class).produce().run(new IRunnable() {

          @Override
          public void run() throws Exception {
            Set<String> tables = getExistingTables();
            createOrganizationTable(tables);
            createPersonTable(tables);
          }
        });
      }
      catch (RuntimeException e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    }
  }

  protected Set<String> getExistingTables() {
    StringArrayHolder tables = new StringArrayHolder();
    SQL.selectInto(SQLs.SELECT_TABLE_NAMES, new NVPair("result", tables));
    return CollectionUtility.hashSet(tables.getValue());
  }

  // end::service[]

  public void createOrganizationTable() {
    createOrganizationTable(getExistingTables());
  }

  // tag::service[]
  private void createOrganizationTable(Set<String> tables) {
    if (!tables.contains("ORGANIZATION")) {
      SQL.insert(SQLs.ORGANIZATION_CREATE_TABLE);
      LOG.info("Database table 'ORGANIZATION' created");
      // end::service[]
      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createOrganizationEntry(ORGANISATION1, "Alice's Adventures in Wonderland", "London", "GB", "http://en.wikipedia.org/wiki/Alice%27s_Adventures_in_Wonderland",
            "https://upload.wikimedia.org/wikipedia/en/3/3f/Alice_in_Wonderland%2C_cover_1865.jpg");
        createOrganizationEntry(ORGANISATION2, "BSI Business Systems Integration AG", "Daettwil, Baden", "CH", "https://www.bsi-software.com",
            "https://wiki.eclipse.org/images/4/4f/Bsiag.png");

        LOG.info("Database table 'ORGANIZATION' populated with sample data");
      }
      // tag::service[]
    }
  }

  // end::service[]

  private void createOrganizationEntry(UUID organizationUuid, String name, String city, String country, String url, String logoUrl) {
    SQL.insert(SQLs.ORGANIZATION_INSERT_SAMPLE_DATA, new NVPair("organizationUuid", organizationUuid.toString()), new NVPair("name", name), new NVPair("city", city), new NVPair("country", country), new NVPair("url", url),
        new NVPair("logoUrl", logoUrl));
  }

  // tag::service[]
  private void createPersonTable(Set<String> tables) {
    if (!tables.contains("PERSON")) {
      SQL.insert(SQLs.PERSON_CREATE_TABLE);
      LOG.info("Database table 'PERSON' created");
      // end::service[]

      if (CONFIG.getPropertyValue(ConfigProperties.DatabaseAutoPopulateProperty.class)) {
        createPersonEntry(PERSON01, "Alice", null, "http://www.uergsel.de/uploads/Alice.png", DateUtility.parse("26.11.1865", "dd.MM.yyyy"), "F", "Daresbury, Cheshire", "GB", "The curious girl", ORGANISATION1);
        createPersonEntry(PERSON02, "Rabbit", "the White", "https://upload.wikimedia.org/wikipedia/commons/4/42/The_White_Rabbit_%28Tenniel%29_-_The_Nursery_Alice_%281890%29_-_BL.jpg", DateUtility.parse("26.11.1861", "dd.MM.yyyy"), "F",
            "Daresbury, Cheshire", "GB", "Follow me", ORGANISATION1);

        LOG.info("Database table 'PERSON' populated with sample data");
      }
      // tag::service[]
    }
  }
  // end::service[]

  private void createPersonEntry(UUID personUuid, String firstName, String lastName, String pictureUrl, Date dob, String gender, String city, String country, String position, UUID organizationUuid) {
    SQL.insert(SQLs.PERSON_INSERT_SAMPLE_DATA,
        new NVPair("personUuid", personUuid.toString()),
        new NVPair("firstName", firstName),
        new NVPair("lastName", lastName),
        new NVPair("pictureUrl", pictureUrl),
        new NVPair("dob", dob),
        new NVPair("gender", gender),
        new NVPair("city", city),
        new NVPair("country", country),
        new NVPair("position", position),
        new NVPair("organizationUuid", organizationUuid.toString()));
  }

}
