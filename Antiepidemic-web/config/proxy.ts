export default {
    '/api/': {
      target: 'http://101.43.149.129:7002/', // 测试环境
      // target: 'http://localhost:8001', // 测试环境
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  }


