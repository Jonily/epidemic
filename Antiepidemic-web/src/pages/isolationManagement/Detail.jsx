// import { getEnterById } from '@/services/api';
import { Descriptions, Drawer, Spin ,Button} from 'antd';
import moment from 'moment';
import { useEffect, useState } from 'react';
import { useRequest } from 'ahooks';
import { detail } from './services/api';
//隔离信息详情（可审核）
const Detail = ({ visible, onCancel, id,onSubmit,typeList }) => {
  const [info, setInfo] = useState({});

  // 获取详情
  const { run: getDetail, loading: loading } = useRequest(detail, {
    manual: true,
    onSuccess: (result, params) => {
      if (result.success) {
        setInfo(result.data)
      }
    },
  });

  useEffect(() => {
    if (id) {
      getDetail({ id: id })
    }
  }, [id]);

  return (
    <Drawer
      title='隔离详情'
      visible={visible}
      onClose={() => onCancel()}
      width='50%'
    >
      <Spin spinning={loading}>
        <Descriptions title="提交人信息" column={{ xs: 3, sm: 2, md: 2 }}>
          <Descriptions.Item label="姓名">{info.name}</Descriptions.Item>
          <Descriptions.Item label="身份证">{info.idCard}</Descriptions.Item>
          <Descriptions.Item label="手机号">{info.phone}</Descriptions.Item>
        </Descriptions>

        <Descriptions title="提交阶段" column={{ xs: 3, sm: 2, md: 2 }}>
          {/* <Descriptions.Item label="审核后所处阶段">{typeList.find(item=>item.id == info.type)?.name}</Descriptions.Item> */}
          <Descriptions.Item label="审核后所处阶段">{info.type}</Descriptions.Item>
        </Descriptions>

        <Descriptions title="提交信息" column={{ xs: 3, sm: 2, md: 2 }}>
          <Descriptions.Item label="提交原因">{info.reason}</Descriptions.Item>
          <Descriptions.Item label="提交时间">{info.creatorTime}</Descriptions.Item>
        </Descriptions>

        <Descriptions title="当前状态" column={{ xs: 3, sm: 2, md: 2 }}>
          <Descriptions.Item label="当前状态">{'待审核'}</Descriptions.Item>
        </Descriptions>
        <div>
          <Button onClick={()=>onSubmit(info.id)} type="primary" style={{margin: '5px 15px',width: '100px'}}>审核</Button>
        </div>
      </Spin>
    </Drawer>
  );
};

export default Detail;
