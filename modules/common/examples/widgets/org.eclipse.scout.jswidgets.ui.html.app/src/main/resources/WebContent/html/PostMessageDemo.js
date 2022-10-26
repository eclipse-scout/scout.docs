// ### IMPLEMENTATION NOTE ###
// Because this file is not processed by a transpiler, the following code is deliberately written in ES5 style.
// Otherwise, it couldn't be run in very old browsers (e.g. Internet Explorer).

// -- Helper methods -----

function log(text, cssClass) {
  var ts = new Date().toISOString();
  var entry = document.createElement('div');
  logBox.insertBefore(entry, logBox.childNodes[0]); // legacy version of "logBox.prepend(entry)"
  entry.textContent = '[' + ts + '] ' + text;
  if (cssClass) {
    entry.className = cssClass;
  }
}

function getTargetWindow() {
  switch (target.value) {
    case 'window.parent':
      return window.parent;
    case 'window.parent.parent':
      return window.parent.parent;
    case 'window.opener':
      return window.opener;
    case 'window.frames[0]':
      return window.frames[0];
    default:
      return window;
  }
}

function formatObject(o) {
  if (o && typeof o === 'object') {
    return JSON.stringify(o);
  }
  return String(o);
}

function sendMessage(data) {
  var targetWindow = getTargetWindow();
  if (!targetWindow) {
    log('Target window not available', 'error');
    return;
  }
  targetWindow.postMessage(data, targetOrigin.value);
  log('Sent: ' + formatObject(data), 'out');
  counter++;
}

// -------------------------------------

// Enable all elements, since we now know that JS is enabled
var disabledElements = document.querySelectorAll('[disabled]');
// noinspection JSFunctionExpressionToArrowFunction
Array.prototype.forEach.call(disabledElements, function(el) {
  el.removeAttribute('disabled');
});

var button1 = document.getElementById('button1');
var button2 = document.getElementById('button2');
var button3 = document.getElementById('button3');
var button4 = document.getElementById('button4');
var button5 = document.getElementById('button5');
var logBox = document.getElementById('log');
var target = document.getElementById('target');
var targetOrigin = document.getElementById('targetOrigin');

var counter = 0;

// -- Buttons -----

var names = ['World', 'Scout', 'Alice', 'Bob', 'Again'];
// noinspection JSFunctionExpressionToArrowFunction
button1.addEventListener('click', function(event) {
  sendMessage('Hello ' + names[Math.floor(Math.random() * names.length)] + '!');
});

// noinspection JSFunctionExpressionToArrowFunction
button2.addEventListener('click', function(event) {
  sendMessage(counter);
});

// noinspection JSFunctionExpressionToArrowFunction
button3.addEventListener('click', function(event) {
  sendMessage('{"counter": ' + counter + ', "message": "Hello Scout!"}');
});

// noinspection JSFunctionExpressionToArrowFunction
button4.addEventListener('click', function(event) {
  sendMessage({
    counter: counter,
    message: 'Hello Scout!'
  });
});

// noinspection JSFunctionExpressionToArrowFunction
button5.addEventListener('click', function(event) {
  sendMessage(['One', 'Two', 'Three', counter, {msg: 'Hello Scout!'}]);
});

if ('URLSearchParams' in window) {
  var params = new URLSearchParams(window.location.search);
  if (!params.get('nested')) {
    // Only add if not nested twice
    document.body.appendChild(document.createElement('hr'));

    var iframeContainer = document.createElement('div');
    iframeContainer.id = 'iframe-container';
    document.body.appendChild(iframeContainer);

    var addIframeButton = document.createElement('button');
    addIframeButton.innerText = 'Add nested iframe';
    document.body.appendChild(addIframeButton);

    addIframeButton.addEventListener('click', function(event) {
      var iframe = document.createElement("iframe");
      iframe.scrolling = "yes";
      iframeContainer.appendChild(iframe);
      iframe.src = document.location + '?nested=true';
      addIframeButton.remove(); // remove button after nested iframe was inserted
    });
  }
}

// -- Message listener -----

// noinspection JSFunctionExpressionToArrowFunction
window.addEventListener('message', function(event) {
  log('Received: ' + formatObject(event.data) + ' (origin: ' + event.origin + ')', 'in');
});
