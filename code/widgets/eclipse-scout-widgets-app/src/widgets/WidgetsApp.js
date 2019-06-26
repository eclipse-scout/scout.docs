/*******************************************************************************
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
import { App } from '@eclipse-scout/eclipse-scout';

export default class WidgetsApp extends App {
  _init(model) {
    super._init(model);

    // FIXME [awe] ES6: check the Plugin proposed by "Izhaki", this would allow to re-define the properties exported by Webpack.
    // Without the configurable: true property, the code below cannot work.
    /*
    var origFunc = scout.isFunction;
    Object.defineProperty(scout, 'isFunction', {
      value: (obj) => {
        console.log('You\'ve been p0wnd!');
        return origFunc(obj);
      }
    });
    */
  }
}
