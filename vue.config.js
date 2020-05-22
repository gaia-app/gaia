const path = require('path');
const CopyPlugin = require('copy-webpack-plugin');

module.exports = {
  css: {
    sourceMap: true,
  },
  publicPath: '/',
  outputDir: 'src/main/resources/public',
  assetsDir: 'assets',
  pages: {
    index: {
      entry: 'src/main/client/app/main.js',
      template: 'src/main/client/public/index.html',
      filename: 'index.html',
    },
  },
  configureWebpack: {
    performance: {
      maxAssetSize: 500000,
    },
    resolve: {
      alias: {
        '@': path.resolve('src/main/client/app'),
      },
    },
  },
  chainWebpack: (config) => {
    config
      .plugin('copy')
      .use(CopyPlugin, [{
        patterns: [{
          from: 'src/main/client/public',
          to: '',
          toType: 'dir',
        }]
      }]);
  },
  devServer: {
    port: 3200,
    overlay: {
      warnings: true,
      errors: true,
    },
    proxy: {
      '^/(api|auth|build-info)': {
        target: 'http://localhost:8080',
        ws: true,
        changeOrigin: false,
      },
    },
  },
};
