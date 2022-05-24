import Vue from 'vue'
import dayjs from 'dayjs'
import moment from 'moment'

// 配置语言包
import 'dayjs/locale/zh-cn'
dayjs.locale('zh-cn')

// 时间日期的格式化处理
// dayjs().format('YYYY-MM-DD')

// 先对时间的处理,我们需要使用插件
import relativeTime from 'dayjs/plugin/relativeTime'
dayjs.extend(relativeTime) // 类似于jq的继承语法  
// 最主要的看文档来处理就好了

// 仅仅供模板使用 {{表达式（产生的结果）  | 过滤器函数 返回渲染的视图中}}
Vue.filter('relativeTime', options => {
  return dayjs().to(dayjs(options))
})

// 配置过滤器 处理格式化问题
Vue.filter('formatDate', options => {
  if (!options) {
    return ''
  }
  return moment(options).format('YYYY.MM.DD')
})
// 配置过滤器 处理格式化问题
Vue.filter('formatDateTime', options => {
  if (!options) {
    return ''
  }
  return moment(options).format('YYYY.MM.DD HH:mm')
})
