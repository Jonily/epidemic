<template>
	<div>
		<van-popup ref="popup" position="bottom" v-model="showRegion">
			<div class="region-container">
				<div class="title">
					地址
				</div>
				<div class="nav-list">
					<div v-for="addressKey,i in addressKeys" :key="i" class="item" :class="{'selected':navKey==addressKey}">
						<div class="line1"  @click="changeNav(addressKey)" v-if="selectedAddress[addressKey]!=null">{{selectedAddress[addressKey].name}}</div>
					</div>
					<div class="item line1 selected" v-if="!finish&&selectedAddress.district==null">请选择</div>
				</div>
				<van-radio-group class="group">
					<van-cell-group>
						<div v-for="regionKey,i in regionArr" :key="i">
							<van-cell :title="regionArr[i].name" clickable :data-name="regionArr[i].name" :data-id="regionArr[i].id" @click="onClick">
								<van-radio slot="right-icon" :name="regionArr[i].id" />
							</van-cell>
						</div>
					</van-cell-group>
				</van-radio-group>
				<!-- <van-picker
				    class="picker1"
					title="地址"
					value-key="name"
					:default-index="0"
					:columns="regionArr"
					@change="onRegionConfirm"
					@cancel="close"
				/> -->
			</div>
		</van-popup>
	</div>
</template>

<script>
	import { getMapList} from '@/service/community.js'
	export default {
		props: {
			defaultAddress: {
				type: Object,
				default: () => {
					return {
						province: null,
						city: null,
						district: null,
						street: null,
					}
				}
			}
		},
		computed:{
			regionArr(){
				let data = this.addressList?.[this.listKey];
				// let arr = [{
				// 	values:[
				// 		{ name: '请选择',},
				// 		...data
				// 	],
				// }];
				// return arr;
				return data || [];
			}
		},
		data() {
			return {
				showRegion:false,
				addressKeys: ['province', 'city', 'district', 'street'],
				addressList: {
					province: [],
					city: [],
					district: [],
					street: [],
				},
				selectedAddress: {
					province: null,
					city: null,
					district: null,
					street: null,
				},
				navKey: null, // 激活的导航列表key
				listKey: 'province', // 激活的地址列表key
				finish: false, // 是否已选择完成
			}
		},
		methods: {
			async onRegionConfirm(picker, value, i){
				if(!value?.[0]?.id){
					this.$toast("请选择！");
					return;
				}
				this.selectItem(value?.[0] || [])
				// await this.selectItem(value?.[0] || []);
				// let index = this.addressKeys.findIndex((key)=>key == this.listKey);
				// if(index > -1 && index<this.addressKeys?.length - 1){
				// 	let curKey = this.addressKeys[index + 1];
				// 	this.selectedAddress[curKey] = this.addressList[this.listKey]?.[0];
				// }
			},
			getCitys(id) {
				return new Promise((reslove, reject) => {
					let params = '';
					if(id) params = {id:id};
					getMapList(params).then((res)=>{
						console.log('请求数据',  res)
						if (res?.success) {
							reslove(res);
						} else {
							reject(res?.codeMessage || '服务器连接出错');
							this.$toast(res?.codeMessage || '服务器连接出错')
						}
					})
				});
			},
			/**
			 * 重选地区
			 */
			changeNav(addressKey) {
				// console.log('addressKey', addressKey)
				this.finish = false
				this.navKey = addressKey
				this.listKey = addressKey
			},
			/**
			 * 初始化
			 */
			async init() {
				if (this.addressList.province.length == 0 && this.addressList.city.length == 0 && this.addressList.district.length ==
					0 && this.addressList.street.length == 0) {
					// 遍历defaultAddress，有这填入list，并设置listKey和navKey
					Object.keys(this.defaultAddress).forEach(async (key) => {
						if (this.defaultAddress[key] != null && this.defaultAddress[key] != 0) {
							this.navKey = key
							this.listKey = key
							this.finish = true
							var addressList = []
							if (key == 'province') {
								addressList = await this.getCitysInfo(0)
							} else if (key == 'city') {
								addressList = await this.getCitysInfo(this.defaultAddress['province'])
							} else if (key == 'district') {
								addressList = await this.getCitysInfo(this.defaultAddress['city'])
							} else if (key == 'street') {
								addressList = await this.getCitysInfo(this.defaultAddress['district'])
							}
							this.addressList[key] = addressList
							addressList.forEach((item) => {
								if (item.id == this.defaultAddress[key]) {
									this.selectedAddress[key] = item
								}
							})
						}
					})

					if (!this.finish) {
						this.addressList['province'] = await this.getCitysInfo(0)
					}
				}
			},
			getCitysInfo(id) {
				return new Promise((resolve, reject) => {
					this.getCitys(id).then(res => {
						if (res.success) {
							resolve(res.data)
						} else {
							return this.$toast({
								title: res.codeMessage,
								icon: 'none'
							})
						}

					}).catch(err => {
						reject('错误', err)
						this.$toast('网络连接错误')
					})
				})
			},
			/**
			 * 选择地址
			 */
			async selectItem(item) {
				let listKey = this.listKey
				this.selectedAddress[listKey] = item
				// 重置nav选中值，根据当前选中键值显示下一个地址列表 || 没有下级的话是完成
				// province->city 
				// city->district 
				// district->street 
				// street>完成
				this.navKey = null
				let res = await this.getCitysInfo(item.id)
				
				if (listKey == 'province') {
					this.cleanNavData(['city', 'district', 'street'])
					this.addressList['city'] = res
					this.listKey = 'city'
				} else if (listKey == 'city') {
					this.cleanNavData(['district', 'street'])
					this.addressList['district'] = res
					this.listKey = 'district'
				} else if (listKey == 'district') {
					this.cleanNavData(['street'])
					this.addressList['street'] = res
					this.listKey = 'street'
				} 
				if (res.length == 0 || listKey == 'street') { //子地图为空或者街道选择完毕时，结束选择
					return this.finishSelect(listKey)
				}
			},
			/**
			 * 清除nav头,地址列表和已选中地址信息
			 */
			cleanNavData(addressKeys) {
				addressKeys.forEach((key) => {
					this.addressList[key] = []
					this.selectedAddress[key] = null
				})
			},
			/**
			 * 完成地址选择
			 */
			finishSelect(listKey) {
				// console.log('selectedAddress', this.selectedAddress)
				this.finish = true
				this.listKey = listKey
				this.navKey = listKey
				this.$emit('finish', this.selectedAddress)
				this.close()
			},
			close() {
				this.showRegion = false;
			},
			open() {
				this.showRegion = true;
				this.init()
			},
			//地址选择器的点击事件
			onClick(event) {
				const { name,id } = event.currentTarget.dataset;
				console.log(name,id)
				if(!id){
					this.$toast("请选择！");
					return;
				}
				this.selectItem({name,id});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.region-container {
		background-color: #FFFFFF;
		border-radius: 10px 10px 0 0;
		height: 100%;
		display: flex;
		flex-wrap: wrap;
		flex-direction: column;

		.line1 {
			white-space: nowrap;
			text-overflow: ellipsis;
			overflow: hidden;
		}

		.title {
			color: #333333;
			font-size: 16px;
			padding: 15px 0;
			text-align: center;
			position: relative;
			font-weight: 500;

			.close {
				width: 24px;
				height: 24px;
				position: absolute;
				right: 12px;
				top: 12px;
			}
		}

		.nav-list {
			width: 100%;
			height: 50px;
			padding: 0 10px;
			display: flex;
			justify-content: flex-start;
			align-items: center;
			box-sizing: border-box;

			.item {
				width: 20%;
				color: #333333;
				font-size: 14px;
				position: relative;
				height: 25px;
				text-align: center;
				transition: font-weight 0.3s;

				&::after {
					content: '';
					display: block;
					height: 3px;
					width: 36px;
					position: absolute;
					bottom: 0px;
					left: 50%;
					transform: translateX(-50%);
					background-color: transparent;
					transition: background-color 0.3s;
				}

				&.selected {
					font-weight: 500;

					&::after {
						background-color: #FF536B;
					}
				}
			}
		}

		.address-list {
			position: relative;
			flex: 1;
			height: 1px;

			.a-item {
				padding: 0 12px;
				height: 44px;
				color: #333333;
				transition: background-color 0.1s;
				display: flex;
				justify-content: space-between;
				align-items: center;

				&.on {
					color: #FF536B;
				}

				.text {
					width: 90%;
				}

				.selected-icon {
					width: 20px;
					height: 20px;
				}

				&.hover {
					background-color: #e9e9e9;
				}
			}
		}
	}
	
	.group{
		height: 250px;
    	overflow: auto;
		.van-hairline--top-bottom::after, .van-hairline-unset--top-bottom::after{
			border: 0 !important;
		}
		.van-cell::after{
			border: 0 !important;
		}
		.van-radio__icon .van-icon{
			border: 0 !important;
		}
		.van-radio{
			display: none;
		}
		.van-cell__title{
			text-align: center;
		}
	}
	.group::-webkit-scrollbar {
		width: 0px;
	}
</style>
