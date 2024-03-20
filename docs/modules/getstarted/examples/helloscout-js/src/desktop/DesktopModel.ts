import {Desktop, DesktopModel} from "@eclipse-scout/core";
import {HelloForm} from "../greeting/HelloForm";

export default (): DesktopModel => ({
  objectType: Desktop,
  navigationHandleVisible: false,
  navigationVisible: false,
  headerVisible: false,
  views: [
    {
      objectType: HelloForm
    }
  ]
});
