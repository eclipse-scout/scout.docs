jswidgets.SalutationLookupCall = function() {
  jswidgets.SalutationLookupCall.parent.call(this);
};
scout.inherits(jswidgets.SalutationLookupCall, jswidgets.StaticLookupCall);

jswidgets.SalutationLookupCall.prototype._data = function() {
  return jswidgets.SalutationLookupCall.DATA;
};

jswidgets.SalutationLookupCall.DATA = [
  ['Weiblich', 'female'],
  ['Männlich', 'male'],
  ['Unpersönlich', 'impersonal'],
  ['Unbekannt', 'unknown']
];
