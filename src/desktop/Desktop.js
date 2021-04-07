import { Desktop as ScoutDesktop, models } from '@eclipse-scout/core';
import DesktopModel from './DesktopModel';

export class Desktop extends ScoutDesktop {

  constructor() {
    super();
  }

  _jsonModel() {
    return models.get(DesktopModel);
  }
}
