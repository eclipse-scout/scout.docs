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
import {Form, graphics, models, Rectangle, scout} from '@eclipse-scout/core';
import PopupFormModel from './PopupFormModel';

export default class PopupForm extends Form {

  constructor() {
    super();
    this.$popupAnchor = null;
    this.popup = null;
  }

  _jsonModel() {
    return models.get(PopupFormModel);
  }

  _init(model) {
    super._init(model);

    let dummyPopup = scout.create('WidgetPopup', {
      parent: this
    });
    this.widget('OpenPopupButton').on('click', this._onOpenPopupButtonClick.bind(this));

    let useButtonAsAnchorField = this.widget('UseButtonAsAnchorField');
    useButtonAsAnchorField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }

      let $anchor = null;
      if (this.widget('UseButtonAsAnchorField').value) {
        $anchor = this.widget('OpenPopupButton').$field;
      }
      this.popup.set$Anchor($anchor);
    });

    let anchorBoundsField = this.widget('AnchorBoundsField');
    anchorBoundsField.on('propertyChange', event => this._updateAnchor());

    let modalField = this.widget('ModalField');
    modalField.setValue(dummyPopup.modal);

    let closeOnAnchorMouseDownField = this.widget('CloseOnAnchorMouseDownField');
    closeOnAnchorMouseDownField.setValue(dummyPopup.closeOnAnchorMouseDown);

    let closeOnMouseDownOutsideField = this.widget('CloseOnMouseDownOutsideField');
    closeOnMouseDownOutsideField.setValue(dummyPopup.closeOnMouseDownOutside);

    let closeOnOtherPopupOpenField = this.widget('CloseOnOtherPopupOpenField');
    closeOnOtherPopupOpenField.setValue(dummyPopup.closeOnOtherPopupOpen);

    let horizontalAlignmentField = this.widget('HorizontalAlignmentField');
    horizontalAlignmentField.setValue(dummyPopup.horizontalAlignment);
    horizontalAlignmentField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }
      this.popup.setHorizontalAlignment(event.newValue);
    });

    let verticalAlignmentField = this.widget('VerticalAlignmentField');
    verticalAlignmentField.setValue(dummyPopup.verticalAlignment);
    verticalAlignmentField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }
      this.popup.setVerticalAlignment(event.newValue);
    });

    let horizontalSwitchField = this.widget('HorizontalSwitchField');
    horizontalSwitchField.setValue(dummyPopup.horizontalSwitch);
    horizontalSwitchField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }
      this.popup.setHorizontalSwitch(event.newValue);
    });

    let verticalSwitchField = this.widget('VerticalSwitchField');
    verticalSwitchField.setValue(dummyPopup.verticalSwitch);
    verticalSwitchField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }
      this.popup.setVerticalSwitch(event.newValue);
    });

    let trimWidthField = this.widget('TrimWidthField');
    trimWidthField.setValue(dummyPopup.trimWidth);
    trimWidthField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }
      this.popup.setTrimWidth(event.newValue);
    });

    let trimHeightField = this.widget('TrimHeightField');
    trimHeightField.setValue(dummyPopup.trimHeight);
    trimHeightField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }
      this.popup.setTrimHeight(event.newValue);
    });

    let withArrowField = this.widget('WithArrowField');
    withArrowField.setValue(dummyPopup.withArrow);
    withArrowField.on('propertyChange:value', event => {
      if (!this.popup) {
        return;
      }
      this.popup.setWithArrow(event.newValue);
    });

    this.widget('WidgetPopupPropertiesBox').setField(dummyPopup);

    dummyPopup.close();
  }

  _render() {
    super._render();
    this._updateAnchor();
  }

  _remove() {
    if (this.$popupAnchor) {
      this.$popupAnchor.remove();
      this.$popupAnchor = null;
    }
    super._remove();
  }

  _onOpenPopupButtonClick(model) {
    let $anchor;
    if (this.widget('UseButtonAsAnchorField').value) {
      $anchor = this.widget('OpenPopupButton').$field;
    }
    let anchorBounds = this._getAnchorBounds();
    this.popup = scout.create('WidgetPopup', {
      parent: this,
      $anchor: $anchor,
      anchorBounds: anchorBounds,
      modal: this.widget('ModalField').value,
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
      withGlassPane: this.widget('WithGlassPaneField').value,
      cssClass: 'popup-form-popup',
      content: {
        objectType: 'Label',
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
  }

  _getAnchorBounds(event) {
    let anchorBoundsRaw = this.widget('AnchorBoundsField').value;
    if (anchorBoundsRaw) {
      anchorBoundsRaw = anchorBoundsRaw.split(',');
      return new Rectangle(Number(anchorBoundsRaw[0]), Number(anchorBoundsRaw[1]), Number(anchorBoundsRaw[2]), Number(anchorBoundsRaw[3]));
    }
  }

  _updateAnchor() {
    let anchorBounds = this._getAnchorBounds();
    if (!anchorBounds) {
      if (this.$popupAnchor) {
        this.$popupAnchor.remove();
        this.$popupAnchor = null;
      }
    } else {
      if (!this.$popupAnchor) {
        this.$popupAnchor = this.session.$entryPoint.appendDiv('popup-anchor');
      }
      graphics.setBounds(this.$popupAnchor, anchorBounds);
    }
  }
}
