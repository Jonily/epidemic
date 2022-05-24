/**
 * request 网络请求工具
 * 更详细的 api 文档: https://github.com/umijs/umi-request
 */
import { stringify } from 'querystring';
import { extend } from 'umi-request';
import router from '../router'
import ThatVue from '../main'
import vuex from 'vuex'

export const getPageQuery = () => {
  const { href } = window.location;
  const qsIndex = href.indexOf('?');
  const sharpIndex = href.indexOf('#');

  if (qsIndex !== -1) {
    if (qsIndex > sharpIndex) {
      return parse(href.split('?')[1]);
    }
    return parse(href.slice(qsIndex + 1, sharpIndex));
  }
  return {};
};

const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};
/**
 * 异常处理程序
 */

const errorHandler = error => {
  const { response } = error;
  if (response && response.status) {
    // if (response.status === 401) {
    //   const { redirect } = getPageQuery(); // redirect
    //   if (window.location.pathname !== '/mobile/inquirer' && !redirect) {
    //     ThatVue.$toast('凭证已失效，请重新登录')
    //     router.replace('/mobile/inquirer')
    //   }
    //   // TOKEN_KEY
    // } else {
    //   const errorText = codeMessage[response.status] || response.statusText;
    //   const {
    //     status,
    //     url
    //   } = response;
    //   ThatVue.$toast(`服务器异常：${errorText}`)
    // }

  } else if (!response) {

    // notification.error({
    //   description: '您的网络发生异常，无法连接服务器',
    //   message: '网络异常',
    // });
    ThatVue.$toast(`您的网络发生异常，无法连接服务器`)

    return response;
  }

  return response;
};

/**
 * 配置request请求时的默认参数
 */
const request = extend({
  errorHandler,
  // 默认错误处理
  credentials: 'include', // 默认请求是否带上cookie
});

request.interceptors.request.use(async (url, options) => {
  const commonHeaders = {
    // 'Content-Type': 'application/json',
    'openId': localStorage.getItem('communityTcloudId') || "",
  };
  return ({
    url,
    options: {
      ...options,
      headers: { ...commonHeaders, ...options.headers },
    },
  });
});

//统一处理报错等相关信息
request.interceptors.response.use(async response => {
  const data = await response.clone().json();
  if (!data.success) {
    // if (response.status !== 401) {
    //   data.codeMessage && ThatVue.$toast(data.codeMessage)
    // }
    // if (response.status == 401) {
    //   ThatVue.$toast('凭证已失效，请重新登录')
    //   router.replace('/mobile/inquirer')
    // }
  }
  return response;
});
export default request;
