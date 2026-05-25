<template>
	<div class="login-page">
		<div class="card">
			<div class="card-top">
				<div>
					<h1>{{ $t({ 'zh-CN': '用户登录', 'en-US': 'Login' }) }}</h1>
					<p class="sub">{{ $t({ 'zh-CN': '切换语言后，前台全局文案会同步更新', 'en-US': 'Switch language to update the full front-end shell.' }) }}</p>
				</div>
				<div class="locale-switch">
					<button :class="{ active: $locale.value === 'zh-CN' }" @click="$setLocale('zh-CN')">中</button>
					<button :class="{ active: $locale.value === 'en-US' }" @click="$setLocale('en-US')">EN</button>
				</div>
			</div>
			<el-form @submit.native.prevent="login">
				<el-form-item :label="$t({ 'zh-CN': '账号', 'en-US': 'Account' })">
					<el-input v-model="form.username" :placeholder="$t({ 'zh-CN': '请输入账号', 'en-US': 'Enter account' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '密码', 'en-US': 'Password' })">
					<el-input v-model="form.password" type="password" :placeholder="$t({ 'zh-CN': '请输入密码', 'en-US': 'Enter password' })" />
				</el-form-item>
				<el-button type="primary" :loading="loading" @click="login">{{ $t({ 'zh-CN': '登录', 'en-US': 'Login' }) }}</el-button>
			</el-form>
			<p class="register-tip">
				{{ $t({ 'zh-CN': '没有账号？', 'en-US': 'No account yet?' }) }}
				<a @click="registerDialog = true" style="color:#0f6fa8; cursor:pointer; font-weight:700">{{ $t({ 'zh-CN': '立即注册', 'en-US': 'Sign up now' }) }}</a>
			</p>
			<p class="hint">{{ $t({ 'zh-CN': '演示账号：12 / 123456', 'en-US': 'Demo user: 12 / 123456' }) }}</p>
		</div>

		<el-dialog
			:title="$t({ 'zh-CN': '注册账号 / Create account', 'en-US': '注册账号 / Create account' })"
			:visible.sync="registerDialog"
			width="460px"
			:close-on-click-modal="false"
		>
			<el-form>
				<el-form-item :label="$t({ 'zh-CN': '账号', 'en-US': 'Account' })">
					<el-input v-model="registerForm.yonghuzhanghao" :placeholder="$t({ 'zh-CN': '请输入账号', 'en-US': 'Enter account' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '密码', 'en-US': 'Password' })">
					<el-input v-model="registerForm.mima" type="password" :placeholder="$t({ 'zh-CN': '请输入密码', 'en-US': 'Enter password' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '确认密码', 'en-US': 'Confirm password' })">
					<el-input v-model="registerForm.mima2" type="password" :placeholder="$t({ 'zh-CN': '请再次输入密码', 'en-US': 'Re-enter password' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '头像', 'en-US': 'Avatar' })">
					<input type="file" accept="image/*" @change="handleAvatarChange" />
					<div class="avatar-preview-wrap">
						<img
							v-if="registerForm.touxiang"
							:src="$assetUrl(registerForm.touxiang)"
							class="avatar-preview"
						/>
						<div
							v-else
							class="avatar-preview avatar-empty"
						></div>
					</div>
				</el-form-item>
			</el-form>
			<div slot="footer">
				<el-button @click="registerDialog = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
				<el-button type="primary" :loading="registerLoading" @click="submitRegister">{{ $t({ 'zh-CN': '提交注册', 'en-US': 'Submit' }) }}</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			loading: false,
			form: {
				username: '12',
				password: '123456',
			},
			registerDialog: false,
			registerLoading: false,
			registerForm: {
				yonghuzhanghao: '',
				mima: '',
				mima2: '',
				touxiang: '',
			},
		}
	},
	methods: {
		async loadSession() {
			const res = await this.$api.get('/yonghu/session')
			localStorage.setItem('frontUserid', res.data.data.id)
			localStorage.setItem('frontUsername', res.data.data.yonghuxingming || res.data.data.yonghuzhanghao)
		},
		async checkReminder() {
			const res = await this.$api.get('/weiyutixing/remind/tixingriqi/2', {
				params: {
					remindend: 0,
				},
			})
			if (res.data && res.data.code === 0 && res.data.count > 0) {
				const names = (res.data.data || []).join(', ')
				await this.$alert(
					this.$t({
						'zh-CN': `待处理提醒：${res.data.count}${names ? `\n项目：${names}` : ''}`,
						'en-US': `Pending reminders: ${res.data.count}${names ? `\nItems: ${names}` : ''}`,
					}),
					this.$t({ 'zh-CN': '喂鱼提醒', 'en-US': 'Feed Reminder' }),
					{
						confirmButtonText: this.$t({ 'zh-CN': '知道了', 'en-US': 'OK' }),
					}
				)
			}
		},
		async login() {
			if (!this.form.username || !this.form.password) {
				this.$message.error(this.$t({ 'zh-CN': '账号和密码不能为空', 'en-US': 'Account and password are required' }))
				return
			}
			this.loading = true
			try {
				const res = await this.$api.get('/yonghu/login', {
					params: this.form,
				})
				if (res.data.code !== 0) {
					this.$message.error(res.data.msg || this.$t({ 'zh-CN': '登录失败', 'en-US': 'Login failed' }))
					return
				}
				localStorage.setItem('frontToken', res.data.token)
				await this.loadSession()
				await this.checkReminder()
				this.$router.push('/home')
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '登录失败', 'en-US': 'Login failed' }))
			} finally {
				this.loading = false
			}
		},
		async handleAvatarChange(event) {
			const file = event.target.files && event.target.files[0]
			if (!file) {
				return
			}
			try {
				const formData = new FormData()
				formData.append('file', file)
				const res = await this.$api.post('/file/upload', formData, {
					headers: { 'Content-Type': 'multipart/form-data' },
				})
				if (res.data && res.data.file) {
					this.registerForm.touxiang = 'upload/' + res.data.file
				} else {
					this.$message.error(this.$t({ 'zh-CN': '头像上传失败', 'en-US': 'Avatar upload failed' }))
				}
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '头像上传失败', 'en-US': 'Avatar upload failed' }))
			}
		},
		async submitRegister() {
			if (!this.registerForm.yonghuzhanghao || !this.registerForm.mima || !this.registerForm.mima2) {
				this.$message.error(this.$t({ 'zh-CN': '账号、密码、确认密码均不能为空', 'en-US': 'Account, password and confirm password are required' }))
				return
			}
			if (this.registerForm.mima !== this.registerForm.mima2) {
				this.$message.error(this.$t({ 'zh-CN': '两次密码不一致', 'en-US': 'Passwords do not match' }))
				return
			}
			this.registerLoading = true
			try {
				const body = {
					yonghuzhanghao: this.registerForm.yonghuzhanghao,
					mima: this.registerForm.mima,
					touxiang: this.registerForm.touxiang,
				}
				const res = await this.$api.post('/yonghu/register', body)
				if (res.data.code !== 0) {
					this.$message.error(res.data.msg || this.$t({ 'zh-CN': '注册失败', 'en-US': 'Register failed' }))
					return
				}
				const loginRes = await this.$api.get('/yonghu/login', {
					params: {
						username: this.registerForm.yonghuzhanghao,
						password: this.registerForm.mima,
					},
				})
				if (loginRes.data.code !== 0) {
					this.$message.error(this.$t({ 'zh-CN': '注册成功但自动登录失败，请手动登录', 'en-US': 'Registered, but auto login failed. Please login manually.' }))
					this.registerDialog = false
					return
				}
				localStorage.setItem('frontToken', loginRes.data.token)
				const sessionRes = await this.$api.get('/yonghu/session')
				if (sessionRes.data && sessionRes.data.code === 0 && sessionRes.data.data) {
					localStorage.setItem('frontUserid', sessionRes.data.data.id)
					localStorage.setItem('frontUsername', sessionRes.data.data.yonghuxingming || sessionRes.data.data.yonghuzhanghao)
				}
				this.$message.success(this.$t({ 'zh-CN': '注册成功，已自动登录', 'en-US': 'Registered and logged in.' }))
				this.registerDialog = false
				this.$router.push('/home')
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '注册失败', 'en-US': 'Register failed' }))
			} finally {
				this.registerLoading = false
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
.register-tip {
	margin-top: 14px;
	font-size: 13px;
	color: #5e7484;
}
.hint {
	margin-top: 8px;
	font-size: 12px;
	color: #7b8a97;
}
.avatar-preview-wrap {
	margin-top: 8px;
}
.avatar-preview {
	width: 84px;
	height: 84px;
	border-radius: 50%;
	object-fit: cover;
	display: block;
}
.avatar-empty {
	background: #fff;
	border: 1px solid #e5e7eb;
}
</style>
