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
const themePath = require.resolve('@eclipse-scout/demo-jswidgets/src/main/js/theme.less');
const themeDarkPath = require.resolve('@eclipse-scout/demo-jswidgets/src/main/js/theme-dark.less');
module.exports = (env, args) => {
  args.resDirArray = [
    'src/main/resources/WebContent',
    'node_modules/@eclipse-scout/demo-jswidgets/src/main/resources/WebContent',
    'node_modules/@eclipse-scout/core/res'];
  const config = baseConfig(env, args);

  config.entry = {
    'jswidgets': './src/main/js/index.js',
    'jswidgets-theme': themePath,
    'jswidgets-theme-dark': themeDarkPath
  };

  // TODO remove as soon as some more code is available (currently no chunk would be generated because the size is too small)
  config.optimization.splitChunks.cacheGroups.scout.minSize = 0;

  return config;
};
