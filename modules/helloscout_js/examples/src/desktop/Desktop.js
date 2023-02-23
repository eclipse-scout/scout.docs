import {Desktop as ScoutDesktop} from '@eclipse-scout/core';
import DesktopModel from './DesktopModel';

export class Desktop extends ScoutDesktop {

  _jsonModel() {
    return DesktopModel();
  }
}
