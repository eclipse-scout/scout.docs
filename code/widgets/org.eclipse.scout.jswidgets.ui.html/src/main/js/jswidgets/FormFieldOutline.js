jswidgets.FormFieldOutline = function() {
  jswidgets.FormFieldOutline.parent.call(this);
};
scout.inherits(jswidgets.FormFieldOutline, scout.Outline);

jswidgets.FormFieldOutline.prototype._jsonModel = function() {
  return scout.models.getModel('jswidgets.FormFieldOutline');
};
