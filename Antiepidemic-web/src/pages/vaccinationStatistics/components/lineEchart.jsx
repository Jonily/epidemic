 
import React, { Component, useEffect, useState } from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { Card, Select, DatePicker, Empty, ArrowLeftOutlined, Button,message,Breadcrumb} from 'antd';
import { useRequest } from 'ahooks';
import { useModel } from 'umi';
import * as echarts from 'echarts';
import config from '@/utils/config';
//折线图
const LineEchart = ({echartData,chartId,unit}) => {
    let myChart;

  useEffect(() => {
    let xAxisArr = echartData?.map(item=>item?.name)
    initalECharts(xAxisArr);
  }, [echartData]);

  //获取最大值   即最大值向上取整  17300为20000，560为600
  const getMax = (value) => {
    let maxInt = (value + '')?.split('.')?.[0];
    let length = (maxInt + '')?.length;
    let tenSize = Math.pow(10,length - 1);
    let num = maxInt/tenSize;
    let maxNum = Math.ceil(num);
    let max = (maxNum == num ? (maxNum + 1) : maxNum) * tenSize;
    return max;
  }

  const initalECharts = (xAxisArr) => {
    // 基于准备好的dom，初始化echarts实例
    myChart = echarts.init(document.getElementById(chartId));
    var option = {
      color:config?.color,
      tooltip: {
        trigger: 'item',
        formatter: (params)=>{
          let value = params?.value;
          value = (value + '')?.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
              {/* .replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')   每千分位一个逗号 */}
          return params?.name + ':' + value + unit;
        }
      },
      grid: {
        left: '8%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'category',
        data: xAxisArr,
        axisLabel: {
          interval: 0,
          rotate: 40,
          margin: 20,
          align:'center',
          verticalAlign:'top'
        },
        axisTick: {
          alignWithLabel: true
        }
      },
      yAxis: {
        type: 'value',
        name: unit,
        nameGap: 25,  // 表现为上下位置
        nameTextStyle: {
          padding: [0, 110, -31, 0]
        },
        nameLocation: 'end',
        splitNumber:4,
        minInterval: 1,
        max: (v) => {
          let max = getMax(v.max);
          // return (v.max-v.min)/5 + v.max
          // return (v.max)/5 + v.max
          return max;
        },
        axisLabel:{
          formatter: function (value, index) {
            return (value + '')?.replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,');
          }
        }
      },
      series: [
        {
          type: 'line',
          data: echartData||[],
        }
      ]
    };
    myChart.setOption(option);
  };
  
  return (
    <Card bordered={false} bodyStyle={{padding:'0px'}}>
      <div id={chartId} style={{width:'100%',height:350}}/> 
    </Card>
  );
};

export default LineEchart;
