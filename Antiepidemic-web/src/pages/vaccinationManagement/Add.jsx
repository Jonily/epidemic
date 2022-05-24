import config from '@/utils/config';
import { Form, Input, message, Modal, Radio, Select, TreeSelect,DatePicker } from 'antd';
import React, { useEffect, useState } from 'react';
import moment from 'moment';
import {getPerson,getNum} from './services/api';

const { TextArea } = Input;
const AddModal = ({ obj = {}, visible, onCancel, onSubmit,typeList}) => {
    const [userList, setUserList] = useState([]);  //用户列表
    const [brand,setBrand] = useState('');  //当前选择的疫苗品牌
    const [frequencyList, setFrequencyList] = useState([]);  //第几针列表， 根据疫苗品牌联动
    const [form] = Form.useForm();

    useEffect(()=>{
        getPerson().then(res=>{
            setUserList(res?.data || [])
        })
    },[])

    useEffect(() => {
        if (visible) {
            form.setFieldsValue({
                ...obj,
                time:moment(obj?.time)
            });
            if(obj?.id) setBrand(obj?.brand);
        }
    }, [visible])

    useEffect(()=>{
        if(brand){
            getNum({id:brand}).then(res=>{
                setFrequencyList(res?.data || [])
            })
        }
    },[brand])

    const onOk = () => {
        form.validateFields()
            .then(values => {
                let params = {
                    ...values,
                    time: values?.time && moment(values?.time).format('YYYY-MM-DD') || ''
                }
                clear();
                onSubmit(params);
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
            idCard: '',
            brand: '',
            inoculationNum: '',
            time: '',
            area: '',
            id: '',
        })
    }

    const changeBrand = (value) => {
        setBrand(value)
        form.setFieldsValue({
            inoculationNum: '',
        })
    }

    return (
        <Modal
            visible={visible}
            title={`${obj.id ? '编辑接种信息' : '新增接种信息'}`}
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
                    label="接种人姓名"
                    name="idCard"
                    rules={[
                        {
                            required: true,
                            message: '请选择接种人姓名',
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
                    label="疫苗品牌"
                    name="brand"
                    rules={[
                        { required: true, message: '请选择疫苗品牌' }
                    ]}
                >
                    <Select placeholder="请选择疫苗品牌" onChange={changeBrand}>
                        {typeList?.length > 0 && typeList.map(item => (
                            <Option value={item?.id} key={item?.id}>{item?.name}</Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="第几针"
                    name="inoculationNum"
                    rules={[
                        {
                            required: true,
                            message: '请选择第几针',
                        }
                    ]}
                >
                    <Select placeholder="请选择第几针">
                        {frequencyList?.length > 0 && frequencyList.map(item => (
                            <Option value={item} key={item}>{item}</Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="接种时间"
                    name="time"
                    rules={[
                        {
                            required: true,
                            message: '请选择接种时间',
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
                    label="接种地址"
                    name="area"
                    rules={[
                        {
                            required: true,
                            message: '请输入接种地址',
                        },
                    ]}
                >
                    <Input placeholder="请输入接种地址"/>
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
