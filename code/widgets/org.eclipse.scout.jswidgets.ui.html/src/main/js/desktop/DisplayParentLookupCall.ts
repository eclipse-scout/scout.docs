/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
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
