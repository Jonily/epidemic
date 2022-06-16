import { GridContent } from '@ant-design/pro-layout';
import { getHeadInfo,getLeftPieInfo,getRightPieInfo,getBottomLineInfo } from './services/api';
import { Col, Row, Select, TreeSelect, Space,Card } from 'antd';
import React, { Suspense, useEffect, useState } from 'react';
import CircleEchart from './components/circleEchart';
import TopCard from './components/TopCard';
import LineEchart from './components/lineEchart';
import styles from './components/index.less';
//疫苗接种情况统计分析
const VaccinationStatistics = () => {
  const [headInfoData, setHeadInfoData] = useState();    //顶部四个卡片数据
  const [leftPieData, setLeftPieData] = useState([]);   //左侧饼图数据信息
  const [rightPieData, setRightPieData] = useState([]);  //右侧饼图数据信息
  const [bottomLineData, setBottomLineData] = useState([]);  //底部折线图数据信息

  useEffect(() => {
    getHeadInfo({}).then((res) => {
      if (res && res.success) {
        setHeadInfoData(res.data);
      }
    });
    getLeftPieInfo({}).then((res) => {
      if (res && res.success) {
        let arr = [];
        let orginObj = res?.data || {};
        for(var i in orginObj){
          arr.push({
            name:i,
            value:orginObj[i]?.count,
            per:orginObj[i]?.per,
          })
        }
        setLeftPieData(arr);
      }
    });
    getRightPieInfo({}).then((res) => {
      if (res && res.success) {
        let arr = [];
        let orginObj = res?.data || {};
        for(var i in orginObj){
          arr.push({
            name:i,
            value:orginObj[i]?.count,
            per:orginObj[i]?.per,
          })
        }
        setRightPieData(arr);
      }
    });
    getBottomLineInfo({}).then((res) => {
      if (res && res.success) {
        let arr = [];
        let orginArr = res?.data || [];
        for(var i in orginArr){
          arr.push({
            name:orginArr[i]?.day,
            value:orginArr[i]?.count,
          })
        }
        setBottomLineData(arr);
      }
    });
  }, []);

  const leftPieProps = {
    id: 'leftPie',
    echartData: leftPieData,
    unit: '人',
  }

  const rightPieProps = {
    id: 'rightPie',
    echartData: rightPieData,
    unit: '人',
  }

  const bottomLineProps = {
    chartId: 'bottomLine',
    echartData: bottomLineData,
    unit: '人',
  }

  return (
    <GridContent style={{ overflow: 'hidden' }}>
      <React.Fragment>
        <div style={{ background: '#fff', marginBottom: 8, padding: '24px' }}>
          {<TopCard oneVal={headInfoData?.hisCount} twoVal={headInfoData?.hisFinCount} threeVal={headInfoData?.toDayCount} fourVal={headInfoData?.toDayFinCount}/>}
        </div>
        <Row gutter={8} style={{marginBottom:8}}>
          <Col xl={12} lg={24} md={24} sm={24} xs={24}>
            <Card
              className={styles.cellCard}
              bordered={false}
              title="未接种完成情况统计-人员年龄段分布"
              style={{ height: '400px', margin: 0 }}
            >
              <CircleEchart {...leftPieProps}/>
            </Card>
          </Col>
          <Col xl={12} lg={24} md={24} sm={24} xs={24}>
            <Card
              className={styles.cellCard}
              bordered={false}
              title="未接种完成情况统计-健康码分布"
              style={{ height: '400px', margin: 0 }}
            >
              <CircleEchart {...rightPieProps}/>
            </Card>
          </Col>
        </Row>
        <Row gutter={24}>
          <Col xl={24} lg={24} md={24} sm={24} xs={24}>
            <Card
              title="本月接种完成情况统计"
              bordered={false}
              className={styles.cellCard}
              style={{ height: '480px', margin: 0 }}
            >
              <LineEchart {...bottomLineProps}/>
            </Card>
          </Col>
        </Row>
      </React.Fragment>
    </GridContent>
  );
};

export default VaccinationStatistics;
