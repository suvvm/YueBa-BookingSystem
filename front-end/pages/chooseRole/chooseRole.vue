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
		<van-radio-group v-model="radio">
		  <van-cell-group>
		    <van-cell title="我是老板" clickable @click="radio = '1'">
		      <van-radio slot="right-icon" name="1" />
		    </van-cell>
		    <van-cell title="我是员工" clickable @click="radio = '2'">
		      <van-radio slot="right-icon" name="2" />
		    </van-cell>
		  </van-cell-group>
		</van-radio-group>
		<van-button type="primary" size="large" @click="submit">下一步</van-button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				radio: '1',
				active: 1
			}
		},
		methods: {
			submit(){
				if(this.radio == '1'){
					this.$router.push({
					    path: '/pages/createShop/createShop', 
					})
				}else{
					this.$router.push({
					    path: '/pages/userWait/userWait', 
					})
				}
			}
		}
	}
</script>

<style>
	@import 'vant/lib/index.css';
</style>
