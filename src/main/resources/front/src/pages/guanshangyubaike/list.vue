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
					<div class="lable">涓枃鍚嶏細</div>
					<el-input v-model="formSearch.zhongwenming" placeholder="涓枃鍚? @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">鑻辨枃鍚嶏細</div>
					<el-input v-model="formSearch.yingwenming" placeholder="鑻辨枃鍚? @keydown.enter.native="getList(1, curFenlei)" clearable></el-input>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">鍝佺鍒嗙被锛?/div>
					<el-select v-model="formSearch.pinzhongfenlei" placeholder="璇烽€夋嫨鍝佺鍒嗙被" :clearable="true">
						<el-option v-for="(item, index) in pinzhongfenleiOptions" :key="index" :label="item" :value="item"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item class="list-item" >
					<div class="lable">楗插吇闅惧害锛?/div>
					<el-select v-model="formSearch.siyangnandu" placeholder="璇烽€夋嫨楗插吇闅惧害" :clearable="true">
						<el-option v-for="(item, index) in siyangnanduOptions" :key="index" :label="item" :value="item"></el-option>
					</el-select>
				</el-form-item>
				<div class="list-btn-box">
					<el-button class="list-search-btn" v-if=" true " type="primary" @click="getList(1, curFenlei)">
						<span class="icon iconfont icon-fangdajing07"></span>
						鎼滅储
					</el-button>
					<el-button class="list-add-btn" v-if="btnAuth('guanshangyubaike','鏂板')" type="primary" @click="add('/index/guanshangyubaikeAdd')">
						<span class="icon iconfont icon-tianjia7"></span>
						鏂板
					</el-button>
  
				</div>
			</el-form>
			<div class="sort_view">
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
					<div v-for="(item, index) in dataList" :key="index" @click.stop="toDetail(item)" class="list-item animation-box" >
						<img class="image" @click.stop="imgPreView(item.guanshangyutupian)" v-if="item.guanshangyutupian && item.guanshangyutupian.substr(0,4)=='http'&& item.guanshangyutupian.split(',w').length>1" :src="item.guanshangyutupian" />
						<img class="image" @click.stop="imgPreView(item.guanshangyutupian.split(',')[0])" v-else-if="item.guanshangyutupian && item.guanshangyutupian.substr(0,4)=='http'" :src="item.guanshangyutupian.split(',')[0]" />
						<img class="image" @click.stop="imgPreView(baseUrl + (item.guanshangyutupian?item.guanshangyutupian.split(',')[0]:''))" v-else :src="baseUrl + (item.guanshangyutupian?item.guanshangyutupian.split(',')[0]:'')" />
						<div class="name">{{item.zhongwenming}}</div>
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
						name: '瑙傝祻楸肩櫨绉?
					}
				],
				formSearch: {
					zhongwenming: '',
					yingwenming: '',
					pinzhongfenlei: '',
					siyangnandu: '',
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
				pinzhongfenleiOptions: [],
				siyangnanduOptions: [],
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
			await this.$http.get('option/pinzhongfenlei/pinzhongfenlei').then(res => {
				if (res.data.code == 0) {
					this.pinzhongfenleiOptions = res.data.data;
				}
			});
			this.siyangnanduOptions = '绠€鍗?涓瓑,鍥伴毦'.split(',');
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
				await this.$http.get('option/pinzhongfenlei/pinzhongfenlei').then(res => {
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
				let searchWhere = {};
				if (this.formSearch.zhongwenming != ''&&this.formSearch.zhongwenming != undefined) searchWhere.zhongwenming = '%' + this.formSearch.zhongwenming + '%';
				if (this.formSearch.yingwenming != ''&&this.formSearch.yingwenming != undefined) searchWhere.yingwenming = '%' + this.formSearch.yingwenming + '%';
				if (this.formSearch.pinzhongfenlei != ''&&this.formSearch.pinzhongfenlei != undefined) searchWhere.pinzhongfenlei = this.formSearch.pinzhongfenlei;
				if (this.formSearch.siyangnandu != ''&&this.formSearch.siyangnandu != undefined) searchWhere.siyangnandu = this.formSearch.siyangnandu;
				if (this.curFenlei != '鍏ㄩ儴') searchWhere.pinzhongfenlei = this.curFenlei;
				let user = JSON.parse(localStorage.getItem('sessionForm'))
				if (this.sortType) searchWhere.sort = this.sortType
				if (this.sortOrder) searchWhere.order = this.sortOrder
				this.$http.get(`guanshangyubaike/${this.centerType?'page':'list'}`, {params: Object.assign(params, searchWhere)}).then(async res => {
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
			toDetail(item) {
				let params = {
					id: item.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/guanshangyubaikeDetail', query: params});
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
				this.$router.push({path: '/index/chapterguanshangyubaike', query: params});
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
</style>

