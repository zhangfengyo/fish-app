<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'/'">
				<el-breadcrumb-item class="item1" to="/"><a>Home</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{ item.name }}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>

		<div class="news-preview-pv">
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="search-item">
					<el-input v-model="title" placeholder="Title"></el-input>
				</el-form-item>
				<el-button class="search-btn" type="primary" @click="getNewsList(1)">
					<span class="icon iconfont icon-chakan14"></span>
					Search
				</el-button>
			</el-form>

			<div class="list5 index-pv1">
				<div v-for="item in newsList" :key="item.id" class="list-item animation-box" @click="toNewsDetail(item)">
					<img :src="$assetUrl(item.picture ? item.picture.split(',')[0] : '')">
					<div class="infoBox">
						<div class="name">{{ item.title }}</div>
						<div class="desc">{{ item.introduction }}</div>
						<div class="meta-row">
							<span class="meta-item">{{ item.addtime ? item.addtime.split(' ')[0] : '' }}</span>
							<span class="meta-item">Publisher: {{ item.name }}</span>
							<span v-if="item.pinzhongfenlei" class="meta-badge">{{ item.pinzhongfenlei }}</span>
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
				:page-sizes="pageSizes"
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
	</div>
</template>

<script>
export default {
	data() {
		return {
			baseUrl: this.$config.baseUrl,
			breadcrumbItem: [
				{ name: 'News' },
			],
			formSearch: {},
			newsList: [],
			total: 1,
			pageSize: 10,
			pageSizes: [],
			totalPage: 1,
			title: '',
		}
	},
	created() {
		this.getNewsList(1)
	},
	watch: {
		$route() {
			this.getNewsList(1)
		},
	},
	methods: {
		getNewsList(page) {
			const params = { page, limit: this.pageSize, sort: 'addtime', order: 'desc' }
			const searchWhere = {}
			if (this.title !== '') {
				searchWhere.title = '%' + this.title + '%'
			}
			this.$http.get('news/list', { params: Object.assign(params, searchWhere) }).then(res => {
				if (res.data.code === 0) {
					this.newsList = res.data.data.list
					this.total = res.data.data.total
					this.pageSize = Number(res.data.data.pageSize)
					this.totalPage = res.data.data.totalPage
					if (this.pageSizes.length === 0) {
						this.pageSizes = [this.pageSize, this.pageSize * 2, this.pageSize * 3, this.pageSize * 5]
					}
				}
			})
		},
		curChange(page) {
			this.getNewsList(page)
		},
		sizeChange(size) {
			this.pageSize = size
			this.getNewsList(1)
		},
		prevClick(page) {
			this.getNewsList(page)
		},
		nextClick(page) {
			this.getNewsList(page)
		},
		toNewsDetail(item) {
			this.$router.push({ path: '/index/newsDetail', query: { id: item.id } })
		},
	},
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.news-preview-pv {
	padding: 0 10%;
	margin: 0 auto;
	display: flex;
	width: 100%;
	align-items: flex-start;
	position: relative;
	flex-wrap: wrap;
}

.list-form-pv {
	padding: 10px 0;
	display: flex;
	width: 100%;
	justify-content: center;
	align-items: center;
	flex-wrap: wrap;
}

.search-item {
	margin: 0 10px;
}

.search-item .el-input {
	width: 100%;
}

.search-item .el-input ::v-deep .el-input__inner {
	border: 1px solid #ddd;
	border-radius: 4px;
	padding: 0 10px;
	color: #333;
	width: 500px;
	font-size: 16px;
	line-height: 42px;
	height: 42px;
}

.search-btn {
	cursor: pointer;
	border: 0;
	border-radius: 4px;
	padding: 0 15px;
	margin: 0 10px 0 0;
	color: #fff;
	background: #46AC2E;
	font-size: 16px;
	line-height: 42px;
	height: 42px;
}

.list5 {
	padding: 0;
	margin: 20px 0 0;
	flex: 1;
	display: flex;
	width: 100%;
	flex-wrap: wrap;
}

.list-item {
	cursor: pointer;
	padding: 0 0 15px;
	margin: 0 0 15px;
	background: #fff;
	display: flex;
	width: 100%;
	border-bottom: 1px solid #eee;
}

.list-item img {
	border-radius: 4px;
	object-fit: cover;
	display: block;
	width: 120px;
	height: 120px;
}

.infoBox {
	padding: 0 0 0 20px;
	flex: 1;
	display: flex;
	flex-wrap: wrap;
	align-content: center;
}

.name {
	overflow: hidden;
	color: #000;
	white-space: nowrap;
	font-weight: 500;
	width: 100%;
	font-size: 16px;
	line-height: 30px;
	text-overflow: ellipsis;
}

.desc {
	margin: 0;
	overflow: hidden;
	color: #666;
	width: 100%;
	font-size: 14px;
	line-height: 24px;
	height: 48px;
}

.meta-row {
	display: flex;
	flex-wrap: wrap;
	align-items: center;
	gap: 12px;
	width: 100%;
	margin-top: 8px;
}

.meta-item {
	color: #666;
	font-size: 14px;
}

.meta-badge {
	display: inline-block;
	padding: 4px 10px;
	border-radius: 999px;
	background: #e8f5fb;
	color: #0f6fa8;
	font-size: 13px;
}

.index-pv1 .animation-box {
	transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
	z-index: initial;
}

.index-pv1 .animation-box:hover {
	transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(6px, 0px, 0px);
	transition: 0.3s;
	z-index: 1;
}
</style>

