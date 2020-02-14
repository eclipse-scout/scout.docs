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
module.exports = (env, args) => {
  args.resDirArray = [
    'src/main/resources/WebContent',
    'node_modules/@eclipse-scout/core/res'];
  const config = baseConfig(env, args);

  config.entry = {
    'jswidgets': './src/main/js/jswidgets.js',
    'jswidgets-theme': './src/main/js/jswidgets-theme.less',
    'jswidgets-theme-dark': './src/main/js/jswidgets-theme-dark.less'
  };

  return config;
};
