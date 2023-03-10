import { scout, App } from '@eclipse-scout/core';
import { Desktop } from './desktop/Desktop'

scout.addObjectFactories({
  'Desktop': () => new Desktop()
});

new App().init({
  bootstrap: {
    textsUrl: 'texts.json',
    localesUrl: 'locales.json'
  }
});
