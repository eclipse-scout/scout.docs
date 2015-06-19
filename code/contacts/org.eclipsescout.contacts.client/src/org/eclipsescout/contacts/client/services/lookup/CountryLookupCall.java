/**
 *
 */
package org.eclipsescout.contacts.client.services.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;

/**
 * @author mzi
 */
public class CountryLookupCall extends LocalLookupCall<String> {

  private static final long serialVersionUID = 1L;

  @Override
  protected List<LookupRow<String>> execCreateLookupRows() throws ProcessingException {
    List<LookupRow<String>> rows = new ArrayList<LookupRow<String>>();

    for (String country : Locale.getISOCountries()) {
      rows.add(new LookupRow<String>(country, country));
    }

    return rows;
  }
}
