<template>
	<div class="brokerNews">
		<div class="brokerTitle">基本信息</div>
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
					label="身份证"
					input-align="right"
					placeholder="请输入身份证号"
				/>
				<van-field
					v-model="phone"
					label="手机号"
					input-align="right"
					placeholder="请输入手机号"
				/>
                <van-field
					v-model="age"
					label="年龄"
					input-align="right"
					placeholder="请输入年龄"
				/>
                <van-cell title="所处社区" :value="deptName || '请选择所处社区'" is-link @click="changeDept"/>
                <van-field
					v-model="origion"
					label="详细居住地址"
					input-align="right"
					placeholder="请输入详细居住地址"
				/>
                <van-cell title="健康码" :value="codeStatusName || '请选择健康码'" is-link @click="changeCodeStatus"/>
			</van-cell-group>
		</div>
		<van-popup v-model="showDeptPicker" position="bottom">
			<van-picker
				title="选择所处社区"
				show-toolbar
				:columns="detpArr"
				@confirm="onConfirm"
				@cancel="onCancel"
			/>
		</van-popup>
        <van-popup v-model="showCodeStatusPicker" position="bottom">
			<van-picker
				title="选择健康码类型"
				show-toolbar
				:columns="codeStatusArr"
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
	import { getDeptList,login,auth} from '@/service/community.js'
	export default{
		components:{
			BottomButton
		},
		data(){
			return {
				showDeptPicker:false,
				showCodeStatusPicker:false,
				buttonText:'提交',
                detpArr:[],
				codeStatusArr:[
					{text:'绿码',value:1},
					{text:'黄码',value:2},
                    {text:'红码',value:3},
				],
				name:'',//姓名
				idCard:'',//身份证
				phone:'',//手机号
				age:'',//年龄
                origion:'', //地址
                deptId:'',//所处社区
                deptName:'',//所处社区名称
				codeStatus:'',//健康码
                codeStatusName:'',//健康码名称
			}
		},
		computed:{

		},
		created () {

			this.getAuth();
		},
		methods:{
			getAuth(){
				let href = window.location.href;
				const openId = localStorage.getItem("communityTcloudId") || ""; // 获取openId  o29SI6MIJ9yFQCtG5_BZYD13pbFU
				const code = href?.split('code=')?.[1]?.split('&state=')?.[0] || "";  //011pBYFa1wWhgC0HLbHa1eiFRb1pBYFC
				const state = '';
				if (!code) {
					if (!code && !state) {
					const redirectUrl = encodeURIComponent(href); // 重定向链接
					// 微信授权链接(第三方开放平台授权链接)
					window.location.href =
						`https://open.weixin.qq.com/connect/oauth2/authorize?appid=${this.$appid}&` +
						`redirect_uri=${redirectUrl}` +
						`&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect`;

					}
				} else if (openId || code) {
					auth({ code: code },{ openId: openId }).then((res) => {
						console.log(res)
						if (res && res?.success) {
							localStorage.setItem("communityTcloudId", res.data.openId);
							let data = res?.data?.person;
							if(data?.idCard){
								this.getUser(data);
							}
              this.getDept();
						}else{
							this.getAuth();
						}
					});
				}
			},
            getDept(){
                getDeptList().then(res=>{
                    if(res.success){
						let arr = res?.data || [];
						let totalArr = [];
						for(let i=0;i<arr?.length;i++){
							totalArr.push({
								text:arr[i]?.deptName,
								value:arr[i]?.deptId,
							})
						}
                        this.detpArr = totalArr;
                    }else{
                        this.$toast(res.codeMessage)
                    }
                })
            },
            getUser(loginInfo){
                if(loginInfo){  //之前有登录信息，则反显之前的登录信息
					this.name = loginInfo?.name;
					this.idCard = loginInfo?.idCard;
					this.phone = loginInfo?.phone;
					this.age = loginInfo?.age;
					this.deptId = loginInfo?.deptId;
					this.deptName=this.detpArr?.find(item=>item.value == this.deptId)?.text;
					this.origion=loginInfo?.origin;
					this.codeStatus=loginInfo?.codeStatus;
					this.codeStatusName=this.codeStatusArr?.find(item=>item.value == this.codeStatus)?.text;
				}
            },
			changeDept(){
				this.showDeptPicker=true
			},
			onCancel(v){
				this.showDeptPicker=false
			},
			onConfirm(v){
				this.deptId=v.value
                this.deptName=v.text;
				this.showDeptPicker=false
			},
            changeCodeStatus(){
				this.showCodeStatusPicker=true
			},
			onCancel2(v){
				this.showCodeStatusPicker=false
			},
			onConfirm2(v){
				this.codeStatus=v.value;
                this.codeStatusName=v.text;
				this.showCodeStatusPicker=false
			},
			//提交信息
			formSubmit(){
				let rules = [{
						name: 'name',
						rule: ['required', 'minLength:1'],
						msg: ['请输入姓名']
				  },
				  {
				    name: 'idCard',
				    rule: ['required', 'isIdCard'],
				    msg: ['请输入身份证号', '请输入正确的身份证号']
				  },
				  {
				    name: 'deptId',
				    rule: ['required'],
				    msg: ['请选择所处社区']
				  },
                  {
				    name: 'origion',
				    rule: ['required'],
				    msg: ['请输入详细居住地址']
				  },
                  {
				    name: 'codeStatus',
				    rule: ['required'],
				    msg: ['请选择健康码']
				  },
				];
				const formData = {
					name: this.name,
					idCard: this.idCard,
					deptId: this.deptId,
                    origion: this.origion,
                    codeStatus: this.codeStatus,
				};
				let checkRes = formValidation.validation(formData, rules);
				// 进行表单检查
				if (checkRes) {
				  return this.$toast(checkRes);
				}
				let saveData = {
				    ...formData,
					phone:this.phone,
					age:this.age,
				};
				login(saveData).then(res=>{
                    if(res.success){
                        this.$toast(res.codeMessage || '登录成功')
                        this.$store.dispatch('setCommunityUserInfo', saveData);
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
