import IconFont from '@/components/IconFont';
import { Button, Checkbox, Form, Input, message } from 'antd';
import React, { useState } from 'react';
import styles from './style.less';
import { fakeAccountLogin } from '@/services/login';
import { useModel, history } from 'umi';
import { Helmet, HelmetProvider } from 'react-helmet-async';
import config from '@/utils/config';
import { getPageQuery } from '@/utils/utils';

const { Password } = Input;
const replaceGoto = (useMsg=null) => {
  let oldHref = '/userManagement';
  window.location.href = oldHref;
};

export default () => {
  const [loading, setLoading] = useState(false);
  const { refresh } = useModel('@@initialState');

  const onFinish = async (values) => {
    setLoading(true);
    // 登录
    const res = await fakeAccountLogin({ ...values });
    console.log(res)
    if (res && res.success) {
      localStorage.setItem('TCLOUD-AUTH-HEADER', res.data.token);
      replaceGoto(res.data.user);
      message.success('登录成功！');
      setTimeout(() => {
        refresh();
      }, 0);
      return;
    }
    setLoading(false);
  };

  return (
    <div className={styles.container}>
      <div className={styles.main}>
        <div className={styles.left}>
        </div>
        <div className={styles.right}>
          <p>疫情防控平台</p>
          <Form
            name="normal_login"
            className={styles.login}
            initialValues={{ remember: true }}
            onFinish={onFinish}
          >
            <Form.Item
              className={styles.formItem}
              name="username"
              rules={[{ required: true, message: '请输入用户名!' }]}
            >
              <Input
                placeholder="请输入用户名"
                className={styles.input}
                prefix={
                  <IconFont
                    type="icon-zhanghaoguanli"
                    style={{ fontSize: '22px', color: 'rgba(0, 0, 0, 0.45)' }}
                  />
                }
              />
            </Form.Item>
            <Form.Item
              className={styles.formItem}
              name="password"
              rules={[{ required: true, message: '请输入密码!' }]}
            >
              <Password
                prefix={
                  <IconFont
                    type="icon-mima1"
                    style={{ fontSize: '22px', color: 'rgba(0, 0, 0, 0.45)' }}
                  />
                }
                placeholder="请输入密码"
                className={styles.input}
                iconRender={(visible) =>
                  visible ? <IconFont type="iconeye" /> : <IconFont type="iconeye-close" />
                }
              />
            </Form.Item>

            <Form.Item className={styles.formItem}>
              <Button type="primary" htmlType="submit" className={styles.btn} loading={loading}>
                登&nbsp;&nbsp;录
              </Button>
            </Form.Item>
          </Form>
          <div className={styles.foot_title}></div>
        </div>
      </div>
    </div>
  );
};
