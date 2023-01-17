/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {RemoteApp} from '@eclipse-scout/core';
import '@eclipse-scout/svg';
import '@eclipse-scout/chart';
import '@eclipse-scout/demo-widgets';
import '@eclipse-scout/demo-widgets-heatmap';

new RemoteApp().init({
  bootstrap: {
    textsUrl: 'res/texts.json'
  }
});
