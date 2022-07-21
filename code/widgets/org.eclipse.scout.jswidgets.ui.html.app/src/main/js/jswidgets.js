/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {App} from '@eclipse-scout/demo-jswidgets';
import {ActionExt, scout} from '@eclipse-scout/core/src';
import NoUniqueId from '@eclipse-scout/core/src/NoUniqueId';

start();

async function start() {
  await new App().init({
    bootstrap: {
      textsUrl: 'res/texts.json'
    }
  });

  let action = scout.create(ActionExt, {
    parent: scout.getSession().desktop
  });
  action.doAction();

  @NoUniqueId
  class MyObj {
    constructor() {
      console.log(this);
    }
  }

  let obj = scout.create(MyObj);
  console.log(obj);

}
