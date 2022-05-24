import IconFont from '@/components/IconFont';
import config from '@/utils/config';
import { Form, Input, Modal } from 'antd';
import React from 'react';

const { Password } = Input;
const ChangePassword = ({ visible, onCancel, onSubmit, loading }) => {
    const [form] = Form.useForm();

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
            title='修改密码'
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
                    label="当前密码"
                    name="currentPassword"
                    rules={[
                        {
                            required: true,
                            message: '请输入当前用户密码',
                        },
                        {
                            pattern: config.exp2,
                            message: '当前用户密码只能包含字母数字、下划线',
                        },
                        {
                            min: 6,
                            message: '密码长度必须大于等于6',
                        }
                    ]}
                >
                    <Password
                        placeholder="请输入当前用户密码"
                        prefix={<IconFont type='icon-mima' />}
                    />
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="新密码"
                    name="password"
                    rules={[
                        {
                            required: true,
                            message: '请输入新密码',
                        },
                        {
                            pattern: config.exp2,
                            message: '新密码只能包含字母数字、下划线和中文',
                        },
                        {
                            min: 6,
                            message: '密码长度必须大于等于6',
                        }
                    ]}
                >
                    <Password
                        prefix={<IconFont type='icon-mima' />}
                        placeholder="请输入新密码"
                    />
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="确认密码"
                    name="confirmPassword"
                    dependencies={['password']}
                    rules={[
                        {
                            required: true,
                            message: '请输入再次输入新密码',
                        },
                        {
                            min: 6,
                            message: '密码长度必须大于等于6',
                        },
                        ({ getFieldValue }) => ({
                            validator(rule, value) {
                                if (value && getFieldValue('password') === value) {
                                    return Promise.resolve();
                                }
                                return Promise.reject('您两次输入的密码不一致，请重新输入!');
                            },
                        }),
                    ]}
                >
                    <Password
                        prefix={<IconFont type='icon-mima' />}
                        placeholder="请输入再次输入新密码"
                    />
                </Form.Item>
            </Form>
        </Modal >
    );
};

export default ChangePassword;
