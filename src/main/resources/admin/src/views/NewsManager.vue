<template>
	<div class="page">
		<div class="toolbar">
			<h1>{{ $t({ 'zh-CN': '资讯管理', 'en-US': 'News Management' }) }}</h1>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增资讯', 'en-US': 'Add News' }) }}</el-button>
		</div>
		<el-table :data="list" border>
			<el-table-column prop="title" :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })" />
			<el-table-column prop="pinzhongfenlei" :label="$t({ 'zh-CN': '鱼种分类', 'en-US': 'Fish Category' })">
				<template slot-scope="{ row }">
					{{ $fishLabel(row.pinzhongfenlei) }}
				</template>
			</el-table-column>
			<el-table-column prop="name" :label="$t({ 'zh-CN': '发布人', 'en-US': 'Publisher' })" />
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑资讯', 'en-US': 'Edit News' }) : $t({ 'zh-CN': '新增资讯', 'en-US': 'Add News' })" :visible.sync="dialogVisible" width="720px">
			<el-form label-width="120px">
				<el-form-item :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })">
					<el-input v-model="form.title" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '鱼种分类', 'en-US': 'Fish Category' })">
					<el-select v-model="form.pinzhongfenlei" filterable :placeholder="$t({ 'zh-CN': '请选择分类', 'en-US': 'Choose category' })">
						<el-option v-for="item in categories" :key="item" :label="$fishLabel(item)" :value="item" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '发布人', 'en-US': 'Publisher' })">
					<el-input v-model="form.name" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '图片', 'en-US': 'Image' })">
					<input type="file" accept="image/*" @change="handleImageChange" />
					<div v-if="form.picture" class="preview">
						<img :src="$assetUrl(form.picture)" />
					</div>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '简介', 'en-US': 'Intro' })">
					<el-input v-model="form.introduction" type="textarea" :rows="4" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '内容', 'en-US': 'Content' })">
					<el-input v-model="form.content" type="textarea" :rows="8" />
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
			categories: [],
			dialogVisible: false,
			saving: false,
			form: this.createEmptyForm(),
		}
	},
	async created() {
		await Promise.all([this.loadList(), this.loadCategories()])
	},
	methods: {
		createEmptyForm() {
			return {
				id: null,
				title: '',
				introduction: '',
				picture: '',
				pinzhongfenlei: '',
				content: '',
				name: 'admin',
				headportrait: '',
			}
		},
		async loadList() {
			const res = await this.$api.get('/news/page', { params: { page: 1, limit: 50, sort: 'addtime', order: 'desc' } })
			this.list = res.data.data.list || []
		},
		async loadCategories() {
			const res = await this.$api.get('/option/pinzhongfenlei/pinzhongfenlei')
			this.categories = res.data.data || []
		},
		openCreate() {
			this.form = this.createEmptyForm()
			this.dialogVisible = true
		},
		openEdit(row) {
			this.form = Object.assign(this.createEmptyForm(), row)
			this.dialogVisible = true
		},
		async handleImageChange(event) {
			const file = event.target.files && event.target.files[0]
			if (!file) return
			const fd = new FormData()
			fd.append('file', file)
			const res = await this.$api.post('/file/upload', fd, {
				headers: { 'Content-Type': 'multipart/form-data' },
			})
			this.form.picture = `upload/${res.data.file}`
		},
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				const url = payload.id ? '/news/update' : '/news/save'
				const res = await this.$api.post(url, payload)
				if (res.data.code === 0) {
					this.$message.success(this.$t({ 'zh-CN': '保存成功', 'en-US': 'Saved' }))
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
			const res = await this.$api.post('/news/delete', [row.id])
			if (res.data.code === 0) {
				this.$message.success(this.$t({ 'zh-CN': '删除成功', 'en-US': 'Deleted' }))
				await this.loadList()
			}
		},
	},
}
</script>

<style scoped>
.toolbar {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
	padding: 20px 22px;
	border-radius: 24px;
	background: #fff;
	box-shadow: 0 18px 38px rgba(15, 111, 168, 0.08);
}
.toolbar h1 {
	margin: 0;
	color: #163246;
}
.preview img {
	width: 160px;
	height: 100px;
	object-fit: cover;
	border-radius: 10px;
	margin-top: 12px;
}
</style>
