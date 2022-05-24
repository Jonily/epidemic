<template>
	<div class="brokerNews">
		<div class="brokerTitle">隔离登记</div>
		<div class="brokerList">
			<van-cell-group inset>
				<van-field 
					v-model="name" 
					:label="'姓名'"
					input-align="right"
					:placeholder="'请输入姓名'"
                    disabled
				/>
                <van-cell title="隔离类型" :value="typeName || '请选择隔离类型'" is-link @click="changeType"/>
                <van-cell title="开始隔离时间" :value="time || '请选择开始隔离时间'" is-link @click="changeTime"/>
				<van-field 
					v-model="origin"
					label="隔离地点"
					input-align="right"
					placeholder="请输入隔离地点"
				/>
                <van-field 
					v-model="describtion"
					label="备注"
					input-align="right"
					placeholder="请输入备注"
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
        <van-popup v-model="showTimePicker" position="bottom">
            <van-datetime-picker
                type="date"
                title="选择开始隔离时间"
				show-toolbar
                :formatter="formatter"
                @confirm="onConfirm"
				@cancel="onCancel"
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
	import {isolationSave} from '@/service/community.js'
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
				time:'',//开始隔离时间
				origin:'',//隔离地点
				describtion:'',//备注
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
				    name: 'time',
				    rule: ['required'],
				    msg: ['请选择开始隔离时间']
				  },
                  {
				    name: 'origin',
				    rule: ['required'],
				    msg: ['请输入隔离地点']
				  },
                  
				];
				const formData = {
					name: this.name,
					type: this.type,
					time: this.time,
                    origin: this.origin,
				};
				let checkRes = formValidation.validation(formData, rules);
				// 进行表单检查
				if (checkRes) {
				  return this.$toast(checkRes);
				}
				let saveData = {
				    ...formData,
					describtion:this.describtion,
                    idCard:this.communityUserInfo?.idCard,
					deptId:this.communityUserInfo?.deptId
				};
				isolationSave(saveData).then(res=>{
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