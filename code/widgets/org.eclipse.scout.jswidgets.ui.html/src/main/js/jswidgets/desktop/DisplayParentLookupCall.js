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
jswidgets.DisplayParentLookupCall = function() {
  jswidgets.DisplayParentLookupCall.parent.call(this);
};
scout.inherits(jswidgets.DisplayParentLookupCall, scout.StaticLookupCall);

jswidgets.DisplayParentLookupCall.prototype._data = function() {
  return jswidgets.DisplayParentLookupCall.DATA;
};

jswidgets.DisplayParentLookupCall.DATA = [
  ['desktop', 'desktop'],
  ['outline', 'outline'],
  ['form', 'form']
];

jswidgets.DisplayParentLookupCall.resolveDisplayParentType = function(displayParent) {
  if (displayParent instanceof scout.Desktop) {
    return 'desktop';
  }
  if (displayParent instanceof scout.Outline) {
    return 'outline';
  }
  if (displayParent instanceof scout.Form) {
    return 'form';
  }
  return null;
};

jswidgets.DisplayParentLookupCall.displayParentForType = function(form, type) {
  if (type === 'desktop') {
    return form.session.desktop;
  }
  if (type === 'outline') {
    return form.session.desktop.outline;
  }
  if (type === 'form') {
    return form;
  }
  return null;
};
