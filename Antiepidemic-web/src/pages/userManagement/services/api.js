import request from '@/utils/request';
import qs from 'qs';
//分页查询用户列表
export async function usePage(params) {
  return request(`/api/base-auth-provider/person/page?${qs.stringify(params)}`);
}
//社区用户新增/编辑
export async function save(params) {
  return request('/api/base-auth-provider/person/save', {
    method: 'POST',
    body: JSON.stringify(params),
  });
}
//社区用户删除
export async function userDel(id) {
  return request(`/api/base-auth-provider/person/${id}`);
}

//社区列表查询
export async function getDeptList() {
  return request(`/api/base-auth-provider/deptInfo/get`);
}

//查询行政区域（新增时需选择地址）
export async function getAddress(id) {
  return request(`/api/base-auth-provider/region/select?id=${id}`);
}