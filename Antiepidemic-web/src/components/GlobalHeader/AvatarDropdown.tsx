import { LogoutOutlined, SettingOutlined, UserOutlined } from '@ant-design/icons';
import { Avatar, Menu, Spin ,Modal} from 'antd';
import React from 'react';
import type { ConnectProps } from 'umi';
import { history, connect } from 'umi';
import type { ConnectState } from '@/models/connect';
import type { CurrentUser } from '@/models/user';
import HeaderDropdown from '../HeaderDropdown';
import styles from './index.less';
import { outLogin } from '@/services/login';
import { getPageQuery } from '@/utils/utils';
import { stringify } from 'querystring';
const { confirm } = Modal;
import UserProfile from './UserProfile';
import ChangePassword from './ChangePassword';
import { changePwd, save } from '@/services/user';

export type GlobalHeaderRightProps = {
  currentUser?: CurrentUser;
  menu?: boolean;
} & Partial<ConnectProps>;

/**
 * 退出登录，并且将当前的 url 保存
 */
 const loginOut = async () => {
  await outLogin();
  const { redirect } = getPageQuery();
  localStorage.removeItem('TCLOUD-AUTH-HEADER');
  if (window.location.pathname !== '/user/login' && !redirect) {
    history.replace({
      pathname: '/user/login',
      search: stringify({
        redirect: window.location.href,
      }),
    });
  }
};

class AvatarDropdown extends React.Component<GlobalHeaderRightProps> {
  state={
    changePasswordVisible:false,
    profileVisible:false
  };
  
  onMenuClick = (event:any) => {
    const key = event?.key;
      if (key === 'logout') {
        confirm({
          title: '您确认要退出系统么?',
          okText: '确定',
          cancelText: '取消',
          onOk() {
            loginOut();
          },
          onCancel() {

          },
        });
      }
      if (key === 'center') {
        this.setState({profileVisible:true});
      }
      if (key === 'settings') {
        this.setState({changePasswordVisible:true});
      }
  };

  //保存个人信息
  saveInfo = (info:object) => {
    save(info).then((result)=>{
      if (result.success) {
        this.setState({profileVisible:false});
        Modal.success({
          title: '修改个人信息成功',
          content: '您已经修改个人信息，请重新登录',
          okText: '重新登录',
          onOk: () => {
            loginOut();
          },
        });
      }
    })
  }

  //修改密码
  savePassword = (info:object) => {
    changePwd(info).then((result)=>{
      if (result.success) {
        this.setState({changePasswordVisible:false});
        Modal.success({
          title: '修改密码成功',
          content: '修改密码后请重新登录',
          okText: '重新登录',
          onOk: () => {
            loginOut();
          },
        });
      }
    })
  }

  render(): React.ReactNode {
    const {
      currentUser = {
        avatar: '',
        name: '',
      },
      menu,
    } = this.props;
    const{profileVisible,changePasswordVisible} = this.state;
    const menuHeaderDropdown = (
      <Menu className={styles.menu} selectedKeys={[]} onClick={(e)=>this.onMenuClick(e)}>
        {/* {menu && (
          <Menu.Item key="center">
            <UserOutlined />
            个人信息
          </Menu.Item>
        )}
        {menu && (
          <Menu.Item key="settings">
            <SettingOutlined />
            修改密码
          </Menu.Item>
        )}
        {menu && <Menu.Divider />} */}

        <Menu.Item key="logout">
          <LogoutOutlined />
          退出登录
        </Menu.Item>
      </Menu>
    );
    return currentUser && currentUser.name ? (
      <div>
      <HeaderDropdown overlay={menuHeaderDropdown}>
        <span className={`${styles.action} ${styles.account}`}>
          <Avatar size="small" className={styles.avatar} style={{background: '#1890ff',color: 'rgba(255, 255, 255, 0.85)'}}  alt="avatar"  icon={<UserOutlined />} />
          <span className={`${styles.name} anticon`}>{currentUser.name}</span>
        </span>
      </HeaderDropdown>
       <UserProfile 
          visible={profileVisible}
          user={currentUser}
          onCancel={() => {
            this.setState({profileVisible:false});
          }}
          onSubmit={(v: any) => {
            this.saveInfo(v);
          }}
          loading={false}
       />
       <ChangePassword 
          visible={changePasswordVisible}
          onCancel={() => {
            this.setState({changePasswordVisible:false});
          }}
          onSubmit={(v: any) => {
            this.savePassword(v);
          }}
          loading={false}
       />
       </div>
    ) : (
      <span className={`${styles.action} ${styles.account}`}>
        <Spin
          size="small"
          style={{
            marginLeft: 8,
            marginRight: 8,
          }}
        />
      </span>
    );
  }
}

export default connect(({ user }: ConnectState) => ({
  currentUser: user.currentUser,
}))(AvatarDropdown);
