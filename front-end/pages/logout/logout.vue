<template>
	<view>
		<van-swipe :autoplay="3000" indicator-color="white" align="center" class="white">
			<van-swipe-item><image src="../../static/天空.jpg"></image></van-swipe-item>
			<van-swipe-item><image src="../../static/海鸥.jpg"></image></van-swipe-item>
			<van-swipe-item><image src="../../static/田园.jpg"></image></van-swipe-item>
			<van-swipe-item><image src="../../static/风车.jpg"></image></van-swipe-item>
		</van-swipe>
			
		<van-contact-card
		  type="edit"
		  :name="name"
		  :tel="pnum"
		  :editable="false"
		/>
		<view v-if="id == shop.ownerid">
			<view class="ownerButG">
			    <b-button-group>
			      <b-button variant="success" @click="toOrders">管理预约</b-button>
			      <b-button variant="info" @click="toWorkers">管理员工</b-button>
			      <b-button variant="warning" @click="toSpace"> 管理工作区</b-button>
			    </b-button-group>
			 </view>
			 <view class="ownerButG2">
			     <b-button-group>
			       <b-button variant="info" @click="toShowAll">全部预约列表</b-button>
			       <b-button variant="warning" @click="toCInf"> 历史客户列表</b-button>
			     </b-button-group>
			  </view>
		</view>
		<view  v-else>
			<van-button class="tot" type="primary" @click="toOrders" size="large">管理预约</van-button>
		</view>
		<div class="logout">
			<van-button type="primary" size="large" @click="logout">退出</van-button>
		</div>
	</view>
	
</template>

<script>
	export default {
		data() {
			return {
				id: '',
				pnum: '',
				name: '',
				isowner: false,
				shop: {},
				shopid: '',
			}
		},
		onLoad() {
			this.$toast.loading({
			  	duration: 0,       // 持续展示 toast
			  	forbidClick: true, // 禁用背景点击
			  	message: '加载中'
			});
			
			console.log(this.$cookies.get("id"))
			this.pnum = this.$cookies.get("pnum");
			this.id = this.$cookies.get("id");
			this.shopid = this.$cookies.get("shopid");
			var rp = require('request-promise');
			var options = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/GetUserByIdServlet',
				form: { 
					id: this.$cookies.get("id")
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			var rpOwner = require('request-promise');
			var optionsOwner = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/GetShopByIdServlet',
				form: { 
					shopid: this.$cookies.get("shopid")
				},
				headers: {  
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			rp(options).then(res => {
				if(res != "error"){
					console.log("用户信息读取成功");
					console.log(res);
					var request = JSON.parse(res)
					// POST succeeded...
					this.name = request.name
					
					rpOwner(optionsOwner).then(res2 => {
						this.$toast.clear();
						if(res2 != "error"){
							console.log("店铺信息读取成功");
							console.log(res2)
							this.$toast.success('加载完成');
							var request2 = JSON.parse(res2);
							this.shop = request2;
						}else{
							this.$toast.fail('加载失败请检1查网络连接');
							console.log("店铺信息读取失败");
						}
					}).catch(err2 => {
						this.$toast.clear();
						this.$toast.fail('加载失败请检查2网络连接');
						console.log("店铺信息读取败亡");
						console.error(err2);
					})
				}else{
					console.log("用户信息读取失败");
					console.log(this.$cookies.get("id"))
				}
				
				
			}).catch(err =>{
				console.log("用户信息读取败亡");
				this.$toast.fail('加载失败请检查3网络连接');
				console.error(err);
				// POST failed...
			})		
		},
		methods: {
			toCInf(){
				this.$router.push({
				    path: '/pages/showCustomer/showCustomer', 
				})
			},
			toShowAll(){
				this.$router.push({
				    path: '/pages/showAllOrders/showAllOrders', 
				})
			},
			logout(){
				this.$cookies.remove("id");
				this.$cookies.remove("password");
				this.$cookies.remove("shopid");
				this.$cookies.remove("pnum");
				this.$router.push({
				    path: '/pages/index/index', 
				})
			},
			toWorkers(){
				this.$router.push({
					path: '/pages/index/AddPerson' 
				})
			},
			toSpace(){
				this.$router.push({
					path: '/pages/index/AddRoom' 
				})
			},
			toOrders(){
				this.$router.push({
					path: '/pages/showOrders/showOrders' 
				})
			}
		}
	}
</script>

<style>
	.ownerButG{
		margin: 40rpx;
	}
	.ownerButG2{
		margin: 80rpx;
	}
	.logout{
		position: fixed;
		bottom: 5rpx;
		width: 80%;
		height: 20rpx;
		margin: 10%;
	}
	.tot{
		margin: 10%;
		width: 80%;
	}
	.white{
		padding-top: 50rpx;
	}
</style>
