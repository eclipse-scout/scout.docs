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
import {GroupBox, models} from '@eclipse-scout/core';
import WidgetActionsBoxModel from './WidgetActionsBoxModel';

export default class WidgetActionsBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
  }

  _jsonModel() {
    return models.get(WidgetActionsBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
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

  _onFocusButtonClick(event) {
    let returned = this.field.focus();
    let returnField = this.widget('FocusReturnField');
    this._updateBooleanReturnValue(returnField, returned);
  }

  _onIsFocusableButtonClick(event) {
    let returned = this.field.isFocusable();
    let returnField = this.widget('IsFocusableReturnField');
    this._updateBooleanReturnValue(returnField, returned);
  }

  _updateBooleanReturnValue(returnField, returned) {
    if (returned) {
      returnField.setValue('-> returned true');
    } else {
      returnField.setValue('-> returned false');
    }
    returnField.toggleCssClass('action-return-success', returned);
    returnField.toggleCssClass('action-return-fail', !returned);
  }

  _onRevealButtonClick(event) {
    this.field.reveal();
  }

  _onScrollToTopButtonClick(event) {
    this.field.scrollToTop();
  }

  _onScrollToBottomButtonClick(event) {
    this.field.scrollToBottom();
  }
}
