jswidgets.App = function() {
  jswidgets.App.parent.call(this);
};
scout.inherits(jswidgets.App, scout.App);

jswidgets.App.prototype._createDesktop = function(parent) {
  return scout.create('Desktop',
    scout.models.getModel('jswidgets.Desktop', parent));
};
