<template>
  <div class="page">
    <div class="toolbar">
      <h1>{{ $t({ 'zh-CN': '购物车', 'en-US': 'Cart' }) }}</h1>
    </div>
    <el-table :data="list" border>
      <el-table-column prop="goodname" :label="$t({ 'zh-CN': '商品', 'en-US': 'Product' })" />
      <el-table-column prop="goodtype" :label="$t({ 'zh-CN': '分类', 'en-US': 'Category' })" />
      <el-table-column prop="buynumber" :label="$t({ 'zh-CN': '数量', 'en-US': 'Qty' })" />
      <el-table-column prop="price" :label="$t({ 'zh-CN': '单价', 'en-US': 'Price' })" />
      <el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="260">
        <template slot-scope="{ row }">
          <el-button size="mini" type="primary" @click="checkout(row)">
            {{ $t({ 'zh-CN': '创建订单', 'en-US': 'Create order' }) }}
          </el-button>
          <el-button size="mini" type="danger" @click="remove(row)">
            {{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    async load() {
      const res = await this.$api.get('/cart/list', {
        params: { page: 1, limit: 100, userid: Number(localStorage.getItem('frontUserid')) },
      })
      this.list = (res.data.data && res.data.data.list) || []
    },
    async remove(row) {
      const res = await this.$api.post('/cart/delete', [row.id])
      if (res.data.code === 0) {
        this.$message.success(this.$t({ 'zh-CN': '已删除', 'en-US': 'Deleted' }))
        this.load()
      }
    },
    async checkout(row) {
      const userid = Number(localStorage.getItem('frontUserid') || 0)
      if (!userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录后再下单', 'en-US': 'Please log in before ordering' }))
        this.$router.push('/login')
        return
      }
      const addrRes = await this.$api.get('/address/default')
      const addr = addrRes.data.data
      if (!addr) {
        this.$message.error(this.$t({ 'zh-CN': '请先添加默认收货地址', 'en-US': 'Please add a default address first' }))
        return
      }
      const order = {
        orderid: 'OD' + Date.now(),
        goodid: row.goodid,
        goodname: row.goodname,
        goodtype: row.goodtype,
        picture: row.picture,
        buynumber: row.buynumber,
        price: row.price,
        total: row.price * row.buynumber,
        type: 1,
        status: '未支付',
        address: addr.address,
        tel: addr.phone,
        consignee: addr.name,
        remark: '商城订单，等待模拟支付',
        userid,
        tablename: row.tablename,
        logistics: '待支付',
        sfsh: '待审核',
        shhf: '',
        role: 'yonghu',
        returnreason: '',
      }
      const res = await this.$api.post('/orders/save', order)
      if (res.data.code === 0) {
        const orderId = res.data.data
        await this.$api.post('/cart/delete', [row.id])
        this.$message.success(this.$t({ 'zh-CN': '订单已创建，请完成模拟支付', 'en-US': 'Order created. Please complete mock payment.' }))
        this.load()
        this.$router.push({
          path: '/orders',
          query: orderId ? { payOrderId: String(orderId) } : {},
        })
      }
    },
  },
}
</script>

<style scoped>
.toolbar{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);margin-bottom:20px}
.toolbar h1{margin:0;color:#163246}
</style>
