/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {AppLinkActionEvent, DesktopNotification, Form, FormModel, HybridManager, InitModelOf, Label, objects, scout, Status, StringField, strings, WrappedFormField} from '@eclipse-scout/core';
import model, {HybridJsFormModel, HybridJsFormWidgetMap} from './HybridJsFormModel';
import {PersonDo} from './PersonDo';

export class HybridJsForm extends Form implements HybridJsFormModel {
  declare model: HybridJsFormModel;
  declare widgetMap: HybridJsFormWidgetMap;

  pageTitle: string;
  hybridDescriptionLabel: Label;
  pingLabel: Label;
  sendDesktopNotificationLabel: Label;
  openPersonFormLabel: Label;
  openFormPersonDoField: StringField;
  createPersonFormLabel: Label;
  createFormPersonDoField: StringField;
  createPersonFormWrappedFormField: WrappedFormField;

  constructor() {
    super();
  }

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.hybridDescriptionLabel = this.widget('HybridDescriptionLabel');
    this.hybridDescriptionLabel.setValue(`The page (${this.pageTitle}) you see here is implemented in Scout Classic but its detailForm is implemented in Scout JS.
This view demonstrates several hybrid abilities like opening a Scout Classic form from Scout JS or executing actions on the UI server from Scout JS.`);

    this.pingLabel = this.widget('PingLabel');
    this.pingLabel.on('appLinkAction', this._onPingLabelAppLinkAction.bind(this));

    this.sendDesktopNotificationLabel = this.widget('SendDesktopNotificationLabel');
    this.sendDesktopNotificationLabel.on('appLinkAction', this._onSendDesktopNotificationLabelAppLinkAction.bind(this));

    this.openPersonFormLabel = this.widget('OpenPersonFormLabel');
    this.openPersonFormLabel.on('appLinkAction', this._onOpenPersonFormLabelAppLinkAction.bind(this));

    this.openFormPersonDoField = this.widget('OpenFormBox.PersonDoField');
    this.openFormPersonDoField.addValidator(this._validatePersonDo.bind(this));

    this.createPersonFormLabel = this.widget('CreatePersonFormLabel');
    this.createPersonFormLabel.on('appLinkAction', this._onCreatePersonFormLabelAppLinkAction.bind(this));

    this.createFormPersonDoField = this.widget('CreateFormBox.PersonDoField');
    this.createFormPersonDoField.addValidator(this._validatePersonDo.bind(this));

    this.createPersonFormWrappedFormField = this.widget('CreatePersonFormWrappedFormField');
  }

  async _onPingLabelAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref === 'ping') {
      await HybridManager.get(this.session).triggerHybridActionAndWait('Ping');
      this.session.desktop.addNotification(scout.create(DesktopNotification, {
        parent: this,
        duration: 2000,
        closable: true,
        htmlEnabled: true,
        status: Status.info('<b>Scout JS</b><br>Ping successful!')
      }));
    }
  }

  _onSendDesktopNotificationLabelAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref === 'sendDesktopNotification') {
      HybridManager.get(this.session).triggerHybridAction('SendDesktopNotification');
    }
  }

  async _onOpenPersonFormLabelAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref === 'openPersonForm') {
      const personDo = PersonDo.of(this.openFormPersonDoField.value);
      const form = await HybridManager.get(this.session).openForm('Person', personDo ? personDo.toJson() : null);
      form.whenSave().then(() => this.openFormPersonDoField.setValue(JSON.stringify(form.data)));
    }
  }

  async _onCreatePersonFormLabelAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref === 'createPersonForm') {
      if (this.createPersonFormWrappedFormField.innerForm) {
        this.session.desktop.addNotification(scout.create(DesktopNotification, {
          parent: this,
          duration: 2000,
          closable: true,
          htmlEnabled: true,
          status: Status.info('<b>Scout JS</b><br>Form already open!')
        }));
        return;
      }
      const personDo = PersonDo.of(this.createFormPersonDoField.value);
      const form = await HybridManager.get(this.session).createForm('Person', personDo ? personDo.toJson() : null);
      form.whenSave().then(() => this.createFormPersonDoField.setValue(JSON.stringify(form.data)));
      this.createPersonFormWrappedFormField.setInnerForm(form);
      form.show();
    }
  }

  _validatePersonDo(value: string): string {
    let parsedValue;
    // Parse input
    if (strings.hasText(value)) {
      try {
        parsedValue = JSON.parse(value);
      } catch (err) {
        throw '' + err; // JSON syntax error
      }
    }
    // Format parsed value
    let formatted = '';
    if (parsedValue) {
      formatted = JSON.stringify(parsedValue, null, 2);
    }
    const personDo = PersonDo.of(parsedValue);
    if (!formatted || objects.equalsRecursive(parsedValue, personDo ? personDo.toJson() : null)) {
      return formatted;
    }
    throw 'Expected JSON type: PersonDo';
  }
}
