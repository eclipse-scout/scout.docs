/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.docs.snippets;

import java.math.BigDecimal;

import org.eclipse.scout.docs.snippets.person.PersonForm.MainBox.LastBox;
import org.eclipse.scout.docs.snippets.person.PersonFormData;
import org.eclipse.scout.docs.snippets.person.PersonFormMainBoxExtension;
import org.eclipse.scout.docs.snippets.person.PersonFormMainBoxExtensionData;
import org.eclipse.scout.rt.client.context.ClientRunContexts;
import org.eclipse.scout.rt.client.extension.ui.form.fields.FormFieldChains.FormFieldInitFieldChain;
import org.eclipse.scout.rt.client.extension.ui.form.fields.stringfield.AbstractStringFieldExtension;
import org.eclipse.scout.rt.client.ui.desktop.bookmark.BookmarkFolderForm.MainBox.GroupBox.NameField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.job.Jobs;
import org.eclipse.scout.rt.shared.extension.IExtensionRegistry;

public class ExtensibilitySnippets {

  /**
   * Extension to intercept e.g. execInitField()
   */
  //tag::NameFieldExtension[]
  public class NameFieldExtension extends AbstractStringFieldExtension<NameField> {

    public NameFieldExtension(NameField owner) {
      super(owner);
    }

    @Override
    public void execInitField(FormFieldInitFieldChain chain) {
      chain.execInitField(); // call the original exec init. whatever it may do.
      getOwner().setValue("FirstName LastName"); // overwrite the initial value of the name field
    }
  }
  //end::NameFieldExtension[]

  public void registerExtension() {
    //tag::RegisterNameFieldExtension[]
    Jobs.schedule(() -> BEANS.get(IExtensionRegistry.class).register(NameFieldExtension.class), Jobs.newInput()
        .withRunContext(ClientRunContexts.copyCurrent())
        .withName("register extension"));
    //end::RegisterNameFieldExtension[]
  }

  public void registerPersonFormMainBoxExtension() {
    //tag::RegisterPersonFormMainBoxExtension[]
    BEANS.get(IExtensionRegistry.class).register(PersonFormMainBoxExtension.class);
    //end::RegisterPersonFormMainBoxExtension[]
  }

  public void accessExtendedFields() {
    //tag::accessExtendedFields[]
    // create a normal FormData
    // contributions are added/imported/exported automatically
    PersonFormData data = new PersonFormData();

    // access the data of an extension
    PersonFormMainBoxExtensionData c = data.getContribution(PersonFormMainBoxExtensionData.class);
    c.getSalary().setValue(new BigDecimal("200.0"));
    //end::accessExtendedFields[]
  }

  public void moveElements() {
    // the name field is moved into the LastBox at position 20.
    //tag::moveElement[]
    BEANS.get(IExtensionRegistry.class).registerMove(NameField.class, 20d, LastBox.class);
    //end::moveElement[]
  }
}
