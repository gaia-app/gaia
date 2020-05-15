const path = require('path');

module.exports = {
  stories: ['../src/main/stories/**/*.stories.js'],
  addons: [
    '@storybook/addon-docs',
    '@storybook/addon-viewport',
    // '@storybook/addon-controls',
    '@storybook/addon-backgrounds',
  ],
  webpackFinal: async (config, { configType }) => {
    // `configType` has a value of 'DEVELOPMENT' or 'PRODUCTION'
    // You can change the configuration based on that.
    // 'PRODUCTION' is used when building the static version of storybook.

    // Make whatever fine-grained changes you need
    config.resolve.alias = {
      ...config.resolve.alias,
      '@': path.resolve('src/main/client/app'),
    }

    // Return the altered config
    return config;
  },
};
