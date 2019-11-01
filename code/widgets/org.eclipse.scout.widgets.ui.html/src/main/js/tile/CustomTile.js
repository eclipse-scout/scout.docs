import {Tile, HtmlComponent} from '@eclipse-scout/core';

export default class CustomTile extends Tile {

  constructor() {
    super();
    this.label = null;
  }


  _render() {
    this.$container = this.$parent.appendDiv('custom-tile');
    this.htmlComp = HtmlComponent.install(this.$container, this.session);
  }

  _renderProperties() {
    super._renderProperties();
    this._renderLabel();
  }

  _renderLabel() {
    this.$container.text(this.label);
  }
}
