<template> 
	<view>
		<div class="form-row">
			<div class="form-group col-md-6">
				<label for="nowDate" class="nowDate">选择预约日期:</label>
				<date-picker :date="nowDate" :option="option" :limit="limit" id="nowDate" ></date-picker>
				<van-button @click="submitDate" type="info" class="submit" size="mini">确认</van-button>
			</div>
			
		</div>
		<scroll-view class="container" scroll-y="true" scroll-x="true"> 
			<table ref="tb" class="table table-bordered table-striped table-sm" >
				<thead>
				     <tr> 
				       <th v-for="(item,index) in space" :key="index">{{item.id}}</th>
				     </tr>
				     </thead>
						
				     <tbody>
				     <tr class="timeTr" v-for="item in times" :key="item">
						
						<td>{{item}}</td>
				
						<td v-bind:class=" isPasasedDate(item,index) ? 'not_finished' : 'finished'" v-for="(n,index) in space.length" :key="index" v-if="index != 0">
							<block v-for="(sord,sindex) in orderlist" :key="sindex">
                            <!-- 我在这里加上style的判断-->
								<block  v-if="(nowDate.time + ' ' + item + ':00') >= sord.bgtime && (nowDate.time + ' ' + item + ':00') <= sord.edtime && space[index].id == orderlist[sindex].space">{{sord.cname}}{{space[index].name}}{{index}}{{sindex}}</block>
							</block>
						</td>
				     </tr>
							 
				     </tbody>
				   </table>
			</scroll-view>
			<van-collapse v-model="activeNames">
			  <van-collapse-item name="1">
			    <div slot="title">新增预约 </div>
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
						  <view v-for="(item,index) in spacelist" :key="index">
						  	<van-radio :name="index">{{item.name}}</van-radio>
						  </view>
						</van-radio-group>
					</van-collapse-item>			
				</van-collapse>
				<van-button type="primary" @click="submit" size="large">提交</van-button>
			  </van-collapse-item>
				
			</van-collapse>
	</view>
</template>

<script>
	import Datepicker from 'vue-datepicker/vue-datepicker-es6.vue'
	import formatTime from "../../js/util.js";
	export default {
		data() {
			return {
				
				times:[
					'06:00', '06:15', '06:30','06:45',
					'07:00', '07:15', '07:30','07:45',
					'08:00', '08:15', '08:30','08:45',
					'09:00', '09:15', '09:30','09:45',
					'10:00', '10:15', '10:30','10:45',
					'11:00', '11:15', '11:30','11:45',
					'12:00', '12:15', '12:30','12:45',
					'13:00', '13:15', '13:30','13:45',
					'14:00', '14:15', '14:30','14:45',
					'15:00', '15:15', '15:30','15:45',
					'16:00', '16:15', '16:30','16:45', 
					'17:00', '17:15', '17:30','17:45',
					'18:00', '18:15', '18:30','18:45',
					'19:00', '19:15', '19:30','19:45',
					'20:00', '20:15', '20:30','20:45',
					'21:00', '21:15', '21:30','21:45',
					'22:00',
				],
				space:[
					{
						id: '时间',
						name: '时间',
						worker: '0'
					}
				],
				shopid: '',
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
				spacelist:[{
						id: '0',
						name: '无',
						worker: '0'
					}],
				cspace:'',
				radio: 0,
				orderlist:[],
				nowDate: {
				    time: ''
				},
				option: {
				    type: 'day',
				    week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
				    month: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
				    format: 'YYYY-MM-DD',
				    placeholder: '请选择日期',
				    inputStyle: {
				        'display': 'inline-block',
				        'padding': '4px',
				        'line-height': '27px',
				        'font-size': '14px',
				        'width': '190px',
				        'border': '1px solid #ddd',
				        //'box-shadow': '0 1px 3px 0 rgba(0, 0, 0, 0.2)',
				        'border-radius': '5px',
				        'color': '#5F5F5F'
				    },
				    color: {
				        header: '#ccc',
				        headerText: '#f00'
				    },
				    buttons: {
				        ok: '确定',
				        cancel: '取消'
				    },
				    overlayOpacity: 0.5, // 0.5 as default
				    dismissible: true // as true as default
				},
				limit: [{ 
				    type: Array,
				    default:function _default(){
				        return [];
				    }
				}]
				
			}
		},
		components: {
		   'date-picker': Datepicker
		},
		onLoad(){
			this.shopid = this.$cookies.get("shopid");
			var rp = require('request-promise');
			var options = {
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
				uri: 'http://101.201.70.76:8080/YueBa/GetOrderByDateAndIdServlet',
				form: { 
					shopid: this.$cookies.get("shopid"),
					date: formatTime.formatTime(new Date())
				},
				headers: {
					/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
				}
			};
			rp(options).then(res => {
				console.log(res);
				
				if(res != 'error'){
					console.log("初始化房间成功");
					var request = JSON.parse(res);
					this.spacelist = this.spacelist.concat(request);
					
					this.space = this.space.concat(request);
					
					rpOrder(optionsOrder).then(res2 => {
						console.log(res2);
						this.$toast.clear();
						if(res2 != 'error'){
							console.log("初始化订单成功");
							var request2 = JSON.parse(res2);
							this.orderlist = request2;
							
						}else{
							console.log("初始化订单失败");
						}
						// POST succeeded...
					}).catch(err2 =>{
						this.$toast.clear();
						console.log("初始化订单化败亡");
						console.error(err2);
						// POST failed...
					});
					
					
				}else{
					console.log("初始化房间失败");
				}
				// POST succeeded...
			}).catch(err =>{
				this.$toast.clear();
				console.log("初始化房间败亡");
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
			onBegintimeCnf(e){
				this.minDate = e;
				console.log(formatTime.formatTime(e))
				this.bgtime = formatTime.formatTime(e);
			},
			onEdtimeCnf(e){
				console.log(formatTime.formatTime(e))
				this.edtime = formatTime.formatTime(e);
			},
			submit(){
				if(this.spacelist[this.radio].id == 0){
					this.$toast("请提交合法房间");
				}else{
					this.$toast.loading({
					  	duration: 0,       // 持续展示 toast
					  	forbidClick: true, // 禁用背景点击
					  	message: '提交中'
					});
					this.shopid = this.$cookies.get("shopid");
					var rp = require('request-promise');
					var options = {
						method: 'POST',
						uri: 'http://101.201.70.76:8080/YueBa/AddOrderServlet',
						form: { 
							shopid: this.$cookies.get("shopid"),
							cname: this.cname,
							pnum: this.cpnum,
							space: this.spacelist[this.radio].id,
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
				}
			},
			submitDate(){
				this.$toast.loading({
				  	duration: 0,       // 持续展示 toast
				  	forbidClick: true, // 禁用背景点击
				  	message: '加载中'
				});
				this.shopid = this.$cookies.get("shopid");
				var rp = require('request-promise');
				var options = {
					method: 'POST',
					uri: 'http://101.201.70.76:8080/YueBa/GetOrderByDateAndIdServlet',
					form: { 
						shopid: this.$cookies.get("shopid"),
						date: this.nowDate.time,
					},
					headers: {
						/* 'content-type': 'application/x-www-form-urlencoded' */ // Is set automatically
					}
				};
				rp(options).then(res => {
					console.log(res);
					this.$toast.clear();
					if(res != 'error'){
						console.log("加载订单成功");
						var request = JSON.parse(res);
						this.orderlist = request;
						this.$toast.success("加载订单成功");
						if(this.nowDate.time + " " + "24:00" < formatTime.formatTime(new Date())){
							console.log(this.nowDate.time + " " + "00:00");
							console.log(formatTime.formatTime(new Date()));
							console.log(this.nowDate.time + " " + "00:00" < formatTime.formatTime(new Date()));
							console.log("历史")
							this.space = [{id: '时间',name: '时间',worker: '0'}];
							for(let i = 0; i < request.length; i++){
								let flag = 0;
								for(let j = 0; j < this.space.length; j++){
									if(this.space[j].name == request[i].space){
										flag = 1;
									}
								}
								if(flag == 0){
									this.space = this.space.concat({ id: request[i].space, name: request[i].space, worker:'0'});
								}
							}
							console.log(this.space)
						}
					}else{
						console.log("加载订单失败");
					}
					// POST succeeded...
				}).catch(err =>{
					this.$toast.clear();
					console.log("加载订单败亡");
					this.$toast.fail("加载订单败亡");
					console.error(err);
					// POST failed...
				});
			},
            isPasasedDate(item,index){//检查这个时间点是否已经过了
                console.log(item + " " + index);
				for(let j = 0; j < this.orderlist.length; j++){
					// console.log("nowbg: " + this.nowDate.time + ' ' + item + ':00')
					// console.log("judbg: " + this.orderlist[j].bgtime)
					// console.log("juded: " + this.orderlist[j].edtime)
					// console.log("sp: "+this.space[index].id)
					// console.log("or: "+this.orderlist[j].space)
					// console.log(this.space[index].id == this.orderlist[j].space)
		
					if((this.nowDate.time + ' ' + item + ':00') >= this.orderlist[j].bgtime && (this.nowDate.time + ' ' + item + ':00') <= this.orderlist[j].edtime && this.space[index].id == this.orderlist[j].space){
							
							// console.log(item);
							return false;
						}
					}
				return true;
            }
		}
	}
</script>

<style>
.container{
	height: 850rpx;
}
.not_finished{
   background-color:white;
}
.finished{
   background-color:red;
}
.submit{
	height: 42rpx;
	margin-left:12rpx;
	font-size: 28rpx;
}
.form-row{
	border: 1rpx solid null;
	padding: 12rpx 5rpx 0rpx 15rpx;
}
.nowDate{
	font-size: 28rpx;
}
</style>
