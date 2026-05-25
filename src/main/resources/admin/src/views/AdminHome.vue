<template>
	<div class="dashboard-page">
		<section class="hero">
			<div class="hero-copy">
				<div class="eyebrow">{{ $t({ 'zh-CN': '数据统计大屏', 'en-US': 'Data Dashboard' }) }}</div>
				<h1>{{ $t({ 'zh-CN': '观赏鱼系统运营总览', 'en-US': 'Ornamental Fish Operations Overview' }) }}</h1>
				<p>
					{{ $t({
						'zh-CN': '集中展示用户、资讯、教学、提醒和商城核心数据，并通过图表呈现内容分布与用户偏好，适合课堂答辩和客户演示。',
						'en-US': 'A single dashboard for users, content, reminders, and shop data, with charts for distribution and preference analysis.',
					}) }}
				</p>
			</div>
			<div class="hero-side">
				<div class="hero-pill">
					<span>{{ $t({ 'zh-CN': '最近更新时间', 'en-US': 'Last updated' }) }}</span>
					<strong>{{ updatedAt || '--' }}</strong>
				</div>
				<div class="hero-pill">
					<span>{{ $t({ 'zh-CN': '图表数量', 'en-US': 'Charts' }) }}</span>
					<strong>3</strong>
				</div>
			</div>
		</section>

		<section class="stats-grid">
			<article class="stat-card" v-for="item in statCards" :key="item.key">
				<div class="stat-head">
					<div class="stat-title">{{ item.title }}</div>
					<div class="stat-chip">{{ item.chip }}</div>
				</div>
				<div class="stat-value">{{ item.value }}</div>
				<div class="stat-desc">{{ item.description }}</div>
			</article>
		</section>

		<section class="chart-grid">
			<article class="chart-card">
				<div class="chart-head">
					<h2>{{ $t({ 'zh-CN': '资讯分类分布', 'en-US': 'News Category Distribution' }) }}</h2>
					<p>{{ $t({ 'zh-CN': '按资讯鱼种分类统计', 'en-US': 'Grouped by fish category in news content' }) }}</p>
				</div>
				<div ref="newsChart" class="chart-box"></div>
			</article>

			<article class="chart-card">
				<div class="chart-head">
					<h2>{{ $t({ 'zh-CN': '用户收藏偏好', 'en-US': 'Favorite Preference Distribution' }) }}</h2>
					<p>{{ $t({ 'zh-CN': '根据收藏记录分析用户偏好', 'en-US': 'Preference pattern derived from favorite records' }) }}</p>
				</div>
				<div ref="preferenceChart" class="chart-box"></div>
			</article>

			<article class="chart-card chart-card-wide">
				<div class="chart-head">
					<h2>{{ $t({ 'zh-CN': '教学视频数量统计', 'en-US': 'Teaching Video Count' }) }}</h2>
					<p>{{ $t({ 'zh-CN': '按教学鱼种分类统计视频条目', 'en-US': 'Video-enabled teaching entries by category' }) }}</p>
				</div>
				<div ref="teachingChart" class="chart-box"></div>
			</article>
		</section>
	</div>
</template>

<script>
import * as echarts from 'echarts'

export default {
	data() {
		return {
			updatedAt: '',
			overview: {
				userCount: 0,
				newsCount: 0,
				teachingCount: 0,
				todayReminderCount: 0,
				shopProductCount: 0,
			},
			charts: {
				newsCategories: [],
				favoritePreferences: [],
				teachingVideos: [],
			},
			chartInstances: {},
		}
	},
	computed: {
		statCards() {
			return [
				{
					key: 'users',
					title: this.$t({ 'zh-CN': '用户数', 'en-US': 'Users' }),
					value: this.overview.userCount,
					description: this.$t({ 'zh-CN': '平台注册用户总量', 'en-US': 'Registered end users' }),
					chip: '01',
				},
				{
					key: 'news',
					title: this.$t({ 'zh-CN': '资讯数', 'en-US': 'News' }),
					value: this.overview.newsCount,
					description: this.$t({ 'zh-CN': '已发布资讯内容数量', 'en-US': 'Published news items' }),
					chip: '02',
				},
				{
					key: 'teaching',
					title: this.$t({ 'zh-CN': '教学数', 'en-US': 'Teachings' }),
					value: this.overview.teachingCount,
					description: this.$t({ 'zh-CN': '教学条目与视频内容', 'en-US': 'Teaching entries and videos' }),
					chip: '03',
				},
				{
					key: 'reminders',
					title: this.$t({ 'zh-CN': '今日提醒数', 'en-US': 'Today Reminders' }),
					value: this.overview.todayReminderCount,
					description: this.$t({ 'zh-CN': '按提醒日期统计今日待提醒数据', 'en-US': 'Today reminder records by reminder date' }),
					chip: '04',
				},
				{
					key: 'shop',
					title: this.$t({ 'zh-CN': '商城商品数', 'en-US': 'Shop Products' }),
					value: this.overview.shopProductCount,
					description: this.$t({ 'zh-CN': '商城商品基础数据总量', 'en-US': 'Total shop product records' }),
					chip: '05',
				},
			]
		},
	},
	async mounted() {
		await this.loadDashboard()
		window.addEventListener('resize', this.handleResize)
	},
	beforeDestroy() {
		window.removeEventListener('resize', this.handleResize)
		this.disposeCharts()
	},
	methods: {
		async loadDashboard() {
			const res = await this.$api.get('/dashboard/overview')
			const payload = res.data.data || {}
			this.updatedAt = payload.updatedAt || ''
			this.overview = Object.assign({}, this.overview, payload.overview || {})
			this.charts = Object.assign({}, this.charts, payload.charts || {})
			this.$nextTick(() => {
				this.renderCharts()
			})
		},
		disposeCharts() {
			Object.keys(this.chartInstances).forEach(key => {
				if (this.chartInstances[key]) {
					this.chartInstances[key].dispose()
				}
			})
			this.chartInstances = {}
		},
		handleResize() {
			Object.keys(this.chartInstances).forEach(key => {
				if (this.chartInstances[key]) {
					this.chartInstances[key].resize()
				}
			})
		},
		normalizeSeries(list, emptyLabel) {
			if (Array.isArray(list) && list.length) {
				return list.map(item => ({
					name: this.$fishLabel(item.name || 'general'),
					value: Number(item.value || 0),
				}))
			}
			return [
				{
					name: emptyLabel,
					value: 0,
				},
			]
		},
		renderCharts() {
			this.renderNewsChart()
			this.renderPreferenceChart()
			this.renderTeachingChart()
		},
		renderNewsChart() {
			const target = this.$refs.newsChart
			if (!target) {
				return
			}
			if (this.chartInstances.newsChart) {
				this.chartInstances.newsChart.dispose()
			}
			const chart = echarts.init(target)
			const series = this.normalizeSeries(
				this.charts.newsCategories,
				this.$t({ 'zh-CN': '暂无资讯数据', 'en-US': 'No news data' })
			)
			chart.setOption({
				color: ['#0f6fa8', '#1f8fcb', '#56b8e8', '#8ed8f8', '#73c0de', '#3ba272'],
				tooltip: {
					trigger: 'item',
				},
				legend: {
					bottom: 0,
					icon: 'circle',
				},
				series: [
					{
						name: 'news',
						type: 'pie',
						radius: ['42%', '70%'],
						center: ['50%', '44%'],
						data: series,
						label: {
							formatter: '{b}: {c}',
						},
					},
				],
			})
			this.chartInstances.newsChart = chart
		},
		renderPreferenceChart() {
			const target = this.$refs.preferenceChart
			if (!target) {
				return
			}
			if (this.chartInstances.preferenceChart) {
				this.chartInstances.preferenceChart.dispose()
			}
			const chart = echarts.init(target)
			const series = this.normalizeSeries(
				this.charts.favoritePreferences,
				this.$t({ 'zh-CN': '暂无偏好数据', 'en-US': 'No preference data' })
			)
			chart.setOption({
				color: ['#0f6fa8'],
				grid: {
					left: '8%',
					right: '6%',
					top: '12%',
					bottom: '10%',
					containLabel: true,
				},
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow',
					},
				},
				xAxis: {
					type: 'value',
					minInterval: 1,
					splitLine: {
						lineStyle: {
							color: 'rgba(15, 111, 168, 0.08)',
						},
					},
				},
				yAxis: {
					type: 'category',
					data: series.map(item => item.name),
					axisTick: { show: false },
				},
				series: [
					{
						type: 'bar',
						barWidth: 18,
						borderRadius: [0, 9, 9, 0],
						data: series.map(item => item.value),
					},
				],
			})
			this.chartInstances.preferenceChart = chart
		},
		renderTeachingChart() {
			const target = this.$refs.teachingChart
			if (!target) {
				return
			}
			if (this.chartInstances.teachingChart) {
				this.chartInstances.teachingChart.dispose()
			}
			const chart = echarts.init(target)
			const series = this.normalizeSeries(
				this.charts.teachingVideos,
				this.$t({ 'zh-CN': '暂无教学视频数据', 'en-US': 'No teaching video data' })
			)
			chart.setOption({
				color: ['#1f8fcb'],
				grid: {
					left: '6%',
					right: '4%',
					top: '14%',
					bottom: '14%',
					containLabel: true,
				},
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'shadow',
					},
				},
				xAxis: {
					type: 'category',
					data: series.map(item => item.name),
					axisLabel: {
						interval: 0,
						rotate: 20,
					},
					axisTick: { show: false },
				},
				yAxis: {
					type: 'value',
					minInterval: 1,
					splitLine: {
						lineStyle: {
							color: 'rgba(15, 111, 168, 0.08)',
						},
					},
				},
				series: [
					{
						type: 'bar',
						barWidth: 30,
						borderRadius: [12, 12, 0, 0],
						data: series.map(item => item.value),
						label: {
							show: true,
							position: 'top',
							color: '#163246',
						},
					},
				],
			})
			this.chartInstances.teachingChart = chart
		},
	},
}
</script>

<style scoped>
.dashboard-page {
	display: grid;
	gap: 22px;
}

.hero {
	display: grid;
	grid-template-columns: 1.6fr 0.8fr;
	gap: 18px;
	padding: 30px;
	border-radius: 28px;
	background: linear-gradient(135deg, rgba(7, 53, 83, 0.96) 0%, rgba(17, 113, 165, 0.94) 54%, rgba(95, 190, 235, 0.88) 100%);
	box-shadow: 0 28px 64px rgba(15, 111, 168, 0.18);
	color: #fff;
}

.eyebrow {
	display: inline-flex;
	padding: 6px 12px;
	border-radius: 999px;
	background: rgba(255, 255, 255, 0.14);
	border: 1px solid rgba(255, 255, 255, 0.18);
	font-size: 12px;
	font-weight: 700;
	letter-spacing: 0.8px;
	margin-bottom: 16px;
}

.hero h1 {
	margin: 0 0 10px;
	font-size: 36px;
	line-height: 1.1;
}

.hero p {
	margin: 0;
	max-width: 720px;
	line-height: 1.8;
	color: rgba(236, 247, 255, 0.88);
}

.hero-side {
	display: grid;
	gap: 14px;
	align-content: start;
}

.hero-pill {
	padding: 18px;
	border-radius: 20px;
	background: rgba(255, 255, 255, 0.12);
	border: 1px solid rgba(255, 255, 255, 0.16);
}

.hero-pill span {
	display: block;
	font-size: 12px;
	color: rgba(236, 247, 255, 0.76);
	margin-bottom: 8px;
}

.hero-pill strong {
	font-size: 20px;
}

.stats-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
	gap: 16px;
}

.stat-card,
.chart-card {
	background: #fff;
	border-radius: 24px;
	border: 1px solid rgba(15, 111, 168, 0.08);
	box-shadow: 0 22px 48px rgba(15, 111, 168, 0.08);
}

.stat-card {
	padding: 20px;
}

.stat-head {
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 12px;
	margin-bottom: 14px;
}

.stat-title {
	font-size: 14px;
	font-weight: 700;
	color: #3f5f72;
}

.stat-chip {
	min-width: 36px;
	height: 36px;
	display: grid;
	place-items: center;
	border-radius: 12px;
	background: linear-gradient(180deg, #e8f6ff 0%, #d8f1fb 100%);
	color: #0f6fa8;
	font-weight: 800;
}

.stat-value {
	font-size: 34px;
	line-height: 1;
	font-weight: 800;
	color: #163246;
	margin-bottom: 10px;
}

.stat-desc {
	color: #6b7f8d;
	line-height: 1.7;
	font-size: 13px;
}

.chart-grid {
	display: grid;
	grid-template-columns: repeat(2, minmax(0, 1fr));
	gap: 18px;
}

.chart-card {
	padding: 20px 20px 14px;
}

.chart-card-wide {
	grid-column: 1 / -1;
}

.chart-head {
	margin-bottom: 14px;
}

.chart-head h2 {
	margin: 0 0 8px;
	font-size: 22px;
	color: #163246;
}

.chart-head p {
	margin: 0;
	font-size: 13px;
	color: #6b7f8d;
}

.chart-box {
	height: 320px;
}

.chart-card-wide .chart-box {
	height: 360px;
}

@media (max-width: 1100px) {
	.hero {
		grid-template-columns: 1fr;
	}
	.chart-grid {
		grid-template-columns: 1fr;
	}
	.chart-card-wide {
		grid-column: auto;
	}
}
</style>
