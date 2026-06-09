/*
 * Copyright (c) 2010, 2026 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ajax, AppLinkActionEvent, Form, FormModel, InitModelOf, MessageBoxes, models} from '@eclipse-scout/core';
import HtmlFieldFormModel from './HtmlFieldFormModel';
import {HtmlFieldFormWidgetMap} from '../index';

export class HtmlFieldForm extends Form {
  declare widgetMap: HtmlFieldFormWidgetMap;

  protected override _jsonModel(): FormModel {
    return models.get(HtmlFieldFormModel);
  }

  // noinspection DuplicatedCode
  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    let htmlField = this.widget('HtmlField');
    htmlField.setValue(this.session.text('HtmlFieldValue'));
    htmlField.on('appLinkAction', this._onAppLinkAction.bind(this));

    let selectableField = this.widget('SelectableField');
    selectableField.setValue(htmlField.selectable);
    selectableField.on('propertyChange:value', event => htmlField.setSelectable(event.newValue));

    let scrollBarEnabledField = this.widget('ScrollBarEnabledField');
    scrollBarEnabledField.setValue(htmlField.scrollBarEnabled);
    scrollBarEnabledField.on('propertyChange:value', event => htmlField.setScrollBarEnabled(event.newValue));

    let scrollToAnchorField = this.widget('ScrollToAnchorField');
    scrollToAnchorField.setValue(htmlField.scrollToAnchor);
    scrollToAnchorField.on('propertyChange:value', event => htmlField.setScrollToAnchor(event.newValue));

    let insertAnchorHtmlMenu = this.widget('InsertAnchorHtmlMenu');
    insertAnchorHtmlMenu.on('action', () => this._onInsertAnchorHtmlAction());

    this.widget('ValueField').setEnabled(true);
    this.widget('ValueFieldPropertiesBox').setField(htmlField);
    this.widget('FormFieldPropertiesBox').setField(htmlField);
    this.widget('GridDataBox').setField(htmlField);
    this.widget('WidgetActionsBox').setField(htmlField);
    this.widget('FormFieldActionsBox').setField(htmlField);
    this.widget('EventsTab').setField(htmlField);
    this.widget('StatesBox').setField(htmlField);
  }

  protected _onAppLinkAction(event: AppLinkActionEvent) {
    MessageBoxes.createOk(this)
      .withBody(this.session.text('ThanksForClickingMe'))
      .withYes(this.session.text('YoureWelcome'))
      .buildAndOpen();
  }

  protected async _onInsertAnchorHtmlAction() {
    let html = await ajax.get('html/ALotOfContent.html');
    this.widget('HtmlField').setValue(html);
  }
}
