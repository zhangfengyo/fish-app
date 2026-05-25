<template>
	<div :style='{"width":"100%","padding":"20px 10% 40px","margin":"10px auto","position":"relative","background":"none"}'>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">杩斿洖</span>
			</el-button>
		</div>
		<div class="section-title" :style='{"padding":"0","borderColor":"#e61f4d","margin":"10px auto","color":"#46AC2E","textAlign":"center","display":"block","background":"url() no-repeat left center / 40px 100%","borderWidth":"0 0 0px","width":"100%","lineHeight":"32px","fontSize":"30px","borderStyle":"solid","fontWeight":"600"}'>鎴戠殑鍦板潃</div>
		<el-button type="primary" icon="el-icon-plus" @click="toAddAddr">娣诲姞鏂板湴鍧€</el-button>
		<el-table
			:data="tableData"
			style="width: 100%">
			<el-table-column
				label="鑱旂郴浜?
				prop="name"
				width="100">
			</el-table-column>
			<el-table-column
				label="鎵嬫満鍙?
				prop="phone"
				width="120">
			</el-table-column>
			<el-table-column
				label="鍦板潃"
				prop="address">
			</el-table-column>
			<el-table-column
				label="榛樿"
				prop="isdefault"
				width="80">
				<template slot-scope="scope">
					<el-tag :type="scope.row.isdefault === '鏄? ? 'primary' : 'success'" size="medium">{{ scope.row.isdefault }}</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="鎿嶄綔">
				<template slot-scope="scope">
					<el-button
						size="mini"
						@click="handleEdit(scope.$index, scope.row)">缂栬緫</el-button>
					<el-button
						size="mini"
						type="danger"
						@click="handleDelete(scope.$index, scope.row)">鍒犻櫎</el-button>
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
			@prev-click="prevClick"
			@size-change="sizeChange"
			@next-click="nextClick"
			></el-pagination>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				layouts: '',
				tableData: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1
			}
		},
		created() {
			this.getAddr(1);
		},
		methods: {
			backClick() {
				this.$router.push('/index/center')
			},
			getAddr(page) {
				this.$http.get('address/list', {params: {page, limit: this.pageSize, userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
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
				this.getAddr(page);
			},
			prevClick(page) {
				this.getAddr(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getAddr(1);
			},
			nextClick(page) {
				this.getAddr(page);
			},
			handleEdit(index, row) {
				this.$router.push({path: '/index/shop-address/addOrUpdate', query: {id: row.id, editObj: JSON.stringify(row)}})
			},
			handleDelete(index, row) {
				this.$confirm('鏄惁纭鍒犻櫎?', '鎻愮ず', {
					confirmButtonText: '纭畾',
					cancelButtonText: '鍙栨秷',
					type: 'warning'
				}).then(() => {
					let delIds = new Array();
					delIds.push(row.id);
					this.$http.post('address/delete', delIds).then(res => {
						if (res.data.code == 0) {
							this.$message({
								type: 'success',
								message: '鍒犻櫎鎴愬姛!',
								duration: 1500,
								onClose: () => {
									this.getAddr(1);
								}
							});
						}
					});
				});
			},
			toAddAddr() {
				this.$router.push('/index/shop-address/addOrUpdate');
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>


