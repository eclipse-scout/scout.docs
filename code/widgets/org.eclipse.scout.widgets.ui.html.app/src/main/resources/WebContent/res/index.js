$(document).ready(function() {
  var app = new scout.RemoteApp();
  app.init({
    session: {
      showTreeIcons: true
    },
    bootstrap: {
      fonts: ['scoutIcons']
    }
  });
});
