<template>
	<div class="detail" v-if="detail">
		<div class="detail-head">
			<el-button type="text" @click="$router.back()">{{ $t({ 'zh-CN': '返回', 'en-US': 'Back' }) }}</el-button>
			<el-button size="mini" :type="isFavorite ? 'success' : 'default'" @click="toggleFavorite">
				{{ isFavorite ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
			</el-button>
		</div>
		<h1>{{ detail.jiaoxuebiaoti }}</h1>
		<div class="sub">{{ $fishLabel(detail.pinzhongfenlei || 'teaching') }}</div>
		<img class="hero" :src="$assetUrl(firstImage(detail.fengmian))" />
		<video v-if="detail.jiaoxueshipin" class="video" :src="$assetUrl(detail.jiaoxueshipin)" controls></video>
		<div class="section">
			<h3>{{ $t({ 'zh-CN': '饲养方法', 'en-US': 'Care Method' }) }}</h3>
			<p>{{ detail.siyangfangfa }}</p>
		</div>
		<div class="section">
			<h3>{{ $t({ 'zh-CN': '疾病处理', 'en-US': 'Disease Treatment' }) }}</h3>
			<p>{{ detail.yubingzhiliao }}</p>
		</div>
		<div class="section ql-snow ql-editor" v-html="detail.jiaoxuexiangqing"></div>
	</div>
</template>

<script>
export default {
	props: ['id'],
	data() {
		return {
			detail: null,
			favoriteRecord: null,
		}
	},
	created() {
		this.loadDetail()
	},
	computed: {
		userid() {
			return Number(localStorage.getItem('frontUserid') || 0)
		},
		isFavorite() {
			return !!this.favoriteRecord
		},
	},
	watch: {
		id() {
			this.loadDetail()
		},
	},
	methods: {
		firstImage(value) {
			return value ? value.split(',')[0] : ''
		},
		async loadDetail() {
			const res = await this.$api.get(`/siyangjiaoxue/detail/${this.id}`)
			this.detail = res.data.data
			await this.loadFavoriteRecord()
		},
		async loadFavoriteRecord() {
			if (!this.userid || !this.detail) {
				this.favoriteRecord = null
				return
			}
			const res = await this.$api.get('/storeup/list', {
				params: {
					page: 1,
					limit: 1,
					userid: this.userid,
					type: '1',
					tablename: 'siyangjiaoxue',
					refid: this.detail.id,
				},
			})
			const list = (res.data.data && res.data.data.list) || []
			this.favoriteRecord = list[0] || null
		},
		async toggleFavorite() {
			if (!this.userid) {
				this.$message.warning(this.$t({ 'zh-CN': '请先登录后再收藏教学内容', 'en-US': 'Please log in before favoriting teachings' }))
				this.$router.push('/login')
				return
			}
			if (this.favoriteRecord) {
				const res = await this.$api.post('/storeup/delete', [this.favoriteRecord.id])
				if (res.data.code === 0) {
					await this.updateStoreupCount(-1)
					this.favoriteRecord = null
					this.$message.success(this.$t({ 'zh-CN': '已取消收藏', 'en-US': 'Favorite removed' }))
				}
				return
			}
			const payload = {
				name: this.detail.jiaoxuebiaoti,
				picture: this.firstImage(this.detail.fengmian),
				refid: this.detail.id,
				tablename: 'siyangjiaoxue',
				userid: this.userid,
				type: '1',
				inteltype: this.detail.pinzhongfenlei || '',
			}
			const res = await this.$api.post('/storeup/add', payload)
			if (res.data.code === 0) {
				await this.updateStoreupCount(1)
				await this.loadFavoriteRecord()
				this.$message.success(this.$t({ 'zh-CN': '收藏成功', 'en-US': 'Favorited' }))
			}
		},
		async updateStoreupCount(delta) {
			if (!this.detail) {
				return
			}
			const nextCount = Math.max(0, Number(this.detail.storeupnum || 0) + delta)
			const payload = Object.assign({}, this.detail, { storeupnum: nextCount })
			await this.$api.post('/siyangjiaoxue/update', payload)
			this.detail.storeupnum = nextCount
		},
	},
}
</script>

<style scoped>
.detail {
	background: #fff;
	padding: 28px;
	border-radius: 24px;
	border: 1px solid rgba(15, 111, 168, 0.08);
	box-shadow: 0 22px 42px rgba(15, 111, 168, 0.08);
}
.detail-head {
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 12px;
}
.detail h1 {
	margin: 0;
	color: #163246;
}
.sub {
	margin: 10px 0 16px;
	color: #0f6fa8;
}
.hero {
	width: 100%;
	max-height: 360px;
	object-fit: cover;
	border-radius: 18px;
	margin-bottom: 20px;
}
.video {
	width: 100%;
	border-radius: 18px;
	margin-bottom: 20px;
}
.section {
	margin-top: 18px;
}
.section h3 {
	margin-bottom: 8px;
	color: #163246;
}
.section p {
	color: #667784;
	line-height: 1.8;
}
</style>
