<template>
  <div>
    <div class="head">
      <div class="left">
        <div class="iconfont user">&#xeb24;</div>
      </div>
      <div class="title">聊天列表</div>
      <ul>
        <li class="iconfont" @click="addFriends">&#xeaf3;</li>
        <li class="iconfont">&#xeaf4;</li>
      </ul>
    </div>
    <!-- 列表，可滚动 -->
	<scroll-view  scroll-y="true" class="scroll-y">
    <div style="margin-top:50px;" @click="goTalkWindow('18324234745')">
      <scoketCard title="18324234745" message="一起出去跑一会吧！我在解放路，等你！" data="17:50"></scoketCard>
    </div>
    <div @click="goTalkWindow('18291563764')">
      <scoketCard title="18291563764" message="一起出去跑一会吧！我在解放路，等你！" data="17:50"></scoketCard>
    </div>
	</scroll-view>
  </div>
</template>
<script>
import scoketCard from "@/components/scoketCard";
export default {
  components: {
    scoketCard
  },
  methods:{
    // 跳转到添加好友页面
    addFriends(){
       uni.navigateTo({
        url:'/pages/talk/talk/addFriends'
      })
    },
    goTalkWindow(title){
      // 跳转到聊天页面
      uni.navigateTo({
        url:'/pages/talk/talk/talkwindow?title='+title
      })
    }
  },
  data() {
    return {};
  }
};
</script>

<style lang="stylus" scoped>
* {
  margin: 0;
  padding: 0;
}

.head {
  width: 100%;
  background: #515151;
  height: 40px;
  position: fixed;
  z-index 999;
  .left {
    .user {
      display: inline-block;
      float: left;
      font-size: 1.4rem;
      color: #fff;
      position: absolute;
      left: 10px;
      line-height: 40px;
    }
  }

  .title {
    text-align: center;
    color: #fff;
    font-size: 0.7rem;
    line-height: 40px;
  }

  ul {
    margin: -31px 8px;

    li {
      float: right;
      font-size: 1.2rem;
      color: #fff;
      margin-right: 7px;
    }
  }
}
</style>
