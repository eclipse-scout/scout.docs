/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {arrays, DefaultStatus, GroupBox, models, numbers, objects, scout, Status} from '@eclipse-scout/core';
import FormFieldPropertiesBoxModel from './FormFieldPropertiesBoxModel';

export default class FormFieldPropertiesBox extends GroupBox {

  constructor() {
    super();
    this.field = null;
    this._fileDropHandler = this._onFileDrop.bind(this);
  }

  _jsonModel() {
    return models.get(FormFieldPropertiesBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field) {
    this.setProperty('field', field);
  }

  _setField(field) {
    if (this.field) {
      this.field.off('drop', this._fileDropHandler);
    }
    this._setProperty('field', field);
    if (!this.field) {
      return;
    }
    this.field.on('drop', this._fileDropHandler);
    let enabledField = this.widget('EnabledField');
    enabledField.setValue(this.field.enabled);
    enabledField.on('propertyChange', this._onPropertyChange.bind(this));

    let visibleField = this.widget('VisibleField');
    visibleField.setValue(this.field.visible);
    visibleField.on('propertyChange', this._onPropertyChange.bind(this));

    let labelVisibleField = this.widget('LabelVisibleField');
    labelVisibleField.setValue(this.field.labelVisible);
    labelVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

    let statusVisibleField = this.widget('StatusVisibleField');
    statusVisibleField.setValue(this.field.statusVisible);
    statusVisibleField.on('propertyChange', this._onPropertyChange.bind(this));

    let mandatoryField = this.widget('MandatoryField');
    mandatoryField.setValue(this.field.mandatory);
    mandatoryField.on('propertyChange', this._onPropertyChange.bind(this));

    let loadingField = this.widget('LoadingField');
    loadingField.setValue(this.field.loading);
    loadingField.on('propertyChange', this._onPropertyChange.bind(this));

    let labelHtmlEnabledField = this.widget('LabelHtmlEnabledField');
    labelHtmlEnabledField.setValue(this.field.labelHtmlEnabled);
    labelHtmlEnabledField.on('propertyChange', this._onPropertyChange.bind(this));

    let inheritAccessibilityField = this.widget('InheritAccessibilityField');
    inheritAccessibilityField.setValue(this.field.inheritAccessibility);
    inheritAccessibilityField.on('propertyChange', this._onPropertyChange.bind(this));

    let fieldStyleField = this.widget('FieldStyleField');
    fieldStyleField.setValue(this.field.fieldStyle);
    fieldStyleField.on('propertyChange', this._onPropertyChange.bind(this));

    let disabledStyleField = this.widget('DisabledStyleField');
    disabledStyleField.setValue(this.field.disabledStyle);
    disabledStyleField.on('propertyChange', this._onPropertyChange.bind(this));

    let dropTypeField = this.widget('DropTypeField');
    dropTypeField.setValue(this.field.dropType);
    dropTypeField.on('propertyChange', this._onPropertyChange.bind(this));

    let dropMaximumSizeField = this.widget('DropMaximumSizeField');
    dropMaximumSizeField.setValue(this.field.dropMaximumSize);
    dropMaximumSizeField.on('propertyChange', this._onPropertyChange.bind(this));

    let labelField = this.widget('LabelField');
    labelField.setValue(this.field.label);
    labelField.on('propertyChange', this._onPropertyChange.bind(this));

    let labelPositionField = this.widget('LabelPositionField');
    labelPositionField.setValue(this.field.labelPosition);
    labelPositionField.on('propertyChange', this._onPropertyChange.bind(this));

    let labelWidthInPixelField = this.widget('LabelWidthInPixelField');
    labelWidthInPixelField.setValue(this.field.labelWidthInPixel);
    labelWidthInPixelField.on('propertyChange', this._onPropertyChange.bind(this));

    let errorStatusField = this.widget('ErrorStatusField');
    errorStatusField.setValue(this.field.errorStatus ? this.field.errorStatus.severity : null);
    errorStatusField.on('propertyChange', this._onPropertyChange.bind(this));

    let tooltipTextField = this.widget('TooltipTextField');
    tooltipTextField.setValue(this.field.tooltipText);
    tooltipTextField.on('propertyChange', this._onPropertyChange.bind(this));

    let tooltipAnchorField = this.widget('TooltipAnchorField');
    tooltipAnchorField.setValue(this.field.tooltipAnchor);
    tooltipAnchorField.on('propertyChange', this._onPropertyChange.bind(this));

    let statusPositionField = this.widget('StatusPositionField');
    statusPositionField.setValue(this.field.statusPosition);
    statusPositionField.on('propertyChange', this._onPropertyChange.bind(this));
  }

  _onPropertyChange(event) {
    if (event.propertyName === 'value' && event.source.id === 'EnabledField') {
      this.field.setEnabled(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'VisibleField') {
      this.field.setVisible(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'LabelVisibleField') {
      this.field.setLabelVisible(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'LabelHtmlEnabledField') {
      this.field.setLabelHtmlEnabled(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'StatusVisibleField') {
      this.field.setStatusVisible(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'MandatoryField') {
      this.field.setMandatory(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'LoadingField') {
      this.field.setLoading(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'InheritAccessibilityField') {
      this.field.setInheritAccessibility(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'FieldStyleField') {
      this.field.setFieldStyle(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'DisabledStyleField') {
      this.field.setDisabledStyle(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'DropTypeField') {
      this.field.setDropType(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'DropMaximumSizeField') {
      this.field.setDropMaximumSize(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'LabelField') {
      this.field.setLabel(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'LabelPositionField') {
      this.field.setLabelPosition(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'LabelWidthInPixelField') {
      if (event.source.lookupRow) {
        this.field.setLabelWidthInPixel(event.source.lookupRow.key);
      } else {
        this.field.setLabelWidthInPixel(numbers.ensure(event.newValue));
      }
    } else if (event.propertyName === 'value' && event.source.id === 'ErrorStatusField') {
      this.field.removeErrorStatus(DefaultStatus);
      if (event.newValue) {
        this.field.setErrorStatus(new DefaultStatus({
          severity: event.newValue,
          message: this.session.text('FormFieldStatusMessage', objects.keyByValue(Status.Severity, event.newValue))
        }));
      }
    } else if (event.propertyName === 'value' && event.source.id === 'TooltipTextField') {
      this.field.setTooltipText(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'TooltipAnchorField') {
      this.field.setTooltipAnchor(event.newValue);
    } else if (event.propertyName === 'value' && event.source.id === 'StatusPositionField') {
      this.field.setStatusPosition(event.newValue);
    }
  }

  _onFileDrop(event) {
    scout.create('DesktopNotification', {
      parent: this,
      duration: 7500,
      status: {
        severity: Status.Severity.OK,
        message: this.session.text('FilesDroppedSuccessfully') + '\n' + this._filesToString(event.files)
      }
    }).show();
  }

  _filesToString(files) {
    return arrays.format(files.map(file => {
      return `
Name: ${file.name}
Size: ${file.size}
Type: ${file.type}`;
    }), '\n');
  }
}
