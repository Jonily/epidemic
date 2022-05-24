import Vue from 'vue'
import VueRouter from 'vue-router'
const Layout = () => import('../views/agricultural/Layout/index')
//社区防疫
const communityRegister = () => import('../views/community/register')
const communityHomePage = () => import('../views/community/homePage')
const communityIsolation = () => import('../views/community/cell/isolation.vue')
const communityIsolationExam = () => import('../views/community/cell/isolationExam.vue')

const communityVaccination = () => import('../views/community/cell/vaccination.vue')
const communityAccess = () => import('../views/community/cell/access.vue')
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'community',
    redirect: '/community/register',
    component: Layout,
    children: [
      {
        path: '/community/register',
        name: 'communityRegister',
        component: communityRegister
      },
      {
        path: '/community/homePage',
        name: 'communityHomePage',
        component: communityHomePage
      },
      {
        path: '/community/isolation',
        name: 'communityIsolation',
        component: communityIsolation
      },
      {
        path: '/community/isolationExam',
        name: 'communityIsolationExam',
        component: communityIsolationExam
      },
      {
        path: '/community/vaccination',
        name: 'communityVaccination',
        component: communityVaccination
      },
      {
        path: '/community/access',
        name: 'communityAccess',
        component: communityAccess
      },
    ]
  }

]


const router = new VueRouter({
  mode: 'hash',
  // base: '/csi/',  // 微信只有两个域名绑定，如果是走多应用
  routes,
})

export default router
