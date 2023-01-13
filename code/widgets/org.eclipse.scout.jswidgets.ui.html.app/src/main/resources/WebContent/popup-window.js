/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */

// Note: all other forms of attaching a 'load' listener
// to either the window or the document are not reliable.

// When window is opened by main-window the popupWindow instance is always set
var url, eventData = {
  window: window
};
if (window.popupWindow) {
  eventData.popupWindow = window.popupWindow;
} else {
  url = new window.opener.scout.URL(document.location);
  eventData.formId = url.getParameter('formId');
}
window.opener.$(window.opener.document).trigger('popupWindowReady', eventData);
