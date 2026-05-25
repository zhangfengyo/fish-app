<template>
	<div class="news-detail-box">
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">Back</span>
			</el-button>
		</div>

		<div class="news-detail-view">
			<div class="news-detail">
				<div class="detail-title">{{ detail.title }}</div>
				<div class="infoBox">
					<div class="meta-item">{{ detail.addtime ? detail.addtime.split(' ')[0] : '' }}</div>
					<div class="meta-item">Publisher: {{ detail.name }}</div>
					<div v-if="detail.pinzhongfenlei" class="meta-badge">{{ detail.pinzhongfenlei }}</div>
				</div>
				<div class="detail-main">
					<div class="detail-image">
						<img :src="$assetUrl(detail.picture ? detail.picture.split(',')[0] : '')">
					</div>
					<div class="content-detail ql-snow ql-editor" v-html="detail.content"></div>
				</div>
			</div>

			<div class="option-box">
				<div class="prev-btn" @click="prepDetailClick">
					<span class="text">Prev: {{ getPrevTitle() }}</span>
				</div>
				<div class="next-btn" @click="nextDetailClick">
					<span class="text">Next: {{ getNextTitle() }}</span>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			id: 0,
			detail: {},
			baseUrl: '',
			currentIndex: 0,
			allList: [],
			storeupType: false,
		}
	},
	created() {
		if (this.$route.query.storeupType && this.$route.query.storeupType != 0) {
			this.storeupType = true
		}
		this.id = this.$route.query.id
		this.baseUrl = this.$config.baseUrl
		this.getDetail()
		this.getNewsList()
	},
	watch: {
		$route() {
			this.id = this.$route.query.id
			this.getDetail()
		},
	},
	methods: {
		backClick() {
			if (this.storeupType) {
				history.back()
			} else {
				this.$router.push({ path: '/index/news' })
			}
		},
		getNewsList() {
			const params = { page: 1, limit: 100, sort: 'addtime', order: 'desc' }
			this.$http.get('news/list', { params }).then(res => {
				if (res.data.code === 0) {
					for (let x in res.data.data.list) {
						if (res.data.data.list[x].id == this.id) {
							this.currentIndex = Number(x)
							break
						}
					}
					this.allList = res.data.data.list
				}
			})
		},
		getPrevTitle() {
			if (this.currentIndex === 0 || !this.allList[this.currentIndex - 1]) {
				return 'None'
			}
			return this.allList[this.currentIndex - 1].title
		},
		getNextTitle() {
			if (this.currentIndex === this.allList.length - 1 || !this.allList[this.currentIndex + 1]) {
				return 'None'
			}
			return this.allList[this.currentIndex + 1].title
		},
		prepDetailClick() {
			if (this.currentIndex === 0) {
				this.$message.error('Already first item')
				return false
			}
			this.currentIndex--
			this.$router.push({ path: '/index/newsDetail', query: { id: this.allList[this.currentIndex].id } })
		},
		nextDetailClick() {
			if (this.currentIndex === this.allList.length - 1) {
				this.$message.error('Already last item')
				return false
			}
			this.currentIndex++
			this.$router.push({ path: '/index/newsDetail', query: { id: this.allList[this.currentIndex].id } })
		},
		getDetail() {
			this.$http.get(`news/detail/${this.id}`, {}).then(res => {
				if (res.data && res.data.code === 0) {
					this.detail = res.data.data
					window.scrollTo(0, 100)
				}
			})
		},
	},
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.news-detail-box {
	padding: 0 10%;
	margin: 10px auto;
	width: 100%;
}

.news-detail {
	width: 100%;
}

.detail-title {
	color: #333;
	font-weight: 500;
	font-size: 24px;
	line-height: 60px;
	text-align: center;
}

.infoBox {
	padding: 0 0 16px;
	color: #999;
	display: flex;
	width: 100%;
	font-size: 15px;
	justify-content: center;
	align-items: center;
	flex-wrap: wrap;
	gap: 12px;
}

.meta-item {
	color: #666;
}

.meta-badge {
	display: inline-block;
	padding: 4px 10px;
	border-radius: 999px;
	background: #e8f5fb;
	color: #0f6fa8;
	font-size: 13px;
}

.detail-main {
	display: flex;
	flex-wrap: wrap;
	gap: 16px;
}

.detail-image {
	width: 40%;
	min-width: 260px;
	img {
		width: 100%;
		display: block;
		border-radius: 10px;
	}
}

.content-detail {
	flex: 1;
	color: #666;
	font-size: 16px;
	line-height: 1.8;
	min-width: 280px;
}

.option-box {
	padding: 0;
	margin: 20px auto;
	display: flex;
	width: 100%;
	justify-content: space-between;
	gap: 16px;
	flex-wrap: wrap;
}

.prev-btn,
.next-btn {
	cursor: pointer;
	border: 1px solid #46AC2E;
	border-radius: 26px;
	padding: 0 14px;
	background: #46AC2E;
}

.prev-btn .text,
.next-btn .text {
	color: #fff;
	font-size: 16px;
	line-height: 40px;
}

.prev-btn:hover,
.next-btn:hover {
	background: #007aff;
}
</style>
