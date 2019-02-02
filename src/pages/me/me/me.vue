<template>
	<view ref="view">
		<div class="head">
			<span class="me" @click="loginOrRegister">我</span>
			<ul>
				<li>
					<image src="../../../static/me-icon/saoma.png" mode=""></image>
				</li>
				<li>
					<image src="../../../static/me-icon/xiaoxi.png" mode=""></image>
				</li>
				<li>
					<image src="../../../static/me-icon/shezhi.png" mode=""></image>
				</li>
			</ul>
			<div class="center">
				<div class="portrait">
					<!-- 头像 -->
					<div style="width:64px;height: 64px;border-radius: 50%;"></div>
				</div>
				<div class="context">
					<div class="title">未登陆</div>
					<div class="slogan">就当左边的头像是我</div>
				</div>
				<div class="iconfont right">&#xe601;</div>
			</div>
		</div>
		<!-- 下面是等级展示 -->
		<div class="kaluli border-bottom">
			<p class="title">KG.0</p>
			<span class="right iconfont">&#xe601;</span>
		</div>
		<!-- 下面是数据展示 -->
		<div class="num">
			<p class="title">最新数据</p>
			<span class="right iconfont">&#xe601;</span>
			<div class="show">
				<div class="left">
					<span class="minute">总运动 ( 分钟 )</span>
					<br>
					<span class="number">0</span>
				</div>
				<div class="right">
					<p class="score">本周排名 <span>第-名</span></p>

					<div class="head" style="width:32px;height: 32px;border-radius: 50%;"></div>

				</div>
			</div>
		</div>
		<!-- 下面是全部菜单 -->
		<div class="list border-bottom border-top" style="margin-top: 75px;">
			<p class="title">我的关注</p>
			<span class="right iconfont">&#xe601;</span>
		</div>
		<div class="list border-bottom border-top">
			<p class="title">聊天</p>
			<span class="right iconfont">&#xe601;</span>
		</div>
		<div class="list border-bottom border-top">
			<p class="title">我的收藏</p>
			<span class="right iconfont">&#xe601;</span>
		</div>
		<div class="list border-bottom border-top">
			<p class="title">我的计划</p>
			<span class="right iconfont">&#xe601;</span>
		</div>
		<div class="list border-bottom border-top">
			<p class="title">支持我们</p>
			<span class="right iconfont">&#xe601;</span>
		</div>
	</view>
</template>

<script>
	export default {
		methods: {
			loginOrRegister() {
				// 跳转页面到登陆页面
				uni.navigateTo({
					url: '/pages/login/login/login?close=1'
				});
			}
		}
	}
</script>

<style lang="stylus" scoped>
	// 头部样式
	.head {
		width: 100%;
		height: 190px;
		background: #515151;
		color: #fff;

		.me {
			display: inline-block;
			margin: 20px 30px;
			font-size: 1rem;
		}

		ul {
			width: 130px;
			float: right;
			margin: 12px 0px;

			li {
				width: 32px;
				height: 32px;
				display: inline-block;
				margin-left: 7px;

				image {
					width: 100%;
					height: 100%;
				}
			}
		}

		// 中心
		.center {
			margin-top: 25px;
			position: relative;

			// 头像
			.portrait {
				margin-left: 20px;

				div {
					background: url('../../../static/me-icon/head.jpg') no-repeat;
					background-position: -5rem 0rem;
					background-size: 250px 157px;
				}
			}

			// 内容
			.context {
				display: inline-block;
				//设置位置
				position: absolute;
				left: 6rem;
				top: .7rem;

				.title {
					font-weight: lighter;
					font-size: 1.2rem;
				}

				.slogan {
					font-size: .6rem;
					margin-top: 7px;
				}
			}

			//箭头
			.right {
				font-size: 1.2rem;
				float: right;
				display: inline-block;
				margin: -41px 16px;
				opacity: 0.7;
			}
		}
	}

	//菜单栏
	.kaluli {
		width: 100%;
		height: 60px;

		.title {
			width: 100px;
			font-size: 1.3rem;
			font-weight: 600;
			height: 60px;
			line-height: 60px;
			margin-left: 23px;
		}

		.right {
			font-size: 1.2rem;
			float: right;
			display: inline-block;
			margin: -41px 16px;
			opacity: 0.7;
		}
	}

	.num {
		width: 100%;
		height: 60px;

		.title {
			width: 100px;
			font-size: .8rem;
			height: 60px;
			line-height: 60px;
			margin-left: 23px;
		}

		.right {
			font-size: 1.2rem;
			float: right;
			display: inline-block;
			margin: -41px 16px;
			opacity: 0.7;
		}

		.show {
			display: flex;


			.left {
				flex: 1;
				height: 70px;
				display: inline-block;


				.minute {
					font-size: .7rem;
					font-weight: lighter;
					margin-left: 22px;
				}

				.number {
					font-stretch: condensed;
					font-size: 1.2rem;
					font-weight: 700;
					display: inline-block;
					margin: 1.2rem 1.2rem;
				}
			}

			.right {
				flex: 1;
				height: 70px;
				margin-top: 0rem;

				.score {
					font-size: .7rem;
					font-weight: lighter;
				}

				.head {
					margin-top: 15px;
					background: url('../../../static/me-icon/head.jpg') no-repeat;
					background-position: -5rem 0rem;
					background-size: 125px 78px;
				}
			}
		}
	}

	.list {
		width: 100%;
		height: 60px;

		.title {
			width: 100px;
			font-size: .8rem;
			height: 60px;
			line-height: 60px;
			margin-left: 23px;
		}

		.right {
			font-size: 1.2rem;
			float: right;
			display: inline-block;
			margin: -41px 16px;
			opacity: 0.7;
		}
	}
</style>
