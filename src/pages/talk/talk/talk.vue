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
    <!-- 好友申请/新消息 icon图标 -->
    <div class="messageFriend" @click="handelClickmessage" :class="animateFriend">
      <span class="iconfont friend" :class="changeIcon"></span>
      <span class="red" v-show="iconShow.n"></span>
    </div>
    <!-- 申请组件 -->
    <scroll-view scroll-y="true" class="scroll-y" v-show="!messageShow" ref="mainFriendWindow">
      <div :class="addMarginTop(index)" v-for="(item,index) in newFriendsList" :key="index">
        <friendMessage ref="message" :title="item.i" :message="item.message"></friendMessage>
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
  methods: {
    // 抽离监听器的类名
    changeAnmite(list) {
      if (list.length != 0) {
        this.animateFriend = "animateFriend";
      } else {
        this.animateFriend = "";
      }
    },
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
        url: socketUrl + "/" + 18291563764,
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
            // 转换
            let list = JSON.parse(res.data);
            console.log(list)
            // 提取type，判断返回消息类型是消息还是好友申请
            if (list.type === "newFriend") {
              // 好友请求列表添加
              vueSelf.newFriendsList.push(list);
            } else {
              // 如果是消息列表
              vueSelf.newMessageList.push(list);
            }
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
      messageShow: true,
      // 监听好友申请列表的数组
      newFriendsList: [],
      // 好友新消息列表的数组
      newMessageList: [],
      // 维护消息icon的变量,n是红色小点点，s是动画效果
      iconShow: {
        n: false,
        s: false
      },
      // icon动画效果
      animateFriend: ""
    };
  },
  // 监听器监听好友列表的变化，做出icon动画效果
  watch: {
    newFriendsList(newValue) {
      // 如果好友申请有变化
      this.changeAnmite(newValue);
    },
    newMessageList(newValue) {
      // 如果消息列表有变化 (有新消息)
      this.changeAnmite(newValue);
    }
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

  // 将单独的动画效果抽离
  .animateFriend {
    animation: jump infinite 3s;
  }

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
