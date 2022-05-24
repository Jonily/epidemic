import config from '@/utils/config';
import { Form, Input, message, Modal, Radio, Select, TreeSelect,DatePicker } from 'antd';
import React, { useEffect, useState } from 'react';
import moment from 'moment';
import {getPerson} from './services/api';

const { TextArea } = Input;
const AddModal = ({ obj = {}, visible, onCancel, onSubmit,typeList}) => {
    const [userList, setUserList] = useState([]);  //用户列表
    const [form] = Form.useForm();

    useEffect(()=>{
        getPerson({}).then(res=>{
            setUserList(res?.data || [])
        })
    },[])

    useEffect(() => {
        if (visible) {
            form.setFieldsValue({
                ...obj,
                time:moment(obj?.time)
            });
        }
    }, [visible])

    const onOk = () => {
        form.validateFields()
            .then(values => {
                let params = {
                    ...values,
                    time: values?.time && moment(values?.time).format('YYYY-MM-DD') || ''
                }
                clear();
                onSubmit(values);
            }).catch(info => {
                console.log('Validate Failed:', info);
            });
    };
    const close = () => {
        clear();
        onCancel();
    };

    const clear = () => {
        form.setFieldsValue({
            personName: '',
            type: '',
            time: '',
            origin: '',
            describtion:'',
            id: '',
        })
    }

    return (
        <Modal
            visible={visible}
            title={`${obj.id ? '编辑隔离信息' : '新增隔离信息'}`}
            onCancel={() => close()}
            width={700}
            okText="提交"
            cancelText="取消"
            onOk={() => onOk()}
        >
            <Form
                form={form}
                layout="horizontal"
                name="userForm"
        >
              <Form.Item
                    {...config.modalFormItemLayout}
                    label="姓名"
                    name="idCard"
                    rules={[
                        {
                            required: true,
                            message: '请选择姓名',
                        },
                    ]}
                >
                    <Select placeholder="请选择接种人姓名" disabled={obj.id ? true : false} 
                        showSearch
                        allowClear
                        optionFilterProp="children"
                        filterOption={(input, option) =>
                        option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                        }
                    >
                        {userList?.length > 0 && userList.map(item => (
                            <Option value={item?.idCard} key={item?.id}>{item?.name + '(' + item?.idCard?.substring(14,18) + ')'}</Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="隔离类型"
                    name="type"
                    rules={[
                        { required: true, message: '请选择隔离类型' }
                    ]}
                >
                    <Select placeholder="请选择隔离类型">
                        {typeList?.length > 0 && typeList.map(item => (
                            <Option value={item?.id} key={item?.id}>{item?.name}</Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="开始隔离时间"
                    name="time"
                    rules={[
                        {
                            required: true,
                            message: '请选择开始隔离时间',
                        },
                    ]}
                >
                    <DatePicker 
                        format="YYYY-MM-DD"
                        style={{width:'100%'}}
                    />
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="隔离地点"
                    name="origin"
                    rules={[
                        {
                            required: true,
                            message: '请输入隔离地点',
                        },
                    ]}
                >
                    <Input placeholder="请输入隔离地点"/>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="备注"
                    name="describtion"
                >
                    <Input placeholder="请输入备注"/>
                </Form.Item>
                <Form.Item
                    name="id"
                    noStyle
                >
                    <Input type="hidden" value={obj.id} />
                </Form.Item>
            </Form>
        </Modal>
    );
};

export default AddModal;
