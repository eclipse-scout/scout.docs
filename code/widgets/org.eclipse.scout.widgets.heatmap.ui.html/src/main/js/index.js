export {default as HeatmapField} from './heatmap/HeatmapField';
export {default as HeatmapFieldAdapter} from './heatmap/HeatmapFieldAdapter';
export {default as HeatmapFieldLayout} from './heatmap/HeatmapFieldLayout';
export {default as simpleheat} from './heatmap/leaflet-heat';

import * as self from './index.js';

export default self;
window.widgets = Object.assign(window.widgets || {}, self);
