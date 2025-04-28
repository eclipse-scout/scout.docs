/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {App, LocalBookmarkStore, objects, scout, Session} from '@eclipse-scout/core';

export class JsWidgetsBookmarkStore extends LocalBookmarkStore {

  protected static _INSTANCES: Map<Session, LocalBookmarkStore> = new Map();

  /**
   * Returns an instance of {@link JsWidgetsBookmarkStore} for a specific {@link Session}. If no instance is registered
   * for the session yet, a new instance is created. If the session is omitted, the first session of the app is used.
   */
  static get(session?: Session): LocalBookmarkStore {
    session = session || App.get().sessions[0];
    return objects.getOrSetIfAbsent(JsWidgetsBookmarkStore._INSTANCES, session, session => scout.create(JsWidgetsBookmarkStore));
  }

  // ----------

  protected override get _storeId(): string {
    return 'jswidgets:bookmarks';
  }
}
