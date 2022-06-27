import {MessageBoxes, scout} from '@eclipse-scout/core';
/*
 * Copyright (c) BSI Business Systems Integration AG. All rights reserved.
 * http://www.bsiag.com/
 */
export default class Contribution {
  constructor() {
  }

  sayHello() {
    MessageBoxes.createOk(scout.getSession().desktop).withBody('Hello there').buildAndOpen();
  }
}
