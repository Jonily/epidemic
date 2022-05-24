import request from '@/utils/request';
import qs from 'qs';

//人员出入列表数据
export async function visitPage(params) {
  return request(`/api/base-web-provider/visit/page?${qs.stringify(params)}`);
}

//查询某条出入数据
export async function detail(params) {
  return request(`/api/base-web-provider/visit/details?${qs.stringify(params)}`);
}

//删除某条出入信息
export async function visitDel(id) {
  return request(`/api/base-web-provider/visit/${id}`);
}