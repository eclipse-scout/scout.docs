/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Event, Form, InitModelOf, Menu, MessageBoxes, models, SmartField} from '@eclipse-scout/core';
import SmartFieldFormModel from './SmartFieldFormModel';
import {SmartFieldFormWidgetMap} from '../index';

export class SmartFieldForm extends Form {
  declare widgetMap: SmartFieldFormWidgetMap;

  smartField: SmartField<any>;

  constructor() {
    super();
  }

  protected override _jsonModel() {
    return models.get(SmartFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.smartField = this.widget('SmartField');

    let newLanguageMenu = this.widget('NewLanguageMenu');
    newLanguageMenu.on('action', this._onNewLanguageMenuAction.bind(this));

    this.widget('SmartFieldPropertiesBox').setField(this.smartField);
    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(this.smartField);
    this.widget('FormFieldPropertiesBox').setField(this.smartField);
    this.widget('GridDataBox').setField(this.smartField);
    this.widget('WidgetActionsBox').setField(this.smartField);
    this.widget('FormFieldActionsBox').setField(this.smartField);
    this.widget('EventsTab').setField(this.smartField);
  }

  protected _onNewLanguageMenuAction(event: Event<Menu>) {
    return MessageBoxes.createOk(this)
      .withHeader(this.session.text('ThanksForClickingMe'))
      .withBody(this.session.text('NewLanguageMessage'))
      .buildAndOpen();
  }
}
