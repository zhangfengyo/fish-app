<template>
	<div class="page">
		<div class="toolbar">
			<div>
				<h1>{{ $t({ 'zh-CN': '论坛管理', 'en-US': 'Forum Management' }) }}</h1>
				<p>{{ $t({ 'zh-CN': '管理论坛主帖（客户端无法发帖，仅在此处发布）', 'en-US': 'Manage forum threads (clients cannot post, publish here only).' }) }}</p>
			</div>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增帖子', 'en-US': 'Add Post' }) }}</el-button>
		</div>

		<div class="filters">
			<el-input v-model="filters.title" clearable :placeholder="$t({ 'zh-CN': '按标题搜索', 'en-US': 'Search by title' })" @keyup.enter.native="loadList" />
			<el-button @click="loadList">{{ $t({ 'zh-CN': '查询', 'en-US': 'Search' }) }}</el-button>
			<el-button @click="resetFilters">{{ $t({ 'zh-CN': '重置', 'en-US': 'Reset' }) }}</el-button>
		</div>

		<el-table :data="list" border>
			<el-table-column prop="title" :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })" min-width="180" />
			<el-table-column prop="username" :label="$t({ 'zh-CN': '发帖人', 'en-US': 'Author' })" width="140" />
			<el-table-column prop="isdone" :label="$t({ 'zh-CN': '状态', 'en-US': 'Status' })" width="100" />
			<el-table-column :label="$t({ 'zh-CN': '是否置顶', 'en-US': 'Pinned' })" width="110">
				<template slot-scope="{ row }">
					<el-switch v-model="row.istop" :active-value="1" :inactive-value="0" @change="(val) => forumistopChange(val, row)" />
				</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '是否匿名', 'en-US': 'Anonymous' })" width="100">
				<template slot-scope="{ row }">
					{{ row.isanon === 1 ? $t({ 'zh-CN': '是', 'en-US': 'Yes' }) : $t({ 'zh-CN': '否', 'en-US': 'No' }) }}
				</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '封面', 'en-US': 'Cover' })" width="120">
				<template slot-scope="{ row }">
					<img v-if="firstImage(row.cover)" :src="$assetUrl(firstImage(row.cover))" class="thumb" />
				</template>
			</el-table-column>
			<el-table-column prop="addtime" :label="$t({ 'zh-CN': '发布时间', 'en-US': 'Published At' })" width="170" />
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑帖子', 'en-US': 'Edit Post' }) : $t({ 'zh-CN': '新增帖子', 'en-US': 'Add Post' })" :visible.sync="dialogVisible" width="820px">
			<el-form label-width="120px">
				<el-form-item :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })">
					<el-input v-model="form.title" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '内容', 'en-US': 'Content' })">
					<el-input v-model="form.content" type="textarea" :rows="8" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '发帖人', 'en-US': 'Author' })">
					<el-input v-model="form.username" :placeholder="$t({ 'zh-CN': '留空默认显示为「管理员」', 'en-US': 'Leave blank to show as Administrator' })" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '状态', 'en-US': 'Status' })">
					<el-select v-model="form.isdone">
						<el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '是否置顶', 'en-US': 'Pinned' })">
					<el-switch v-model="form.istop" :active-value="1" :inactive-value="0" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '是否匿名', 'en-US': 'Anonymous' })">
					<el-switch v-model="form.isanon" :active-value="1" :inactive-value="0" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '封面图', 'en-US': 'Cover Images' })">
					<input type="file" accept="image/*" multiple @change="handleImagesChange" />
					<div class="preview-grid" v-if="form.cover">
						<img v-for="(item, index) in form.cover.split(',')" :key="index" :src="$assetUrl(item)" />
					</div>
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
			statusOptions: ['开放', '关闭'],
			dialogVisible: false,
			saving: false,
			filters: {
				title: '',
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
				title: '',
				content: '',
				parentid: 0,
				username: '',
				isdone: '开放',
				istop: 0,
				isanon: 0,
				delflag: 0,
				cover: '',
			}
		},
		firstImage(value) {
			if (!value) return ''
			return value.split(',')[0]
		},
		formatDateTime(date) {
			const pad = (n) => (n < 10 ? '0' + n : '' + n)
			return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())} ${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
		},
		async loadList() {
			const params = {
				page: 1,
				limit: 100,
				parentid: 0,
				sort: 'istop,toptime,addtime',
				order: 'desc,desc,desc',
			}
			if (this.filters.title) {
				params.title = this.filters.title
			}
			const res = await this.$api.get('/forum/page', { params })
			this.list = (res.data.data && res.data.data.list) || []
		},
		resetFilters() {
			this.filters = { title: '' }
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
		async handleImagesChange(event) {
			const files = Array.from(event.target.files || [])
			if (!files.length) return
			const uploaded = []
			for (const file of files) {
				uploaded.push(await this.uploadAny(file))
			}
			this.form.cover = uploaded.join(',')
		},
		async save() {
			if (!this.form.title) {
				this.$message.warning(this.$t({ 'zh-CN': '请填写标题', 'en-US': 'Please enter a title' }))
				return
			}
			if (!this.form.content) {
				this.$message.warning(this.$t({ 'zh-CN': '请填写内容', 'en-US': 'Please enter content' }))
				return
			}
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				if (!payload.username) {
					payload.username = '管理员'
				}
				const url = payload.id ? '/forum/update' : '/forum/save'
				const res = await this.$api.post(url, payload)
				if (res.data.code === 0) {
					this.$message.success(this.$t({ 'zh-CN': '保存成功，客户端论坛页会读取最新内容。', 'en-US': 'Saved. Client forum pages will use the latest content.' }))
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
					'zh-CN': `确认删除“${row.title}”吗？`,
					'en-US': `Delete "${row.title}"?`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/forum/delete', [row.id])
			if (res.data.code === 0) {
				this.$message.success(this.$t({ 'zh-CN': '删除成功', 'en-US': 'Deleted' }))
				await this.loadList()
			}
		},
		async forumistopChange(val, row) {
			if (val === 1) {
				row.toptime = this.formatDateTime(new Date())
			} else {
				row.toptime = ''
			}
			try {
				const res = await this.$api.post('/forum/update', row)
				if (res.data.code !== 0) {
					row.istop = val === 1 ? 0 : 1
					this.$message.error(res.data.msg || this.$t({ 'zh-CN': '置顶状态更新失败', 'en-US': 'Failed to update pinned status' }))
				}
			} catch (e) {
				row.istop = val === 1 ? 0 : 1
				this.$message.error(this.$t({ 'zh-CN': '置顶状态更新失败', 'en-US': 'Failed to update pinned status' }))
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
.filters .el-input,
.filters .el-select {
	width: 220px;
}
.thumb,
.preview-grid img {
	width: 84px;
	height: 64px;
	object-fit: cover;
	border-radius: 10px;
}
.preview-grid {
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	margin-top: 12px;
}
</style>
