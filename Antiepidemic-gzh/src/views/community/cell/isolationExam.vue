<template>
  <div class="brokerNews">
    <div class="brokerTitle">隔离审核</div>
    <div class="brokerList">
      <van-cell-group inset>
        <van-field
            v-model="name"
            :label="'姓名'"
            input-align="right"
            :placeholder="'请输入姓名'"
        />
        <van-field
            v-model="idCard"
            :label="'身份证号'"
            input-align="right"
            :placeholder="'请输入身份证号'"
        />
        <van-field
            v-model="phone"
            :label="'手机号'"
            input-align="right"
            :placeholder="'请输入手机号'"
        />
        <van-cell title="当前隔离类型" :value="typeName || '请选择隔离类型'" is-link @click="changeType"/>
        <van-field
            v-model="reason"
            label="申请原因"
            input-align="right"
            placeholder="请输入申请原因"
        />
      </van-cell-group>

    </div>
    <van-popup v-model="showTypePicker" position="bottom">
      <van-picker
          title="选择隔离类型"
          show-toolbar
          :columns="typeArr"
          @confirm="onConfirm2"
          @cancel="onCancel2"
      />
    </van-popup>

    <BottomButton :buttonText="buttonText" :isIcon="false" @adminChange="formSubmit" :isFixed="false"/>
  </div>
</template>

<script>
import axios from "axios";
import { mapState } from 'vuex';
import formValidation from '@/common/formValidation.js';
import BottomButton from '@/components/bottomButton/bottomButton.vue';
import {isolationExam} from '@/service/community.js'
import moment from 'moment';
export default{
  components:{
    BottomButton
  },
  data(){
    return {
      showTypePicker:false,
      showTimePicker:false,
      buttonText:'提交',
      typeArr:[
        {text:'集中隔离',value:1},
        {text:'医院留观',value:2},
        {text:'社区管理',value:3},
      ],
      name:'',//姓名
      idCard:'',//身份证号
      phone:'',//手机号
      reason:'',//备注
      type:'',//隔离类型
      typeName:'',//隔离类型名称
    }
  },
  computed: {
    communityUserInfo() {
      console.log(this.$store.state.communityUserInfo)
      return this.$store.state.communityUserInfo;
    },
  },
  created () {
    this.name = this.communityUserInfo?.name;
    this.idCard = this.communityUserInfo?.idCard;
  },
  methods:{
    changeType(){
      this.showTypePicker=true
    },
    onCancel2(v){
      this.showTypePicker=false
    },
    onConfirm2(v){
      this.type=v.value;
      this.typeName=v.text;
      this.showTypePicker=false
    },
    changeTime(){
      this.showTimePicker=true
    },
    onCancel(v){
      this.showTimePicker=false
    },
    onConfirm(v){
      this.time=moment(v).format('YYYY-MM-DD')
      this.showTimePicker=false
    },
    formatter(type, value){
      if (type === 'year') {
        return `${value}年`;
      }
      if (type === 'month') {
        return `${value}月`;
      }
      if (type === 'day') {
        return `${value}日`;
      }
      return value;
    },
    //提交信息
    formSubmit(){
      let rules = [{
        name: 'name',
        rule: ['required', 'minLength:1'],
        msg: ['请输入姓名']
      },
        {
          name: 'type',
          rule: ['required'],
          msg: ['请选择隔离类型']
        },
        {
          name: 'idCard',
          rule: ['required'],
          msg: ['请输入身份证']
        },
        {
          name: 'phone',
          rule: ['required'],
          msg: ['请输入手机号']
        },
        {
          name: 'reason',
          rule: ['required'],
          msg: ['请输入申请原因']
        },

      ];
      const formData = {
        name: this.name,
        type: this.type,
        idCard: this.idCard,
        phone: this.phone,
        reason: this.reason,
      };
      let checkRes = formValidation.validation(formData, rules);
      // 进行表单检查
      if (checkRes) {
        return this.$toast(checkRes);
      }
      let saveData = {
        ...formData,
        name:this.name,
        type:this.type,
        idCard: this.idCard,
        phone:this.phone,
        reason:this.reason,
      };
      isolationExam(saveData).then(res=>{
        if(res.success){
          this.$toast(res.codeMessage || '操作成功')
          this.$router.push({
            path:'/community/homePage'
          })
        }else{
          this.$toast(res.codeMessage)
        }
      }).catch(err=>{})
    },
  }
}
</script>

<style lang="scss" scoped>
.brokerNews{
  height: 100%;
  box-sizing: border-box;
}
.brokerTitle{
  font-weight: 600;
  font-size:16px;
  box-sizing: border-box;
  padding: 10px 0 10px 15px;
}
.brokerList{
  padding: 0 5px;
  height: calc(100% - 104px);
}
</style>
