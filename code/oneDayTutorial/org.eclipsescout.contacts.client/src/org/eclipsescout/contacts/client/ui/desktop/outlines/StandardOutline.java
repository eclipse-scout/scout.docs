/**
 *
 */
package org.eclipsescout.contacts.client.ui.desktop.outlines;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.client.ui.desktop.outline.pages.CompanyTablePage;
import org.eclipsescout.contacts.client.ui.desktop.outline.pages.PersonTablePage;

/**
 * @author mzi
 */
public class StandardOutline extends AbstractExtensibleOutline {

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("StandardOutline");
  }

  @Override
  protected void execCreateChildPages(List<IPage> pageList) throws ProcessingException {
    PersonTablePage personTablePage = new PersonTablePage();
    pageList.add(personTablePage);
    CompanyTablePage companyTablePage = new CompanyTablePage();
    pageList.add(companyTablePage);
  }
}
