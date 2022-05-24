import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Vant, { Toast, Dialog, ImagePreview } from 'vant'
import 'amfe-flexible/index.js'
import './assets/iconfont/iconfont.css'

// 全局样式初始化
import 'vant/lib/index.css'
import './assets/style/index.scss'
Vue.config.productionTip = false
// Vant组件库
Vue.use(Vant).use(ImagePreview);
Vue.config.$toast = Toast
Vue.config.$dialog = Dialog
// 加载DyaJS
import './utils/dayjs'
// 中央事件总线
Vue.prototype.$store = store
//appid
Vue.prototype.$appid="wx6c9ff947def78452"

let BusCoenterLine = new Vue()
Vue.prototype.name_Scope = {
  BusCoenterLine
}
// mock数据
import '@/mock'
let ThatVue = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

export default ThatVue