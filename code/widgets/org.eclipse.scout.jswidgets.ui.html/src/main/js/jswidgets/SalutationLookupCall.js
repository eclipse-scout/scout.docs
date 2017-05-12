jswidgets.SalutationLookupCall = function() {
  jswidgets.SalutationLookupCall.parent.call(this);

  this.setDelay(250);
};
scout.inherits(jswidgets.SalutationLookupCall, scout.StaticLookupCall);

jswidgets.SalutationLookupCall.prototype._data = function() {
  return jswidgets.SalutationLookupCall.DATA;
};

jswidgets.SalutationLookupCall.DATA = [
  ['Weiblich', 'female'],
  ['Männlich', 'male'],
  ['Unpersönlich', 'impersonal'],
  ['Unbekannt', 'unknown']
];
