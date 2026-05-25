<template>
	<div>
		<div class="login-container" :style="{ backgroundImage: indexBgUrl ? `url(${$assetUrl(indexBgUrl)})` : '' }">
			<el-form ref="loginForm" :model="loginForm" :rules="rules" class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="login-title">Ornamental Fish System</div>

					<div v-if="loginType === 1" class="list-item">
						<div class="lable">Account:</div>
						<input v-model="loginForm.username" placeholder="Enter account">
					</div>

					<div v-if="loginType === 1" class="list-item">
						<div class="lable">Password:</div>
						<div class="password-box">
							<input v-model="loginForm.password" :type="showPassword ? 'text' : 'password'" placeholder="Enter password">
							<span class="icon iconfont" :class="showPassword ? 'icon-liulan13' : 'icon-liulan17'" @click="showPassword = !showPassword"></span>
						</div>
					</div>

					<div v-if="loginType === 3" class="list-item">
						<div class="lable">Email:</div>
						<input v-model="email" placeholder="Enter email">
					</div>

					<div v-if="loginType === 3" class="list-code">
						<div class="lable">Code:</div>
						<input v-model="emailcode" placeholder="Enter code" type="text">
						<div v-if="isEndFlag" class="nums" @click="emailClick">Send code</div>
						<div v-else class="nums">{{ emailText }}</div>
					</div>

					<div v-if="roles.length > 1 && loginType === 1" class="list-item">
						<div class="lable">Role:</div>
						<div class="list-type">
							<el-radio
								v-for="(item, index) in roles"
								:key="index"
								v-model="loginForm.tableName"
								:label="item.tableName"
								@change.native="getCurrentRow(item)"
							>{{ item.roleName }}</el-radio>
						</div>
					</div>

					<div v-if="emailroles.length > 1 && loginType === 3" class="list-item">
						<div class="lable">Role:</div>
						<div class="list-type">
							<el-radio
								v-for="(item, index) in emailroles"
								:key="index"
								v-model="loginForm.tableName"
								:label="item.tableName"
								@change.native="getCurrentRow(item)"
							>{{ item.roleName }}</el-radio>
						</div>
					</div>

					<div class="list-btn">
						<el-button class="login_btn" @click="submitForm('loginForm')">Login</el-button>

						<div class="list-btn2">
							<router-link
								v-for="(item, index) in roles2"
								:key="index"
								class="register_btn"
								:to="{ path: '/register', query: { role: item.tableName, pageFlag: 'register' } }"
							>Register {{ item.roleName.replace('register', '') }}</router-link>
							<a v-if="loginType === 1" class="resetpwd_btn" @click="switchToEmailLogin">Email login</a>
							<a v-if="loginType === 3" class="resetpwd_btn" @click="passwordLoginChange">Password login</a>
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
import 'animate.css'
import Vue from 'vue'
import menu from '@/config/menu'

export default {
	data() {
		return {
			baseUrl: this.$config.baseUrl,
			loginType: 1,
			roleMenus: [],
			loginForm: {
				username: '',
				password: '',
				tableName: '',
			},
			email: '',
			emailcode: '',
			emailroles: [
				{
					tableName: 'yonghu',
					roleName: 'User',
				},
			],
			count: 60,
			inter: null,
			isEndFlag: true,
			role: '',
			roles: [],
			roles2: [],
			rules: {
				username: [
					{ required: true, message: 'Account is required', trigger: 'blur' },
				],
				password: [
					{ required: true, message: 'Password is required', trigger: 'blur' },
				],
			},
			showPassword: false,
			indexBgUrl: '',
		}
	},
	created() {
		this.$http.get('config/info?name=fLoginBackgroundImg').then(rs => {
			this.indexBgUrl = rs.data.data ? rs.data.data.value : ''
		})
		this.roleMenus = menu.list()
		for (let item in this.roleMenus) {
			if (this.roleMenus[item].hasFrontLogin === 'yes' || this.roleMenus[item].hasFrontLogin === '閺?') {
				this.roles.push(this.roleMenus[item])
			}
			if (this.roleMenus[item].hasFrontRegister === 'yes' || this.roleMenus[item].hasFrontRegister === '閺?') {
				this.roles2.push(this.roleMenus[item])
			}
		}
		if (this.roles.length === 0) {
			this.roles = [{ tableName: 'yonghu', roleName: 'User' }]
		}
		if (this.roles2.length === 0) {
			this.roles2 = [{ tableName: 'yonghu', roleName: 'User', hasFrontRegister: 'yes' }]
		}
	},
	computed: {
		emailText() {
			return `${parseInt(this.count, 10)}s`
		},
	},
	methods: {
		switchToEmailLogin() {
			this.loginType = 3
			this.loginForm.password = ''
			this.loginForm.tableName = ''
			this.role = ''
		},
		getCurrentRow(row) {
			this.role = row.roleName
		},
		resolveRole(isEmailLogin = false) {
			const source = isEmailLogin ? this.emailroles : this.roles
			if (source.length !== 1) {
				if (!this.role) {
					this.$message.error('Please choose a role')
					return false
				}
			} else {
				this.role = source[0].roleName
				this.loginForm.tableName = source[0].tableName
			}
			return true
		},
		emailClick() {
			if (!this.email) {
				this.$message.error('Email is required')
				return
			}
			if (!this.$validate.isEmail2(this.email)) {
				this.$message.error('Invalid email format')
				return
			}
			if (!this.resolveRole(true)) {
				return
			}
			this.$http({
				url: `${this.loginForm.tableName}/sendemail/login?email=${this.email}`,
				method: 'get',
				params: {},
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.isEndFlag = false
					this.inter = window.setInterval(() => {
						this.count -= 1
						if (this.count <= 0) {
							window.clearInterval(this.inter)
							this.isEndFlag = true
							this.count = 60
						}
					}, 1000)
					this.$message.success('Code sent')
				} else if (data) {
					this.$message.error(data.msg)
				}
			})
		},
		submitForm(formName) {
			if (this.loginType === 1) {
				if (!this.resolveRole(false)) {
					return
				}
				if (!this.loginForm.username) {
					this.$message.error('Account is required')
					return
				}
				if (!this.loginForm.password) {
					this.$message.error('Password is required')
					return
				}
			}
			if (this.loginType === 3) {
				if (!this.email) {
					this.$message.error('Email is required')
					return
				}
				if (!this.emailcode) {
					this.$message.error('Code is required')
					return
				}
				if (!this.resolveRole(true)) {
					return
				}
			}
			this.loginPost(formName)
		},
		passwordLoginChange() {
			this.loginType = 1
		},
		async handleLoginSuccess(token, username) {
			localStorage.setItem('frontToken', token)
			localStorage.setItem('UserTableName', this.loginForm.tableName)
			localStorage.setItem('username', username)
			localStorage.setItem('frontSessionTable', this.loginForm.tableName)
			localStorage.setItem('frontRole', this.role)
			localStorage.setItem('keyPath', 0)
			Vue.http.headers.common.Token = token
			await this.checkFeedReminderAndRedirect()
			this.$message({
				message: 'Login successful',
				type: 'success',
				duration: 1500,
			})
		},
		async checkFeedReminderAndRedirect() {
			if (this.loginForm.tableName !== 'yonghu') {
				this.$router.push('/')
				return
			}
			try {
				const res = await this.$http.get('weiyutixing/remind/tixingriqi/2', {
					params: {
						remindend: 0,
					},
				})
				if (res.data && res.data.code === 0 && res.data.count > 0) {
					const names = (res.data.data || []).join(', ')
					await this.$msgbox({
						title: 'Feed Reminder',
						message: this.$createElement('div', { class: 'login-remind-box' }, [
							this.$createElement('div', { class: 'login-remind-title' }, 'Feed reminders expire today'),
							this.$createElement('div', { class: 'login-remind-count' }, `Pending reminders: ${res.data.count}`),
							this.$createElement('div', { class: 'login-remind-names' }, names ? `Items: ${names}` : 'Please open your reminder list soon.'),
						]),
						showCancelButton: false,
						confirmButtonText: 'OK',
						closeOnClickModal: false,
					})
				}
			} catch (e) {
			}
			this.$router.push('/')
		},
		loginPost(formName) {
			if (this.loginType === 3) {
				this.emailLogin()
				return false
			}
			this.$refs[formName].validate(valid => {
				if (!valid) {
					return false
				}
				this.$http.get(`${this.loginForm.tableName}/login`, { params: this.loginForm }).then(res => {
					if (res.data.code === 0) {
						this.handleLoginSuccess(res.data.token, this.loginForm.username)
					} else {
						this.$message.error(res.data.msg)
					}
				})
			})
		},
		emailLogin() {
			this.$http.post(`${this.loginForm.tableName}/email/login?email=${this.email}&emailcode=${this.emailcode}`, {}).then(res => {
				if (res.data.code === 0) {
					this.handleLoginSuccess(res.data.token, res.data.username)
				} else {
					this.$message.error(res.data.msg)
				}
			})
		},
	},
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.login-container {
	background: url(http://codegen.caihongy.cn/20251211/b74b8dc545354f1fb594a616b5871d37.png) no-repeat left center / 50% 100%;
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: flex-end;
	align-items: center;
	position: relative;
}

.login_form {
	border-radius: 60px;
	padding: 30px 100px;
	margin: 0;
	z-index: 10;
	background: none;
	width: 55%;
}

.login_form2 {
	width: 100%;
}

.login-title {
	padding: 0 0 0 160px;
	margin: 0 0 20px 0;
	color: #46AC2E;
	font-weight: 600;
	font-size: 22px;
	line-height: 2;
	text-align: center;
}

.list-item,
.list-code {
	margin: 0 0 20px 0;
	display: flex;
	width: 100%;
	align-items: center;
}

.lable {
	color: #000;
	width: 160px;
	font-size: 16px;
	line-height: 40px;
	text-align: right;
}

.list-item input,
.list-code input {
	border: 1px solid #00000050;
	border-radius: 0;
	padding: 0 30px;
	box-shadow: none;
	color: #000000;
	flex: 1;
	background: none;
	width: 100%;
	font-size: 16px;
	height: 50px;
}

.password-box {
	flex: 1;
	display: flex;
	width: calc(100% - 100px);
	position: relative;
	align-items: center;
}

.password-box input {
	width: 100%;
}

.password-box .iconfont {
	cursor: pointer;
	z-index: 1;
	color: #666;
	top: 0;
	font-size: 16px;
	line-height: 50px;
	position: absolute;
	right: 20px;
}

.nums {
	cursor: pointer;
	padding: 0 15px;
	margin: 0 0 0 10px;
	white-space: nowrap;
	display: inline-block;
	line-height: 50px;
	background: #058747;
	color: #fff;
	text-align: center;
	height: 50px;
}

.list-type {
	flex: 1;
	width: 100%;
	line-height: 50px;
	min-height: 50px;
}

.list-type ::v-deep .el-radio__input .el-radio__inner {
	background: rgba(53, 53, 53, 0);
	border-color: #333;
}

.list-type ::v-deep .el-radio__input.is-checked .el-radio__inner {
	background: #058747;
	border-color: #058747;
}

.list-type ::v-deep .el-radio__label {
	color: #333;
	font-size: 16px;
}

.list-type ::v-deep .el-radio__input.is-checked + .el-radio__label {
	color: #058747;
	font-size: 16px;
}

.list-btn {
	padding: 0 0 0 160px;
	width: 100%;
	text-align: center;
}

.login_btn {
	border: 1px solid #707070;
	cursor: pointer;
	padding: 0 24px;
	margin: 0 auto;
	color: #fff;
	display: inline-block;
	font-size: 20px;
	line-height: 50px;
	border-radius: 25px;
	outline: none;
	background: #058747;
	width: 100%;
	min-width: 150px;
	height: 50px;
}

.login_btn:hover,
.register_btn:hover,
.resetpwd_btn:hover {
	opacity: 0.8;
}

.list-btn2 {
	padding: 0;
	margin: 0;
	display: flex;
	width: 100%;
	justify-content: center;
	align-items: center;
	flex-wrap: wrap;
	text-align: center;
}

.register_btn,
.resetpwd_btn {
	cursor: pointer;
	padding: 0 20px;
	color: #000;
	background: none;
	display: inline-block;
	text-decoration: none;
	font-size: 16px;
	line-height: 60px;
}

.login-remind-box {
	padding: 4px 0;
	text-align: center;
}

.login-remind-title {
	font-size: 18px;
	font-weight: 600;
	color: #0f6fa8;
	margin-bottom: 10px;
}

.login-remind-count {
	font-size: 15px;
	color: #333;
	margin-bottom: 8px;
}

.login-remind-names {
	font-size: 14px;
	color: #666;
	line-height: 1.8;
	word-break: break-all;
}
</style>

