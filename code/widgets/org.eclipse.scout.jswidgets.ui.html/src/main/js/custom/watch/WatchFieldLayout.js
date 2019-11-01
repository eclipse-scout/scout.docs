import {NullLayout} from '@eclipse-scout/core';

export default class WatchFieldLayout extends NullLayout {

constructor(watchField) {
  super();
  this.watchField = watchField;
}


layout($container) {
  var containerHeight = $container.height(),
    containerWidth = $container.width(),
    size = Math.min(containerHeight, containerWidth),
    $canvas = this.watchField.$canvas;
  // set width and height
  $canvas.prop('width', size);
  $canvas.prop('height', size);
  // set css size and position
  $canvas
    .cssTop((containerHeight - size) / 2)
    .cssRight((containerWidth - size) / 2)
    .cssHeight(size)
    .cssWidth(size);

  this.watchField._paintWatch();
  super.layout( $container);
}
}
