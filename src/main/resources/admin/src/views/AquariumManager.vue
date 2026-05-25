<template>
	<div class="page">
		<div class="toolbar">
			<div>
				<h1>{{ $t({ 'zh-CN': '水族馆管理', 'en-US': 'Aquarium Management' }) }}</h1>
				<p>{{ $t({ 'zh-CN': '这里修改的数据会直接影响客户端水族馆列表和详情页。', 'en-US': 'Changes here directly affect the client aquarium list and detail pages.' }) }}</p>
			</div>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增水族馆', 'en-US': 'Add Aquarium' }) }}</el-button>
		</div>

		<div class="filters">
			<el-input v-model="filters.name" clearable :placeholder="$t({ 'zh-CN': '搜索场馆名称', 'en-US': 'Search aquarium name' })" @keyup.enter.native="loadList" />
			<el-select v-model="filters.category" clearable filterable :placeholder="$t({ 'zh-CN': '筛选场馆分类', 'en-US': 'Filter category' })" @change="loadList">
				<el-option v-for="item in categories" :key="item" :label="item" :value="item" />
			</el-select>
			<el-input v-model="filters.location" clearable :placeholder="$t({ 'zh-CN': '搜索场馆位置', 'en-US': 'Search location' })" @keyup.enter.native="loadList" />
			<el-button @click="loadList">{{ $t({ 'zh-CN': '查询', 'en-US': 'Search' }) }}</el-button>
			<el-button @click="resetFilters">{{ $t({ 'zh-CN': '重置', 'en-US': 'Reset' }) }}</el-button>
		</div>

		<el-table :data="list" border>
			<el-table-column prop="changguanbianhao" :label="$t({ 'zh-CN': '编号', 'en-US': 'Code' })" width="150" />
			<el-table-column prop="changguanmingcheng" :label="$t({ 'zh-CN': '场馆名称', 'en-US': 'Name' })" min-width="180" />
			<el-table-column prop="changguanfenlei" :label="$t({ 'zh-CN': '场馆分类', 'en-US': 'Category' })" width="140" />
			<el-table-column prop="changguanweizhi" :label="$t({ 'zh-CN': '场馆位置', 'en-US': 'Location' })" min-width="180" />
			<el-table-column prop="kaifangshijian" :label="$t({ 'zh-CN': '开放时间', 'en-US': 'Open Time' })" width="140" />
			<el-table-column prop="storeupnum" :label="$t({ 'zh-CN': '收藏数', 'en-US': 'Favorites' })" width="100" />
			<el-table-column :label="$t({ 'zh-CN': '封面', 'en-US': 'Image' })" width="120">
				<template slot-scope="{ row }">
					<img v-if="firstImage(row.changguantupian)" :src="$assetUrl(firstImage(row.changguantupian))" class="thumb" />
				</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑水族馆', 'en-US': 'Edit Aquarium' }) : $t({ 'zh-CN': '新增水族馆', 'en-US': 'Add Aquarium' })" :visible.sync="dialogVisible" width="820px">
			<el-form label-width="120px">
				<el-form-item :label="$t({ 'zh-CN': '场馆编号', 'en-US': 'Code' })">
					<el-input v-model="form.changguanbianhao" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '场馆名称', 'en-US': 'Name' })">
					<el-input v-model="form.changguanmingcheng" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '场馆分类', 'en-US': 'Category' })">
					<el-select v-model="form.changguanfenlei" clearable filterable allow-create default-first-option :placeholder="$t({ 'zh-CN': '请选择或输入分类', 'en-US': 'Choose or type category' })">
						<el-option v-for="item in categories" :key="item" :label="item" :value="item" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '场馆图片', 'en-US': 'Images' })">
					<input type="file" accept="image/*" multiple @change="handleImagesChange" />
					<div class="preview-grid" v-if="form.changguantupian">
						<img v-for="(item, index) in form.changguantupian.split(',')" :key="index" :src="$assetUrl(item)" />
					</div>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '场馆位置', 'en-US': 'Location' })">
					<el-input v-model="form.changguanweizhi" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '联系人', 'en-US': 'Contact' })">
					<el-input v-model="form.lianxiren" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '联系电话', 'en-US': 'Phone' })">
					<el-input v-model="form.lianxidianhua" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '开放时间', 'en-US': 'Open Time' })">
					<el-input v-model="form.kaifangshijian" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '场馆介绍', 'en-US': 'Introduction' })">
					<el-input v-model="form.changguanjieshao" type="textarea" :rows="8" />
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
			filters: {
				name: '',
				category: '',
				location: '',
			},
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
				changguanbianhao: `CG${Date.now()}`,
				changguanmingcheng: '',
				changguanfenlei: '',
				changguantupian: '',
				changguanweizhi: '',
				lianxiren: '',
				lianxidianhua: '',
				kaifangshijian: '',
				changguanjieshao: '',
				storeupnum: 0,
			}
		},
		firstImage(value) {
			if (!value) return ''
			return value.split(',')[0]
		},
		async loadCategories() {
			const res = await this.$api.get('/option/changguanfenlei/changguanfenlei')
			this.categories = res.data.data || []
		},
		async loadList() {
			const params = {
				page: 1,
				limit: 100,
				sort: 'addtime',
				order: 'desc',
			}
			if (this.filters.name) params.changguanmingcheng = this.filters.name
			if (this.filters.category) params.changguanfenlei = this.filters.category
			if (this.filters.location) params.changguanweizhi = this.filters.location
			const res = await this.$api.get('/shuizuguanxinxi/page', { params })
			this.list = (res.data.data && res.data.data.list) || []
		},
		resetFilters() {
			this.filters = { name: '', category: '', location: '' }
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
			this.form.changguantupian = uploaded.join(',')
		},
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				const url = payload.id ? '/shuizuguanxinxi/update' : '/shuizuguanxinxi/save'
				const res = await this.$api.post(url, payload)
				if (res.data.code === 0) {
					this.$message.success(this.$t({ 'zh-CN': '保存成功，客户端水族馆页面会读取最新数据。', 'en-US': 'Saved. Client aquarium pages will use the latest data.' }))
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
					'zh-CN': `确认删除“${row.changguanmingcheng}”吗？删除后客户端将不再显示它。`,
					'en-US': `Delete "${row.changguanmingcheng}"? It will disappear from the client pages.`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/shuizuguanxinxi/delete', [row.id])
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
