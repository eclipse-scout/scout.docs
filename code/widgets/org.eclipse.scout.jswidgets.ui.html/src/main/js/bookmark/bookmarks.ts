/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {arrays, BookmarkDo, dataObjects, UuidPool, webstorage} from '@eclipse-scout/core';

// Simple bookmark service that uses the local storage
export const bookmarks = {

  DEFAULT_STORE_ID: 'jswidgets:bookmarks',

  getBookmarkStore(storeId?: string): BookmarkDo[] {
    storeId = storeId || bookmarks.DEFAULT_STORE_ID;
    let raw = webstorage.getItemFromLocalStorage(storeId);
    return dataObjects.parse(raw, Array<BookmarkDo>);
  },

  setBookmarkStore(bookmarkStore: BookmarkDo[], storeId?: string) {
    storeId = storeId || bookmarks.DEFAULT_STORE_ID;
    if (!bookmarkStore) {
      webstorage.removeItemFromLocalStorage(storeId);
      return;
    }
    webstorage.setItemToLocalStorage(storeId, dataObjects.stringify(bookmarkStore));
  },

  storeBookmark(bookmark: BookmarkDo, storeId?: string): JQuery.Promise<void> {
    return $.resolvedPromise().then(() => {
      if (!bookmark) {
        return;
      }

      let bookmarkStore = bookmarks.getBookmarkStore(storeId) || [];
      bookmark.id = bookmark.id || UuidPool.get().take();
      let index = bookmarkStore.findIndex(b => b.id === bookmark.id);
      if (index === -1) {
        bookmarkStore.push(bookmark);
      } else {
        bookmarkStore[index] = bookmark;
      }
      bookmarks.setBookmarkStore(bookmarkStore, storeId);
    });
  },

  loadBookmark(id: string, storeId?: string): JQuery.Promise<BookmarkDo> {
    return $.resolvedPromise().then(() => {
      let bookmarkStore = bookmarks.getBookmarkStore(storeId) || [];
      return bookmarkStore.find(b => b.id === id) || null;
    });
  },

  loadAllBookmarks(storeId?: string): JQuery.Promise<BookmarkDo[]> {
    return $.resolvedPromise().then(() => {
      return bookmarks.getBookmarkStore(storeId) || [];
    });
  },

  storeAllBookmarks(allBookmarks: BookmarkDo[], storeId?: string): JQuery.Promise<void> {
    return $.resolvedPromise().then(() => {
      allBookmarks = arrays.ensure(allBookmarks).filter(bookmark => {
        if (!bookmark) {
          return false;
        }
        bookmark.id = bookmark.id || UuidPool.get().take();
        return true;
      });
      bookmarks.setBookmarkStore(allBookmarks, storeId);
    });
  }
} as const;
