<template>
  <div class="page">
    <div class="toolbar">
      <div>
        <h1>{{ $t({ 'zh-CN': '我的喂鱼提醒', 'en-US': 'My Feed Reminders' }) }}</h1>
        <p>{{ $t({ 'zh-CN': '这里的提醒由用户自己创建、编辑和维护，不是管理员代为设置。', 'en-US': 'These reminders are created and managed by the user.' }) }}</p>
      </div>
      <el-button type="primary" @click="openCreate">{{ $t({ 'zh-CN': '新增提醒', 'en-US': 'Add Reminder' }) }}</el-button>
    </div>

    <div class="actions">
      <el-input v-model="keyword" clearable :placeholder="$t({ 'zh-CN': '搜索标题或内容', 'en-US': 'Search title or content' })" class="search-box" />
      <el-button @click="loadList">{{ $t({ 'zh-CN': '刷新', 'en-US': 'Refresh' }) }}</el-button>
    </div>

    <el-table :data="filteredList" border v-loading="loading">
      <el-table-column prop="tixingbiaoti" :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })" min-width="180" />
      <el-table-column prop="tixingriqi" :label="$t({ 'zh-CN': '提醒日期', 'en-US': 'Date' })" width="140" />
      <el-table-column prop="tixingneirong" :label="$t({ 'zh-CN': '提醒内容', 'en-US': 'Content' })" min-width="280">
        <template slot-scope="{ row }">
          <div class="content-cell">{{ row.tixingneirong }}</div>
        </template>
      </el-table-column>
      <el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="180">
        <template slot-scope="{ row }">
          <el-button size="mini" @click="openEdit(row)">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
          <el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="form.id ? $t({ 'zh-CN': '编辑提醒', 'en-US': 'Edit Reminder' }) : $t({ 'zh-CN': '新增提醒', 'en-US': 'Add Reminder' })" :visible.sync="dialogVisible" width="680px">
      <el-form label-width="110px">
        <el-form-item :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })">
          <el-input v-model="form.tixingbiaoti" />
        </el-form-item>
        <el-form-item :label="$t({ 'zh-CN': '提醒日期', 'en-US': 'Date' })">
          <el-date-picker v-model="form.tixingriqi" type="date" value-format="yyyy-MM-dd" format="yyyy-MM-dd" :placeholder="$t({ 'zh-CN': '请选择日期', 'en-US': 'Choose date' })" />
        </el-form-item>
        <el-form-item :label="$t({ 'zh-CN': '提醒内容', 'en-US': 'Content' })">
          <el-input v-model="form.tixingneirong" type="textarea" :rows="5" />
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
      loading: false,
      saving: false,
      dialogVisible: false,
      keyword: '',
      list: [],
      form: this.createEmptyForm(),
    }
  },
  computed: {
    filteredList() {
      if (!this.keyword) return this.list
      return this.list.filter(item =>
        (item.tixingbiaoti || '').includes(this.keyword) ||
        (item.tixingneirong || '').includes(this.keyword)
      )
    },
  },
  created() {
    this.loadList()
  },
  methods: {
    createEmptyForm() {
      return {
        id: null,
        tixingbiaoti: '',
        tixingriqi: '',
        tixingneirong: '',
      }
    },
    async loadList() {
      this.loading = true
      try {
        const res = await this.$api.get('/weiyutixing/page', {
          params: {
            page: 1,
            limit: 100,
            sort: 'addtime',
            order: 'desc',
          },
        })
        this.list = (res.data.data && res.data.data.list) || []
      } finally {
        this.loading = false
      }
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
      if (!this.form.tixingbiaoti || !this.form.tixingriqi || !this.form.tixingneirong) {
        this.$message.error(this.$t({ 'zh-CN': '标题、日期和内容都不能为空', 'en-US': 'Title, date, and content are required' }))
        return
      }
      this.saving = true
      try {
        const payload = Object.assign({}, this.form)
        const url = payload.id ? '/weiyutixing/update' : '/weiyutixing/add'
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
        this.$t({ 'zh-CN': `确认删除“${row.tixingbiaoti}”吗？`, 'en-US': `Delete "${row.tixingbiaoti}"?` }),
        this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
        { type: 'warning' }
      )
      const res = await this.$api.post('/weiyutixing/delete', [row.id])
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
.toolbar h1 { margin: 0; color: #163246; }
.toolbar p { margin: 6px 0 0; color: #6c7f8d; font-size: 13px; }
.actions { display:flex; gap:12px; margin-bottom:16px; align-items:center; }
.search-box { max-width: 320px; }
.content-cell { white-space: pre-wrap; line-height: 1.7; color:#365062; }
</style>
