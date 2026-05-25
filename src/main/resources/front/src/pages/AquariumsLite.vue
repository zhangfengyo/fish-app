<template>
  <div class="page">
    <div class="toolbar">
      <h1>{{ $t({ 'zh-CN': '水族馆信息', 'en-US': 'Aquariums' }) }}</h1>
    </div>
    <div class="grid">
      <div class="card" v-for="item in list" :key="item.id" @click="openDetail(item)">
        <div class="image-wrap">
          <img :src="$assetUrl(firstImage(item.changguantupian))" />
          <el-button
            size="mini"
            :type="isFavorite(item.id) ? 'success' : 'default'"
            class="favorite-btn"
            @click.stop="toggleFavorite(item)"
          >
            {{ isFavorite(item.id) ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
          </el-button>
        </div>
        <div class="body">
          <h3>{{ item.changguanmingcheng }}</h3>
          <div class="meta-row">
            <div class="tag">{{ item.changguanfenlei }}</div>
            <div class="storeup-count">{{ $t({ 'zh-CN': '收藏', 'en-US': 'Favorites' }) }} {{ item.storeupnum || 0 }}</div>
          </div>
          <p>{{ item.changguanweizhi }}</p>
          <p>{{ item.kaifangshijian }}</p>
          <p>{{ item.changguanjieshao }}</p>
        </div>
      </div>
    </div>
    <el-dialog
      :visible.sync="dialogVisible"
      :title="(currentItem.changguanmingcheng || '') + ' ' + $t({ 'zh-CN': '详情', 'en-US': 'Details' })"
      width="720px"
      @close="handleDialogClose"
    >
      <div v-if="imageList.length" class="image-grid">
        <img v-for="(img, idx) in imageList" :key="idx" :src="$assetUrl(img)" />
      </div>
      <dl class="info-grid">
        <template v-if="currentItem.changguanbianhao">
          <dt>{{ $t({ 'zh-CN': '场馆编号', 'en-US': 'Venue ID' }) }}</dt>
          <dd>{{ currentItem.changguanbianhao }}</dd>
        </template>
        <template v-if="currentItem.changguanmingcheng">
          <dt>{{ $t({ 'zh-CN': '场馆名称', 'en-US': 'Venue name' }) }}</dt>
          <dd>{{ currentItem.changguanmingcheng }}</dd>
        </template>
        <template v-if="currentItem.changguanfenlei">
          <dt>{{ $t({ 'zh-CN': '场馆分类', 'en-US': 'Category' }) }}</dt>
          <dd>{{ currentItem.changguanfenlei }}</dd>
        </template>
        <template v-if="currentItem.changguanweizhi">
          <dt>{{ $t({ 'zh-CN': '位置', 'en-US': 'Location' }) }}</dt>
          <dd>{{ currentItem.changguanweizhi }}</dd>
        </template>
        <template v-if="currentItem.lianxiren">
          <dt>{{ $t({ 'zh-CN': '联系人', 'en-US': 'Contact' }) }}</dt>
          <dd>{{ currentItem.lianxiren }}</dd>
        </template>
        <template v-if="currentItem.lianxidianhua">
          <dt>{{ $t({ 'zh-CN': '联系电话', 'en-US': 'Phone' }) }}</dt>
          <dd>{{ currentItem.lianxidianhua }}</dd>
        </template>
        <template v-if="currentItem.kaifangshijian">
          <dt>{{ $t({ 'zh-CN': '开放时间', 'en-US': 'Opening hours' }) }}</dt>
          <dd>{{ currentItem.kaifangshijian }}</dd>
        </template>
        <template v-if="currentItem.changguanjieshao">
          <dt>{{ $t({ 'zh-CN': '场馆介绍', 'en-US': 'Introduction' }) }}</dt>
          <dd>{{ currentItem.changguanjieshao }}</dd>
        </template>
      </dl>
      <span slot="footer">
        <el-button
          v-if="currentItem.id"
          :type="isFavorite(currentItem.id) ? 'success' : 'default'"
          @click="toggleFavorite(currentItem)"
        >
          {{ isFavorite(currentItem.id) ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
        </el-button>
        <el-button @click="dialogVisible = false">{{ $t({ 'zh-CN': '关闭', 'en-US': 'Close' }) }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
      dialogVisible: false,
      currentItem: {},
      favoriteMap: {},
    }
  },
  async created() {
    await this.loadList()
    await this.loadFavorites()
    await this.openDetailFromRoute()
  },
  computed: {
    userid() {
      return Number(localStorage.getItem('frontUserid') || 0)
    },
    imageList() {
      const value = this.currentItem && this.currentItem.changguantupian
      if (!value) return []
      return String(value).split(',').map(item => item.trim()).filter(Boolean)
    },
  },
  watch: {
    '$route.query.itemId': {
      async handler(value) {
        if (value) {
          await this.openDetailById(value)
        }
      },
    },
  },
  methods: {
    firstImage(value) {
      return value ? value.split(',')[0] : ''
    },
    async loadList() {
      const res = await this.$api.get('/shuizuguanxinxi/list', { params: { page: 1, limit: 20 } })
      this.list = (res.data.data && res.data.data.list) || []
    },
    async loadFavorites() {
      if (!this.userid) {
        this.favoriteMap = {}
        return
      }
      const res = await this.$api.get('/storeup/list', {
        params: {
          page: 1,
          limit: 200,
          userid: this.userid,
          type: '1',
          tablename: 'shuizuguanxinxi',
        },
      })
      const list = (res.data.data && res.data.data.list) || []
      this.favoriteMap = list.reduce((map, item) => {
        map[item.refid] = item
        return map
      }, {})
    },
    isFavorite(id) {
      return !!this.favoriteMap[id]
    },
    openDetail(item) {
      this.currentItem = Object.assign({}, item || {})
      this.dialogVisible = true
    },
    async openDetailFromRoute() {
      if (this.$route.query.itemId) {
        await this.openDetailById(this.$route.query.itemId)
      }
    },
    async openDetailById(id) {
      const res = await this.$api.get(`/shuizuguanxinxi/detail/${id}`)
      this.currentItem = (res.data && res.data.data) || {}
      this.dialogVisible = true
    },
    handleDialogClose() {
      const nextQuery = Object.assign({}, this.$route.query)
      delete nextQuery.itemId
      this.$router.replace({ path: this.$route.path, query: nextQuery }).catch(() => {})
    },
    async toggleFavorite(item) {
      if (!item || !item.id) {
        return
      }
      if (!this.userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录后再收藏水族馆内容', 'en-US': 'Please log in before favoriting aquariums' }))
        this.$router.push('/login')
        return
      }
      if (this.isFavorite(item.id)) {
        const record = this.favoriteMap[item.id]
        const res = await this.$api.post('/storeup/delete', [record.id])
        if (res.data.code === 0) {
          await this.syncStoreupCount(item.id, -1)
          await this.loadFavorites()
          this.$message.success(this.$t({ 'zh-CN': '已取消收藏', 'en-US': 'Favorite removed' }))
        }
        return
      }
      const payload = {
        name: item.changguanmingcheng,
        picture: this.firstImage(item.changguantupian),
        refid: item.id,
        tablename: 'shuizuguanxinxi',
        userid: this.userid,
        type: '1',
        inteltype: item.changguanfenlei || '',
      }
      const res = await this.$api.post('/storeup/add', payload)
      if (res.data.code === 0) {
        await this.syncStoreupCount(item.id, 1)
        await this.loadFavorites()
        this.$message.success(this.$t({ 'zh-CN': '收藏成功', 'en-US': 'Favorited' }))
      }
    },
    async syncStoreupCount(id, delta) {
      const source = this.list.find(item => Number(item.id) === Number(id)) || this.currentItem
      if (!source || !source.id) {
        return
      }
      const nextCount = Math.max(0, Number(source.storeupnum || 0) + delta)
      const payload = Object.assign({}, source, { storeupnum: nextCount })
      await this.$api.post('/shuizuguanxinxi/update', payload)
      const row = this.list.find(item => Number(item.id) === Number(id))
      if (row) {
        row.storeupnum = nextCount
      }
      if (this.currentItem && Number(this.currentItem.id) === Number(id)) {
        this.currentItem = Object.assign({}, this.currentItem, { storeupnum: nextCount })
      }
    },
  },
}
</script>

<style scoped>
.toolbar{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);margin-bottom:20px}
.toolbar h1{margin:0;color:#163246}
.grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(260px,1fr));gap:18px}
.card{background:#fff;border-radius:20px;overflow:hidden;box-shadow:0 16px 34px rgba(15,111,168,.08);transition:transform .2s;cursor:pointer}
.card:hover{transform:translateY(-3px)}
.image-wrap{position:relative}
.favorite-btn{position:absolute;top:12px;right:12px}
img{width:100%;height:180px;object-fit:cover}
.body{padding:16px}
.meta-row{display:flex;justify-content:space-between;align-items:center;gap:10px}
.tag{display:inline-block;padding:4px 10px;border-radius:999px;background:#e8f5fb;color:#0f6fa8;font-size:12px;margin-bottom:10px}
.storeup-count{color:#64748b;font-size:12px}
.body h3{margin:0 0 8px}
.body p{margin:6px 0;color:#667784;line-height:1.7}
.info-grid{display:grid;grid-template-columns:1fr 1fr;gap:10px 20px}
.info-grid dt{color:#7b8a97;font-size:13px}
.info-grid dd{margin:0 0 6px;color:#163246}
.image-grid{display:flex;flex-wrap:wrap;gap:10px;margin-bottom:14px}
.image-grid img{width:200px;height:140px;border-radius:14px;object-fit:cover}
</style>
