<template>
  <div class="page">
    <div class="toolbar">
      <h1>{{ $t({ 'zh-CN': '咨询列表', 'en-US': 'News' }) }}</h1>
      <el-input
        v-model="keyword"
        :placeholder="$t({ 'zh-CN': '按标题搜索', 'en-US': 'Search title' })"
        class="search"
        @keyup.enter.native="loadNews(1)"
      />
      <el-button type="primary" @click="loadNews(1)">{{ $t({ 'zh-CN': '搜索', 'en-US': 'Search' }) }}</el-button>
    </div>
    <div class="list">
      <div class="item" v-for="item in newsList" :key="item.id" @click="$router.push(`/news/${item.id}`)">
        <img :src="$assetUrl(firstImage(item.picture))" />
        <div class="meta">
          <div class="head">
            <div class="title">{{ item.title }}</div>
            <el-button
              size="mini"
              :type="isFavorite(item.id) ? 'success' : 'default'"
              @click.stop="toggleFavorite(item)"
            >
              {{ isFavorite(item.id) ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
            </el-button>
          </div>
          <div class="sub">
            <span>{{ $fishLabel(item.pinzhongfenlei || 'general') }}</span>
            <span>{{ $t({ 'zh-CN': '发布人', 'en-US': 'Publisher' }) }}: {{ item.name }}</span>
          </div>
          <p>{{ item.introduction }}</p>
        </div>
      </div>
    </div>
    <el-pagination
      background
      layout="total, prev, pager, next"
      :total="total"
      :page-size="pageSize"
      @current-change="loadNews"
    />
  </div>
</template>

<script>
export default {
  data() {
    return {
      keyword: '',
      newsList: [],
      total: 0,
      pageSize: 10,
      favoriteMap: {},
    }
  },
  async created() {
    await this.loadNews(1)
    await this.loadFavorites()
  },
  computed: {
    userid() {
      return Number(localStorage.getItem('frontUserid') || 0)
    },
  },
  methods: {
    firstImage(value) {
      return value ? value.split(',')[0] : ''
    },
    isFavorite(id) {
      return !!this.favoriteMap[id]
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
          tablename: 'news',
        },
      })
      const list = (res.data.data && res.data.data.list) || []
      this.favoriteMap = list.reduce((map, item) => {
        map[item.refid] = item
        return map
      }, {})
    },
    async loadNews(page) {
      const params = {
        page,
        limit: this.pageSize,
        sort: 'addtime',
        order: 'desc',
      }
      if (this.keyword) {
        params.title = `%${this.keyword}%`
      }
      const res = await this.$api.get('/news/list', { params })
      const data = res.data.data || {}
      this.newsList = data.list || []
      this.total = data.total || 0
    },
    async toggleFavorite(item) {
      if (!this.userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录后再收藏咨询内容', 'en-US': 'Please log in before favoriting news' }))
        this.$router.push('/login')
        return
      }
      if (this.isFavorite(item.id)) {
        const record = this.favoriteMap[item.id]
        const res = await this.$api.post('/storeup/delete', [record.id])
        if (res.data.code === 0) {
          await this.loadFavorites()
          this.$message.success(this.$t({ 'zh-CN': '已取消收藏', 'en-US': 'Favorite removed' }))
        }
        return
      }
      const payload = {
        name: item.title,
        picture: this.firstImage(item.picture),
        refid: item.id,
        tablename: 'news',
        userid: this.userid,
        type: '1',
        inteltype: item.pinzhongfenlei || '',
      }
      const res = await this.$api.post('/storeup/add', payload)
      if (res.data.code === 0) {
        await this.loadFavorites()
        this.$message.success(this.$t({ 'zh-CN': '收藏成功', 'en-US': 'Favorited' }))
      }
    },
  },
}
</script>

<style scoped>
.toolbar {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
  padding: 20px 22px;
  border-radius: 22px;
  background: #fff;
  box-shadow: 0 18px 35px rgba(15, 111, 168, 0.08);
}
.toolbar h1 {
  margin: 0 auto 0 0;
  color: #163246;
}
.search {
  width: 280px;
}
.list {
  display: flex;
  flex-direction: column;
  gap: 16px;
  margin-bottom: 20px;
}
.item {
  display: flex;
  gap: 16px;
  padding: 16px;
  background: #fff;
  border-radius: 20px;
  box-shadow: 0 12px 28px rgba(15, 111, 168, 0.08);
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}
.item:hover {
  transform: translateY(-4px);
  box-shadow: 0 18px 36px rgba(15, 111, 168, 0.14);
}
.item img {
  width: 140px;
  height: 100px;
  object-fit: cover;
  border-radius: 12px;
}
.meta {
  flex: 1;
}
.head {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 12px;
}
.title {
  font-size: 18px;
  font-weight: 700;
  color: #163246;
}
.sub {
  margin-top: 6px;
  color: #0f6fa8;
  font-size: 13px;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}
.meta p {
  color: #667784;
  line-height: 1.7;
}
</style>
