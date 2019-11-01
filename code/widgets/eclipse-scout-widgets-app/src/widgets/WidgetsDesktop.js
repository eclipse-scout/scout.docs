import widgetsDesktopModel from './WidgetsDesktop.json';
import scout, {Desktop, OutlineViewButton} from '@eclipse-scout/eclipse-scout';

export default class WidgetsDesktop extends Desktop {
  _jsonModel() {
    return widgetsDesktopModel;
  }

  _init(model) {
    super._init(model);

    /** @type {OutlineViewButton}*/ const dataButton = scout.create(OutlineViewButton, {
      parent: this,
      text: 'Data',
      displayStyle: 'TAB'
    });
    /** @type {OutlineViewButton}*/ const searchButton = scout.create(OutlineViewButton, {
      parent: this,
      text: 'Search',
      displayStyle: 'TAB'
    });
    var viewButtons = this.viewButtons;
    this._setViewButtons(viewButtons.concat([dataButton, searchButton]));
  }
}

