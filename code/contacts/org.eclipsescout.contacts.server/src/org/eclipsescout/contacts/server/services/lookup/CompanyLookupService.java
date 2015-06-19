/**
 *
 */
package org.eclipsescout.contacts.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.shared.services.lookup.ICompanyLookupService;

/**
 * @author mzi
 */
public class CompanyLookupService extends AbstractSqlLookupService<String>implements ICompanyLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return TEXTS.get("SqlCompanyLookup");
  }
}
