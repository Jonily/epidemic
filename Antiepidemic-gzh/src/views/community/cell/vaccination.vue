<template>
	<div class="brokerNews">
		<div class="brokerTitle">疫苗接种登记</div>
		<div class="brokerList">
			<van-cell-group inset>
				<van-field 
					v-model="name" 
					:label="'姓名'"
					input-align="right"
					:placeholder="'请输入姓名'"
                    disabled
				/>
                <van-cell title="疫苗品牌" :value="brandName || '请选择疫苗品牌'" is-link @click="changeBrand"/>
				<van-cell title="第几针" :value="inoculationNumName || '请选择第几针'" is-link @click="changeInoculationNum"/>
                <van-cell title="接种时间" :value="time || '请选择接种时间'" is-link @click="changeTime"/>
				<van-field 
					v-model="area"
					label="接种地址"
					input-align="right"
					placeholder="请输入接种地址"
				/>
			</van-cell-group>
		</div>
		<van-popup v-model="showBrandPicker" position="bottom">
			<van-picker
				title="选择疫苗品牌"
				show-toolbar
				:columns="brandArr"
				@confirm="onConfirm2"
				@cancel="onCancel2"
			/>
		</van-popup>
		<van-popup v-model="showInoculationNumPicker" position="bottom">
			<van-picker
				title="选择第几针"
				show-toolbar
				:columns="inoculationNumArr"
				@confirm="onConfirm3"
				@cancel="onCancel3"
			/>
		</van-popup>
        <van-popup v-model="showTimePicker" position="bottom">
            <van-datetime-picker
                type="date"
                title="选择接种时间"
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
	import {getVaccines,getNum,vaccinationSave} from '@/service/community.js';
    import moment from 'moment';
	export default{
		components:{
			BottomButton
		},
		data(){
			return {
				showBrandPicker:false,
				showInoculationNumPicker:false,
                showTimePicker:false,
				buttonText:'提交',
                brandArr:[],
				inoculationNumArr:[],
				name:'',//姓名
				time:'',//接种时间
				area:'',//接种地点
				brand:'',//疫苗品牌
                brandName:'',//疫苗品牌名称
				inoculationNum:'', //第几针
				inoculationNumName:'', //第几针名称
			}
		},
		computed: {
			communityUserInfo() {
				return this.$store.state.communityUserInfo;
			},
		},
		watch:{
			brand:{
				handler:function(nval,oval){
					if(nval){
						getNum({id:nval}).then(res=>{
							if(res.success){
								let arr = res?.data || [];
								let totalArr = [];
								for(let i=0;i<arr?.length;i++){
									totalArr.push({
										text:arr[i],
										value:arr[i],
									})
								}
								this.inoculationNumArr = totalArr;
							}else{
								this.$toast(res.codeMessage)
							}
						})
					}
					else this.inoculationNumArr = [];
				},
				immediate:true
			}
		},
		created () {
            this.name = this.communityUserInfo?.name;
			getVaccines().then(res=>{
				if(res.success){
					let arr = res?.data || [];
					let totalArr = [];
					for(let i=0;i<arr?.length;i++){
						totalArr.push({
							text:arr[i]?.name,
							value:arr[i]?.id,
						})
					}
					this.brandArr = totalArr;
				}else{
					this.$toast(res.codeMessage)
                }
			})
		},
		methods:{
            changeBrand(){
				this.showBrandPicker=true
			},
			onCancel2(v){
				this.showBrandPicker=false
			},
			onConfirm2(v){
				this.brand=v.value;
                this.brandName=v.text;
				this.showBrandPicker=false
			},
			changeInoculationNum(){
				this.showInoculationNumPicker=true
			},
			onCancel3(v){
				this.showInoculationNumPicker=false
			},
			onConfirm3(v){
				this.inoculationNum=v.value;
                this.inoculationNumName=v.text;
				this.showInoculationNumPicker=false
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
				    name: 'brand',
				    rule: ['required'],
				    msg: ['请选择疫苗品牌']
				  },
				  {
				    name: 'inoculationNum',
				    rule: ['required'],
				    msg: ['请选择第几针']
				  },
				  {
				    name: 'time',
				    rule: ['required'],
				    msg: ['请选择接种时间']
				  },
                  {
				    name: 'area',
				    rule: ['required'],
				    msg: ['请输入接种地址']
				  },
                  
				];
				const formData = {
					name: this.name,
					brand: this.brand,
					inoculationNum:this.inoculationNum,
					time: this.time,
                    area: this.area,
				};
				let checkRes = formValidation.validation(formData, rules);
				// 进行表单检查
				if (checkRes) {
				  return this.$toast(checkRes);
				}
				let saveData = {
				    ...formData,
                    idCard:this.communityUserInfo?.idCard,
					deptId:this.communityUserInfo?.deptId
				};
				vaccinationSave(saveData).then(res=>{
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