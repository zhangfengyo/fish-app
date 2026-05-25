<template>
	<div class="page">
		<section class="hero">
			<div class="hero-copy">
				<div class="eyebrow">{{ $t({ 'zh-CN': '观赏鱼智能', 'en-US': 'Ornamental Intelligence' }) }}</div>
				<h1>{{ $t({ 'zh-CN': '推荐资讯', 'en-US': 'Recommended News' }) }}</h1>
				<p>{{ $t({ 'zh-CN': '基于你的兴趣推送观赏鱼资讯，并整合教学视频与提醒能力。', 'en-US': 'Personalized ornamental fish content based on your interests, with teaching videos and care reminders in one place.' }) }}</p>
			</div>
			<div class="hero-panel">
				<div class="hero-stat">
					<span class="value">{{ recommendNews.length }}</span>
					<span class="label">{{ $t({ 'zh-CN': '推荐资讯数', 'en-US': 'Recommended stories' }) }}</span>
				</div>
				<div class="hero-stat">
					<span class="value">{{ teachings.length }}</span>
					<span class="label">{{ $t({ 'zh-CN': '教学条目数', 'en-US': 'Teaching entries' }) }}</span>
				</div>
				<div class="hero-note">{{ $t({ 'zh-CN': '蓝色演示版，适合客户展示', 'en-US': 'Blue-season launch build for client demo' }) }}</div>
			</div>
		</section>

		<section class="grid-section">
			<h2>{{ preferenceTitle }}</h2>
			<div class="grid">
				<article class="card" v-for="item in recommendNews" :key="item.id" @click="$router.push(`/news/${item.id}`)">
					<img :src="$assetUrl(firstImage(item.picture))" />
					<div class="card-body">
						<div class="badge">{{ $fishLabel(item.pinzhongfenlei || 'general') }}</div>
						<h3>{{ item.title }}</h3>
						<p>{{ item.introduction }}</p>
					</div>
				</article>
			</div>
		</section>

		<section class="grid-section">
			<div class="section-head">
				<h2>{{ $t({ 'zh-CN': '教学视频', 'en-US': 'Teaching Videos' }) }}</h2>
				<el-button type="text" @click="$router.push('/teachings')">{{ $t({ 'zh-CN': '查看全部', 'en-US': 'View all' }) }}</el-button>
			</div>
			<div class="grid">
				<article class="card" v-for="item in teachings" :key="item.id" @click="$router.push(`/teachings/${item.id}`)">
					<img :src="$assetUrl(firstImage(item.fengmian))" />
					<div class="card-body">
						<div class="badge">{{ $fishLabel(item.pinzhongfenlei || 'teaching') }}</div>
						<h3>{{ item.jiaoxuebiaoti }}</h3>
						<p>{{ item.siyangfangfa }}</p>
					</div>
				</article>
			</div>
		</section>
	</div>
</template>

<script>
export default {
	data() {
		return {
			recommendNews: [],
			recommendPreference: '',
			teachings: [],
		}
	},
	computed: {
		preferenceTitle() {
			if (this.recommendPreference) {
				return this.$t({
					'zh-CN': `偏好品种：${this.$fishLabel(this.recommendPreference)}`,
					'en-US': `Favorite: ${this.$fishLabel(this.recommendPreference)}`,
				})
			}
			return this.$t({ 'zh-CN': '最新资讯', 'en-US': 'Latest News' })
		},
	},
	async created() {
		await Promise.all([this.loadRecommend(), this.loadTeachings()])
	},
	methods: {
		firstImage(value) {
			return value ? value.split(',')[0] : ''
		},
		async loadRecommend() {
			const res = await this.$api.get('/news/recommend', { params: { limit: 4 } })
			this.recommendNews = (res.data.data && res.data.data.list) || []
			this.recommendPreference = (res.data.data && res.data.data.preference) || ''
		},
		async loadTeachings() {
			const res = await this.$api.get('/siyangjiaoxue/list', { params: { page: 1, limit: 4 } })
			this.teachings = res.data.data.list || []
		},
	},
}
</script>

<style scoped>
.hero {
	padding: 34px 34px 28px;
	border-radius: 28px;
	display: grid;
	grid-template-columns: 1.6fr 0.9fr;
	gap: 24px;
	background:
		linear-gradient(135deg, rgba(7, 53, 83, 0.96) 0%, rgba(17, 113, 165, 0.94) 55%, rgba(95, 190, 235, 0.88) 100%);
	box-shadow: 0 28px 56px rgba(15, 111, 168, 0.15);
	color: #fff;
}
.eyebrow {
	display: inline-flex;
	padding: 6px 12px;
	border-radius: 999px;
	background: rgba(255,255,255,0.14);
	border: 1px solid rgba(255,255,255,0.18);
	font-size: 12px;
	letter-spacing: 0.9px;
	text-transform: uppercase;
	margin-bottom: 16px;
}
.hero h1 {
	margin: 0;
	font-size: 40px;
	color: #fff;
	line-height: 1.08;
}
.hero p {
	max-width: 680px;
	color: rgba(235, 247, 255, 0.84);
	font-size: 15px;
	line-height: 1.8;
}
.hero-panel {
	display: grid;
	gap: 14px;
}
.hero-stat {
	padding: 18px 18px 16px;
	border-radius: 20px;
	background: rgba(255,255,255,0.12);
	border: 1px solid rgba(255,255,255,0.16);
	backdrop-filter: blur(8px);
}
.hero-stat .value {
	display: block;
	font-size: 28px;
	font-weight: 800;
	line-height: 1;
	margin-bottom: 8px;
}
.hero-stat .label {
	color: rgba(235, 247, 255, 0.82);
	font-size: 13px;
}
.hero-note {
	padding: 14px 18px;
	border-radius: 18px;
	background: rgba(255,255,255,0.08);
	font-size: 13px;
	color: rgba(235, 247, 255, 0.76);
}
.grid-section {
	margin-top: 34px;
}
.section-head {
	display: flex;
	justify-content: space-between;
	align-items: center;
}
.section-head h2,
.grid-section h2 {
	margin: 0;
	font-size: 24px;
	color: #163246;
}
.grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
	gap: 20px;
	margin-top: 18px;
}
.card {
	cursor: pointer;
	background: #fff;
	border: 1px solid rgba(15, 111, 168, 0.08);
	border-radius: 22px;
	overflow: hidden;
	box-shadow: 0 22px 44px rgba(15, 111, 168, 0.08);
	transition: transform 0.22s ease, box-shadow 0.22s ease;
}
.card img {
	width: 100%;
	height: 180px;
	object-fit: cover;
	display: block;
}
.card:hover {
	transform: translateY(-6px);
	box-shadow: 0 28px 54px rgba(15, 111, 168, 0.14);
}
.card-body {
	padding: 18px;
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
	font-size: 18px;
	color: #163246;
}
.card p {
	margin: 0;
	color: #667784;
	line-height: 1.7;
	font-size: 14px;
}
@media (max-width: 920px) {
	.hero {
		grid-template-columns: 1fr;
	}
	.hero h1 {
		font-size: 34px;
	}
}
</style>
