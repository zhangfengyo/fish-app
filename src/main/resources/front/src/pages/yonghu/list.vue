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
				<el-form-item class="list-item" >
					<div class="lable">鐢ㄦ埛濮撳悕锛?/div>
					<el-input v-model="formSearch.yonghuxingming" placeholder="鐢ㄦ埛濮撳悕" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<div class="list-btn-box">
					<el-button class="list-search-btn" v-if=" true " type="primary" @click="getList(1, curFenlei)">
						<span class="icon iconfont icon-fangdajing07"></span>
						鎼滅储
					</el-button>
					<el-button class="list-add-btn" v-if="btnAuth('yonghu','鏂板')" type="primary" @click="add('/index/yonghuAdd')">
						<span class="icon iconfont icon-tianjia7"></span>
						鏂板
					</el-button>
  
				</div>
			</el-form>
			<div class="list">
				<!-- 鏍峰紡涓€ -->
				<div class="list1 index-pv1">
					<div v-for="(item, index) in dataList" :key="index" @click.stop="toDetail(item)" class="list-item animation-box" >
						<img class="image" @click.stop="imgPreView(item.touxiang)" v-if="item.touxiang && item.touxiang.substr(0,4)=='http'&& item.touxiang.split(',w').length>1" :src="item.touxiang" />
						<img class="image" @click.stop="imgPreView(item.touxiang.split(',')[0])" v-else-if="item.touxiang && item.touxiang.substr(0,4)=='http'" :src="item.touxiang.split(',')[0]" />
						<img class="image" @click.stop="imgPreView(baseUrl + (item.touxiang?item.touxiang.split(',')[0]:''))" v-else :src="baseUrl + (item.touxiang?item.touxiang.split(',')[0]:'')" />
						<div class="name">{{item.yonghuxingming}}</div>
						<div class="time_item">
							<span class="icon iconfont icon-shijian21"></span>
							<span class="label">鍙戝竷鏃堕棿锛?/span>
							<span class="text">{{item.addtime.split(' ')[0]}}</span>
						</div>
					</div>
				</div>
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
						name: '鐢ㄦ埛'
					}
				],
				formSearch: {
					yonghuxingming: '',
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
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				if (this.sortType) searchWhere.sort = this.sortType
				if (this.sortOrder) searchWhere.order = this.sortOrder
				this.$http.get(`yonghu/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(async res => {
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
				this.$router.push({path: '/index/yonghuDetail', query: params});
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
				this.$router.push({path: '/index/chapteryonghu', query: params});
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
			.list1 {
				padding: 0;
				margin: 0 0 0 -10px;
				color: #666;
				background: none;
				width: calc(100% + 20px);
				font-size: 14px;
				height: auto;
				.list-item {
					cursor: pointer;
					padding: 0 0 10px;
					margin: 0 10px 20px;
					background: #f5f5f5;
					display: inline-block;
					width: calc(20% - 20px);
					position: relative;
					height: auto;
					.image {
						margin: 0 0 5px;
						object-fit: cover;
						display: block;
						width: 100%;
						height: 275px;
					}
					.price {
						padding: 0 10px;
						color: red;
						width: 100%;
						font-size: 16px;
						line-height: 30px;
					}
					.name {
						padding: 0 10px;
						overflow: hidden;
						color: #333;
						white-space: nowrap;
						font-weight: 600;
						width: 100%;
						font-size: 15px;
						line-height: 2;
						text-overflow: ellipsis;
					}
					.time_item {
						padding: 0 10px;
						.icon {
							margin: 0 2px 0 0;
							line-height: 1.5;
						}
						.label {
							line-height: 1.5;
						}
						.text {
							line-height: 1.5;
						}
					}
					.publisher_item {
						padding: 0 10px;
						display: inline-block;
						.icon {
							margin: 0 2px 0 0;
							line-height: 1.5;
						}
						.label {
							line-height: 1.5;
						}
						.text {
							line-height: 1.5;
						}
					}
					.like_item {
						padding: 0 10px;
						display: inline-block;
						.icon {
							margin: 0 2px 0 0;
							line-height: 1.5;
						}
						.label {
							line-height: 1.5;
						}
						.text {
							line-height: 1.5;
						}
					}
					.collect_item {
						padding: 0 10px;
						display: inline-block;
						.icon {
							margin: 0 2px 0 0;
							line-height: 1.5;
						}
						.label {
							line-height: 1.5;
						}
						.text {
							line-height: 1.5;
						}
					}
					.view_item {
						padding: 0 10px;
						display: inline-block;
						.icon {
							margin: 0 2px 0 0;
							line-height: 1.5;
						}
						.label {
							line-height: 1.5;
						}
						.text {
							line-height: 1.5;
						}
					}
				}
			}
		}
	}
</style>

