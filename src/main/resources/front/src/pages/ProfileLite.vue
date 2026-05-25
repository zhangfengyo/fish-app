<template>
	<div class="page">
		<div class="toolbar"><h1>{{ $t({ 'zh-CN': '个人中心', 'en-US': 'Profile' }) }}</h1></div>
		<div class="card" v-if="user">
			<div class="profile">
				<img v-if="user.touxiang" :src="$assetUrl(user.touxiang)" class="avatar" />
				<div v-else class="avatar avatar-empty"></div>
				<div class="profile-info">
					<h2>{{ user.yonghuxingming }}</h2>
					<p>{{ user.yonghuzhanghao }} · {{ user.email }}</p>
					<p>{{ $t({ 'zh-CN': '手机', 'en-US': 'Phone' }) }}: {{ user.shoujihao }}</p>
				</div>
				<el-button class="edit-btn" type="primary" size="small" @click="openEdit">
					{{ $t({ 'zh-CN': '编辑资料', 'en-US': 'Edit profile' }) }}
				</el-button>
			</div>
			<div class="links">
				<router-link to="/favorites">{{ $t({ 'zh-CN': '我的收藏', 'en-US': 'My Favorites' }) }}</router-link>
				<router-link to="/reminders">{{ $t({ 'zh-CN': '喂鱼提醒', 'en-US': 'Reminders' }) }}</router-link>
				<router-link to="/addresses">{{ $t({ 'zh-CN': '地址管理', 'en-US': 'Addresses' }) }}</router-link>
				<router-link to="/orders">{{ $t({ 'zh-CN': '我的订单', 'en-US': 'Orders' }) }}</router-link>
			</div>
		</div>

		<el-dialog
			:title="$t({ 'zh-CN': '编辑个人资料 / Edit profile', 'en-US': '编辑个人资料 / Edit profile' })"
			:visible.sync="editDialog"
			width="520px"
			:close-on-click-modal="false"
		>
			<el-form>
				<el-form-item :label="$t({ 'zh-CN': '用户姓名', 'en-US': 'Name' })">
					<el-input v-model="editForm.yonghuxingming" :placeholder="$t({ 'zh-CN': '请输入用户姓名', 'en-US': 'Enter name' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '性别', 'en-US': 'Gender' })">
					<el-select v-model="editForm.xingbie" :placeholder="$t({ 'zh-CN': '请选择性别', 'en-US': 'Select gender' })" style="width: 100%">
						<el-option :label="$t({ 'zh-CN': '男', 'en-US': 'Male' })" value="男" />
						<el-option :label="$t({ 'zh-CN': '女', 'en-US': 'Female' })" value="女" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '手机号', 'en-US': 'Phone' })">
					<el-input v-model="editForm.shoujihao" :placeholder="$t({ 'zh-CN': '请输入手机号', 'en-US': 'Enter phone' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '头像', 'en-US': 'Avatar' })">
					<input type="file" accept="image/*" @change="handleAvatarChange" />
					<div class="avatar-preview-wrap">
						<img
							v-if="editForm.touxiang"
							:src="$assetUrl(editForm.touxiang)"
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
				<el-button @click="editDialog = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
				<el-button type="primary" :loading="editSaving" @click="saveProfile">{{ $t({ 'zh-CN': '保存', 'en-US': 'Save' }) }}</el-button>
			</div>
		</el-dialog>
	</div>
</template>
<script>
export default {
	data() {
		return {
			user: null,
			editDialog: false,
			editForm: {},
			editSaving: false,
		}
	},
	async created() {
		await this.loadSession()
	},
	methods: {
		async loadSession() {
			const res = await this.$api.get('/yonghu/session')
			if (res.data.code === 0) {
				this.user = res.data.data
			}
		},
		openEdit() {
			this.editForm = Object.assign({}, this.user)
			this.editDialog = true
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
					this.$set(this.editForm, 'touxiang', 'upload/' + res.data.file)
				} else {
					this.$message.error(this.$t({ 'zh-CN': '头像上传失败', 'en-US': 'Avatar upload failed' }))
				}
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '头像上传失败', 'en-US': 'Avatar upload failed' }))
			}
		},
		async saveProfile() {
			this.editSaving = true
			try {
				const res = await this.$api.post('/yonghu/update', this.editForm)
				if (res.data.code !== 0) {
					this.$message.error(res.data.msg || this.$t({ 'zh-CN': '保存失败', 'en-US': 'Save failed' }))
					return
				}
				this.$message.success(this.$t({ 'zh-CN': '保存成功', 'en-US': 'Saved' }))
				this.editDialog = false
				await this.loadSession()
				if (this.user) {
					localStorage.setItem('frontUsername', this.user.yonghuxingming || this.user.yonghuzhanghao)
				}
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '保存失败', 'en-US': 'Save failed' }))
			} finally {
				this.editSaving = false
			}
		},
	},
}
</script>
<style scoped>
.toolbar { padding: 20px 22px; border-radius: 22px; background: #fff; box-shadow: 0 18px 35px rgba(15,111,168,.08); margin-bottom: 20px }
.toolbar h1 { margin: 0; color: #163246 }
.card { background: #fff; border-radius: 20px; padding: 22px; box-shadow: 0 16px 34px rgba(15,111,168,.08) }
.profile { display: flex; gap: 18px; align-items: center }
.profile-info { flex: 1 }
.avatar { width: 84px; height: 84px; border-radius: 50%; object-fit: cover }
.avatar-empty { background: #fff; border: 1px solid #e5e7eb }
.edit-btn { margin-left: auto }
.links { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 14px; margin-top: 24px }
.links a { text-decoration: none; padding: 14px 16px; border-radius: 16px; background: #f5fbff; color: #0f6fa8; font-weight: 700; border: 1px solid rgba(15,111,168,.08) }
.avatar-preview-wrap { margin-top: 8px }
.avatar-preview { width: 84px; height: 84px; border-radius: 50%; object-fit: cover; display: block }
</style>
