<template>
	<div>
		<van-pull-refresh v-model="isLoading" @refresh="onRefresh">
		<view v-for="item in roomList"  :key="item.name">
			<van-card
				:desc="item.name"
				title="房间名称"
			>
			<div slot="tags">
				<text>值班员工编号:</text>
			    <van-tag plain type="danger">{{item.worker}}</van-tag>
			  </div>
			<div slot="footer">
				<van-button :data-roomid="item.id" :data-roomname="item.name" size="mini" @click="showMdf">修改</van-button>
				<van-button :data-roomid="item.id" size="mini" @click="dleRoomSubmit">删除</van-button>
			</div>
			</van-card>
		</view>
		<van-button class="submit" type="primary" size="large" @click="showAdd" >添加房间</van-button>
		</van-pull-refresh>
		
		<van-popup v-model="addNew" position="bottom">
			<van-cell-group>
			  <van-field
			    v-model="name"
			    label="房间名"
			    placeholder="请输入房间名"
			  />
			  
			<van-radio-group v-model="radio">
				<view v-for="(item,index) in workers" :key="index">
					<van-radio :name="index">{{item.id}}</van-radio>
				</view>
			</van-radio-group>
			
			  <van-button type="primary" size="large" @click="addRoomSubmit" >添加</van-button>
			</van-cell-group>
		</van-popup>
		<van-popup v-model="mdf" position="bottom">
			<van-cell-group>
			  <van-field
			    v-model="name"
			    label="房间名"
			    placeholder="请输入房间名"
			  />
			<van-radio-group v-model="radio">
				<view v-for="(item,index) in workers" :key="item.id">
					<van-radio :name="index">{{item.id}}</van-radio>
				</view>
			</van-radio-group>
			  <van-button type="primary" size="large" @click="mdfRoomSubmit" >修改</van-button>
			</van-cell-group>
		</van-popup>
	</div>
</template>

<script>
	export default {
		//el:"#app1",
		data() {
			return {
				chooseRoomId:'',
				radio: 0,
				name:'',
				roomList:[],
				addWorker:{name:"无",tel:"0",id:'无'},
				addNew: false,
				workers:[
					{name:"无",tel:"0",id:'无'},
				],
				mdf: false,
				isLoading: false
				
			}
		},
		onLoad() {
			this.$toast.loading({
			  	duration: 0,       // 持续展示 toast
			  	forbidClick: true, // 禁用背景点击
			  	message: '加载中'
			});
			var rp = require('request-promise');
			var rpRoom = require('request-promise');
			var options = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/GetWorkerServlet',
				form: { 
					shopid: this.$cookies.get("shopid"),
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			var optionsRoom = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/GetSpaceServlet',
				form: { 
					shopid: this.$cookies.get("shopid"),
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			rp(options).then(res => {
				console.log(res);
				if(res != 'error'){
					console.log("初始化员工成功");
					var request = JSON.parse(res);
					this.workers = this.workers.concat(request);
					console.log(this.workers);
					//继续初始化房间
					rpRoom(optionsRoom).then(res2 => {
						console.log(res2);
						this.$toast.clear();
						if(res2 != 'error'){
							console.log("初始化房间成功");
							var request2 = JSON.parse(res2);
							this.roomList = request2;							
						}else{
							console.log("初始房间化失败");
						}
						// POST succeeded...
					}).catch(err2 =>{
						this.$toast.clear();
						console.log("初始化房间败亡");
						console.error(err2);
						// POST failed...
					});
				}else{
					this.$toast.clear();
					console.log("初始化员工失败");
				}
				// POST succeeded...
			}).catch(err =>{
				this.$toast.clear();
				console.log("初始化员工败亡");
				console.error(err);
				// POST failed...
			});
		},
		methods:{
			showAdd(){
				this.addNew = true;
			},
			showMdf(e){
				this.name = e.currentTarget.dataset.roomname;
				this.chooseRoomId = e.currentTarget.dataset.roomid;
				this.mdf = true;
			},
			addRoomSubmit(){
				//console.log("员工" + this.workers[this.radio].id);
				var j = 0;
				if(this.workers[this.radio].id != '无'){
					j = this.workers[this.radio].id
				}
				this.$toast.loading({
				  	duration: 0,       // 持续展示 toast
				  	forbidClick: true, // 禁用背景点击
				  	message: '提交中'
				});
				var rp = require('request-promise');
				var options = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/AddSpaceServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
						name: this.name,
						worker: this.workers[this.radio].id
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				var rp2 = require('request-promise');
				var rpRoom = require('request-promise');
				var options2 = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/GetWorkerServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				var optionsRoom = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/GetSpaceServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				rp(options).then(res => {
					console.log(res);
					this.$toast.clear();
					if(res != 'error'){
						console.log(this.workers[parseInt(this.radio)].id)
						console.log("提交成功");
						this.$toast('提交成功');

						rp2(options2).then(res3 => {
							console.log(res3);
							if(res3 != 'error'){
								console.log("初始化员工成功");
								var request = JSON.parse(res3);
								
								this.workers = request;
								this.workers = this.workers.concat({name:"无",tel:"0",id:0})
								
								console.log(this.workers);
								//继续初始化房间
								rpRoom(optionsRoom).then(res2 => {
									console.log(res2);
									if(res2 != 'error'){
										console.log("初始化房间成功");
										this.$toast.success('刷新成功');
										var request2 = JSON.parse(res2);
										this.roomList = request2;							
									}else{
										this.$toast.fail('刷新失败');
										console.log("初始房间化失败");
									}
									// POST succeeded...
								}).catch(err2 =>{
									this.$toast.fail('刷新失败');
									console.log("初始化房间败亡");
									console.error(err2);
									// POST failed...
								});
							}else{
								this.$toast.fail('刷新失败');
								console.log("初始化员工失败");
							}
							// POST succeeded...
						}).catch(err3 =>{
							this.$toast.fail('刷新失败');
							console.log("初始化员工败亡");
							console.error(err3);
							// POST failed...
						});
						
						
						
					}else{
						console.log("提交失败");
						this.$toast('提交失败');
					}
					// POST succeeded...
				}).catch(err =>{
					this.$toast.clear();
					console.log("提交败亡");
					this.$toast('提交失败');
					console.error(err);
					// POST failed...
				});
			},
			mdfRoomSubmit(){
				this.$toast.loading({
				  	duration: 0,       // 持续展示 toast
				  	forbidClick: true, // 禁用背景点击
				  	message: '提交中'
				});
				var rp2 = require('request-promise');
				var rpRoom = require('request-promise');
				var options2 = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/GetWorkerServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				var optionsRoom = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/GetSpaceServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				var rp = require('request-promise');
				var options = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/MdfSpaceServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
						name: this.name,
						worker: this.workers[this.radio].id,
						id: this.chooseRoomId
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				rp(options).then(res => {
					console.log(res);
					this.$toast.clear();
					if(res != 'error'){
						console.log("修改成功");
						this.$toast('修改成功');
						
						rp2(options2).then(res3 => {
							console.log(res3);
							if(res3 != 'error'){
								console.log("初始化员工成功");
								var request = JSON.parse(res3);
								
								this.workers = request;
								this.workers = this.workers.concat({name:"无",tel:"0",id:0})
								
								console.log(this.workers);
								//继续初始化房间
								rpRoom(optionsRoom).then(res2 => {
									console.log(res2);
									if(res2 != 'error'){
										console.log("初始化房间成功");
										this.$toast.success('刷新成功');
										var request2 = JSON.parse(res2);
										this.roomList = request2;							
									}else{
										this.$toast.fail('刷新失败');
										console.log("初始房间化失败");
									}
									// POST succeeded...
								}).catch(err2 =>{
									this.$toast.fail('刷新失败');
									console.log("初始化房间败亡");
									console.error(err2);
									// POST failed...
								});
							}else{
								this.$toast.fail('刷新失败');
								console.log("初始化员工失败");
							}
							// POST succeeded...
						}).catch(err3 =>{
							this.$toast.fail('刷新失败');
							console.log("初始化员工败亡");
							console.error(err3);
							// POST failed...
						});
						
						
					}else{
						console.log("修改失败");
						this.$toast('修改失败');
					}
					// POST succeeded...
				}).catch(err =>{
					this.$toast.clear();
					console.log("修改败亡");
					this.$toast('修改失败');
					console.error(err);
					// POST failed...
				});
			},
			dleRoomSubmit(e){
				this.$toast.loading({
				  	duration: 0,       // 持续展示 toast
				  	forbidClick: true, // 禁用背景点击
				  	message: '提交中'
				});
				var rp2 = require('request-promise');
				var rpRoom = require('request-promise');
				var options2 = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/GetWorkerServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				var optionsRoom = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/GetSpaceServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				var rp = require('request-promise');
				var options = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/DltServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
						type: 'space',
						rmid: e.currentTarget.dataset.roomid
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				rp(options).then(res => {
					console.log(res);
					this.$toast.clear();
					if(res != 'error'){
						console.log("删除成功");
						this.$toast('删除成功');
						
						rp2(options2).then(res3 => {
							console.log(res3);
							if(res3 != 'error'){
								console.log("初始化员工成功");
								var request = JSON.parse(res3);
								
								this.workers = request;
								this.workers = this.workers.concat({name:"无",tel:"0",id:0})
								
								console.log(this.workers);
								//继续初始化房间
								rpRoom(optionsRoom).then(res2 => {
									console.log(res2);
									if(res2 != 'error'){
										console.log("初始化房间成功");
										this.$toast.success('刷新成功');
										var request2 = JSON.parse(res2);
										this.roomList = request2;							
									}else{
										this.$toast.fail('刷新失败');
										console.log("初始房间化失败");
									}
									// POST succeeded...
								}).catch(err2 =>{
									this.$toast.fail('刷新失败');
									console.log("初始化房间败亡");
									console.error(err2);
									// POST failed...
								});
							}else{
								this.$toast.fail('刷新失败');
								console.log("初始化员工失败");
							}
							// POST succeeded...
						}).catch(err3 =>{
							this.$toast.fail('刷新失败');
							console.log("初始化员工败亡");
							console.error(err3);
							// POST failed...
						});
						
						
					}else{
						console.log("删除失败");
						this.$toast('删除失败');
					}
					// POST succeeded...
				}).catch(err =>{
					this.$toast.clear();
					console.log('删除败亡');
					this.$toast('删除失败');
					console.error(err);
					// POST failed...
				});
			},
			onRefresh(){
				setTimeout(() => {
				   
				    this.isLoading = false;
			
					var rp = require('request-promise');
					var rpRoom = require('request-promise');
					var options = {
						method: 'POST',
						uri: 'http://101.201.70.76:8080/YueBa/GetWorkerServlet',
						form: { 
							shopid: this.$cookies.get("shopid"),
						},
						headers: {
							/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
						}
					};
					var optionsRoom = {
						method: 'POST',
						uri: 'http://101.201.70.76:8080/YueBa/GetSpaceServlet',
						form: { 
							shopid: this.$cookies.get("shopid"),
						},
						headers: {
							/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
						}
					};
					rp(options).then(res => {
						console.log(res);
						if(res != 'error'){
							console.log("初始化员工成功");
							var request = JSON.parse(res);
							this.workers = this.workers.concat(request);
							console.log(this.workers);
							//继续初始化房间
							rpRoom(optionsRoom).then(res2 => {
								console.log(res2);
								if(res2 != 'error'){
									console.log("初始化房间成功");
									this.$toast.success('刷新成功');
									var request2 = JSON.parse(res2);
									this.roomList = request2;							
								}else{
									this.$toast.fail('刷新失败');
									console.log("初始房间化失败");
								}
								// POST succeeded...
							}).catch(err2 =>{
								this.$toast.fail('刷新失败');
								console.log("初始化房间败亡");
								console.error(err2);
								// POST failed...
							});
						}else{
							this.$toast.fail('刷新失败');
							console.log("初始化员工失败");
						}
						// POST succeeded...
					}).catch(err =>{
						this.$toast.fail('刷新失败');
						console.log("初始化员工败亡");
						console.error(err);
						// POST failed...
					});
				}, 500);
			}
		}	
	}	
</script>

<style>
	@import 'vant/lib/index.css';
	.submit{
		margin-top: 30rpx;
	}
</style>

