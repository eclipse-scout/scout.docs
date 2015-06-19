/**
 *
 */
package org.eclipsescout.contacts.server.services.lookup;

import org.eclipse.scout.rt.server.services.lookup.AbstractSqlLookupService;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipsescout.contacts.shared.services.lookup.IContactLookupService;

/**
 * @author mzi
 */
public class ContactLookupService extends AbstractSqlLookupService<String>implements IContactLookupService {

  @Override
  protected String getConfiguredSqlSelect() {
    return TEXTS.get("SqlContactLookup");
  }
}
