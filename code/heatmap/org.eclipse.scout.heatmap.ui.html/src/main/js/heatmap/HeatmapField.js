scout.HeatmapField = function() {
  scout.HeatmapField.parent.call(this);
};
scout.inherits(scout.HeatmapField, scout.FormField);

scout.HeatmapField.prototype._renderProperties = function() {
  scout.HeatmapField.parent.prototype._renderProperties.call(this);
  this._renderViewParameter();
  this._renderHeatPointList();
};

scout.HeatmapField.prototype._render = function($parent) {
  this.addContainer($parent, 'heatmap-field');
  this.addLabel();
  this.addMandatoryIndicator();


  var heatmapId = scout.objectFactory.createUniqueId();
  var $field = this.$container
    .makeDiv('heatmap')
    .attr('id', heatmapId);
  this.addField($field);

  var fieldHtmlComp = new scout.HtmlComponent($field, this.session);
  fieldHtmlComp.setSize(new scout.Dimension(1,1));
  fieldHtmlComp.setLayout(new scout.HeatmapFieldLayout(this));

  this.heatmap = L.map(heatmapId, {trackResize: false});
  var tiles = L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
  }).addTo(this.heatmap);

  this.heatmap.on('zoomend', this._handleViewParameterChanged.bind(this));
  this.heatmap.on('moveend', this._handleViewParameterChanged.bind(this));


};

scout.HeatmapField.prototype._handleViewParameterChanged = function() {
	this._send('viewParameterChanged', {
		center: {
			x: this.heatmap.getCenter().lat,
			y: this.heatmap.getCenter().lng
		},
		zoomFactor: this.heatmap.getZoom()
	});
};

scout.HeatmapField.prototype._renderViewParameter = function() {
	  this.heatmap.setView([this.viewParameter.center.x, this.viewParameter.center.y],this.viewParameter.zoomFactor);
};

scout.HeatmapField.prototype._renderHeatPointList = function() {
  if(this._heatLayer) {
    this.heatmap.removeLayer(this._heatLayer);
  }
  this._heatLayer = L.heatLayer(this.heatPointList, {radius: 20})
  this._heatLayer.addTo(this.heatmap);
};

scout.HeatmapField.prototype._onHeatPointsAdded = function(points) {
  if(this._heatLayer) {
    for (i = 0; i < points.length; i++) {
      this._heatLayer.addLatLng(points[i]);
    }
  }
};

scout.HeatmapField.prototype.onModelAction = function(event) {
  if (event.type === 'heatPointsAdded') {
    this._onHeatPointsAdded(event.points);
  } else {
    scout.HeatmapField.parent.prototype.onModelAction.call(this, event);
  }
};