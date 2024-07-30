/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Action, BookmarkForm, Desktop as ScoutDesktop, DesktopModel as DesktopModel, DesktopNotification, Event, Form, GroupBox, icons as scoutIcons, InitModelOf, LabelField, ManageBookmarksForm, Menu, scout} from '@eclipse-scout/core';
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

    this.widget('StoreBookmarkMenu').on('action', this._onStoreBookmarkAction.bind(this));
    this.widget('ManageBookmarksMenu').on('action', this._onManageBookmarksAction.bind(this));
    this._rebuildBookmarkMenus();
    this.on('bookmarksChanged', () => this._rebuildBookmarkMenus());

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
    let form = scout.create(Form, {
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
            h: 2
          }
        }]
      }
    });
    form.open();
  }

  protected _rebuildBookmarkMenus() {
    let bookmarksMenu = this.widget('BookmarksMenu');
    let storeBookmarkMenu = this.widget('StoreBookmarkMenu');
    let manageBookmarksMenu = this.widget('ManageBookmarksMenu');

    bookmarksMenu.setChildActions([storeBookmarkMenu, manageBookmarksMenu]);

    this.bookmarkSupport.loadAllBookmarks()
      .then(bookmarks => {
        let language = this.session.locale.language; // FIXME bsh [js-bookmark] Use LanguageCodeType
        let loadBookmarkMenus = bookmarks.map(bookmark => {
          let name = bookmark.titles?.[language];
          let menu = scout.create(Menu, {
            parent: bookmarksMenu,
            iconId: icons.BOOKMARK,
            text: name
          });
          menu.on('action', this._onLoadBookmarkAction.bind(this, bookmark.key));
          return menu;
        });
        bookmarksMenu.setChildActions([storeBookmarkMenu, manageBookmarksMenu, ...loadBookmarkMenus]);
      })
      .catch(error => {
        // ignore
      });
  }

  protected _onStoreBookmarkAction(event: Event<Action>) {
    this.bookmarkSupport.createBookmark().then(bookmark => {
      if (!bookmark) {
        return; // FIXME bsh [js-bookmark] Exception vs. return null?
      }
      let form = scout.create(BookmarkForm, {
        parent: this,
        bookmark: bookmark
      });
      form.open();
      form.whenSave().then(() => {
        this.setBusy(true);
        this.bookmarkSupport.storeBookmark(form.bookmark)
          .then(bookmark => {
            scout.create(DesktopNotification, {
              parent: this,
              message: 'Bookmark saved' // FIXME bsh [js-bookmark] NLS
            }).show();
          })
          .catch(error => App.get().errorHandler.handle(error))
          .then(() => this.setBusy(false));
      });
    });
  }

  protected _onManageBookmarksAction(event: Event<Action>) {
    let form = scout.create(ManageBookmarksForm, {
      parent: this
    });
    form.open();
  }

  protected _onLoadBookmarkAction(key: string, event: Event<Action>) {
    this.setBusy(true);
    this.bookmarkSupport.loadBookmark(key)
      .then(bookmark => {
        this.bookmarkSupport.openBookmarkInOutline(bookmark);
      })
      .catch(error => App.get().errorHandler.handle(error))
      .then(() => this.setBusy(false));
  }
}
