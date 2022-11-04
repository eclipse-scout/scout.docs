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
import TablePropertiesBoxModel from './TablePropertiesBoxModel';

export default class TablePropertiesBox extends GroupBox {

  constructor() {
    super();
    this.table = null;
  }

  _jsonModel() {
    return models.get(TablePropertiesBoxModel);
  }

  _init(model) {
    super._init(model);

    this._setTable(this.table);
  }

  setTable(table) {
    this.setProperty('table', table);
  }

  _setTable(table) {
    this._setProperty('table', table);
    if (!this.table) {
      return;
    }

    let autoResizeColumnsField = this.widget('AutoResizeColumnsField');
    autoResizeColumnsField.setValue(this.table.autoResizeColumns);
    autoResizeColumnsField.on('propertyChange:value', event => this.table.setAutoResizeColumns(event.newValue));

    let autoOptimizeColumnWidthsField = this.widget('AutoOptimizeColumnWidthsField');
    autoOptimizeColumnWidthsField.setValue(false);
    autoOptimizeColumnWidthsField.on('propertyChange:value', event => this.table.columns.forEach(column => {
      column.setAutoOptimizeWidth(event.newValue);
    }));

    let checkableField = this.widget('CheckableField');
    checkableField.setValue(this.table.checkable);
    checkableField.on('propertyChange:value', event => this.table.setCheckable(event.newValue));

    let compactField = this.widget('CompactField');
    compactField.setValue(this.table.compact);
    compactField.on('propertyChange:value', event => this.table.setCompact(event.newValue));

    let headerEnabledField = this.widget('HeaderEnabledField');
    headerEnabledField.setValue(this.table.headerEnabled);
    headerEnabledField.on('propertyChange:value', event => this.table.setHeaderEnabled(event.newValue));

    let headerVisibleField = this.widget('HeaderVisibleField');
    headerVisibleField.setValue(this.table.headerVisible);
    headerVisibleField.on('propertyChange:value', event => this.table.setHeaderVisible(event.newValue));

    let headerMenusEnabledField = this.widget('HeaderMenusEnabledField');
    headerMenusEnabledField.setValue(this.table.headerMenusEnabled);
    headerMenusEnabledField.on('propertyChange:value', event => this.table.setHeaderMenusEnabled(event.newValue));

    let menuBarVisibleField = this.widget('MenuBarVisibleField');
    menuBarVisibleField.setValue(this.table.menuBarVisible);
    menuBarVisibleField.on('propertyChange:value', event => this.table.setMenuBarVisible(event.newValue));

    let multiCheckField = this.widget('MultiCheckField');
    multiCheckField.setValue(this.table.multiCheck);
    multiCheckField.on('propertyChange:value', event => this.table.setMultiCheck(event.newValue));

    let multiSelectField = this.widget('MultiSelectField');
    multiSelectField.setValue(this.table.multiSelect);
    multiSelectField.on('propertyChange:value', event => this.table.setMultiSelect(event.newValue));

    let multilineTextField = this.widget('MultilineTextField');
    multilineTextField.setValue(this.table.multilineText);
    multilineTextField.on('propertyChange:value', event => this.table.setMultilineText(event.newValue));

    let truncatedCellTooltipEnabledField = this.widget('TruncatedCellTooltipEnabledField');
    truncatedCellTooltipEnabledField.setValue(this.table.truncatedCellTooltipEnabled);
    truncatedCellTooltipEnabledField.on('propertyChange:value', event => this.table.setTruncatedCellTooltipEnabled(event.newValue));

    let scrollToSelectionField = this.widget('ScrollToSelectionField');
    scrollToSelectionField.setValue(this.table.scrollToSelection);
    scrollToSelectionField.on('propertyChange:value', event => this.table.setScrollToSelection(event.newValue));

    let sortEnabledField = this.widget('SortEnabledField');
    sortEnabledField.setValue(this.table.sortEnabled);
    sortEnabledField.on('propertyChange:value', event => this.table.setSortEnabled(event.newValue));

    let footerVisibleField = this.widget('FooterVisibleField');
    footerVisibleField.setValue(this.table.footerVisible);
    footerVisibleField.on('propertyChange:value', event => this.table.setFooterVisible(event.newValue));

    let rowIconVisibleField = this.widget('RowIconVisibleField');
    rowIconVisibleField.setValue(this.table.rowIconVisible);
    rowIconVisibleField.on('propertyChange:value', event => this.table.setRowIconVisible(event.newValue));

    let rowIconColumnWidthField = this.widget('RowIconColumnWidthField');
    rowIconColumnWidthField.setValue(this.table.rowIconColumnWidth);
    rowIconColumnWidthField.on('propertyChange:value', event => this.table.setRowIconColumnWidth(event.newValue));

    let checkableStyleField = this.widget('CheckableStyleField');
    checkableStyleField.setValue(this.table.checkableStyle);
    checkableStyleField.on('propertyChange:value', event => this.table.setCheckableStyle(event.newValue));

    let groupingStyleField = this.widget('GroupingStyleField');
    groupingStyleField.setValue(this.table.groupingStyle);
    groupingStyleField.on('propertyChange:value', event => this.table.setGroupingStyle(event.newValue));

    let tileModeField = this.widget('TileModeField');
    tileModeField.setValue(this.table.tileMode);
    tileModeField.on('propertyChange:value', event => this.table.setTileMode(event.newValue));

    let textFilterEnabledField = this.widget('TextFilterEnabledField');
    textFilterEnabledField.setValue(this.table.textFilterEnabled);
    textFilterEnabledField.on('propertyChange:value', event => this.table.setTextFilterEnabled(event.newValue));
  }
}
