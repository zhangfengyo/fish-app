<template>
  <div class="page">
    <div class="toolbar">
      <h1>{{ $t({ 'zh-CN': '地址管理', 'en-US': 'Addresses' }) }}</h1>
      <el-button type="primary" @click="dialog = true">{{ $t({ 'zh-CN': '新增地址', 'en-US': 'Add address' }) }}</el-button>
    </div>
    <el-table :data="list" border>
      <el-table-column prop="name" :label="$t({ 'zh-CN': '收货人', 'en-US': 'Name' })" />
      <el-table-column prop="phone" :label="$t({ 'zh-CN': '电话', 'en-US': 'Phone' })" />
      <el-table-column prop="address" :label="$t({ 'zh-CN': '地址', 'en-US': 'Address' })" />
      <el-table-column prop="isdefault" :label="$t({ 'zh-CN': '默认', 'en-US': 'Default' })" />
      <el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="220">
        <template slot-scope="{ row }">
          <el-button size="mini" @click="edit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
          <el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialog" :title="form.id ? $t({ 'zh-CN': '编辑地址', 'en-US': 'Edit address' }) : $t({ 'zh-CN': '新增地址', 'en-US': 'Add address' })">
      <el-form label-width="100px">
        <el-form-item :label="$t({ 'zh-CN': '收货人', 'en-US': 'Name' })"><el-input v-model="form.name" /></el-form-item>
        <el-form-item :label="$t({ 'zh-CN': '电话', 'en-US': 'Phone' })"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item :label="$t({ 'zh-CN': '地址', 'en-US': 'Address' })"><el-input v-model="form.address" /></el-form-item>
        <el-form-item :label="$t({ 'zh-CN': '默认', 'en-US': 'Default' })">
          <el-select v-model="form.isdefault">
            <el-option value="是" :label="$t({ 'zh-CN': '是', 'en-US': 'Yes' })" />
            <el-option value="否" :label="$t({ 'zh-CN': '否', 'en-US': 'No' })" />
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="dialog = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
        <el-button type="primary" @click="save">{{ $t({ 'zh-CN': '保存', 'en-US': 'Save' }) }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
      dialog: false,
      form: { id: null, name: '', phone: '', address: '', isdefault: '否' },
    }
  },
  created() {
    this.load()
  },
  methods: {
    async load() {
      const res = await this.$api.get('/address/list', { params: { page: 1, limit: 100 } })
      this.list = (res.data.data && res.data.data.list) || []
    },
    edit(row) {
      this.form = Object.assign({}, row)
      this.dialog = true
    },
    async save() {
      const url = this.form.id ? '/address/update' : '/address/save'
      const payload = Object.assign({ userid: Number(localStorage.getItem('frontUserid')) }, this.form)
      const res = await this.$api.post(url, payload)
      if (res.data.code === 0) {
        this.dialog = false
        this.form = { id: null, name: '', phone: '', address: '', isdefault: '否' }
        await this.load()
        this.$message.success(this.$t({ 'zh-CN': '地址已保存', 'en-US': 'Address saved.' }))
      }
    },
    async remove(row) {
      await this.$confirm(
        this.$t({ 'zh-CN': '确认删除这条地址吗？', 'en-US': 'Delete this address?' }),
        this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
        { type: 'warning' }
      )
      const res = await this.$api.post('/address/delete', [row.id])
      if (res.data.code === 0) {
        await this.load()
        this.$message.success(this.$t({ 'zh-CN': '地址已删除', 'en-US': 'Address deleted.' }))
      } else {
        this.$message.error((res.data && res.data.msg) || this.$t({ 'zh-CN': '删除失败', 'en-US': 'Delete failed.' }))
      }
    },
  },
}
</script>

<style scoped>
.toolbar{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);margin-bottom:20px;display:flex;justify-content:space-between;align-items:center}
.toolbar h1{margin:0;color:#163246}
</style>
