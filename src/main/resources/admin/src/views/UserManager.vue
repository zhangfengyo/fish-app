<template>
	<div class="page">
		<div class="toolbar">
			<div>
				<h1>{{ $t({ 'zh-CN': '用户管理', 'en-US': 'User Management' }) }}</h1>
				<p>{{ $t({ 'zh-CN': '这里维护的是客户端普通用户账号，修改后会直接影响用户端登录和个人资料。', 'en-US': 'This page manages client user accounts and directly affects client login and profile data.' }) }}</p>
			</div>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增用户', 'en-US': 'Add User' }) }}</el-button>
		</div>

		<div class="filters">
			<el-input v-model="filters.name" clearable :placeholder="$t({ 'zh-CN': '搜索姓名', 'en-US': 'Search name' })" @keyup.enter.native="loadList" />
			<el-input v-model="filters.account" clearable :placeholder="$t({ 'zh-CN': '搜索账号', 'en-US': 'Search account' })" @keyup.enter.native="loadList" />
			<el-button @click="loadList">{{ $t({ 'zh-CN': '查询', 'en-US': 'Search' }) }}</el-button>
			<el-button @click="resetFilters">{{ $t({ 'zh-CN': '重置', 'en-US': 'Reset' }) }}</el-button>
		</div>

		<el-table :data="list" border>
			<el-table-column prop="yonghuzhanghao" :label="$t({ 'zh-CN': '账号', 'en-US': 'Account' })" min-width="140" />
			<el-table-column prop="yonghuxingming" :label="$t({ 'zh-CN': '姓名', 'en-US': 'Name' })" min-width="140" />
			<el-table-column prop="xingbie" :label="$t({ 'zh-CN': '性别', 'en-US': 'Gender' })" width="90" />
			<el-table-column prop="age" :label="$t({ 'zh-CN': '年龄', 'en-US': 'Age' })" width="80" />
			<el-table-column prop="shoujihao" :label="$t({ 'zh-CN': '手机号', 'en-US': 'Phone' })" min-width="140" />
			<el-table-column prop="email" :label="$t({ 'zh-CN': '邮箱', 'en-US': 'Email' })" min-width="180" />
			<el-table-column :label="$t({ 'zh-CN': '头像', 'en-US': 'Avatar' })" width="100">
				<template slot-scope="{ row }">
					<img v-if="firstImage(row.touxiang)" :src="$assetUrl(firstImage(row.touxiang))" class="avatar" />
				</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑用户', 'en-US': 'Edit User' }) : $t({ 'zh-CN': '新增用户', 'en-US': 'Add User' })" :visible.sync="dialogVisible" width="760px">
			<el-form label-width="120px">
				<el-form-item :label="$t({ 'zh-CN': '账号', 'en-US': 'Account' })">
					<el-input v-model="form.yonghuzhanghao" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '密码', 'en-US': 'Password' })">
					<el-input v-model="form.mima" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '姓名', 'en-US': 'Name' })">
					<el-input v-model="form.yonghuxingming" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '性别', 'en-US': 'Gender' })">
					<el-select v-model="form.xingbie" clearable>
						<el-option label="男" value="男" />
						<el-option label="女" value="女" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '身份证', 'en-US': 'ID Card' })">
					<el-input v-model="form.idcard" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '年龄', 'en-US': 'Age' })">
					<el-input-number v-model="form.age" :min="0" :max="120" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '手机号', 'en-US': 'Phone' })">
					<el-input v-model="form.shoujihao" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '头像', 'en-US': 'Avatar' })">
					<input type="file" accept="image/*" @change="handleAvatarChange" />
					<div class="preview-grid" v-if="form.touxiang">
						<img :src="$assetUrl(firstImage(form.touxiang))" class="avatar large" />
					</div>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '邮箱', 'en-US': 'Email' })">
					<el-input v-model="form.email" />
				</el-form-item>
			</el-form>
			<span slot="footer">
				<el-button @click="dialogVisible = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
				<el-button type="primary" :loading="saving" @click="save">{{ $t({ 'zh-CN': '保存', 'en-US': 'Save' }) }}</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
export default {
	data() {
		return {
			list: [],
			dialogVisible: false,
			saving: false,
			filters: {
				name: '',
				account: '',
			},
			form: this.createEmptyForm(),
		}
	},
	async created() {
		await this.loadList()
	},
	methods: {
		createEmptyForm() {
			return {
				id: null,
				yonghuzhanghao: '',
				mima: '',
				yonghuxingming: '',
				xingbie: '',
				idcard: '',
				age: 0,
				shoujihao: '',
				touxiang: '',
				email: '',
				money: 0,
			}
		},
		firstImage(value) {
			if (!value) return ''
			return value.split(',')[0]
		},
		async loadList() {
			const params = {
				page: 1,
				limit: 100,
				sort: 'addtime',
				order: 'desc',
			}
			if (this.filters.name) params.yonghuxingming = this.filters.name
			if (this.filters.account) params.yonghuzhanghao = this.filters.account
			const res = await this.$api.get('/yonghu/page', { params })
			this.list = (res.data.data && res.data.data.list) || []
		},
		resetFilters() {
			this.filters = { name: '', account: '' }
			this.loadList()
		},
		openCreate() {
			this.form = this.createEmptyForm()
			this.dialogVisible = true
		},
		openEdit(row) {
			this.form = Object.assign(this.createEmptyForm(), row)
			this.dialogVisible = true
		},
		async uploadAny(file) {
			const fd = new FormData()
			fd.append('file', file)
			const res = await this.$api.post('/file/upload', fd, {
				headers: { 'Content-Type': 'multipart/form-data' },
			})
			return `upload/${res.data.file}`
		},
		async handleAvatarChange(event) {
			const file = event.target.files && event.target.files[0]
			if (!file) return
			this.form.touxiang = await this.uploadAny(file)
		},
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				const url = payload.id ? '/yonghu/update' : '/yonghu/save'
				const res = await this.$api.post(url, payload)
				if (res.data.code === 0) {
					this.$message.success(this.$t({ 'zh-CN': '保存成功，客户端用户资料会读取最新信息。', 'en-US': 'Saved. Client user profiles will use the latest information.' }))
					this.dialogVisible = false
					await this.loadList()
				} else {
					this.$message.error(res.data.msg || this.$t({ 'zh-CN': '保存失败', 'en-US': 'Save failed' }))
				}
			} finally {
				this.saving = false
			}
		},
		async remove(row) {
			await this.$confirm(
				this.$t({
					'zh-CN': `确认删除用户“${row.yonghuxingming || row.yonghuzhanghao}”吗？删除后客户端该账号将无法继续使用。`,
					'en-US': `Delete user "${row.yonghuxingming || row.yonghuzhanghao}"? The account will no longer be usable on the client.`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/yonghu/delete', [row.id])
			if (res.data.code === 0) {
				this.$message.success(this.$t({ 'zh-CN': '删除成功', 'en-US': 'Deleted' }))
				await this.loadList()
			}
		},
	},
}
</script>

<style scoped>
.page {
	display: grid;
	gap: 18px;
}
.toolbar,
.filters {
	display: flex;
	align-items: center;
	gap: 12px;
	flex-wrap: wrap;
	padding: 20px 22px;
	border-radius: 24px;
	background: #fff;
	box-shadow: 0 18px 38px rgba(15, 111, 168, 0.08);
}
.toolbar {
	justify-content: space-between;
}
.toolbar h1 {
	margin: 0 0 6px;
	color: #163246;
}
.toolbar p {
	margin: 0;
	color: #6b7f8d;
	font-size: 13px;
}
.filters .el-input {
	width: 220px;
}
.avatar {
	width: 52px;
	height: 52px;
	border-radius: 50%;
	object-fit: cover;
}
.avatar.large {
	width: 96px;
	height: 96px;
}
.preview-grid {
	margin-top: 12px;
}
</style>
