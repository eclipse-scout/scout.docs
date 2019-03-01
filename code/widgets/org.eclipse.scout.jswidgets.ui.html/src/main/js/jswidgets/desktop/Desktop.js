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
  var defaultThemeMenu = this.widget('DefaultThemeMenu');
  defaultThemeMenu.on('action', this._onDefaultThemeMenuAction.bind(this));
  var darkThemeMenu = this.widget('DarkThemeMenu');
  darkThemeMenu.on('action', this._onDarkThemeMenuAction.bind(this));
  var denseModeMenu = this.widget('DenseMenu');
  denseModeMenu.on('action', this._onDenseMenuAction.bind(this));

  if (this.theme === 'dark') {
    darkThemeMenu.setIconId(scout.icons.CHECKED_BOLD);
  } else {
    defaultThemeMenu.setIconId(scout.icons.CHECKED_BOLD);
  }
  if (this.dense) {
    denseModeMenu.setIconId(scout.icons.CHECKED_BOLD);
  }
  this.on('propertyChange', function(event) {
    if (event.propertyName === 'dense') {
      this.dense ? denseModeMenu.setIconId(scout.icons.CHECKED_BOLD) : denseModeMenu.setIconId(null);
    }
  }.bind(this));
};

jswidgets.Desktop.prototype._onDefaultThemeMenuAction = function(event) {
  this.setTheme('default');
};

jswidgets.Desktop.prototype._onDarkThemeMenuAction = function(event) {
  this.setTheme('dark');
};

jswidgets.Desktop.prototype._onDenseMenuAction = function(event) {
  this.setDense(!this.dense);
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
