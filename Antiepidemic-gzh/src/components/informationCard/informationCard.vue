<template>
	<div class="growerCard">
		<div class="growerCardImage">
			<img src="@/assets/images/home/QiPao.png" style="width: 57px;height: 75px;"/>
		</div>
		<div class="growerCardBox">
			<div class="growerCardBoxHeader">
				<div class="headerContent">
					<div class="contentFirst">
						<img src='@/assets/images/home/person.jpg' style="width: 70px;height: 70px;margin:-8px 0 0 -8px;"/>
					</div>
					<div class="contentSecond">
						<div class="contentName">
							<span>{{name}}</span>
						</div>
						<div class="contentPhone">
							<span>{{phone}}</span>
						</div>
					</div>
				</div>
				<div class="headerFooter" @click="openType" v-if="isEdit">
					<i class="iconfont iconxitongguanli1 fontStyle"/>
				</div>
			</div>
			<div class="growerCardBoxFooter">
				<span>{{encryptedCard}}</span>
			</div>
		</div>
		<action ref="action" @itemClick="itemClick"></action>
	</div>
</template>

<script>
	import action from '@/components/shufei-action/action.vue';
	export default{
		components:{
			action,
		},
		props: {
			name:{
				type:String,
				default: () => {
					return ''
				}
			},
			idCard:{
				type: String,
				default: () => {
					return ''
				}
			},
			phone:{
				type: String,
				default: () => {
					return ''
				}
			},
			goEdit:{
				type:Function,  
			},
			isEdit:{
				type: Boolean,
				default: () => {
					return true
				}
			}
		},
		data() {
			return {
				
			};  
		},
		computed: {
			encryptedCard() {
				let encryptedId = (this.idCard.length == 18) ? (this.idCard.substring(0,6) + '********' + this.idCard.substring(14,18)) : '';
				return encryptedId;
			},
		},
		methods: {
			openType() {
				this.$refs.action.actionConfig = {
					list: [
						{
							title: '信息修改',
							color: '#333'
						},
						{
							title: '注销',
							color: '#ff0000'
						},
					],
					type: 4,
					specClass: 'show'
				};
			},
			itemClick(index, type) {
				//这里根据不同的类型点击的索引值,做对应的逻辑处理
				if (index == 0) {
					return this.editClick();
				}
				if (index == 1) {
					return this.$emit('outLogin',{})
				}
			},
			//点击修改按钮
			editClick(){
				this.$emit('editChange',{})
				if(this.goEdit&&typeof this.goEdit==='function'){
					this.goEdit();
				}
			}
			
		}
	}
</script>

<style lang="scss" scoped>
	.growerCard{
		width: 355px;
		height: 175px;
		position: relative;
		margin: 5px auto;
		border-radius: 10px;
		overflow: hidden;
		box-sizing: border-box;
		padding: 34px 35px 32px 28px;
		background: #40a9ff;
		.growerCardImage{
			display: inline-block;
			position: absolute;
			top: 0;
			left: 0;
		}
		.growerCardBox{
			margin: auto;
			.growerCardBoxHeader{
				display: flex;
				width: 292px;
				justify-content: space-between;
				.headerContent{
					display: flex;
					align-items: center;
					justify-content: space-between;
					.contentFirst{
						width: 54px;
						height: 54px;
						line-height: 54px;
						margin-right: 14px;
						span-align: center;
						border-radius: 50%;
						background: #FFFFFF;
						overflow: hidden;
						image{
							width: 100%;
							height: 100%;
						}
						.face{
							// background-image: ;
							.face2{
								height: 86%;
								width: 86%;
								background-position: 0% 0%;
								background-size: 100% 100%;
								background-repeat: no-repeat;
								margin: 14% 0 0 9%;
								overflow: hidden;
							}
						}
					}
					.contentSecond{
						display: flex;
						flex-direction:column;
						justify-content: space-around;
						height: 100%;
						font-size: 18px;
						font-weight: 400;
						color: #FFFFFF;
						font-family: Microsoft YaHei;
						flex:5;
						.contentName{
							display: -webkit-box;
							-webkit-box-orient: vertical;
							-webkit-line-clamp: 2;
							overflow: hidden;
						}
						.contentPhone{
							margin-top: 5px;
						}
					}
				}
				.headerFooter{
					span-align: right;
				}
			}
			.growerCardBoxFooter{
				position: absolute;
				bottom: 17px;
				left: 50%;
				transform: translateX(-50%);
				margin: auto;
				padding-top: 12px;
				span-align: center;
				span{
					font-size: 28px;
					font-weight: 400;
					color: #FFFFFF;
					line-height: 14px;
				}
			}
		}
	}
	.fontStyle{
		font-size:30px;
		color: #fff;
	}
</style>
