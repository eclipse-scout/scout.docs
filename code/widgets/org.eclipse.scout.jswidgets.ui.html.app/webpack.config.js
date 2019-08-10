/*
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */

const baseConfig = require('@eclipse-scout/cli/scripts/webpack-defaults');
const mainModule = require.main;
const CopyPlugin = mainModule.require('copy-webpack-plugin');
module.exports = (env, args) => {
  args.resDir = 'src/main/resources/WebContent';
  const config = baseConfig(env, args);

  config.entry = {
    'jswidgets': './index.js',
    'jswidgets-theme': './node_modules/@eclipse-scout/demo-jswidgets/src/main/js/jswidgets/theme.less'
  };
  config.plugins.push(
    new CopyPlugin([{
      // # Copy Scout web-resources
      from: 'node_modules/@eclipse-scout/core/res',
      to: '../res'
    }]));

  // TODO remove as soon as some more code is available (currently no chunk would be generated because the size is too small)
  config.optimization.splitChunks.cacheGroups.scout.minSize = 0;

  return config;
};
