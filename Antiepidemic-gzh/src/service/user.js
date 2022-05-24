/**
 * 评论请求模块
 */
// import http from '../plugins/http';
import http from '../plugins/umi-request'


const sendCode = params => {
  return http.post('/api/csi-auth-provider/sms/send', { data: params })
};
const login = params => {
  return http.post('/api/csi-auth-provider/auth/smsLogin', { data: params })
};
export {
  sendCode, login
}