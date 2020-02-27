const path = require('path');
const merge = require('webpack-merge');

const webpackProductionConfig = require('./webpack.prod.js');
const exoServerPath = "/home/ali/work/binaries/5.3/ideation/platform-5.3.0-M08";
const app = 'ideation-vue-portlets';

module.exports = merge(webpackProductionConfig, {
  output: {
    path: path.resolve(`${exoServerPath}/webapps/${app}/`),
    filename: 'js/[name].bundle.js'
  }
});