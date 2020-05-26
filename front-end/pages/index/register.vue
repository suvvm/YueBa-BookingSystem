<template>
	<div class="body">
		<div class="point">
		    <span>注册成功后，手机号也可为登录账号。</span>
		</div>
		<!--
			绑定data中数据，在变量名前加：冒号
			绑定方法，在input或其他前加@
		-->
		<van-cell-group>
			<van-field
				v-model="username"
				label="姓名"
				left-icon="contact"
				placeholder="请输入真实姓名"
				
			/>
			<van-field
				v-model="pnum"
				label="手机号"
				left-icon="label-o"
				placeholder="请输入手机号"
				:error-message="errpnum"
				@blur="onPnumChanged"
			/>		
			<van-field
				v-model="password"
				label="密码"
				type="password"
				left-icon="label-o"
				placeholder="请输入6-20位密码"
				maxlength="20"
					
			/>
			<van-field
				v-model="password1"
				type="password"
				label="确认密码"
				left-icon="label-o"
				placeholder="请再次输入密码"
				input="onPswChanged"
				maxlength="20"
				:error-message="err"		
				@blur="onPswChanged"		
			/>						
		</van-cell-group>
				
		<van-cell-group>
			<van-field
				v-model="sms"
				center
				clearable
				label="短信验证码"
				left-icon="label-o"
				placeholder="请输入短信验证码">
				<van-button slot="button" size="small" :disabled="disabled" @click="sentIdentify" type="primary">
					<block v-if="count == 60">发送验证码</block>
					<block v-else>
						{{count}}
					</block>
					
					</van-button>
			</van-field>
		</van-cell-group>
		<text>上传图片</text>
		<van-uploader :after-read="afterRead" capture="camera"  multiple :max-count="1"/>
		<text v-if="imgfile != ''">选择完成</text>
		<van-checkbox v-model="checked">同意<van-button @click="showPopup" class="xieyi">《注册协议》</van-button></van-checkbox>
			<van-popup v-model="show" >《注册协议》</van-popup>

		<van-button class="submit" type="submit" @click="handleClick">注册</van-button>				
						
	</div>
</template>

<script>
	import { Field } from 'vant';
	export default {
	  data() {
	    return {
		  disabled: false,
	      checked: true,
		  show: false,
		  username: '',
		  pnum:'',
		  password: '',
		  password1:'',
		  sms:'',
		  err:'',
		  errpnum:'',
		  identifyCode: '123',
		  imgfile:'',
		  time: 0,
		  count: 60
		  
	    };
	  },
	  methods:{
		afterRead(file) {
	         //此时可以自行将文件上传至服务器
			  lrz(file.file, {
					quality: 0.7 
				}).then(rst=> {
			   // 处理成功会执行
					this.imgfile = rst.base64.slice(23);	 
			  }).catch(err => {
			             // 处理失败会执行
					this.$toast.fail("上传失败")
			  })

			this.imgfile = file.content.slice(23);
	        console.log(file.content.slice(23));
		},
	    showPopup() {
	      this.show = true;
	    },
		onPswChanged(){
			if(this.password != this.password1){
				this.err = "两次输入不一致"
			}else{
				this.err = ""
			}
		},
		onPnumChanged(){
			if(this.pnum.length != 11){
				this.errpnum = "手机号格式错误"
			}else{
				this.errpnum = ""
			}
		},
		handleClick(){
			this.$toast.loading({
			  	duration: 0,       // 持续展示 toast
			  	forbidClick: true, // 禁用背景点击
			  	message: '注册中'
			});
			 if(this.identifyCode != this.sms){
				this.$toast.clear();
			 	this.$toast.fail("短信验证码错误")
			 }else{
				 
				var rp = require('request-promise');
				var options = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/RegisterServlet',
					form: { 
						name: this.username,
						pnum: this.pnum,
						password: this.password,
						img: this.imgfile
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				rp(options).then(res => {
					console.log(res);
					this.$toast.clear();
					console.log("注册成功");
					this.$toast.success("注册成功")
					console.log(res);
					// POST succeeded...
					 this.$router.push({  
					    path: '/pages/index/index', 
					    query: {   
							pnum: this.pnum,   
					        password: this.password  
					    }
					 })
				}).catch(err =>{
					this.$toast.clear();
					console.log("败亡");
					console.$toast.fail("注册失败");
					console.error(err);
					// POST failed...
				})	
			}
		},
		sentIdentify(){
			const TIME_COUNT = 60;
			if (!this.time) {
			  this.count = TIME_COUNT;
			  this.disabled = true;
			  this.time = setInterval(() => {
			  if (this.count > 0 && this.count <= TIME_COUNT) {
			    this.count--;
			   } else {
			    this.disabled = false;
			    clearInterval(this.time);
			    this.time = 0;
				this.count = TIME_COUNT
			   }
			  }, 1000)
			 }
			
			
			
			var i = Math.random()*(999999-100000)+100000; 
			var j = parseInt(i,10); 
			this.identifyCode = j;
			console.log("随机" + j)
			var rp = require('request-promise');
			var options = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/SmsServlet',
				form: { 
					pnum: this.pnum,
					code: "{\"code\":\""+j+"\"}"
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			rp(options).then(res => {
				console.log(res);
				if(res != 'error'){
					console.log("发送成功");
					this.$toast.success('发送成功');
				}else{
					this.$toast.fail('发送失败');
					console.log("发送失败");
				}
				// POST succeeded...
			}).catch(err =>{
				console.log("发送败亡");
				this.$toast.fail('发送败亡');
				console.error(err);
				// POST failed...
			});
		}
		
		}
	  
	};	

</script>

<style>
	@import 'vant/lib/index.css';
	
	.body{
		background: #efeff4;
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0px;
		bottom: 0px;
	}
	.point{
	    padding: 6% 5%;
	}
	.xieyi{
		background: #EFEFF4;
		font-size:18px; 
		font-style: italic;
		
	}
	.content form input:not(:nth-child(6)){
	    border: 0;
	    border-bottom: 1px solid #c3c3c5;
	}
	.content form{
	    width: 100%;
	    height: 35.21%;
	} 
	.message{
	    background: #ffffff;
	    padding:2% 5% 0 5%;
	    position: relative;
	}
	.message input{
	    width: 90%;
	    padding: 4% 0 4% 10%;
	    font-size: 0.875em;
	    font-family: "Microsoft YaHei";
	}
	.message .icons b{
	    position: absolute;
	    width: 3.75%;
	    height: 4%;
	    top: 7%;
	    left: 7%;
	}
	.message .icons b img{
	    width: 100%;
	}
	.message .icons b:nth-child(2){
	    width: 5%;
	    top: 26%;
	    left: 6%;
	}
	.message .icons b:nth-child(3){
	    top: 43%;
	}
	.message .icons b:nth-child(4){
	    top: 61%;
	}
	.code{
	    position: absolute;
	    top: 60%;
	    right:10%;
	    color: #21a9f5;
	    font-size: 0.875em;
	    font-family: "Microsoft YaHei";
	}
	select{
	    width: 33%;
	    padding: 2% 1%;
	    margin: 5%;
	    font-size: 1em;
	    color: #909093;
	    border: solid 1px #909093;
	    font-family: "Microsoft YaHei";
	}
	.agree{
	    margin: 6% 5%;
	}

	.submit{
	    width: 84%;
	    margin: 2% 8%;
	    background: #21a9f5;
	    color: #ffffff;
	    border: 0;
	    font-size: 1em;
	    font-family: "Microsoft YaHei";
	}
	form .agree input[type="checkbox"] :default{
	    outline: 2px solid #21a9f5;
	}
	
</style>
