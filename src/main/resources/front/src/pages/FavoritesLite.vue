<template>
  <div class="page">
    <div class="toolbar">
      <h1>{{ $t({ 'zh-CN': '我的收藏', 'en-US': 'Favorites' }) }}</h1>
    </div>
    <el-table :data="list" border>
      <el-table-column prop="name" :label="$t({ 'zh-CN': '名称', 'en-US': 'Name' })" />
      <el-table-column prop="tablename" :label="$t({ 'zh-CN': '来源模块', 'en-US': 'Module' })" />
      <el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="240">
        <template slot-scope="{ row }">
          <el-button size="mini" type="primary" @click="view(row)">{{ $t({ 'zh-CN': '查看', 'en-US': 'View' }) }}</el-button>
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
  created() {
    this.load()
  },
  methods: {
    view(row) {
      if (row.tablename === 'forum') {
        this.$router.push({ path: '/forumDetail', query: { id: row.refid } })
        return
      }
      if (row.tablename === 'news') {
        this.$router.push(`/news/${row.refid}`)
        return
      }
      if (row.tablename === 'siyangjiaoxue') {
        this.$router.push(`/teachings/${row.refid}`)
        return
      }
      if (row.tablename === 'guanshangyubaike') {
        this.$router.push({ path: '/encyclopedia', query: { itemId: String(row.refid) } })
        return
      }
      if (row.tablename === 'shuizuguanxinxi') {
        this.$router.push({ path: '/aquariums', query: { itemId: String(row.refid) } })
        return
      }
      if (row.tablename === 'xianshangshangcheng') {
        this.$router.push({ path: '/shop', query: { productId: String(row.refid) } })
        return
      }
      this.$message.warning(this.$t({ 'zh-CN': '当前收藏项暂未接入查看入口', 'en-US': 'View is not available for this favorite yet' }))
    },
    async load() {
      const res = await this.$api.get('/storeup/list', {
        params: {
          page: 1,
          limit: 100,
          userid: Number(localStorage.getItem('frontUserid')),
          type: '1',
        },
      })
      this.list = (res.data.data && res.data.data.list) || []
    },
    async remove(row) {
      const res = await this.$api.post('/storeup/delete', [row.id])
      if (res.data.code === 0) {
        this.load()
      }
    },
  },
}
</script>

<style scoped>
.toolbar{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);margin-bottom:20px}
.toolbar h1{margin:0;color:#163246}
</style>
