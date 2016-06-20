package org.eclipse.scout.docs.snippets.lookup;

import java.util.List;

import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.platform.util.date.DateUtility;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;

/**
 * <h3>{@link LookupExample}</h3>
 */
public class LookupExample {

  static class P_Field extends AbstractSmartField<String> {

    //tag::SmartFieldLookup[]
    @Override
    protected void execPrepareLookup(ILookupCall<String> call) {
      LanguageLookupCall c = (LanguageLookupCall) call;
      c.setValidityFrom(DateUtility.parse("2012-02-26", "yyyy-mm-dd"));
      c.setValidityTo(DateUtility.parse("2013-02-27", "yyyy-mm-dd"));
    }
    //end::SmartFieldLookup[]

    //tag::SmartFieldLookupCall[]
    @Override
    protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
      return LanguageLookupCall.class;
    }
    //end::SmartFieldLookupCall[]
  }

  @SuppressWarnings("unused")
  void executeLookupCall() {
    String thisKey = "foo";

    //tag::executeLookupCall[]
    //Execute the LookupCall (using DataByKey)
    LookupCall<String> call = new LanguageLookupCall();
    call.setKey(thisKey);
    List<? extends ILookupRow<String>> rows = call.getDataByKey();

    //Get the text (with a null check)
    String text = null;
    if (rows != null && !rows.isEmpty()) {
      text = rows.get(0).getText();
    }
    //end::executeLookupCall[]
  }
}
