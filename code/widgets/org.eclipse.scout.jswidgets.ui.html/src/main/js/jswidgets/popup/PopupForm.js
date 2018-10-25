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
};
scout.inherits(jswidgets.PopupForm, scout.Form);

jswidgets.PopupForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.PopupForm');
};

jswidgets.PopupForm.prototype._init = function(model) {
  jswidgets.PopupForm.parent.prototype._init.call(this, model);

  var dummyPopup = scout.create('Popup', {
    parent: this
  });
  this.widget('OpenPopupButton').on('click', this._onOpenPopupButtonClick.bind(this));

  var anchorBoundsField = this.widget('AnchorBoundsField');
  anchorBoundsField.on('propertyChange', this._onAnchorBoundsChange.bind(this));

  var horizontalAlignmentField = this.widget('HorizontalAlignmentField');
  horizontalAlignmentField.setValue(dummyPopup.horizontalAlignment);

  var verticalAlignmentField = this.widget('VerticalAlignmentField');
  verticalAlignmentField.setValue(dummyPopup.verticalAlignment);

  var trimWidthField = this.widget('TrimWidthField');
  trimWidthField.setValue(dummyPopup.trimWidth);

  var trimHeightField = this.widget('TrimHeightField');
  trimHeightField.setValue(dummyPopup.trimHeight);

  var horizontalSwitchField = this.widget('HorizontalSwitchField');
  horizontalSwitchField.setValue(dummyPopup.horizontalSwitch);

  var verticalSwitchField = this.widget('VerticalSwitchField');
  verticalSwitchField.setValue(dummyPopup.verticalSwitch);

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
  var popup = scout.create('WidgetPopup', {
    parent: this,
    $anchor: $anchor,
    anchorBounds: anchorBounds,
    horizontalAlignment: this.widget('HorizontalAlignmentField').value,
    verticalAlignment: this.widget('VerticalAlignmentField').value,
    trimWidth: this.widget('TrimWidthField').value,
    trimHeight: this.widget('TrimHeightField').value,
    horizontalSwitch: this.widget('HorizontalSwitchField').value,
    verticalSwitch: this.widget('VerticalSwitchField').value,
    cssClass: 'popup-form-popup',
    widget: {
      objectType: "jswidgets.Text",
      htmlEnabled: true,
      text: '<h2>Hi, I\'m a popup!</h2>' +
          '<p>This widget popup contains a label field to display some text.</p>' +
          '<p>Instead of a label field you can use any other widget you like, the popup will act as wrapper for your widget.</p>' +
          '<p>The popup will be as big as its content, so you probably need to define its size, e.g. using the min- and max-width CSS properties.</p>'
    }
  });
  this.widget('EventsTab').setField(popup);
  this.widget('WidgetActionsBox').setField(popup);
  popup.open();
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
