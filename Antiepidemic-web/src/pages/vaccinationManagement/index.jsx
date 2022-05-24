
import AC from '@/components/AuthorizedComponent';
import IconFont from '@/components/IconFont';
import config from '@/utils/config';
import ProTable from '@ant-design/pro-table';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { UploadOutlined,DownloadOutlined } from '@ant-design/icons';
import { useRequest } from 'ahooks';
import { Button, Card, Input, Popconfirm, Tag, Tooltip,Row,Col,TreeSelect,Select,Table, message } from 'antd';
import React, { useRef, useState,useEffect } from 'react';
import { getPage,save,delOne,verifyOne,getVaccines} from './services/api';
import Modal from './Add';
const { Search } = Input;
const { Option } = Select;
import { useModel } from 'umi';
import qs from 'qs';
//疫苗接种管理
const VaccinationManagement = () => {
    const [loading, setLoading] = useState(false);
    const [data, setData] = useState([]);  //表格数据
    const [visible, setVisible] = useState(false); //新增浮层
    const [obj, setObj] = useState({});  //当前选中的用户
    const [page, setPage] = useState({
        current: 1,
        pageSize: 10,
        total: 0
      });
    const [searchCard,setSearchCard] = useState();
    const [searchStatus,setSearchStatus] = useState();
    const [searchType,setSearchType] = useState();
    const [refresh,setRefresh] = useState(false);
    const [typeList,setTypeList] = useState([]);  //疫苗品牌数据

    useEffect(()=>{
        getVaccines().then(res=>{
            setTypeList(res?.data || [])
        })
    },[])

    useEffect(() => {
        let params = {
            idCard:searchCard,
            type:searchStatus,
            brand:searchType,
            ...page,
        }
        setLoading(true);
        getPageRun(params);
    }, [page.current, page.pageSize,refresh,searchCard,searchStatus,searchType]);

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
                message.success(result?.data || "操作成功!");
                setVisible(false);
                UpdateCheckInfoRefresh();
            }
        },
    });

    const { run: delRun } = useRequest(delOne, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                message.success(result?.data || "操作成功!");
                UpdateCheckInfoRefresh();
            }
        },
    });

    const { run: verifyRun } = useRequest(verifyOne, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                message.success(result?.data || "操作成功!");
                UpdateCheckInfoRefresh();
            }
        },
    });

    const del = (id) => {
        delRun(id);
    }

    const verify = (id) => {
        verifyRun({id:id});
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
            title: '疫苗品牌',
            dataIndex: 'vaccinesName',
            width: 100,
            ellipsis: true,
        },
        {
            title: '第几针',
            dataIndex: 'inoculationNum',
            width: 100,
            ellipsis: true,
        },
        {
            title: '接种时间',
            dataIndex: 'time',
            width: 150,
            ellipsis: true,
        },
        {
            title: '接种地点',
            key: 'area',
            dataIndex: 'area',
            width: 350,
            ellipsis: true,
        },
        {
            title: '是否完成接种',
            key: 'finishTime',
            dataIndex: 'finishTime',
            width: 150,
            render: (text, row) => {
                let color =  (!text) ? 'volcano' : 'green';
                let name = (!text) ? '未完成' : '已完成';
                return (
                      <Tag color={color} key={name}>
                        {name}
                      </Tag>
                    )
            }
        },
        {
            title: '审核状态',
            key: 'status',
            dataIndex: 'status',
            width: 150,
            render: (text, row) => {
                let color =  (text == 0) ? 'volcano' : 'green';
                let name = (text == 0) ? '未审核' : '已审核';
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
                (row.status == 0) && <a onClick={() => edit(row)} target="_blank" rel="noopener noreferrer">编辑</a>,
                (row.status == 0) && <Tooltip title={'审核'}>
                    <div style={{display:'inline-block',marginLeft: '20px'}}>
                        <a target="_blank" rel="noopener noreferrer">
                        <Popconfirm
                        title="您确认要审核该疫苗接种信息么?"
                        onConfirm={() => verify(row.id)}
                        okText="是"
                        cancelText="否"
                        >
                        审核
                        </Popconfirm>
                        </a>
                    </div>
                </Tooltip>,
                <Tooltip title={'删除'}>
                      <div style={{display:'inline-block',marginLeft: '20px'}}>
                        <a target="_blank" rel="noopener noreferrer">
                        <Popconfirm
                          title="您确认要删除该疫苗接种信息么?"
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

    const onSearchCard = value => {
        setSearchCard(value);
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

    const downLoadParams = {
        idCard:searchCard,
        type:searchStatus,
        brand:searchType,
        'x-auth-token': localStorage.getItem('TCLOUD-AUTH-HEADER')
      }
    let downLoadHref = `/api/base-web-provider/vaccination/download?&${qs.stringify(downLoadParams)}`;

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
                                <Search key="搜索2" placeholder="按身份证号搜索" style={{ width: 200,marginRight: 20 }} onSearch={onSearchCard}
                                />
                                <div style={{marginRight: 20}}>
                                    <span>疫苗品牌：</span>
                                    <Select value={searchType} style={{ width: 130 }} onChange={onSearchType} allowClear>
                                        {typeList?.length > 0 && typeList.map(item => (
                                            <Option value={item?.id} key={item?.id}>{item?.name}</Option>
                                        ))}
                                    </Select>
                                </div>
                                <div style={{marginRight: 20}}>
                                    <span>审核状态：</span>
                                    <Select value={searchStatus} style={{ width: 130 }} onChange={onSearchStatus} allowClear>
                                        <Option value={0} key={0}>{"未审核"}</Option>
                                        <Option value={1} key={1}>{"已审核"}</Option>
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
                            { x: 1500 }
                          }
                    />
                    <Modal {...props} />
                </div>
            </Card>
        </PageHeaderWrapper>

    );
}
export default VaccinationManagement;
