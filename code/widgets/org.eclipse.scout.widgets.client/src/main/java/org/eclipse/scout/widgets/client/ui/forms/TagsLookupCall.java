package org.eclipse.scout.widgets.client.ui.forms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

public class TagsLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;

  private List<String> m_tags;

  private static final String TAGS = "bsi crm,bsi crm 15,bsicrm,bsi crm 14,bsi crm 14.2,entwicklung,scout,fachliches,cloud,datenbank,eclipse,teammeeting,changelog,best,practice," +
      "jenkins,kurs,django,git,bap,indigo,technische,oracle,bsi crm 12.8,dokumentation,linux,maven,concorde_betrieb,d-min,deployment,html,ui,hamo,support,techsupport,datenschutz," +
      "java,performance,postgres,docker,release,test,cti,migration,sql,event,fsr3,insurance,five,todo,lieferung,herbstmesse,konfiguration,testing,dsc,infrastruktur,open source,outlook," +
      "qualitätssicherung,update,bsicrm 15.2,db,baden,bsi entwicklung,entwicklungsumgebung,lync,bsi crm 12.9,bsi crm 15.2,build,crm,cpan,firewall,d-min8.4,xdb,xdc,zahlungsverkehr,citrus," +
      "seedperfect - betriebshandbuch,nogias,projekt,protokoll,security,skype for business,sonarqube,contact center,gitlab,javascript,svn,systeme,telefon,tutorial,bsi crm word,artifactory," +
      "asciidoctor,eclipse,scout,etl,generierung,junit,mariadb,release engineering,skype,tickets,berechtigungen,bsicrm 15.3,loop,münchen,postgresql,releasenotes,technische dokumentation bsi crm," +
      "d-min,9.1,bsi,bsi crm 15.3,bsi,portal,ci,kurse,lotus,notes,releng,scout,js,bsi,events,cluster,cyberzonk,hossa,fsr 3,goforit,groupware,guidelines,java8,marketing,migapp,ssh,telefonanlage," +
      "ticket,unit,tests,d-min8.7,zertifikat,administration,aktenkoffer,css,delivery,exchange,klonbus,hardware,kurskatalog,labdays,ldap,projekt,setup,scoutdoc,server,spezifikation,system," +
      "weihnachten,windows,3oder3,android,bsi crm 15.4,bsi crm projekt,bsi search,e-mail,erweiterbarkeit,high availability,html,jump,host,lizenz,log,login portal,regelwerk,reverse proxy,veritas," +
      "schnittstellen,serialdatamigration,upgrade,d-min,8.9,d-min ide,d-min9.0,weiterbildung,backup,crm 15,debugging,email,glossar,mobile,prozess,scout 6,seppmail,setup,spesen,tech forum,hiscore," +
      "vorlagen,vpn,webservice,websphere,wls,15.2,ios,jira,jumpserver,rest,tomcat,bisono,wiki";

  public TagsLookupCall() {
    List<String> tags = Arrays.asList(TAGS.split(","));
    Collections.sort(tags);
    m_tags = tags;
  }

  @Override
  protected List<? extends ILookupRow<String>> execCreateLookupRows() {
    return m_tags.stream()
        .map(tag -> {
          return new LookupRow<>(tag, tag);
        }).collect(Collectors.toList());
  }

}
