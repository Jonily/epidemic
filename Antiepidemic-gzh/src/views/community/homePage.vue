<template>
	<div class="growerInformation">
		<InformationCard :name="communityUserInfo&&communityUserInfo.name || ''" :idCard="communityUserInfo&&communityUserInfo.idCard || ''" :phone="communityUserInfo&&communityUserInfo.phone || ''" :isEdit="false"/>
		<div class="growerContent">
      <van-cell-group inset>
        <van-cell title="出入登记" is-link to="/community/access">
          <template #icon>
            <i
              class="iconfont iconluxian1"
              style="color: rgb(24, 190, 122);font-size: 22px;margin-right:8px"
            />
          </template>
        </van-cell>
        <van-cell title="疫苗接种" is-link to="/community/vaccination">
          <template #icon>
            <i
              class="iconfont iconqianyang1"
              style="color: rgb(248, 166, 48);font-size: 22px;margin-right:8px"
            />
          </template>
        </van-cell>
        <van-cell title="隔离登记" is-link to="/community/isolation">
          <template #icon>
            <i
              class="iconfont iconshequjiaozheng"
              style="color: rgb(48, 137, 248);font-size: 22px;margin-right:8px"
            />
          </template>
        </van-cell>
		     <van-cell title="我的申请" is-link to="/community/isolationExam">
          <template #icon>
            <i
              class="iconfont iconerweima1"
              style="color: rgb(48, 137, 248);font-size: 22px;margin-right:8px"
            />
          </template>
        </van-cell>
      </van-cell-group>
		</div>
	</div>
</template>

<script>
	import InformationCard from '@/components/informationCard/informationCard.vue';
	export default{
		components: {
			InformationCard,
		},
		data(){
			return{}
		},
		computed: {
			communityUserInfo() {
        return this.$store.state.communityUserInfo;
			},
		},
		methods:{

		}
	}
</script>

<style lang="scss" scoped>
	// @import './index.scss';
</style>
