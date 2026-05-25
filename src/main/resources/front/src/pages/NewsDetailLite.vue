<template>
  <div class="detail" v-if="detail">
    <div class="detail-head">
      <el-button type="text" @click="$router.back()">{{ $t({ 'zh-CN': '返回', 'en-US': 'Back' }) }}</el-button>
      <el-button size="mini" :type="isFavorite ? 'success' : 'default'" @click="toggleFavorite">
        {{ isFavorite ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
      </el-button>
    </div>
    <h1>{{ detail.title }}</h1>
    <div class="sub">{{ $fishLabel(detail.pinzhongfenlei || 'general') }} · {{ detail.name }}</div>
    <img class="hero" :src="$assetUrl(firstImage(detail.picture))" />
    <div class="content ql-snow ql-editor" v-html="detail.content"></div>
  </div>
</template>

<script>
export default {
  props: ['id'],
  data() {
    return {
      detail: null,
      favoriteRecord: null,
    }
  },
  created() {
    this.loadDetail()
  },
  computed: {
    userid() {
      return Number(localStorage.getItem('frontUserid') || 0)
    },
    isFavorite() {
      return !!this.favoriteRecord
    },
  },
  watch: {
    id() {
      this.loadDetail()
    },
  },
  methods: {
    firstImage(value) {
      return value ? value.split(',')[0] : ''
    },
    async loadDetail() {
      const res = await this.$api.get(`/news/detail/${this.id}`)
      this.detail = res.data.data
      await this.loadFavoriteRecord()
    },
    async loadFavoriteRecord() {
      if (!this.userid || !this.detail) {
        this.favoriteRecord = null
        return
      }
      const res = await this.$api.get('/storeup/list', {
        params: {
          page: 1,
          limit: 1,
          userid: this.userid,
          type: '1',
          tablename: 'news',
          refid: this.detail.id,
        },
      })
      const list = (res.data.data && res.data.data.list) || []
      this.favoriteRecord = list[0] || null
    },
    async toggleFavorite() {
      if (!this.userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录后再收藏咨询内容', 'en-US': 'Please log in before favoriting news' }))
        this.$router.push('/login')
        return
      }
      if (this.favoriteRecord) {
        const res = await this.$api.post('/storeup/delete', [this.favoriteRecord.id])
        if (res.data.code === 0) {
          this.favoriteRecord = null
          this.$message.success(this.$t({ 'zh-CN': '已取消收藏', 'en-US': 'Favorite removed' }))
        }
        return
      }
      const payload = {
        name: this.detail.title,
        picture: this.firstImage(this.detail.picture),
        refid: this.detail.id,
        tablename: 'news',
        userid: this.userid,
        type: '1',
        inteltype: this.detail.pinzhongfenlei || '',
      }
      const res = await this.$api.post('/storeup/add', payload)
      if (res.data.code === 0) {
        await this.loadFavoriteRecord()
        this.$message.success(this.$t({ 'zh-CN': '收藏成功', 'en-US': 'Favorited' }))
      }
    },
  },
}
</script>

<style scoped>
.detail {
  background: #fff;
  padding: 28px;
  border-radius: 24px;
  border: 1px solid rgba(15, 111, 168, 0.08);
  box-shadow: 0 20px 42px rgba(15, 111, 168, 0.08);
}
.detail-head {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
}
.detail h1 {
  margin: 0;
  color: #163246;
  line-height: 1.2;
}
.sub {
  margin: 10px 0 16px;
  color: #0f6fa8;
}
.hero {
  width: 100%;
  max-height: 360px;
  object-fit: cover;
  border-radius: 18px;
  margin-bottom: 20px;
}
</style>
