import http from '../plugins/umi-request'
import qs from 'qs'

// oaOcMwpTZ9wM2tvGiPtCfe9N5EUs
export async function auth(params, headers) {
  return http(`/api/base-auth-provider/weChatUser/auth?${qs.stringify(params)}`, {
    method: 'GET',
    headers: {
      tcloudId: headers.openId || '',
    }
  });
}

//社区列表查询
const getDeptList = () => {
    return http('/api/base-auth-provider/deptInfo/get', {
      method: 'GET',
    })
}

//登录
const login = (params) => {
    return http('/api/base-auth-provider/account/login', {
      method: 'POST',
      data:params
    })
}

//疫苗品牌下拉接口
export function getVaccines() {
  return http('/api/base-web-provider/vaccines/list', {
    method: 'GET',
  })
}

// 根据疫苗品牌获取针数列表(先选品牌)
export function getNum(params) {
  return http(`/api/base-web-provider/vaccines/getNum?${qs.stringify(params)}`, {
    method: 'GET',
  });
}

//查询交通方式接口
export function getVisitTypes() {
  return http('/api/base-web-provider/visitType/list', {
    method: 'GET',
  })
}

//出入登记
export function accessSave(params) {
  return http('/api/base-web-provider/visit/save', {
    method: 'POST',
    data: params,
  });
}

//疫苗接种登记
export function vaccinationSave(params) {
  return http('/api/base-web-provider/vaccination/wxSave', {
    method: 'POST',
    data: params,
  });
}

//隔离登记
const isolationSave = (params) => {
  return http('/api/base-web-provider/isolation/wxSave', {
    method: 'POST',
    data: params,
  });
}

//隔离申请
const isolationExam = (params) => {
  return http('/api/base-web-provider/isolationExam/save', {
    method: 'POST',
    data: params,
  });
}

//查询行政区域（新增时需选择地址）
export function getMapList(params) {
  return http(`/api/base-auth-provider/region/select?${qs.stringify(params)}`);
}



export {
    getDeptList,login,isolationSave,isolationExam
}
