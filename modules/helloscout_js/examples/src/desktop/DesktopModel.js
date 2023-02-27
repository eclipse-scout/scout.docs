import {HelloForm} from "../greeting/HelloForm";
import {Desktop} from "./Desktop";

export default () => ({
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
