/*
 * Copyright (c) 2021 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */

// tag::hello[]
class Desktop extends scout.Desktop {

  constructor() {
    super();
  }

  _jsonModel() {
    return {
      objectType: 'Desktop',
      navigationHandleVisible: false,
      navigationVisible: false,
      headerVisible: false,
      views: [
        {
          objectType: 'Form',
          displayHint: 'view',
          modal: false,
          rootGroupBox: {
            objectType: 'GroupBox',
            borderDecoration: scout.GroupBox.BorderDecoration.EMPTY,
            fields: [
              {
                id: 'NameField',
                objectType: 'StringField',
                label: 'Name'
              },
              {
                id: 'GreetButton',
                objectType: 'Button',
                label: 'Say Hello',
                keyStroke: 'enter',
                processButton: false
              }
            ]
          }
        }
      ]
    };
  }

  _init(model) {
    super._init(model);
    this.widget('GreetButton').on('click', event => {
      let name = this.widget('NameField').value || 'stranger';
      scout.MessageBoxes.openOk(this.session.desktop, `Hello ${name}!`);
    });
  }
}

scout.addObjectFactories({
  'Desktop': () => new Desktop()
});

new scout.App().init({
  bootstrap: {
    textsUrl: 'https://cdn.jsdelivr.net/npm/@eclipse-scout/core@11.0.10/dist/texts.json'
  }
});
// end::hello[]
