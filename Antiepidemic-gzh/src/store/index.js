import Vue from 'vue'
import Vuex from 'vuex'

// 封装抽离
import { getItem, setItem, removeItem } from '../utils/storage'

Vue.use(Vuex)

// 注入类型常量
import { tcloudId,communityUserInfo } from '../constants/vuex-type'

export default new Vuex.Store({
  state: {
    // 存储当前数据,结合使用vuex还有localStorage
    tcloudId: getItem(tcloudId),
    communityUserInfo: getItem(communityUserInfo),
  },
  mutations: {
    
  },
  actions: {
    setCommunityUserInfo(v, value) {
      v.state.communityUserInfo = value
      setItem(communityUserInfo, JSON.stringify(value))
    },
  },
  modules: {
  }
})
