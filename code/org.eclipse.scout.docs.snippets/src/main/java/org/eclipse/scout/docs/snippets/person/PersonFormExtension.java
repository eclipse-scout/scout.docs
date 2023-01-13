/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets.person;

import org.eclipse.scout.docs.snippets.person.PersonForm.NewHandler;
import org.eclipse.scout.rt.client.extension.ui.form.AbstractFormExtension;
import org.eclipse.scout.rt.client.extension.ui.form.AbstractFormHandlerExtension;
import org.eclipse.scout.rt.client.extension.ui.form.FormChains.FormInitFormChain;
import org.eclipse.scout.rt.client.extension.ui.form.FormHandlerChains.FormHandlerPostLoadChain;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBoxes;

//tag::PersonFormExtension[]
public class PersonFormExtension extends AbstractFormExtension<PersonForm> {

  public PersonFormExtension(PersonForm ownerForm) {
    super(ownerForm);
  }

  @Override
  public void execInitForm(FormInitFormChain chain) {
    chain.execInitForm();
    // Example logic: Access the form, disable field
    getOwner().getNameField().setEnabled(false, true, true);
  }

  public void testMethod() {
    MessageBoxes.create().withHeader("Extension method test").withBody("A method from the form extension was called").show();
  }

  public static class NewFormHandlerExtension extends AbstractFormHandlerExtension<NewHandler> {

    public NewFormHandlerExtension(NewHandler owner) {
      super(owner);
    }

    @Override
    public void execPostLoad(FormHandlerPostLoadChain chain) {
      chain.execPostLoad();
      // Example logic: Show a message box after load
      MessageBoxes.create().withHeader("Extension test").withBody("If you can read this, the extension works correctly").show();

      // Access element from the outer extension.
      PersonFormExtension extension = ((AbstractForm) getOwner().getForm()).getExtension(PersonFormExtension.class);
      extension.testMethod();
    }
  }
}
//end::PersonFormExtension[]
