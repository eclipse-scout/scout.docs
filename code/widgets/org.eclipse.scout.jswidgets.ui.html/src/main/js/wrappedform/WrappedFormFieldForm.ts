/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Button, Form, FormModel, InitModelOf, models, scout, SmartField, WrappedFormField} from '@eclipse-scout/core';
import WrappedFormFieldFormModel from './WrappedFormFieldFormModel';
import {EventsTab, FormFieldPropertiesBox, GridDataBox, WidgetActionsBox, WrappedFormFieldFormWidgetMap} from '../index';

export class WrappedFormFieldForm extends Form {
  declare widgetMap: WrappedFormFieldFormWidgetMap;

  innerFormField: SmartField<Form>;
  closeInnerFormButton: Button;
  wrappedFormField: WrappedFormField;
  formFieldPropertiesBox: FormFieldPropertiesBox;
  gridDataBox: GridDataBox;
  widgetActionsBox: WidgetActionsBox;
  eventsTab: EventsTab;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(WrappedFormFieldFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.innerFormField = this.widget('InnerFormField');
    this.closeInnerFormButton = this.widget('CloseInnerFormButton');
    this.wrappedFormField = this.widget('WrappedFormField');

    this.innerFormField.on('propertyChange:value', event => {
      let innerForm = null;
      if (event.newValue) {
        innerForm = scout.create(event.newValue, {parent: this.wrappedFormField});
        innerForm.one('close', event => this.innerFormField.setValue(null));
      }
      this.wrappedFormField.setInnerForm(innerForm);
    });
    this.wrappedFormField.on('propertyChange:innerForm', event => this.closeInnerFormButton.setEnabled(!!event.newValue));
    this.closeInnerFormButton.on('click', event => this.wrappedFormField.innerForm.close());

    this.formFieldPropertiesBox = this.widget('FormFieldPropertiesBox');
    this.formFieldPropertiesBox.setField(this.wrappedFormField);
    this.gridDataBox = this.widget('GridDataBox');
    this.gridDataBox.setField(this.wrappedFormField);

    this.widgetActionsBox = this.widget('WidgetActionsBox');
    this.widgetActionsBox.setField(this.wrappedFormField);
    this.eventsTab = this.widget('EventsTab');
    this.eventsTab.setField(this.wrappedFormField);
  }
}
