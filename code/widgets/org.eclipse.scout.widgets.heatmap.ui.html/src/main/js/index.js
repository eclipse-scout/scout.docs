import {ObjectFactory} from '@eclipse-scout/core';
import * as self from './index.js';

export {default as HeatmapField} from './heatmap/HeatmapField';
export {default as HeatmapFieldAdapter} from './heatmap/HeatmapFieldAdapter';
export {default as HeatmapFieldLayout} from './heatmap/HeatmapFieldLayout';
export {default as simpleheat} from './heatmap/leaflet-heat';

export default self;
ObjectFactory.get().registerNamespace('widgets', self);
