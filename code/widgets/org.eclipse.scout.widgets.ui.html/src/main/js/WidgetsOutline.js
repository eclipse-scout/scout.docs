/*
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {Outline, scout} from '@eclipse-scout/core';

export default class WidgetsOutline extends Outline {

  constructor() {
    super();
  }


  _createOutlineOverview() {
    return scout.create('widgets.WidgetsTileOutlineOverview', {
      parent: this,
      outline: this
    });
  }
}
