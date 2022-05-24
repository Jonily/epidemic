import request from '@/utils/request';

//获取登录人信息
export async function queryCurrent() {
  return request('/api/base-auth-provider/manage/get');
}


export async function changePwd(params) {
  return request('/api/user/changePassword', {
    method: 'POST',
    body: JSON.stringify(params),
  });
}


export async function save(params) {
  return request('/api/user/profile', {
    method: 'POST',
    body: JSON.stringify(params),
  });
}
