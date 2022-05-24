import request from '@/utils/request';
import qs from 'qs';

// 获取隔离记录列表
export async function getPage(params) {
  return request(`/api/base-web-provider/isolation/page?${qs.stringify(params)}`);
}

// 新增或编辑隔离记录
export async function save(params) {
  return request('/api/base-web-provider/isolation/save', {
    method: 'POST',
    body: JSON.stringify(params),
  });
}

// 删除隔离记录
export async function delOne(id) {
  return request(`/api/base-web-provider/isolation/${id}`);
}

// 审核隔离记录
export async function verify(id) {
  return request(`/api/base-web-provider/isolationExam/exam?id=${id}`, {
    method: 'GET',
  });
}

//查询隔离记录的待审核信息
export async function detail(params) {
  return request(`/api/base-web-provider/isolationExam/get?${qs.stringify(params)}`);
}

// 接种人员下拉(下拉搜索)
export async function getPerson(params) {
  return request(`/api/base-auth-provider/person/getPerson?${qs.stringify(params)}`);
}