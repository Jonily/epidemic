<template>
	<div :class="isFixed?'bottomButtonFooterFlex':'bottomButtonFooter'" @click="inputData">
		<!-- <Iconfont v-if="isIcon" :size="iconSize" :color="iconColor" :type="iconType"/> -->
		<span class="bottomButtonText">{{buttonText}}</span>
	</div>
</template>

<script>
	// import Iconfont from '@/components/IconFont/uni-icons.vue';
	export default{
		components:{
			// Iconfont,
		},
		props:{
			isFixed:{
				type:Boolean,
				default:()=>{
					return true
				}
			},
			buttonText:{
				type:String,
				required: true
			},
			iconSize:{
				type:Number,
				default:()=>{
					return 30
				}
			},
			iconColor:{
				type:String,
				default:()=>{
					return '#FFFFFF'
				}
			},
			iconType:{
				type:String,
				default:()=>{
					return 'zhanghaoguanli'
				}
			},
			isIcon:{
				type:Boolean,
				default:()=>{
					return true
				}
			},
			clickButton:{
				type:Function,
			}
		},
		methods:{
			inputData(){
				this.$emit('adminChange',{})
				if(typeof this.clickButton == 'function'){
					this.clickButton()
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.bottomButtonFooter{
		display: flex;
		height: 44px;
		width: 355px;
		border-radius: 10px;
		align-items: center;
		justify-content: center;
		margin:auto;
		background: #40a9ff;
		.bottomButtonText{
			color: #FFFFFF;
			font-size: 18px;
			font-weight: 400;
			line-height: 14px;
			margin-left: 13px;
		}
	}
	.bottomButtonFooterFlex{
		display: flex;
		position: fixed;
		bottom: 16rpx;
		left: 50%;
		height: 88rpx;
		width: 710rpx;
		border-radius: 20rpx;
		align-items: center;
		justify-content: center;
		transform: translateX(-50%);
		background: #40a9ff;
		.bottomButtonText{
			color: #FFFFFF;
			font-size: 36rpx;
			font-weight: 400;
			line-height: 28rpx;
			margin-left: 16rpx;
		}
	}
</style>
