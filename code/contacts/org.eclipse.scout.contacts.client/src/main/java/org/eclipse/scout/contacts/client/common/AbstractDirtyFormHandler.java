/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.scout.contacts.client.common;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.scout.contacts.client.Icons;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.IForm;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.IValueField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.exception.ExceptionHandler;

public abstract class AbstractDirtyFormHandler extends AbstractFormHandler {

  private static final String PROP_DIRTY_LISTENER = AbstractDirtyFormHandler.class.getName() + ".dirtyListener";

  @Override
  protected void execPostLoad() {
    installDirtyListener(getForm());
  }

  @Override
  protected void execFinally() {
    uninstallDirtyListener(getForm());
  }

  /**
   * Method invoked once the dirty status of the {@link IForm} changes.
   */
  protected void execDirtyStatusChanged(final boolean dirty) {
  }

  private void installDirtyListener(final IForm form) {
    final Set<IFormField> dirtyFields = new HashSet<>();

    final PropertyChangeListener dirtyListener = evt -> {
      if (form.isFormLoading()) {
        return;
      }

      // Check whether this field is dirty
      final IFormField formField = (IFormField) evt.getSource();
      formField.checkSaveNeeded();
      if (formField.isSaveNeeded()) {
        dirtyFields.add(formField);
      }
      else {
        dirtyFields.remove(formField);
      }

      final boolean dirty = dirtyFields.isEmpty();
      form.setIconId(dirty ? null : Icons.Pencil);

      try {
        execDirtyStatusChanged(dirty);
      }
      catch (final Exception e) {
        BEANS.get(ExceptionHandler.class).handle(e);
      }
    };
    form.setProperty(PROP_DIRTY_LISTENER, dirtyListener);

    for (final IFormField field : form.getAllFields()) {
      field.addPropertyChangeListener(IValueField.PROP_DISPLAY_TEXT, dirtyListener);
    }
  }

  private void uninstallDirtyListener(final IForm form) {
    final Object dirtyListener = form.getProperty(PROP_DIRTY_LISTENER);
    if (dirtyListener instanceof PropertyChangeListener) {
      form.removePropertyChangeListener((PropertyChangeListener) dirtyListener);
    }
  }
}
