import config from '@/utils/config';
import { Form, Input, Modal } from 'antd';
import React from 'react';

const UserProfile = ({ user = {}, visible, onCancel, onSubmit, loading }) => {
    const [form] = Form.useForm();
    form.setFieldsValue({
        ...user,
    });
    const onOk = () => {
        form.validateFields()
            .then(values => {
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
            pid: undefined,
            username: '',
            name: '',
            isAdmin: undefined,
            roleId: undefined,
            email: '',
            phone: '',
            id: ''
        })
    }

    return (
        <Modal
            visible={visible}
            title='修改个人信息'
            onCancel={() => close()}
            confirmLoading={loading}
            width={700}
            okText="提交"
            cancelText="取消"
            onOk={() => onOk()}
        >
            <Form
                form={form}
                layout="horizontal"
                name="changePasswordForm"
            >

                <Form.Item
                    {...config.modalFormItemLayout}
                    label="姓名"
                    name="name"
                    rules={[
                        {
                            required: true,
                            message: '请输入姓名',
                        },
                    ]}
                >
                    <Input placeholder="请输入姓名" />
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="邮箱"
                    name="email"
                    rules={[
                        {
                            required: true,
                            message: '请输入邮箱',
                        },
                        {
                            type: 'email',
                            message: '请输入正确邮箱',
                        },
                    ]}
                >
                    <Input placeholder="请输入邮箱" />
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="手机号码"
                    name="phone"
                    rules={[
                        {
                            required: true,
                            message: '请输入手机号码',
                        },
                        {
                            pattern: config.phone,
                            message: '请输入正确的手机号码',
                        },
                    ]}
                >
                    <Input placeholder="请输入手机号码" />
                </Form.Item>
                <Form.Item
                    name="id"
                    noStyle
                >
                    <Input type="hidden" />
                </Form.Item>
            </Form>
        </Modal >
    );
};

export default UserProfile;
