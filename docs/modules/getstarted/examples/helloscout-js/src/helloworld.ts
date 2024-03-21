import {App, ObjectFactory, scout} from '@eclipse-scout/core';
import {Desktop} from './desktop/Desktop'
import {HelloForm} from './greeting/HelloForm'

scout.addObjectFactories({
  'Desktop': () => new Desktop()
});

ObjectFactory.get().registerNamespace('helloworld', {
  HelloForm
});

new App().init({
  bootstrap: {
    textsUrl: 'texts.json',
    localesUrl: 'locales.json'
  }
});
