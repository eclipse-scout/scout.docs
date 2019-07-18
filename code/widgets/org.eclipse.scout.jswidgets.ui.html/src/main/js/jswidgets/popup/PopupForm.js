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
jswidgets.PopupForm = function() {
  jswidgets.PopupForm.parent.call(this);
  this.$popupAnchor = null;
  this.popup = null;
};
scout.inherits(jswidgets.PopupForm, scout.Form);

jswidgets.PopupForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.PopupForm');
};

jswidgets.PopupForm.prototype._init = function(model) {
  jswidgets.PopupForm.parent.prototype._init.call(this, model);

  var dummyPopup = scout.create('WidgetPopup', {
    parent: this
  });
  this.widget('OpenPopupButton').on('click', this._onOpenPopupButtonClick.bind(this));

  var useButtonAsAnchorField = this.widget('UseButtonAsAnchorField');
  useButtonAsAnchorField.on('propertyChange', this._onUseButtonAsAnchorChange.bind(this));

  var anchorBoundsField = this.widget('AnchorBoundsField');
  anchorBoundsField.on('propertyChange', this._onAnchorBoundsChange.bind(this));

  var closeOnAnchorMouseDownField = this.widget('CloseOnAnchorMouseDownField');
  closeOnAnchorMouseDownField.setValue(dummyPopup.closeOnAnchorMouseDown);

  var closeOnMouseDownOutsideField = this.widget('CloseOnMouseDownOutsideField');
  closeOnMouseDownOutsideField.setValue(dummyPopup.closeOnMouseDownOutside);

  var closeOnOtherPopupOpenField = this.widget('CloseOnOtherPopupOpenField');
  closeOnOtherPopupOpenField.setValue(dummyPopup.closeOnOtherPopupOpen);

  var horizontalAlignmentField = this.widget('HorizontalAlignmentField');
  horizontalAlignmentField.setValue(dummyPopup.horizontalAlignment);
  horizontalAlignmentField.on('propertyChange', this._onHorizontalAlignmentPropertyChange.bind(this));

  var verticalAlignmentField = this.widget('VerticalAlignmentField');
  verticalAlignmentField.setValue(dummyPopup.verticalAlignment);
  verticalAlignmentField.on('propertyChange', this._onVerticalAlignmentPropertyChange.bind(this));

  var horizontalSwitchField = this.widget('HorizontalSwitchField');
  horizontalSwitchField.setValue(dummyPopup.horizontalSwitch);
  horizontalSwitchField.on('propertyChange', this._onHorizontalSwitchPropertyChange.bind(this));

  var verticalSwitchField = this.widget('VerticalSwitchField');
  verticalSwitchField.setValue(dummyPopup.verticalSwitch);
  verticalSwitchField.on('propertyChange', this._onVerticalSwitchPropertyChange.bind(this));

  var trimWidthField = this.widget('TrimWidthField');
  trimWidthField.setValue(dummyPopup.trimWidth);
  trimWidthField.on('propertyChange', this._onTrimWidthPropertyChange.bind(this));

  var trimHeightField = this.widget('TrimHeightField');
  trimHeightField.setValue(dummyPopup.trimHeight);
  trimHeightField.on('propertyChange', this._onTrimHeightPropertyChange.bind(this));

  var withArrowField = this.widget('WithArrowField');
  withArrowField.setValue(dummyPopup.withArrow);
  withArrowField.on('propertyChange', this._onWithArrowPropertyChange.bind(this));

  this.widget('WidgetPopupPropertiesBox').setField(dummyPopup);

  dummyPopup.close();
};

jswidgets.PopupForm.prototype._render = function() {
  jswidgets.PopupForm.parent.prototype._render.call(this);
  this._updateAnchor();
};

jswidgets.PopupForm.prototype._remove = function() {
  if (this.$popupAnchor) {
    this.$popupAnchor.remove();
    this.$popupAnchor = null;
  }
  jswidgets.PopupForm.parent.prototype._remove.call(this);
};

jswidgets.PopupForm.prototype._onOpenPopupButtonClick = function(model) {
  var $anchor;
  if (this.widget('UseButtonAsAnchorField').value) {
    $anchor = this.widget('OpenPopupButton').$field;
  }
  var anchorBounds = this._getAnchorBounds();
  this.popup = scout.create('WidgetPopup', {
    parent: this,
    $anchor: $anchor,
    anchorBounds: anchorBounds,
    closeOnAnchorMouseDown: this.widget('CloseOnAnchorMouseDownField').value,
    closeOnMouseDownOutside: this.widget('CloseOnMouseDownOutsideField').value,
    closeOnOtherPopupOpen: this.widget('CloseOnOtherPopupOpenField').value,
    closable: this.widget('ClosableField').value,
    movable: this.widget('MovableField').value,
    resizable: this.widget('ResizableField').value,
    horizontalAlignment: this.widget('HorizontalAlignmentField').value,
    verticalAlignment: this.widget('VerticalAlignmentField').value,
    trimWidth: this.widget('TrimWidthField').value,
    trimHeight: this.widget('TrimHeightField').value,
    horizontalSwitch: this.widget('HorizontalSwitchField').value,
    verticalSwitch: this.widget('VerticalSwitchField').value,
    withArrow: this.widget('WithArrowField').value,
    cssClass: 'popup-form-popup',
    widget: {
      objectType: "Label",
      htmlEnabled: true,
      value: '<h2>Hi, I\'m a popup!</h2>' +
        '<p>This widget popup contains a label to display some text.</p>' +
        '<p>Instead of a label you can use any other widget you like, the popup will act as wrapper for your widget.</p>' +
        '<p>The popup will be as big as its content, so you probably need to define its size, e.g. using the min- and max-width CSS properties.</p>'
    }
  });
  this.widget('EventsTab').setField(this.popup);
  this.widget('WidgetActionsBox').setField(this.popup);
  this.widget('WidgetPopupPropertiesBox').setField(this.popup);
  this.popup.open();
};

jswidgets.PopupForm.prototype._onUseButtonAsAnchorChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    var $anchor = null;
    if (this.widget('UseButtonAsAnchorField').value) {
      $anchor = this.widget('OpenPopupButton').$field;
    }
    this.popup.set$Anchor($anchor);
  }
};

jswidgets.PopupForm.prototype._onAnchorBoundsChange = function(event) {
  this._updateAnchor();
};

jswidgets.PopupForm.prototype._getAnchorBounds = function(event) {
  var anchorBoundsRaw = this.widget('AnchorBoundsField').value;
  if (anchorBoundsRaw) {
    anchorBoundsRaw = anchorBoundsRaw.split(',');
    return new scout.Rectangle(Number(anchorBoundsRaw[0]), Number(anchorBoundsRaw[1]), Number(anchorBoundsRaw[2]), Number(anchorBoundsRaw[3]));
  }
};

jswidgets.PopupForm.prototype._updateAnchor = function() {
  var anchorBounds = this._getAnchorBounds();
  if (!anchorBounds) {
    if (this.$popupAnchor) {
      this.$popupAnchor.remove();
      this.$popupAnchor = null;
    }
  } else {
    if (!this.$popupAnchor) {
      this.$popupAnchor = this.session.$entryPoint.appendDiv('popup-anchor');
    }
    scout.graphics.setBounds(this.$popupAnchor, anchorBounds);
  }
};

jswidgets.PopupForm.prototype._onHorizontalAlignmentPropertyChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    this.popup.setHorizontalAlignment(event.newValue);
  }
};

jswidgets.PopupForm.prototype._onVerticalAlignmentPropertyChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    this.popup.setVerticalAlignment(event.newValue);
  }
};

jswidgets.PopupForm.prototype._onHorizontalSwitchPropertyChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    this.popup.setHorizontalSwitch(event.newValue);
  }
};

jswidgets.PopupForm.prototype._onTrimWidthPropertyChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    this.popup.setTrimWidth(event.newValue);
  }
};

jswidgets.PopupForm.prototype._onTrimHeightPropertyChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    this.popup.setTrimHeight(event.newValue);
  }
};

jswidgets.PopupForm.prototype._onVerticalSwitchPropertyChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    this.popup.setVerticalSwitch(event.newValue);
  }
};

jswidgets.PopupForm.prototype._onWithArrowPropertyChange = function(event) {
  if (event.propertyName === 'value' && this.popup) {
    this.popup.setWithArrow(event.newValue);
  }
};
