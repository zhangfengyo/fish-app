<template>
  <div class="page">
    <div class="toolbar">
      <el-button @click="$router.push('/forum')">{{ $t({ 'zh-CN': '返回论坛', 'en-US': 'Back to forum' }) }}</el-button>
      <h1>{{ detail.title || $t({ 'zh-CN': '帖子详情', 'en-US': 'Post detail' }) }}</h1>
      <el-button size="mini" :type="isCollected ? 'success' : 'default'" @click="toggleFavorite">
        {{ isCollected ? $t({ 'zh-CN': '已收藏', 'en-US': 'Collected' }) : $t({ 'zh-CN': '收藏', 'en-US': 'Favorite' }) }}
      </el-button>
    </div>

    <div class="article" v-loading="loading">
      <div class="meta">
        <span>{{ $t({ 'zh-CN': '发布人', 'en-US': 'Author' }) }}: {{ displayAuthor }}</span>
        <span v-if="detail.addtime">{{ $t({ 'zh-CN': '发布时间', 'en-US': 'Published' }) }}: {{ detail.addtime }}</span>
      </div>
      <div class="content ql-snow ql-editor" v-html="detail.content || emptyContent"></div>
    </div>

    <div class="comments">
      <div class="comments-head">
        <h2>{{ $t({ 'zh-CN': '评论', 'en-US': 'Comments' }) }}</h2>
        <el-button type="primary" size="mini" @click="commentDialog = true">{{ $t({ 'zh-CN': '发表评论', 'en-US': 'Add comment' }) }}</el-button>
      </div>

      <div v-if="comments.length" class="comment-list">
        <div class="comment-card" v-for="item in comments" :key="item.id">
          <div class="comment-author">{{ item.username || 'User' }}</div>
          <div class="comment-content ql-snow ql-editor" v-html="item.content"></div>
          <div v-if="item.childs && item.childs.length" class="reply-list">
            <div class="reply-card" v-for="child in item.childs" :key="child.id">
              <div class="comment-author">{{ child.username || 'User' }}</div>
              <div class="comment-content ql-snow ql-editor" v-html="child.content"></div>
            </div>
          </div>
        </div>
      </div>
      <el-empty v-else :description="$t({ 'zh-CN': '暂时没有评论', 'en-US': 'No comments yet' })" />
    </div>

    <el-dialog :visible.sync="commentDialog" :title="$t({ 'zh-CN': '发表评论', 'en-US': 'Add comment' })" width="640px">
      <el-form label-width="90px">
        <el-form-item :label="$t({ 'zh-CN': '评论内容', 'en-US': 'Comment' })">
          <el-input v-model="commentContent" type="textarea" :rows="6" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="commentDialog = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
        <el-button type="primary" :loading="commentSaving" @click="submitComment">{{ $t({ 'zh-CN': '提交', 'en-US': 'Submit' }) }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: false,
      detail: {},
      comments: [],
      favoriteRecord: null,
      commentDialog: false,
      commentSaving: false,
      commentContent: '',
    }
  },
  computed: {
    postId() {
      return Number(this.$route.query.id || 0)
    },
    userid() {
      return Number(localStorage.getItem('frontUserid') || 0)
    },
    displayAuthor() {
      if (!this.detail) return ''
      return this.detail.isanon == 1 && this.detail.userid != this.userid ? this.$t({ 'zh-CN': '匿名', 'en-US': 'Anonymous' }) : (this.detail.username || 'User')
    },
    isCollected() {
      return !!this.favoriteRecord
    },
    emptyContent() {
      return `<p>${this.$t({ 'zh-CN': '帖子内容为空', 'en-US': 'No content' })}</p>`
    },
  },
  async created() {
    await this.loadDetail()
    await this.loadFavoriteRecord()
  },
  methods: {
    async loadDetail() {
      if (!this.postId) return
      this.loading = true
      try {
        const res = await this.$api.get(`/forum/list/${this.postId}`)
        if (res.data.code === 0) {
          const data = res.data.data || {}
          this.detail = data
          this.comments = data.childs || []
        } else {
          this.$message.error(res.data.msg || this.$t({ 'zh-CN': '帖子加载失败', 'en-US': 'Failed to load post' }))
        }
      } finally {
        this.loading = false
      }
    },
    async loadFavoriteRecord() {
      if (!this.userid || !this.postId) {
        this.favoriteRecord = null
        return
      }
      const res = await this.$api.get('/storeup/list', {
        params: {
          page: 1,
          limit: 1,
          userid: this.userid,
          type: '1',
          tablename: 'forum',
          refid: this.postId,
        },
      })
      const list = (res.data.data && res.data.data.list) || []
      this.favoriteRecord = list[0] || null
    },
    async toggleFavorite() {
      if (!this.userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录再收藏帖子', 'en-US': 'Please log in before favoriting posts' }))
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
        picture: this.detail.cover ? this.detail.cover.split(',')[0] : '',
        refid: this.postId,
        tablename: 'forum',
        userid: this.userid,
        type: '1',
      }
      const res = await this.$api.post('/storeup/add', payload)
      if (res.data.code === 0) {
        await this.loadFavoriteRecord()
        this.$message.success(this.$t({ 'zh-CN': '收藏成功', 'en-US': 'Favorited' }))
      } else {
        this.$message.error(res.data.msg || this.$t({ 'zh-CN': '收藏失败', 'en-US': 'Favorite failed' }))
      }
    },
    async submitComment() {
      if (!this.commentContent.trim()) {
        this.$message.warning(this.$t({ 'zh-CN': '请输入评论内容', 'en-US': 'Please enter a comment' }))
        return
      }
      if (!this.userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录再评论', 'en-US': 'Please log in before commenting' }))
        this.$router.push('/login')
        return
      }
      this.commentSaving = true
      try {
        const payload = {
          content: this.commentContent,
          parentid: this.postId,
          userid: this.userid,
          username: localStorage.getItem('frontUsername') || 'User',
          avatarurl: localStorage.getItem('frontHeadportrait') || '',
          isanon: 0,
        }
        const res = await this.$api.post('/forum/add', payload)
        if (res.data.code === 0) {
          this.commentContent = ''
          this.commentDialog = false
          await this.loadDetail()
          this.$message.success(this.$t({ 'zh-CN': '评论成功', 'en-US': 'Comment added' }))
        } else {
          this.$message.error(res.data.msg || this.$t({ 'zh-CN': '评论失败', 'en-US': 'Comment failed' }))
        }
      } finally {
        this.commentSaving = false
      }
    },
  },
}
</script>

<style scoped>
.page{display:grid;gap:18px}
.toolbar,.article,.comments{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08)}
.toolbar{display:flex;align-items:center;justify-content:space-between;gap:12px}
.toolbar h1{margin:0;flex:1;color:#163246}
.meta{display:flex;gap:16px;flex-wrap:wrap;color:#0f6fa8;font-size:13px;margin-bottom:16px}
.content{color:#667784;line-height:1.9;word-break:break-word}
.comments-head{display:flex;align-items:center;justify-content:space-between;gap:12px;margin-bottom:14px}
.comments-head h2{margin:0;color:#163246}
.comment-list{display:grid;gap:14px}
.comment-card,.reply-card{padding:14px;border-radius:16px;background:#f8fcff;border:1px solid rgba(15,111,168,.08)}
.reply-list{display:grid;gap:10px;margin-top:12px}
.comment-author{font-weight:700;color:#163246;margin-bottom:8px}
.comment-content{color:#667784;line-height:1.8;word-break:break-word}
</style>
