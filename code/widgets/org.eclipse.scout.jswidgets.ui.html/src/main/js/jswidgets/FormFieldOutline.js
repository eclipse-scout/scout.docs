jswidgets.FormFieldOutline = function() {
  jswidgets.FormFieldOutline.parent.call(this);
};
scout.inherits(jswidgets.FormFieldOutline, scout.Outline);

/**
 * @override Outline.js
 */
jswidgets.FormFieldOutline.prototype._init = function(model) {
  jswidgets.FormFieldOutline.parent.prototype._init.call(this,
      this._loadJsonModel('jswidgets.FormFieldOutline', model));
};
