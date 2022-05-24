<template>
	<div class="content">
		<div 
			class="popup" 
			:class="actionConfig.specClass"
			@touchmove.stop.prevent="stopPrevent"
			@click="toggleSpec"
		>
			<!-- 遮罩层 -->
			<div class="mask"></div>
			<div class="layer attr-content" @click.stop="stopPrevent">
				<!-- 内容区域 -->
				<div class="action-title" 
				v-if="actionConfig.title&&actionConfig.title.length" 
				@click="titleClick(actionConfig.type)" 
				:style='{"border-bottom-color":actionConfig.isBorderColor?"#EBEBEB":"#fff"}'
				>{{actionConfig.title}}</div>
				<div class="action-summary" v-if="actionConfig.summary && actionConfig.summary.length"><div class="summary-center">{{actionConfig.summary}}</div></div>
				<div class="action-list">
					<div class="action-item"  v-for="(item,index) of actionConfig.list" :key="index" @click="itemClick(index,actionConfig.type)" >
						<div v-if='typeof(item)=="string"'>{{item}}</div>
						<div v-else :style='{"color":item.color,"background":item.background?item.background:"#fff"}'>{{item.title}}</div>
					</div>
				</div>
				<div class="cancel" @click="cancelClick" v-if="!actionConfig.isCloseCancel" :style="{color:actionConfig.cancelColor}">取消</div>
			</div>
		</div>
	</div>
</template>
<script>
	export default {
		data() {
			return {
				
				actionConfig:{
					title:'',
					summary:'',
					list:[],
					type:null,
					isCloseCancel:false,
					cancelColor:"#333333",
					isBorderColor:false,
					titleColor:"#333333",
					specClass: 'none',
				}
			}
		},
		methods: {
			toggleSpec() {
				if(this.actionConfig.specClass === 'show'){
					this.actionConfig.specClass = 'hide';
					setTimeout(() => {
						this.actionConfig.specClass = 'none';
					}, 250);
				}else if(this.actionConfig.specClass === 'none'){
					this.actionConfig.specClass = 'show';
				}
			},
			stopPrevent(){},
			itemClick(index,type){
				this.$emit("itemClick",index,type)
				this.toggleSpec()
			},
			cancelClick(){
				this.toggleSpec()
			},
			titleClick(type){
				this.$emit("titleClick",type)
				this.toggleSpec()
			}
		}
	}
</script>
<style lang="scss" scoped>
	.popup {
		position: fixed;
		left: 0;
		top: 0;
		right: 0;
		bottom: 0;
		z-index: 99;
		
		&.show {
			display: block;
			.mask{
				animation: showPopup 0.2s linear both;
			}
			.layer {
				animation: showLayer 0.2s linear both;
			}
		}
		&.hide {
			.mask{
				animation: hidePopup 0.2s linear both;
			}
			.layer {
				animation: hideLayer 0.2s linear both;
			}
		}
		&.none {
			display: none;
		}
		.mask{
			position: fixed;
			top: 0;
			width: 100%;
			height: 100%;
			z-index: 1;
			background-color: rgba(0, 0, 0, 0.5);
		}
		.layer {
			position: fixed;
			z-index: 99;
			bottom: 0;
			width: 100%;
			background-color: #F7F7F7;
			.action-title{
				height: 44px;
				line-height: 44px;
				text-align: center;
				color: #333;
				background-color: #fff;
				border-bottom-width: 1px; 
				border-bottom-style: solid; 
			}
			.action-summary{
				text-align: center;
				color: #888;
				background-color: #fff;
				box-sizing: border-box;
				padding:  15px 36px;
				display: flex;
				justify-content: center;
				align-items: center;
				
				.summary-center{
					font-size: 14px;
					color: #666666;
				}
			}
			.action-list{
				font-size:16px;
				background-color: #fff;
				.action-item{
					height: 44px;
					line-height: 44px;
					text-align: center;
					color: #333;
					border-top: 1px solid #EBEBEB;
				}
			}
			.cancel{
				height: 44px;
				line-height: 44px;
				text-align: center;
				color: #333;
				font-size:16px;
				background-color: #fff;
				margin-top: 6px;
			}
		}
		@keyframes showPopup {
			0% {
				opacity: 0;
			}
			100% {
				opacity: 1;
			}
		}
		@keyframes hidePopup {
			0% {
				opacity: 1;
			}
			100% {
				opacity: 0;
			}
		}
		@keyframes showLayer {
			0% {
				transform: translateY(120%);
			}
			100% {
				transform: translateY(0%);
			}
		}
		@keyframes hideLayer {
			0% {
				transform: translateY(0);
			}
			100% {
				transform: translateY(120%);
			}
		}
	}
</style>