/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Action, Event, Form, icons, Menu, numbers, PageModel, PageWithNodes, scout, Table, Widget} from '@eclipse-scout/core';
import {PageConfigForm, PageConfigFormData, RandomEmojiForm} from '../index';
import model from './DynamicPageWithNodesModel';

export class DynamicPageWithNodes extends PageWithNodes {

  constructor() {
    super();
    this.detailFormVisible = false; // hidden by default
  }

  protected override _jsonModel(): PageModel {
    return model();
  }

  protected override _createDetailForm(): Form {
    let form = scout.create(RandomEmojiForm, {
      parent: this.getOutline()
    });
    if (form) {
      form.rootGroupBox.menus.push(...this._createMenus(form.rootGroupBox));
    }
    return form;
  }

  protected override _createDetailTable(): Table {
    let table = super._createDetailTable();
    if (table) {
      table.menus.push(...this._createMenus(table));
    }
    return table;
  }

  protected _createMenus(parent: Widget): Menu[] {
    let menus = [];

    let editPageMenu = scout.create(Menu, {
      parent: parent,
      text: 'Edit page',
      iconId: icons.PENCIL
    });
    editPageMenu.on('action', this._onEditMenuAction.bind(this));
    menus.push(editPageMenu);

    let addChildPageMenu = scout.create(Menu, {
      parent: parent,
      text: 'Add child page',
      iconId: icons.PLUS
    });
    addChildPageMenu.on('action', this._onAddChildPageMenuAction.bind(this));
    menus.push(addChildPageMenu);

    if (this.parentNode instanceof DynamicPageWithNodes) {
      let deletePageMenu = scout.create(Menu, {
        parent: parent,
        text: 'Delete page',
        iconId: icons.REMOVE
      });
      deletePageMenu.on('action', this._onDeleteMenuAction.bind(this));
      menus.push(deletePageMenu);
    }

    return menus;
  }

  protected _onEditMenuAction(event: Event<Action>) {
    let outline = this.getOutline();

    let form = scout.create(PageConfigForm, {
      parent: outline,
      page: this
    });
    form.open();
  }

  protected _onAddChildPageMenuAction(event: Event<Action>) {
    let outline = this.getOutline();

    let formData: PageConfigFormData = {
      name: numbers.randomId(),
      detailFormVisible: true,
      detailTableVisible: true,
      leaf: false,
      drillDownOnRowClick: false,
      lazyExpandingEnabled: false
    };
    let form = scout.create(PageConfigForm, {
      parent: outline,
      data: formData
    });
    form.widget('OkMenu').on('action', event => {
      form.touch();
    });

    form.open();
    form.whenSave().then(() => {
      let newNode = scout.create(DynamicPageWithNodes, {
        parent: outline,
        routeRef: null,
        text: form.data.name,
        detailFormVisible: form.data.detailFormVisible,
        detailTableVisible: form.data.detailTableVisible,
        leaf: form.data.leaf,
        drillDownOnRowClick: form.data.drillDownOnRowClick,
        lazyExpandingEnabled: form.data.lazyExpandingEnabled
      });
      outline.insertNode(newNode, this);
      outline.setNodeExpanded(this, true);
    });
  }

  protected _onDeleteMenuAction(event: Event<Action>) {
    let outline = this.getOutline();

    outline.selectNode(this.parentNode);
    outline.deleteNode(this, this.parentNode);
  }
}
