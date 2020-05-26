<template>
	<view>
		<view>
			<van-steps :active="active">
			  <van-step>注册账号</van-step>
			  <van-step>选择角色</van-step>
			  <van-step>等待绑定</van-step>
			  <van-step>注册完成</van-step>
			</van-steps>
		</view>
		<van-checkbox v-model="checked">同意<van-button @click="showPopup" class="xieyi">《开店声明》</van-button></van-checkbox>
		<van-popup v-model="show" >《开店声明》</van-popup>
		<van-button type="primary" size="large" @click="submit">下一步</van-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				checked: false,
				show: false,
				active: 2,
				id: ''
			}
		},
		onLoad() {
			this.id = this.$cookies.get("id");
		},
		methods: {
			showPopup() {
			  this.show = true;
			},
			submit(){
				if(!this.checked){
					this.$toast.fail('请阅读并同意《开店声明》');
				}else{
					this.$toast.loading({
						duration: 0,       // 持续展示 toast
						forbidClick: true, // 禁用背景点击
						message: '提交中'
					});
					var rp = require('request-promise');
					var options = {
						method: 'POST',
						uri: 'http://101.201.70.76:8080/YueBa/AddShopServlet',
						form: { 
							ownerid: this.$cookies.get("id")
						},
						headers: {
							/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
						}
					};
					rp(options).then(res => {// POST succeeded...
						console.log(res);
						if(res != 'error'){
							this.$toast.clear();
							console.log("成功");
							var request = JSON.parse(res);
							this.$router.push({
							    path: '/pages/completeRegister/completeRegister', 
							})
							this.$cookies.set("shopid", request.shopid, 60 * 60 * 24 * 7);
						}else{
							this.$toast.clear();
							console.log("败亡");
							this.$toast('失败');
						}
						
					}).catch(err =>{// POST failed...
						this.$toast.clear();
						console.log("败亡");
						console.error(err);
						this.$toast('失败，请检查网络连接');
					})
				}
			}
		}
	}
</script>

<style>

</style>
