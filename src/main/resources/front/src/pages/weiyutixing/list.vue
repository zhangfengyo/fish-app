<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'/'">
				<el-breadcrumb-item class="item1" to="/"><a>棣栭〉</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{item.name}}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div v-if="centerType " class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">杩斿洖</span>
			</el-button>
		</div>
		<div class="list-preview">
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="list-item" v-if="queryChange(['绠＄悊鍛?])">
					<div class="lable">鐢ㄦ埛濮撳悕锛?/div>
					<el-input v-model="formSearch.yonghuxingming" placeholder="鐢ㄦ埛濮撳悕" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">鎻愰啋鏃ユ湡锛?/div>
					<el-date-picker
						class="datetimerange"
						v-model="timeRange"
						type="daterange"
						range-separator="鑷?
						start-placeholder="鎻愰啋鏃ユ湡璧峰"
						end-placeholder="鎻愰啋鏃ユ湡缁撴潫"
						value-format="yyyy-MM-dd">
					</el-date-picker>
				</el-form-item>
				<div class="list-btn-box">
					<el-button class="list-search-btn" v-if=" true " type="primary" @click="getList(1, curFenlei)">
						<span class="icon iconfont icon-fangdajing07"></span>
						鎼滅储
					</el-button>
					<el-button class="list-add-btn" v-if="btnAuth('weiyutixing','鏂板')" type="primary" @click="add('/index/weiyutixingAdd')">
						<span class="icon iconfont icon-tianjia7"></span>
						鏂板
					</el-button>
  
				</div>
			</el-form>
			<div class="list">
				<el-table class="tables" :stripe='false'
					:style='{"width":"100%","padding":"0","borderColor":"#eee","borderStyle":"solid","borderWidth":"1px 0 0 1px","background":"#fff"}' 
					:border='true' 
					:data="dataList">
					<el-table-column :resizable='true' :sortable='false'
						prop="yonghuzhanghao"
						label="鐢ㄦ埛璐﹀彿">
						<template slot-scope="scope">
							{{scope.row.yonghuzhanghao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='false'
						prop="yonghuxingming"
						label="鐢ㄦ埛濮撳悕">
						<template slot-scope="scope">
							{{scope.row.yonghuxingming}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='false'
						prop="tixingbiaoti"
						label="鎻愰啋鏍囬">
						<template slot-scope="scope">
							{{scope.row.tixingbiaoti}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='false'
						prop="tixingriqi"
						label="鎻愰啋鏃ユ湡">
						<template slot-scope="scope">
							{{scope.row.tixingriqi}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='false'
						prop="tixingneirong"
						label="鎻愰啋鍐呭">
						<template slot-scope="scope">
							{{scope.row.tixingneirong}}
						</template>
					</el-table-column>
					<el-table-column width="300" label="鎿嶄綔">
						<template slot-scope="scope">
							<el-button class="table-view" type="success" @click.native="toDetail(scope.row)">
								<span class="icon iconfont icon-fangdajing02"></span>
								鏌ョ湅
							</el-button>
							<el-button class="table-btn5" type="success" @click.native="chapterClick(scope.row)" v-if="btnAuth('weiyutixing','绔犺妭绠＄悊')">
								<span class="icon iconfont icon-zhangjie7"></span>
								绔犺妭绠＄悊
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>

	
			<el-pagination
				background
				id="pagination"
				class="pagination"
				:pager-count="7"
				:page-size="pageSize"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="true"
				:layout='["total","prev","pager","next","sizes","jumper"].join()'
				:total="total"
				:page-sizes="pageSizes"
				@current-change="curChange"
				@size-change="sizeChange"
				@prev-click="prevClick"
				@next-click="nextClick"
				></el-pagination>
		</div>
		<el-dialog title="棰勮鍥? :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
	</div>
</template>
<script>
	import axios from 'axios';
	export default {
		//鏁版嵁闆嗗悎
		data() {
			return {
				layouts: '',
				swiperIndex: -1,
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '鍠傞奔鎻愰啋'
					}
				],
				formSearch: {
					yonghuxingming: '',
					tixingriqi: '',
				},
				fenlei: [],
				dataList: [],
				total: 1,
				pageSize: 5,
				pageSizes: [],
				totalPage: 1,
				curFenlei: '鍏ㄩ儴',
				isPlain: false,
				indexQueryCondition: '',
				timeRange: [],
				centerType:false,
				previewImg: '',
				previewVisible: false,
				sortType: 'id',
				sortOrder: 'desc',
			}
		},
		async created() {
			if(this.$route.query.centerType&&this.$route.query.centerType!=0){
				this.centerType = true
			}
			this.baseUrl = this.$config.baseUrl;
			if(this.centerType) {
				let urltixingriqi = 'weiyutixing/remind/tixingriqi/2';
				urltixingriqi+=`?remindend=0`,

				this.$http.get(urltixingriqi).then(({ data }) => {
					if (data && data.code == 0 && data.count>0 ) {
						const h = this.$createElement;
						var name = data.data.join(',')
						this.tixingriqiRemind = data.data
						this.$msgbox({
							message: h('p', null, [
								h('div', { style: 'text-align: center;font-weight: bold;font-size: 20px;padding: 20px 0' }, `鎻愰啋鏃ユ湡鍒版湡棰勮`),
								h('p', null, [
									h('div', { style: 'text-align: center;font-weight: bold;padding: 0 0 10px;font-size: 16px' }, `${data.count}鏉℃暟鎹埌鏈焋),
									h('div', { style: 'text-align: center' }, [
										h('span', null, `鍖呭惈锛歚),
										h('span', { style: 'color: teal;font-weight: bold' }, `${name}`)
									])
								])
							]),
							showCancelButton: false,
						})
					}
				});
			}
			await this.getFenlei();
			let fenlei = '鍏ㄩ儴'
			if(this.$route.query.homeFenlei){
				fenlei = this.$route.query.homeFenlei
			}
			this.getList(1, fenlei);
		},
		watch:{
			$route(newValue){
				this.getList(1, newValue.query.homeFenlei);
			}
		},
		computed: {
			role(){
				return localStorage.getItem('frontRole');
			},
			username() {
				return localStorage.getItem('username')
			}
		},
		//鏂规硶闆嗗悎
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			add(path) {
				let query = {}
				if(this.centerType){
					query.centerType = 1
				}
				this.$router.push({path: path,query:query});
			},
			async getFenlei() {
			},
			getList(page, fenlei, ref = '') {
				let params = {
					page,
					limit: this.pageSize,
				};
				let searchWhere = {};
				if (this.formSearch.yonghuxingming != ''&&this.formSearch.yonghuxingming != undefined) searchWhere.yonghuxingming = '%' + this.formSearch.yonghuxingming + '%';
				if (this.timeRange&&this.timeRange.length > 0) {
					searchWhere.tixingriqistart = this.timeRange[0];
					searchWhere.tixingriqiend = this.timeRange[1];
				}
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				if (this.sortType) searchWhere.sort = this.sortType
				if (this.sortOrder) searchWhere.order = this.sortOrder
				this.$http.get(`weiyutixing/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(async res => {
					if (res.data.code == 0) {
						this.dataList = res.data.data.list;
						this.total = Number(res.data.data.total);
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			curChange(page) {
				this.getList(page);
			},
			prevClick(page) {
				this.getList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getList(1);
			},
			nextClick(page) {
				this.getList(page);
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
			},
			toDetail(item) {
				let params = {
					id: item.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/weiyutixingDetail', query: params});
			},
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			backClick() {
				this.$router.push({path: '/index/center'});
			},

			chapterClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/chapterweiyutixing', query: params});
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.list-preview {
		padding: 0 10%;
		margin: 0px auto;
		align-content: flex-start;
		background: none;
		display: flex;
		width: 100%;
		align-items: flex-start;
		position: relative;
		flex-wrap: wrap;
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
			.list-add-btn {
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
		.list {
			margin: 20px 0 0;
			background: none;
			flex: 1;
			width: 100%;
			order: 4;
			.index-pv1 .animation-box {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				z-index: initial;
			}
				
			.index-pv1 .animation-box:hover {
				transform: rotate(0) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
			}
				
			.index-pv1 .animation-box img {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
			}
			
			.index-pv1 .animation-box img:hover {
				transform: rotate(0) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
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
			.el-table ::v-deep .table-view {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: #67C23A;
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table ::v-deep .table-view:hover {
				opacity: 0.8;
			}
			.el-table ::v-deep .table-edit {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: rgba(64, 158, 255, 1);
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table ::v-deep .table-edit:hover {
				opacity: 0.8;
			}
			.el-table ::v-deep .table-del {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: rgba(255, 0, 0, 1);
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table ::v-deep .table-del:hover {
				opacity: 0.8;
			}
			.el-table ::v-deep .table-btn5 {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 5px 2px 0;
				outline: none;
				color: #fff;
				background: rgba(255, 128, 0, 1);
				width: auto;
				font-size: 14px;
				height: 32px;
				.iconfont {
					margin: 0 0px;
					color: #fff;
					font-size: 14px;
					height: 40px;
				}
			}
			.el-table ::v-deep .table-btn5:hover {
				opacity: 0.8;
			}
		}
	}
</style>

