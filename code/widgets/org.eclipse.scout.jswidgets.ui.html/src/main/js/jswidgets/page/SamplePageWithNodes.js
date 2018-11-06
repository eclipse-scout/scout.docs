jswidgets.SamplePageWithNodes = function() {
  jswidgets.SamplePageWithNodes.parent.call(this);
};
scout.inherits(jswidgets.SamplePageWithNodes, scout.PageWithNodes);

jswidgets.SamplePageWithNodes.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.SamplePageWithNodes');
};

jswidgets.SamplePageWithNodes.prototype._createChildPages = function() {
  return $.resolvedPromise([
    scout.create('jswidgets.SamplePageWithTable', this._pageParam()),
    scout.create('jswidgets.SamplePageWithNodes', this._pageParam())
  ]);
};
