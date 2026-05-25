<template>
	<div class="layout">
		<div class="backdrop">
			<div class="glow glow-a"></div>
			<div class="glow glow-b"></div>
		</div>
		<aside class="sidebar">
			<div class="logo">{{ $t({ 'zh-CN': '管理控制台', 'en-US': 'Admin Panel' }) }}</div>
			<div class="menu">
				<router-link to="/home">{{ $t({ 'zh-CN': '仪表盘', 'en-US': 'Dashboard' }) }}</router-link>
				<router-link to="/news">{{ $t({ 'zh-CN': '资讯管理', 'en-US': 'News' }) }}</router-link>
				<router-link to="/teachings">{{ $t({ 'zh-CN': '教学管理', 'en-US': 'Teachings' }) }}</router-link>
				<router-link to="/shop">{{ $t({ 'zh-CN': '商城管理', 'en-US': 'Shop' }) }}</router-link>
				<router-link to="/orders">{{ $t({ 'zh-CN': '订单管理', 'en-US': 'Orders' }) }}</router-link>
				<router-link to="/aquariums">{{ $t({ 'zh-CN': '水族馆管理', 'en-US': 'Aquariums' }) }}</router-link>
				<router-link to="/aquarium-categories">{{ $t({ 'zh-CN': '场馆分类', 'en-US': 'Aquarium Categories' }) }}</router-link>
				<router-link to="/forum">{{ $t({ 'zh-CN': '论坛管理', 'en-US': 'Forum' }) }}</router-link>
				<router-link to="/encyclopedia">{{ $t({ 'zh-CN': '百科管理', 'en-US': 'Encyclopedia' }) }}</router-link>
				<router-link to="/fish-categories">{{ $t({ 'zh-CN': '品种分类', 'en-US': 'Fish Categories' }) }}</router-link>
				<router-link to="/members">{{ $t({ 'zh-CN': '用户管理', 'en-US': 'Users' }) }}</router-link>
				<router-link to="/assistant">{{ $t({ 'zh-CN': '智能助手', 'en-US': 'Assistant' }) }}</router-link>
			</div>
		</aside>
		<div class="main">
			<header class="topbar">
				<div class="title">{{ $t({ 'zh-CN': '观赏鱼后台', 'en-US': 'Ornamental Fish Admin' }) }}</div>
				<div class="actions">
					<div class="locale-switch">
						<button :class="{ active: $locale.value === 'zh-CN' }" @click="$setLocale('zh-CN')">中</button>
						<button :class="{ active: $locale.value === 'en-US' }" @click="$setLocale('en-US')">EN</button>
					</div>
					<span>{{ adminName }}</span>
					<el-button size="mini" type="primary" @click="logout">{{ $t({ 'zh-CN': '退出登录', 'en-US': 'Logout' }) }}</el-button>
				</div>
			</header>
			<section class="content">
				<router-view />
			</section>
		</div>
	</div>
</template>

<script>
export default {
	computed: {
		adminName() {
			return localStorage.getItem('adminName') || 'admin'
		},
	},
	methods: {
		async logout() {
			try {
				await this.$api.post('/users/logout')
			} catch (e) {
			}
			localStorage.removeItem('adminToken')
			localStorage.removeItem('adminName')
			this.$router.push('/login')
		},
	},
}
</script>

<style scoped>
.layout {
	display: flex;
	min-height: 100vh;
	position: relative;
	background: linear-gradient(180deg, #eef8fc 0%, #f8fcff 100%);
}

.backdrop {
	position: fixed;
	inset: 0;
	pointer-events: none;
}

.glow {
	position: absolute;
	border-radius: 999px;
	filter: blur(70px);
	opacity: 0.22;
}

.glow-a {
	width: 280px;
	height: 280px;
	background: #56b8e8;
	top: 110px;
	right: 80px;
}

.glow-b {
	width: 320px;
	height: 320px;
	background: #0f6fa8;
	left: -80px;
	bottom: 120px;
}

.sidebar {
	width: 240px;
	background: linear-gradient(180deg, #0f6fa8 0%, #1f8fcb 60%, #56b8e8 100%);
	color: #fff;
	padding: 26px 18px;
	box-shadow: 14px 0 38px rgba(15, 111, 168, 0.16);
}

.logo {
	font-size: 24px;
	font-weight: 700;
	margin-bottom: 28px;
}

.menu {
	display: flex;
	flex-direction: column;
	gap: 10px;
}

.menu a {
	padding: 12px 14px;
	border-radius: 14px;
	color: #e8f7ff;
	text-decoration: none;
	transition: all 0.18s ease;
}

.menu a.router-link-exact-active {
	background: rgba(255, 255, 255, 0.18);
	color: #fff;
}

.menu a:hover {
	background: rgba(255,255,255,0.1);
}

.main {
	flex: 1;
	display: flex;
	flex-direction: column;
}

.topbar {
	height: 72px;
	padding: 0 24px;
	display: flex;
	align-items: center;
	justify-content: space-between;
	background: linear-gradient(90deg, #0f6fa8 0%, #1f8fcb 70%, #56b8e8 100%);
	color: #fff;
	box-shadow: 0 18px 40px rgba(15, 111, 168, 0.14);
}

.title {
	font-size: 22px;
	font-weight: 700;
}

.actions {
	display: flex;
	align-items: center;
	gap: 12px;
}

.locale-switch {
	display: flex;
	padding: 4px;
	border-radius: 999px;
	background: rgba(255,255,255,0.14);
	border: 1px solid rgba(255,255,255,0.16);
}

.locale-switch button {
	border: 0;
	background: transparent;
	color: rgba(234, 248, 255, 0.84);
	padding: 8px 12px;
	border-radius: 999px;
	font-weight: 700;
	cursor: pointer;
}

.locale-switch button.active {
	background: #fff;
	color: #0f6fa8;
}

.content {
	padding: 24px;
	position: relative;
}
</style>
