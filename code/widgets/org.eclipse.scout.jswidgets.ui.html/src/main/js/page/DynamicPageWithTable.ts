/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Action, arrays, Event, Form, icons, Menu, ObjectOrModel, Page, PageModel, PageWithTable, scout, Table, TableRow, Widget} from '@eclipse-scout/core';
import {DynamicPageWithNodes, DynamicPageWithTableAddRowForm, DynamicPageWithTableTable, PageConfigForm, PageTypeType, RandomEmojiForm} from '../index';
import $ from 'jquery';
import model from './DynamicPageWithTableModel';

export class DynamicPageWithTable extends PageWithTable {
  declare detailTable: DynamicPageWithTableTable;

  constructor() {
    super();
    this.detailFormVisible = false; // hidden by default
  }

  protected override _jsonModel(): PageModel {
    return model();
  }

  protected override _createDetailForm(): Form {
    return scout.create(RandomEmojiForm, {
      parent: this.getOutline()
    });
  }

  protected override _initDetailForm(form: Form) {
    super._initDetailForm(form);
    if (form) {
      form.rootGroupBox.setMenus(this._createMenus(form.rootGroupBox));
    }
  }

  protected override _initDetailTable(table: DynamicPageWithTableTable) {
    super._initDetailTable(table);
    if (table) {
      table.setMenus(this._createMenus(table));
    }
  }

  protected _createMenus(parent: Widget): Menu[] {
    let menus = [];

    let editPageMenu = scout.create(Menu, {
      parent: parent,
      text: 'Edit page',
      iconId: icons.PENCIL,
      keyStroke: 'ctrl-e'
    });
    editPageMenu.on('action', this._onEditPageMenuAction.bind(this));
    menus.push(editPageMenu);

    if (this.parentNode instanceof DynamicPageWithNodes) {
      let deletePageMenu = scout.create(Menu, {
        parent: parent,
        text: 'Delete page',
        iconId: icons.REMOVE
      });
      deletePageMenu.on('action', this._onDeletePageMenuAction.bind(this));
      menus.push(deletePageMenu);
    }

    let addRowMenu = scout.create(Menu, {
      parent: parent,
      text: 'Add row',
      iconId: icons.PLUS,
      keyStroke: 'insert'
    });
    addRowMenu.on('action', this._onAddRowMenuAction.bind(this));
    menus.push(addRowMenu);

    if (parent instanceof Table) {
      let editRowMenu = scout.create(Menu, {
        parent: parent,
        text: 'Edit row',
        iconId: icons.LIST,
        keyStroke: 'alt-enter',
        menuTypes: [Table.MenuType.SingleSelection]
      });
      editRowMenu.on('action', this._onEditRowMenuAction.bind(this));
      menus.push(editRowMenu);

      let deleteRowMenu = scout.create(Menu, {
        parent: parent,
        text: 'Delete row',
        iconId: icons.MINUS,
        keyStroke: 'delete',
        menuTypes: [Table.MenuType.SingleSelection]
      });
      deleteRowMenu.on('action', this._onDeleteRowMenuAction.bind(this));
      menus.push(deleteRowMenu);
      let deleteRowsMenu = scout.create(Menu, {
        parent: parent,
        text: 'Delete rows',
        iconId: icons.MINUS,
        keyStroke: 'delete',
        menuTypes: [Table.MenuType.MultiSelection]
      });
      deleteRowsMenu.on('action', this._onDeleteRowMenuAction.bind(this));
      menus.push(deleteRowsMenu);
    }

    return menus;
  }

  protected _onEditPageMenuAction(event: Event<Action>) {
    let outline = this.getOutline();

    let form = scout.create(PageConfigForm, {
      parent: outline,
      page: this
    });
    form.open();
  }

  protected _onDeletePageMenuAction(event: Event<Action>) {
    let outline = this.getOutline();

    outline.selectNode(this.parentNode);
    outline.deleteNode(this, this.parentNode);
  }

  protected _onAddRowMenuAction(event: Event<Action>) {
    let form = scout.create(DynamicPageWithTableAddRowForm, {
      parent: this.getOutline()
    });
    let alphabet = [
      'Alpha', 'Bravo', 'Charlie', 'Delta', 'Echo', 'Foxtrot', 'Golf', 'Hotel', 'India',
      'Juliett', 'Kilo', 'Lima', 'Mike', 'November', 'Oscar', 'Papa', 'Quebec', 'Romeo',
      'Sierra', 'Tango', 'Uniform', 'Victor', 'Whiskey', 'X-ray', 'Yankee', 'Zulu'
    ];
    if (this.detailTable.rows.length < alphabet.length) {
      let used = new Set(this.detailTable.rows.map(row => this.detailTable.columnById('NameColumn').cellValue(row)));
      let lastedUsedIndex = arrays.findIndexFromReverse(alphabet, alphabet.length - 1, candidate => used.has(candidate));
      if (lastedUsedIndex >= 0 && lastedUsedIndex < alphabet.length - 1) {
        form.widget('NameField').setValue(alphabet[lastedUsedIndex + 1]);
      }
    }
    form.open();
    form.whenSave().then(() => {
      this.detailTable.insertRow({
        cells: [
          form.widget('NameField').value,
          form.widget('PageTypeField').value
        ]
      });
    });
  }

  protected _onEditRowMenuAction(event: Event<Action>) {
    let form = scout.create(DynamicPageWithTableAddRowForm, {
      parent: this.getOutline(),
      title: 'Edit row'
    });
    let row = this.detailTable.selectedRow();
    form.widget('NameField').setValue(this.detailTable.columnById('NameColumn').cellValue(row));
    form.widget('PageTypeField').setValue(this.detailTable.columnById('PageTypeColumn').cellValue(row));
    form.widget('PageTypeField').setEnabled(false);
    form.open();
    form.whenSave().then(() => {
      this.detailTable.columnById('NameColumn').setCellValue(row, form.widget('NameField').value);
    });
  }

  protected _onDeleteRowMenuAction(event: Event<Action>) {
    // Ensure this node is selected, because the menu might be triggered from the child page (inheritMenusFromParentTablePage) .
    this.getOutline().selectNode(this);

    this.detailTable.deleteRows(this.detailTable.selectedRows);
  }

  protected override _loadTableData(searchFilter: any): JQuery.Promise<any> {
    let data: { name: string; type: PageTypeType }[] = [{
      name: 'Alpha',
      type: 'PageWithNodes'
    }, {
      name: 'Bravo',
      type: 'PageWithNodes'
    }, {
      name: 'Charlie',
      type: 'PageWithTable'
    }, {
      name: 'Delta',
      type: 'None'
    }];
    return $.resolvedPromise(data);
  }

  protected override _transformTableDataToTableRows(tableData: any): ObjectOrModel<TableRow>[] {
    return tableData.map(tableDataRow => {
      return {
        cells: [
          tableDataRow.name,
          tableDataRow.type
        ]
      };
    });
  }

  override createChildPage(row: TableRow): Page {
    let pageType = this.detailTable.columnById('PageTypeColumn').cellValue(row);
    if (pageType === 'PageWithNodes') {
      return scout.create(DynamicPageWithNodes, {
        parent: this.getOutline(),
        detailFormVisible: true
      });
    }
    if (pageType === 'PageWithTable') {
      return scout.create(DynamicPageWithTable, {
        parent: this.getOutline(),
        inheritMenusFromParentTablePage: true
      });
    }
    return null;
  }
}
