jswidgets.WatchField = function() {
  jswidgets.WatchField.parent.call(this);
};
scout.inherits(jswidgets.WatchField, scout.FormField);

jswidgets.WatchField.prototype._render = function() {
  // create the field container
  this.addContainer(this.$parent, 'watch-field');
  // create a label
  this.addLabel();

  // create a field
  var $field = this.$parent.appendDiv('content');
  this.addField($field);
  var htmlField = scout.HtmlComponent.install(this.$field, this.session);

  // watch canvas
  this.$canvas = this.$field.makeElement('<canvas>', 'clock');
  this.$field.append(this.$canvas);

  // layout
  htmlField.setLayout(new jswidgets.WatchFieldLayout(this));

  this.addMandatoryIndicator();
  this.addStatus();

  // start watch
  this._paintWatch();
  setInterval(this._paintWatch.bind(this), 1000);
};

jswidgets.WatchField.prototype._paintWatch = function() {
  var context = this.$canvas[0].getContext("2d");
  var radius = this.$canvas.height() / 2;
  context.clearRect(0, 0, this.$canvas.height(), this.$canvas.height());
  context.setTransform(1, 0, 0, 1, 0, 0); // do not use resetTransform because it is experimental API and not supported by all browsers
  context.translate(radius, radius);
  radius = radius * 0.90;
  this._paintBackground(context, radius);
  this._paintNumbers(context, radius);
  this._paintTime(context, radius);
};

jswidgets.WatchField.prototype._paintBackground = function(context, radius) {
  var strokeStyle;

  context.beginPath();
  context.fillStyle = 'white';
  context.arc(0, 0, radius, 0, 2 * Math.PI);
  context.fill();
  strokeStyle = context.createRadialGradient(0, 0, radius * 0.95, 0, 0, radius * 1.05);
  strokeStyle.addColorStop(0, 'white');
  strokeStyle.addColorStop(0.3, '#014786');
  strokeStyle.addColorStop(0.7, '#014786');
  strokeStyle.addColorStop(1, 'white');
  context.strokeStyle = strokeStyle;
  context.lineWidth = radius * 0.06;
  context.stroke();
};

jswidgets.WatchField.prototype._paintNumbers = function(context, radius) {
  var ang;
  var hour;
  context.font = radius * 0.15 + "px arial";
  context.fillStyle = '#014786';
  context.strokeStyle = '#014786';
  context.textAlign = "center";
  context.textBaseline = "middle";
  for (hour = 1; hour <= 12; hour++) {
    ang = hour * 2 * Math.PI / 12;
    context.rotate(ang);
    context.translate(0, -radius * 0.85);
    context.rotate(-ang);
    if (hour % 3 === 0) {
      context.fillText(hour.toString(), 0, 0);
    } else {
      context.beginPath();
      context.arc(0, 0, radius * 0.01, 0, 2 * Math.PI);
      context.fill();
    }
    context.rotate(ang);
    context.translate(0, radius * 0.85);
    context.rotate(-ang);
  }
};

jswidgets.WatchField.prototype._paintTime = function(context, radius) {
  var now = new Date(),
    hour = now.getHours(),
    minute = now.getMinutes(),
    second = now.getSeconds(),
    hourAngle = (2 * Math.PI / 12 * hour % 12) + // hours
    (2 * Math.PI / 12 / 60 * minute) + // minutes
    (2 * Math.PI / 12 / 60 / 60 * second), // seconds
    minuteAngle = (2 * Math.PI / 60 * minute) + // minutes
    (2 * Math.PI / 60 / 60 * second), // seconds
    secondAngle = (2 * Math.PI / 60 * second);

  // paint hour line
  this._paintLine(context, hourAngle, 0, radius * 0.6, radius * 0.04);
  //minute
  this._paintLine(context, minuteAngle, 0, radius * 0.8, radius * 0.04);
  // second
  this._paintLine(context, secondAngle, radius * 0.7, radius * 1, radius * 0.02);
};

jswidgets.WatchField.prototype._paintLine = function(context, angle, begin, length, width) {
  context.beginPath();
  context.strokeStyle = '#014786';
  context.lineWidth = width;
  context.lineCap = "round";
  context.rotate(angle);
  context.moveTo(0, -begin);
  context.lineTo(0, -length);
  context.stroke();
  context.rotate(-angle);
};
