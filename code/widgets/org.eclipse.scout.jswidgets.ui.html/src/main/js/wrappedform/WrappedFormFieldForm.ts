/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Form, FormModel, InitModelOf, models, scout} from '@eclipse-scout/core';
import WrappedFormFieldFormModel from './WrappedFormFieldFormModel';
import {WrappedFormFieldFormWidgetMap} from '../index';

export class WrappedFormFieldForm extends Form {
  declare widgetMap: WrappedFormFieldFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return models.get(WrappedFormFieldFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let wrappedFormField = this.widget('WrappedFormField');

    let initialFocusEnabledField = this.widget('InitialFocusEnabledField');
    initialFocusEnabledField.setValue(wrappedFormField.initialFocusEnabled);
    initialFocusEnabledField.on('propertyChange:value', event => wrappedFormField.setInitialFocusEnabled(event.newValue));

    let innerFormField = this.widget('InnerFormField');
    innerFormField.on('propertyChange:value', event => {
      let innerForm = null;
      if (event.newValue) {
        innerForm = scout.create(event.newValue, {parent: wrappedFormField});
        innerForm.one('close', event => innerFormField.setValue(null));
      }
      wrappedFormField.setInnerForm(innerForm);
    });

    let closeInnerFormButton = this.widget('CloseInnerFormButton');
    wrappedFormField.on('propertyChange:innerForm', event => closeInnerFormButton.setEnabled(!!event.newValue));
    closeInnerFormButton.on('click', event => wrappedFormField.innerForm.close());

    this.widget('FormFieldPropertiesBox').setField(wrappedFormField);
    this.widget('GridDataBox').setField(wrappedFormField);

    this.widget('FormFieldActionsBox').setField(wrappedFormField);
    this.widget('WidgetActionsBox').setField(wrappedFormField);
    this.widget('EventsTab').setField(wrappedFormField);
  }
}
