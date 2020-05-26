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
			@click-right-icon="$toast('请按正确格式输入手机号')"
			rows="1"
			autosize
		  />
		</van-cell-group> 

		<van-uploader :after-read="afterRead">
		  <van-button class="submit" icon="photo"  type="primary" size="large" >人脸识别登录</van-button>
		</van-uploader>
		<van-button class="submit"  plain hairline size="large"  @click="toRegister"  type="primary">立刻注册</van-button>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				pnum: '',
				imgfile: '',
				err: '',
			}
		},
		methods: {
			afterRead(file) {
				this.$toast.loading({
				  	duration: 0,       // 持续展示 toast
				  	forbidClick: true, // 禁用背景点击
				  	message: '识别中'
				});
				console.log(file)
				//window.URL.createObjectURL(new Blob(file, {type: "application/zip"}))
				lrz(file.file, {
					quality: 0.7 
				}).then(rst=> {
				      //成功时执行
					  console.log(rst.base64);
					  
					   this.imgfile = rst.base64.slice(23);
					   var rp = require('request-promise');
					   var options = {
					   	method: 'POST',
					   	uri: 'http://101.201.70.76:8080/YueBa/FVerifyServlet',
					   	form: { 
					   		pnum: this.pnum,
					   		type: 1,
					   		content_1: rst.base64.slice(23)
					   	},
					   	headers: {
					   		/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					  	}
					  };
					  var rplg = require('request-promise');
					  var optionslg = {
					  	method: 'POST',
					  	uri: 'http://101.201.70.76:8080/YueBa/LoginPnumOnlyServlet',
					  	form: { 
					  		pnum: this.pnum,
					  		password: this.password
					  	},
					  	headers: {
					  		/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					  	}
					  };
					  rp(options).then(res => {
		
					  	console.log("人脸成功");
					  	console.log(res);
					  	if(res != 'error'){
					  		var request = JSON.parse(res);
					  		if(request.confidence >= 80){
								rplg(optionslg)
									.then(res2 =>{
										this.$toast.clear();
										console.log("手机号" + this.pnum);
										console.log("密码" + this.password);
										console.log(res2);
										if(res2 != 'error'){
											var request2 = JSON.parse(res2)
											console.log(request2);
											console.log("登录成功");
											console.log("id");
											console.log(request2.id);
											console.log("shop")
											console.log(request2.shop);
											this.$cookies.set("pnum", this.pnum, 60 * 60  * 24 * 7);
											this.$cookies.set("id", request2.id, 60 * 60  * 24 * 7);
											this.$cookies.set("shopid", request2.shop, 60 * 60 * 24 * 7);
											this.loginerr = '登录成功';
											this.$toast.success('登录成功');
											if(request2.shop == '0'){
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
											this.$toast.fail('登录失败，请检查网络连接');
											this.loginerr = "用户名或密码错误";
										}
									}).catch(err =>{
										this.$toast.clear();
										this.$toast.fail('登录失败，请检查网络连接');
										console.log("败亡");
										console.error(err);
									})

					  		}else{
					  			this.$toast.fail("非本人")
					  		}
					  	}
					  	// POST succeeded...
					  }).catch(err =>{
					  	this.$toast.clear();
					  	this.err = err
					  	this.$toast.fail("识别失败，请重新识别")
					  	console.log("人脸败亡");
					  	// POST failed...
					  });
					  
				}).catch(error => {
				     //失败时执行
					  console.log(error);
				})
	
				
			   
			},
			 toRegister(){
			 	this.$router.push({
			 		path: '/pages/index/register', 
			 	})
			 },
		}
	}
</script>

<style>
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
	}	
	.content{
		text-align: center;
		height: 400upx;
		background: #ffffff;
	}
	.logo {
		height: 600upx;
		width: 400upx;
		margin-top: 100upx;
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
	.submit{
		margin-top: 10rpx;
		width: 600rpx;
	}
</style>
