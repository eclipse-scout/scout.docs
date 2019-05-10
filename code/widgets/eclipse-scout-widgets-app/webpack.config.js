/*******************************************************************************
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
const baseConfig = require('@eclipse-scout/cli/scripts/webpack-defaults');
const mainModule = require.main;
const CopyPlugin = mainModule.require('copy-webpack-plugin');
module.exports = (env, args) => {
  let config = baseConfig(env, args);

  config.entry = {
    'widgets-app': './index.js',
    'widgets-theme': './src/widgets/widgets-theme.less',
    'widgets-theme-dark': './src/widgets/widgets-theme-dark.less'
  };
  config.plugins.push(
    new CopyPlugin([{
      // # Copy Scout web-resources
      from: 'node_modules/@eclipse-scout/eclipse-scout/res',
      to: '../res/'
    }]));

  return config;
};
