<template>
  <div class="page">
    <div class="toolbar">
      <el-button @click="goBack">{{ $t({ 'zh-CN': '返回', 'en-US': 'Back' }) }}</el-button>
      <h1>{{ isEdit ? $t({ 'zh-CN': '编辑帖子', 'en-US': 'Edit post' }) : $t({ 'zh-CN': '发布帖子', 'en-US': 'Create post' }) }}</h1>
      <div></div>
    </div>
    <div class="panel">
      <el-form label-width="100px">
        <el-form-item :label="$t({ 'zh-CN': '标题', 'en-US': 'Title' })">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item :label="$t({ 'zh-CN': '内容', 'en-US': 'Content' })">
          <el-input v-model="form.content" type="textarea" :rows="8" />
        </el-form-item>
        <el-form-item :label="$t({ 'zh-CN': '是否匿名', 'en-US': 'Anonymous' })">
          <el-switch v-model="anonymous" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="saving" @click="save">{{ $t({ 'zh-CN': '保存', 'en-US': 'Save' }) }}</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      saving: false,
      form: {
        id: null,
        title: '',
        content: '',
        parentid: 0,
        username: localStorage.getItem('frontUsername') || 'User',
        userid: Number(localStorage.getItem('frontUserid') || 0),
        isdone: '开放',
        istop: 0,
        isanon: 0,
        delflag: 0,
        cover: '',
      },
      anonymous: false,
    }
  },
  computed: {
    isEdit() {
      return !!this.$route.query.id
    },
  },
  async created() {
    if (this.isEdit) {
      await this.loadDetail()
    }
  },
  methods: {
    async loadDetail() {
      const res = await this.$api.get(`/forum/detail/${this.$route.query.id}`)
      if (res.data.code === 0) {
        this.form = Object.assign({}, this.form, res.data.data || {})
        this.anonymous = Number(this.form.isanon) === 1
      }
    },
    async save() {
      if (!this.form.title.trim()) {
        this.$message.warning(this.$t({ 'zh-CN': '请输入标题', 'en-US': 'Please enter a title' }))
        return
      }
      if (!this.form.content.trim()) {
        this.$message.warning(this.$t({ 'zh-CN': '请输入内容', 'en-US': 'Please enter content' }))
        return
      }
      this.saving = true
      try {
        const payload = Object.assign({}, this.form, {
          isanon: this.anonymous ? 1 : 0,
        })
        const url = this.isEdit ? '/forum/update' : '/forum/add'
        const res = await this.$api.post(url, payload)
        if (res.data.code === 0) {
          this.$message.success(this.$t({ 'zh-CN': '保存成功', 'en-US': 'Saved' }))
          this.goBack()
        } else {
          this.$message.error(res.data.msg || this.$t({ 'zh-CN': '保存失败', 'en-US': 'Save failed' }))
        }
      } finally {
        this.saving = false
      }
    },
    goBack() {
      if (this.isEdit) {
        this.$router.push('/myForumList')
      } else {
        this.$router.push('/forum')
      }
    },
  },
}
</script>

<style scoped>
.page{display:grid;gap:18px}
.toolbar,.panel{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08)}
.toolbar{display:flex;align-items:center;justify-content:space-between;gap:12px}
.toolbar h1{margin:0;flex:1;text-align:center;color:#163246}
</style>
