<template>
	<div class="page">
		<div class="toolbar">
			<div>
				<h1>{{ $t({ 'zh-CN': '观赏鱼百科管理', 'en-US': 'Encyclopedia Management' }) }}</h1>
				<p>{{ $t({ 'zh-CN': '这里维护的条目会直接影响客户端百科列表、详情页和搜索结果。', 'en-US': 'Entries here directly affect client encyclopedia lists, details, and search results.' }) }}</p>
			</div>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增百科条目', 'en-US': 'Add Entry' }) }}</el-button>
		</div>

		<div class="filters">
			<el-input v-model="filters.keyword" clearable :placeholder="$t({ 'zh-CN': '搜索中英文名称', 'en-US': 'Search Chinese or English name' })" @keyup.enter.native="loadList" />
			<el-select v-model="filters.category" clearable filterable :placeholder="$t({ 'zh-CN': '筛选品种分类', 'en-US': 'Filter fish category' })" @change="loadList">
				<el-option v-for="item in categories" :key="item" :label="$fishLabel(item)" :value="item" />
			</el-select>
			<el-select v-model="filters.level" clearable :placeholder="$t({ 'zh-CN': '筛选饲养难度', 'en-US': 'Filter difficulty' })" @change="loadList">
				<el-option v-for="item in difficultyOptions" :key="item" :label="item" :value="item" />
			</el-select>
			<el-button @click="loadList">{{ $t({ 'zh-CN': '查询', 'en-US': 'Search' }) }}</el-button>
			<el-button @click="resetFilters">{{ $t({ 'zh-CN': '重置', 'en-US': 'Reset' }) }}</el-button>
		</div>

		<el-table :data="list" border>
			<el-table-column prop="zhongwenming" :label="$t({ 'zh-CN': '中文名', 'en-US': 'Chinese Name' })" min-width="150" />
			<el-table-column prop="yingwenming" :label="$t({ 'zh-CN': '英文名', 'en-US': 'English Name' })" min-width="150" />
			<el-table-column prop="pinzhongfenlei" :label="$t({ 'zh-CN': '品种分类', 'en-US': 'Category' })" width="140">
				<template slot-scope="{ row }">
					{{ $fishLabel(row.pinzhongfenlei) }}
				</template>
			</el-table-column>
			<el-table-column prop="siyangnandu" :label="$t({ 'zh-CN': '饲养难度', 'en-US': 'Difficulty' })" width="120" />
			<el-table-column prop="clicknum" :label="$t({ 'zh-CN': '点击量', 'en-US': 'Clicks' })" width="90" />
			<el-table-column prop="storeupnum" :label="$t({ 'zh-CN': '收藏量', 'en-US': 'Favorites' })" width="90" />
			<el-table-column :label="$t({ 'zh-CN': '图片', 'en-US': 'Image' })" width="120">
				<template slot-scope="{ row }">
					<img v-if="firstImage(row.guanshangyutupian)" :src="$assetUrl(firstImage(row.guanshangyutupian))" class="thumb" />
				</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑百科', 'en-US': 'Edit Encyclopedia' }) : $t({ 'zh-CN': '新增百科', 'en-US': 'Add Encyclopedia' })" :visible.sync="dialogVisible" width="920px">
			<el-form label-width="130px">
				<el-form-item :label="$t({ 'zh-CN': '中文名', 'en-US': 'Chinese Name' })">
					<el-input v-model="form.zhongwenming" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '英文名', 'en-US': 'English Name' })">
					<el-input v-model="form.yingwenming" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '品种分类', 'en-US': 'Category' })">
					<el-select v-model="form.pinzhongfenlei" clearable filterable allow-create default-first-option :placeholder="$t({ 'zh-CN': '请选择或输入分类', 'en-US': 'Choose or type category' })">
						<el-option v-for="item in categories" :key="item" :label="$fishLabel(item)" :value="item" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '种属', 'en-US': 'Genus' })">
					<el-input v-model="form.zhongshu" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '图片', 'en-US': 'Images' })">
					<input type="file" accept="image/*" multiple @change="handleImagesChange" />
					<div class="preview-grid" v-if="form.guanshangyutupian">
						<img v-for="(item, index) in form.guanshangyutupian.split(',')" :key="index" :src="$assetUrl(item)" />
					</div>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '最小水体', 'en-US': 'Min Tank' })">
					<el-input v-model="form.zuixiaoshuiti" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '饲养难度', 'en-US': 'Difficulty' })">
					<el-select v-model="form.siyangnandu" clearable :placeholder="$t({ 'zh-CN': '请选择难度', 'en-US': 'Choose difficulty' })">
						<el-option v-for="item in difficultyOptions" :key="item" :label="item" :value="item" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '成体尺寸', 'en-US': 'Adult Size' })">
					<el-input v-model="form.chengtichicun" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '珊瑚缸兼容', 'en-US': 'Reef Compatible' })">
					<el-input v-model="form.shanhugangjianrong" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '主要产地', 'en-US': 'Origin' })">
					<el-input v-model="form.zhuyaochandi" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '食物要求', 'en-US': 'Food Requirement' })">
					<el-input v-model="form.shiwuyaoqiu" type="textarea" :rows="3" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '性情', 'en-US': 'Temperament' })">
					<el-input v-model="form.xingqing" type="textarea" :rows="3" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '要求', 'en-US': 'Requirement' })">
					<el-input v-model="form.yaoqiu" type="textarea" :rows="3" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '饲养方法', 'en-US': 'Care Method' })">
					<el-input v-model="form.siyangfangfa" type="textarea" :rows="4" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '生物简介', 'en-US': 'Introduction' })">
					<el-input v-model="form.shengwujianjie" type="textarea" :rows="8" />
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
			difficultyOptions: ['简单', '中等', '困难'],
			dialogVisible: false,
			saving: false,
			filters: {
				keyword: '',
				category: '',
				level: '',
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
				zhongwenming: '',
				yingwenming: '',
				pinzhongfenlei: '',
				zhongshu: '',
				guanshangyutupian: '',
				zuixiaoshuiti: '',
				siyangnandu: '',
				chengtichicun: '',
				shanhugangjianrong: '',
				zhuyaochandi: '',
				shiwuyaoqiu: '',
				xingqing: '',
				yaoqiu: '',
				siyangfangfa: '',
				shengwujianjie: '',
				thumbsupnum: 0,
				crazilynum: 0,
				clicknum: 0,
				storeupnum: 0,
			}
		},
		firstImage(value) {
			if (!value) return ''
			return value.split(',')[0]
		},
		async loadCategories() {
			const res = await this.$api.get('/option/pinzhongfenlei/pinzhongfenlei')
			this.categories = res.data.data || []
		},
		async loadList() {
			const params = {
				page: 1,
				limit: 100,
				sort: 'addtime',
				order: 'desc',
			}
			if (this.filters.keyword) {
				params.zhongwenming = this.filters.keyword
			}
			if (this.filters.category) {
				params.pinzhongfenlei = this.filters.category
			}
			if (this.filters.level) {
				params.siyangnandu = this.filters.level
			}
			const res = await this.$api.get('/guanshangyubaike/page', { params })
			this.list = (res.data.data && res.data.data.list) || []
		},
		resetFilters() {
			this.filters = { keyword: '', category: '', level: '' }
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
			this.form.guanshangyutupian = uploaded.join(',')
		},
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				const url = payload.id ? '/guanshangyubaike/update' : '/guanshangyubaike/save'
				const res = await this.$api.post(url, payload)
				if (res.data.code === 0) {
					this.$message.success(this.$t({ 'zh-CN': '保存成功，客户端百科页会读取到最新内容。', 'en-US': 'Saved. Client encyclopedia pages will use the latest content.' }))
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
					'zh-CN': `确认删除“${row.zhongwenming || row.yingwenming}”吗？删除后客户端百科页将不再显示它。`,
					'en-US': `Delete "${row.zhongwenming || row.yingwenming}"? It will disappear from the client encyclopedia pages.`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/guanshangyubaike/delete', [row.id])
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
