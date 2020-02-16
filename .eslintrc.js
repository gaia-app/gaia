module.exports = {
  root: true,

  env: {
    node: true,
  },

  extends: [
    'plugin:vue/recommended',
    '@vue/airbnb',
  ],

  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    quotes: ['error', 'single'],
    semi: ['error', 'always'],
    indent: ['error', 2],
    'max-len': ['error', 120],
    'import/prefer-default-export': 0,
    'vue/script-indent': ['error', 2, {baseIndent: 1, switchCase: 1, ignores: []}],
    'vue/html-indent': ['error', 2, {baseIndent: 1, switchCase: 1, ignores: []}],
    'padded-blocks': 'off'
  },

  parserOptions: {
    parser: 'babel-eslint',
  },

  overrides: [
    {
      files: ['*.vue'],
      rules: {
        indent: 'off',
      },
    },
  ],
};
