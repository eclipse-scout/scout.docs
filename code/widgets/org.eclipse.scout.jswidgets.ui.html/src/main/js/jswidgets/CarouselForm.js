jswidgets.CarouselForm = function() {
  jswidgets.CarouselForm.parent.call(this);
};
scout.inherits(jswidgets.CarouselForm, scout.Form);

jswidgets.CarouselForm.prototype._init = function(model) {
  jswidgets.CarouselForm.parent.prototype._init.call(this, model);

  var bodyGrid = new scout.HorizontalGroupBoxBodyGrid();
  bodyGrid.validate(this.widget('DetailBox'));

  scout.arrays.ensure(this.widget('Carousel').widgets).forEach(function(widget) {
    if (widget.rootGroupBox) {
      var grid = new scout.HorizontalGroupBoxBodyGrid();
      grid.validate(widget.rootGroupBox);
    }
  });
};

jswidgets.CarouselForm.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.CarouselForm');
};
