<template>
	<div :style='{"width":"100%","padding":"20px 10% 40px","margin":"10px auto","position":"relative","background":"none"}'>





















		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">杩斿洖</span>
			</el-button>
		</div>
		<div class="section-title" :style='{"padding":"0","borderColor":"#e61f4d","margin":"10px auto","color":"#46AC2E","textAlign":"center","display":"block","background":"url() no-repeat left center / 40px 100%","borderWidth":"0 0 0px","width":"100%","lineHeight":"32px","fontSize":"30px","borderStyle":"solid","fontWeight":"600"}'>鎴戠殑璁㈠崟</div>
		<el-tabs v-model="activeName" @tab-click="handleClick">
			<el-tab-pane label="鏈敮浠? name="鏈敮浠?></el-tab-pane>
			<el-tab-pane label="宸叉敮浠? name="宸叉敮浠?></el-tab-pane>
			<el-tab-pane  label="宸插彂璐? name="宸插彂璐?></el-tab-pane>
			<el-tab-pane label="宸插畬鎴? name="宸插畬鎴?></el-tab-pane>
			<el-tab-pane label="宸查€€娆? name="宸查€€娆?></el-tab-pane>
			<el-tab-pane label="宸插彇娑? name="宸插彇娑?></el-tab-pane>
		</el-tabs>
		<el-form :inline="true" :model="formSearch" class="list-form-pv">
			<el-form-item class="list-item" >
				<div class="lable">璁㈠崟缂栧彿锛?/div>
				<el-input v-model="formSearch.orderid" placeholder="璁㈠崟缂栧彿" @keydown.enter.native="getMyOrderList(1)" clearable></el-input>
			</el-form-item>
			<div class="list-btn-box">
				<el-button class="list-search-btn" v-if=" true " type="primary" @click="getMyOrderList(1)">
					<span class="icon iconfont icon-fangdajing07"></span>
					鎼滅储
				</el-button>
			</div>
		</el-form>
		<el-table :data="tableData" style="width: 100%" >
			<el-table-column label="璁㈠崟缂栧彿" prop="orderid"></el-table-column>
			<el-table-column label="鍚嶇О" align="center" width="200px">
				<template slot-scope="scope">
					<div class="shangpin">
						<el-image style="width: 100px; height: 100px;flex: 1" :src="baseUrl + scope.row.picture" fit="fill"></el-image>
						<span style="margin-left: 10px;flex: 1" >{{ scope.row.goodname }}</span>
					</div>
				</template>
			</el-table-column>
			<el-table-column label="浠锋牸">
				<template slot-scope="scope">
					<span v-if="(scope.row.type == 1 || scope.row.type == 3)&&scope.row.price>0"><span :style='{"fontSize":"12px"}'>锟?/span>{{ scope.row.price }}</span>
					<span v-if="scope.row.type == 2&&scope.row.price>0">{{ scope.row.price }}绉垎</span>
				</template>
			</el-table-column>
			<el-table-column label="鏁伴噺" prop="buynumber"></el-table-column>
			<el-table-column label="鎬讳环">
				<template slot-scope="scope">
					<span v-if="(scope.row.type == 1 || scope.row.type == 3)&&scope.row.total>0"><span :style='{"fontSize":"12px"}'>锟?/span>{{ scope.row.total }}</span>
					<span v-if="scope.row.type == 2&&scope.row.total>0">{{ scope.row.total }}绉垎</span>
				</template>
			</el-table-column>
			<el-table-column label="鍦板潃" prop="address"></el-table-column>
			<el-table-column label="鐢佃瘽" prop="tel"></el-table-column>
			<el-table-column label="鏀惰揣浜? prop="consignee"></el-table-column>
			<el-table-column label="涓嬪崟鏃堕棿" prop="addtime"></el-table-column>
			<el-table-column label="澶囨敞" prop="remark"></el-table-column>
			<el-table-column label="閫€璐у鏍? prop="sfsh" v-if="changeReturnGoods()">
				<template slot-scope="scope">
					<el-tag type="success" v-if="scope.row.sfsh=='鏄?">宸查€氳繃</el-tag>
					<el-tag type="warning" v-if="scope.row.sfsh=='寰呭鏍?">寰呭鏍?/el-tag>
					<el-tag type="danger" v-if="scope.row.sfsh=='鍚?">鏈€氳繃</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="瀹℃牳鍥炲" prop="shhf" v-if="changeReturnGoods()"></el-table-column>
			<el-table-column label="鎿嶄綔" width="150">
				<template slot-scope="scope">
					<el-button v-show="activeName == '鏈敮浠?" type="success" :style='{"margin":"2px auto",}' size="mini" @click="beforePay(scope.row)">鏀粯</el-button>
					<el-button v-show="activeName == '鏈敮浠?" type="danger" :style='{"margin":"2px auto",}' size="mini" @click="cancel(scope.row)">鍙栨秷</el-button>
					<el-button v-show="activeName == '宸叉敮浠?" type="danger" :style='{"margin":"2px auto",}' size="mini" @click="refund(scope.row)">{{scope.row.total>0?'閫€娆?:'閫€璁?}}</el-button>
					<el-button v-if="scope.row.logistics" type="warning" :style='{"margin":"2px auto",}' size="mini" @click="logistics(scope.row)">鐗╂祦</el-button>
					<el-button v-if="activeName == '宸插畬鎴?&&scope.row.sfsh==''&&changeReturnGoods()" type="danger" :style='{"margin":"2px auto",}' size="mini" @click="returnGood(scope.row)">閫€璐?/el-button>
					<el-button v-show="activeName == '宸插畬鎴?&&scope.row.sfsh==''" type="primary" :style='{"margin":"2px auto",}' size="mini" @click="toDetail(scope.row)">璇勪环</el-button>
					<el-button v-show="activeName == '宸插彂璐?" type="success" :style='{"margin":"2px auto",}' size="mini" @click="confirm(scope.row)">纭鏀惰揣</el-button>
					<el-button v-show="activeName == '宸叉敮浠?&&btnAuth('orders/' + activeName,'浜岀淮鐮?)" type="success" :style='{"margin":"2px auto",}' size="mini" @click="qrcodeClick(scope.row)">浜岀淮鐮?/el-button>
					<el-button v-if="btnAuth('orders/' + activeName,'鍒犻櫎')" type="danger" size="mini" @click="delClick(scope.row)">
						鍒犻櫎
					</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
			background
			id="pagination" class="pagination"
			:pager-count="7"
			:page-size="pageSize"
			:page-sizes="pageSizes"
			prev-text="<"
			next-text=">"
			:hide-on-single-page="true"
			:layout='["total","prev","pager","next","sizes","jumper"].join()'
			:total="total"
			:style='{"padding":"0","margin":"20px auto","whiteSpace":"nowrap","color":"#333","textAlign":"center","width":"100%","fontSize":"16px","fontWeight":"500","order":"50"}'
			@current-change="curChange"
			@size-change="sizeChange"
			@prev-click="prevClick"
			@next-click="nextClick"
			></el-pagination>

		<el-dialog title="鐗╂祦淇℃伅" :visible.sync="logisticsVisible" width="30%">
			<div class="ql-snow ql-editor" v-html="logisticsText"></div>
		</el-dialog>
		<el-dialog title="閫€璐х悊鐢? :visible.sync="returngoodVisible" width="30%">
			<el-input v-model="returnGoodForm.returnreason" type="textarea" placeholder="閫€璐х悊鐢?></el-input>
			<el-tag v-for="(item,index) in reasonList" :key="index" :type="item.type" style="cursor: pointer;margin: 5px 5px 0 0;" @click="reasonClick(item.title)">{{item.title}}</el-tag>
			<div style="margin: 20px 0 0">
				<el-button type="primary" style="margin-left: 10px;" @click="returnGoodSave">纭</el-button>
				<el-button type="danger" style="margin-left: 10px;" @click="returngoodVisible=false">鍙栨秷</el-button>
			</div>
		</el-dialog>
		<el-dialog title="浜岀淮鐮? :visible.sync="codeVisible" width="490px">
			<div id="qrcode"></div>
		</el-dialog>
		<el-dialog title="楠岃瘉鏀粯瀵嗙爜" :visible.sync="payPasswordVisible" width="20%">
			<el-input type="password" v-model="payPassword" placeholder="鏀粯瀵嗙爜" style="width: 100%;"></el-input>
			<span slot="footer" class="dialog-footer">
				<el-button @click="payPasswordVisible=false">杩斿洖</el-button>
				<el-button @click="payConfirm" type="success">纭鏀粯</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	import config from '@/config/config';
	import QRCode from 'qrcode2';
	export default {
		data() {
			return {
				layouts: '',
				baseUrl: config.baseUrl,
				activeName: '鏈敮浠?,
				tableData: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1,
				session: {},
				timeRange: [],
				formSearch: {},
				userTableName: localStorage.getItem('UserTableName'),
				codeVisible: false,
				logisticsVisible: false,
				logisticsText: '',
				returngoodVisible: false,
				returnGoodForm: {
					returnreason: ''
				},
				reasonList: [{
					title: '涔板浜?,
					type: ''
				}, {
					title: '涓嶆兂瑕佷簡',
					type: 'info'
				}, {
					title: '涓庡師鍥句笉绗?,
					type: 'warning'
				}, {
					title: '鍟嗗搧璐ㄩ噺宸?,
					type: 'danger'
				}, ],
				payPasswordVisible: false,
				payPassword: '',
				payForm: {},
			}
		},
		created() {
			this.getSession();
			this.getMyOrderList(1);
		},
		computed: {
			userid(){
				return localStorage.getItem('frontUserid')
			},
			username(){
				return localStorage.getItem('username')
			},
			role(){
				return localStorage.getItem('frontRole');
			},
		},
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			qrcodeClick(row) {
				this.codeVisible = true
				this.$nextTick(()=>{
					if(document.getElementById('qrcode').innerHTML!=null) {
						document.getElementById('qrcode').innerHTML = ''
					}
					var qrcode = new QRCode(document.getElementById('qrcode'), {
						text: row.orderid,
						width: 450,
						height: 450,
						colorDark : "#000000",
						colorLight : "#ffffff",
						correctLevel : QRCode.CorrectLevel.H
					});
				})
			},
			btnAuth(tableName,key){
				return this.isBackAuth(tableName,key)
			},
			backClick() {
				this.$router.push('/index/center')
			},
			getSession() {
				this.$http.get(this.userTableName+'/session', {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						this.session = res.data.data;
					}
				});
			},
			handleClick(tab, event) {
				tab.activeName = event.target.outerText;
				this.getMyOrderList(1);
			},
			toDetail(row) {
				this.$router.push({path: '/index/'+row.tablename+'Detail', query: {id: row.goodid}});
			},
			handleAction(index, row) {
				if (index == '宸叉敮浠? && this.session.money < row.total) {
					this.$message.error('浣欓涓嶈冻锛岃鍏堝厖鍊?);
					return;
				}
	
				row.status = index;
				this.$http.post('orders/update', row).then(res => {
					if (res.data.code == 0) {
						if (index == '宸叉敮浠?) this.session.money -= row.total;
						if (index == '宸查€€娆? && row.type == 1) this.session.money += row.total;
						if (index == '宸查€€娆? && row.type == 2) this.session.jifen += row.total;
						if (index == '宸叉敮浠? || index == '宸查€€娆?) {
							this.session.money = this.session.money.toFixed(2);
							this.$http.post(this.userTableName+'/update', this.session).then(res => {});
						}
						this.$message({
							message: '鎿嶄綔鎴愬姛',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.getMyOrderList(1);
							}
						});
					}
				});
			},
			// 閫€娆?			refund(item) {
				this.$confirm(`${item.total>0?'纭畾閫€娆惧悧锛熼€€娆鹃噾棰濆皢杩斿洖璐︽埛涓€?:'鏄惁閫€璁紵'}`, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(() => {
					// 鑾峰彇鍟嗗搧璇︽儏淇℃伅
					this.$http.get(item.tablename+'/info/'+item.goodid, {}).then(res => {
						// 鍟嗗搧淇℃伅
						let good = res.data.data;
						// 鐢ㄦ埛褰撳墠鐢ㄦ埛淇℃伅
						let table = localStorage.getItem("UserTableName");
						this.$http.get(table+'/session', {}).then(res => {
							this.user = res.data.data;
							if(item.type==1||item.type==3) {
								// 濡傛灉璇ュ晢鍝佸瓨鍦ㄧН鍒?								if (good.jf) {
									this.user.jf = (Number(this.user.jf) - Number(item.total)).toFixed(2);
								}
								this.user.money = (Number(this.user.money) + Number(item.total)).toFixed(2);
								// 鏇存柊鐢ㄦ埛浣欓
								this.$http.post(table+'/update', this.user).then(res => {
									item.status = '宸查€€娆?
									localStorage.setItem('sessionForm',JSON.stringify(this.user))
									this.$http.post(`orders/update`, item).then(res => {
										if(item.tablename == `xianshangshangcheng`){
											// 鍔犲洖搴撳瓨鏁伴噺
											good.alllimittimes = good.alllimittimes + item.buynumber;
											this.$http.post(`xianshangshangcheng/update`, good).then(res => {
											});
										}
										this.$message({
											message: `${item.total>0?'閫€娆炬垚鍔?:'閫€璁㈡垚鍔?}`,
											type: 'success',
											duration: 1500,
											onClose: () => {
												this.getMyOrderList(1);
											}
										});
									});
								});
							} else if(item.type==2) {
								this.user.jf = Number(this.user.jf) + Number(item.total);
								// 鏇存柊鐢ㄦ埛浣欓
								this.$http.post(table+'/update', this.user).then(res => {
									item.status = '宸查€€娆?
									localStorage.setItem('sessionForm',JSON.stringify(this.user))
									this.$http.post(`orders/update`, item).then(res => {
										if(item.tablename == `xianshangshangcheng`){
											// 鍔犲洖搴撳瓨鏁伴噺
											good.alllimittimes = good.alllimittimes + item.buynumber;
											this.$http.post(`xianshangshangcheng/update`, good).then(res => {
											});
										}
										this.$message({
											message: `${item.total>0?'閫€娆炬垚鍔?:'閫€璁㈡垚鍔?}`,
											type: 'success',
											duration: 1500,
											onClose: () => {
												this.getMyOrderList(1);
											}
										});
									});
								});
							}
						});
					});
				});
			},
			//鐗╂祦
			logistics(row) {
				this.logisticsText = row.logistics;
				this.logisticsVisible = true;
			},
			//閫€璐?
			returnGood(item){
				this.returnGoodForm = item
				this.returngoodVisible = true
			},
			reasonClick(name) {
				if (this.returnGoodForm.returnreason) {
					this.returnGoodForm.returnreason = this.returnGoodForm.returnreason + '锛? + name
				} else {
					this.returnGoodForm.returnreason = name
				}
				this.$forceUpdate()
			},
			returnGoodSave() {
				this.$confirm(`纭畾閫€璐у悧锛熷鏍搁€氳繃鍚庨€€娆鹃噾棰濆皢杩斿洖璐︽埛涓€俙, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(() => {
					this.returnGoodForm.sfsh = '寰呭鏍?
					this.$http.post(`orders/update`, this.returnGoodForm).then(res => {
						this.$message({
							message: '鐢宠鎴愬姛',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.getMyOrderList(1);
								this.returngoodVisible = false
							}
						});
					})
				});
			},

			beforePay(item){
				if(localStorage.getItem('hasPayPassword')&&localStorage.getItem('hasPayPassword')==1) {
					this.payPassword = ''
					this.payPasswordVisible = true
					this.payForm = JSON.parse(JSON.stringify(item))
				}else {
					this.pay(item)
				}
			},
			payConfirm() {
				let that = this
				if(this.payPassword == this.session.paypassword) {
					this.$message({
						message: '鏀粯瀵嗙爜姝ｇ‘锛?,
						type: 'success',
						duration: 1500,
						onClose(){
							that.payPasswordVisible = false
							that.pay(that.payForm)
						}
					});
				}else {
					this.$message({
						message: '鏀粯瀵嗙爜閿欒锛?,
						type: 'error',
						duration: 1500
					});
				}
			},
			// 鏀粯
			pay(item) {
				// 鑾峰彇鍟嗗搧璇︽儏淇℃伅
				this.$http.get(item.tablename+'/info/'+item.goodid, {}).then(res => {
					// 鍟嗗搧淇℃伅
					let data = res.data.data;
					// 鐢ㄦ埛褰撳墠鐢ㄦ埛淇℃伅
					let table = localStorage.getItem("UserTableName");
					this.$http.get(table+'/session', {}).then(res => {
						this.user = res.data.data;
						// 鍒ゆ柇浣欓鏄惁鍏呰冻
						if (Number(this.user.money) < Number(item.total)) {
							this.$message({
								message: '浣欓涓嶈冻锛岃鍏堝厖鍊?,
								type: 'error',
								duration: 1500
							});
							return
						}
						// 濡傛灉璇ュ晢鍝佸瓨鍦ㄧН鍒?						if (data.jf) {
							this.user.jf = Number((Number(this.user.jf) + Number(item.total)).toFixed(2));
						}
						this.user.money = Number((Number(this.user.money) - Number(item.total)).toFixed(2));
						// 鏇存柊鐢ㄦ埛浣欓
						this.$http.post(table+'/update', this.user).then(res => {
							item.status = '宸叉敮浠?
							localStorage.setItem('sessionForm',JSON.stringify(this.user))
							this.$http.post(`orders/update`, item).then(res => {
								this.$message({
									message: '鏀粯鎴愬姛',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.getMyOrderList(1);
									}
								});
							});
						});
					});
				});
			},
			confirm(item){
				this.$confirm(`鏄惁纭畾鏀惰揣锛焋, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(() => {
					item.status = '宸插畬鎴?
					this.$http.post(`orders/update`, item).then(async res => {
						await this.$http.get(`${item.tablename}/info/${item.goodid}`).then(async rs=>{
							if(rs.data.data.sales||rs.data.data.sales==0) {
								rs.data.data.sales = Number((Number(rs.data.data.sales) + Number(item.buynumber)).toFixed(0))
								await this.$http.post(`${item.tablename}/update`,rs.data.data).then(rs2=>{})
							}
						})
						this.$message({
							message: '纭鏀惰揣鎴愬姛',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.getMyOrderList(1);
							}
						});
					});
				});
			},
			cancel(item){
				this.$confirm(`鏄惁鍙栨秷璁㈠崟锛焋, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(() => {
					item.status = '宸插彇娑?
					this.$http.post(`orders/update`, item).then(res => {
						// 鑾峰彇鍟嗗搧璇︽儏淇℃伅
						this.$http.get(item.tablename+'/info/'+item.goodid, {}).then(res => {
							// 鍟嗗搧淇℃伅
							let good = res.data.data;
							if(item.tablename == `xianshangshangcheng`){
								// 鍔犲洖搴撳瓨鏁伴噺
								good.alllimittimes = good.alllimittimes + item.buynumber;
								this.$http.post(`xianshangshangcheng/update`, good).then(res => {
								});
							}
							this.$message({
								message: '璁㈠崟鍙栨秷鎴愬姛',
								type: 'success',
								duration: 1500,
								onClose: () => {
									this.getMyOrderList(1);
								}
							});
						});
					});
				});
			},
			getMyOrderList(page) {
				let params = {
					page, limit: this.pageSize,sort:'addtime',order:'desc', status: this.activeName
				}
				if (this.formSearch.orderid != ''&&this.formSearch.orderid != undefined) params.orderid = '%' + this.formSearch.orderid + '%';
				this.$http.get('orders/mch/list', {params: params}).then(res => {
					if (res.data.code == 0) {
						this.tableData = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			curChange(page) {
				this.getMyOrderList(page);
			},
			prevClick(page) {
				this.getMyOrderList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getMyOrderList(1);
			},
			nextClick(page) {
				this.getMyOrderList(page);
			},
			delClick(row) {
				this.$confirm(`鏄惁鍒犻櫎璇ヨ鍗曪紵`, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(() => {
					this.$http.post(`orders/delete`, [row.id]).then(async res => {
						this.$message({
							message: '鍒犻櫎鎴愬姛',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.getMyOrderList(1);
							}
						});
					});
				});
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.section {
		width: 1000px;
		margin: 0 auto;
	}

	.shangpin {
		display: flex;
	}
	.el-tabs ::v-deep .el-tabs__nav-wrap {
		background: #fff;
		padding: 10px;
	}

	.list-form-pv {
		padding: 0;
		margin: 10px auto;
		background: none;
		display: flex;
		width: 100%;
		border-color: #ddd;
		border-width: 0px;
		justify-content: center;
		align-items: center;
		border-style: solid;
		flex-wrap: wrap;
		height: auto;
		.list-item {
			margin: 0 5px 0 0;
			::v-deep.el-form-item__content {
				display: flex;
			}
			.lable {
				padding: 0;
				color: #333;
				white-space: nowrap;
				display: inline-block;
				width: auto;
				line-height: 42px;
			}
			.el-input {
				width: 100%;
			}
			.datetimerange {
				border: 1px solid #707070;
				border-radius: 2px;
				padding: 3px 0px;
				outline: none;
				background: none;
				width: auto;
				justify-content: center;
			}
			.el-input ::v-deep .el-input__inner {
				border: 1px solid #707070;
				border-radius: 2px;
				padding: 0 10px;
				margin: 0;
				outline: none;
				color: #333;
				background: none;
				width: 140px;
				font-size: 14px;
				line-height: 42px;
				height: 42px;
			}
			.el-select {
				width: 100%;
			}
			.el-select ::v-deep .el-input__inner {
				border: 1px solid #707070;
				border-radius: 2px;
				padding: 0 10px;
				margin: 0;
				outline: none;
				color: #333;
				background: none;
				width: 140px;
				font-size: 14px;
				line-height: 42px;
				height: 42px;
			}
			.el-date-editor {
				width: 100%;
			}
			.el-date-editor ::v-deep .el-input__inner {
				border: 1px solid #707070;
				border-radius: 2px;
				padding: 0 30px;
				margin: 0;
				outline: none;
				color: #333;
				background: none;
				width: 140px;
				font-size: 14px;
				line-height: 42px;
				height: 42px;
			}
		}
		.list-btn-box {
			display: flex;
			flex-wrap: wrap;
		}
		.list-search-btn {
			cursor: pointer;
			border: 0 solid #000000;
			border-radius: 20px;
			padding: 0px 10px;
			margin: 0 10px 0 0;
			outline: none;
			color: #fff;
			background: #46AC2E;
			width: auto;
			font-size: 14px;
			line-height: 42px;
			height: 42px;
			.icon {
				margin: 0 3px 0 0;
				color: inherit;
				font-size: 14px;
			}
		}
	}
</style>

