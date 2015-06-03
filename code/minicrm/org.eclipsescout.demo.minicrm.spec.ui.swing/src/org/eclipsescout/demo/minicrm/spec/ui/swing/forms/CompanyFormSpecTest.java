package org.eclipsescout.demo.minicrm.spec.ui.swing.forms;

import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.spec.client.AbstractFormSpecTest;
import org.eclipsescout.demo.minicrm.client.ui.desktop.form.CompanyForm;

public class CompanyFormSpecTest extends AbstractFormSpecTest {

  @Override
  protected IForm createAndStartForm(List<FormListener> formListeners) throws ProcessingException {
    CompanyForm form = new CompanyForm();
    for (FormListener listener : formListeners) {
      form.addFormListener(listener);
    }
    form.startNew();
    return form;
  }

}
