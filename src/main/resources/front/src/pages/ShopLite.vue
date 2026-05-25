<template>
  <div class="page">
    <div class="toolbar">
      <h1>{{ $t({ 'zh-CN': '商城', 'en-US': 'Shop' }) }}</h1>
    </div>
    <div class="grid">
      <div class="card" v-for="item in list" :key="item.id" @click="openDetail(item)">
        <img :src="resolveImage(firstImage(item.shangpintupian))" />
        <div class="body">
          <h3>{{ item.shangpinmingcheng }}</h3>
          <div class="tag">{{ item.shangpinfenlei }}</div>
          <p class="price"><span class="currency">￥</span>{{ item.price }}</p>
          <el-button
            type="primary"
            size="mini"
            class="add-cart-btn"
            data-testid="add-to-cart-btn"
            aria-label="add-to-cart"
            @click.stop="addCart(item)"
          >
            {{ $t({ 'zh-CN': '加入购物车', 'en-US': 'Add to cart' }) }}
          </el-button>
        </div>
      </div>
    </div>

    <el-dialog
      :visible.sync="detailDialogVisible"
      width="72%"
      append-to-body
      custom-class="shop-detail-modal"
      @close="handleDetailDialogClose"
    >
      <span slot="title">
        {{
          detailDialogItem && detailDialogItem.shangpinmingcheng
            ? detailDialogItem.shangpinmingcheng
            : $t({ 'zh-CN': '商品详情', 'en-US': 'Product details' })
        }}
      </span>
      <div class="detail-shell" v-loading="detailDialogLoading">
        <template v-if="detailDialogItem && detailDialogItem.id">
          <div class="detail-top">
            <div class="detail-gallery">
              <el-carousel
                v-if="detailImages.length > 1"
                height="320px"
                :autoplay="false"
                indicator-position="inside"
              >
                <el-carousel-item v-for="(image, index) in detailImages" :key="index">
                  <img class="detail-image" :src="resolveImage(image)" />
                </el-carousel-item>
              </el-carousel>
              <img
                v-else-if="detailImages.length"
                class="detail-image"
                :src="resolveImage(detailImages[0])"
              />
              <div v-else class="detail-image detail-image-empty">
                {{ $t({ 'zh-CN': '暂无图片', 'en-US': 'No Image' }) }}
              </div>
            </div>

            <div class="detail-info">
              <div class="detail-price"><span class="currency">￥</span>{{ detailDialogItem.price || 0 }}</div>
              <div class="detail-meta">
                <div class="meta-item">
                  <span class="label">{{ $t({ 'zh-CN': '分类', 'en-US': 'Category' }) }}</span>
                  <span class="value">{{ detailDialogItem.shangpinfenlei || '-' }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">{{ $t({ 'zh-CN': '品牌', 'en-US': 'Brand' }) }}</span>
                  <span class="value">{{ detailDialogItem.shangpinpinpai || '-' }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">{{ $t({ 'zh-CN': '规格', 'en-US': 'Spec' }) }}</span>
                  <span class="value">{{ detailDialogItem.shangpinguige || '-' }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">{{ $t({ 'zh-CN': '厂家', 'en-US': 'Manufacturer' }) }}</span>
                  <span class="value">{{ detailDialogItem.shengchanchangjia || '-' }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">{{ $t({ 'zh-CN': '单次限购', 'en-US': 'Per-order limit' }) }}</span>
                  <span class="value">{{ detailDialogItem.onelimittimes || 0 }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">{{ $t({ 'zh-CN': '库存', 'en-US': 'Stock' }) }}</span>
                  <span class="value">{{ detailDialogItem.alllimittimes || 0 }}</span>
                </div>
              </div>
              <div class="detail-actions">
                <el-button type="primary" @click="addCart(detailDialogItem)">
                  {{ $t({ 'zh-CN': '加入购物车', 'en-US': 'Add to cart' }) }}
                </el-button>
              </div>
            </div>
          </div>

          <div v-if="detailDialogItem.shangpinxiangqing" class="detail-content ql-snow">
            <div class="ql-editor" v-html="detailDialogItem.shangpinxiangqing"></div>
          </div>
        </template>

        <div v-else-if="!detailDialogLoading" class="detail-empty">
          {{ $t({ 'zh-CN': '暂无详情', 'en-US': 'No details yet' }) }}
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      list: [],
      detailDialogVisible: false,
      detailDialogLoading: false,
      detailDialogItem: null,
      detailImages: [],
    }
  },
  watch: {
    '$route.query.productId': {
      async handler(value) {
        if (value) {
          await this.openDetailById(value)
        }
      },
    },
  },
  async created() {
    await this.loadList()
    await this.openDetailFromRoute()
  },
  methods: {
    async loadList() {
      const res = await this.$api.get('/xianshangshangcheng/list', {
        params: { page: 1, limit: 20, onshelves: 1 },
      })
      this.list = (res.data.data && res.data.data.list) || []
    },
    firstImage(value) {
      return value ? value.split(',')[0] : ''
    },
    resolveImage(value) {
      if (!value) {
        return ''
      }
      return /^https?:\/\//.test(value) ? value : this.$assetUrl(value)
    },
    splitImages(value) {
      if (!value) {
        return []
      }
      if (value.split(',w').length > 1) {
        return [value]
      }
      return value.split(',').map(item => item.trim()).filter(Boolean)
    },
    async openDetail(item) {
      await this.openDetailById(item && item.id)
    },
    async openDetailFromRoute() {
      const productId = this.$route.query.productId
      if (productId) {
        await this.openDetailById(productId)
      }
    },
    async openDetailById(productId) {
      if (!productId) {
        return
      }
      this.detailDialogVisible = true
      this.detailDialogLoading = true
      this.detailDialogItem = null
      this.detailImages = []
      try {
        const res = await this.$api.get(`/xianshangshangcheng/detail/${productId}`)
        this.detailDialogItem = (res.data && res.data.data) || null
        this.detailImages = this.splitImages(this.detailDialogItem && this.detailDialogItem.shangpintupian)
      } catch (e) {
        this.$message.error(this.$t({ 'zh-CN': '加载商品详情失败', 'en-US': 'Failed to load product details' }))
        this.detailDialogVisible = false
      } finally {
        this.detailDialogLoading = false
      }
    },
    handleDetailDialogClose() {
      if (!this.$route.query.productId) {
        return
      }
      const nextQuery = Object.assign({}, this.$route.query)
      delete nextQuery.productId
      delete nextQuery.fromOrderId
      this.$router.replace({ path: this.$route.path, query: nextQuery }).catch(() => {})
    },
    async addCart(item) {
      const userid = Number(localStorage.getItem('frontUserid'))
      if (!userid) {
        this.$message.warning(this.$t({ 'zh-CN': '请先登录后再加入购物车', 'en-US': 'Please log in before adding items to the cart' }))
        this.$router.push('/login')
        return
      }
      const body = {
        tablename: 'xianshangshangcheng',
        userid,
        goodid: item.id,
        goodname: item.shangpinmingcheng,
        picture: this.firstImage(item.shangpintupian),
        buynumber: 1,
        price: item.price,
        goodtype: item.shangpinfenlei,
      }
      const res = await this.$api.post('/cart/save', body)
      if (res.data.code === 0) {
        this.$message.success(this.$t({ 'zh-CN': '已加入购物车', 'en-US': 'Added to cart' }))
      }
    },
  },
}
</script>

<style scoped>
.toolbar{padding:20px 22px;border-radius:22px;background:#fff;box-shadow:0 18px 35px rgba(15,111,168,.08);margin-bottom:20px}
.toolbar h1{margin:0;color:#163246}
.grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(240px,1fr));gap:18px}
.card{background:#fff;border-radius:20px;overflow:hidden;box-shadow:0 16px 34px rgba(15,111,168,.08);cursor:pointer;transition:transform .2s ease,box-shadow .2s ease}
.card:hover{transform:translateY(-4px);box-shadow:0 22px 40px rgba(15,111,168,.14)}
img{width:100%;height:180px;object-fit:cover}
.body{padding:16px}
.tag{display:inline-block;padding:4px 10px;border-radius:999px;background:#e8f5fb;color:#0f6fa8;font-size:12px;margin-bottom:10px}
.price{margin:0;color:#e65100;font-size:18px;font-weight:700}
.currency{margin-right:2px;font-size:14px}
.detail-shell{min-height:220px}
.detail-top{display:flex;gap:24px;align-items:flex-start}
.detail-gallery{flex:0 0 42%;max-width:42%}
.detail-info{flex:1}
.detail-image{display:block;width:100%;height:320px;border-radius:18px;object-fit:cover;background:#f4f7fb}
.detail-image-empty{display:flex;align-items:center;justify-content:center;color:#94a3b8;font-weight:600}
.detail-price{margin-bottom:18px;color:#e65100;font-size:32px;font-weight:700}
.detail-meta{display:grid;grid-template-columns:repeat(2,minmax(0,1fr));gap:12px}
.meta-item{padding:12px 14px;border-radius:16px;background:#f7fbfe}
.label{display:block;margin-bottom:6px;color:#64748b;font-size:12px;letter-spacing:.04em;text-transform:uppercase}
.value{display:block;color:#163246;font-size:15px;word-break:break-word}
.detail-actions{margin-top:20px}
.detail-content{margin-top:24px;padding:8px 0;border-top:1px solid rgba(15,111,168,.1)}
.detail-empty{display:flex;align-items:center;justify-content:center;min-height:220px;color:#94a3b8}
@media (max-width: 900px){
  .detail-top{flex-direction:column}
  .detail-gallery{flex-basis:100%;max-width:100%;width:100%}
  .detail-meta{grid-template-columns:1fr}
}
</style>
