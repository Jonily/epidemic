module.exports = {
  devServer: {
    proxy: {
      '/api': {
       target: 'http://101.43.149.129:7002/',
        // 允许跨域
        changeOrigin: true,
        ws: true,
        pathRewrite: {
          '^/api': '/api'
        }
      },
    },
    // publicPath: '/csi'
  }
}
