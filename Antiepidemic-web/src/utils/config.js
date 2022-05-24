module.exports = {
  appId: 'community-antiepidemic-provider',
  title: '社区防疫云平台',
  paginationDisplay: {
    showSizeChanger: true,
    showQuickJumper: true,
    showTotal: (total) => `共 ${total} 条`,
    pageSize: 10,
    pageSizeOptions: [5, 10, 20, 50, 100],
  },
  modalFormItemLayout: {
    labelCol: {
      span: 6,
    },
    wrapperCol: {
      span: 14,
    },
  }, // 表单布局
  exp1: /^[A-Za-z0-9_\-\u4e00-\u9fa5]+$/, //字母数字下划线中文
  exp2: /^[A-Za-z0-9_]+$/, //字母数字下划线
  exp3: /^[a-zA-Z][a-zA-Z0-9_]*$/,
  exp4: /^[0-9A-Za-z]+$/, //字母数字
  exp5: /^[A-Za-z0-9\u4e00-\u9fa5]+$/, //字母数字汉字
  exp6: /^[A-Za-z0-9_\-]+$/, //字母数字下划线中文
  phone: /^1[123456789]\d{9}$/, //手机号码
  editColor: '#87d068',
  deleteColor: '#f50',
  otherColor: '#108ee9',
  disabledColor: '#BEBEBE',
  successColor: '#87d068',
  failColor: '#f50',
  seize: 'small',
  color: [
    '#f63e44',
    '#56aece',
    '#7ed262',
    '#dec662',
    '#9546ba',
    '#f88716',
    '#418cd9',
    '#74b360',
    '#bece56',
    '#25b643',
    '#7744a6',
    '#cf653b',
    '#5675ce',
    '#eeb741',
    '#634cc4',
    '#d3523d',
    '#97ce56',
    '#e16274',
    '#edd953',
    '#56a8ce',
    '#c24a3e',
    '#4ec241',
    '#d2579d',
    '#3c5dbc',
    '#d76d4d',
    '#8dbf6c',
    '#3779c4',
    '#dca53c',
    '#8349cc',
    '#41c29c',
    '#e16262',
    '#35a8bd',
  ]
};
