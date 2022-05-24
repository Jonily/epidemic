<template>
	<div class="brokerNews">
		<div class="brokerTitle">出入登记</div>
		<div class="brokerList">
			<van-cell-group inset>
				<van-field
					v-model="name"
					:label="'姓名'"
					input-align="right"
					:placeholder="'请输入姓名'"
                    disabled
				/>
                <van-cell title="出入类型" :value="typeName || '请选择出入类型'" is-link @click="changeType"/>
				<van-cell title="来源地址" is-link @click="openRegion">
						<div
							class="mapSite ui-list-body block"
							v-if="Object.keys(address).length == 0 || !address['province']"
						>
							{{'请选择来源地址'}}
						</div>
						<div v-else class="van-field__control">
							<span v-if="address['province']">{{address['province'].name}}</span>
							<span v-if="address['city']">{{address['city'].name}}</span>
							<span v-if="address['district']">{{address['district'].name}}</span>
							<span v-if="address['street']">{{address['street'].name}}</span>
						</div>
				</van-cell>
				<van-field
					v-model="fromVillage"
					label="来源详址"
					input-align="right"
					placeholder="请输入来源详址"
				/>
				<van-cell title="目标地址" is-link @click="openTargetRegion">
						<div
							class="mapSite ui-list-body block"
							v-if="Object.keys(targetAddress).length == 0 || !targetAddress['province']"
						>
							{{'请选择目标地址'}}
						</div>
						<div v-else class="van-field__control">
							<span v-if="targetAddress['province']">{{targetAddress['province'].name}}</span>
							<span v-if="targetAddress['city']">{{targetAddress['city'].name}}</span>
							<span v-if="targetAddress['district']">{{targetAddress['district'].name}}</span>
							<span v-if="targetAddress['street']">{{targetAddress['street'].name}}</span>
						</div>
				</van-cell>
				<van-field
					v-model="targetVillage"
					label="目标详址"
					input-align="right"
					placeholder="请输入目标详址"
				/>
				<van-cell title="交通方式" :value="fromModelName || '请选择交通方式'" is-link @click="changeFromModel"/>
				<van-field
					v-model="reason"
					label="行程原因"
					input-align="right"
					placeholder="请输入行程原因"
				/>
				<van-field
					v-model="dayNum"
					label="滞留天数"
					input-align="right"
					placeholder="请输入滞留天数"
				/>
                <van-cell title="预计到达时间" :value="planTime || '请选择预计到达时间'" is-link @click="changeTime"/>
			</van-cell-group>
		</div>
		<van-popup v-model="showTypePicker" position="bottom">
			<van-picker
				title="选择出入类型"
				show-toolbar
				:columns="typeArr"
				@confirm="onConfirm2"
				@cancel="onCancel2"
			/>
		</van-popup>
		<van-popup v-model="showFromModelPicker" position="bottom">
			<van-picker
				title="选择交通方式"
				show-toolbar
				:columns="fromModelArr"
				@confirm="onConfirm3"
				@cancel="onCancel3"
			/>
		</van-popup>
        <van-popup v-model="showTimePicker" position="bottom">
            <van-datetime-picker
                type="date"
                title="选择预计到达时间"
				show-toolbar
                :formatter="formatter"
                @confirm="onConfirm"
				@cancel="onCancel"
            />
        </van-popup>
		<BottomButton :buttonText="buttonText" :isIcon="false" @adminChange="formSubmit" :isFixed="false"/>
		<region-select ref="region" @finish="finish" :defaultAddress="defaultAddress"></region-select>
		<region-select ref="targetRegion" @finish="targetFinish" :defaultAddress="defaultAddress"></region-select>
	</div>
</template>

<script>
	import axios from "axios";
	import { mapState } from 'vuex';
	import formValidation from '@/common/formValidation.js';
	import BottomButton from '@/components/bottomButton/bottomButton.vue';
	import regionSelect from '@/components/regionSelect/regionSelect.vue';
	import {getVisitTypes,accessSave} from '@/service/community.js'
    import moment from 'moment';
	export default{
		components:{
			BottomButton,
			regionSelect,
		},
		data(){
			return {
				showTypePicker:false,
				showFromModelPicker:false,
                showTimePicker:false,
				buttonText:'提交',
                typeArr:[
					{text:'来访',value:0},
					{text:'离开',value:1},
				],
				fromModelArr:[],
				name:'',//姓名
				fromVillage:'',
				targetVillage:'',
				reason:'',//
				dayNum:'',
				planTime:'',
				type:'',//出入类型
                typeName:'',//出入类型名称
				fromModel:'',
				fromModelName:'',
				defaultAddress: {
					province: '11',
					city: '1101',
					district: '110105',
					street: null,
				},
				address: {}, //  省市县镇 拼接后地址
				targetAddress:{},
			}
		},
		computed: {
			communityUserInfo() {
				return this.$store.state.communityUserInfo;
			},
		},
		created () {
            this.name = this.communityUserInfo?.name;
			getVisitTypes().then(res=>{
				if(res.success){
					let arr = res?.data || [];
					let totalArr = [];
					for(let i=0;i<arr?.length;i++){
						totalArr.push({
							text:arr[i]?.name,
							value:arr[i]?.id,
						})
					}
					this.fromModelArr = totalArr;
				}else{
					this.$toast(res.codeMessage)
                }
			})
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
			changeFromModel(){
				this.showFromModelPicker=true
			},
			onCancel3(v){
				this.showFromModelPicker=false
			},
			onConfirm3(v){
				this.fromModel=v.value;
                this.fromModelName=v.text;
				this.showFromModelPicker=false
			},
            changeTime(){
				this.showTimePicker=true
			},
            onCancel(v){
				this.showTimePicker=false
			},
			onConfirm(v){
                this.planTime=moment(v).format('YYYY-MM-DD')
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
				    msg: ['请选择出入类型']
				  },
				  {
					name: 'province',
					rule: ['required'],
					msg: ['请选择来源地址']
				  },
				  {
				    name: 'fromVillage',
				    rule: ['required'],
				    msg: ['请输入来源详址']
				  },
				  {
					name: 'targetProvince',
					rule: ['required'],
					msg: ['请选择目标地址']
				  },
				  {
				    name: 'targetVillage',
				    rule: ['required'],
				    msg: ['请输入目标详址']
				  },
				  {
				    name: 'fromModel',
				    rule: ['required'],
				    msg: ['请选择交通方式']
				  },
				  {
				    name: 'reason',
				    rule: ['required'],
				    msg: ['请输入行程原因']
				  },
				  {
				    name: 'dayNum',
				    rule: ['required'],
				    msg: ['请输入滞留天数']
				  },
				  {
				    name: 'planTime',
				    rule: ['required'],
				    msg: ['请选择预计到达时间']
				  },
				];
				const formData = {
					name: this.name,
					type: this.type,
					province: (this.address&&this.address['province']) ? this.address['province'].id : '',
					fromVillage:this.fromVillage,
					targetProvince:(this.targetAddress&&this.targetAddress['province']) ? this.targetAddress['province'].id : '',
					targetVillage:this.targetVillage,
					fromModel:this.fromModel,
					reason:this.reason,
					dayNum: this.dayNum,
                    planTime: this.planTime,
				};
				let checkRes = formValidation.validation(formData, rules);
				// 进行表单检查
				if (checkRes) {
				  return this.$toast(checkRes);
				}
				let saveData = {
				    ...formData,
                    idCard:this.communityUserInfo?.idCard,
					deptId:this.communityUserInfo?.deptId,
					fromProvince:this.address['province']?.id,
					fromCity:this.address['city']?.id,
					fromDistrict:this.address['district']?.id,
					fromStreet:this.address['street']?.id,
					targetProvince:this.targetAddress['province']?.id,
					targetCity:this.targetAddress['city']?.id,
					targetDistrict:this.targetAddress['district']?.id,
					targetStreet:this.targetAddress['street']?.id,
				};
				accessSave(saveData).then(res=>{
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
			finish(value) {
				console.log(value);
				this.address = value;
			},
			openRegion() {
				this.$refs.region.open()
			},
			targetFinish(value) {
				console.log(value);
				this.targetAddress = value;
			},
			openTargetRegion() {
				this.$refs.targetRegion.open()
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
