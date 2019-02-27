<template>
  <div class="box" ref="box">
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
    <!-- 好友申请 -->
    <div class="messageFriend" @click="handelClickmessage">
      <span class="iconfont friend " :class="changeIcon"></span>
      <span class="red"></span>
    </div>
    <!-- 申请组件 -->
    <scroll-view scroll-y="true" class="scroll-y" v-show="!messageShow" ref="mainFriendWindow">
      <div :class="addMarginTop(index)" v-for="(item,index) in 5" :key="index">
        <friendMessage ref="message" title="18324234745" message="我想和你交个朋友！"></friendMessage>
      </div>
    </scroll-view>
    <!-- 列表，可滚动 -->
    <scroll-view scroll-y="true" class="scroll-y" v-show="messageShow" ref="mainCheckWindow">
      <div :class="addMarginTop(0)" @click="goTalkWindow('18324234745')">
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
import { socketUrl } from "../../../config/port";
import friendMessage from "@/components/friendMessage";

export default {
  computed: {
    // 改变icon
    changeIcon() {
      if (this.messageShow) {
        // 评论icon
        return "icon-haoyou";
      } else if (!this.messageShow) {
        // 聊天icon
        return "icon-liaotian";
      }
    }
  },
  onLoad() {
    //建立聊天的scoket
    this.conSocket();
  },
  components: {
    scoketCard,
    friendMessage
  },
  mounted() {
    // 让两个引用的子级div的第一个元素添加margin
    console.log(this.$refs.mainFriendWindow.childNodes);
  },
  methods: {
    // 给div加margintop类名
    addMarginTop(index) {
      if (index === 0) {
        return "addMarginTop";
      }
    },
    // 弹出验证
    handelClickmessage() {
      // 改变变量
      this.messageShow = !this.messageShow;
      // if(this.messageShow){
      //   this.$refs.message.show();
      // }else{
      //   this.$refs.message.hide();
      // }
    },
    // 建立socket
    conSocket() {
      let vueSelf = this;
      uni.connectSocket({
        url: socketUrl + "/" + this.user,
        data() {
          return {
            user: this.user,
            to: this.title
          };
        },
        method: "GET",
        success: res => {
          // 回调函数
          console.log("scoket建立成功！");
          // 监听webscoket打开
          uni.onSocketOpen(function(res) {
            console.log("WebSocket连接已打开！");
          });
          // 监听服务器返回数据
          uni.onSocketMessage(function(res) {
            console.log(res.data);
            vueSelf.message.push(JSON.parse(res.data));
          });
        }
      });
    },
    // 跳转到添加好友页面
    addFriends() {
      uni.navigateTo({
        url: "/pages/talk/talk/addFriends"
      });
    },
    goTalkWindow(title) {
      // 跳转到聊天页面
      uni.navigateTo({
        url: "/pages/talk/talk/talkwindow?title=" + title
      });
    }
  },
  data() {
    return {
      // 控制好友聊天/添加好友的界面变量
      messageShow: true
    };
  }
};
</script>

<style lang="stylus" scoped>
* {
  margin: 0;
  padding: 0;
}

.addMarginTop {
  margin-top: 50px;
}

.head {
  width: 100%;
  background: #515151;
  height: 40px;
  position: fixed;
  z-index: 999;

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

// 好友申请
.messageFriend {
  width: 40px;
  height: 40px;
  background: #000;
  position: fixed;
  border-radius: 50%;
  left: 83%;
  top: 78%;
  box-shadow: 2px 2px 10px #969696;
  animation: jump infinite 3s;

  @keyframes jump {
    0% {
      opacity: 1;
    }

    50% {
      opacity: 0.5;
    }

    100% {
      opacity: 1;
    }
  }

  .friend {
    display: inline-block;
    color: #fff;
    text-align: center;
    font-size: 1.2rem;
    margin: 8px;
  }

  .red {
    display: inline-block;
    width: 5px;
    height: 5px;
    border-radius: 50%;
    background: red;
    position: absolute;
    left: 75%;
    top: 20%;
  }
}
</style>
