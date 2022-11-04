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
import {arrays, DefaultStatus, DesktopNotification, FileDropEvent, FormField, GroupBox, GroupBoxModel, InitModelOf, models, numbers, objects, scout, Status} from '@eclipse-scout/core';
import FormFieldPropertiesBoxModel from './FormFieldPropertiesBoxModel';
import {FormFieldPropertiesBoxWidgetMap} from '../index';

export class FormFieldPropertiesBox extends GroupBox {
  declare widgetMap: FormFieldPropertiesBoxWidgetMap;

  field: FormField;
  protected _fileDropHandler: any;

  constructor() {
    super();
    this.field = null;
    this._fileDropHandler = this._onFileDrop.bind(this);
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(FormFieldPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setField(this.field);
  }

  setField(field: FormField) {
    this.setProperty('field', field);
  }

  protected _setField(field: FormField) {
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
    enabledField.on('propertyChange:value', event => this.field.setEnabled(event.newValue));

    let visibleField = this.widget('VisibleField');
    visibleField.setValue(this.field.visible);
    visibleField.on('propertyChange:value', event => this.field.setVisible(event.newValue));

    let labelVisibleField = this.widget('LabelVisibleField');
    labelVisibleField.setValue(this.field.labelVisible);
    labelVisibleField.on('propertyChange:value', event => this.field.setLabelVisible(event.newValue));

    let statusVisibleField = this.widget('StatusVisibleField');
    statusVisibleField.setValue(this.field.statusVisible);
    statusVisibleField.on('propertyChange:value', event => this.field.setStatusVisible(event.newValue));

    let mandatoryField = this.widget('MandatoryField');
    mandatoryField.setValue(this.field.mandatory);
    mandatoryField.on('propertyChange:value', event => this.field.setMandatory(event.newValue));

    let loadingField = this.widget('LoadingField');
    loadingField.setValue(this.field.loading);
    loadingField.on('propertyChange:value', event => this.field.setLoading(event.newValue));

    let labelHtmlEnabledField = this.widget('LabelHtmlEnabledField');
    labelHtmlEnabledField.setValue(this.field.labelHtmlEnabled);
    labelHtmlEnabledField.on('propertyChange:value', event => this.field.setLabelHtmlEnabled(event.newValue));

    let inheritAccessibilityField = this.widget('InheritAccessibilityField');
    inheritAccessibilityField.setValue(this.field.inheritAccessibility);
    inheritAccessibilityField.on('propertyChange:value', event => this.field.setInheritAccessibility(event.newValue));

    let fieldStyleField = this.widget('FieldStyleField');
    fieldStyleField.setValue(this.field.fieldStyle);
    fieldStyleField.on('propertyChange:value', event => this.field.setFieldStyle(event.newValue));

    let disabledStyleField = this.widget('DisabledStyleField');
    disabledStyleField.setValue(this.field.disabledStyle);
    disabledStyleField.on('propertyChange:value', event => this.field.setDisabledStyle(event.newValue));

    let dropTypeField = this.widget('DropTypeField');
    dropTypeField.setValue(this.field.dropType);
    dropTypeField.on('propertyChange:value', event => this.field.setDropType(event.newValue));

    let dropMaximumSizeField = this.widget('DropMaximumSizeField');
    dropMaximumSizeField.setValue(this.field.dropMaximumSize);
    dropMaximumSizeField.on('propertyChange:value', event => this.field.setDropMaximumSize(event.newValue));

    let labelField = this.widget('LabelField');
    labelField.setValue(this.field.label);
    labelField.on('propertyChange:value', event => this.field.setLabel(event.newValue));

    let labelPositionField = this.widget('LabelPositionField');
    labelPositionField.setValue(this.field.labelPosition);
    labelPositionField.on('propertyChange:value', event => this.field.setLabelPosition(event.newValue));

    let labelWidthInPixelField = this.widget('LabelWidthInPixelField');
    labelWidthInPixelField.setValue('' + this.field.labelWidthInPixel);
    labelWidthInPixelField.on('propertyChange:value', event => {
      if (event.source.lookupRow) {
        this.field.setLabelWidthInPixel(numbers.ensure(event.source.lookupRow.key));
      } else {
        this.field.setLabelWidthInPixel(numbers.ensure(event.newValue));
      }
    });

    let errorStatusField = this.widget('ErrorStatusField');
    errorStatusField.setValue(this.field.errorStatus ? this.field.errorStatus.severity : null);
    errorStatusField.on('propertyChange:value', event => {
      this.field.removeErrorStatus(DefaultStatus);
      if (event.newValue) {
        this.field.setErrorStatus(new DefaultStatus({
          severity: event.newValue,
          message: this.session.text('FormFieldStatusMessage', objects.keyByValue(Status.Severity, event.newValue))
        }));
      }
    });

    let tooltipTextField = this.widget('TooltipTextField');
    tooltipTextField.setValue(this.field.tooltipText);
    tooltipTextField.on('propertyChange:value', event => this.field.setTooltipText(event.newValue));

    let tooltipAnchorField = this.widget('TooltipAnchorField');
    tooltipAnchorField.setValue(this.field.tooltipAnchor);
    tooltipAnchorField.on('propertyChange:value', event => this.field.setTooltipAnchor(event.newValue));

    let statusPositionField = this.widget('StatusPositionField');
    statusPositionField.setValue(this.field.statusPosition);
    statusPositionField.on('propertyChange:value', event => this.field.setStatusPosition(event.newValue));
  }

  protected _onFileDrop(event: FileDropEvent) {
    scout.create(DesktopNotification, {
      parent: this,
      duration: 7500,
      status: {
        severity: Status.Severity.OK,
        message: this.session.text('FilesDroppedSuccessfully') + '\n' + this._filesToString(event.files)
      }
    }).show();
  }

  protected _filesToString(files: File[]): string {
    return arrays.format(files.map(file => {
      return `
Name: ${file.name}
Size: ${file.size}
Type: ${file.type}`;
    }), '\n');
  }
}
