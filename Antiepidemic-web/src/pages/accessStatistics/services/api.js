import request from '@/utils/request';
import qs from 'qs';

// 获取顶部卡片数据信息
export async function getHeadInfo() {
  return request(`/api/base-web-provider/visit/statistic`);
}

// 获取左侧饼图数据信息
export async function getLeftPieInfo() {
  return request(`/api/base-web-provider/visit/inOut`);
}

// 获取右侧排行榜数据信息
export async function getRightRankInfo() {
  return request(`/api/base-web-provider/visit/ranking`);
}

// 获取底部左侧表格数据信息
export async function getLeftTableInfo() {
  return request(`/api/base-web-provider/visit/todayVisitList`, {
    method: 'GET',
  });
}

// 获取底部右侧表格数据信息
export async function getRightTableInfo() {
  return request(`/api/base-web-provider/visit/todayTripList`, {
    method: 'GET',
  });
}