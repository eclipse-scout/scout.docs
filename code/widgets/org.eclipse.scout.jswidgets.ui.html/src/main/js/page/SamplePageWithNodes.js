import {PageWithNodes, models, scout} from '@eclipse-scout/core';
import SamplePageWithNodesModel from './SamplePageWithNodesModel';
import * as $ from 'jquery';

export default class SamplePageWithNodes extends PageWithNodes {

constructor() {
  super();
}


_jsonModel() {
  return models.get(SamplePageWithNodesModel);
}

_createChildPages() {
  return $.resolvedPromise([
    scout.create('jswidgets.SamplePageWithTable', this._pageParam()),
    scout.create('jswidgets.SamplePageWithNodes', this._pageParam())
  ]);
}
}
