jswidgets.Desktop = function() {
  jswidgets.Desktop.parent.call(this);
};
scout.inherits(jswidgets.Desktop, scout.Desktop);

jswidgets.Desktop.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.Desktop');
};

jswidgets.Desktop.prototype._init = function(model) {
  jswidgets.Desktop.parent.prototype._init.call(this, model);

  this.widget('AboutMenu').on('action', this._onAboutMenuAction.bind(this));
};

jswidgets.Desktop.prototype._onAboutMenuAction = function(event) {
  var form = scout.create('Form', {
    parent: this,
    resizable: false,
    title: "Scout JS Widgets Application",
    rootGroupBox: {
      objectType: 'GroupBox',
      borderDecoration: 'empty',
      fields: [{
        objectType: 'LabelField',
        value: this.session.text('AboutText', scout.app.scoutVersion),
        labelVisible: false,
        wrapText: true,
        htmlEnabled: true,
        cssClass: "about-info",
        statusVisible: false,
        gridDataHints: {
          h: 2
        }
      }]
    }
  });
  form.open();
};
