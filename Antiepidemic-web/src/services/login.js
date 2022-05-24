import request from '@/utils/request';
import config from '@/utils/config';
//登录
export async function fakeAccountLogin(params) {
  return request('/api/base-auth-provider/manage/login', {   
    method: 'POST',
    data: params,
  });
}
//退出登入
export async function outLogin() {
  return request('/api/base-auth-provider/manage/logout', {
    method: 'POST',
  });
}


