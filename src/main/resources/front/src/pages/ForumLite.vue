<template>
  <div class="page">
    <div class="toolbar">
      <h1>{{ $t({ 'zh-CN': '论坛', 'en-US': 'Forum' }) }}</h1>
    </div>
    <div class="list">
      <div class="card" v-for="item in list" :key="item.id">
        <h3>{{ item.title }}</h3>
        <div class="sub">{{ item.username }}</div>
        <p>{{ item.content }}</p>
        <div class="actions">
          <el-button size="mini" type="primary" @click="viewPost(item)">{{ $t({ 'zh-CN': '查看', 'en-US': 'View' }) }}</el-button>
          <el-button size="mini" :type="isCollected(item) ? 'success' : 'default'" @click="collectPost(item)">
            {{ isCollected(item) ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
      favorites: [],
    }
  },
  async created() {
    await this.load()
    await this.loadFavorites()
  },
  methods: {
    async loadFavorites() {
      const userid = Number(localStorage.getItem('frontUserid'))
      if (!userid) {
        this.favorites = []
        return
      }
      const res = await this.$api.get('/storeup/list', {
        params: { page: 1, limit: 200, userid, type: '1' },
      })
      const list = (res.data.data && res.data.data.list) || []
      this.favorites = list.filter(item => item.tablename === 'forum')
    },
    isCollected(item) {
      return this.favorites.some(favorite => Number(favorite.refid) === Number(item.id))
    },
    viewPost(item) {
      this.$router.push({ path: '/forumDetail', query: { id: item.id } })
    },
    async collectPost(item) {
      const userid = Number(localStorage.getItem('frontUserid'))
      if (!userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录再收藏帖子', 'en-US': 'Please log in before favoriting posts' }))
        this.$router.push('/login')
        return
      }
      if (this.isCollected(item)) {
        this.$router.push('/favorites')
        return
      }
      const payload = {
        name: item.title,
        picture: item.cover ? item.cover.split(',')[0] : '',
        refid: item.id,
        tablename: 'forum',
        userid,
        type: '1',
      }
      const res = await this.$api.post('/storeup/add', payload)
      if (res.data.code === 0) {
        this.$message.success(this.$t({ 'zh-CN': '收藏成功，已进入收藏栏目', 'en-US': 'Saved to favorites. Opening favorites now.' }))
        await this.loadFavorites()
        this.$router.push('/favorites')
      } else {
        this.$message.error(res.data.msg || this.$t({ 'zh-CN': '收藏失败', 'en-US': 'Favorite failed' }))
      }
    },
    async load() {
      const res = await this.$api.get('/forum/flist', { params: { page: 1, limit: 100 } })
      this.list = (res.data.data && res.data.data.list) || []
    },
  },
}
</script>

<style scoped>
.toolbar{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);margin-bottom:20px}
.toolbar h1{margin:0;color:#163246}
.list{display:grid;gap:14px}
.card{background:#fff;border-radius:18px;padding:18px;box-shadow:0 16px 34px rgba(15,111,168,.08)}
.card h3{margin:0 0 6px}
.sub{color:#0f6fa8;font-size:13px;margin-bottom:8px}
.card p{margin:0;color:#667784;line-height:1.7}
.actions{display:flex;gap:10px;margin-top:14px}
</style>
