// import { getEnterById } from '@/services/api';
import { Descriptions, Drawer, Spin } from 'antd';
import moment from 'moment';
import { useEffect, useState } from 'react';
import { useRequest } from 'ahooks';
import { detail } from './services/api';
//出入详情
const Detail = ({ visible, onCancel, id }) => {
  const [info, setInfo] = useState({});

  // 出入详情
  const { run: outDetail, loading: outLoading } = useRequest(detail, {
    manual: true,
    onSuccess: (result, params) => {
      if (result.success) {
        setInfo(result.data)
      }
      else{
        setInfo({})
      }
    },
  });

  useEffect(() => {
    if (id) {
      outDetail({ id: id })
    }
  }, [id]);

  return (
    <Drawer
      title='出入详情'
      visible={visible}
      onClose={() => onCancel()}
      width='50%'
    >
      <Spin spinning={outLoading}>
        <Descriptions title="行程人信息" column={{ xs: 3, sm: 2, md: 2 }}>
          <Descriptions.Item label="姓名">{info.visitPersonName}</Descriptions.Item>
          <Descriptions.Item label="身份证">{info.idCard}</Descriptions.Item>
          <Descriptions.Item label="手机号">{info.visitPhone}</Descriptions.Item>
          <Descriptions.Item label="健康码状态">{info.codeStatus==1 ? '绿码' : (info.codeStatus==2 ? '黄码' : '红码')}</Descriptions.Item>
          <Descriptions.Item label="创建时间">{info.creatorTime}</Descriptions.Item>
        </Descriptions>

        <Descriptions title="来源信息" column={{ xs: 3, sm: 2, md: 2 }}>
          <Descriptions.Item label="来源地址">{info?.fromAddress}</Descriptions.Item>
          <Descriptions.Item label="交通方式">{info.fromModel}</Descriptions.Item>
        </Descriptions>

        <Descriptions title="行程原因" column={{ xs: 3, sm: 2, md: 2 }}>
          <Descriptions.Item label="行程原因">{info.reason}</Descriptions.Item>
        </Descriptions>

        <Descriptions title="目标信息" column={{ xs: 3, sm: 2, md: 2 }}>
          <Descriptions.Item label="目标地址">{info.targetAddress}</Descriptions.Item>
          <Descriptions.Item label="滞留天数">{info.dayNum}</Descriptions.Item>
          <Descriptions.Item label="预计到达时间">{info.planTime}</Descriptions.Item>
        </Descriptions>

      </Spin>
    </Drawer>
  );
};

export default Detail;
