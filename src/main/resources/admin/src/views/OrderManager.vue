<template>
	<div class="page">
		<div class="toolbar">
			<h1>{{ $t({ 'zh-CN': '订单管理', 'en-US': 'Order Management' }) }}</h1>
			<div class="toolbar-actions">
				<el-select v-model="statusFilter" clearable :placeholder="$t({ 'zh-CN': '全部状态', 'en-US': 'All status' })">
					<el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
				</el-select>
				<el-input
					v-model.trim="keyword"
					class="keyword-input"
					:placeholder="$t({ 'zh-CN': '搜索订单号或商品名', 'en-US': 'Search order or product' })"
				/>
				<el-button type="primary" @click="loadList">{{ $t({ 'zh-CN': '刷新', 'en-US': 'Refresh' }) }}</el-button>
			</div>
		</div>

		<el-table :data="filteredList" border>
			<el-table-column prop="orderid" :label="$t({ 'zh-CN': '订单号', 'en-US': 'Order ID' })" min-width="170" />
			<el-table-column prop="goodname" :label="$t({ 'zh-CN': '商品名', 'en-US': 'Product' })" min-width="180" />
			<el-table-column prop="goodtype" :label="$t({ 'zh-CN': '分类', 'en-US': 'Category' })" min-width="130" />
			<el-table-column prop="total" :label="$t({ 'zh-CN': '总价', 'en-US': 'Total' })" width="120">
				<template slot-scope="{ row }">CNY {{ money(row.total) }}</template>
			</el-table-column>
			<el-table-column :label="$t({ 'zh-CN': '状态', 'en-US': 'Status' })" width="120">
				<template slot-scope="{ row }">
					<el-tag size="mini" :type="statusType(row.status)">{{ row.status || '-' }}</el-tag>
				</template>
			</el-table-column>
			<el-table-column prop="consignee" :label="$t({ 'zh-CN': '收货人', 'en-US': 'Consignee' })" width="120" />
			<el-table-column prop="logistics" :label="$t({ 'zh-CN': '物流', 'en-US': 'Logistics' })" min-width="150" />
			<el-table-column prop="addtime" :label="$t({ 'zh-CN': '创建时间', 'en-US': 'Created' })" min-width="170" />
			<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="280">
				<template slot-scope="{ row }">
					<el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '详情', 'en-US': 'Details' }) }}</el-button>
					<el-button v-if="row.status === '已支付'" size="mini" type="primary" @click="applyQuickStatus(row, '已发货')">
						{{ $t({ 'zh-CN': '发货', 'en-US': 'Ship' }) }}
					</el-button>
					<el-button v-if="row.status === '已发货'" size="mini" type="success" @click="applyQuickStatus(row, '已完成')">
						{{ $t({ 'zh-CN': '完成', 'en-US': 'Complete' }) }}
					</el-button>
					<el-button
						v-if="row.status !== '已取消' && row.status !== '已退款' && row.status !== '已完成'"
						size="mini"
						type="danger"
						@click="applyQuickStatus(row, '已退款')"
					>
						{{ $t({ 'zh-CN': '退款', 'en-US': 'Refund' }) }}
					</el-button>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :visible.sync="dialogVisible" :title="$t({ 'zh-CN': '订单详情', 'en-US': 'Order Details' })" width="760px">
			<el-form label-width="110px">
				<el-row :gutter="16">
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '订单号', 'en-US': 'Order ID' })">
							<el-input v-model="form.orderid" readonly />
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '创建时间', 'en-US': 'Created' })">
							<el-input v-model="form.addtime" readonly />
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="16">
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '商品名', 'en-US': 'Product' })">
							<el-input v-model="form.goodname" readonly />
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '分类', 'en-US': 'Category' })">
							<el-input v-model="form.goodtype" readonly />
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="16">
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '数量', 'en-US': 'Quantity' })">
							<el-input :value="form.buynumber || 0" readonly />
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '总价', 'en-US': 'Total' })">
							<el-input :value="`CNY ${money(form.total)}`" readonly />
						</el-form-item>
					</el-col>
				</el-row>
				<el-row :gutter="16">
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '收货人', 'en-US': 'Consignee' })">
							<el-input v-model="form.consignee" readonly />
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '电话', 'en-US': 'Phone' })">
							<el-input v-model="form.tel" readonly />
						</el-form-item>
					</el-col>
				</el-row>
				<el-form-item :label="$t({ 'zh-CN': '地址', 'en-US': 'Address' })">
					<el-input v-model="form.address" type="textarea" :rows="2" readonly />
				</el-form-item>
				<el-row :gutter="16">
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '订单状态', 'en-US': 'Order Status' })">
							<el-select v-model="form.status">
								<el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :span="12">
						<el-form-item :label="$t({ 'zh-CN': '支付方式', 'en-US': 'Payment Type' })">
							<el-select v-model="form.type">
								<el-option :label="$t({ 'zh-CN': '余额', 'en-US': 'Balance' })" :value="1" />
								<el-option :label="$t({ 'zh-CN': '积分', 'en-US': 'Points' })" :value="2" />
								<el-option :label="$t({ 'zh-CN': '其他', 'en-US': 'Other' })" :value="3" />
							</el-select>
						</el-form-item>
					</el-col>
				</el-row>
				<el-form-item :label="$t({ 'zh-CN': '物流信息', 'en-US': 'Logistics' })">
					<el-input v-model="form.logistics" type="textarea" :rows="2" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '备注', 'en-US': 'Remark' })">
					<el-input v-model="form.remark" type="textarea" :rows="2" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '退货原因', 'en-US': 'Return reason' })">
					<el-input v-model="form.returnreason" type="textarea" :rows="2" />
				</el-form-item>
			</el-form>
			<span slot="footer">
				<el-button @click="dialogVisible = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
				<el-button type="danger" @click="remove(form)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
				<el-button type="primary" :loading="saving" @click="save">{{ $t({ 'zh-CN': '保存', 'en-US': 'Save' }) }}</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
const STATUS_OPTIONS = ['未支付', '已支付', '已发货', '已完成', '已取消', '已退款']

export default {
	data() {
		return {
			list: [],
			keyword: '',
			statusFilter: '',
			statusOptions: STATUS_OPTIONS,
			dialogVisible: false,
			saving: false,
			form: this.createEmptyForm(),
		}
	},
	computed: {
		filteredList() {
			return this.list.filter(item => {
				const statusMatch = !this.statusFilter || item.status === this.statusFilter
				const text = `${item.orderid || ''} ${item.goodname || ''}`.toLowerCase()
				const keywordMatch = !this.keyword || text.includes(this.keyword.toLowerCase())
				return statusMatch && keywordMatch
			})
		},
	},
	async created() {
		await this.loadList()
	},
	methods: {
		createEmptyForm() {
			return {
				id: null,
				orderid: '',
				goodname: '',
				goodtype: '',
				buynumber: 0,
				total: 0,
				status: '未支付',
				consignee: '',
				tel: '',
				address: '',
				logistics: '',
				remark: '',
				returnreason: '',
				type: 1,
				addtime: '',
			}
		},
		money(value) {
			return Number(value || 0).toFixed(2)
		},
		statusType(status) {
			const typeMap = {
				未支付: 'warning',
				已支付: 'primary',
				已发货: '',
				已完成: 'success',
				已取消: 'info',
				已退款: 'danger',
			}
			return typeMap[status] || 'info'
		},
		async loadList() {
			const res = await this.$api.get('/orders/page', {
				params: { page: 1, limit: 100, sort: 'addtime', order: 'desc' },
			})
			this.list = (res.data.data && res.data.data.list) || []
		},
		openEdit(row) {
			this.form = Object.assign(this.createEmptyForm(), row, {
				type: Number(row.type || 1),
			})
			this.dialogVisible = true
		},
		async save() {
			this.saving = true
			try {
				const payload = Object.assign({}, this.form, {
					type: Number(this.form.type || 1),
				})
				const res = await this.$api.post('/orders/update', payload)
				if (res.data.code === 0) {
					this.$message.success(this.$t({ 'zh-CN': '订单已更新', 'en-US': 'Order updated' }))
					this.dialogVisible = false
					await this.loadList()
				} else {
					this.$message.error(res.data.msg || this.$t({ 'zh-CN': '更新失败', 'en-US': 'Update failed' }))
				}
			} finally {
				this.saving = false
			}
		},
		async applyQuickStatus(row, status) {
			const logisticsMap = {
				已发货: '已发货，等待签收',
				已完成: '已签收',
				已退款: '已退款',
			}
			const payload = Object.assign({}, row, {
				status,
				logistics: logisticsMap[status] || row.logistics,
			})
			if (status === '已退款' && !payload.returnreason) {
				payload.returnreason = '管理员已处理退款'
			}
			const res = await this.$api.post('/orders/update', payload)
			if (res.data.code === 0) {
				this.$message.success(this.$t({ 'zh-CN': '订单状态已更新', 'en-US': 'Order status updated' }))
				if (this.dialogVisible && Number(this.form.id) === Number(row.id)) {
					this.form = Object.assign({}, this.form, payload)
				}
				await this.loadList()
			} else {
				this.$message.error(res.data.msg || this.$t({ 'zh-CN': '订单状态更新失败', 'en-US': 'Failed to update order status' }))
			}
		},
		async remove(row) {
			await this.$confirm(
				this.$t({
					'zh-CN': `确认删除订单 ${row.orderid || ''} 吗？`,
					'en-US': `Delete order ${row.orderid || ''}?`,
				}),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			const res = await this.$api.post('/orders/delete', [row.id])
			if (res.data.code === 0) {
				this.$message.success(this.$t({ 'zh-CN': '订单已删除', 'en-US': 'Order deleted' }))
				this.dialogVisible = false
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
	gap: 16px;
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

.toolbar-actions {
	display: flex;
	align-items: center;
	gap: 12px;
}

.keyword-input {
	width: 260px;
}
</style>
