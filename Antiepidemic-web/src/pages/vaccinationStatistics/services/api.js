import request from '@/utils/request';
import qs from 'qs';

// 获取顶部卡片数据信息
export async function getHeadInfo() {
  return request(`/api/base-web-provider/vaccination/one`);
}

// 获取左侧饼图数据信息
export async function getLeftPieInfo() {
  return request(`/api/base-web-provider/vaccination/two`);
}

// 获取右侧饼图数据信息
export async function getRightPieInfo() {
  return request(`/api/base-web-provider/vaccination/three`);
}

// 获取底部折线图数据信息
export async function getBottomLineInfo() {
  return request(`/api/base-web-provider/vaccination/four`, {
    method: 'GET',
  });
}