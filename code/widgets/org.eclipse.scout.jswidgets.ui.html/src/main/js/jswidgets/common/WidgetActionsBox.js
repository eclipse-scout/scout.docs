/*******************************************************************************
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
jswidgets.WidgetActionsBox = function() {
  jswidgets.WidgetActionsBox.parent.call(this);
  this.field = null;
};
scout.inherits(jswidgets.WidgetActionsBox, scout.GroupBox);

jswidgets.WidgetActionsBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.WidgetActionsBox');
};

jswidgets.WidgetActionsBox.prototype._init = function(model) {
  jswidgets.WidgetActionsBox.parent.prototype._init.call(this, model);

  this._setField(this.field);
};

jswidgets.WidgetActionsBox.prototype.setField = function(field) {
  this.setProperty('field', field);
};

jswidgets.WidgetActionsBox.prototype._setField = function(field) {
  this._setProperty('field', field);
  if (!this.field) {
    return;
  }
  var focusButton = this.widget('FocusButton');
  focusButton.on('click', this._onFocusButtonClick.bind(this));

  var isFocusableButton = this.widget('IsFocusableButton');
  isFocusableButton.on('click', this._onIsFocusableButtonClick.bind(this));

  var revealButton = this.widget('RevealButton');
  revealButton.on('click', this._onRevealButtonClick.bind(this));

  var scrollToTopButton = this.widget('ScrollToTopButton');
  scrollToTopButton.on('click', this._onScrollToTopButtonClick.bind(this));

  var scrollToBottomButton = this.widget('ScrollToBottomButton');
  scrollToBottomButton.on('click', this._onScrollToBottomButtonClick.bind(this));

  var scrollTopField = this.widget('ScrollTopField');
  scrollTopField.setValue(this.field.scrollTop);
  scrollTopField.on('propertyChange', this._onScrollTopPropertyChange.bind(this));
};

jswidgets.WidgetActionsBox.prototype._onFocusButtonClick = function(event) {
  var returned = this.field.focus();
  var returnField = this.widget('FocusReturnField');
  this._updateBooleanReturnValue(returnField, returned);
};

jswidgets.WidgetActionsBox.prototype._onIsFocusableButtonClick = function(event) {
  var returned = this.field.isFocusable();
  var returnField = this.widget('IsFocusableReturnField');
  this._updateBooleanReturnValue(returnField, returned);
};

jswidgets.WidgetActionsBox.prototype._updateBooleanReturnValue = function(returnField, returned) {
  if (returned) {
    returnField.setValue('-> returned true');
  } else {
    returnField.setValue('-> returned false');
  }
  returnField.toggleCssClass('action-return-success', returned);
  returnField.toggleCssClass('action-return-fail', !returned);
};

jswidgets.WidgetActionsBox.prototype._onRevealButtonClick = function(event) {
  this.field.reveal();
};

jswidgets.WidgetActionsBox.prototype._onScrollToTopButtonClick = function(event) {
  this.field.scrollToTop();
};

jswidgets.WidgetActionsBox.prototype._onScrollToBottomButtonClick = function(event) {
  this.field.scrollToBottom();
};

jswidgets.WidgetActionsBox.prototype._onScrollTopPropertyChange = function(event) {
  if (event.propertyName === 'value') {
    this.field.setScrollTop(event.newValue);
  }
};
