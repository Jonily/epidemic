import React, { useState, useEffect, useRef } from 'react';
import { Button, DatePicker, Space, Card, Radio, Modal, Form, Input, message, Divider, InputNumber, Tooltip, Select, Popconfirm,Row ,Col,Table,Tag} from 'antd';
import { PlusOutlined, ExclamationCircleOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import moment from 'moment';
import { render } from 'react-dom';
import { useRequest } from 'ahooks';
import { history } from 'umi';
const { Search } = Input;
const { RangePicker } = DatePicker;

import { visitPage,visitDel } from './services/api';
import Detail from './Detail';
//社区人员出入管理
const AccessManagement = () => {
  const [loading, setLoading] = useState(false); 
  const [data, setData] = useState([]);  //表格数据
  const [detailVisible, setDetailVisible] = useState(false);
  const [row, setRow] = useState();
  const [page, setPage] = useState({
    current: 1,
    pageSize: 10,
    total: 0
  });
  const [startDate,setStartDate] = useState();
  const [endDate,setEndDate] = useState();
  const [searchCard,setSearchCard] = useState();
  const [searchType,setSearchType] = useState();
  const [refresh,setRefresh] = useState(false);

  useEffect(() => {
    let params = {
        idCard:searchCard,
        type:searchType,
        startDate,
        endDate,
        ...page,
    }
    setLoading(true);
    getPageRun(params);
}, [page.current, page.pageSize,refresh,searchCard,searchType,startDate,endDate]);

const { run: getPageRun } = useRequest(visitPage, {
  manual: true,
  onSuccess: (res, params) => {
      if (res && res.success) {
          setData(res?.data?.data);
          setPage((oldData) => {
            return {
              ...oldData,
              total: res.data.total
            }
          })
          setLoading(false)
        }
  },
});

const { run: delRun } = useRequest(visitDel, {
  manual: true,
  onSuccess: (result, params) => {
      if (result.success) {
          message.success(result.data || "操作成功!");
          UpdateCheckInfoRefresh();
      }
  },
});

const del = (id) => {
  delRun(id);
}

  const columns = [
    {
      title: '姓名',
      dataIndex: 'visitPersonName',
      width: 100,
    },
    {
      title: '身份证号',
      dataIndex: 'idCard',
      width: 200,
    },
    {
      title: '来源地址',
      dataIndex: 'fromAddress',
      width: 350,
    },
    {
      title: '目标地址',
      dataIndex: 'targetAddress',
      width: 350,
    },
    {
      title: '交通方式',
      dataIndex: 'fromModel',
      width: 100,
    },
    {
      title: '出入类型',
      dataIndex: 'type',
      width: 100,
      render: (text, row) => {
        let color =  (text == 0) ? 'green' : 'volcano';
        let name = (text == 0) ? '来访' : '离开';
        return (
              <Tag color={color} key={name}>
                {name}
              </Tag>
            )
      }
    },
    {
      title: '预计到达时间',
      dataIndex: 'planTime',
      width: 200,
      // render:(text,row)=>{
      //   return text ? (text + '时') : ''
      // }
    },
    {
      title: '创建时间',
      dataIndex: 'creatorTime',
      width: 200,
    },
    {
      title: '操作',
      valueType: 'option',
      width: 200,
      fixed:'right',
      render: (text, row, _, action) => [
        <a onClick={() => { setDetailVisible(true), setRow(row) }}>查看详情</a>,
          <Tooltip title={'删除'}>
                <div style={{display:'inline-block',marginLeft: '20px'}}>
                  <a target="_blank" rel="noopener noreferrer">
                  <Popconfirm
                    title="您确认要删除该出入信息么?"
                    onConfirm={() => del(row.id)}
                    okText="是"
                    cancelText="否"
                  >
                    删除
                  </Popconfirm>
                  </a>
                </div>
          </Tooltip>,
      ]
    }
  ]

  // 获取日期字段
  const onPicker=(value)=>{
    if(value){
      setStartDate(moment(value[0]).format('YYYY-MM-DD'))
      setEndDate((moment(value[1]).format('YYYY-MM-DD')))
    }else{
      setStartDate()
      setEndDate()
    }
  }

  const onSearchCard = value => {
      setSearchCard(value);
  };

  const onSearchType = (value) => {
    setSearchType(value);
  }

  const UpdateCheckInfoRefresh = () => {
      setRefresh(!refresh);
  };

  const pageChange = (page1, pageSize1) => {
      setPage({
          current: page1,
          pageSize:pageSize1,
          total:page.total
      });
  }

  const dProps = {
    visible: detailVisible,
    id: row?.id,
    onCancel: () => {
      setDetailVisible(false);
      setRow({});
    }
  }

  return (
    <PageHeaderWrapper>
        <Card className="contentMinHeight" bordered={false}>
            <div>
                <Row style={{ marginBottom: 20 }}>
                    <Col span={24}>
                        <div style={{ display: "flex",}}>
                            <div>
                              <span>选择时段：</span> 
                              <RangePicker
                                key={'time'}
                                style={{ marginRight: 20 }}
                                onChange={(value)=>onPicker(value)}
                                //  disabledTime 禁止选择以后的时间
                                disabledDate={(current) => current && current > moment().endOf('day')}
                              />
                            </div>
                            <Search key="搜索2" placeholder="按身份证号搜索" style={{ width: 200,marginRight: 20 }} onSearch={onSearchCard}
                            />
                            <div style={{marginRight: 20}}>
                                <span>出入类型：</span>
                                <Select value={searchType} style={{ width: 130 }} onChange={onSearchType} allowClear>
                                    <Option value={0} key={0}>{"来访"}</Option>
                                    <Option value={1} key={1}>{"离开"}</Option>
                                </Select>
                            </div>
                        </div>
                    </Col>
                </Row>
                <Table
                    bordered
                    dataSource={data}
                    columns={columns}
                    loading={loading}
                    rowKey={(record) => record.id}
                    pagination={{
                        current: page.current || 1,
                        pageSize: page.pageSize || 10,
                        total: page.total || 0,
                        onChange: pageChange,
                        showQuickJumper: true,
                        pageSizeOptions: ['10', '15', '20', '50', '100'],
                        showSizeChanger: true,
                        showTotal: () => `共${page.total}条数据`,
                    }}
                    scroll={
                        { x: 1800 }
                      }
                />
                <Detail {...dProps} />
            </div>
        </Card>
    </PageHeaderWrapper>
    
  );
}
export default AccessManagement;