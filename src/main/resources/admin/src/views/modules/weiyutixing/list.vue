  
<template>
	<div class="main-content" :style='{"width":"100%","padding":"20px","fontSize":"14px","color":"#666","height":"auto"}'>
		<!-- 鍒楄〃椤?-->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 0px","flexWrap":"wrap","display":"flex"}' :inline="true" :model="searchForm">
				<el-row :style='{"border":"0px solid #e5e7eb","padding":"0","boxShadow":"none","margin":"0 10px 5px 0","borderRadius":"0","alignItems":"center","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto","position":"relative"}' >
					<div v-if="queryChange(['绠＄悊鍛?])" :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">鐢ㄦ埛濮撳悕</label>
						<el-input v-model="searchForm.yonghuxingming" placeholder="鐢ㄦ埛濮撳悕" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div  :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">鎻愰啋鏃ユ湡璧峰</label>
						<el-date-picker v-model="searchForm.tixingriqistart" type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" placeholder="鎻愰啋鏃ユ湡璧峰"></el-date-picker>
					</div>
					<div  :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">鎻愰啋鏃ユ湡缁撴潫</label>
						<el-date-picker v-model="searchForm.tixingriqiend" type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" placeholder="鎻愰啋鏃ユ湡缁撴潫"></el-date-picker>
					</div>
					<el-button class="search" type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing02" :style='{"margin":"0 0px","fontSize":"inherit","color":"#fff","height":"40px"}'></span>
						鏌ヨ
					</el-button>
				</el-row>

				<el-row class="actions" :style='{"border":"0px solid #e5e7eb","padding":"0","margin":"0 0 5px","borderRadius":"0","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto"}'>
					<el-button class="btn18" type="success" @click="refreshClick()">
						鍒锋柊
					</el-button>
					<el-button class="add" v-if="isAuth('weiyutixing','鏂板')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia1" :style='{"margin":"0 2px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
						鏂板
					</el-button>
					<el-button class="del" v-if="isAuth('weiyutixing','鍒犻櫎')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鎵归噺鍒犻櫎
					</el-button>


				</el-row>
			</el-form>
			<div :style='{"width":"100%","padding":"10px 0px 20px","boxShadow":"none","borderRadius":"0 0 4px 4px","background":"#fff"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#eee","borderRadius":"0","borderWidth":"1px 1px 0 1px","background":"#fff","width":"100%","fontSize":"inherit","borderStyle":"solid"}' 
					:border='false'
					:row-class-name="tableRowClassName"
					v-if="isAuth('weiyutixing','鏌ョ湅')"
					:data="dataList"
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="搴忓彿" type="index" width="50" />
					<el-table-column :resizable='true' :sortable='true'
												prop="yonghuzhanghao"
						label="鐢ㄦ埛璐﹀彿">
						<template slot-scope="scope">
							{{scope.row.yonghuzhanghao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="yonghuxingming"
						label="鐢ㄦ埛濮撳悕">
						<template slot-scope="scope">
							{{scope.row.yonghuxingming}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="tixingbiaoti"
						label="鎻愰啋鏍囬">
						<template slot-scope="scope">
							{{scope.row.tixingbiaoti}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
						class-name="showColor1"						prop="tixingriqi"
						label="鎻愰啋鏃ユ湡">
						<template slot-scope="scope">
							{{scope.row.tixingriqi}}
						</template>
					</el-table-column>
					<el-table-column width="300" label="鎿嶄綔">
						<template slot-scope="scope">
							<el-button class="view" v-if=" isAuth('weiyutixing','鏌ョ湅')" type="success" @click="addOrUpdateHandler(scope.row.id,'info')">
								<span class="icon iconfont icon-chakan2" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								鏌ョ湅
							</el-button>
							<el-button class="edit" v-if=" isAuth('weiyutixing','淇敼') " type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								缂栬緫
							</el-button>




							<el-button class="del" v-if="isAuth('weiyutixing','鍒犻櫎')" type="primary" @click="deleteHandler(scope.row.id)">
								<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								鍒犻櫎
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				background
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:layout="layouts.join()"
				:total="totalPage"
				prev-text="< "
				next-text="> "
				:hide-on-single-page="false"
				:style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","display":"flex","width":"100%","fontWeight":"500","justifyContent":"flex-end"}'
			></el-pagination>
		</template>
		<!-- 娣诲姞/淇敼椤甸潰  灏嗙埗缁勪欢鐨剆earch鏂规硶浼犻€掔粰瀛愮粍浠?->
		<add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>





	</div>
</template>

<script>
	import * as echarts from 'echarts'
	import chinaJson from "@/components/echarts/china.json";
	import axios from 'axios';
	import AddOrUpdate from "./add-or-update";
	import {
		Loading
	} from 'element-ui';
	export default {
		data() {
			return {
				indexQueryCondition: '',
				searchForm: {
					key: ""
				},
				form:{},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				addOrUpdateFlag:false,
				layouts: ["total","prev","pager","next","sizes","jumper"],
				tixingriqiRemind: [],
			};
		},
		created() {
			if(this.statType) {
				return false
			}
			this.init();
			this.getDataList();
		},
		mounted() {
		},
		watch: {
		},
		filters: {
			htmlfilter: function (val) {
				return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
			}
		},
		computed: {
			tablename(){
				return this.$storage.get('sessionTable')
			},
			role(){
				return this.$storage.get('role')
			},
		},
		components: {
			AddOrUpdate,
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
			tableRowClassName({
			  row,
			  rowIndex
			}) {
				let name = ''
				let title = ''
				title = 'tixingbiaoti'
				if(this.changeInList(row[title],this.tixingriqiRemind)) {
					name = name + ' ' + 'tixingriqi-row';
				}
				return name;
			},
			changeClick(e) {
				// 鎻愰啋鏍囬鏈夊悧
				this.searchForm.tixingbiaoti = e
				this.search()
				// 鍏抽棴寮圭獥
				this.$msgbox.close()
			},
			changeInList(name,arr){
				for(let x in arr) {
					if(arr[x] == name) {
						return true
					}
				}
				return false
			},
			init () {
				let urltixingriqi = 'weiyutixing/remind/tixingriqi/2';
				urltixingriqi+=`?remindend=0`,


				this.$http({
					url: urltixingriqi,
					method: "get",
				}).then(({ data }) => {
					if (data && data.code == 0 && data.count>0 ) {
						const h = this.$createElement;
						var name = data.data
						this.tixingriqiRemind = data.data
						var arr = []
						for(let x=0;x<name.length;x++) {
							arr.push(h('span',{style: 'color: teal;font-weight: bold;cursor: pointer;text-decoration: underline;', on: { click: () => this.changeClick(name[x]) }}, `${name[x]}${(x!=name.length - 1)?'锛?:''}`))
						}
						this.$msgbox({
							message: h('p', null, [
								h('div', { style: 'text-align: center;font-weight: bold;font-size: 20px;padding: 20px 0' }, `鎻愰啋鏃ユ湡鍒版湡棰勮`),
								h('p', null, [
									h('div', { style: 'text-align: center;font-weight: bold;padding: 0 0 10px;font-size: 16px' }, `${data.count}鏉℃暟鎹埌鏈焋),
									h('div', { style: 'text-align: center' }, [
										h('span', null, `鍖呭惈锛歚),
										arr
									])
								])
							]),
							showCancelButton: false,
						})
					}
				});
			},
			search() {
				this.pageIndex = 1;
				this.getDataList();
			},

			// 鑾峰彇鏁版嵁鍒楄〃
			getDataList() {
				this.dataListLoading = true;
				let params = {
					page: this.pageIndex,
					limit: this.pageSize,
					sort: 'id',
					order: 'desc',
				}
				if(this.searchForm.tixingbiaoti) {
					params['tixingbiaoti'] =  '%' + this.searchForm.tixingbiaoti + '%'
				}
				if(this.searchForm.yonghuxingming!='' && this.searchForm.yonghuxingming!=undefined){
					params['yonghuxingming'] = '%' + this.searchForm.yonghuxingming + '%'
				}
				if(this.searchForm.tixingriqistart!='' && this.searchForm.tixingriqistart!=undefined ){
					params['tixingriqistart'] = this.searchForm.tixingriqistart
				}
				if(this.searchForm.tixingriqiend!='' && this.searchForm.tixingriqiend!=undefined){
					params['tixingriqiend'] = this.searchForm.tixingriqiend
				}
				let user = JSON.parse(this.$storage.getObj('userForm'))
				this.$http({
					url: "weiyutixing/page",
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.dataList = data.data.list;
						this.totalPage = data.data.total;
					} else {
						this.dataList = [];
						this.totalPage = 0;
					}
					this.dataListLoading = false;
				});
			},
			// 姣忛〉鏁?			sizeChangeHandle(val) {
				this.pageSize = val;
				this.pageIndex = 1;
				this.getDataList();
			},
			// 褰撳墠椤?			currentChangeHandle(val) {
				this.pageIndex = val;
				this.getDataList();
			},
			// 澶氶€?			selectionChangeHandler(val) {
				this.dataListSelections = val;
			},
			// 娣诲姞/淇敼
			addOrUpdateHandler(id,type) {
				this.showFlag = false;
				this.addOrUpdateFlag = true;
				this.crossAddOrUpdateFlag = false;
				if(type!='info'&&type!='msg'){
					type = 'else';
				}
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,type );
				});
			},
			// 鍒犻櫎
			async deleteHandler(id ) {
				var ids = id? [Number(id)]: this.dataListSelections.map(item => {
					return Number(item.id);
				});
				await this.$confirm(`纭畾杩涜[${id ? "鍒犻櫎" : "鎵归噺鍒犻櫎"}]鎿嶄綔?`, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(async () => {
					await this.$http({
						url: "weiyutixing/delete",
						method: "post",
						data: ids
					}).then(async ({ data }) => {
						if (data && data.code === 0) {
							this.$message({
								message: "鎿嶄綔鎴愬姛",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.search();
								}
							});
			
						} else {
							this.$message.error(data.msg);
						}
					});
				});
			},


		}

	};
</script>
<style lang="scss" scoped>
	
	.center-form-pv {
		.el-date-editor.el-input {
			width: auto;
		}
	}
	
	.el-input {
		width: auto;
	}
	
	// form
	.center-form-pv .el-input {
		width: auto;
	}
	.center-form-pv .el-input ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	.center-form-pv .el-select {
		width: auto;
	}
	.center-form-pv .el-select ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	.center-form-pv .el-date-editor {
		width: auto;
	}
	
	.center-form-pv .el-date-editor ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px 0 25px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	
	.center-form-pv .search {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px 0 8px;
		outline: none;
		margin: 0 0 0 3px;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	
	.center-form-pv .search:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .add {
		border: 0px solid #6c63ff80;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #31c72c;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .add:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .del {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #bd3124;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .del:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .statis {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #27ba65;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .statis:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .btn18 {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .btn18:hover {
		opacity: 0.8;
	}
	
	// table
	.el-table ::v-deep .el-table__header-wrapper thead {
		color: #999;
		font-weight: 500;
		width: 100%;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr {
		background: #f2f2f2;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr th {
		padding: 3px 0;
		background: none;
		border-color: #ddd;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: center;
	}

	.el-table ::v-deep .el-table__header-wrapper thead tr th .cell {
		padding: 0 0 0 5px;
		word-wrap: normal;
		color: #333;
		white-space: normal;
		font-weight: bold;
		display: flex;
		vertical-align: middle;
		font-size: 14px;
		line-height: 24px;
		text-overflow: ellipsis;
		word-break: break-all;
		width: 100%;
		justify-content: flex-start;
		align-items: center;
		position: relative;
		min-width: 110px;
	}

	.el-table ::v-deep .el-table__body-wrapper {
		position: relative;
	}
	.el-table ::v-deep .el-table__body-wrapper tbody {
		width: 100%;
	}

	.el-table ::v-deep .el-table__body-wrapper tbody tr {
		background: #fff;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
		
	.el-table ::v-deep .el-table__body-wrapper tbody tr:hover td {
		padding: 4px 0;
		color: #2dc7a3;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}

	.el-table ::v-deep .el-table__body-wrapper tbody tr td .cell {
		padding: 0 0 0 5px;
		overflow: hidden;
		word-break: break-all;
		white-space: normal;
		font-size: inherit;
		line-height: 24px;
		text-overflow: ellipsis;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: 3;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .view:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add {
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add:hover {
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2d9dc6;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: -1;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #c72d46;
		width: auto;
		font-size: inherit;
		height: 32px;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .del:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8 {
		border: 0px solid #165dff50;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 5px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2dc75f;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: 5;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8:hover {
		opacity: 0.8;
	}
	
	// pagination
	.main-content .el-pagination ::v-deep .el-pagination__total {
		margin: 0 auto 0 0;
		color: #666;
		font-weight: 400;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-prev {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-next {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-prev:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-next:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}

	.main-content .el-pagination ::v-deep .el-pager {
		padding: 0;
		margin: 0;
		display: inline-block;
		vertical-align: top;
	}

	.main-content .el-pagination ::v-deep .el-pager .number {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #666;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #fff;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pager .number:hover {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #2dc7a3;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pager .number.active {
		cursor: default;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #2dc7a3;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes {
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input {
		margin: 0 5px;
		width: 100px;
		position: relative;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 25px 0 8px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input span.el-input__suffix {
		top: 0;
		position: absolute;
		right: 0;
		height: 100%;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
		cursor: pointer;
		color: #C0C4CC;
		width: 25px;
		font-size: 15px;
		line-height: 28px;
		text-align: center;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump {
		margin: 0 0 0 24px;
		color: #606266;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input {
		border-radius: 3px;
		padding: 0 2px;
		margin: 0 2px;
		display: inline-block;
		width: 50px;
		font-size: 15px;
		line-height: 18px;
		position: relative;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 3px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	// list one
	.one .list1-view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-view:hover {
		opacity: 0.8;
	}
	
	.one .list1-edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f60;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-edit:hover {
		opacity: 0.8;
	}
	
	.one .list1-del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f00;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-del:hover {
		opacity: 0.8;
	}
	
	.one .list1-btn8 {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-btn8:hover {
		opacity: 0.8;
	}
	
	.main-content .el-table .el-switch {
		display: inline-flex;
		vertical-align: middle;
		line-height: 30px;
		position: relative;
		align-items: center;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--left {
		cursor: pointer;
		margin: 0 10px 0 0;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--right {
		cursor: pointer;
		margin: 0 0 0 10px;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core {
		border: 1px solid #ff8b00;
		cursor: pointer;
		border-radius: 15px;
		margin: 0;
		outline: 0;
		background: #ff8b00;
		display: inline-block;
		width: 36px;
		box-sizing: border-box;
		transition: border-color .3s,background-color .3s;
		height: 18px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core::after {
		border-radius: 100%;
		top: 1px;
		left: 2px;
		background: #FFF;
		width: 14px;
		position: absolute;
		transition: all .3s;
		height: 14px;
	}
	.main-content .el-table .el-switch.is-checked ::v-deep .el-switch__core::after {
		margin: 0 0 0 -16px;
		left: 100%;
	}
	
	.main-content .el-table .el-rate ::v-deep .el-rate__item {
		cursor: pointer;
		display: inline-block;
		vertical-align: middle;
		font-size: 0;
		position: relative;
	}
	.main-content .el-table .el-rate ::v-deep .el-rate__item .el-rate__icon {
		margin: 0 3px;
		display: inline-block;
		font-size: 18px;
		position: relative;
		transition: .3s;
	}

	::v-deep .el-table .tixingriqi-row .showColor1 {
		background-color: #f7bba6 !important;
		color: #000;
	}
	.chartDialog ::v-deep .el-dialog {
		background: #fff;
	}
</style>

