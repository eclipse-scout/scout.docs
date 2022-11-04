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
import {StaticLookupCall, Tree, TreeCheckableStyle} from '@eclipse-scout/core';

export class CheckableTreeStyleLookupCall extends StaticLookupCall<TreeCheckableStyle> {

  constructor() {
    super();
  }

  protected override _data(): any[] {
    return CheckableTreeStyleLookupCall.DATA;
  }

  static DATA = [
    [Tree.CheckableStyle.CHECKBOX, 'checkbox'],
    [Tree.CheckableStyle.CHECKBOX_TREE_NODE, 'checkbox_tree_node']
  ];
}
