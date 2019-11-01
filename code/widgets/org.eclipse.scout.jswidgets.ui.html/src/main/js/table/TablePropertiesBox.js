/*
 * Copyright (c) 2017 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
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

    var autoResizeColumnsField = this.widget('AutoResizeColumnsField');
    autoResizeColumnsField.setValue(this.table.autoResizeColumns);
    autoResizeColumnsField.on('propertyChange', this._onAutoResizeColumnsPropertyChange.bind(this));

    var autoOptimizeColumnWidthsField = this.widget('AutoOptimizeColumnWidthsField');
    autoOptimizeColumnWidthsField.setValue(false);
    autoOptimizeColumnWidthsField.on('propertyChange', this._onAutoOptimizeColumnWidthsPropertyChange.bind(this));

    var checkableField = this.widget('CheckableField');
    checkableField.setValue(this.table.checkable);
    checkableField.on('propertyChange', this._onCheckablePropertyChange.bind(this));

    var headerEnabledField = this.widget('HeaderEnabledField');
    headerEnabledField.setValue(this.table.headerEnabled);
    headerEnabledField.on('propertyChange', this._onHeaderEnabledPropertyChange.bind(this));

    var headerVisibleField = this.widget('HeaderVisibleField');
    headerVisibleField.setValue(this.table.headerVisible);
    headerVisibleField.on('propertyChange', this._onHeaderVisiblePropertyChange.bind(this));

    var headerMenusEnabledField = this.widget('HeaderMenusEnabledField');
    headerMenusEnabledField.setValue(this.table.headerMenusEnabled);
    headerMenusEnabledField.on('propertyChange', this._onHeaderMenusEnabledPropertyChange.bind(this));

    var menuBarVisibleField = this.widget('MenuBarVisibleField');
    menuBarVisibleField.setValue(this.table.menuBarVisible);
    menuBarVisibleField.on('propertyChange', this._onMenuBarVisiblePropertyChange.bind(this));

    var multiCheckField = this.widget('MultiCheckField');
    multiCheckField.setValue(this.table.multiCheck);
    multiCheckField.on('propertyChange', this._onMultiCheckPropertyChange.bind(this));

    var multiSelectField = this.widget('MultiSelectField');
    multiSelectField.setValue(this.table.multiSelect);
    multiSelectField.on('propertyChange', this._onMultiSelectPropertyChange.bind(this));

    var multilineTextField = this.widget('MultilineTextField');
    multilineTextField.setValue(this.table.multilineText);
    multilineTextField.on('propertyChange', this._onMultilineTextPropertyChange.bind(this));

    var truncatedCellTooltipEnabledField = this.widget('TruncatedCellTooltipEnabledField');
    truncatedCellTooltipEnabledField.setValue(this.table.truncatedCellTooltipEnabled);
    truncatedCellTooltipEnabledField.on('propertyChange', this._onTruncatedCellTooltipEnabledPropertyChange.bind(this));

    var scrollToSelectionField = this.widget('ScrollToSelectionField');
    scrollToSelectionField.setValue(this.table.scrollToSelection);
    scrollToSelectionField.on('propertyChange', this._onScrollToSelectionPropertyChange.bind(this));

    var sortEnabledField = this.widget('SortEnabledField');
    sortEnabledField.setValue(this.table.sortEnabled);
    sortEnabledField.on('propertyChange', this._onSortEnabledPropertyChange.bind(this));

    var footerVisibleField = this.widget('FooterVisibleField');
    footerVisibleField.setValue(this.table.footerVisible);
    footerVisibleField.on('propertyChange', this._onFooterVisiblePropertyChange.bind(this));

    var rowIconVisibleField = this.widget('RowIconVisibleField');
    rowIconVisibleField.setValue(this.table.rowIconVisible);
    rowIconVisibleField.on('propertyChange', this._onRowIconVisiblePropertyChange.bind(this));

    var rowIconColumnWidthField = this.widget('RowIconColumnWidthField');
    rowIconColumnWidthField.setValue(this.table.rowIconColumnWidth);
    rowIconColumnWidthField.on('propertyChange', this._onRowIconColumnWidthPropertyChange.bind(this));

    var checkableStyleField = this.widget('CheckableStyleField');
    checkableStyleField.setValue(this.table.checkableStyle);
    checkableStyleField.on('propertyChange', this._onCheckableStylePropertyChange.bind(this));

    var groupingStyleField = this.widget('GroupingStyleField');
    groupingStyleField.setValue(this.table.groupingStyle);
    groupingStyleField.on('propertyChange', this._onGroupingStylePropertyChange.bind(this));

    var tileModeField = this.widget('TileModeField');
    tileModeField.setValue(this.table.tileMode);
    tileModeField.on('propertyChange', this._onTileModePropertyChange.bind(this));
  }

  _onAutoResizeColumnsPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setAutoResizeColumns(event.newValue);
    }
  }

  _onAutoOptimizeColumnWidthsPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.columns.forEach(function(column) {
        column.setAutoOptimizeWidth(event.newValue);
      });
    }
  }

  _onCheckablePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setCheckable(event.newValue);
    }
  }

  _onHeaderEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setHeaderEnabled(event.newValue);
    }
  }

  _onHeaderVisiblePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setHeaderVisible(event.newValue);
    }
  }

  _onHeaderMenusEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setHeaderMenusEnabled(event.newValue);
    }
  }

  _onMenuBarVisiblePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setMenuBarVisible(event.newValue);
    }
  }

  _onMultiCheckPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setMultiCheck(event.newValue);
    }
  }

  _onMultiSelectPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setMultiSelect(event.newValue);
    }
  }

  _onMultilineTextPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setMultilineText(event.newValue);
    }
  }

  _onTruncatedCellTooltipEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setTruncatedCellTooltipEnabled(event.newValue);
    }
  }

  _onScrollToSelectionPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setScrollToSelection(event.newValue);
    }
  }

  _onSortEnabledPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setSortEnabled(event.newValue);
    }
  }

  _onFooterVisiblePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setFooterVisible(event.newValue);
    }
  }

  _onRowIconVisiblePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setRowIconVisible(event.newValue);
    }
  }

  _onRowIconColumnWidthPropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setRowIconColumnWidth(event.newValue);
    }
  }

  _onCheckableStylePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setCheckableStyle(event.newValue);
    }
  }

  _onGroupingStylePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setGroupingStyle(event.newValue);
    }
  }

  _onTileModePropertyChange(event) {
    if (event.propertyName === 'value') {
      this.table.setTileMode(event.newValue);
    }
  }
}
