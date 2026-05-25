<template>
	<div class="page">
		<div class="toolbar">
			<div>
				<h1>{{ $t({ 'zh-CN': '品种分类管理', 'en-US': 'Fish Category Management' }) }}</h1>
				<p>{{ $t({ 'zh-CN': '这里维护的是客户端百科、资讯、教学等模块复用的鱼类分类。', 'en-US': 'This manages fish categories reused by encyclopedia, news, and teaching modules on the client.' }) }}</p>
			</div>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增分类', 'en-US': 'Add Category' }) }}</el-button>
		</div>

		<div class="filters">
			<el-input v-model="keyword" clearable :placeholder="$t({ 'zh-CN': '搜索品种分类', 'en-US': 'Search category' })" @keyup.enter.native="loadList" />
			<el-button @click="loadList">{{ $t({ 'zh-CN': '查询', 'en-US': 'Search' }) }}</el-button>
			<el-button @click="resetFilters">{{ $t({ 'zh-CN': '重置', 'en-US': 'Reset' }) }}</el-button>
		</div>

		<el-table :data="list" border>
			<el-table-column prop="pinzhongfenlei" :label="$t({ 'zh-CN': '品种分类', 'en-US': 'Fish Category' })" min-width="240" />
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑品种分类', 'en-US': 'Edit Fish Category' }) : $t({ 'zh-CN': '新增品种分类', 'en-US': 'Add Fish Category' })" :visible.sync="dialogVisible" width="560px">
			<el-form label-width="110px">
				<el-form-item :label="$t({ 'zh-CN': '分类名称', 'en-US': 'Category Name' })">
					<el-input v-model="form.pinzhongfenlei" />
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
			keyword: '',
			list: [],
			dialogVisible: false,
			saving: false,
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
				pinzhongfenlei: '',
			}
		},
		async loadList() {
			const params = {
				page: 1,
				limit: 100,
				sort: 'id',
				order: 'desc',
			}
			if (this.keyword) {
				params.pinzhongfenlei = `%${this.keyword}%`
			}
			const res = await this.$api.get('/pinzhongfenlei/page', { params })
			this.list = (res.data.data && res.data.data.list) || []
		},
		resetFilters() {
			this.keyword = ''
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
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				const url = payload.id ? '/pinzhongfenlei/update' : '/pinzhongfenlei/save'
				const res = await this.$api.post(url, payload)
				if (res.data.code === 0) {
					this.$message.success(this.$t({ 'zh-CN': '保存成功，百科和教学等模块可直接使用新分类。', 'en-US': 'Saved. Encyclopedia and teaching modules can use the new category immediately.' }))
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
					'zh-CN': `确认删除分类“${row.pinzhongfenlei}”吗？如果百科或教学还在用这个分类，建议先改内容再删。`,
					'en-US': `Delete category "${row.pinzhongfenlei}"? If encyclopedia or teaching items still use it, update them first.`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/pinzhongfenlei/delete', [row.id])
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
	width: 260px;
}
</style>
