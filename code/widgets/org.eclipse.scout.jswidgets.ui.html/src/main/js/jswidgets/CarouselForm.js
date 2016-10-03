jswidgets.CarouselForm = function() {
  jswidgets.CarouselForm.parent.call(this);
};
scout.inherits(jswidgets.CarouselForm, scout.Form);

jswidgets.CarouselForm.prototype._init = function(model) {
  jswidgets.CarouselForm.parent.prototype._init.call(this,
      this._loadJsonModel('jswidgets.CarouselForm', model));

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.getWidgetById("DetailBox"));

  scout.arrays.ensure(this.getWidgetById("Carousel").widgets).forEach(function (widget) {
    if (widget.rootGroupBox) {
      var grid = new scout.HorizontalGroupBoxBodyGrid();
      grid.validate(widget.rootGroupBox);
    }
  });
};
