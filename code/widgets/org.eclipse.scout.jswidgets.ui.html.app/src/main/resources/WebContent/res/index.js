$(document).ready(function() {
  var app = new jswidgets.App();
  app.init({
    bootstrap: {
      modelsUrl: 'res/jswidgets-all.json',
      textsUrl: 'res/texts.json'
    }
  });
});
