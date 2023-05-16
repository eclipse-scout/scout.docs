/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {AppLinkActionEvent, DefaultStatus, Form, FormField, FormModel, GroupBox, InitModelOf, LabelField, models, objects, SmartField, Status, StatusSeverity} from '@eclipse-scout/core';
import $ from 'jquery';
import LifecycleFormModel from './LifecycleFormModel';
import {FormFieldLookupCall, LifecycleFormWidgetMap} from '../index';

export class LifecycleForm extends Form {
  declare widgetMap: LifecycleFormWidgetMap;

  detailBox: GroupBox;
  addErrorStatusWithSeverityField: LabelField;
  statusSeverityField: SmartField<StatusSeverity>;
  targetField: SmartField<FormField>;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return models.get(LifecycleFormModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.widget('HasCloseButtonField').on('propertyChange:value', event => this.widget('CloseMenu').setVisible(event.newValue));

    let askIfNeedSaveField = this.widget('AskIfNeedSaveField');
    askIfNeedSaveField.setValue(this.askIfNeedSave);
    askIfNeedSaveField.on('propertyChange:value', event => this.setAskIfNeedSave(event.newValue));

    this.detailBox = this.widget('DetailBox');

    this.addErrorStatusWithSeverityField = this.widget('AddErrorStatusWithSeverityField');
    this.addErrorStatusWithSeverityField.on('appLinkAction', this._onAddErrorStatusWithSeverityFieldAppLinkAction.bind(this));

    this.statusSeverityField = this.widget('StatusSeverityField');

    this.targetField = this.widget('TargetField');
    this.targetField.setLookupCall(new FormFieldLookupCall(this.detailBox));
  }

  protected _onAddErrorStatusWithSeverityFieldAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref !== 'add') {
      return;
    }

    const target = this.targetField.value;
    if (!target) {
      return;
    }
    target.removeErrorStatus(DefaultStatus);

    const severity = this.statusSeverityField.value;
    if (!severity) {
      return;
    }

    target.setErrorStatus(new DefaultStatus({
      severity, message: this.session.text('FormFieldStatusMessage', objects.keyByValue(Status.Severity, severity))
    }));
  }

  override importData() {
    this.widget('NameField').setValue(this.data.name);
    this.widget('BirthdayField').setValue(this.data.birthday);
  }

  override exportData(): LifecycleFormData {
    return {
      name: this.widget('NameField').value,
      birthday: this.widget('BirthdayField').value
    };
  }

  protected override _save(data: LifecycleFormData): JQuery.Promise<void> {
    if (!this.widget('ExceptionField').value) {
      return super._save(data);
    }
    // Simulate a failing asynchronous save operation (e.g. an ajax call)
    return $.resolvedPromise().then(() => {
      throw new Error('Saving failed');
    });
  }
}

export type LifecycleFormData = {
  name?: string;
  birthday?: Date;
};
