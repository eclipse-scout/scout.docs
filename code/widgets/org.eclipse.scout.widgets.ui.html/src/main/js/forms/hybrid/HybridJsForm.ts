/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {AppLinkActionEvent, dataObjects, DesktopNotification, Form, FormModel, HybridManager, InitModelOf, Label, Menu, objects, scout, Status, StringField, strings, WrappedFormField} from '@eclipse-scout/core';
import model, {HybridJsFormWidgetMap} from './HybridJsFormModel';
import {PersonDo} from '../../index';

export class HybridJsForm extends Form {
  declare widgetMap: HybridJsFormWidgetMap;

  hybridDescriptionLabel: Label;
  pingLabel: Label;
  sendDesktopNotificationLabel: Label;
  openPersonFormLabel: Label;
  openFormPersonDoField: StringField;
  createPersonFormLabel: Label;
  createFormPersonDoField: StringField;
  createPersonFormWrappedFormField: WrappedFormField;
  createFormCloseMenu: Menu;

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.hybridDescriptionLabel = this.widget('HybridDescriptionLabel');
    this.hybridDescriptionLabel.setValue('The page you see here is implemented in Scout Classic but its detailForm is implemented in <b>Scout JS</b>.<br>' +
      'This view demonstrates several hybrid abilities like opening a Scout Classic form from Scout JS or executing actions on the UI server from Scout JS.');

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

    this.createFormCloseMenu = this.widget('CreateFormCloseMenu');
    this.createFormCloseMenu.on('action', () => this.createPersonFormWrappedFormField.innerForm.close());

    this.createPersonFormWrappedFormField = this.widget('CreatePersonFormWrappedFormField');
    this.createPersonFormWrappedFormField.on('propertyChange:innerForm', event => this.createFormCloseMenu.setVisible(!!event.newValue));
  }

  protected async _onPingLabelAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref === 'ping') {
      await HybridManager.get(this.session).callActionAndWait('widgets.Ping');
      this.session.desktop.addNotification(scout.create(DesktopNotification, {
        parent: this,
        duration: 2000,
        closable: true,
        htmlEnabled: true,
        status: Status.info('<b>Scout JS</b><br>Ping successful!')
      }));
    }
  }

  protected _onSendDesktopNotificationLabelAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref === 'sendDesktopNotification') {
      HybridManager.get(this.session).callAction('widgets.SendDesktopNotification');
    }
  }

  protected async _onOpenPersonFormLabelAppLinkAction(event: AppLinkActionEvent) {
    if (event.ref === 'openPersonForm') {
      const personDo = dataObjects.parse(this.openFormPersonDoField.value, PersonDo);
      const form = await HybridManager.get(this.session).openForm('widgets.Person', personDo);
      form.whenSave().then(() => this.openFormPersonDoField.setValue(dataObjects.stringify(form.data)));
    }
  }

  protected async _onCreatePersonFormLabelAppLinkAction(event: AppLinkActionEvent) {
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
      const personDo = dataObjects.parse(this.createFormPersonDoField.value, PersonDo);
      const form = await HybridManager.get(this.session).createForm('widgets.Person', personDo);
      form.whenSave().then(() => this.createFormPersonDoField.setValue(dataObjects.stringify(form.data)));
      this.createPersonFormWrappedFormField.setInnerForm(form);
    }
  }

  protected _validatePersonDo(value: string): string {
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
    const personDo = dataObjects.deserialize(parsedValue, PersonDo);
    if (!formatted || objects.equalsRecursive(parsedValue, personDo ? personDo.toPojo() : null)) {
      return formatted;
    }
    throw 'Expected JSON type: PersonDo';
  }
}
