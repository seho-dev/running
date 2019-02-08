<template>
  <div class="box">
    <p class="register" @click="toggleLogin">{{toggle}}</p>
    <h2 class="animated bounceInRight">{{title}}</h2>
    <!-- 登陆表单 -->
    <div class="form animated bounceInUp" v-show="login">
      <label for="username">手机号</label>
      <br>
      <input type="number" id="username" v-model="userName">
      <label for="password" style="margin-top:20px ;">密码</label>
      <br>
      <input type="password" id="password" v-model="passWord">
      <!-- 登陆按钮 -->
      <button class="primary" type="warn" @click="SignLogin">登陆</button>
      <p class="forget">忘记密码？</p>
    </div>
    <!-- 注册表单 -->
    <div class="form animated bounceInRight" v-show="!login" style="position: relative;">
      <label for="phone">手机号</label>
      <br>
      <!-- 边框 -->
      <div style="border-bottom:1px solid #ccc;width: 74%;margin:1px 12%;">
        <input
          type="number"
          id="phone"
          v-model="userName"
          style="width: 59%;border: none;padding-left: 1px;margin-left: 0;"
        >
        <span class="sendPass" @click="send">{{time}}</span>
      </div>
      <!-- 验证码 -->
      <label for="password" style="margin-top:20px ;">验证码</label>
      <br>
      <input type="text" id="password" v-model="checkNumber">
      <br>
      <label for="password" style="margin-top:20px ;">密码</label>
      <br>
      <input type="password" id="password" placeholder="建议6-13位混合大小写" v-model="passWord">
      <br>
      <label for="password" style="margin-top:20px ;">重复密码</label>
      <br>
      <input type="password" id="password" placeholder="重复上面的密码" v-model="againPassWord">
      <!-- 登陆按钮 -->
      <button class="primary" type="warn" @click="insertUser">注册</button>
    </div>
  </div>
</template>

<script>
import {
  insertUser,
  sendMessage,
  login,
  searchUser
} from "../../../api/me/userApi.js";
import { isMobileNumber } from "../../../common/js/tools.js";

import { MessageBox, Indicator } from "mint-ui";
export default {
  computed: {
    toggle() {
      return this.login ? "立即注册" : "返回登录";
    },
    title() {
      return this.login ? "密码登录" : "注册用户";
    }
  },
  data() {
    return {
      // 登陆页面默认显示
      login: true,
      // 倒计时
      time: "发送验证码",
      // 账号和密码
      userName: "",
      passWord: "",
      // 重复密码
      againPassWord: "",
      checkNumber: ""
    };
  },
  methods: {
    // 登陆
    async SignLogin() {
      const loginResult = await login({
        username: this.userName,
        password: this.passWord
      });
      if (loginResult.status == 0) {
        // 登陆成功 查询账号对象
        const user = await searchUser(this.userName);
        // 存储到内存
        uni.setStorageSync("Tb_user", user);
        Indicator.close();
        // 跳转到me组件 (关闭其他页面)
        uni.reLaunch({
          url: "/pages/me/me/me"
        });
      } else if (loginResult.status == 1) {
        // 用户名密码不匹配
      } else if (loginResult.status == 2) {
        console.log("用户名不存在");
      }
    },
    async insertUser() {
      // 判断非空
      if (
        this.username == "" ||
        this.password == "" ||
        this.againPassWord == "" ||
        this.checkNumber == ""
      ) {
        // 弹出层
        MessageBox("提示", "您有空未输入");
        return;
      }
      if (this.passWord != this.againPassWord) {
        MessageBox("提示", "密码不一致");
        return;
      }
      // loading
      Indicator.open({
        text: "注册中...",
        spinnerType: "fading-circle"
      });
      const result = await insertUser({
        username: this.userName,
        password: this.passWord,
        checkNumber: this.checkNumber
      });
      console.log(result);
      // 关闭loading
      Indicator.close();
      // 判断是否注册成功
      if (result.status == 0) {
        console.log(MessageBox("提示", "注册成功了哟"));
        // 弹出层
        MessageBox("提示", "注册成功了哟");
        // 清除掉所有输入的内容
        (this.userName = ""), (this.passWord = ""), (this.checkNumber = "");
        this.againPassWord = "";
        this.time = "发送验证码";
        // 改变状态
        //this.login = true;
      } else if (result.status == 1) {
        console.log(MessageBox("提示", "您输入的验证码或手机号不匹配"));
        MessageBox("提示", "您输入的验证码或手机号不匹配");
      } else if (result.status == 2) {
        console.log(MessageBox("提示", "此账号已被注册过，请直接登陆"));
        MessageBox("提示", "此账号已被注册过，请直接登陆");
      }
    },
    toggleLogin() {
      this.login = !this.login;
    },
    // 发送验证码倒计时
    async send() {
      // 判断手机格式
      const checkResult = isMobileNumber(this.userName);
      if (checkResult) {
        // 调用请求
        const sendResult = await sendMessage(this.userName);
        if (sendResult.status == 0) {
          // 发送成功
          // 点击之后
          let num = 60;
          let timer = setInterval(() => {
            num = num - 1;
            this.time = num + "秒后";
            if (num <= 0) {
              clearInterval(timer);
              this.time = "发送验证码";
            }
          }, 1000);
        } else {
          // 发送后端出现未知的错误 弹出模块
          MessageBox("提示", "发送验证码出现错误，请重试");
        }
      } else {
        // 手机格式出现错误
        MessageBox("提示", "手机格式有误");
        this.userName = "";
      }
    }
  }
};
</script>

<style lang="stylus" scoped>
.box {
  width: 100%;
  height: 750px;
  background: #515151;
  overflow: hidden;

  .register {
    color: #fff;
    font-size: 0.7rem;
    float: right;
    font-weight: lighter;
    margin: 19px 22px;
  }

  h2 {
    color: #fff;
    font-size: 1.2rem;
    display: inline-block;
    margin: 15% 12%;
    font-weight: lighter;
  }

  .form {
    width: 100%;
    height: 200px;

    label {
      color: #fff;
      font-size: 0.8rem;
      font-weight: lighter;
      display: inline-block;
      margin: 12px 12%;
    }

    input {
      display: inline-block;
      margin: 1px 12%;
      width: 73%;
      height: 30px;
      color: #fff;
      padding-left: 5px;
      border-bottom: 1px solid #ccc;
      font-size: 0.8rem;
    }

    .primary {
      width: 74%;
      height: 40px;
      font-size: 0.8rem;
      margin: 35px 12%;
      border: none;
      line-height: 40px;
    }

    .forget {
      color: #fff;
      font-size: 0.7rem;
      text-align: center;
    }

    // 发送验证码的样式
    .sendPass {
      display: inline-block;
      width: 102px;
      height: 18px;
      color: #ccc;
      font-size: 0.7rem;
      float: right;
      text-indent: 2em;
      margin: -23px 0%;
      // border-bottom: 1px solid #ccc;
      // padding-bottom:11px;
      // position:absolute;
      // left:0;
      // top:0;
    }
  }
}
</style>
