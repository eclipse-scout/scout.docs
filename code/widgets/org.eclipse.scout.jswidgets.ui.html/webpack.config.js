/*
 * Copyright (c) BSI Business Systems Integration AG. All rights reserved.
 * http://www.bsiag.com/
 */
const baseConfig = require('@eclipse-scout/cli/scripts/webpack-defaults');
module.exports = (env, args) => {
  args.resDirArray = [];
  const config = baseConfig(env, args);
  return {
    entry: {
      'eclipse-scout-demo-jswidgets': './src/main/js/index.ts'
    },
    ...baseConfig.libraryConfig(config)
  };
};
