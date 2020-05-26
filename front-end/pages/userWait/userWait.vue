<template>
	<van-pull-refresh v-model="isLoading" @refresh="onRefresh">
		<view>
			<van-steps :active="active">
				<van-step>注册账号</van-step>
				<van-step>选择角色</van-step>
				<van-step>等待绑定</van-step>
				<van-step>注册完成</van-step>
			</van-steps>
			<view v-if="showNext">
				<van-button type="primary" size="large" @click="submit">下一步</van-button>
			</view>
			<view v-else>
				<p class="rfinfo">下拉刷新以查看绑定状态</p>
			</view>
		</view>
	</van-pull-refresh>
	
</template>

<script>
	export default {
		data() {
			return {
				active: 2,
				showNext: false,
				isLoading: false
			}
		},
		methods: {
			submit(){
				this.$router.push({
					path: '/pages/completeRegister/completeRegister', 
				})
			},
			
			onRefresh() {
				
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
				setTimeout(() => {
					 this.isLoading = false;
					rp(options)
						.then(res =>{
							console.log(res);
							if(res != 'error'){
								var request = JSON.parse(res);
								console.log(res);
								if(request.shop == '0'){
									this.$toast.success('您还没有绑定');
								}else{
									this.$toast.success('绑定完成');
									this.showNext = true;
									this.$cookies.set("shopid", request.shop, 60 * 60 * 24 * 7);
								}
							}else{
								this.$toast.fail('绑定出错');
							}
						}).catch(err =>{
							this.$toast.fail('绑定出错，请检查网络连接');
							console.log("败亡");
							console.error(err);
						})
					this.isLoading = false;		
			    }, 500);
			}
		}
	}
</script>

<style>
.rfinfo{
	margin-top: 300rpx;
	text-align: center;
}
</style>
