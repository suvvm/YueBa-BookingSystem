<template>
	<view>
		<van-notice-bar
		  text="点击提交修改前请确保所有员工手机号已经成功注册,请勿添加未注册手机号!!!"
		  left-icon="volume-o"
		/>
	
			<van-contact-card
			  type="add"
			  :name="currentContact.name"
			  :tel="currentContact.tel"
			  @click="showList = true"
			  add-text="添加员工"
			/>
			
			<view v-for="item in list" :key="item.tel">
				<van-contact-card
				  type="edit"
				  :name="item.name"
				  :tel="item.tel"
				  :editable="false"
				/>
			</view>
			<!-- 联系人列表 -->
			<van-button class="submit" type="primary" size="large" @click="submit">提交修改</van-button>
			<van-popup v-model="showList" position="bottom">
			  <van-contact-list
			    v-model="chosenContactId"
			    :list="list"
			    @add="onAdd"
			    @edit="onEdit"
			    @select="onSelect"
			  />
			</van-popup>
			
			<!-- 联系人编辑 -->
			<van-popup v-model="showEdit" position="bottom">
			  <van-contact-edit
			    :contact-info="editingContact"
			    :is-edit="isEdit"
			    @save="onSave"
			    @delete="onDelete"
			  />
			</van-popup>
			
	</view>
</template>

<script>
	export default {
	  data() {
	    return {
			shopid: '',
			chosenContactId: null,
			editingContact: {},
			showList: false,
			showEdit: false,
			isEdit: false,
			list: [],
			isLoading: false
	    };
	  },
	  onLoad() {
		this.$toast.loading({
		  	duration: 0,       // 持续展示 toast
		  	forbidClick: true, // 禁用背景点击
		  	message: '加载中'
		});
	  	this.shopid = this.$cookies.get("shopid");
		var rp = require('request-promise');
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
		rp(options).then(res => {
			console.log(res);
			this.$toast.clear();
			if(res != 'error'){
				console.log("初始化成功");
				var request = JSON.parse(res);
				this.list = request;
			}else{
				console.log("初始化失败");
			}
			// POST succeeded...
		}).catch(err =>{
			this.$toast.clear();
			console.log("初始化败亡，亲检查网络连接");
			console.error(err);
			// POST failed...
		});
	  },
	  computed: {
	    currentContact() {
	      const id = this.chosenContactId;
	      return id !== null ? this.list.filter(item => item.id === id)[0] : {};
	    }
	  },
	
	  methods: {
	    // 添加联系人
	    onAdd() {
	      this.editingContact = { id: this.list.length };
	      this.isEdit = false;
	      this.showEdit = true;
	    },
	
	    // 编辑联系人
	    onEdit(item) {
	      this.isEdit = true;      
	      this.showEdit = true;
	      this.editingContact = item;
	    },
	
	    // 选中联系人
	    onSelect() {
	      this.showList = false;
	    },
	
	    // 保存联系人
	    onSave(info) {
	      this.showEdit = false;
	      this.showList = false;
	      
	      if (this.isEdit) {
	        this.list = this.list.map(item => item.id === info.id ? info : item);
	      } else {
	        this.list.push(info);
	      }
	      this.chosenContactId = info.id;
	    },
	
	    // 删除联系人
	    onDelete(info) {
	      this.showEdit = false;
	      this.list = this.list.filter(item => item.id !== info.id);
	      if (this.chosenContactId === info.id) {
	        this.chosenContactId = null;
	      }
	    },
		submit(){
			this.$toast.loading({
			  	duration: 0,       // 持续展示 toast
			  	forbidClick: true, // 禁用背景点击
			  	message: '提交中'
			});
			var rp = require('request-promise');
			var options = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/ResetWorkersServlet',
				form: { 
					list: JSON.stringify(this.list),
					shopid: this.$cookies.get("shopid")
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			var rp2 = require('request-promise');
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
			rp(options).then(res => {
				console.log(JSON.stringify(this.list));
				
				console.log("成功");
				console.log(res);
				

				rp2(options2).then(res2 => {
					this.$toast.clear();
					console.log(res2);
					if(res != 'error'){
						console.log("加载成功");
						this.$toast.success('提交成功');
						var request2 = JSON.parse(res2);
						this.list = request2;
					}else{
						console.log("加载失败");
						this.$toast.fail('提交失败，请检查员工信息是否合法');
					}
					// POST succeeded...
				}).catch(err2 =>{
					this.$toast.clear();
					console.log("加载败亡");
					console.error(err2);
					this.$toast.fail('提交失败，请检查员工信息是否合法');
					// POST failed...
				});
				// POST succeeded...
			}).catch(err =>{
				console.log(JSON.stringify(this.list));
				this.$toast.clear();
				console.log("提交败亡");
				console.error(err);
				this.$toast.fail('提交失败，请检查网络连接');
				// POST failed...
			})	
			
		},
		// onRefresh() { 
		// 	this.isLoading = false;
		// 	setTimeout(() => {
		// 		
		// 		var rp = require('request-promise');
		// 		var options = {
		// 			method: 'POST',
		// 			uri: 'http://101.201.70.76:8080/YueBa/GetWorkerServlet',
		// 			form: { 
		// 				shopid: this.$cookies.get("shopid"),
		// 			},
		// 			headers: {
		// 				/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
		// 			}
		// 		};
		// 		rp(options).then(res => {
		// 			console.log(res);
		// 			if(res != 'error'){
		// 				console.log("加载成功");
		// 				this.$toast.success('加载成功');
		// 				var request = JSON.parse(res);
		// 				this.list = request;
		// 			}else{
		// 				this.$toast.fail('加载失败，请联系运营人员');
		// 				console.log("加载失败");
		// 			}
		// 			// POST succeeded...
		// 		}).catch(err =>{
		// 			console.log("加载败亡");
		// 			this.$toast.fail('加载败亡，请检查网络连接');
		// 			console.error(err);
		// 			// POST failed...
		// 		});
		// 	}, 500)
		// 	
		// }
	  }
	};
</script>

<style>
	@import 'vant/lib/index.css';
	.submit{
		margin-top: 30rpx;
	}
</style>===
