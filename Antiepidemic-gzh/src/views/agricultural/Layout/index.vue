<template>
  <div class="layout-container">
    <div class="main-entry">
      <!-- 路由入口 -->
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { setItem, getItem } from "../../../utils/storage";
import { mapState } from "vuex";
export default {
  name: "layout",
  data() {
    return {
      // 控制默认选中红的项，开启路由模式之后这个就没有作用了
      active: 0,
      isShowMain: true,
    };
  },
  async created() {
    this.isShowMain = getItem("EVENT_LOCAK");
  },
  methods: {
    name() {},
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
  },
  computed: {
    ...mapState({ vxsUser: "user" }),
  },
  watch: {
    isShowMain(newValue, oldValue) {
      setItem("EVENT_LOCAK", newValue);
    },
  },
};
</script>

<style lang="scss" scoped>
.layout-container {
  height:100%
  /* .toutiao {
    font-size: 40px;
  }
  .text {
    font-size: 20px;
  } */
}
.main-entry{
  height:100%
}
.my-swipe .van-swipe-item {
  color: #fff;
  font-size: 20px;
  height: 100vh;
  line-height: 150px;
  text-align: center;
  background-color: #39a9ed;
}
img {
  display: inline-block;
  text-align: center;
  width: 57vh;
  height: 100vh;
}
::v-deep .van-swipe__indicators {
  bottom: 8vh;

  .van-swipe__indicator {
    background-color: red;
  }
  .van-swipe__indicator--active {
    background-color: #1989fa !important;
  }
}
.btn {
  padding: 35px;
  z-index: 1;
  position: absolute;
  bottom: 140px;
  left: 50%;
  transform: -50%;
  translate: -50%;
}

</style>