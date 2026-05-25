<template>
	<div class="page">
		<div class="toolbar">
			<h1>{{ $t({ 'zh-CN': '教学管理', 'en-US': 'Teaching Management' }) }}</h1>
			<el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增教学', 'en-US': 'Add Teaching' }) }}</el-button>
		</div>
		<el-table :data="list" border>
			<el-table-column prop="jiaoxuebiaoti" :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })" />
			<el-table-column prop="pinzhongfenlei" :label="$t({ 'zh-CN': '鱼种分类', 'en-US': 'Fish Category' })">
				<template slot-scope="{ row }">
					{{ $fishLabel(row.pinzhongfenlei) }}
				</template>
			</el-table-column>
			<el-table-column prop="jiaoxueshipin" :label="$t({ 'zh-CN': '视频', 'en-US': 'Video' })">
				<template slot-scope="{ row }">
					<span>{{ row.jiaoxueshipin ? $t({ 'zh-CN': '已上传', 'en-US': 'Uploaded' }) : $t({ 'zh-CN': '无', 'en-US': 'None' }) }}</span>
				</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
					<el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :title="form.id ? $t({ 'zh-CN': '编辑教学', 'en-US': 'Edit Teaching' }) : $t({ 'zh-CN': '新增教学', 'en-US': 'Add Teaching' })" :visible.sync="dialogVisible" width="760px">
			<el-form label-width="120px">
				<el-form-item :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })">
					<el-input v-model="form.jiaoxuebiaoti" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '鱼种分类', 'en-US': 'Fish Category' })">
					<el-select v-model="form.pinzhongfenlei" filterable :placeholder="$t({ 'zh-CN': '请选择分类', 'en-US': 'Choose category' })">
						<el-option v-for="item in categories" :key="item" :label="$fishLabel(item)" :value="item" />
					</el-select>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '封面', 'en-US': 'Cover' })">
					<input type="file" accept="image/*" @change="handleCoverChange" />
					<div v-if="form.fengmian" class="preview">
						<img :src="$assetUrl(form.fengmian)" />
					</div>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '视频', 'en-US': 'Video' })">
					<input type="file" accept="video/*" @change="handleVideoChange" />
					<div v-if="form.jiaoxueshipin" class="file-name">{{ form.jiaoxueshipin }}</div>
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '饲养方法', 'en-US': 'Care Method' })">
					<el-input v-model="form.siyangfangfa" type="textarea" :rows="3" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '疾病处理', 'en-US': 'Disease Help' })">
					<el-input v-model="form.yubingzhiliao" type="textarea" :rows="3" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '详情', 'en-US': 'Detail' })">
					<el-input v-model="form.jiaoxuexiangqing" type="textarea" :rows="6" />
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
				jiaoxuebiaoti: '',
				pinzhongfenlei: '',
				fengmian: '',
				jiaoxueshipin: '',
				siyangfangfa: '',
				yubingzhiliao: '',
				jiaoxuexiangqing: '',
				storeupnum: 0,
			}
		},
		async loadList() {
			const res = await this.$api.get('/siyangjiaoxue/page', { params: { page: 1, limit: 50, sort: 'addtime', order: 'desc' } })
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
		async uploadAny(file) {
			const fd = new FormData()
			fd.append('file', file)
			const res = await this.$api.post('/file/upload', fd, {
				headers: { 'Content-Type': 'multipart/form-data' },
			})
			return `upload/${res.data.file}`
		},
		async handleCoverChange(event) {
			const file = event.target.files && event.target.files[0]
			if (!file) return
			this.form.fengmian = await this.uploadAny(file)
		},
		async handleVideoChange(event) {
			const file = event.target.files && event.target.files[0]
			if (!file) return
			this.form.jiaoxueshipin = await this.uploadAny(file)
		},
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form)
				const url = payload.id ? '/siyangjiaoxue/update' : '/siyangjiaoxue/save'
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
					'zh-CN': `确认删除“${row.jiaoxuebiaoti}”吗？`,
					'en-US': `Delete "${row.jiaoxuebiaoti}"?`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/siyangjiaoxue/delete', [row.id])
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
.file-name {
	margin-top: 10px;
	font-size: 13px;
	color: #0f6fa8;
	word-break: break-all;
}
</style>
