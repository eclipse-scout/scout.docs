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
import {StaticLookupCall, Tree} from '@eclipse-scout/core';

export default class CheckableTreeStyleLookupCall extends StaticLookupCall {

  constructor() {
    super();
  }


  _data() {
    return CheckableTreeStyleLookupCall.DATA;
  }

  static DATA = [
    [Tree.CheckableStyle.CHECKBOX, 'checkbox'],
    [Tree.CheckableStyle.CHECKBOX_TREE_NODE, 'checkbox_tree_node']
  ];
}
