package org.eclipse.scout.docs.snippets.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scout.docs.snippets.ILanguageLookupService;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;

/**
 * <h3>{@link LanguageLookupService}</h3>
 */
public class LanguageLookupService extends AbstractLookupService<String> implements ILanguageLookupService {

  @Override
  public List<? extends ILookupRow<String>> getDataByKey(ILookupCall<String> call) {
    return null;
  }

  @Override
  public List<? extends ILookupRow<String>> getDataByText(ILookupCall<String> call) {
    return null;
  }

  @SuppressWarnings("unused")
  //tag::getDataByAll[]
  @Override
  public List<? extends ILookupRow<String>> getDataByAll(ILookupCall<String> call) {
    LanguageLookupCall c = (LanguageLookupCall) call;
    Date validityFrom = c.getValidityFrom();
    Date validityTo = c.getValidityTo();

    List<? extends ILookupRow<String>> result = new ArrayList<ILookupRow<String>>();
    //compute result: corresponding lookup rows (depending on validityFrom and validityTo).
    return result;
  }
  //end::getDataByAll[]

  @Override
  public List<? extends ILookupRow<String>> getDataByRec(ILookupCall<String> call) {
    return null;
  }
}
