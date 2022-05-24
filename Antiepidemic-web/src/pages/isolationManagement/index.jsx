
import AC from '@/components/AuthorizedComponent';
import IconFont from '@/components/IconFont';
import config from '@/utils/config';
import ProTable from '@ant-design/pro-table';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { UploadOutlined,DownloadOutlined } from '@ant-design/icons';
import { useRequest } from 'ahooks';
import { Button, Card, Input, Popconfirm, Tag, Tooltip,Row,Col,TreeSelect,Select,Table, message } from 'antd';
import React, { useRef, useState,useEffect } from 'react';
import { getPage,save,delOne,verify} from './services/api';
import Modal from './Add';
import Detail from './Detail';
const { Search } = Input;
const { Option } = Select;
import { useModel } from 'umi';
import qs from 'qs';
//隔离信息管理
const IsolationManagement = () => {
    const [loading, setLoading] = useState(false); 
    const [data, setData] = useState([{name:1,status:0}]);  //表格数据
    const [visible, setVisible] = useState(false); //新增浮层
    const [obj, setObj] = useState({});  //当前选中的用户
    const [detailVisible, setDetailVisible] = useState(false);
    const [page, setPage] = useState({
        current: 1,
        pageSize: 10,
        total: 0
      });
    const [searchName,setSearchName] = useState();
    const [searchStatus,setSearchStatus] = useState();
    const [searchType,setSearchType] = useState();
    const [refresh,setRefresh] = useState(false);
    const typeList = [{
        id:'1',
        name:'集中隔离'
    },{
        id:'2',
        name:'医院留观'
    },{
        id:'3',
        name:'社区管理'
    }]

    useEffect(() => {
        let params = {
            name:searchName,
            status:searchStatus,
            type:searchType,
            ...page,
        }
        setLoading(true);
        getPageRun(params);
    }, [page.current, page.pageSize,refresh,searchName,searchStatus,searchType]);

    const add = () => {
        setObj({});
        setVisible(true);
    }

    const edit = (obj) => {
        setObj(obj);
        setVisible(true);
    }

    const { run: getPageRun } = useRequest(getPage, {
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

    const { run: changeRun } = useRequest(save, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                message.success(result.data || "操作成功!");
                setVisible(false);
                UpdateCheckInfoRefresh();
            }
        },
    });

    const { run: delRun } = useRequest(delOne, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                message.success(result.data || "操作成功!");
                UpdateCheckInfoRefresh();
            }
        },
    });

    const { run: verifyRun } = useRequest(verify, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                message.success(result.data || "操作成功!");
                setDetailVisible(false);
                UpdateCheckInfoRefresh();
            }
        },
    });

    const del = (id) => {
        delRun(id);
    }

    const renderStatus = (text)=>{
        let color =  'green',name = '';
        if(text == 3){
            color =  'green',name = '社区管理';
        }
        else if(text == 2) {
            color='yellow';name='医院留观';
        }
        else if(text == 1) {
            color='red';name='集中隔离';
        }
        if(name) return (
            <Tag color={color} key={name}>
                {name}
            </Tag>
        )
        else{
            return '-'
        }
    }

    const columns = [
        {
            title: '姓名',
            dataIndex: 'personName',
            width: 100,
            ellipsis: true,
        },
        {
            title: '身份证号',
            dataIndex: 'idCard',
            width: 200,
            ellipsis: true,
        },
        {
            title: '隔离类型',
            dataIndex: 'type',
            width: 150,
            ellipsis: true,
            render: (text, row) => {
                return renderStatus(text);
            }
        },
        {
            title: '隔离地点',
            dataIndex: 'origin',
            width: 350,
            ellipsis: true,
        },
        {
            title: '本阶段开始时间',
            dataIndex: 'time',
            width: 200,
            ellipsis: true,
        },
        {
            title: '预计解除时间',
            key: 'planCloseTime',
            dataIndex: 'planCloseTime',
            width: 200,
            ellipsis: true,
        },
        {
            title: '是否已解除',
            key: 'closeTime',
            dataIndex: 'closeTime',
            width: 150,
            render: (text, row) => {
                let color =  (!text) ? 'volcano' : 'green';
                let name = (!text) ? '未解除' : '已解除';
                return (
                      <Tag color={color} key={name}>
                        {name}
                      </Tag>
                    )
            }
        },
        {
            title: '操作',
            valueType: 'option',
            width: 200,
            fixed:'right',
            render: (text, row, _, action) => [
                // <a onClick={() => edit(row)} target="_blank" rel="noopener noreferrer">编辑</a>,
                // exam字段：微信端有没有待审核记录，如果有，则exam=true，有待审核记录时，才展示审核按钮，可进行审核操作
                (row.exam) && <a onClick={() => { setDetailVisible(true), setObj(row) }} target="_blank" rel="noopener noreferrer" style={verityStyle}>审核</a>,
                <Tooltip title={'删除'}>
                      <div style={{display:'inline-block',marginLeft: '20px'}}>
                        <a target="_blank" rel="noopener noreferrer">
                        <Popconfirm
                          title="您确认要删除该隔离信息么?"
                          onConfirm={() => del(row.id)}
                          okText="是"
                          cancelText="否"
                        >
                          删除
                        </Popconfirm>
                        </a>
                      </div>
                </Tooltip>
            ],
        },
    ];

    const verityStyle={
        display: 'inline-block',
        border: '1px solid #91d5ff',
        padding: '5px 15px',
        borderRadius: '4px',
        color: '#1890ff',
        background: '#e6f7ff',
        borderColor: '#91d5ff',
    }
    
    const onSearchName = value => {
        setSearchName(value);
    };

    const onSearchType = (value) => {
        setSearchType(value);
    }

    const onSearchStatus = (value) => {
        setSearchStatus(value);
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

    const props = {
        visible,
        obj,
        typeList,
        onSubmit: (values) => {
            changeRun(values);
        },
        onCancel: () => {
            setVisible(false);
            setObj({});
        }
    }

    const dProps = {
        visible: detailVisible,
        id: obj?.id,
        typeList,
        onSubmit: (id) => {
            verifyRun(id);
        },
        onCancel: () => {
          setDetailVisible(false);
          setObj({});
        }
    }

    return (
        <PageHeaderWrapper>
            <Card className="contentMinHeight" bordered={false}>
                <div>
                    <Row style={{ marginBottom: 20 }}>
                        <Col span={24}>
                            <div style={{ display: "flex",}}>
                                <Button
                                    onClick={() => add()}
                                    style={{ marginRight: 20 }}
                                    type="primary"
                                    key={"新增"}
                                >
                                新增
                                </Button>
                                <Search key="搜索" placeholder="按姓名搜索" style={{ width: 200,marginRight: 20 }} onSearch={onSearchName}
                                />
                                <div style={{marginRight: 20}}>
                                    <span>隔离类型：</span>
                                    <Select value={searchType} style={{ width: 130 }} onChange={onSearchType} allowClear>
                                        {typeList?.length > 0 && typeList.map(item => (
                                            <Option value={item?.id} key={item?.id}>{item?.name}</Option>
                                        ))}
                                    </Select>
                                </div>
                                <div style={{marginRight: 20}}>
                                    <span>解除状态：</span>
                                    <Select value={searchStatus} style={{ width: 130 }} onChange={onSearchStatus} allowClear>
                                        <Option value={0} key={0}>{"未解除"}</Option>
                                        <Option value={1} key={1}>{"已解除"}</Option>
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
                            { x: 1550 }
                          }
                    />
                    <Modal {...props} />
                    <Detail {...dProps} />
                </div>
            </Card>
        </PageHeaderWrapper>
        
    );
}
export default IsolationManagement;