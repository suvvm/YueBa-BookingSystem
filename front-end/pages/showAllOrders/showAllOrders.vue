<template>
	<view>
		<van-pull-refresh v-model="isLoading" @refresh="onRefresh">
		 <scroll-view class="container" scroll-x="true">
		 	<table ref="tb" class="table table-bordered table-striped table-sm" >
		 		<thead>
		 			 <tr> 
		 			  <th>客户姓名</th>
		 			  <th>客户手机</th>
		 			  <th>预定位置</th>
		 			  <th>开始时间</th>
		 			  <th>结束时间</th>
		 			 </tr>
		 		</thead>
		 		<tbody>
		 			<tr v-for="(item, index) in orderlist" :key="index"  @click="mdforder(index)">
		 				<td>{{item.cname}}</td>
		 				<td>{{item.pnum}}</td>
		 				<td>{{item.space}}</td>
		 				<td>{{item.bgtime}}</td>
		 				<td>{{item.edtime}}</td>
		 			</tr>		 
		 		</tbody>
		 	  </table>
		 </scroll-view>
		</van-pull-refresh>
		
		
		<van-popup v-model="show" position="bottom"> 
			<van-collapse v-model="activeNames">
			  <van-collapse-item name="0">
			    <div slot="title">修改预约 </div>
				<van-cell-group>
				  <van-field
				    v-model="cname"
				    required
				    clearable
				    label="客户姓名"
				    right-icon="question-o"
				    placeholder="请输入客户姓名"
				    @click-right-icon="$toast('question')"
				  />
				
				  <van-field
				    v-model="cpnum"
				    label="客户电话"
				    placeholder="请输入客户电话"
				    required
				  />
				</van-cell-group>
				
				<van-collapse v-model="beginActiveNames">
					<van-collapse-item title="开始时间" name="1" v-model="bgtime" :placeholder="bgtime" required>
						<van-cell-group>
							<van-datetime-picker
							   v-model="currentDatebg"
							   type="datetime"
							   :filter="filter"
							   :formatter="formatter"
							   @confirm="onBegintimeCnf"
							/>
						</van-cell-group>
					</van-collapse-item>		
				</van-collapse>
				<van-collapse v-model="endActiveNames">
					<van-collapse-item title="结束时间" name="1" v-model="edtime" :placeholder="edtime" required>
						<van-cell-group>
							<van-datetime-picker
							   v-model="currentDateed"
							   type="datetime"
							   :min-date="minDate"
							   :filter="filter"
							   :formatter="formatter"
							   @confirm="onEdtimeCnf"
							/>
						</van-cell-group>
					</van-collapse-item>		
				</van-collapse>
				<van-collapse v-model="activeNamesInner">
					<van-collapse-item name="1">
						<div slot="title">选择房间 </div>
						<van-radio-group v-model="radio">
						  <view v-for="(item,index) in slist" :key="index">
						  	<van-radio :name="index">{{item.name}}</van-radio>
						  </view>
						</van-radio-group>
					</van-collapse-item>			
				</van-collapse>
				<van-button type="primary" @click="submit" size="large">提交</van-button>
			  </van-collapse-item>
				
			</van-collapse>
		</van-popup>
	</view>
</template>

<script>
	import formatTime from "../../js/util.js";
	export default {
		data() {
			return {
				isLoading: false,
				orderlist: [],
				show: false,
				slist:[],
				activeNames: ['0'],
				activeNamesInner: ['1'],
				beginActiveNames:['1'],
				endActiveNames:['1'],
				cname: '',
				cpnum: '',
				currentDatebg: new Date(),
				currentDateed: new Date(),
				bgtime:'',
				edtime:'',
				minDate:new Date(),
				cspace:'',
				radio: 0,
				nowDate: {
				    time: ''
				},
			}
		},
		onLoad() {
			
			this.$toast.loading({
				duration: 0,       // 持续展示 toast
				forbidClick: true, // 禁用背景点击
				message: '加载中',
			});
			
			var rpSpace = require('request-promise');
			var optionsSpace = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/GetSpaceServlet',
				form: { 
					shopid: this.$cookies.get("shopid"),
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			var rpOrder = require('request-promise');
			var optionsOrder = {
				method: 'POST',
				uri: 'http://101.201.70.76:8080/YueBa/GetOrderServlet',
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
					console.log("初始化订单成功");
					this.$toast.success('加载成功');
					var request = JSON.parse(res);
					this.orderlist = request;
					
					rpSpace(optionsSpace).then(res2=> {
						console.log(res2);
						this.$toast.clear();
						if(res2 != 'error'){
							console.log("初始化订单成功");
							this.$toast.success('加载成功');
							var request2 = JSON.parse(res2);
							this.slist = request2;
							
						}else{
							console.log("初始化订单失败");
							this.$toast.fail('加载失败');
						}
						// POST succeeded...
					}).catch(err2 =>{
						this.$toast.clear();
						console.log("初始化订单化败亡");
						this.$toast.fail('加载失败');
						console.error(err2);
						// POST failed...
					});
					
					
					
				}else{
					console.log("初始化订单失败");
					this.$toast.fail('加载失败');
				}
				// POST succeeded...
			}).catch(err =>{
				this.$toast.clear();
				console.log("初始化订单化败亡");
				this.$toast.fail('加载失败');
				console.error(err);
				// POST failed...
			});
		},
		methods: {
			formatter(type, value) {
				if (type === 'year') {
					return `${value}年`;
				} else if (type === 'month') {
					return `${value}月`;
				}else if (type === 'day'){
					return `${value}日`;
				}else if (type === 'hour'){
					return `${value}时`;
				}else if (type === 'minute'){
					return `${value}分`
				}
				return value;
			},
			filter(type, options) {
				if (type === 'minute') {
					return options.filter(option => option % 15 === 0)
				}
				return options;
			},
			mdforder(index){
				this.show = true;
				this.cname = this.orderlist[index].cname;
				this.cpnum = this.orderlist[index].pnum;
				this.bgtime = this.orderlist[index].bgtime;
				this.edtime = this.orderlist[index].edtime;
				this.mdfid = this.orderlist[index].id;
				console.log(this.mdfid);
				let spnum = 0;
				for(let i = 0; i < this.cspace; i++){
					if(this.orderlist[index].space == this.cspace[i]){
						spnum = i;
						break;
					}
				}
				this.radio = spnum;
				console.log(index);
			},
			submit(){
				console.log( this.$cookies.get("shopid"))
				console.log( this.mdfid)
				console.log(  this.cname)
				console.log( this.cpnum)
				console.log( this.slist[this.radio].id)
				console.log(  this.bgtime)
				console.log(  this.edtime)
				this.$toast.loading({
				  	duration: 0,       // 持续展示 toast
				  	forbidClick: true, // 禁用背景点击
				  	message: '提交中'
				});
				this.shopid = this.$cookies.get("shopid");
				var rp = require('request-promise');
				var options = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/MdfOrderServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
						orderid: this.mdfid,
						cname: this.cname,
						pnum: this.cpnum,
						space: this.slist[this.radio].id,
						bgtime: this.bgtime,
						edtime: this.edtime
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				rp(options).then(res => {
					console.log(res);
					this.$toast.clear();
					if(res != 'error'){
						console.log("提交成功");
						this.$toast.success("提交成功");
					}else{
						console.log("提交失败");
						this.$toast.fail("提交失败");
					}
					// POST succeeded...
				}).catch(err =>{
					this.$toast.clear();
					console.log("提交败亡");
					this.$toast.fail("提交败亡");
					console.error(err);
					// POST failed...
				});
			},
			onRefresh(){
				 setTimeout(() => {
				    this.isLoading = false;
					var rpOrder = require('request-promise');
					var optionsOrder = {
						method: 'POST',
						uri: 'http://101.201.70.76:8080/YueBa/GetOrderServlet',
						form: { 
							shopid: this.$cookies.get("shopid"),
						},
						headers: {
							/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
						}
					};
					rpOrder(optionsOrder).then(res => {
						console.log(res);
						if(res != 'error'){
							console.log("初始化订单成功");
							this.$toast.success('加载成功');
							var request = JSON.parse(res);
							this.orderlist = request;				
						}else{
							console.log("初始化订单失败");
							this.$toast.fail('加载失败');
						}
						// POST succeeded...
					}).catch(err =>{
						console.log("初始化订单化败亡");
						this.$toast.fail('加载失败');
						console.error(err);
						// POST failed...
					});
				}, 500);
			}
			
		}
	}
</script>

<style>
.table{
	width: 1000rpx;
}
</style>
