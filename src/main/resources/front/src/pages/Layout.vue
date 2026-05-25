<template>
	<div class="layout">
		<div class="backdrop">
			<div class="orb orb-a"></div>
			<div class="orb orb-b"></div>
			<div class="mesh"></div>
		</div>
		<header class="topbar shell">
			<div class="brand-block" @click="$router.push('/home')">
				<div class="brand-mark">OF</div>
				<div class="brand-copy">
					<div class="brand">{{ $t({ 'zh-CN': '观赏鱼系统', 'en-US': 'Ornamental Fish System' }) }}</div>
					<div class="brand-sub">{{ $t({ 'zh-CN': '资讯推荐、喂鱼提醒与教学视频', 'en-US': 'News recommendation, reminders, and teaching videos' }) }}</div>
				</div>
			</div>
			<nav class="nav">
				<router-link to="/home">{{ $t({ 'zh-CN': '首页', 'en-US': 'Home' }) }}</router-link>
				<router-link to="/news">{{ $t({ 'zh-CN': '资讯', 'en-US': 'News' }) }}</router-link>
				<router-link to="/teachings">{{ $t({ 'zh-CN': '教学', 'en-US': 'Teachings' }) }}</router-link>
			</nav>
			<div class="actions">
				<div class="locale-switch">
					<button :class="{ active: $locale.value === 'zh-CN' }" @click="$setLocale('zh-CN')">中</button>
					<button :class="{ active: $locale.value === 'en-US' }" @click="$setLocale('en-US')">EN</button>
				</div>
				<template v-if="isLoggedIn">
					<div class="profile-pill">
						<span class="profile-label">{{ $t({ 'zh-CN': '已登录', 'en-US': 'Signed in' }) }}</span>
						<span class="welcome">{{ username }}</span>
					</div>
					<el-button size="mini" class="solid-btn" @click="logout">{{ $t({ 'zh-CN': '退出登录', 'en-US': 'Logout' }) }}</el-button>
				</template>
				<template v-else>
					<el-button size="mini" class="solid-btn" @click="$router.push('/login')">{{ $t({ 'zh-CN': '登录', 'en-US': 'Login' }) }}</el-button>
				</template>
			</div>
		</header>
		<div class="quickbar">
			<router-link to="/aquariums">{{ $t({ 'zh-CN': '水族馆', 'en-US': 'Aquariums' }) }}</router-link>
			<router-link to="/encyclopedia">{{ $t({ 'zh-CN': '观赏鱼百科', 'en-US': 'Encyclopedia' }) }}</router-link>
			<router-link to="/shop">{{ $t({ 'zh-CN': '商城', 'en-US': 'Shop' }) }}</router-link>
			<router-link to="/cart">{{ $t({ 'zh-CN': '购物车', 'en-US': 'Cart' }) }}</router-link>
			<router-link to="/orders">{{ $t({ 'zh-CN': '订单', 'en-US': 'Orders' }) }}</router-link>
			<router-link to="/addresses">{{ $t({ 'zh-CN': '地址', 'en-US': 'Addresses' }) }}</router-link>
			<router-link to="/forum">{{ $t({ 'zh-CN': '论坛', 'en-US': 'Forum' }) }}</router-link>
			<router-link to="/favorites">{{ $t({ 'zh-CN': '收藏', 'en-US': 'Favorites' }) }}</router-link>
			<router-link to="/reminders">{{ $t({ 'zh-CN': '提醒', 'en-US': 'Reminders' }) }}</router-link>
			<router-link to="/profile">{{ $t({ 'zh-CN': '个人中心', 'en-US': 'Profile' }) }}</router-link>
		</div>
		<main class="content">
			<router-view />
		</main>
		<assistant-widget />
	</div>
</template>

<script>
import AssistantWidget from '../components/AssistantWidget.vue'

export default {
	components: {
		AssistantWidget,
	},
	computed: {
		isLoggedIn() {
			return !!localStorage.getItem('frontToken')
		},
		username() {
			return localStorage.getItem('frontUsername') || 'User'
		},
	},
	methods: {
		async logout() {
			try {
				await this.$api.post('/yonghu/logout')
			} catch (e) {
			}
			localStorage.removeItem('frontToken')
			localStorage.removeItem('frontUserid')
			localStorage.removeItem('frontUsername')
			this.$router.push('/login')
		},
	},
}
</script>

<style scoped>
.layout {
	min-height: 100vh;
	position: relative;
	background:
		radial-gradient(circle at top left, rgba(137, 215, 255, 0.55), transparent 28%),
		linear-gradient(180deg, #eaf8ff 0%, #f8fcff 40%, #ffffff 100%);
	overflow: hidden;
}
.backdrop {
	position: fixed;
	inset: 0;
	pointer-events: none;
}
.orb {
	position: absolute;
	border-radius: 999px;
	filter: blur(60px);
	opacity: 0.35;
}
.orb-a {
	width: 280px;
	height: 280px;
	top: 92px;
	left: -40px;
	background: #52b7e8;
}
.orb-b {
	width: 360px;
	height: 360px;
	right: -80px;
	top: 180px;
	background: #90e0ff;
}
.mesh {
	position: absolute;
	inset: 0;
	background-image:
		linear-gradient(rgba(15, 111, 168, 0.035) 1px, transparent 1px),
		linear-gradient(90deg, rgba(15, 111, 168, 0.035) 1px, transparent 1px);
	background-size: 28px 28px;
	mask-image: linear-gradient(180deg, rgba(0,0,0,0.8), transparent);
}
.shell {
	max-width: 1320px;
	margin: 22px auto 0;
	border: 1px solid rgba(255, 255, 255, 0.45);
	border-radius: 28px;
	backdrop-filter: blur(18px);
	box-shadow: 0 26px 60px rgba(15, 111, 168, 0.18);
}
.quickbar {
	max-width: 1320px;
	margin: 16px auto 0;
	display: flex;
	gap: 10px;
	flex-wrap: wrap;
	padding: 0 20px;
}
.quickbar a {
	padding: 10px 14px;
	border-radius: 999px;
	background: rgba(255,255,255,0.8);
	border: 1px solid rgba(15,111,168,0.1);
	box-shadow: 0 10px 22px rgba(15,111,168,0.08);
	color: #18415b;
	text-decoration: none;
	font-size: 13px;
	font-weight: 700;
}
.quickbar a.router-link-exact-active {
	background: linear-gradient(90deg, #0f6fa8 0%, #1f8fcb 100%);
	color: #fff;
}
.topbar {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 18px 28px;
	background: linear-gradient(100deg, rgba(10, 79, 122, 0.96) 0%, rgba(21, 122, 181, 0.94) 54%, rgba(86, 184, 232, 0.9) 100%);
	color: #fff;
}
.brand-block {
	display: flex;
	align-items: center;
	gap: 16px;
	cursor: pointer;
}
.brand-mark {
	width: 50px;
	height: 50px;
	border-radius: 16px;
	display: grid;
	place-items: center;
	background: linear-gradient(145deg, rgba(255,255,255,0.22), rgba(255,255,255,0.08));
	border: 1px solid rgba(255,255,255,0.24);
	font-size: 18px;
	font-weight: 800;
	letter-spacing: 1px;
}
.brand-copy {
	display: flex;
	flex-direction: column;
	gap: 4px;
}
.brand {
	font-size: 24px;
	font-weight: 800;
	letter-spacing: 0.2px;
}
.brand-sub {
	font-size: 12px;
	letter-spacing: 0.3px;
	color: rgba(232, 247, 255, 0.82);
}
.nav {
	display: flex;
	gap: 10px;
	padding: 6px;
	border-radius: 999px;
	background: rgba(255,255,255,0.14);
	border: 1px solid rgba(255,255,255,0.16);
}
.nav a {
	color: #eaf8ff;
	text-decoration: none;
	padding: 10px 16px;
	border-radius: 999px;
	transition: all 0.2s ease;
}
.nav a.router-link-exact-active {
	font-weight: 700;
	color: #0f6fa8;
	background: #fff;
	box-shadow: 0 10px 24px rgba(7, 47, 75, 0.15);
}
.nav a:hover {
	background: rgba(255,255,255,0.1);
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
	color: rgba(234, 248, 255, 0.8);
	padding: 8px 12px;
	border-radius: 999px;
	font-weight: 700;
	cursor: pointer;
}
.locale-switch button.active {
	background: #fff;
	color: #0f6fa8;
}
.profile-pill {
	display: flex;
	flex-direction: column;
	padding: 8px 14px;
	border-radius: 16px;
	background: rgba(255,255,255,0.12);
	border: 1px solid rgba(255,255,255,0.16);
}
.profile-label {
	font-size: 10px;
	text-transform: uppercase;
	letter-spacing: 0.9px;
	color: rgba(234, 248, 255, 0.7);
}
.welcome {
	font-size: 14px;
	font-weight: 700;
}
.solid-btn {
	border: 0;
	border-radius: 999px;
	padding: 10px 16px;
	background: #ffffff !important;
	color: #0f6fa8 !important;
	font-weight: 700;
}
.content {
	position: relative;
	max-width: 1320px;
	margin: 0 auto;
	padding: 28px 20px 48px;
}
@media (max-width: 1040px) {
	.topbar {
		flex-direction: column;
		align-items: stretch;
		gap: 18px;
	}
	.nav {
		justify-content: center;
		flex-wrap: wrap;
	}
	.quickbar {
		justify-content: center;
	}
	.actions {
		justify-content: space-between;
	}
}
@media (max-width: 720px) {
	.brand {
		font-size: 20px;
	}
	.brand-sub {
		display: none;
	}
	.actions {
		flex-direction: column;
		align-items: stretch;
	}
	.locale-switch {
		justify-content: center;
	}
}
</style>
