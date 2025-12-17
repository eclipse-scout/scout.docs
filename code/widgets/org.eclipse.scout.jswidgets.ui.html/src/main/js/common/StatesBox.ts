/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {CheckBoxField, ChildModelOf, EventDelegator, EventEmitter, FormField, FormFieldMenu, GroupBox, InitModelOf, scout, Status, Widget, WidgetModel} from '@eclipse-scout/core';
import model, {StatesBoxModel} from './StatesBoxModel';

export class StatesBox extends GroupBox implements StatesBoxModel {
  declare model: StatesBoxModel;
  field: FormField;
  fieldModel: ChildModelOf<FormField>;
  fieldInitProperties: string[] = [];

  protected override _jsonModel(): WidgetModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setField(this.field);
  }

  setField(field: FormField) {
    this.setProperty('field', field);
  }

  protected _setField(field: FormField) {
    if (this.field) {
      throw new Error('Field is already set, it cannot be changed');
    }
    if (!field) {
      return;
    }
    this._setProperty('field', field);

    let initModel = {
      objectType: field.objectType,
      parent: this,
      ...this.fieldModel
    };
    for (const property of this.fieldInitProperties) {
      initModel[property] = this.field[property];
    }

    let errorField = scout.create({
      ...initModel,
      label: 'Error',
      errorStatus: {
        objectType: Status,
        severity: Status.Severity.ERROR,
        message: 'error'
      }
    });
    this._delegateProperties(errorField, 'errorStatus');

    let disabledField = scout.create({
      ...initModel,
      label: 'Disabled',
      enabled: false
    });
    this._delegateProperties(disabledField, 'enabled', 'enabled-default');

    let readOnlyField = scout.create({
      ...initModel,
      label: 'Read Only',
      enabled: false,
      disabledStyle: Widget.DisabledStyle.READ_ONLY
    });
    this._delegateProperties(readOnlyField, 'enabled', 'enabled-default', 'disabledStyle');

    let maskedField = scout.create({
      ...initModel,
      label: 'Masked',
      enabled: false,
      disabledStyle: Widget.DisabledStyle.MASKED
    });
    this._delegateProperties(maskedField, 'enabled', 'enabled-default', 'disabledStyle', 'value', 'displayText');

    this.setFields([...this.fields, errorField, disabledField, readOnlyField, maskedField]);

    this.getForm().rootGroupBox.insertMenu({
      id: 'ShowStatesMenu',
      objectType: FormFieldMenu,
      horizontalAlignment: 1,
      field: {
        id: 'ShowStatesField',
        objectType: CheckBoxField,
        cssClass: 'no-mandatory-indicator',
        labelVisible: false,
        statusVisible: false,
        label: 'Show States'
      }
    });
    let showStatesField = this.getForm().rootGroupBox.widget('ShowStatesField') as CheckBoxField;
    showStatesField.setValue(this.visible);
    showStatesField.on('propertyChange:value', event => {
      this.setVisible(event.newValue);
    });
  }

  protected _delegateProperties(disabledField: EventEmitter, ...exclusions: string[]) {
    EventDelegator.create(this.field, disabledField, {delegateAllProperties: true, excludeProperties: ['focused', ...exclusions]});
  }
}
