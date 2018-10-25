jswidgets.Text = function() {
  jswidgets.Text.parent.call(this);
  this.text = null;
  this.htmlEnabled = false;
};
scout.inherits(jswidgets.Text, scout.Widget);

jswidgets.Text.prototype._render = function() {
  this.$container = this.$parent.appendDiv('jw-text');
  this.htmlComp = scout.HtmlComponent.install(this.$container, this.session);
};

jswidgets.Text.prototype._renderProperties = function() {
  jswidgets.Text.parent.prototype._renderProperties.call(this);
  this._renderText();
};

jswidgets.Text.prototype.setText = function(text) {
  this.setProperty('text', text);
};

jswidgets.Text.prototype._renderText = function() {
  var text = this.text || '';
  if (this.htmlEnabled) {
    this.$container.html(text);
  } else {
    this.$container.html(scout.strings.nl2br(text));
  }
  this.invalidateLayoutTree();
};

jswidgets.Text.prototype._renderHtmlEnabled = function() {
  // Render the text again when html enabled changes dynamically
  this._renderText();
};
