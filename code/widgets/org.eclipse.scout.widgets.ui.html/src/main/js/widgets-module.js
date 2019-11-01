export { default as ExampleBeanField } from './ExampleBeanField';
export { default as ExampleBeanColumn } from './ExampleBeanColumn';
export { default as WidgetsOutline } from './WidgetsOutline';
export { default as WidgetsOutlineAdapter } from './WidgetsOutlineAdapter';
export { default as WidgetsTileOutlineOverview } from './WidgetsTileOutlineOverview';
export { default as CustomTile } from './tile/CustomTile';
export { default as CustomTileAdapter } from './tile/CustomTileAdapter';
export { default as ChartField } from './custom/chartfield/ChartField';
export { default as ChartFieldAdapter } from './custom/chartfield/ChartFieldAdapter';

import * as self from './index.js';
export default self;
window.widgets = Object.assign(window.widgets || {}, self);
