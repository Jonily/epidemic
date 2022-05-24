import { GridContent } from '@ant-design/pro-layout';
import { getHeadInfo,getLeftPieInfo,getRightRankInfo,getLeftTableInfo,getRightTableInfo } from './services/api';
import { Col, Row, Select, TreeSelect, Space,Card,Table,Tooltip } from 'antd';
import React, { Suspense, useEffect, useState } from 'react';
import CircleEchart from '@/pages/vaccinationStatistics/components/circleEchart';
import TopCard from '@/pages/vaccinationStatistics/components/TopCard';
import styles from '@/pages/vaccinationStatistics/components/index.less';
import style from './index.less';
//疫苗接种情况统计分析
const VaccinationStatistics = () => {
  const [headInfoData, setHeadInfoData] = useState();    //顶部四个卡片数据
  const [leftPieData, setLeftPieData] = useState([]);   //左侧饼图数据信息
  const [rightRankData, setRightRankData] = useState([]);  //右侧饼图数据信息
  const [leftTableData, setLeftTableData] = useState([]);  //底部左侧表格数据信息
  const [rightTableData, setRightTableData] = useState([]);  //底部右侧表格数据信息

  useEffect(() => {
    getHeadInfo({}).then((res) => {
      if (res && res.success) {
        setHeadInfoData(res.data);
      }
    });
    getLeftPieInfo({}).then((res) => {
      if (res && res.success) {
        setLeftPieData(res.data);
      }
    });
    getRightRankInfo({}).then((res) => {
      if (res && res.success) {
        setRightRankData(res.data);
      }
    });
    getLeftTableInfo({}).then((res) => {
      if (res && res.success) {
        setLeftTableData(res.data);
      }
    });
    getRightTableInfo({}).then((res) => {
      if (res && res.success) {
        setRightTableData(res.data);
      }
    });
  }, []);

  const leftPieProps = {
    id: 'leftPie',
    echartData: leftPieData,
    unit: '次',
  }

  const basicColumns = [
    {
      title: '排名',
      dataIndex: 'id',
      key: 'id',
      width: "15%",
      render: (text, record, index) => {
        if (index < 3) {
          return <span className={`${style.rankingItemNumber} ${style.active}`}>{index + 1}</span>;
        }
        return <span className={`${style.rankingItemNumber} ${style.normal}`}>{index + 1}</span>;
      },
    },
    {
      title: '姓名',
      dataIndex: 'name',
      key: 'name',
      ellipsis: {
        showTitle: false,
      },
      width: "20%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
    {
      title: '身份证号',
      dataIndex: 'idCard',
      key: 'idCard',
      ellipsis: {
        showTitle: false,
      },
      width: "40%",
      render: (text, record) => {
        let showText = text?.length == 18 ? text.substring(0, 6) + '********' + text.substring(14,18) : text;
        return <Tooltip>{showText}</Tooltip>
      }
    },
    {
      title: '次数',
      dataIndex: 'count',
      key: 'count',
      ellipsis: {
        showTitle: false,
      },
      width: "25%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
  ];

  const basicColumns2 = [
    {
      title: '序号',
      dataIndex: 'id',
      key: 'id',
      width: "15%",
      render: (text, record, index) => {
        if (index < 3) {
          return <span className={`${style.rankingItemNumber} ${style.active}`}>{index + 1}</span>;
        }
        return <span className={`${style.rankingItemNumber} ${style.normal}`}>{index + 1}</span>;
      },
    },
    {
      title: '姓名',
      dataIndex: 'visitPersonName',
      key: 'visitPersonName',
      ellipsis: {
        showTitle: false,
      },
      width: "20%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
    {
      title: '来源地址',
      dataIndex: 'fromAddress',
      key: 'fromAddress',
      ellipsis: {
        showTitle: false,
      },
      width: "40%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
    {
      title: '来访原因',
      dataIndex: 'reason',
      key: 'reason',
      ellipsis: {
        showTitle: false,
      },
      width: "25%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
  ];

  const basicColumns3 = [
    {
      title: '序号',
      dataIndex: 'id',
      key: 'id',
      width: "15%",
      render: (text, record, index) => {
        if (index < 3) {
          return <span className={`${style.rankingItemNumber} ${style.active}`}>{index + 1}</span>;
        }
        return <span className={`${style.rankingItemNumber} ${style.normal}`}>{index + 1}</span>;
      },
    },
    {
      title: '姓名',
      dataIndex: 'visitPersonName',
      key: 'visitPersonName',
      ellipsis: {
        showTitle: false,
      },
      width: "20%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
    {
      title: '目标地址',
      dataIndex: 'targetAddress',
      key: 'targetAddress',
      ellipsis: {
        showTitle: false,
      },
      width: "40%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
    {
      title: '离开原因',
      dataIndex: 'reason',
      key: 'reason',
      ellipsis: {
        showTitle: false,
      },
      width: "25%",
      render: (text, record) => {
        return (
          <Tooltip title={text}>{text}</Tooltip>
        )
      }
    },
  ];
  
  return (
    <GridContent style={{ overflow: 'hidden' }}>
      <React.Fragment>
        <div style={{ background: '#fff', marginBottom: 8, padding: '24px' }}>
          {<TopCard oneVal={headInfoData?.visitCount} twoVal={headInfoData?.tripCount} threeVal={headInfoData?.visitCountHistory} fourVal={headInfoData?.tripCountHistory}
          oneTitle={'今日新增来访行程总数'} twoTitle={'今日新增出行行程总数'} threeTitle={'历史来访行程总数'} fourTitle={'历史出行行程总数'}/>}
        </div>
        <Row gutter={8} style={{marginBottom:8}}>
          <Col xl={12} lg={24} md={24} sm={24} xs={24}>
            <Card
              className={styles.cellCard}
              bordered={false}
              title="出入类型统计"
              style={{ height: '400px', margin: 0 }}
            >
              <CircleEchart {...leftPieProps}/>
            </Card>
          </Col>
          <Col xl={12} lg={24} md={24} sm={24} xs={24}>
            <Card
              className={styles.cellCard}
              bordered={false}
              title="出入人员排行榜"
              style={{ height: '400px', margin: 0 }}
            >
              <Table
                size="small"
                pagination={false}
                dataSource={rightRankData}
                className={style.my_table}
                rowKey={(record,index)=>index}
                columns={[...basicColumns]}
                scroll={rightRankData?.length > 5 ? {y:200} : {}}
              />
            </Card>
          </Col>
        </Row>
        <Row gutter={8} style={{marginBottom:8}}>
          <Col xl={12} lg={24} md={24} sm={24} xs={24}>
            <Card
              className={styles.cellCard}
              bordered={false}
              title="今日来访人员名单"
              style={{ height: '400px', margin: 0 }}
            >
              <Table
                size="small"
                pagination={false}
                dataSource={leftTableData}
                className={style.my_table}
                rowKey={(record,index)=>index}
                columns={[...basicColumns2]}
                scroll={leftTableData?.length > 5 ? {y:200} : {}}
              />
            </Card>
          </Col>
          <Col xl={12} lg={24} md={24} sm={24} xs={24}>
            <Card
              className={styles.cellCard}
              bordered={false}
              title="今日出行人员名单"
              style={{ height: '400px', margin: 0 }}
            >
              <Table
                size="small"
                pagination={false}
                dataSource={rightTableData}
                className={style.my_table}
                rowKey={(record,index)=>index}
                columns={[...basicColumns3]}
                scroll={rightTableData?.length > 5 ? {y:200} : {}}
              />
            </Card>
          </Col>
        </Row>
      </React.Fragment>
    </GridContent>
  );
};

export default VaccinationStatistics;
