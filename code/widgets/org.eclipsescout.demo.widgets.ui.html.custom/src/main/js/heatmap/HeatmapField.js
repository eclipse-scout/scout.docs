scout.HeatmapField = function() {
  scout.HeatmapField.parent.call(this);
};
scout.inherits(scout.HeatmapField, scout.FormField);

scout.HeatmapField.prototype._render = function($parent) {
  this.addContainer($parent, 'heatmap-field');
  this.addLabel();
  this.addStatus();

  var heatmapId = scout.objectFactory.createUniqueId();
  var $field = this.$container
    .makeDiv('heatmap')
    .attr('id', heatmapId);
  this.addField($field);

  // Before (!) installing the layout, set the initial size to 1x1. The size of $field must not
  // get smaller than that, because Leaflet.js throws an error when the drawing canvas has size 0.
  // After the initial rendering, this condition is ensured by HeapmapFieldLayout.js.
  scout.graphics.setSize($field, new scout.Dimension(1, 1));
  var fieldHtmlComp = new scout.HtmlComponent($field, this.session);
  fieldHtmlComp.setLayout(new scout.HeatmapFieldLayout(this));

  this.heatmap = L.map(heatmapId, {
    trackResize: false
  });
  L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
    attribution: '&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors',
  }).addTo(this.heatmap);

  this.heatmap.on('zoomend', this._handleViewParameterChanged.bind(this));
  this.heatmap.on('moveend', this._handleViewParameterChanged.bind(this));
  this.heatmap.on('click', this._handleClicked.bind(this));
  this.heatmap.on('contextmenu', this._handleClicked.bind(this));
};

scout.HeatmapField.prototype._renderProperties = function() {
  scout.HeatmapField.parent.prototype._renderProperties.call(this);
  this._renderViewParameter();
  this._renderHeatPointList();
};

scout.HeatmapField.prototype._remove = function() {
  scout.HeatmapField.parent.prototype._remove.call(this);

  this.heatmap.remove();
  this.heatmap = null;
  this._heatLayer = null;
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

scout.HeatmapField.prototype._handleClicked = function(event) {
  this._send('clicked', {
    point: {
      x: event.latlng.lat,
      y: event.latlng.lng
    }
  });
};

scout.HeatmapField.prototype._renderViewParameter = function() {
  this.heatmap.setView([this.viewParameter.center.x, this.viewParameter.center.y], this.viewParameter.zoomFactor);
};

scout.HeatmapField.prototype._renderHeatPointList = function() {
  if (this._heatLayer) {
    this.heatmap.removeLayer(this._heatLayer);
  }
  this._heatLayer = L.heatLayer(this.heatPointList,
	  // TODO make this parameter list configurable from the model!
      // parameters to control the appearance of heat points
	  // see leaflet.heat docu for full spec
	  { radius: 20, blur: 30, max: 1.0 }
  );
  this._heatLayer.addTo(this.heatmap);
};

scout.HeatmapField.prototype._onHeatPointsAdded = function(points) {
  if (this._heatLayer) {
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
