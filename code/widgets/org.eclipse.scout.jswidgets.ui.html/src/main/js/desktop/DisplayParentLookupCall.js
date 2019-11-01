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
import {StaticLookupCall, Desktop, Form, Outline} from '@eclipse-scout/core';

export default class DisplayParentLookupCall extends StaticLookupCall {

constructor() {
  super();
}


_data() {
  return DisplayParentLookupCall.DATA;
}

static DATA = [
  ['desktop', 'desktop'],
  ['outline', 'outline'],
  ['form', 'form']
];

static resolveDisplayParentType(displayParent) {
  if (displayParent instanceof Desktop) {
    return 'desktop';
  }
  if (displayParent instanceof Outline) {
    return 'outline';
  }
  if (displayParent instanceof Form) {
    return 'form';
  }
  return null;
}

static displayParentForType(form, type) {
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
}
}
