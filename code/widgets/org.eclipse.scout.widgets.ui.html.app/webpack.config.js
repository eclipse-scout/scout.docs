/*
 * Copyright (c) 2019 BSI Business Systems Integration AG.
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
const path = require('path');
const themePath = require.resolve('@eclipse-scout/demo-widgets/src/main/js/theme.less');
const resPath = path.join(path.dirname(require.resolve('@eclipse-scout/core')), 'res');
module.exports = (env, args) => {
  args.resDir = 'src/main/resources/WebContent';
  const config = baseConfig(env, args);

  config.entry = {
    'widgets': './index.js',
    'widgets-theme': themePath
  };
  config.plugins.push(
    new CopyPlugin([{
      // # Copy Scout web-resources
      from: resPath,
      to: '../res'
    }]));

  // TODO remove as soon as some more code is available (currently no chunk would be generated because the size is too small)
  config.optimization.splitChunks.cacheGroups.scout.minSize = 0;

  return config;
};
