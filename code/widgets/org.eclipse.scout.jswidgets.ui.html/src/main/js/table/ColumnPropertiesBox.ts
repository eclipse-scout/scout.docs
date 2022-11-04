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
import {Alignment, Column, GroupBox, GroupBoxModel, InitModelOf, models, TableColumnResizedEvent, TableGroupEvent, TableSortEvent} from '@eclipse-scout/core';
import ColumnPropertiesBoxModel from './ColumnPropertiesBoxModel';
import {ColumnPropertiesBoxWidgetMap} from '../index';

export class ColumnPropertiesBox extends GroupBox {
  declare widgetMap: ColumnPropertiesBoxWidgetMap;

  column: Column;

  constructor() {
    super();
    this.column = null;
  }

  protected override _jsonModel(): GroupBoxModel {
    return models.get(ColumnPropertiesBoxModel);
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this._setColumn(this.column);
  }

  setColumn(column: Column) {
    this.setProperty('column', column);
  }

  protected _setColumn(column: Column) {
    this._setProperty('column', column);
    if (!this.column) {
      return;
    }
    let autoOptimizeWidthField = this.widget('AutoOptimizeWidthField');
    autoOptimizeWidthField.setValue(this.column.autoOptimizeWidth);
    autoOptimizeWidthField.on('propertyChange:value', event => this.column.setAutoOptimizeWidth(event.newValue));

    let autoOptimizeMaxWidthField = this.widget('AutoOptimizeMaxWidthField');
    autoOptimizeMaxWidthField.setValue(this.column.autoOptimizeMaxWidth);

    let editableField = this.widget('EditableField');
    editableField.setValue(this.column.editable);
    editableField.on('propertyChange:value', event => this.column.setEditable(event.newValue));

    let modifiableField = this.widget('ModifiableField');
    modifiableField.setValue(this.column.modifiable);

    let removableField = this.widget('RemovableField');
    removableField.setValue(this.column.removable);

    let fixedWidthField = this.widget('FixedWidthField');
    fixedWidthField.setValue(this.column.fixedWidth);
    fixedWidthField.on('propertyChange:value', event => {
      this.column.fixedWidth = event.newValue;
      this.column.table.invalidateLayoutTree();
    });

    let fixedPositionField = this.widget('FixedPositionField');
    fixedPositionField.setValue(this.column.fixedPosition);
    fixedPositionField.on('propertyChange:value', event => {
      this.column.fixedPosition = event.newValue;
    });

    let groupedField = this.widget('GroupedField');
    groupedField.setValue(this.column.grouped);

    let headerMenuEnabledField = this.widget('HeaderMenuEnabledField');
    headerMenuEnabledField.setValue(this.column.headerMenuEnabled);
    headerMenuEnabledField.on('propertyChange:value', event => {
      this.column.headerMenuEnabled = event.newValue;
    });

    let headerHtmlEnabledField = this.widget('HeaderHtmlEnabledField');
    headerHtmlEnabledField.setValue(this.column.headerHtmlEnabled);
    headerHtmlEnabledField.on('propertyChange:value', event => this.column.setHeaderHtmlEnabled(event.newValue));

    let htmlEnabledField = this.widget('HtmlEnabledField');
    htmlEnabledField.setValue(this.column.htmlEnabled);

    let headerTooltipHtmlEnabledField = this.widget('HeaderTooltipHtmlEnabledField');
    headerTooltipHtmlEnabledField.setValue(this.column.headerTooltipHtmlEnabled);
    headerTooltipHtmlEnabledField.on('propertyChange:value', event => this.column.setHeaderTooltipHtmlEnabled(event.newValue));

    let mandatoryField = this.widget('MandatoryField');
    mandatoryField.setValue(this.column.mandatory);
    mandatoryField.on('propertyChange:value', event => this.column.setMandatory(event.newValue));

    let sortActiveField = this.widget('SortActiveField');
    sortActiveField.setValue(this.column.sortActive);

    let sortAscendingField = this.widget('SortAscendingField');
    sortAscendingField.setValue(this.column.sortAscending);

    let textWrapField = this.widget('TextWrapField');
    textWrapField.setValue(this.column.textWrap);
    textWrapField.on('propertyChange:value', event => this.column.setTextWrap(event.newValue));

    let displayableField = this.widget('DisplayableField');
    displayableField.setValue(this.column.displayable);
    displayableField.on('propertyChange:value', event => this.column.setDisplayable(event.newValue));

    let visibleField = this.widget('VisibleField');
    visibleField.setValue(this.column.visible);
    visibleField.on('propertyChange:value', event => this.column.setVisible(event.newValue));

    let cssClassField = this.widget('CssClassField');
    cssClassField.setValue(this.column.cssClass);
    cssClassField.on('propertyChange:value', event => this.column.setCssClass(event.newValue));

    let horizontalAlignmentField = this.widget('HorizontalAlignmentField');
    horizontalAlignmentField.setValue(this.column.horizontalAlignment);
    horizontalAlignmentField.on('propertyChange:value', event => {
      let hAlign = event.newValue;
      if (hAlign < 0) {
        hAlign = -1;
      } else if (hAlign > 0) {
        hAlign = 1;
      }
      this.column.setHorizontalAlignment(hAlign as Alignment);
    });

    let textField = this.widget('TextField');
    textField.setValue(this.column.text);
    textField.on('propertyChange:value', event => this.column.setText(event.newValue));

    let headerTooltipTextField = this.widget('HeaderTooltipTextField');
    headerTooltipTextField.setValue(this.column.headerTooltipText);
    headerTooltipTextField.on('propertyChange:value', event => this.column.setHeaderTooltipText(event.newValue));

    let headerIconIdField = this.widget('HeaderIconIdField');
    headerIconIdField.setValue(this.column.headerIconId);
    headerIconIdField.on('propertyChange:value', event => this.column.setHeaderIconId(event.newValue));

    let headerCssClassField = this.widget('HeaderCssClassField');
    headerCssClassField.setValue(this.column.headerCssClass);
    headerCssClassField.on('propertyChange:value', event => this.column.setHeaderCssClass(event.newValue));

    let sortIndexField = this.widget('SortIndexField');
    sortIndexField.setValue(this.column.sortIndex);

    let widthField = this.widget('WidthField');
    widthField.setValue(this.column.width);
    widthField.on('propertyChange:value', event => this.column.setWidth(event.newValue));

    let minWidthField = this.widget('MinWidthField');
    minWidthField.setValue(this.column.minWidth);

    let maxLengthField = this.widget('MaxLengthField');
    maxLengthField.setValue(this.column.maxLength);
    maxLengthField.on('propertyChange:value', event => this.column.setMaxLength(event.newValue));

    column.table.on('sort', this._onSort.bind(this));
    column.table.on('group', this._onGroup.bind(this));
    column.table.on('columnResized', this._onColumnResized.bind(this));
  }

  protected _onColumnResized(event: TableColumnResizedEvent) {
    if (this.column === event.column) {
      let widthField = this.widget('WidthField');
      widthField.setValue(this.column.width);
    }
  }

  protected _onSort(event: TableSortEvent) {
    if (this.column === event.column) {
      let sortActiveField = this.widget('SortActiveField');
      sortActiveField.setValue(this.column.sortActive);
      let sortAscendingField = this.widget('SortAscendingField');
      sortAscendingField.setValue(this.column.sortAscending);
      let sortIndexField = this.widget('SortIndexField');
      sortIndexField.setValue(this.column.sortIndex);
    }
  }

  protected _onGroup(event: TableGroupEvent) {
    if (this.column === event.column) {
      let groupedField = this.widget('GroupedField');
      groupedField.setValue(this.column.grouped);
    }
  }
}
