<template>
	<div class="box">
		<p class="register" @click="toggleLogin">{{toggle}}</p>
		<h2 class="animated bounceInRight">{{title}}</h2>
		<!-- 登陆表单 -->
		<div class="form animated bounceInUp" v-show="login">
			<label for="username">手机号</label>
			<br>
			<input type="number" id="username">
			<label for="password" style="margin-top:20px ;">密码</label>
			<br>
			<input type="password" id="password">
			<!-- 登陆按钮 -->
			<button class="primary" type="warn">登陆</button>
			<p class="forget">忘记密码？</p>
		</div>
		<!-- 注册表单 -->
		<div class="form animated bounceInRight" v-show="!login">
			<label for="phone">手机号</label>
			<br>
			<input type="number" id="phone" style="width: 49%;">
			<span class="sendPass" @click.stop="send">{{time}}</span>
			<label for="password" style="margin-top:20px ;">密码</label>
			<br>
			<input type="password" id="password" placeholder="建议6-13位混合大小写">
			<!-- 登陆按钮 -->
			<button class="primary" type="warn">注册</button>
		</div>
	</div>
</template>

<script>
	export default {
		computed:{
			toggle(){
				return this.login ? '立即注册' : '返回登录'
			},
			title(){
				return this.login ? '密码登录' : '注册用户'
			}
		},
		data() {
			return {
				// 登陆页面默认显示
				login: true,
				// 倒计时
				time: '发送验证码'
			};
		},
		methods:{
			toggleLogin(){
				this.login = !this.login;
			},
			// 发送验证码倒计时
			send(){
				// 点击之后
				let num = 60;
				let timer = setInterval(()=>{
					num = num-1
					this.time = num+"秒后"
					if(this.time <= 0){
						clearInterval(timer)
						this.time = '发送验证码'
					}
				},1000)
			}
		}
	}
</script>

<style lang="stylus" scoped>
	.box{
		width:100%;
		height:750px;
		background: #515151;
		overflow:hidden;
		.register{
			color:#fff;
			font-size:.7rem;
			float:right;
			font-weight:lighter;
			margin: 19px 22px;
		}
		h2{
			color:#fff;
			font-size:1.2rem;
			display: inline-block;
			margin: 15% 12%;
			font-weight:lighter;
		}
		.form{
			width:100%;
			height:200px;
			label{
				color:#fff;
				font-size:.8rem;
				font-weight:lighter;
				display:inline-block;
				margin:12px 12%;
			}
			input{
				display:inline-block;
				margin:1px 12%;
				width:73%;
				height:30px;
				color:#fff;
				padding-left:5px;
				border-bottom:1px solid #ccc;
				font-size:.8rem;
				
			}
			.primary{
				width:74%;
				height:40px;
				font-size:.8rem;
				margin:35px 12%;
				border:none;
				line-height:40px;
			}
			.forget{
				color:#fff;
				font-size:.7rem;
				text-align:center;
			}
			// 发送验证码的样式
			.sendPass{
				display:inline-block;
				width:92px;
				height:18px;
				color:#ccc;
				font-size:.7rem;
				float:right;
				margin: -26px 49px;
				border-bottom:1px solid #ccc;
				padding-bottom:6px;
				text-indent:2em;
			}
		}
	}
</style>
