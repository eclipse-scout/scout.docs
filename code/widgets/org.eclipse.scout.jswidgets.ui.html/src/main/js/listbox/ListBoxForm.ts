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
import {Form, FormModel, InitModelOf, ListBox, LookupCall, models, SmartField} from '@eclipse-scout/core';
import ListBoxFormModel from './ListBoxFormModel';
import {ListBoxFormWidgetMap} from '../index';

export class ListBoxForm extends Form {
  declare widgetMap: ListBoxFormWidgetMap;

  listBox: ListBox<any>;
  lookupCallField: SmartField<LookupCall<any>>;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(ListBoxFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.listBox = this.widget('ListBox');

    this.lookupCallField = this.widget('LookupCallField');
    this.lookupCallField.setValue(this.listBox.lookupCall);
    this.lookupCallField.on('propertyChange:value', event => this.listBox.setLookupCall(event.newValue));
    this.listBox.on('propertyChange:lookupCall', event => this.lookupCallField.setValue(event.newValue));

    this.widget('ValueFieldPropertiesBox').setField(this.listBox);
    this.widget('FormFieldPropertiesBox').setField(this.listBox);
    this.widget('PropertiesBox').setTable(this.listBox.table);
    this.widget('GridDataBox').setField(this.listBox);
    this.widget('WidgetActionsBox').setField(this.listBox);
    this.widget('FormFieldActionsBox').setField(this.listBox);
    this.widget('EventsTab').setField(this.listBox);
  }
}
