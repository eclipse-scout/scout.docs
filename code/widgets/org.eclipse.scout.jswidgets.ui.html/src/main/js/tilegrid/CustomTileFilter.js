import {HtmlTile, strings} from '@eclipse-scout/core';
import {CustomTile} from '../index';

export default class CustomTileFilter {

  constructor(model) {
    model = model || {};
    this.text = null;
    this.setText(model.text);
  }

  setText(text) {
    this.text = text || '';
    this.text = this.text.toLowerCase();
  }

  accept(tile) {
    let label = '';
    if (tile instanceof CustomTile) {
      label = tile.label || '';
    } else if (tile instanceof HtmlTile) {
      label = strings.plainText(tile.content) || '';
    }
    let filterText = label.trim().toLowerCase();
    return filterText.indexOf(this.text) > -1;
  }
}
