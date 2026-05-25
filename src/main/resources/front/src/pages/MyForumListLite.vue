<template>
  <div class="page">
    <div class="toolbar">
      <el-button @click="$router.push('/profile')">{{ $t({ 'zh-CN': '返回个人中心', 'en-US': 'Back to profile' }) }}</el-button>
      <h1>{{ $t({ 'zh-CN': '我的帖子', 'en-US': 'My posts' }) }}</h1>
      <div></div>
    </div>
    <el-table :data="list" border>
      <el-table-column prop="title" :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })" min-width="220" />
      <el-table-column prop="addtime" :label="$t({ 'zh-CN': '发布时间', 'en-US': 'Published' })" width="180" />
      <el-table-column prop="istop" :label="$t({ 'zh-CN': '置顶', 'en-US': 'Top' })" width="80">
        <template slot-scope="{ row }">{{ row.istop == 1 ? $t({ 'zh-CN': '是', 'en-US': 'Yes' }) : $t({ 'zh-CN': '否', 'en-US': 'No' }) }}</template>
      </el-table-column>
      <el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="260">
        <template slot-scope="{ row }">
          <el-button size="mini" type="primary" @click="$router.push({ path: '/forumDetail', query: { id: row.id } })">{{ $t({ 'zh-CN': '查看', 'en-US': 'View' }) }}</el-button>
          <el-button size="mini" @click="$router.push({ path: '/forumAdd', query: { id: row.id } })">{{ $t({ 'zh-CN': '编辑', 'en-US': 'Edit' }) }}</el-button>
          <el-button size="mini" type="danger" @click="remove(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
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
  async created() {
    await this.load()
  },
  methods: {
    async load() {
      const res = await this.$api.get('/forum/page', {
        params: {
          page: 1,
          limit: 100,
          parentid: 0,
          sort: 'istop,toptime',
          order: 'desc,desc',
        },
      })
      const data = (res.data.data && res.data.data.list) || []
      const userid = Number(localStorage.getItem('frontUserid') || 0)
      this.list = data.filter(item => Number(item.userid) === userid)
    },
    async remove(row) {
      const res = await this.$api.post('/forum/delete', [row.id])
      if (res.data.code === 0) {
        this.$message.success(this.$t({ 'zh-CN': '删除成功', 'en-US': 'Deleted' }))
        await this.load()
      }
    },
  },
}
</script>

<style scoped>
.page{display:grid;gap:18px}
.toolbar{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);display:flex;align-items:center;justify-content:space-between;gap:12px}
.toolbar h1{margin:0;flex:1;text-align:center;color:#163246}
</style>
