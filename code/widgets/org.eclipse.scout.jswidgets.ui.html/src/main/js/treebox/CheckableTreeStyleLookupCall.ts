/*
 * Copyright (c) 2010, 2023 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
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
