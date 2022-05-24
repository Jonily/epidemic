
import AC from '@/components/AuthorizedComponent';
import IconFont from '@/components/IconFont';
import config from '@/utils/config';
import ProTable from '@ant-design/pro-table';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { UploadOutlined,DownloadOutlined } from '@ant-design/icons';
import { useRequest } from 'ahooks';
import { Button, Card, Input, Popconfirm, Tag, Tooltip,Row,Col,TreeSelect,Select,Table, message } from 'antd';
import React, { useRef, useState,useEffect } from 'react';
import { usePage,save,userDel} from './services/api';
import Modal from './Add';
const { Search } = Input;
const { Option } = Select;
import { useModel } from 'umi';
import qs from 'qs';
//用户管理
const UserManagement = () => {
    const { initialState = {} } = useModel('@@initialState');
    const { currentUser = {} } = initialState;
    const [loading, setLoading] = useState(false); 
    const [data, setData] = useState([]);  //表格数据
    const [visible, setVisible] = useState(false); //新增浮层
    const [obj, setObj] = useState({});  //当前选中的用户
    const [page, setPage] = useState({
        current: 1,
        pageSize: 10,
        total: 0
      });
    const [searchName,setSearchName] = useState('');
    const [searchCard,setSearchCard] = useState('');
    const [searchStatus,setSearchStatus] = useState('');
    const [refresh,setRefresh] = useState(false);

    useEffect(() => {
        let params = {
            name:searchName,
            idCard:searchCard,
            codeStatus:searchStatus,
            ...page,
        }
        setLoading(true);
        getPageRun(params);
    }, [page.current, page.pageSize,refresh,searchName,searchCard,searchStatus]);

    const add = () => {
        setObj({});
        setVisible(true);
    }

    const edit = (obj) => {
        setObj(obj);
        setVisible(true);
    }

    const { run: getPageRun } = useRequest(usePage, {
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

    const { run: delRun } = useRequest(userDel, {
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

    const renderStatus = (text)=>{
        let color =  'green',name = '';
        if(text == 1){
            color =  'green',name = '绿码';
        }
        else if(text == 2) {
            color='yellow';name='黄码';
        }
        else if(text == 3) {
            color='red';name='红码';
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
            dataIndex: 'name',
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
            title: '手机号',
            dataIndex: 'phone',
            width: 150,
            ellipsis: true,
        },
        {
            title: '年龄',
            dataIndex: 'age',
            width: 100,
            ellipsis: true,
        },
        {
            title: '地址',
            dataIndex: 'showOrigin',
            width: 350,
            ellipsis: true,
        },
        {
            title: '健康码状态',
            key: 'codeStatus',
            dataIndex: 'codeStatus',
            width: 150,
            render: (text, row) => {
                return renderStatus(text);
            }
        },
        {
            title: '上一次健康码状态',
            key: 'lastCodeStatus',
            dataIndex: 'lastCodeStatus',
            width: 150,
            render: (text, row) => {
                return renderStatus(text);
            }
        },
        {
            title: '所处社区',
            dataIndex: 'deptName',
            width: 350,
            ellipsis: true,
        },
        {
            title: '操作',
            valueType: 'option',
            width: 200,
            fixed:'right',
            render: (text, row, _, action) => [
                <a onClick={() => edit(row)} target="_blank" rel="noopener noreferrer">编辑</a>,
                <Tooltip title={'删除'}>
                      <div style={{display:'inline-block',marginLeft: '20px'}}>
                        <a target="_blank" rel="noopener noreferrer">
                        <Popconfirm
                          title="您确认要删除该用户信息么?"
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
    
    const onSearchName = value => {
        setSearchName(value);
    };

    const onSearchCard = value => {
        setSearchCard(value);
    };

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
        onSubmit: (values) => {
            changeRun(values);
        },
        onCancel: () => {
            setVisible(false);
            setObj({});
        }
    }

    const downLoadParams = {
        name:searchName,
        idCard:searchCard,
        codeStatus:searchStatus,
        'x-auth-token': localStorage.getItem('TCLOUD-AUTH-HEADER')
      }
    let downLoadHref = `/api/base-auth-provider/person/download?&${qs.stringify(downLoadParams)}`;

    return (
        <PageHeaderWrapper>
            <Card className="contentMinHeight" bordered={false}>
                <div>
                    <Row style={{ marginBottom: 20 }}>
                        <Col span={17}>
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
                                <Search key="搜索2" placeholder="按身份证号搜索" style={{ width: 200,marginRight: 20 }} onSearch={onSearchCard}
                                />
                                <div style={{marginRight: 20}}>
                                    <span>健康码状态：</span>
                                    <Select value={searchStatus} style={{ width: 130 }} onChange={onSearchStatus} allowClear>
                                        <Option value={1} key={1}>{"绿码"}</Option>
                                        <Option value={2} key={2}>{"黄码"}</Option>
                                        <Option value={3} key={3}>{"红码"}</Option>
                                    </Select>
                                </div>
                            </div>
                        </Col>
                        <Col span={7}>
                            <div style={{ float: 'right' }}>
                                <Button style={{marginLeft: 10,marginRight:10}}
                                    href={downLoadHref}
                                >
                                < DownloadOutlined/>
                                    导出数据
                                </Button>
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
                            { x: 1750 }
                          }
                    />
                    <Modal {...props} />
                </div>
            </Card>
        </PageHeaderWrapper>
        
    );
}
export default UserManagement;