import config from '@/utils/config';
import { Form, Input, message, Modal, Radio, Select, TreeSelect,InputNumber } from 'antd';
import React, { useEffect, useState } from 'react';
import {getDeptList,getAddress} from './services/api';
import { useModel } from 'umi';
import styles from './style.less';
import { useRequest } from 'ahooks';

const { TextArea } = Input;
const AddModal = ({ obj = {}, visible, onCancel, onSubmit}) => {
    const [communityList, setCommunityList] = useState([]);  //社区列表
    const [form] = Form.useForm();
    const [provinceList, setProvinceList] = useState([]);  //省份列表
    const [cityList, setCityList] = useState([]);   //市
    const [districtList, setDistrictList] = useState([]);  //县
    const [streetList, setStreetList] = useState([]);  //区
    const [villageList,setVillageList]=useState([]) //村
    const [provinceData, setProvinceData] = useState();  //省份数据id
    const [cityData, setCityData] = useState();   //市
    const [districtData, setDistrictData] = useState();  //县
    const [streetData, setStreetData] = useState();  //区
    const [villageData, setVillageData] = useState();  //村
    const deptId = '4304' || '';

    useEffect(()=>{
        //获取社区列表
        getDeptList({}).then(res=>{
            if(res && res.success){
                setCommunityList(res.data || []);
            }
        })
    },[])

    useEffect(() => {
        if (visible) {
            form.setFieldsValue({
                ...obj,
                province:provinceList?.find(item=>item.id == obj.province)?.name,
                city:cityList?.find(item=>item.id == obj.city)?.name,
                district:districtList?.find(item=>item.id == obj.district)?.name,
                street:streetList?.find(item=>item.id == obj.street)?.name,
                village:villageList?.find(item=>item.id == obj.village)?.name,
                codeStatus:''+(obj?.codeStatus||''),
            });
            if(obj?.id){
                setProvinceData(obj.province);
                setCityData(obj.city);
                setDistrictData(obj.district);
                setStreetData(obj.street);
                setVillageData(obj.village);
            }
        }
    }, [visible])

    const onOk = () => {
        form.validateFields()
            .then(values => {
                clear();
                onSubmit({
                    ...values,
                    province:provinceData,
                    city:cityData,
                    district:districtData,
                    street:streetData,
                    village:villageData
                });
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
            name: '',
            idCard: '',
            phone: '',
            age: '',
            province:'',
            city:'',
            district:'',
            street:'',
            village:'',
            orgion: '',
            codeStatus:'',
            deptName:'',
            id: '',
        })
        setProvinceData('');
        setCityData('');
        setDistrictData('');
        setStreetData('');
        setVillageData('');
    }

    // 验证身份证
    const checkCard = (rule, sId) => {
        if (sId) {
          let iSum = 0;
          let info = "";
          let sBirthday;
          let aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
          if(!/^\d{17}(\d|x)$/i.test(sId)) return Promise.reject("你输入的身份证长度或格式错误");
          sId=sId.replace(/x$/i,"a");
          if(aCity[parseInt(sId.substr(0,2))]==null) return Promise.reject("你的身份证地区非法");
          sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
          let d=new Date(sBirthday.replace(/-/g,"/")) ;
          if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())) return Promise.reject("身份证上的出生日期非法");
          for(let i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
          if(iSum%11!=1) return Promise.reject("你输入的身份证号非法");
          //aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女");//此次还可以判断出输入的身份证号的人性别
          return Promise.resolve();
        } else {
          return Promise.reject();
        }
    }

    useEffect(() => {
        getAddress('').then((res) => {
          let data=res.data;
          setProvinceList(data);
           let provinceid=deptId.substring(0,2)
           let province=""
           for(var i in data){
            if(data[i].agencyId==provinceid){
              province=data[i].name
            }
          }
          setProvinceData(parseInt(provinceid))
          setCityData(parseInt(deptId.substring(0,4)))
          // 获取城市名称
          getAddress(provinceid).then((res) => {
              // 默认市名称
           let cityname=""
           for(var i in res.data){
            if(res.data[i].agencyId==deptId.substring(0,4)){
              cityname=res.data[i].name
            }
          }
          form.setFieldsValue({
            province:province,
            city:cityname,
          })
          })
        })
    }, [])

    // 获取市
  useEffect(() => {
    provinceData && cityRun(provinceData);
  }, [provinceData])
  // 县
  useEffect(() => {
    cityData && districtRun(cityData);
  }, [cityData])
  // 区
  useEffect(() => {
    districtData && streetRun(districtData);
  }, [districtData])
  // 村
  useEffect(() => {
    streetData && villageRun(streetData);
  }, [streetData])

  // 选择省份
  const handleProvince = (value, res) => {
    setProvinceData(res.key)
    setCityData();
    setDistrictData();
    setStreetData();
    setVillageData();
    form.setFieldsValue({
      city: undefined,
      district: undefined,
      street: undefined,
      village:undefined
    })
  }
  const handleCity = (value, res) => {
    setCityData(res.key);
    setDistrictData();
    setStreetData();
    setVillageData();
    form.setFieldsValue({
      district: undefined,
      street: undefined,
      village:undefined
    })
  }
  const handleDistrict = (value, res) => {
    setDistrictData(res.key);
    setStreetData();
    setVillageData();
    form.setFieldsValue({
      street: undefined,
      village:undefined
    })
  }
  const handleStreet = (value, res) => {
    setVillageData();
    form.setFieldsValue({
      village:undefined
    })
    setStreetData(res.key)
  }

    // 获取市
    const { run: cityRun } = useRequest(getAddress, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                setCityList(result.data);
            }
        },
    });
    // 县
    const { run: districtRun } = useRequest(getAddress, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                setDistrictList(result.data);
            }
        },
    });
    // 区
    const { run: streetRun } = useRequest(getAddress, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                setStreetList(result.data);
            }
        },
    });
    // 村
    const { run: villageRun } = useRequest(getAddress, {
        manual: true,
        onSuccess: (result, params) => {
            if (result.success) {
                setVillageList(result.data);
            }
        },
    });

    

    return (
        <Modal
            visible={visible}
            title={`${obj.id ? '编辑用户' : '新增用户'}`}
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
                    name="name"
                    rules={[
                        {
                            pattern: config.exp1,
                            message: '姓名只能包含字母数字、下划线和中文',
                        },
                        { max: 20, message: '姓名不能超过20个字符' },
                    ]}
                >
                    <Input placeholder="请输入姓名"/>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="身份证号"
                    name="idCard"
                    rules={[
                        { required: true, message: '请输入身份证号' },
                        { validator: checkCard }
                    ]}
                >
                    <Input placeholder="请输入身份证号" disabled={obj.id ? true : false} />
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="手机号码"
                    name="phone"
                    rules={[
                        {
                            pattern: config.phone,
                            message: '请输入正确的手机号码',
                        },
                    ]}
                >
                    <Input placeholder="请输入手机号码" />
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="年龄"
                    name="age"
                >
                    <InputNumber placeholder="请输入年龄" min={0} max={150} style={{width:'100%'}}/>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label='地址'
                >
                    <Form.Item
                        name="province"
                        className={styles.addressItem}
                    >
                        <Select placeholder="请选择省份" onChange={handleProvince}>
                            {provinceList?.length > 0 && provinceList.map((item) => (
                                <Select.Option value={item.name} key={item.id}>
                                    {item.name}
                                </Select.Option>
                            ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="city"
                        className={styles.addressItem}
                    >
                        <Select placeholder="请选择市" onChange={handleCity} >
                        {cityList.length > 0 && cityList.map((item) => (
                            <Select.Option value={item.name} key={item.id} id={item.id}>
                                {item.name}
                            </Select.Option>
                        ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="district"
                        className={styles.addressItem}
                    >
                        <Select placeholder="请选择区县" onChange={handleDistrict}>
                        {districtList.length > 0 && districtList.map((item) => (
                            <Select.Option value={item.name} key={item.id}>
                                {item.name}
                            </Select.Option>
                        ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="street"
                        className={styles.addressItem}
                    >
                        <Select placeholder="请选择乡镇"  onChange={handleStreet}>
                        {streetList.length > 0 && streetList.map(item => (
                            <Select.Option value={item.name} key={item.id}>{item.name}</Select.Option>
                        ))}
                        </Select>
                    </Form.Item>
                    <Form.Item
                        name="village"
                        className={styles.addressItem}
                    >
                        <Select placeholder="请选择村" >
                        {villageList.length > 0 && villageList.map(item => (
                            <Select.Option value={item.name} key={item.id}>{item.name}</Select.Option>
                        ))}
                        </Select>
                    </Form.Item>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="详细地址"
                    name="origin"
                >
                    <Input placeholder="请输入详细地址" />
                </Form.Item>
                <Form.Item 
                    {...config.modalFormItemLayout}
                    label="当前健康码状态" 
                    name="codeStatus"
                >
                    <Select placeholder="请选择当前健康码状态">
                        <Select.Option value="1">绿码</Select.Option>
                        <Select.Option value="2">黄码</Select.Option>
                        <Select.Option value="3">红码</Select.Option>
                    </Select>
                </Form.Item>
                <Form.Item
                    {...config.modalFormItemLayout}
                    label="所处社区"
                    name="deptName"
                    rules={[
                        {
                            required: true,
                            message: '请选择所处社区',
                        }]}
                >
                    <Select  placeholder="请选择所处社区" >
                        {communityList.map((item) => (
                            <Select.Option key={item.deptId} value={item.deptId}>
                                {item.deptName}
                            </Select.Option>
                        ))}
                    </Select>
                </Form.Item>
                <Form.Item
                    name="id"
                    noStyle
                >
                    <Input type="hidden"/>
                </Form.Item>
            </Form>
        </Modal>
    );
};

export default AddModal;
