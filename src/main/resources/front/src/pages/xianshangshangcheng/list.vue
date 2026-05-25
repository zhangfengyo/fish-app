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
			<div class="category-1">
				<div class="item" :class="swiperIndex == '-1' ? 'active' : ''" @click="getList(1, '鍏ㄩ儴')" :plain="isPlain">鍏ㄩ儴</div>
				<div class="item" :class="swiperIndex == index ? 'active' : ''" v-for="(item, index) in fenlei" :key="index" @click="getList(1, item, 'btn' + index)" :ref="'btn' + index" plain>{{item}}</div>
			</div>
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="list-item" >
					<div class="lable">鍟嗗搧鍚嶇О锛?/div>
					<el-input v-model="formSearch.shangpinmingcheng" placeholder="鍟嗗搧鍚嶇О" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">鍟嗗搧鍝佺墝锛?/div>
					<el-input v-model="formSearch.shangpinpinpai" placeholder="鍟嗗搧鍝佺墝" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">鍟嗗搧瑙勬牸锛?/div>
					<el-input v-model="formSearch.shangpinguige" placeholder="鍟嗗搧瑙勬牸" @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">浠锋牸锛?/div>
					<el-input v-model="formSearch.pricestart" placeholder="鏈€灏忎环鏍? clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<el-input v-model="formSearch.priceend" placeholder="鏈€澶т环鏍? clearable></el-input>
				</el-form-item>
				<div class="list-btn-box">
					<el-button class="list-search-btn" v-if=" true " type="primary" @click="getList(1, curFenlei)">
						<span class="icon iconfont icon-fangdajing07"></span>
						鎼滅储
					</el-button>
					<el-button class="list-add-btn" v-if="btnAuth('xianshangshangcheng','鏂板')" type="primary" @click="add('/index/xianshangshangchengAdd')">
						<span class="icon iconfont icon-tianjia7"></span>
						鏂板
					</el-button>
  
				</div>
			</el-form>
			<div class="sort_view">
				<el-button class="price-sort-btn" @click="sortClick('price')">
					<span class="icon iconfont icon-chujia13" v-if="sortType!='price'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='price'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='price'&&sortOrder=='asc'"></span>
					<span class="text">鎸変环鏍兼帓搴?/span>
				</el-button>
				<el-button class="click-sort-btn" @click="sortClick('clicknum')">
					<span class="icon iconfont icon-liulan13" v-if="sortType!='clicknum'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='clicknum'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='clicknum'&&sortOrder=='asc'"></span>
					<span class="text">鎸夌偣鍑婚噺鎺掑簭</span>
				</el-button>
				<el-button class="collect-sort-btn" @click="sortClick('storeupnum')">
					<span class="icon iconfont icon-shoucang10" v-if="sortType!='storeupnum'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='storeupnum'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='storeupnum'&&sortOrder=='asc'"></span>
					<span class="text">鎸夋敹钘忛噺鎺掑簭</span>
				</el-button>
				<el-button class="like-sort-btn" @click="sortClick('thumbsupnum')">
					<span class="icon iconfont icon-zan10" v-if="sortType!='thumbsupnum'"></span>
					<span class="icon iconfont icon-jiantou35" v-else-if="sortType=='thumbsupnum'&&sortOrder=='desc'"></span>
					<span class="icon iconfont icon-jiantou36" v-else-if="sortType=='thumbsupnum'&&sortOrder=='asc'"></span>
					<span class="text">鎸夌偣璧炴暟鎺掑簭</span>
				</el-button>
			</div>
			<div class="list">
				<!-- 鏍峰紡涓€ -->
				<div class="list1 index-pv1">
					<div v-for="(item, index) in dataList" :key="index" @click.stop="openDetailDialog(item)" class="list-item animation-box" >
						<img class="image" @click.stop="imgPreView(item.shangpintupian)" v-if="item.shangpintupian && item.shangpintupian.substr(0,4)=='http'&& item.shangpintupian.split(',w').length>1" :src="item.shangpintupian" />
						<img class="image" @click.stop="imgPreView(item.shangpintupian.split(',')[0])" v-else-if="item.shangpintupian && item.shangpintupian.substr(0,4)=='http'" :src="item.shangpintupian.split(',')[0]" />
						<img class="image" @click.stop="imgPreView(baseUrl + (item.shangpintupian?item.shangpintupian.split(',')[0]:''))" v-else :src="baseUrl + (item.shangpintupian?item.shangpintupian.split(',')[0]:'')" />
						<div class="price"><span style="font-size: 12px">锟?/span>{{item.price}}</div>
						<div class="name">{{item.shangpinmingcheng}}</div>
						<div class="name">{{item.shangpinfenlei}}</div>
						<div class="time_item">
							<span class="icon iconfont icon-shijian21"></span>
							<span class="label">鍙戝竷鏃堕棿锛?/span>
							<span class="text">{{item.addtime.split(' ')[0]}}</span>
						</div>
						<div class="like_item">
							<span class="icon iconfont icon-zan10"></span>
							<span class="label">鐐硅禐鏁帮細</span>
							<span class="text">{{item.thumbsupnum}}</span>
						</div>
						<div class="collect_item">
							<span class="icon iconfont icon-shoucang10"></span>
							<span class="label">鏀惰棌閲忥細</span>
							<span class="text">{{item.storeupnum}}</span>
						</div>
						<div class="view_item" v-if="item.clicknum">
							<span class="icon iconfont icon-chakan2"></span>
							<span class="label">鐐瑰嚮閲忥細</span>
							<span class="text">{{item.clicknum}}</span>
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
		<el-dialog
			:visible.sync="detailDialogVisible"
			width="70%"
			custom-class="shop-detail-dialog"
			:close-on-click-modal="true">
			<span slot="title">&#x5546;&#x54C1;&#x8BE6;&#x60C5;</span>
			<div class="detail-dialog" v-loading="detailDialogLoading">
				<div v-if="detailDialogData.id" class="detail-dialog__body">
					<div class="detail-dialog__media">
						<el-carousel
							v-if="detailDialogImages.length > 1"
							:autoplay="false"
							indicator-position="inside"
							height="360px">
							<el-carousel-item v-for="(image, index) in detailDialogImages" :key="index">
								<img class="detail-dialog__image" :src="resolveDetailImage(image)" alt="">
							</el-carousel-item>
						</el-carousel>
						<img
							v-else-if="detailDialogImages.length"
							class="detail-dialog__image detail-dialog__image--single"
							:src="resolveDetailImage(detailDialogImages[0])"
							alt="">
						<div v-else class="detail-dialog__image detail-dialog__empty">
							No Image
						</div>
					</div>
					<div class="detail-dialog__info">
						<div class="detail-dialog__title">{{detailDialogData.shangpinmingcheng}}</div>
						<div class="detail-dialog__price">
							<span class="detail-dialog__price-symbol">￥</span>
							<span>{{detailDialogData.price}}</span>
						</div>
						<div class="detail-dialog__meta">
							<div class="detail-dialog__meta-item">
								<span class="label">&#x5206;&#x7C7B;</span>
								<span class="value">{{detailDialogData.shangpinfenlei || '-'}}</span>
							</div>
							<div class="detail-dialog__meta-item">
								<span class="label">&#x54C1;&#x724C;</span>
								<span class="value">{{detailDialogData.shangpinpinpai || '-'}}</span>
							</div>
							<div class="detail-dialog__meta-item">
								<span class="label">&#x89C4;&#x683C;</span>
								<span class="value">{{detailDialogData.shangpinguige || '-'}}</span>
							</div>
							<div class="detail-dialog__meta-item">
								<span class="label">&#x5382;&#x5BB6;</span>
								<span class="value">{{detailDialogData.shengchanchangjia || '-'}}</span>
							</div>
							<div class="detail-dialog__meta-item">
								<span class="label">&#x5355;&#x9650;</span>
								<span class="value">{{detailDialogData.onelimittimes || 0}}</span>
							</div>
							<div class="detail-dialog__meta-item">
								<span class="label">&#x5E93;&#x5B58;</span>
								<span class="value">{{detailDialogData.alllimittimes || 0}}</span>
							</div>
						</div>
						<div class="detail-dialog__actions">
							<el-button type="primary" @click="toDetail(detailDialogData)">
								&#x8FDB;&#x5165;&#x5B8C;&#x6574;&#x8BE6;&#x60C5;
							</el-button>
						</div>
					</div>
				</div>
				<div v-if="detailDialogData.id && detailDialogData.shangpinxiangqing" class="detail-dialog__content ql-snow">
					<div class="ql-editor" v-html="detailDialogData.shangpinxiangqing"></div>
				</div>
				<div v-else-if="!detailDialogLoading" class="detail-dialog__empty">
					&#x6682;&#x65E0;&#x8BE6;&#x60C5;
				</div>
			</div>
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
						name: '绾夸笂鍟嗗煄'
					}
				],
				formSearch: {
					shangpinmingcheng: '',
					shangpinpinpai: '',
					shangpinguige: '',
					price: '',
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
				detailDialogVisible: false,
				detailDialogLoading: false,
				detailDialogData: {},
				detailDialogImages: [],
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
				await this.$http.get('option/shangpinfenlei/shangpinfenlei').then(res => {
					if (res.data.code == 0) {
						this.fenlei = res.data.data;
						if(this.$route.query.homeFenlei){
							for(let i=0;i<this.fenlei.length;i++) {
								if(this.$route.query.homeFenlei == this.fenlei[i]) {
									this.swiperIndex = i;
									const currentRoute = this.$route;
									const routeWithoutQuery = { ...currentRoute };
									delete routeWithoutQuery.query;
									this.$router.replace(routeWithoutQuery)
									break;
								}
							}
						}
					}
				});
			},
			getList(page, fenlei, ref = '') {
				if(fenlei == '鍏ㄩ儴') this.swiperIndex = -1;
				for(let i=0;i<this.fenlei.length;i++) {
					if(fenlei == this.fenlei[i]) {
						this.swiperIndex = i;
						break;
					}
				}
				if(fenlei){
					this.curFenlei = fenlei;
				}
				let params = {
					page,
					limit: this.pageSize,
				};
				if(!this.centerType) {
					params.onshelves = 1
				}
				let searchWhere = {};
				if (this.formSearch.shangpinmingcheng != ''&&this.formSearch.shangpinmingcheng != undefined) searchWhere.shangpinmingcheng = '%' + this.formSearch.shangpinmingcheng + '%';
				if (this.formSearch.shangpinpinpai != ''&&this.formSearch.shangpinpinpai != undefined) searchWhere.shangpinpinpai = '%' + this.formSearch.shangpinpinpai + '%';
				if (this.formSearch.shangpinguige != ''&&this.formSearch.shangpinguige != undefined) searchWhere.shangpinguige = '%' + this.formSearch.shangpinguige + '%';
				if(this.formSearch.pricestart!='' && this.formSearch.pricestart!=undefined ){
					searchWhere.pricestart = this.formSearch.pricestart
				}
				if(this.formSearch.priceend!='' && this.formSearch.priceend!=undefined){
					searchWhere.priceend = this.formSearch.priceend
				}
				if (this.curFenlei != '鍏ㄩ儴') searchWhere.shangpinfenlei = this.curFenlei;
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				if (this.sortType) searchWhere.sort = this.sortType
				if (this.sortOrder) searchWhere.order = this.sortOrder
				this.$http.get(`xianshangshangcheng/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(async res => {
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
			sortClick(type){
				if(this.sortType==type){
					if(this.sortOrder == 'desc'){
						this.sortOrder = 'asc'
					}else{
						this.sortOrder = ''
						this.sortType = ''
					}
				}else{
					this.sortType = type
					this.sortOrder = 'desc'
				}
				this.getList(1, '鍏ㄩ儴')
			},
			curChange(page) {
				this.getList(page,this.curFenlei);
			},
			prevClick(page) {
				this.getList(page,this.curFenlei);
			},
			sizeChange(size){
				this.pageSize = size
				this.getList(1,this.curFenlei);
			},
			nextClick(page) {
				this.getList(page,this.curFenlei);
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
			},
			splitDetailImages(value) {
				if (!value) {
					return []
				}
				if (value.split(',w').length > 1) {
					return [value]
				}
				return value.split(',').filter(item => item)
			},
			resolveDetailImage(url) {
				if (!url) {
					return ''
				}
				return url.substr(0, 4) === 'http' ? url : this.baseUrl + url
			},
			openDetailDialog(item) {
				if (!item || !item.id) {
					return
				}
				this.detailDialogVisible = true
				this.detailDialogLoading = true
				this.detailDialogData = {}
				this.detailDialogImages = []
				this.$http.get(`xianshangshangcheng/detail/${item.id}`).then(res => {
					if (res.data.code === 0 && res.data.data) {
						this.detailDialogData = res.data.data
						this.detailDialogImages = this.splitDetailImages(res.data.data.shangpintupian)
					} else {
						this.$message.error(res.data.msg || '\u52A0\u8F7D\u5546\u54C1\u8BE6\u60C5\u5931\u8D25')
					}
				}).catch(() => {
					this.$message.error('\u52A0\u8F7D\u5546\u54C1\u8BE6\u60C5\u5931\u8D25')
				}).finally(() => {
					this.detailDialogLoading = false
				})
			},
			toDetail(item) {
				let params = {
					id: item.id
				}
				this.detailDialogVisible = false
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/xianshangshangchengDetail', query: params});
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

			discussClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/discussxianshangshangcheng', query: params});
			},
			chapterClick (row){
				let params = {
					refid: row.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/chapterxianshangshangcheng', query: params});
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
		.category-1 {
			padding: 0;
			margin: 20px 30px 0 0;
			display: flex;
			border-color: #707070;
			flex-wrap: wrap;
			background: none;
			gap: 16px;
			width: 100%;
			justify-content: center;
			border-width: 1px 0 0 0;
			border-style: solid;
			height: auto;
			order: 3;
			.item {
				cursor: pointer;
				border: 0;
				padding: 0 20px;
				margin: 0 0 10px;
				color: #000000;
				font-weight: 600;
				display: flex;
				font-size: 16px;
				border-color: transparent;
				line-height: 45px;
				border-radius: 0;
				background: none;
				width: auto;
				border-width: 2px 0 0 0;
				align-items: center;
				border-style: solid;
			}
			.item:hover {
				padding: 0 20px;
				color: #46AC2E;
				background: linear-gradient( 180deg, #EEF8F2 0%, #FFFFFF 100%);
				font-weight: 600;
				width: auto;
				font-size: 16px;
				border-color: #46AC2E;
				border-width: 2px 0 0 0;
				line-height: 45px;
				border-style: solid;
			}
			.item.active {
				padding: 0 20px;
				color: #46AC2E;
				background: linear-gradient( 180deg, #EEF8F2 0%, #FFFFFF 100%);
				font-weight: 600;
				width: auto;
				font-size: 16px;
				border-color: #46AC2E;
				border-width: 2px 0 0 0;
				line-height: 45px;
				border-style: solid;
			}
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
		.sort_view {
			border: 0px solid #ddd;
			padding: 0;
			margin: 10px auto;
			color: #333;
			background: none;
			width: 100%;
			font-size: 16px;
			text-align: center;
			order: 2;
			.price-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
				}
			}
			.click-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
				}
			}
			.collect-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
				}
			}
			.like-sort-btn {
				border: 0;
				border-radius: 8px;
				padding: 0 10px;
				margin: 0 5px;
				color: inherit;
				background: none;
				font-size: inherit;
				.icon {
					margin: 0 2px 0 0;
					line-height: 40px;
				}
				.text {
					line-height: 40px;
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
	.detail-dialog {
		.detail-dialog__body {
			display: flex;
			gap: 24px;
			align-items: flex-start;
		}
		.detail-dialog__media {
			flex: 0 0 44%;
			max-width: 44%;
		}
		.detail-dialog__info {
			flex: 1;
			min-width: 0;
		}
		.detail-dialog__image {
			border-radius: 12px;
			background: #f5f5f5;
			display: block;
			width: 100%;
			height: 360px;
			object-fit: cover;
		}
		.detail-dialog__image--single {
			box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
		}
		.detail-dialog__title {
			color: #222;
			font-size: 24px;
			font-weight: 700;
			line-height: 1.4;
		}
		.detail-dialog__price {
			margin: 16px 0 20px;
			color: #e53935;
			font-size: 28px;
			font-weight: 700;
		}
		.detail-dialog__price-symbol {
			margin-right: 4px;
			font-size: 18px;
		}
		.detail-dialog__meta {
			display: grid;
			grid-template-columns: repeat(2, minmax(0, 1fr));
			gap: 12px 16px;
		}
		.detail-dialog__meta-item {
			border-radius: 10px;
			padding: 12px 14px;
			background: #f8faf7;
			display: flex;
			flex-direction: column;
			gap: 6px;
			.label {
				color: #7a7a7a;
				font-size: 13px;
			}
			.value {
				color: #222;
				font-size: 15px;
				word-break: break-word;
			}
		}
		.detail-dialog__actions {
			margin-top: 20px;
		}
		.detail-dialog__content {
			margin-top: 24px;
			border-radius: 12px;
			padding: 16px;
			background: #fafafa;
		}
		.detail-dialog__empty {
			border-radius: 12px;
			padding: 48px 16px;
			color: #999;
			background: #fafafa;
			text-align: center;
		}
	}
	@media (max-width: 992px) {
		.detail-dialog {
			.detail-dialog__body {
				flex-direction: column;
			}
			.detail-dialog__media {
				flex-basis: 100%;
				max-width: 100%;
				width: 100%;
			}
			.detail-dialog__image {
				height: 280px;
			}
			.detail-dialog__meta {
				grid-template-columns: 1fr;
			}
		}
	}
</style>

