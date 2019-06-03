widgets.ChartField = function() {
  widgets.ChartField.parent.call(this);
  this.chart = null;
};
scout.inherits(widgets.ChartField, scout.ValueField);

widgets.ChartField.prototype._init = function(model) {
  widgets.ChartField.parent.prototype._init.call(this, model);
  this._setConfig(this.config);
};

widgets.ChartField.prototype._render = function() {
  this.addContainer(this.$parent, 'chart-field');
  this.addLabel();
  this.addMandatoryIndicator();

  var $field = this.$container.appendElement('<canvas>', 'chart');
  this.addField($field);

};

widgets.ChartField.prototype._renderProperties = function() {
  widgets.ChartField.parent.prototype._renderProperties.call(this);
  this._renderConfig();
  this._renderValue();
};

widgets.ChartField.prototype._remove = function() {
  widgets.ChartField.parent.prototype._remove.call(this);
  if (this.chart) {
    this.chart.destroy();
    this.chart = null;
  }
};

widgets.ChartField.prototype._renderValue = function() {
  if (!this.value) {
    return;
  }
  var datasets = this.value.map(function(data) {
    return {
      data: data
    };
  });
  $.extend(true, this.config.data.datasets, datasets);
  this.chart.update();
};

widgets.ChartField.prototype.setConfig = function(config) {
  this.setProperty('config', config);
};

widgets.ChartField.prototype._setConfig = function(config) {
  if (typeof config === 'string') {
    config = JSON.parse(config);
  }
  this._setProperty('config', config);
};

widgets.ChartField.prototype._renderConfig = function() {
  if (this.chart) {
    this.chart.destroy();
  }
  this.chart = new Chart(this.$field[0], this.config); // jshint ignore:line
  this._renderValue();
};
