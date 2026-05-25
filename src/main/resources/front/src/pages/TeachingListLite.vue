<template>
	<div class="page">
		<div class="toolbar">
			<h1>{{ $t({ 'zh-CN': '教学列表', 'en-US': 'Teachings' }) }}</h1>
		</div>
		<div class="grid">
			<div class="card" v-for="item in teachings" :key="item.id" @click="$router.push(`/teachings/${item.id}`)">
				<div class="image-wrap">
					<img :src="$assetUrl(firstImage(item.fengmian))" />
					<el-button
						size="mini"
						:type="isFavorite(item.id) ? 'success' : 'default'"
						class="favorite-btn"
						@click.stop="toggleFavorite(item)"
					>
						{{ isFavorite(item.id) ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
					</el-button>
				</div>
				<div class="card-body">
					<div class="meta-row">
						<div class="badge">{{ $fishLabel(item.pinzhongfenlei || 'teaching') }}</div>
						<div class="storeup-count">{{ $t({ 'zh-CN': '收藏', 'en-US': 'Favorites' }) }} {{ item.storeupnum || 0 }}</div>
					</div>
					<h3>{{ item.jiaoxuebiaoti }}</h3>
					<p>{{ item.siyangfangfa }}</p>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			teachings: [],
			favoriteMap: {},
		}
	},
	async created() {
		await this.loadTeachings()
		await this.loadFavorites()
	},
	computed: {
		userid() {
			return Number(localStorage.getItem('frontUserid') || 0)
		},
	},
	methods: {
		firstImage(value) {
			return value ? value.split(',')[0] : ''
		},
		async loadTeachings() {
			const res = await this.$api.get('/siyangjiaoxue/list', {
				params: { page: 1, limit: 20 },
			})
			this.teachings = (res.data.data && res.data.data.list) || []
		},
		async loadFavorites() {
			if (!this.userid) {
				this.favoriteMap = {}
				return
			}
			const res = await this.$api.get('/storeup/list', {
				params: {
					page: 1,
					limit: 200,
					userid: this.userid,
					type: '1',
					tablename: 'siyangjiaoxue',
				},
			})
			const list = (res.data.data && res.data.data.list) || []
			this.favoriteMap = list.reduce((map, item) => {
				map[item.refid] = item
				return map
			}, {})
		},
		isFavorite(id) {
			return !!this.favoriteMap[id]
		},
		async toggleFavorite(item) {
			if (!this.userid) {
				this.$message.warning(this.$t({ 'zh-CN': '请先登录后再收藏教学内容', 'en-US': 'Please log in before favoriting teachings' }))
				this.$router.push('/login')
				return
			}
			if (this.isFavorite(item.id)) {
				await this.removeFavorite(item)
				return
			}
			const payload = {
				name: item.jiaoxuebiaoti,
				picture: this.firstImage(item.fengmian),
				refid: item.id,
				tablename: 'siyangjiaoxue',
				userid: this.userid,
				type: '1',
				inteltype: item.pinzhongfenlei || '',
			}
			const res = await this.$api.post('/storeup/add', payload)
			if (res.data.code === 0) {
				await this.syncStoreupCount(item, 1)
				await this.loadFavorites()
				this.$message.success(this.$t({ 'zh-CN': '收藏成功', 'en-US': 'Favorited' }))
			}
		},
		async removeFavorite(item) {
			const record = this.favoriteMap[item.id]
			if (!record) {
				return
			}
			const res = await this.$api.post('/storeup/delete', [record.id])
			if (res.data.code === 0) {
				await this.syncStoreupCount(item, -1)
				await this.loadFavorites()
				this.$message.success(this.$t({ 'zh-CN': '已取消收藏', 'en-US': 'Favorite removed' }))
			}
		},
		async syncStoreupCount(item, delta) {
			const nextCount = Math.max(0, Number(item.storeupnum || 0) + delta)
			const payload = Object.assign({}, item, { storeupnum: nextCount })
			await this.$api.post('/siyangjiaoxue/update', payload)
			item.storeupnum = nextCount
		},
	},
}
</script>

<style scoped>
.toolbar {
	display: flex;
	align-items: center;
	gap: 12px;
	margin-bottom: 20px;
	padding: 20px 22px;
	border-radius: 22px;
	background: #fff;
	box-shadow: 0 18px 35px rgba(15, 111, 168, 0.08);
}
.toolbar h1 {
	margin: 0;
	color: #163246;
}
.grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
	gap: 20px;
}
.card {
	background: #fff;
	border-radius: 22px;
	overflow: hidden;
	box-shadow: 0 18px 35px rgba(15, 111, 168, 0.08);
	cursor: pointer;
	transition: transform 0.22s ease, box-shadow 0.22s ease;
}
.card:hover {
	transform: translateY(-6px);
	box-shadow: 0 24px 46px rgba(15, 111, 168, 0.14);
}
.image-wrap {
	position: relative;
}
.card img {
	width: 100%;
	height: 180px;
	object-fit: cover;
}
.favorite-btn {
	position: absolute;
	top: 12px;
	right: 12px;
}
.card-body {
	padding: 16px;
}
.meta-row {
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 10px;
}
.badge {
	display: inline-block;
	padding: 4px 10px;
	border-radius: 999px;
	background: linear-gradient(90deg, #e6f7ff 0%, #d8f1fb 100%);
	color: #0f6fa8;
	font-size: 12px;
	margin-bottom: 10px;
}
.card h3 {
	margin: 0 0 8px;
}
.card p {
	color: #667784;
	line-height: 1.7;
}
.storeup-count {
	color: #64748b;
	font-size: 12px;
}
</style>
