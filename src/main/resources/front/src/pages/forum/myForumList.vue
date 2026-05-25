<template>
	<div :style='{"alignContent":"flex-start","padding":"0 10%","margin":"0px auto","alignItems":"flex-start","flexWrap":"wrap","background":"none","display":"flex","width":"100%","position":"relative"}'>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">杩斿洖</span>
			</el-button>
		</div>
	<div class="section-title" :style='{"padding":"0","borderColor":"#e61f4d","margin":"10px auto","color":"#46AC2E","textAlign":"center","display":"block","background":"url() no-repeat left center / 40px 100%","borderWidth":"0 0 0px","width":"100%","lineHeight":"32px","fontSize":"30px","borderStyle":"solid","fontWeight":"600"}'>鎴戠殑鍙戝竷</div>
	<el-table :stripe='false'
		:style='{"width":"100%","padding":"0","borderColor":"#eee","borderStyle":"solid","borderWidth":"1px 0 0 1px","background":"#fff"}' 
		:border='true'
		:data="tableData">
		<el-table-column :resizable='true' :sortable='false'
			label="封面图"
			prop="cover">
			<template slot-scope="scope">
				<el-image :src="$assetUrl(scope.row.cover.split(',')[0])" style="width: 150px;height: 150px" :preview-src-list="[$assetUrl(scope.row.cover.split(',')[0])]" ></el-image>
			</template>
		</el-table-column>
		<el-table-column :resizable='true' :sortable='false'
			label="标题"
			prop="title">
		</el-table-column>
		<el-table-column :resizable='true' :sortable='false'
			label="发布时间"
			prop="addtime">
		</el-table-column>
		<el-table-column :resizable='true' :sortable='false'
			label="是否置顶"
			prop="istop">
			<template slot-scope="scope">
				{{ scope.row.istop == 1 ? '是' : '否' }}
			</template>
		</el-table-column>
		<el-table-column :resizable='true' :sortable='false'
			label="置顶时间"
			prop="toptime">
			<template slot-scope="scope">
				{{scope.row.toptime}}
			</template>
		</el-table-column>
		<el-table-column :resizable='true' :sortable='false'
			label="是否匿名"
			prop="isanon">
			<template slot-scope="scope">
				{{ scope.row.isanon == 1 ? '是' : '否' }}
			</template>
		</el-table-column>
		<el-table-column :resizable='true' :sortable='false' label="鎿嶄綔" width="150">
			<template slot-scope="scope">
				<el-button
					size="mini"
					@click="handleEdit(scope.$index, scope.row)">淇敼</el-button>
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
		prev-text="<"
		next-text=">"
		:hide-on-single-page="true"
		:layout='["total","prev","pager","next","sizes","jumper"].join()'
		:total="total"
		@current-change="curChange"
		@size-change="sizeChange"
		@prev-click="prevClick"
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
				totalPage: 1
			}
		},
		created() {
			this.getMyForumList(1);
		},
		methods: {
			backClick() {
				history.back()
			},
			getMyForumList(page) {
				this.$http.get('forum/page', {params: {page, limit: this.pageSize, parentid: 0, sort: 'istop,toptime', order: 'desc,desc'}}).then(res => {
					if (res.data.code == 0) {
						this.tableData = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
					}
				});
			},
			curChange(page) {
				this.getMyForumList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getMyForumList(1);
			},
			prevClick(page) {
				this.getMyForumList(page);
			},
			nextClick(page) {
				this.getMyForumList(page);
			},
			handleEdit(index, row) {
				this.$router.push({path: '/forumAdd', query: {id: row.id}})
			},
			handleDelete(index, row) {
				this.$confirm('鏄惁纭鍒犻櫎?', '鎻愮ず', {
					confirmButtonText: '纭畾',
					cancelButtonText: '鍙栨秷',
					type: 'warning'
				}).then(() => {
					let delIds = new Array();
					delIds.push(row.id);
					this.$http.post('forum/delete', delIds).then(res => {
						if (res.data.code == 0) {
							this.$message({
								type: 'success',
								message: '鍒犻櫎鎴愬姛!',
								duration: 1500,
								onClose: () => {
									this.getMyForumList(1);
								}
							});
						}
					});
				});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.section {
		width: 900px;
		margin: 0 auto;
	}
	.el-table ::v-deep .el-table__header-wrapper thead {
		color: #333;
		font-weight: 500;
		width: 100%;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr {
		background: #fff;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr th {
		padding: 12px 0;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr th .cell {
		padding: 0 10px;
		word-wrap: normal;
		word-break: break-all;
		white-space: normal;
		font-weight: bold;
		display: inline-block;
		vertical-align: middle;
		width: 100%;
		line-height: 24px;
		position: relative;
		text-overflow: ellipsis;
	}
	
	
	.el-table ::v-deep .el-table__body-wrapper tbody {
		width: 100%;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr {
		background: #fff;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 12px 0;
		color: #666;
		background: #fff;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr:hover td {
		padding: 12px 0;
		color: #333;
		background: rgba(64, 158, 255, .1);
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 12px 0;
		color: #666;
		background: #fff;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .cell {
		padding: 0 10px;
		overflow: hidden;
		word-break: break-all;
		white-space: normal;
		line-height: 24px;
		text-overflow: ellipsis;
	}
</style>

