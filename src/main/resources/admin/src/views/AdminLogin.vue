<template>
	<div class="login-page">
		<div class="card">
			<div class="card-top">
				<div>
					<h1>{{ $t({ 'zh-CN': '管理员登录', 'en-US': 'Admin Login' }) }}</h1>
					<p class="sub">{{ $t({ 'zh-CN': '登录后可切换全局中英界面', 'en-US': 'Login to manage the global Chinese/English interface.' }) }}</p>
				</div>
				<div class="locale-switch">
					<button :class="{ active: $locale.value === 'zh-CN' }" @click="$setLocale('zh-CN')">中</button>
					<button :class="{ active: $locale.value === 'en-US' }" @click="$setLocale('en-US')">EN</button>
				</div>
			</div>
			<el-form @submit.native.prevent="login">
				<el-form-item :label="$t({ 'zh-CN': '用户名', 'en-US': 'Username' })">
					<el-input v-model="form.username" :placeholder="$t({ 'zh-CN': '请输入用户名', 'en-US': 'Enter username' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '密码', 'en-US': 'Password' })">
					<el-input v-model="form.password" type="password" :placeholder="$t({ 'zh-CN': '请输入密码', 'en-US': 'Enter password' })" />
				</el-form-item>
				<el-button type="primary" :loading="loading" @click="login">{{ $t({ 'zh-CN': '登录', 'en-US': 'Login' }) }}</el-button>
			</el-form>
			<p class="hint">{{ $t({ 'zh-CN': '演示管理员：admin / admin', 'en-US': 'Demo admin: admin / admin' }) }}</p>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			loading: false,
			form: {
				username: 'admin',
				password: 'admin',
			},
		}
	},
	methods: {
		async login() {
			this.loading = true
			try {
				const res = await this.$api.get('/users/login', { params: this.form })
				if (res.data.code !== 0) {
					this.$message.error(res.data.msg || this.$t({ 'zh-CN': '登录失败', 'en-US': 'Login failed' }))
					return
				}
				localStorage.setItem('adminToken', res.data.token)
				localStorage.setItem('adminName', this.form.username)
				this.$router.push('/home')
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '登录失败', 'en-US': 'Login failed' }))
			} finally {
				this.loading = false
			}
		},
	},
}
</script>

<style scoped>
.login-page {
	min-height: 100vh;
	display: flex;
	align-items: center;
	justify-content: center;
	background:
		radial-gradient(circle at top left, rgba(86, 184, 232, 0.35), transparent 26%),
		linear-gradient(135deg, #dff4ff 0%, #ffffff 100%);
}
.card {
	width: 460px;
	padding: 32px;
	background: #fff;
	border-radius: 24px;
	border: 1px solid rgba(15, 111, 168, 0.08);
	box-shadow: 0 24px 46px rgba(15, 111, 168, 0.14);
}
.card-top {
	display: flex;
	justify-content: space-between;
	align-items: flex-start;
	gap: 20px;
	margin-bottom: 8px;
}
.card h1 {
	margin: 0 0 8px;
	color: #0f6fa8;
	font-size: 30px;
}
.sub {
	margin: 0 0 8px;
	font-size: 13px;
	color: #7b8a97;
	line-height: 1.7;
}
.locale-switch {
	display: flex;
	padding: 4px;
	border-radius: 999px;
	background: #eef8fd;
	border: 1px solid rgba(15, 111, 168, 0.08);
}
.locale-switch button {
	border: 0;
	background: transparent;
	color: #5e7484;
	padding: 8px 12px;
	border-radius: 999px;
	font-weight: 700;
	cursor: pointer;
}
.locale-switch button.active {
	background: #0f6fa8;
	color: #fff;
}
.hint {
	margin-top: 16px;
	font-size: 12px;
	color: #7b8a97;
}
</style>
