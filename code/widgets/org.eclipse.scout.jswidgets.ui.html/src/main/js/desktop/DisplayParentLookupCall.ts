/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Desktop, DisplayParent, Form, Outline, StaticLookupCall} from '@eclipse-scout/core';

export class DisplayParentLookupCall extends StaticLookupCall<string> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return DisplayParentLookupCall.DATA;
  }

  static DATA = [
    ['desktop', 'desktop'],
    ['outline', 'outline'],
    ['form', 'form']
  ];

  static resolveDisplayParentType(displayParent: DisplayParent): string {
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

  static displayParentForType(form: Form, type: string): DisplayParent {
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
