import {Desktop as ScoutDesktop, DesktopModel as ScoutDesktopModel} from '@eclipse-scout/core';
import DesktopModel from './DesktopModel';

export class Desktop extends ScoutDesktop {
  protected override _jsonModel(): ScoutDesktopModel {
    return DesktopModel();
  }
}
