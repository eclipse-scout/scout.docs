/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.server.sql;

//tag::createDB[]
//tag::organizationListing[]
public interface SQLs {
  //end::organizationListing[]

  String SELECT_TABLE_NAMES = ""
      + "SELECT   UPPER(tablename) "
      + "FROM     sys.systables "
      + "INTO     :result"; // <1>

  String ORGANIZATION_CREATE_TABLE = ""
      + "CREATE   TABLE ORGANIZATION "
      + "         (organization_id VARCHAR(64) NOT NULL CONSTRAINT ORGANIZATION_PK PRIMARY KEY,"
      + "          name VARCHAR(64), "
      + "          logo_url VARCHAR(512), "
      + "          url VARCHAR(64), "
      + "          street VARCHAR(64), "
      + "          city VARCHAR(64), "
      + "          country VARCHAR(2), "
      + "          phone VARCHAR(20), "
      + "          email VARCHAR(64), "
      + "          notes VARCHAR(1024)"
      + "         )";

  String PERSON_CREATE_TABLE = ""
      + "CREATE   TABLE PERSON "
      + "         (person_id VARCHAR(64) NOT NULL CONSTRAINT PERSON_PK PRIMARY KEY, "
      + "          first_name VARCHAR(64), "
      + "          last_name VARCHAR(64), "
      + "          picture_url VARCHAR(512), "
      + "          date_of_birth DATE, "
      + "          gender VARCHAR(1), "
      + "          street VARCHAR(64), "
      + "          city VARCHAR(64), "
      + "          country VARCHAR(2), "
      + "          phone VARCHAR(20), "
      + "          mobile VARCHAR(20), "
      + "          email VARCHAR(64), "
      + "          organization_id VARCHAR(64), "
      + "          position VARCHAR(512), "
      + "          phone_work VARCHAR(20), "
      + "          email_work VARCHAR(64), "
      + "          notes VARCHAR(1024), "
      + "          CONSTRAINT ORGANIZATION_FK FOREIGN KEY (organization_id) REFERENCES ORGANIZATION (organization_id)"
      + "         )";
// end::createDB[]

  String PERSON_LOOKUP = ""
      + "SELECT   person_id, "
      + "         CASE "
      + "           WHEN first_name IS null "
      + "            THEN last_name "
      + "           WHEN last_name IS null "
      + "            THEN first_name "
      + "           ELSE "
      + "            first_name || ' ' || last_name "
      + "         END "
      + "FROM     PERSON "
      + "WHERE    1 = 1 "
      + "<key>    AND person_id = :key</key> "
      + "<text>   AND (UPPER(first_name) LIKE UPPER('%'||:text||'%') "
      + "         OR UPPER(last_name) LIKE UPPER('%'||:text||'%')) "
      + "</text>"
      + "<all> </all>";

  //tag::lookupService[]
  String ORGANIZATION_LOOKUP = ""
      + "SELECT   organization_id, "
      + "         name "
      + "FROM     ORGANIZATION "
      + "WHERE    1 = 1 "
      + "<key>    AND organization_id = :key</key> " // <1>
      + "<text>   AND UPPER(name) LIKE UPPER(:text||'%') </text> " // <2>
      + "<all></all>"; // <3>
  //end::lookupService[]

  String AND_LIKE_CAUSE = "AND LOWER(%s) LIKE LOWER(:%s || '%%') ";

  //tag::organizationListing[]
  String ORGANIZATION_PAGE_SELECT = ""
      + "SELECT   organization_id, "
      + "         name, "
      + "         city, "
      + "         country, "
      + "         url "
      + "FROM     ORGANIZATION ";

  String ORGANIZATION_PAGE_DATA_SELECT_INTO = ""
      + "INTO     :{page.organizationId}, " // <1>
      + "         :{page.name}, "
      + "         :{page.city}, "
      + "         :{page.country}, "
      + "         :{page.homepage}";
  //end::organizationListing[]

  String ORGANIZATION_INSERT = ""
      + "INSERT   INTO "
      + "ORGANIZATION  (organization_id) "
      + "VALUES   (:organizationId)";

  String ORGANIZATION_SELECT = ""
      + "SELECT   name, "
      + "         logo_url, "
      + "         url, "
      + "         phone, "
      + "         email, "
      + "         street, "
      + "         city, "
      + "         country, "
      + "         notes "
      + "FROM     ORGANIZATION "
      + "WHERE    organization_id = :organizationId "
      + "INTO     :name, "
      + "         :picture.url, "
      + "         :homepage, "
      + "         :phone, "
      + "         :email, "
      + "         :addressBox.street, "
      + "         :addressBox.city, "
      + "         :addressBox.country, "
      + "         :notesBox.notes";

  String ORGANIZATION_UPDATE = ""
      + "UPDATE   ORGANIZATION "
      + "SET      name = :name, "
      + "         logo_url = :picture.url, "
      + "         url = :homepage, "
      + "         phone = :phone, "
      + "         email = :email, "
      + "         street = :addressBox.street, "
      + "         city = :addressBox.city, "
      + "         country = :addressBox.country, "
      + "         notes = :notesBox.notes "
      + "WHERE    organization_id = :organizationId";

  String PERSON_PAGE_SELECT = ""
      + "SELECT   person_id, "
      + "         first_name, "
      + "         last_name, "
      + "         city, "
      + "         country, "
      + "         phone, "
      + "         mobile, "
      + "         email, "
      + "         organization_id "
      + "FROM     PERSON ";

  String PERSON_PAGE_DATA_SELECT_INTO = ""
      + "INTO     :{page.personId}, "
      + "         :{page.firstName}, "
      + "         :{page.lastName}, "
      + "         :{page.city}, "
      + "         :{page.country}, "
      + "         :{page.phone}, "
      + "         :{page.mobile}, "
      + "         :{page.email}, "
      + "         :{page.organization}";

  String PERSON_INSERT = ""
      + "INSERT   INTO "
      + "PERSON  (person_id) "
      + "VALUES   (:personId)";

  String PERSON_SELECT = ""
      + "SELECT   first_name, "
      + "         last_name, "
      + "         picture_url, "
      + "         date_of_birth, "
      + "         gender, "
      + "         phone, "
      + "         mobile, "
      + "         email, "
      + "         street, "
      + "         city, "
      + "         country, "
      + "         position, "
      + "         organization_id, "
      + "         phone_work, "
      + "         email_work, "
      + "         notes "
      + "FROM     PERSON "
      + "WHERE    person_id = :personId "
      + "INTO     :firstName, "
      + "         :lastName, "
      + "         :pictureUrl, "
      + "         :dateOfBirth, "
      + "         :genderGroup, "
      + "         :phone, "
      + "         :mobile, "
      + "         :email, "
      + "         :street, "
      + "         :city, "
      + "         :country, "
      + "         :position, "
      + "         :organization, "
      + "         :phoneWork, "
      + "         :emailWork, "
      + "         :notes";

  String PERSON_UPDATE = ""
      + "UPDATE   PERSON "
      + "SET      first_name = :firstName, "
      + "         last_name = :lastName, "
      + "         picture_url = :pictureUrl, "
      + "         date_of_birth = :dateOfBirth, "
      + "         gender = :genderGroup, "
      + "         phone  = :phone, "
      + "         mobile = :mobile, "
      + "         email = :email, "
      + "         street = :street, "
      + "         city = :city, "
      + "         country = :country, "
      + "         position = :position, "
      + "         organization_id = :organization, "
      + "         phone_work = :phoneWork, "
      + "         email_work = :emailWork, "
      + "         notes = :notes "
      + "WHERE    person_id = :personId";

  String ORGANIZATION_INSERT_SAMPLE = ""
      + "INSERT   INTO ORGANIZATION "
      + "        (organization_id, "
      + "         name, "
      + "         city, "
      + "         country, "
      + "         url, "
      + "         logo_url) ";

  String ORGANIZATION_VALUES_01 = ""
      + "VALUES  ('org01', "
      + "         'Alice''s Adventures in Wonderland', "
      + "         'London', "
      + "         'GB', "
      + "         'http://en.wikipedia.org/wiki/Alice%27s_Adventures_in_Wonderland', "
      + "         'https://upload.wikimedia.org/wikipedia/en/3/3f/Alice_in_Wonderland%2C_cover_1865.jpg')";

  String ORGANIZATION_VALUES_02 = ""
      + "VALUES  ('org02', "
      + "         'BSI Business Systems Integration AG', "
      + "         'Daettwil, Baden', "
      + "         'CH', "
      + "         'https://www.bsi-software.com', "
      + "         'https://wiki.eclipse.org/images/4/4f/Bsiag.png')";

  String PERSON_INSERT_SAMPLE = ""
      + "INSERT   INTO PERSON "
      + "         (person_id, "
      + "          first_name, "
      + "          last_name, "
      + "          picture_url, "
      + "          date_of_birth, "
      + "          gender, "
      + "          street, "
      + "          city, "
      + "          country, "
      + "          position, "
      + "          organization_id) ";

  String PERSON_VALUES_01 = ""
      + "VALUES   ('prs01', "
      + "          'Alice', "
      + "          null, "
      + "          'http://www.uergsel.de/uploads/Alice.png', "
      + "          '26.11.1865', "
      + "          'F', "
      + "          null, "
      + "          'Daresbury, Cheshire', "
      + "          'GB', "
      + "          'The curious girl', "
      + "          'org01')";

  String PERSON_VALUES_02 = ""
      + "VALUES   ('prs02', "
      + "          'Rabbit', "
      + "          'White', "
      + "          'https://upload.wikimedia.org/wikipedia/commons/4/42/The_White_Rabbit_%28Tenniel%29_-_The_Nursery_Alice_%281890%29_-_BL.jpg', "
      + "          '26.11.1865', "
      + "          'M', "
      + "          null, "
      + "          'Daresbury, Cheshire', "
      + "          'GB', "
      + "          null, "
      + "          'org01')";

  String PERSON_VALUES_03 = ""
      + "VALUES   ('prs03', "
      + "          'Gegor', "
      + "          'Bauer', "
      + "          'https://wiki.eclipse.org/images/5/54/Scout_contacts_112.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Aarau', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_04 = ""
      + "VALUES   ('prs04', "
      + "          'Alexandre', "
      + "          'Schroder', "
      + "          'https://wiki.eclipse.org/images/5/54/Scout_contacts_105.png', "
      + "          '30.05.1976', "
      + "          'M', "
      + "          'Zypressenstrasse 60', "
      + "          'Zürich', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_05 = ""
      + "VALUES   ('prs05', "
      + "          'André', "
      + "          'Wegmüller', "
      + "          'https://wiki.eclipse.org/images/f/ff/Scout_contacts_103.png', "
      + "          '04.11.1975', "
      + "          'M', "
      + "          'Rüttihubel 29', "
      + "          'Walkringen', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_06 = ""
      + "VALUES   ('prs06', "
      + "          'Catherine', "
      + "          'Crowden', "
      + "          'https://wiki.eclipse.org/images/9/96/Scout_contacts_111.png', "
      + "          '01.01.2000', "
      + "          'F', "
      + "          'Poststrasse 7', "
      + "          'Bümpliz', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_07 = ""
      + "VALUES   ('prs07', "
      + "          'Cédric', "
      + "          'Amstalden', "
      + "          'https://wiki.eclipse.org/images/f/ff/Scout_contacts_118.png', "
      + "          null, "
      + "          'M', "
      + "          'Brünigstrasse 5', "
      + "          'Alpnach Dorf', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_08 = ""
      + "VALUES   ('prs08', "
      + "          'Christian', "
      + "          'Braun', "
      + "          'https://wiki.eclipse.org/images/a/ab/Scout_contacts_108.png', "
      + "          '04.11.1975', "
      + "          'M', "
      + "          'Huttenstrasse 22', "
      + "          'Zürich', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_09 = ""
      + "VALUES   ('prs09', "
      + "          'Christoph', "
      + "          'Bräunlich', "
      + "          'https://wiki.eclipse.org/images/0/0e/Scout_contacts_122.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Baar', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_10 = ""
      + "VALUES   ('prs10', "
      + "          'Fabian', "
      + "          'Laubacher', "
      + "          'https://wiki.eclipse.org/images/1/16/Scout_contacts_115.png', "
      + "          '23.07.1977', "
      + "          'M', "
      + "          'Steiggistrasse 6b', "
      + "          'Auw', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_11 = ""
      + "VALUES   ('prs11', "
      + "          'Glen', "
      + "          'Reif', "
      + "          'https://wiki.eclipse.org/images/0/00/Scout_contacts_101.png', "
      + "          '04.11.1975', "
      + "          'M', "
      + "          'Marienplatz 1', "
      + "          'München', "
      + "          'DE', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_12 = ""
      + "VALUES   ('prs12', "
      + "          'Ivan', "
      + "          'Motsch', "
      + "          'https://wiki.eclipse.org/images/a/ab/Scout_contacts_124.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Luzern', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_13 = ""
      + "VALUES   ('prs13', "
      + "          'Christian', "
      + "          'Frey', "
      + "          'https://wiki.eclipse.org/images/0/0f/Scout_contacts_126.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Thun', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_14 = ""
      + "VALUES   ('prs14', "
      + "          'Jens', "
      + "          'Thuesen', "
      + "          'https://wiki.eclipse.org/images/0/02/Scout_contacts_121.png', "
      + "          '14.01.1964', "
      + "          'M', "
      + "          'Weidstrasse 9', "
      + "          'Zufikon', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_15 = ""
      + "VALUES   ('prs15', "
      + "          'Patrick', "
      + "          'Gerber', "
      + "          'https://wiki.eclipse.org/images/c/c1/Scout_contacts_117.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Freiburg', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_16 = ""
      + "VALUES   ('prs16', "
      + "          'Jürg', "
      + "          'Perner', "
      + "          'https://wiki.eclipse.org/images/f/f8/Scout_contacts_107.png', "
      + "          '27.05.1966', "
      + "          'M', "
      + "          'Birkenweg 6', "
      + "          'Triengen', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_17 = ""
      + "VALUES   ('prs17', "
      + "          'Luc', "
      + "          'Hansen', "
      + "          'https://wiki.eclipse.org/images/8/89/Scout_contacts_119.png', "
      + "          null, "
      + "          'M', "
      + "          'Via Engiadina', "
      + "          'Sils', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_18 = ""
      + "VALUES   ('prs18', "
      + "          'Markus', "
      + "          'Brunold', "
      + "          'https://wiki.eclipse.org/images/f/f1/Scout_contacts_113.png', "
      + "          '12.06.1976', "
      + "          'M', "
      + "          'Grundstrasse 1', "
      + "          'Baden', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_19 = ""
      + "VALUES   ('prs19', "
      + "          'Martin', "
      + "          'Grunder', "
      + "          'https://wiki.eclipse.org/images/3/33/Scout_contacts_110.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'St. Gallen', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_20 = ""
      + "VALUES   ('prs20', "
      + "          'Matthias', "
      + "          'Zimmermann', "
      + "          'https://wiki.eclipse.org/images/2/28/Scout_contacts_125.png', "
      + "          null, "
      + "          'M', "
      + "          'Schänzlistrasse', "
      + "          'Bern', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_21 = ""
      + "VALUES   ('prs21', "
      + "          'Nicolas', "
      + "          'Born', "
      + "          'https://wiki.eclipse.org/images/f/f6/Scout_contacts_102.png', "
      + "          '25.05.1986', "
      + "          'M', "
      + "          'Bremgartenstrasse 117', "
      + "          'Bern', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_22 = ""
      + "VALUES   ('prs22', "
      + "          'Oliver', "
      + "          'Schmid', "
      + "          'https://wiki.eclipse.org/images/1/1d/Scout_contacts_116.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Winterthur', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_23 = ""
      + "VALUES   ('prs23', "
      + "          'Adrian', "
      + "          'Meier', "
      + "          'https://wiki.eclipse.org/images/1/13/Scout_contacts_123.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Basel', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_24 = ""
      + "VALUES   ('prs24', "
      + "          'Peter', "
      + "          'Seitel', "
      + "          'https://wiki.eclipse.org/images/f/f1/Scout_contacts_127.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'München', "
      + "          'DE', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_25 = ""
      + "VALUES   ('prs25', "
      + "          'Robert', "
      + "          'Echelmeyer', "
      + "          'https://wiki.eclipse.org/images/b/b6/Scout_contacts_120.png', "
      + "          '27.07.1989', "
      + "          'M', "
      + "          'Gladbacher Str. 116', "
      + "          'Düsseldorf', "
      + "          'DE', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_26 = ""
      + "VALUES   ('prs26', "
      + "          'Michael', "
      + "          'Richter', "
      + "          'https://wiki.eclipse.org/images/e/ea/Scout_contacts_104.png', "
      + "          null, "
      + "          'M', "
      + "          null, "
      + "          'Frankfurt', "
      + "          'DE', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_27 = ""
      + "VALUES   ('prs27', "
      + "          'Sion', "
      + "          'Huws', "
      + "          'https://wiki.eclipse.org/images/0/03/Scout_contacts_114.png', "
      + "          '11.05.1978', "
      + "          'M', "
      + "          'Zürichstr. 133', "
      + "          'Affoltern a. A.', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_28 = ""
      + "VALUES   ('prs28', "
      + "          'Stefan', "
      + "          'Leicht Vogt', "
      + "          'https://wiki.eclipse.org/images/0/05/Scout_contacts_106.png', "
      + "          '19.04.1987', "
      + "          'M', "
      + "          'Matschweg 3', "
      + "          'Gebenstorf', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_VALUES_29 = ""
      + "VALUES   ('prs29', "
      + "          'Zeno', "
      + "          'Hug', "
      + "          'https://wiki.eclipse.org/images/c/c6/Scout_contacts_109.png', "
      + "          '14.06.1972', "
      + "          'M', "
      + "          'Dorfstrasse 1', "
      + "          'Baden', "
      + "          'CH', "
      + "          null, "
      + "          'org02')";

  String PERSON_DROP_TABLE = "DROP TABLE PERSON";
  String ORGANIZATION_DROP_TABLE = "DROP TABLE ORGANIZATION";

  // tag::organizationListing[]
  // tag::createDB[]
}
// end::createDB[]
// end::organizationListing[]
