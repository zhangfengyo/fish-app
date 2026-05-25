<template>
  <div class="page">
    <div class="toolbar">
      <div>
        <h1>{{ $t({ 'zh-CN': '订单', 'en-US': 'Orders' }) }}</h1>
        <p>{{ $t({ 'zh-CN': '支持模拟支付、取消、收货和退款状态流转', 'en-US': 'Supports mock payment and order status flow.' }) }}</p>
      </div>
      <el-radio-group v-model="statusFilter" size="small">
        <el-radio-button v-for="item in filterOptions" :key="item" :label="item" />
      </el-radio-group>
    </div>

    <el-table
      :data="filteredList"
      border
      class="order-table"
      @row-click="openDetail"
    >
      <el-table-column prop="orderid" :label="$t({ 'zh-CN': '订单号', 'en-US': 'Order ID' })" min-width="180" />
      <el-table-column prop="goodname" :label="$t({ 'zh-CN': '商品', 'en-US': 'Product' })" min-width="220" />
      <el-table-column prop="total" :label="$t({ 'zh-CN': '总价', 'en-US': 'Total' })" width="130">
        <template slot-scope="{ row }">
          <span class="money">CNY {{ money(row.total) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t({ 'zh-CN': '状态', 'en-US': 'Status' })" width="130">
        <template slot-scope="{ row }">
          <el-tag size="mini" :type="statusType(row.status)">{{ row.status || '-' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="addtime" :label="$t({ 'zh-CN': '创建时间', 'en-US': 'Created' })" min-width="170" />
      <el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="280">
        <template slot-scope="{ row }">
          <el-button type="primary" size="mini" @click.stop="openDetail(row)">
            {{ $t({ 'zh-CN': '详情', 'en-US': 'Details' }) }}
          </el-button>
          <el-button v-if="row.status === '未支付'" size="mini" type="warning" @click.stop="openPayment(row)">
            {{ $t({ 'zh-CN': '模拟支付', 'en-US': 'Mock Pay' }) }}
          </el-button>
          <el-button v-if="row.status === '未支付'" size="mini" @click.stop="cancelOrder(row)">
            {{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}
          </el-button>
          <el-button v-if="row.status === '已发货'" size="mini" type="success" @click.stop="confirmReceive(row)">
            {{ $t({ 'zh-CN': '确认收货', 'en-US': 'Confirm' }) }}
          </el-button>
          <el-button
            v-if="row.status === '已支付' || row.status === '已发货'"
            size="mini"
            type="danger"
            @click.stop="requestRefund(row)"
          >
            {{ $t({ 'zh-CN': '申请退款', 'en-US': 'Refund' }) }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="detailVisible"
      width="720px"
      append-to-body
      custom-class="order-detail-dialog"
    >
      <span slot="title">
        {{ detailItem && detailItem.orderid ? detailItem.orderid : $t({ 'zh-CN': '订单详情', 'en-US': 'Order details' }) }}
      </span>

      <div class="detail-shell" v-loading="detailLoading">
        <template v-if="detailItem && detailItem.id">
          <div class="detail-top">
            <img
              v-if="detailItem.picture"
              class="detail-image"
              :src="resolveImage(detailItem.picture)"
              :alt="detailItem.goodname"
            />
            <div v-else class="detail-image detail-image-empty">No Image</div>

            <div class="detail-main">
              <button
                v-if="detailItem.goodid"
                type="button"
                class="product-jump"
                @click="goToProductDetail(detailItem)"
              >
                {{ detailItem.goodname || '-' }}
              </button>
              <h3 v-else>{{ detailItem.goodname || '-' }}</h3>
              <div class="status-row">
                <el-tag :type="statusType(detailItem.status)">{{ detailItem.status || '-' }}</el-tag>
                <span v-if="detailItem.sfsh" class="tag secondary">{{ detailItem.sfsh }}</span>
              </div>
              <div class="price">CNY {{ money(detailItem.total) }}</div>
              <div class="summary">
                <div class="summary-item">
                  <span class="label">{{ $t({ 'zh-CN': '分类', 'en-US': 'Category' }) }}</span>
                  <span class="value">{{ detailItem.goodtype || '-' }}</span>
                </div>
                <div class="summary-item">
                  <span class="label">{{ $t({ 'zh-CN': '数量', 'en-US': 'Quantity' }) }}</span>
                  <span class="value">{{ detailItem.buynumber || 0 }}</span>
                </div>
                <div class="summary-item">
                  <span class="label">{{ $t({ 'zh-CN': '单价', 'en-US': 'Unit price' }) }}</span>
                  <span class="value">CNY {{ money(detailItem.price) }}</span>
                </div>
                <div class="summary-item">
                  <span class="label">{{ $t({ 'zh-CN': '创建时间', 'en-US': 'Created at' }) }}</span>
                  <span class="value">{{ detailItem.addtime || '-' }}</span>
                </div>
              </div>
            </div>
          </div>

          <div class="detail-grid">
            <div class="detail-card">
              <div class="card-title">{{ $t({ 'zh-CN': '收货信息', 'en-US': 'Shipping info' }) }}</div>
              <div class="card-line"><span>{{ $t({ 'zh-CN': '收货人', 'en-US': 'Consignee' }) }}</span><strong>{{ detailItem.consignee || '-' }}</strong></div>
              <div class="card-line"><span>{{ $t({ 'zh-CN': '电话', 'en-US': 'Phone' }) }}</span><strong>{{ detailItem.tel || '-' }}</strong></div>
              <div class="card-line card-line-full"><span>{{ $t({ 'zh-CN': '地址', 'en-US': 'Address' }) }}</span><strong>{{ detailItem.address || '-' }}</strong></div>
            </div>

            <div class="detail-card">
              <div class="card-title">{{ $t({ 'zh-CN': '订单信息', 'en-US': 'Order info' }) }}</div>
              <div class="card-line"><span>{{ $t({ 'zh-CN': '支付方式', 'en-US': 'Payment type' }) }}</span><strong>{{ paymentType(detailItem.type) }}</strong></div>
              <div class="card-line"><span>{{ $t({ 'zh-CN': '商品表', 'en-US': 'Table' }) }}</span><strong>{{ detailItem.tablename || '-' }}</strong></div>
              <div class="card-line card-line-full"><span>{{ $t({ 'zh-CN': '物流', 'en-US': 'Logistics' }) }}</span><strong>{{ detailItem.logistics || '-' }}</strong></div>
            </div>

            <div class="detail-card" v-if="detailItem.remark">
              <div class="card-title">{{ $t({ 'zh-CN': '备注', 'en-US': 'Remark' }) }}</div>
              <div class="card-text">{{ detailItem.remark }}</div>
            </div>

            <div class="detail-card" v-if="detailItem.shhf || detailItem.returnreason">
              <div class="card-title">{{ $t({ 'zh-CN': '售后信息', 'en-US': 'After-sales' }) }}</div>
              <div class="card-line card-line-full" v-if="detailItem.returnreason">
                <span>{{ $t({ 'zh-CN': '退货原因', 'en-US': 'Return reason' }) }}</span>
                <strong>{{ detailItem.returnreason }}</strong>
              </div>
              <div class="card-line card-line-full" v-if="detailItem.shhf">
                <span>{{ $t({ 'zh-CN': '审核回复', 'en-US': 'Review reply' }) }}</span>
                <strong>{{ detailItem.shhf }}</strong>
              </div>
            </div>
          </div>
        </template>

        <div v-else-if="!detailLoading" class="detail-empty">
          {{ $t({ 'zh-CN': '暂无订单详情', 'en-US': 'No order details' }) }}
        </div>
      </div>

      <span slot="footer" v-if="detailItem && detailItem.id">
        <el-button v-if="detailItem.status === '未支付'" type="warning" @click="openPayment(detailItem)">
          {{ $t({ 'zh-CN': '模拟支付', 'en-US': 'Mock Pay' }) }}
        </el-button>
        <el-button v-if="detailItem.status === '未支付'" @click="cancelOrder(detailItem)">
          {{ $t({ 'zh-CN': '取消订单', 'en-US': 'Cancel Order' }) }}
        </el-button>
        <el-button v-if="detailItem.status === '已发货'" type="success" @click="confirmReceive(detailItem)">
          {{ $t({ 'zh-CN': '确认收货', 'en-US': 'Confirm Receipt' }) }}
        </el-button>
        <el-button
          v-if="detailItem.status === '已支付' || detailItem.status === '已发货'"
          type="danger"
          @click="requestRefund(detailItem)"
        >
          {{ $t({ 'zh-CN': '申请退款', 'en-US': 'Request Refund' }) }}
        </el-button>
      </span>
    </el-dialog>

    <el-dialog
      :visible.sync="payDialogVisible"
      width="420px"
      append-to-body
      :title="$t({ 'zh-CN': '模拟支付', 'en-US': 'Mock Payment' })"
      @close="handlePaymentDialogClose"
    >
      <div v-if="payOrder" class="pay-box">
        <div class="pay-line">
          <span>{{ $t({ 'zh-CN': '订单号', 'en-US': 'Order ID' }) }}</span>
          <strong>{{ payOrder.orderid }}</strong>
        </div>
        <div class="pay-line">
          <span>{{ $t({ 'zh-CN': '商品', 'en-US': 'Product' }) }}</span>
          <strong>{{ payOrder.goodname }}</strong>
        </div>
        <div class="pay-line total-line">
          <span>{{ $t({ 'zh-CN': '支付金额', 'en-US': 'Pay amount' }) }}</span>
          <strong>CNY {{ money(payOrder.total) }}</strong>
        </div>
        <el-radio-group v-model="paymentForm.type" class="pay-methods">
          <el-radio :label="1">{{ $t({ 'zh-CN': '余额支付', 'en-US': 'Balance' }) }}</el-radio>
          <el-radio :label="2">{{ $t({ 'zh-CN': '积分支付', 'en-US': 'Points' }) }}</el-radio>
          <el-radio :label="3">{{ $t({ 'zh-CN': '其他方式', 'en-US': 'Other' }) }}</el-radio>
        </el-radio-group>
        <p class="pay-note">{{ $t({ 'zh-CN': '这是演示支付，不会调用真实支付接口。', 'en-US': 'This is a demo payment flow. No real payment gateway is used.' }) }}</p>
      </div>
      <span slot="footer">
        <el-button @click="payDialogVisible = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
        <el-button type="primary" :loading="payLoading" @click="confirmPayment">{{ $t({ 'zh-CN': '确认支付', 'en-US': 'Confirm Payment' }) }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
const FILTER_OPTIONS = ['全部', '未支付', '已支付', '已发货', '已完成', '已取消', '已退款']

export default {
  data() {
    return {
      list: [],
      filterOptions: FILTER_OPTIONS,
      statusFilter: '全部',
      detailVisible: false,
      detailLoading: false,
      detailItem: null,
      payDialogVisible: false,
      payLoading: false,
      payOrder: null,
      paymentForm: {
        type: 1,
      },
    }
  },
  computed: {
    filteredList() {
      if (this.statusFilter === '全部') {
        return this.list
      }
      return this.list.filter(item => item.status === this.statusFilter)
    },
  },
  watch: {
    '$route.query.payOrderId': {
      async handler(value) {
        if (value) {
          await this.openPaymentByOrderId(value)
        }
      },
    },
  },
  async created() {
    await this.loadList()
    await this.openPaymentFromRoute()
  },
  methods: {
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
    async loadOrderDetail(id, silent) {
      if (!silent) {
        this.detailLoading = true
      }
      try {
        const res = await this.$api.get(`/orders/detail/${id}`)
        return (res.data && res.data.data) || null
      } finally {
        if (!silent) {
          this.detailLoading = false
        }
      }
    },
    resolveImage(value) {
      if (!value) {
        return ''
      }
      return /^https?:\/\//.test(value) ? value : this.$assetUrl(value)
    },
    paymentType(value) {
      if (Number(value) === 2) {
        return this.$t({ 'zh-CN': '积分', 'en-US': 'Points' })
      }
      if (Number(value) === 3) {
        return this.$t({ 'zh-CN': '其他', 'en-US': 'Other' })
      }
      if (value == null || value === '') {
        return this.$t({ 'zh-CN': '待支付', 'en-US': 'Pending payment' })
      }
      return this.$t({ 'zh-CN': '余额', 'en-US': 'Balance' })
    },
    async openPaymentFromRoute() {
      if (this.$route.query.payOrderId) {
        await this.openPaymentByOrderId(this.$route.query.payOrderId)
      }
    },
    async openPaymentByOrderId(orderId) {
      const detail = await this.loadOrderDetail(orderId, true)
      if (!detail || detail.status !== '未支付') {
        this.handlePaymentDialogClose()
        return
      }
      this.paymentForm.type = Number(detail.type || 1)
      this.payOrder = detail
      this.payDialogVisible = true
    },
    openPayment(row) {
      if (!row || row.status !== '未支付') {
        return
      }
      this.paymentForm.type = Number(row.type || 1)
      this.payOrder = Object.assign({}, row)
      this.payDialogVisible = true
    },
    async confirmPayment() {
      if (!this.payOrder || !this.payOrder.id) {
        return
      }
      this.payLoading = true
      try {
        await this.updateOrder(
          this.payOrder,
          {
            status: '已支付',
            type: Number(this.paymentForm.type || 1),
            logistics: '待发货',
            remark: this.payOrder.remark || '模拟支付成功',
          },
          this.$t({ 'zh-CN': '模拟支付成功', 'en-US': 'Mock payment completed' })
        )
        this.payDialogVisible = false
      } finally {
        this.payLoading = false
      }
    },
    handlePaymentDialogClose() {
      this.payOrder = null
      const nextQuery = Object.assign({}, this.$route.query)
      delete nextQuery.payOrderId
      this.$router.replace({ path: this.$route.path, query: nextQuery }).catch(() => {})
    },
    goToProductDetail(item) {
      if (!item || !item.goodid) {
        return
      }
      if (item.tablename && item.tablename !== 'xianshangshangcheng') {
        this.$message.warning(this.$t({ 'zh-CN': '当前订单不是商城商品', 'en-US': 'This order is not a shop product' }))
        return
      }
      this.detailVisible = false
      this.$router.push({
        path: '/shop',
        query: {
          productId: String(item.goodid),
          fromOrderId: String(item.id || ''),
        },
      })
    },
    async openDetail(row) {
      this.detailVisible = true
      this.detailItem = null
      try {
        this.detailItem = await this.loadOrderDetail(row.id)
      } catch (e) {
        this.$message.error(this.$t({ 'zh-CN': '订单详情加载失败', 'en-US': 'Failed to load order details' }))
        this.detailVisible = false
      }
    },
    async cancelOrder(row) {
      await this.$confirm(
        this.$t({
          'zh-CN': '确认取消这个订单吗？',
          'en-US': 'Cancel this order?',
        }),
        this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
        { type: 'warning' }
      )
      await this.updateOrder(
        row,
        {
          status: '已取消',
          logistics: '订单已取消',
        },
        this.$t({ 'zh-CN': '订单已取消', 'en-US': 'Order cancelled' })
      )
    },
    async confirmReceive(row) {
      await this.updateOrder(
        row,
        {
          status: '已完成',
          logistics: '已签收',
        },
        this.$t({ 'zh-CN': '已确认收货', 'en-US': 'Order completed' })
      )
    },
    async requestRefund(row) {
      await this.$confirm(
        this.$t({
          'zh-CN': '确认申请退款吗？',
          'en-US': 'Request refund for this order?',
        }),
        this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
        { type: 'warning' }
      )
      await this.updateOrder(
        row,
        {
          status: '已退款',
          logistics: '退款处理中',
          returnreason: row.returnreason || '用户申请退款',
        },
        this.$t({ 'zh-CN': '退款状态已提交', 'en-US': 'Refund requested' })
      )
    },
    async updateOrder(row, changes, successMessage) {
      const payload = Object.assign({}, row, changes)
      const res = await this.$api.post('/orders/update', payload)
      if (res.data.code !== 0) {
        this.$message.error(res.data.msg || this.$t({ 'zh-CN': '订单更新失败', 'en-US': 'Failed to update order' }))
        return
      }
      this.$message.success(successMessage)
      await this.loadList()
      if (this.detailVisible && this.detailItem && Number(this.detailItem.id) === Number(payload.id)) {
        this.detailItem = await this.loadOrderDetail(payload.id, true)
      }
      if (this.payOrder && Number(this.payOrder.id) === Number(payload.id)) {
        this.payOrder = Object.assign({}, this.payOrder, changes)
      }
    },
  },
}
</script>

<style scoped>
.toolbar{display:flex;justify-content:space-between;align-items:flex-start;gap:18px;padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);margin-bottom:20px}
.toolbar h1{margin:0;color:#163246}
.toolbar p{margin:8px 0 0;color:#64748b;font-size:13px}
.order-table :deep(.el-table__row){cursor:pointer}
.money{color:#e65100;font-weight:700}
.detail-shell{min-height:240px}
.detail-top{display:flex;gap:22px;align-items:flex-start;margin-bottom:20px}
.detail-image{width:220px;height:220px;object-fit:cover;border-radius:18px;background:#f4f7fb;flex:0 0 220px}
.detail-image-empty{display:flex;align-items:center;justify-content:center;color:#94a3b8;font-weight:600}
.detail-main{flex:1}
.detail-main h3{margin:0 0 12px;color:#163246;font-size:24px;line-height:1.3}
.product-jump{display:inline-block;padding:0;border:none;background:none;margin:0 0 12px;color:#0f6fa8;font-size:24px;font-weight:700;line-height:1.3;text-align:left;cursor:pointer}
.product-jump:hover{text-decoration:underline;color:#0b5a88}
.status-row{display:flex;gap:10px;flex-wrap:wrap;margin-bottom:12px}
.tag{display:inline-flex;align-items:center;padding:6px 12px;border-radius:999px;background:#e8f5fb;color:#0f6fa8;font-size:13px;font-weight:700}
.tag.secondary{background:#fff1f2;color:#be123c}
.price{margin-bottom:16px;color:#e65100;font-size:32px;font-weight:700}
.summary{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:12px}
.summary-item{padding:12px 14px;border-radius:16px;background:#f7fbfe}
.label{display:block;margin-bottom:6px;color:#64748b;font-size:12px;letter-spacing:.04em;text-transform:uppercase}
.value{display:block;color:#163246;font-size:15px;word-break:break-word}
.detail-grid{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:16px}
.detail-card{padding:16px 18px;border-radius:18px;background:#fff;box-shadow:0 14px 30px rgba(15,111,168,.08)}
.card-title{margin-bottom:12px;color:#163246;font-size:16px;font-weight:700}
.card-line{display:flex;justify-content:space-between;gap:12px;margin-bottom:10px;color:#475569}
.card-line span{flex:0 0 82px}
.card-line strong{flex:1;color:#0f172a;font-weight:600;text-align:right;word-break:break-word}
.card-line-full{display:block}
.card-line-full strong{display:block;margin-top:6px;text-align:left}
.card-text{color:#0f172a;line-height:1.7;word-break:break-word}
.detail-empty{display:flex;align-items:center;justify-content:center;min-height:220px;color:#94a3b8}
.pay-box{display:grid;gap:14px}
.pay-line{display:flex;justify-content:space-between;gap:12px;color:#475569}
.pay-line strong{color:#0f172a}
.total-line strong{color:#e65100;font-size:20px}
.pay-methods{display:grid;gap:10px}
.pay-note{margin:0;color:#64748b;line-height:1.7;font-size:13px}
@media (max-width: 900px){
  .toolbar{flex-direction:column}
  .detail-top{flex-direction:column}
  .detail-image{width:100%;height:260px;flex:auto}
  .summary{grid-template-columns:1fr}
  .detail-grid{grid-template-columns:1fr}
}
</style>
