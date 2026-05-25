<template>
	<div class="page">
		<div class="toolbar">
			<h1>{{ $t({ 'zh-CN': '商城管理', 'en-US': 'Shop Management' }) }}</h1>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增商品', 'en-US': 'Add Product' }) }}</el-button>
		</div>

		<el-table :data="list" border>
			<el-table-column prop="shangpinmingcheng" :label="$t({ 'zh-CN': '商品名称', 'en-US': 'Product' })" min-width="180" />
			<el-table-column prop="shangpinfenlei" :label="$t({ 'zh-CN': '商品分类', 'en-US': 'Category' })" min-width="140" />
			<el-table-column prop="price" :label="$t({ 'zh-CN': '价格', 'en-US': 'Price' })" width="100" />
			<el-table-column prop="alllimittimes" :label="$t({ 'zh-CN': '库存', 'en-US': 'Stock' })" width="90" />
			<el-table-column :label="$t({ 'zh-CN': '上架状态', 'en-US': 'On Shelf' })" width="120">
				<template slot-scope="{ row }">
					<el-switch
						:value="row.onshelves === 1"
						@change="toggleShelf(row, $event)"
					/>
				</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑商品', 'en-US': 'Edit Product' }) : $t({ 'zh-CN': '新增商品', 'en-US': 'Add Product' })" :visible.sync="dialogVisible" width="820px">
			<el-form label-width="120px">
				<el-form-item :label="$t({ 'zh-CN': '商品名称', 'en-US': 'Product' })">
					<el-input v-model="form.shangpinmingcheng" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '商品分类', 'en-US': 'Category' })">
					<el-select v-model="form.shangpinfenlei" filterable :placeholder="$t({ 'zh-CN': '请选择分类', 'en-US': 'Choose category' })">
						<el-option v-for="item in categories" :key="item" :label="item" :value="item" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '商品图片', 'en-US': 'Images' })">
					<input type="file" accept="image/*" multiple @change="handleImagesChange" />
					<div class="preview-grid" v-if="form.shangpintupian">
						<img
							v-for="(item, index) in form.shangpintupian.split(',')"
							:key="index"
							:src="$assetUrl(item)"
						/>
					</div>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '商品品牌', 'en-US': 'Brand' })">
					<el-input v-model="form.shangpinpinpai" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '商品规格', 'en-US': 'Spec' })">
					<el-input v-model="form.shangpinguige" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '生产厂家', 'en-US': 'Manufacturer' })">
					<el-input v-model="form.shengchanchangjia" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '单限', 'en-US': 'Per Limit' })">
					<el-input-number v-model="form.onelimittimes" :min="-1" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '库存', 'en-US': 'Stock' })">
					<el-input-number v-model="form.alllimittimes" :min="-1" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '价格', 'en-US': 'Price' })">
					<el-input-number v-model="form.price" :min="0" :step="1" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '详情', 'en-US': 'Detail' })">
					<el-input v-model="form.shangpinxiangqing" type="textarea" :rows="7" />
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
				shangpinmingcheng: '',
				shangpinfenlei: '',
				shangpintupian: '',
				shangpinpinpai: '',
				shangpinguige: '',
				shengchanchangjia: '',
				shangpinxiangqing: '',
				onelimittimes: -1,
				alllimittimes: -1,
				price: 0,
				thumbsupnum: 0,
				crazilynum: 0,
				clicknum: 0,
				discussnum: 0,
				onshelves: 1,
				storeupnum: 0,
			}
		},
		async loadList() {
			const res = await this.$api.get('/xianshangshangcheng/page', {
				params: { page: 1, limit: 100, sort: 'addtime', order: 'desc' },
			})
			this.list = (res.data.data && res.data.data.list) || []
		},
		async loadCategories() {
			const res = await this.$api.get('/option/shangpinfenlei/shangpinfenlei')
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
			this.form.shangpintupian = uploaded.join(',')
		},
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				const url = payload.id ? '/xianshangshangcheng/update' : '/xianshangshangcheng/save'
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
		async toggleShelf(row, enabled) {
			const payload = Object.assign({}, row, {
				onshelves: enabled ? 1 : 0,
			})
			const res = await this.$api.post('/xianshangshangcheng/update', payload)
			if (res.data.code === 0) {
				row.onshelves = enabled ? 1 : 0
				this.$message.success(this.$t({ 'zh-CN': '上下架状态已更新', 'en-US': 'Shelf status updated' }))
			}
		},
		async remove(row) {
			await this.$confirm(
				this.$t({
					'zh-CN': `确认删除“${row.shangpinmingcheng}”吗？`,
					'en-US': `Delete "${row.shangpinmingcheng}"?`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/xianshangshangcheng/delete', [row.id])
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

.preview-grid {
	display: flex;
	flex-wrap: wrap;
	gap: 10px;
	margin-top: 12px;
}

.preview-grid img {
	width: 120px;
	height: 90px;
	object-fit: cover;
	border-radius: 10px;
}
</style>
