import {Menu} from '@eclipse-scout/core';

export default class SamplePageWithTableTileToggleMenu extends Menu {

constructor() {
  super();
}


_init(model) {
  super._init( model);

  this.on('action', function() {
    var table = model.parent;
    table.setTileMode(!table.tileMode);
  }.bind(this));
}
}
