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
jswidgets.ColumnPropertiesBox = function() {
  jswidgets.ColumnPropertiesBox.parent.call(this);
  this.column = null;
};
scout.inherits(jswidgets.ColumnPropertiesBox, scout.GroupBox);

jswidgets.ColumnPropertiesBox.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.ColumnPropertiesBox');
};

jswidgets.ColumnPropertiesBox.prototype._init = function(model) {
  jswidgets.ColumnPropertiesBox.parent.prototype._init.call(this, model);

  this._setColumn(this.column);
};

jswidgets.ColumnPropertiesBox.prototype.setColumn = function(column) {
  this.setProperty('column', column);
};

jswidgets.ColumnPropertiesBox.prototype._setColumn = function(column) {
  this._setProperty('column', column);
  if (!this.column) {
    return;
  }
  var autoOptimizeWidthField = this.widget('AutoOptimizeWidthField');
  autoOptimizeWidthField.setValue(this.column.autoOptimizeWidth);
  autoOptimizeWidthField.on('propertyChange', this._onPropertyChange.bind(this));

  var autoOptimizeMaxWidthField = this.widget('AutoOptimizeMaxWidthField');
  autoOptimizeMaxWidthField.setValue(this.column.autoOptimizeMaxWidth);
  autoOptimizeMaxWidthField.on('propertyChange', this._onPropertyChange.bind(this));

  var editableField = this.widget('EditableField');
  editableField.setValue(this.column.editable);
  editableField.on('propertyChange', this._onPropertyChange.bind(this));

  var modifiableField = this.widget('ModifiableField');
  modifiableField.setValue(this.column.modifiable);
  modifiableField.on('propertyChange', this._onPropertyChange.bind(this));

  var removableField = this.widget('RemovableField');
  removableField.setValue(this.column.removable);
  removableField.on('propertyChange', this._onPropertyChange.bind(this));

  var fixedWidthField = this.widget('FixedWidthField');
  fixedWidthField.setValue(this.column.fixedWidth);
  fixedWidthField.on('propertyChange', this._onPropertyChange.bind(this));

  var fixedPositionField = this.widget('FixedPositionField');
  fixedPositionField.setValue(this.column.fixedPosition);
  fixedPositionField.on('propertyChange', this._onPropertyChange.bind(this));

  var headerHtmlEnabledField = this.widget('HeaderHtmlEnabledField');
  headerHtmlEnabledField.setValue(this.column.headerHtmlEnabled);
  headerHtmlEnabledField.on('propertyChange', this._onPropertyChange.bind(this));

  var htmlEnabledField = this.widget('HtmlEnabledField');
  htmlEnabledField.setValue(this.column.htmlEnabled);
  htmlEnabledField.on('propertyChange', this._onPropertyChange.bind(this));

  var mandatoryField = this.widget('MandatoryField');
  mandatoryField.setValue(this.column.mandatory);
  mandatoryField.on('propertyChange', this._onPropertyChange.bind(this));

  var sortActiveField = this.widget('SortActiveField');
  sortActiveField.setValue(this.column.sortActive);
  sortActiveField.on('propertyChange', this._onPropertyChange.bind(this));

  var sortAscendingField = this.widget('SortAscendingField');
  sortAscendingField.setValue(this.column.sortAscending);
  sortAscendingField.on('propertyChange', this._onPropertyChange.bind(this));

  var textWrapField = this.widget('TextWrapField');
  textWrapField.setValue(this.column.textWrap);
  textWrapField.on('propertyChange', this._onPropertyChange.bind(this));

  var displayableField = this.widget('DisplayableField');
  displayableField.setValue(this.column.displayable);
  displayableField.on('propertyChange', this._onPropertyChange.bind(this));

  var visibleField = this.widget('VisibleField');
  visibleField.setValue(this.column.visible);
  visibleField.on('propertyChange', this._onPropertyChange.bind(this));

  var cssClassField = this.widget('CssClassField');
  cssClassField.setValue(this.column.cssClass);
  cssClassField.on('propertyChange', this._onPropertyChange.bind(this));

  var horizontalAlignmentField = this.widget('HorizontalAlignmentField');
  horizontalAlignmentField.setValue(this.column.horizontalAlignment);
  horizontalAlignmentField.on('propertyChange', this._onPropertyChange.bind(this));

  var textField = this.widget('TextField');
  textField.setValue(this.column.text);
  textField.on('propertyChange', this._onPropertyChange.bind(this));

  var sortIndexField = this.widget('SortIndexField');
  sortIndexField.setValue(this.column.sortIndex);
  sortIndexField.on('propertyChange', this._onPropertyChange.bind(this));

  var widthField = this.widget('WidthField');
  widthField.setValue(this.column.width);
  widthField.on('propertyChange', this._onPropertyChange.bind(this));

  var minWidthField = this.widget('MinWidthField');
  minWidthField.setValue(this.column.minWidth);
  minWidthField.on('propertyChange', this._onPropertyChange.bind(this));
};

jswidgets.ColumnPropertiesBox.prototype._onPropertyChange = function(event) {
  if (event.propertyName === 'value' && event.source.id === 'AutoOptimizeWidthField') {
    this.column.setAutoOptimizeWidth(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'VisibleField') {
    this.column.setVisible(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'DisplayableField') {
    this.column.setDisplayable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'CssClassField') {
    this.column.setCssClass(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'EditableField') {
    this.column.setEditable(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'MandatoryField') {
    this.column.setMandatory(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'WidthField') {
    this.column.setWidth(event.newValue);
  } else if (event.propertyName === 'value' && event.source.id === 'HorizontalAlignmentField') {
    var hAlign = event.newValue;
    if (hAlign < 0) {
      hAlign = -1;
    } else if (hAlign > 0) {
      hAlign = 1;
    }
    this.column.setHorizontalAlignment(hAlign);
  }
};
