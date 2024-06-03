/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {
  Action, BookmarkForm, BookmarkStore, BookmarkSupport, Desktop as ScoutDesktop, DesktopModel as DesktopModel, DesktopNotification, Event, Form, GroupBox, icons as scoutIcons, InitModelOf, LabelField, ManageBookmarksForm, Menu, scout
} from '@eclipse-scout/core';
import {App, DesktopWidgetMap, icons} from '../index';
import model from './DesktopModel';

export class Desktop extends ScoutDesktop {

  declare widgetMap: DesktopWidgetMap;

  constructor() {
    super();
  }

  protected override _jsonModel(): DesktopModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);

    this.widget('CreateBookmarkMenu').on('action', this._onCreateBookmarkAction.bind(this));
    this.widget('ManageBookmarksMenu').on('action', this._onManageBookmarksAction.bind(this));
    this._rebuildBookmarkMenus();

    this.on('logoAction', this._onLogoAction.bind(this));
    let defaultThemeMenu = this.widget('DefaultThemeMenu');
    defaultThemeMenu.on('action', this._onDefaultThemeMenuAction.bind(this));
    let darkThemeMenu = this.widget('DarkThemeMenu');
    darkThemeMenu.on('action', this._onDarkThemeMenuAction.bind(this));
    let denseModeMenu = this.widget('DenseMenu');
    denseModeMenu.on('action', this._onDenseMenuAction.bind(this));

    if (this.theme === 'dark') {
      darkThemeMenu.setIconId(scoutIcons.CHECKED_BOLD);
    } else {
      defaultThemeMenu.setIconId(scoutIcons.CHECKED_BOLD);
    }
    if (this.dense) {
      denseModeMenu.setIconId(scoutIcons.CHECKED_BOLD);
    }
    this.on('propertyChange:dense', event => this.dense ? denseModeMenu.setIconId(scoutIcons.CHECKED_BOLD) : denseModeMenu.setIconId(null));
  }

  protected _onDefaultThemeMenuAction(event: Event<Menu>) {
    this.setTheme('default');
  }

  protected _onDarkThemeMenuAction(event: Event<Menu>) {
    this.setTheme('dark');
  }

  protected _onDenseMenuAction(event: Event<Menu>) {
    this.setDense(!this.dense);
  }

  protected _onLogoAction(event: Event<Desktop>) {
    let form = this._createAboutInfoForm();
    form.open();
  }

  protected _createAboutInfoForm(): Form {
    return scout.create(Form, {
      parent: this,
      resizable: false,
      title: 'Scout JS Widgets Application',
      rootGroupBox: {
        objectType: GroupBox,
        borderDecoration: 'empty',
        fields: [{
          objectType: LabelField,
          value: this.session.text('AboutText', App.get().scoutVersion),
          labelVisible: false,
          wrapText: true,
          htmlEnabled: true,
          cssClass: 'about-info',
          statusVisible: false,
          gridDataHints: {
            useUiHeight: true
          }
        }]
      }
    });
  }
  protected _rebuildBookmarkMenus() {
    let bookmarksMenu = this.widget('BookmarksMenu');
    let createBookmarkMenu = this.widget('CreateBookmarkMenu');
    let manageBookmarksMenu = this.widget('ManageBookmarksMenu');

    bookmarksMenu.setChildActions([createBookmarkMenu, manageBookmarksMenu]);

    BookmarkStore.get(this.session).loadAllBookmarks()
      .then(bookmarks => {
        let loadBookmarkMenus = bookmarks.map(bookmark => {
          let name = bookmark.title;
          let menu = scout.create(Menu, {
            parent: bookmarksMenu,
            iconId: icons.BOOKMARK,
            text: name
          });
          menu.on('action', this._onActivateBookmarkAction.bind(this, bookmark.id));
          return menu;
        });
        bookmarksMenu.setChildActions([createBookmarkMenu, manageBookmarksMenu, ...loadBookmarkMenus]);
      })
      .catch(error => {
        $.log.error('Could not load bookmarks', error);
      });
  }

  protected _onCreateBookmarkAction(event: Event<Action>) {
    let form = scout.create(BookmarkForm, {
      parent: this
    });
    form.whenSave().then(() => {
      scout.create(DesktopNotification, {
        parent: this,
        message: 'Bookmark saved!'
      }).show();
      this._rebuildBookmarkMenus();
    });
    form.open();
  }

  protected _onManageBookmarksAction(event: Event<Action>) {
    let form = this.session.desktop.createFormExclusive(ManageBookmarksForm, {
      parent: this
    }, 'jswidgets:ManageBookmarksForm');
    form.whenSave().then(() => {
      this._rebuildBookmarkMenus();
    });

    // Disable the possibility to create a new bookmark while the manage bookmark form is open,
    // because the form would not detect new bookmarks.
    let createBookmarkMenu = this.widget('CreateBookmarkMenu');
    createBookmarkMenu.setEnabled(false);
    form.whenClose().then(() => createBookmarkMenu.setEnabled(true));

    return form.open();
  }

  protected _onActivateBookmarkAction(id: string, event: Event<Action>) {
    this.setBusy(true);
    BookmarkStore.get(this.session).loadBookmark(id)
      .then(bookmark => BookmarkSupport.get(this.session).activateBookmark(bookmark))
      .always(() => this.setBusy(false));
  }
}
