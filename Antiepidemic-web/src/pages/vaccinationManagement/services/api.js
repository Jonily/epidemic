import request from '@/utils/request';
import qs from 'qs';

// 获取疫苗接种信息
export async function getPage(params) {
  return request(`/api/base-web-provider/vaccination/page?${qs.stringify(params)}`);
}

// 新增或编辑疫苗接种信息
export async function save(params) {
  return request('/api/base-web-provider/vaccination/save', {
    method: 'POST',
    body: JSON.stringify(params),
  });
}

// 删除疫苗接种信息
export async function delOne(id) {
  return request(`/api/base-web-provider/vaccination/${id}`);
}

// 审核疫苗接种信息
export async function verifyOne(params) {
  return request(`/api/base-web-provider/vaccination/examine?${qs.stringify(params)}`, {
    method: 'GET',
  });
}

// 接种人员下拉(下拉搜索)
export async function getPerson(params) {
  return request(`/api/base-auth-provider/person/getPerson?${qs.stringify(params)}`);
}

// 疫苗品牌下拉接口
export async function getVaccines() {
  return request(`/api/base-web-provider/vaccines/list`);
}

// 根据疫苗品牌获取针数列表(先选品牌)
export async function getNum(params) {
  return request(`/api/base-web-provider/vaccines/getNum?${qs.stringify(params)}`);
}