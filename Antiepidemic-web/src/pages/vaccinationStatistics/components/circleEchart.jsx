import { useEffect, useState } from 'react';
import * as echarts from 'echarts';
import config from '@/utils/config';
//圆环饼图
const circleEchart = ({echartData=[], id, unit='吨'}) => {
  let currentLegend;
  let curTotal = 0;
  
  const pieEchart = () => {
    let chartDom = document.getElementById(id)
    let total = 0;
    echartData.forEach(item => {
      total+=Number(item.value)
    })
    let option = {
      title: {
        show:false
      },
      tooltip: {
        trigger: 'item',
        formatter: '{b} : {c} '+ unit +' ( {d}% )'
      },
      legend: {
        left:'50%',
        top:'middle',
        height:'53%',
        itemWidth: 6,
        itemHeight: 9,//改变圆圈大小
        icon: 'circle',
        type: 'scroll',
        orient: 'vertical',
        textStyle: { //图例文字的样式
          fontSize: 14
        },
        formatter: function (name) {
          if(name?.length > 20) return name.substring(0,20) + '...';
          return name
        },
        // tooltip: {
        //     show: true
        // }
      },
      series: [
        {
          name: 'pie',
          zlevel: 1,
          type: 'pie',
          radius: ['45%', '80%'],
          center: ['30%', '52%'],
          stillShowZeroSum: false,
          avoidLabelOverlap: false,
          label: {
            normal: {
              show: true,
              padding: 3,
              position: 'center',
              fontWeight: 'bolder',
              formatter: (v) => {
                let arr = ["{a|" + '总计'+'('+unit+')' + "}", "{b|"+total.toFixed(2)+"}"]
                return arr.join("\n");
              },
              rich: {
                a: {
                  color: '#999',
                  lineHeight: 20,
                  align: 'center'
                },
                b: {
                  fontSize: 16,
                  lineHeight: 30,
                  fontWeight:'bold',
                },
              },
              backgroundColor: '#fff',
            },
            emphasis: {
              show: true,
            }
          },
          color: config?.color,
          data: echartData || []
        }
      ]
    };
    let myChart = echarts.init(chartDom)
    if (myChart) {
      myChart.on('legendselectchanged', (e) => {
        var option = myChart.getOption();
        let obj = e.selected;
        let newObj = {}
        if(e.name == currentLegend){//不选中图例
          for (let key in obj) {
            newObj[key] = true;
          }
          currentLegend = '';
          curTotal = total.toFixed(2);
        }else{ //选中图例
          for (let key in obj) {
            if (e.name == key) {
              newObj[key] = true;
            } else {
              newObj[key] = false;
            }
          }
          currentLegend = e.name;
          curTotal = echartData?.find(item=>item?.name == e.name)?.value;
        }
        option.legend[0].selected = newObj
        option.series[0].label.formatter = (name)=>{
          let totals = (Number(curTotal) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
          let arr=[`{a|总计(${unit})}`,"{b|"+(totals||'0')+"}"]
          return arr.join("\n");
        },
        myChart.setOption(option)
      })
      option && myChart.setOption(option);
    }
  }

  useEffect(() => {
    if (echartData?.length && id) {
      pieEchart()
    }
  },[echartData,id])

  return (
    <div style={{height:'300px',width:'100%'}} id={id}/>
  )
}
export default circleEchart