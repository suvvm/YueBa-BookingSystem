<template>
	<view>
		<table ref="tb" class="table table-bordered table-striped table-sm" >
			<thead>
				 <tr> 
				  <th>客户姓名</th>
				  <th>客户手机</th>
				 </tr>
			</thead>	
			<tbody>
				<tr v-for="(item, index) in clist" :key="index">
					<td>{{item.cname}}</td>
					<td>{{item.pnum}}</td>
				</tr>		 
			</tbody>
		  </table>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				clist: []
			}
		},
		onLoad() {
			this.$toast.loading({
				duration: 0,       // 持续展示 toast
				forbidClick: true, // 禁用背景点击
				message: '加载中'
			});
			var rpOrder = require('request-promise');
			var optionsOrder = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/GetCustomerServlet',
				form: { 
					shopid: this.$cookies.get("shopid"),
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			rpOrder(optionsOrder).then(res => {
				console.log(res);
				this.$toast.clear();
				if(res != 'error'){
					console.log("初始化顾客成功");
					this.$toast.success('加载成功');
					var request = JSON.parse(res);
					this.clist = request;
					
				}else{
					console.log("初始化顾客失败");
					this.$toast.fail('加载失败');
				}
				// POST succeeded...
			}).catch(err =>{
				this.$toast.clear();
				console.log("初始化顾客化败亡");
				this.$toast.fail('加载失败');
				console.error(err);
				// POST failed...
			});
		},
		methods: {
			
		}
	}
</script>

<style>

</style>
