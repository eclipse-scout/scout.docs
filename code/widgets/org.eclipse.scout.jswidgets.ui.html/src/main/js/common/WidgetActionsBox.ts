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
import {Button, Event, GroupBox, GroupBoxModel, InitModelOf, LabelField, models, Widget} from '@eclipse-scout/core';
import WidgetActionsBoxModel from './WidgetActionsBoxModel';
import {WidgetActionsBoxWidgetMap} from '../index';

export class WidgetActionsBox extends GroupBox {
  declare widgetMap: WidgetActionsBoxWidgetMap;
  field: Widget;

  constructor() {
    super();
    this.field = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(WidgetActionsBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field: Widget) {
    this.setProperty('field', field);
  }

  protected _setField(field: Widget) {
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }
    let focusButton = this.widget('FocusButton');
    focusButton.on('click', this._onFocusButtonClick.bind(this));

    let isFocusableButton = this.widget('IsFocusableButton');
    isFocusableButton.on('click', this._onIsFocusableButtonClick.bind(this));

    let revealButton = this.widget('RevealButton');
    revealButton.on('click', this._onRevealButtonClick.bind(this));

    let scrollToTopButton = this.widget('ScrollToTopButton');
    scrollToTopButton.on('click', this._onScrollToTopButtonClick.bind(this));

    let scrollToBottomButton = this.widget('ScrollToBottomButton');
    scrollToBottomButton.on('click', this._onScrollToBottomButtonClick.bind(this));

    let scrollTopField = this.widget('ScrollTopField');
    scrollTopField.setValue(this.field.scrollTop);
    scrollTopField.on('propertyChange:value', event => this.field.setScrollTop(event.newValue));
  }

  protected _onFocusButtonClick(event: Event<Button>) {
    let returned = this.field.focus();
    let returnField = this.widget('FocusReturnField');
    this._updateBooleanReturnValue(returnField, returned);
  }

  protected _onIsFocusableButtonClick(event: Event<Button>) {
    let returned = this.field.isFocusable();
    let returnField = this.widget('IsFocusableReturnField');
    this._updateBooleanReturnValue(returnField, returned);
  }

  protected _updateBooleanReturnValue(returnField: LabelField, returned: boolean) {
    if (returned) {
      returnField.setValue('-> returned true');
    } else {
      returnField.setValue('-> returned false');
    }
    returnField.toggleCssClass('action-return-success', returned);
    returnField.toggleCssClass('action-return-fail', !returned);
  }

  protected _onRevealButtonClick(event: Event<Button>) {
    this.field.reveal(null);
  }

  protected _onScrollToTopButtonClick(event: Event<Button>) {
    this.field.scrollToTop();
  }

  protected _onScrollToBottomButtonClick(event: Event<Button>) {
    this.field.scrollToBottom();
  }
}
