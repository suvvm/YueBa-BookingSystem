<template>
	<div class="content">
		<image class="logo" src="/static/logo1.png"></image>
		<van-cell-group class="username">
		  <van-field
			v-model="pnum"
			required
			clearable
			label="手机号"
			right-icon="question-o"
			placeholder="请输入用户名"
			@click-right-icon="$toast('就是手机号')"
			rows="1"
			autosize
		  />
		</van-cell-group> 
		
		<van-cell-group class="username">
		  <van-field
			v-model="password"
			type="password"
			label="密	码"
			placeholder="请输入密码"
			required
			:error-message="loginerr"
		  />
		</van-cell-group>

		<van-cell-group class="username">
		  <van-field
			v-model="picCode"
			label="验证码"
			placeholder="请输入验证码"
			required
			:error-message="picerr"
			@blur="picChange"
		  >
		  <van-button  slot="button"  @click="refreshCode" size="small" type="primary">更改验证码</van-button>
		</van-field>
		</van-cell-group>
		
		<view class="code">
			 <s-identify :identifyCode="identifyCode"></s-identify>
		</view>
		<van-button class="submit" type="primary"  size="large" @click="toRegister" plain hairline >立刻注册</van-button>
		<van-button class="submit" type="primary"  size="large" @click="handleClick">确定</van-button>
		<van-button class="submit" type="primary"  size="large" @click="toFVlogin" plain hairline>人脸识别登录</van-button>
	</div>
</template>

<script>
	//<link rel="stylesheet" href="css/common.css"/>
	//<link rel="stylesheet" href="css/login.css"/>

	export default {
		name: "codetest",
		data() { 
			return {
				title: 'Hello',
				show: false,
				pnum: '',
				password: '',
				loginerr: '',
				identifyCodes: '1234567890',
				identifyCode: '',
				picCode: '',
				picerr:''
			}
		},
		onLoad() {
			this.pnum = this.$route.query.pnum
			this.password = this.$route.query.password
			if(this.$cookies.get("pnum") != null){
				if(this.$cookies.get("shopid") != '0'){
					this.$router.push({
						path: '/pages/logout/logout', 
					})
				}else{
					this.$router.push({
						path: '/pages/chooseRole/chooseRole', 
					})
				}
			}
		},
		mounted() {
		  this.identifyCode = "";
		  this.makeCode(this.identifyCodes, 4);
		},
		methods: {
			toFVlogin(){
				this.$router.push({
					path: '/pages/loginFV/loginFV', 
				})
			},
			toRegister(){
				this.$router.push({
					path: '/pages/index/register', 
				})
			},
			picChange(){
				if(this.picCode != this.identifyCode){
					this.picerr = "验证码错误"
				}else{
					this.picerr = ""
				}
			},
			showPopup() { 
				  this.show = true;
				  console.log()
			},
			handleClick(){
				this.$toast.loading({
				  	duration: 0,       // 持续展示 toast
				  	forbidClick: true, // 禁用背景点击
				  	message: '登录中'
				});
				var rp = require('request-promise');
				var options = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/LoginServlet',
					form: { 
						pnum: this.pnum,
						password: this.password
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				if(this.picCode != this.identifyCode){
					this.$toast.clear();
					console.log("验证码错误");
					this.$toast.fail('验证码错误');
				}else{
					rp(options)
						.then(res =>{
							console.log("手机号" + this.pnum);
							console.log("密码" + this.password);
							console.log(res);
							if(res != 'error'){
								var request = JSON.parse(res)
								console.log(request);
								console.log("登录成功");
								console.log("id");
								console.log(request.id);
								console.log("shop")
								console.log(request.shop);
								this.$cookies.set("pnum", this.pnum, 60 * 60  * 24 * 7);
								this.$cookies.set("password", this.password, 60 * 60  * 24 * 7);
								this.$cookies.set("id", request.id, 60 * 60  * 24 * 7);
								this.$cookies.set("shopid", request.shop, 60 * 60 * 24 * 7);
								this.loginerr = '登录成功';
								this.$toast.clear();
								this.$toast.success('登录成功');
								if(request.shop == '0'){
									this.$router.push({
										path: '/pages/chooseRole/chooseRole', 
									})
								}else{
									this.$router.push({
									    path: '/pages/logout/logout', 
									})
								}
							}else{
								this.$toast.clear();
								console.log("用户名或密码错误");
								this.$toast.fail('用户名或密码错误');
								this.loginerr = "用户名或密码错误";
							}
						}).catch(err =>{
							this.$toast.clear();
							this.$toast.fail('登录失败，请检查用户名或密码');
							console.log("败亡");
							console.error(err);
						})
				}
				
			},
			randomNum(min, max) {
			  return Math.floor(Math.random() * (max - min) + min);
			},
			refreshCode() {
			  this.identifyCode = "";
			  this.makeCode(this.identifyCodes, 4);
			},
			makeCode(o, l) {
			  for (let i = 0; i < l; i++) {
			    this.identifyCode += this.identifyCodes[
			      this.randomNum(0, this.identifyCodes.length)
			    ];
			  }
			  console.log(this.identifyCode);
			}
		}
	}
</script>

<style>
	@import 'vant/lib/index.css';
 
	
	.primay{
		width: 20%;
		height: 80upx;
		background-color: #007AFF;
		font-size: 1em;
	}
	.username{
		width: 80%;
		display: block;
		padding:0 0 ;
		background: #ffffff;
		border: 0;
		font-size: 1em;
		font-family: "Microsoft YaHei";
		-webkit-box-shadow: #8f9bad 0px 0px 20px;
		-moz-box-shadow: #8f9bad 0px 0px 20px;
		box-shadow: #8f9bad 0px 0px 20px;
		text-align: center;
		height: 70rpx;
	}	
	.content{
		text-align: center;
		height: 400upx;
		background: #ffffff;
	}
	.logo {
		height: 600upx;
		width: 400upx;
		margin-top: 120upx;
	}
	.title{
		font-size: 36upx;
		color: #8f8f94;
	}
	.username{
	    width: 80%;
	    margin: 3%;
		margin-left: 10%;
	}
	.other_choose{
	    width: 80upx;
	    top:90%;
	    position:absolute;
	    margin: 0 -3%;
		height: 80upx;
	}
	.code{
		margin-left: 60%;
		margin-bottom: -210rpx;
	}
	.submit{
		width: 27%;
		height: 80rpx;

	}
	
</style>
