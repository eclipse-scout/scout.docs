/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
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
      + "         'Eclipse Foundation', "
      + "         'Brussels', "
      + "         'BE', "
      + "         'https://www.eclipse.org/org/foundation/', "
      + "         'https://wiki.eclipse.org/images/a/ab/Eclipse_foundation_logo.png')";

  String ORGANIZATION_VALUES_02 = ""
      + "VALUES  ('org02', "
      + "         'BSI Business Systems Integration AG', "
      + "         'Baden', "
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
      + "          'Kenneth', "
      + "          'Hartley', "
      + "          'avatars/Kenneth.jpg', "
      + "          '06.03.1977', "
      + "          'M', "
      + "          '2203 Edington Drive', "
      + "          'Smyrna', "
      + "          'US', "
      + "          'Webmaster', "
      + "          'org01')";

  String PERSON_VALUES_02 = ""
      + "VALUES   ('prs02', "
      + "          'Thea', "
      + "          'Ommundsen', "
      + "          'avatars/Thea.jpg', "
      + "          '17.12.1999', "
      + "          'F', "
      + "          'Nermarka 246', "
      + "          'Trondheim', "
      + "          'NO', "
      + "          'Airport terminal controller', "
      + "          'org01')";

  String PERSON_VALUES_03 = ""
      + "VALUES   ('prs03', "
      + "          'Julek', "
      + "          'Ostrowski', "
      + "          'avatars/Julek.jpg', "
      + "          '05.11.1954', "
      + "          'M', "
      + "          'ul. Kiepury Jana 56', "
      + "          'Kielce', "
      + "          'PL', "
      + "          'Food processing occupation', "
      + "          'org02')";

  String PERSON_VALUES_04 = ""
      + "VALUES   ('prs04', "
      + "          'Joséphine', "
      + "          'Doiron', "
      + "          'avatars/Josephine.jpg', "
      + "          '18.08.1990', "
      + "          'F', "
      + "          '80, Rue Hubert de Lisle', "
      + "          'Lion', "
      + "          'FR', "
      + "          'Optometrist', "
      + "          'org02')";

  String PERSON_VALUES_05 = ""
      + "VALUES   ('prs05', "
      + "          'Bagi', "
      + "          'Gergely', "
      + "          'avatars/Bagi.jpg', "
      + "          '21.03.1972', "
      + "          'M', "
      + "          'Tas vezér u. 5.', "
      + "          'Kistormás', "
      + "          'HU', "
      + "          'Law librarian', "
      + "          'org02')";

  String PERSON_VALUES_06 = ""
      + "VALUES   ('prs06', "
      + "          'Federica', "
      + "          'Serrato', "
      + "          'avatars/Federica.jpg', "
      + "          '17.12.1982', "
      + "          'F', "
      + "          'Boriñaur enparantza, 9', "
      + "          'Robregordo', "
      + "          'ES', "
      + "          'Customer services manager', "
      + "          'org02')";

  String PERSON_VALUES_07 = ""
      + "VALUES   ('prs07', "
      + "          'Basimah', "
      + "          'Rahal', "
      + "          'avatars/Basimah.jpg', "
      + "          '07.05.1969', "
      + "          'F', "
      + "          '120 Avenue Aghlabité', "
      + "          'Cide el Khrachfa', "
      + "          'TN', "
      + "          'Personnel officer', "
      + "          'org02')";

  String PERSON_VALUES_08 = ""
      + "VALUES   ('prs08', "
      + "          'Bethany', "
      + "          'Pasco', "
      + "          'avatars/Bethany.jpg', "
      + "          '08.10.1991', "
      + "          'F', "
      + "          '2 Albert Street', "
      + "          'Innisplain ', "
      + "          'AU', "
      + "          'Benefits administrator', "
      + "          'org02')";

  String PERSON_VALUES_09 = ""
      + "VALUES   ('prs09', "
      + "          'Livia', "
      + "          'Barros', "
      + "          'avatars/Livia.jpg', "
      + "          '25.12.1978', "
      + "          'F', "
      + "          'Rua dos Cinamomos, 356', "
      + "          'Caxias do Sul', "
      + "          'BR', "
      + "          'General trial court judge', "
      + "          'org02')";

  String PERSON_VALUES_10 = ""
      + "VALUES   ('prs10', "
      + "          'Mulan', "
      + "          'Chien', "
      + "          'avatars/Mulan.jpg', "
      + "          '15.04.1987', "
      + "          'F', "
      + "          'Lungolago 11', "
      + "          'Tartegnin', "
      + "          'CH', "
      + "          'Promoter', "
      + "          'org02')";

  String PERSON_VALUES_11 = ""
      + "VALUES   ('prs11', "
      + "          'Gustav', "
      + "          'Østergaard', "
      + "          'avatars/Gustav.jpg', "
      + "          '05.02.1961', "
      + "          'M', "
      + "          'Stenløsegyden 9', "
      + "          'København', "
      + "          'DK', "
      + "          'Training consultant', "
      + "          'org02')";

  String PERSON_VALUES_12 = ""
      + "VALUES   ('prs12', "
      + "          'Malik', "
      + "          'Lundblad', "
      + "          'avatars/Malik.jpg', "
      + "          '05.12.1965', "
      + "          'M', "
      + "          'Ringvej 235', "
      + "          'Qaqortoq', "
      + "          'GL', "
      + "          'Executive administrator', "
      + "          'org02')";

  String PERSON_VALUES_13 = ""
      + "VALUES   ('prs13', "
      + "          'Gandolfo', "
      + "          'Cattaneo', "
      + "          'avatars/Gandolfo.jpg', "
      + "          '03.12.1976', "
      + "          'M', "
      + "          'Via delle Mura Gianicolensi, 45', "
      + "          'Ripabottoni Stazione', "
      + "          'IT', "
      + "          'Linguistic anthropologist', "
      + "          'org02')";

  String PERSON_VALUES_14 = ""
      + "VALUES   ('prs14', "
      + "          'Maurizia', "
      + "          'Piccio', "
      + "          'avatars/Maurizia.jpg', "
      + "          '24.07.1971', "
      + "          'F', "
      + "          'Via Campi Flegrei, 106', "
      + "          'Villanova Del Battista', "
      + "          'IT', "
      + "          'Home health aide', "
      + "          'org02')";

  String PERSON_VALUES_15 = ""
      + "VALUES   ('prs15', "
      + "          'Hashim', "
      + "          'van Wel', "
      + "          'avatars/Hashim.jpg', "
      + "          '03.09.1969', "
      + "          'M', "
      + "          'Rosier Faassenstraat 131', "
      + "          'Rotterdam', "
      + "          'NL', "
      + "          'Parking enforcement worker', "
      + "          'org02')";

  String PERSON_VALUES_16 = ""
      + "VALUES   ('prs16', "
      + "          'Atifa', "
      + "          'Sulejmanoski', "
      + "          'avatars/Atifa.jpg', "
      + "          '18.01.1993', "
      + "          'F', "
      + "          'Tavcarjeva 5', "
      + "          'Škocjan', "
      + "          'SL', "
      + "          'Group exercise instructor', "
      + "          'org02')";

  String PERSON_VALUES_17 = ""
      + "VALUES   ('prs17', "
      + "          'Verónica', "
      + "          'Ulibarri', "
      + "          'avatars/Veronica.jpg', "
      + "          '25.12.2002', "
      + "          'F', "
      + "          'Avda. Los llanos, 58', "
      + "          'Villalba de Rioja', "
      + "          'ES', "
      + "          'Traffic clerk', "
      + "          'org02')";

  String PERSON_VALUES_18 = ""
      + "VALUES   ('prs18', "
      + "          'Jiřina', "
      + "          'Třetinová', "
      + "          'avatars/Jirina.jpg', "
      + "          '15.09.1988', "
      + "          'F', "
      + "          'Nerudova 1158', "
      + "          'Ledenice', "
      + "          'CZ', "
      + "          'Clock repairer', "
      + "          'org02')";

  String PERSON_VALUES_19 = ""
      + "VALUES   ('prs19', "
      + "          'Ghassan', "
      + "          'Halabi', "
      + "          'avatars/Ghassan.jpg', "
      + "          '15.05.1974', "
      + "          'M', "
      + "          '1 Rue Tahar Khmiri', "
      + "          'Baajla', "
      + "          'TN', "
      + "          'Geotechnical engineer', "
      + "          'org02')";

  String PERSON_VALUES_20 = ""
      + "VALUES   ('prs20', "
      + "          'Gemma', "
      + "          'Faucher', "
      + "          'avatars/Gemma.jpg', "
      + "          '03.06.1979', "
      + "          'F', "
      + "          '2215 Wolmarans St', "
      + "          'Witbank', "
      + "          'ZA', "
      + "          'Personal appearance worker', "
      + "          'org02')";

  String PERSON_DROP_TABLE = "DROP TABLE PERSON";
  String ORGANIZATION_DROP_TABLE = "DROP TABLE ORGANIZATION";

  // tag::organizationListing[]
  // tag::createDB[]
}
// end::createDB[]
// end::organizationListing[]
