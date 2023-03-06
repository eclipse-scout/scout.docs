const baseConfig = require('@eclipse-scout/cli/scripts/webpack-defaults');
module.exports = (env, args) => {
  args.resDirArray = [
    './res', 
    './node_modules/@eclipse-scout/core/res',
    './node_modules/@eclipse-scout/core/dist/locales.json',
    './node_modules/@eclipse-scout/core/dist/texts.json'
  ];
  const config = baseConfig(env, args);
  config.entry = {
    'helloworld': './src/helloworld.js',
    'helloworld-theme': './src/helloworld.less'
  };
  return config;
};
