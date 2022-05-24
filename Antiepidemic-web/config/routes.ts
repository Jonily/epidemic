export default [
  {
    path: '/',
    name: '首页',
    redirect: '/user/login',
  },
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [{
      name: 'login',
      path: '/user/login',
      component: './user/login',
    }],
  },
  {
    path: '/',
    component: '../layouts/BasicLayout',
    routes: [
      { path: '/', redirect: '/user/login' },
      {
        path: '/userManagement',
        name: '社区用户管理',
        component: './userManagement',
      },
      {
        path: '/vaccinationManagement',
        name: '疫苗接种管理',
        component: './vaccinationManagement',
      },
      {
        path: '/vaccinationStatistics',
        name: '接种情况统计',
        component: './vaccinationStatistics',
      },
      {
        path: '/accessManagement',
        name: '社区人员出入管理',
        component: './accessManagement',
      },
      {
        path: '/accessStatistics',
        name: '社区人员行程统计',
        component: './accessStatistics',
      },
      {
        path: '/isolationManagement',
        name: '隔离信息管理',
        component: './isolationManagement',
      },
    ]
  },
  
];
