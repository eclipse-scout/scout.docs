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
const CleanWebpackPlugin = require('clean-webpack-plugin');
const CopyPlugin = require('copy-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const OptimizeCssAssetsPlugin = require('optimize-css-assets-webpack-plugin');
const WebpackShellPlugin = require('webpack-shell-plugin');
const TerserPlugin = require('terser-webpack-plugin');

let path = require('path');
let webpack = require('webpack');

module.exports = (env, args) => {
  let devMode = args.mode !== 'production';
  let jsFilename = devMode ? '[name].js' : '[name]-[contenthash].min.js';
  let cssFilename = devMode ? '[name].css' : '[name]-[contenthash].min.css';
  let outSubDir = devMode ? 'dev' : 'prod';
  console.log('Webpack mode:', args.mode);

  return {
    target: 'web',
    mode: 'none',
    devtool: devMode ? 'inline-module-source-map' : undefined,
    /* ------------------------------------------------------
     * + Entry                                              +
     * ------------------------------------------------------ */
    entry: {
      'widgets-app': './index.js',
      'widgets-theme': './src/widgets/widgets-theme.less',
      'widgets-theme-dark': './src/widgets/widgets-theme-dark.less'
    },
    /* ------------------------------------------------------
     * + Output                                             +
     * ------------------------------------------------------ */
    output: {
      filename: jsFilename,
      path: path.join(__dirname, 'dist', outSubDir),
      libraryTarget: 'umd',
      libraryExport: 'default',
      chunkFilename: jsFilename,
      umdNamedDefine: true
    },
    performance: {
      hints: false
    },
    /* ------------------------------------------------------
     * + Optimization                                       +
     * ------------------------------------------------------ */
    optimization: {
      // # Split Chunks
      // Note: we don't define jQuery and Eclipse Scout as 'externals', since we want to bundle
      // them with our code and also provide minify, content-hash etc. for these libraries
      splitChunks: {
        chunks: 'all',
        cacheGroups: {
          // # Eclipse Scout
          scout: {
            test: /.*[\\/]eclipse-scout[\\/]/,
            name: 'eclipse-scout',
            priority: -5,
            reuseExistingChunk: true
          },
          //# jQuery
          jquery: {
            test: /.*[\\/]jquery[\\/]/,
            name: 'jquery',
            priority: -1,
            reuseExistingChunk: true
          }
        }
      },
      minimizer: [
        // # Minify CSS
        // Used to minify CSS assets (by default, run when mode is 'production')
        // see: https://github.com/NMFR/optimize-css-assets-webpack-plugin
        new OptimizeCssAssetsPlugin({
          assetNameRegExp: /\.min\.css$/g,
          cssProcessorPluginOptions: {
            preset: ['default', { discardComments: { removeAll: true } }],
          },
        }),
        // # Minify JS
        new TerserPlugin({
          test: /\.js(\?.*)?$/i,
          sourceMap: devMode,
          cache: true,
          parallel: true
        })
      ]
    },
    /* ------------------------------------------------------
     * + Module                                             +
     * ------------------------------------------------------ */
    module: {
      rules: [{
        test: /\.less$/,
        use: [{
          // Extracts CSS into separate files. It creates a CSS file per JS file which contains CSS.
          // It supports On-Demand-Loading of CSS and SourceMaps.
          // see: https://webpack.js.org/plugins/mini-css-extract-plugin/
          //
          // TODO [awe] toolstack: discuss with MVI - unnecessary .js files
          // Note: this creates some useless *.js files, like dark-theme.js
          // This seems to be an issue in webpack, workaround is to remove the files later
          // see: https://github.com/webpack-contrib/mini-css-extract-plugin/issues/151
          loader: MiniCssExtractPlugin.loader
        }, {
          // Interprets @import and url() like import/require() and will resolve them.
          // see: https://webpack.js.org/loaders/css-loader/
          loader: 'css-loader',
          options: {
            sourceMap: devMode,
            modules: false, // We don't want to work with CSS modules
            url: false      // Don't resolve URLs in LESS, because relative path does not match /res/fonts
          }
        }, {
          // Compiles Less to CSS.
          // see: https://webpack.js.org/loaders/less-loader/
          loader: 'less-loader',
          options: {
            sourceMap: devMode
          }
        }]
      }, {
        // # Babel
        test: /\.m?js$/,
        use: {
          loader: 'babel-loader',
          options: {
            compact: false,
            sourceMaps: devMode ? 'inline' : undefined,
            plugins: ['@babel/plugin-transform-object-assign', '@babel/proposal-class-properties', '@babel/proposal-object-rest-spread'],
            ignore: [/node_modules\/(?!@eclipse-scout)/], // Don't transpile node_modules except for eclipse-scout
            presets: [
              ['@babel/preset-env', {
                debug: true,
                targets: {
                  firefox: '35',
                  chrome: '40',
                  ie: '11'
                }
              }]
            ]
          }
        }
      }]
    },
    /* ------------------------------------------------------
     * + Plugins                                            +
     * ------------------------------------------------------ */
    plugins: [
      new MiniCssExtractPlugin({
        filename: cssFilename
      }),
      // # Clean dist/ folder
      // see: https://webpack.js.org/guides/output-management/#cleaning-up-the-dist-folder
      new CleanWebpackPlugin(),
      // see: https://www.npmjs.com/package/webpack-shell-plugin
      new WebpackShellPlugin({
        onBuildEnd: ['node post-build.js ' + args.mode]
      }),
      // # Copy resources
      // https://www.npmjs.com/package/copy-webpack-plugin
      new CopyPlugin([{
        // # Copy Scout web-resources
        from: 'node_modules/@eclipse-scout/eclipse-scout/res',
        to: '../res/'
      }, {
        // # Copy static web-resources
        from: 'res',
        to: '../res/'
      }]),
      // # Shows progress information in the console
      new webpack.ProgressPlugin()
    ]
  };
};
