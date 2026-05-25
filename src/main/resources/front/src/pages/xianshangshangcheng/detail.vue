<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'/'">
				<el-breadcrumb-item class="item1" to="/"><a>棣栭〉</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/xianshangshangcheng?centerType=' + (centerType?'1':'0')"><a>{{item.name}}</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item3"><a href="javascript:void(0);">璇︽儏</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">杩斿洖</span>
			</el-button>
		</div>
		<div class="detail-preview">
			<div class="attr">
				<div class="info">
					<div class="title-item">
						<div class="detail-title">
							{{detail.shangpinmingcheng}}
						</div>
						<div class="colectBtn" @click="storeup(1)" v-show="!isStoreup">
							<i class="icon iconfont icon-shoucang10"></i>
							<span class="text">鏀惰棌({{detail.storeupnum}})</span>
						</div>
						<div class="colectBtnActive" @click="storeup(-1)" v-show="isStoreup">
							<i class="icon iconfont icon-shoucang12"></i>
							<span class="text">宸叉敹钘?{{detail.storeupnum}})</span>
						</div>
					</div>
					<div class="item item-price" v-if="detail.price">
						<div class="lable">浠锋牸</div>
						<div class="text"><span style="font-size: 12px">锟?/span>{{detail.price}}</div>
					</div>
					<div class="item item-price" v-if="detail.jf">
						<div class="lable">绉垎</div>
						<div class="text">{{detail.jf}}</div>
					</div>
					<div class="item">
						<div class="lable">鍗曢檺</div>
						<div class="text">{{detail.onelimittimes}}</div>
					</div>
					<div class="item">
						<div class="lable">搴撳瓨</div>
						<div class="text">{{detail.alllimittimes}}</div>
					</div>
					<div class="item">
						<div class="lable">鍟嗗搧鍒嗙被</div>
						<div class="text "  >{{detail.shangpinfenlei}}</div>
					</div>
					<div class="item">
						<div class="lable">鍟嗗搧鍝佺墝</div>
						<div class="text "  >{{detail.shangpinpinpai}}</div>
					</div>
					<div class="item">
						<div class="lable">鍟嗗搧瑙勬牸</div>
						<div class="text "  >{{detail.shangpinguige}}</div>
					</div>
					<div class="item">
						<div class="lable">鐢熶骇鍘傚</div>
						<div class="text "  >{{detail.shengchanchangjia}}</div>
					</div>
					<div class="item">
						<div class="lable">鐐瑰嚮娆℃暟</div>
						<div class="text "  >{{detail.clicknum}}</div>
					</div>
					<div class="btn_box">
						<el-input-number v-if="!centerType&&detail.alllimittimes" :min=1 v-model="buynumber"></el-input-number>
						<el-button class="addCartBtn" v-if="!centerType&&detail.alllimittimes" type="warning" size="small" @click="addCart">娣诲姞鍒拌喘鐗╄溅</el-button>
						<el-button class="buyBtn" v-if="!centerType&&detail.alllimittimes" type="warning" size="small" @click="buyNow">绔嬪嵆璐拱</el-button>

					</div>
					<div class="btn_box">
						<el-button class="editBtn" v-if="btnAuth('xianshangshangcheng','淇敼')" @click="editClick">淇敼</el-button>
						<el-button class="delBtn" v-if="btnAuth('xianshangshangcheng','鍒犻櫎')" @click="delClick">鍒犻櫎</el-button>
						<el-button class="editBtn" @click="allchapterClick()" type="warning" v-if="btnAuth('xianshangshangcheng','绔犺妭绠＄悊')">绔犺妭绠＄悊</el-button>
						<el-button class="editBtn" @click="discussClick()" type="warning" v-if="btnAuth('xianshangshangcheng','鏌ョ湅璇勮')">鏌ョ湅璇勮</el-button>
						<el-button v-if="centerType" class="editBtn" @click="onshelvesClick" type="warning">{{detail.onshelves==1?'涓嬫灦':'涓婃灦'}}</el-button>
					</div>
				</div>
			</div>
		
			<el-carousel v-if="detailBanner.length" trigger="click" indicator-position="inside" arrow="always" type="default" direction="horizontal" height="480px" :autoplay="false" :interval="3000" :loop="true">
				<el-carousel-item v-for="item in detailBanner" :key="item.id">
					<img :preview-src-list="[item]" v-if="item.substr(0,4)=='http'" :src="item" class="image">
					<img :preview-src-list="[baseUrl + item]" v-else :src="baseUrl + item" class="image">
				</el-carousel-item>
			</el-carousel>

			<div class="zancai">
				<div v-if="!isThumbsupnum && !isCrazilynum" class="zan" @click="thumbsupOrCrazily(21)">
					<i class="icon iconfont icon-zan07"></i>
					<span class="text">璧炰竴涓?{{detail.thumbsupnum}})</span>
				</div>
				<div v-if="!isThumbsupnum && !isCrazilynum" class="cai" @click="thumbsupOrCrazily(22)">
					<i class="icon iconfont icon-cai01"></i>
					<span class="text">韪╀竴涓?{{detail.crazilynum}})</span>
				</div>
				<div v-if="isThumbsupnum" class="zanActive" @click="cancelThumbsupOrCrazily(21)">
					<i class="icon iconfont icon-zan11"></i>
					<span class="text">宸茶禐({{detail.thumbsupnum}})</span>
				</div>
				<div v-if="isCrazilynum" class="caiActive" @click="cancelThumbsupOrCrazily(22)">
					<i  class="icon iconfont icon-cai16"></i>
					<span class="text">宸茶俯({{detail.crazilynum}})</span>
				</div>
			</div>


			<el-tabs id="tabsBox" class="detail-tabs" v-model="activeName" type="border-card" v-if="tabsNum>0" >
				<el-tab-pane label="鍟嗗搧璇︽儏" name="1">
					<div class="ql-snow ql-editor" v-html="detail.shangpinxiangqing"></div>
				</el-tab-pane>
				<el-tab-pane label="璇勮" name="2">
					<el-form class="add commentForm" :model="form" :rules="rules" ref="form">
						<el-form-item class="item" label="璇勮" prop="content">
							<editor
								myQuillEditor="content"
								v-model="form.content" 
								class="editor" 
								action="file/upload">
							</editor>
						</el-form-item>
						<el-form-item class="commentBtn">
							<el-button class="submitBtn" type="primary" @click="submitForm('form')">绔嬪嵆鎻愪氦</el-button>
							<el-button class="resetBtn" @click="resetForm('form')">閲嶇疆</el-button>
						</el-form-item>
					</el-form>
				
					<div v-if="infoList.length" class="comment-list">
						<div class="comment-item" v-for="item in infoList" :key="item.id" @mouseenter="discussEnter(item.id)" :id="'discuss' + item.id"
							@mouseleave="discussLeave">
							<div class="istop" v-if="item.istop">
								<span class="icon iconfont icon-jiantou24"></span>
							</div>
							<div class="user">
								<el-image v-if="item.avatarurl" :size="50" :src="baseUrl + item.avatarurl"></el-image>
								<el-image v-if="!item.avatarurl" :size="50" :src="require('@/assets/touxiang.png')"></el-image>
								<div class="name">{{item.nickname}}</div>
							</div>
							<div class="comment-content-box">
								<div class="ql-snow ql-editor" v-html="item.content"></div>
								<div class="comment-time">{{item.addtime}}</div>
								<div class="zancai-box">
									<div v-if="!comcaiChange(item)" class="zan-item" :class="comzanChange(item)?'active':''" @click="comzanClick(item)">
										<span class="icon iconfont" :class="comzanChange(item)?'icon-zan11':'icon-zan07'"></span>
										<span class="label">{{comzanChange(item)?'宸茶禐':'璧?}}</span>
										<span class="num">({{item.thumbsupnum}})</span>
									</div>
									<div v-if="!comzanChange(item)" class="cai-item" :class="comcaiChange(item)?'active':''" @click="comcaiClick(item)">
										<span class="icon iconfont" :class="comcaiChange(item)?'icon-cai16':'icon-cai01'"></span>
										<span class="label">{{comcaiChange(item)?'宸茶俯':'韪?}}</span>
										<span class="num">({{item.crazilynum}})</span>
									</div>
								</div>
								<div class="comment-btn">
									<!-- <el-button :style='{"border":"0","cursor":"pointer","padding":"0 20px","margin":"0 10px","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"4px","background":"#46AC2E","width":"auto","lineHeight":"34px","fontSize":"14px","height":"34px"}'>鍥炲</el-button> -->
									<el-button class="delBtn" v-if="showIndex==item.id&&userid==item.userid" @click="discussDel(item.id)">鍒犻櫎</el-button>
								</div>
							</div>
							<div class="comment-content-box" v-if="item.reply">
								鍥炲锛?span class="ql-snow ql-editor" v-html="item.reply"></span>
							</div>
						</div>
					</div>
				
					<el-pagination
						background
						id="pagination" class="pagination"
						:page-size="pageSize"
						prev-text="<"
						next-text=">"
						:hide-on-single-page="true"
						:layout='["total","prev","pager","next","sizes","jumper"].join()'
						:total="total"
						@current-change="curChange"
						@prev-click="prevClick"
						@next-click="nextClick"
						@size-change="sizeChange"
						></el-pagination>
				</el-tab-pane>
			</el-tabs>

		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import Swiper from "swiper";
	export default {
		//鏁版嵁闆嗗悎
		data() {
			return {
				tablename: 'xianshangshangcheng',
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '绾夸笂鍟嗗煄'
					}
				],
				title: '',
				detailBanner: [],
				userid: Number(localStorage.getItem('frontUserid')),
				id: 0,
				detail: {},
				tabsNum: 2,
				activeName: '1',
				form: {
					content: '',
					userid: Number(localStorage.getItem('frontUserid')),
					nickname: localStorage.getItem('username'),
					avatarurl: '',
				},
				showIndex: -1,
				infoList: [],
				rules: {
					content: [
						{ required: true, message: '璇疯緭鍏ュ唴瀹?, trigger: 'blur' }
					],
				},
				total: 1,
				pageSize: 10,
				totalPage: 1,
				storeupParams: {
					name: '',
					picture: '',
					refid: 0,
					tablename: 'xianshangshangcheng',
					userid: Number(localStorage.getItem('frontUserid'))
				},
				isStoreup: false,
				storeupInfo: {},
				isCrazilynum: false,
				isThumbsupnum: false,
				thumbsupOrCrazilyInfo: {},
				buynumber: 1,
				cart: {
					buynumber: 0,
					discountprice: 0,
					goodid: 0,
					goodname: '',
					picture: '',
					price: 0,
					userid: Number(localStorage.getItem('frontUserid'))
				},
				isInCart: false,
				centerType: false,
				storeupType: false,
			}
		},
		created() {
			if(this.$route.query.centerType&&this.$route.query.centerType!=0) {
				this.centerType = true
			}
			if(this.$route.query.storeupType&&this.$route.query.storeupType!=0) {
				this.storeupType = true
			}
			this.init();
		},
		mounted() {
		},
		computed: {
			username() {
				return localStorage.getItem('username')
			}
		},
		//鏂规硶闆嗗悎
		methods: {
			init() {
				this.id = this.$route.query.id
				this.baseUrl = this.$config.baseUrl;
				this.$http.get(this.tablename + '/detail/'  + this.id, {}).then(async res => {
					if (res.data.code == 0) {
						this.detail = res.data.data;
						this.title = this.detail.shangpinmingcheng;
						if(this.detail.shangpintupian) {
							this.detailBanner = this.detail.shangpintupian.split(",w").length>1?[this.detail.shangpintupian]:this.detail.shangpintupian.split(',');
						}
						this.$forceUpdate();
						this.getDiscussList(1);
						if(localStorage.getItem('frontToken')){
							this.getStoreupStatus();
							this.getThumbsupOrCrazilyStatus();
							this.getCartList();
						}

					}
				});
			},
			storeup(type) {
				if (type == 1 && !this.isStoreup) {
					this.storeupParams.name = this.title;
					this.storeupParams.picture = this.detailBanner[0];
					this.storeupParams.inteltype = this.detail.shangpinfenlei;
					this.storeupParams.refid = this.detail.id;
					this.storeupParams.type = String(type);
					this.$http.post('storeup/add', this.storeupParams).then(res => {
						if (res.data.code == 0) {
							this.isStoreup = true;
							this.detail.storeupnum++
							this.$http.post('xianshangshangcheng/update', this.detail).then(res => {});
							this.$message({
								type: 'success',
								message: '鏀惰棌鎴愬姛!',
								duration: 1500,
							});
						}
					});
				}
				if (type == -1 && this.isStoreup) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '1', refid: this.detail.id, tablename: 'xianshangshangcheng', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isStoreup = true;
							this.storeupInfo = res.data.data.list[0];
							let delIds = new Array();
							delIds.push(this.storeupInfo.id);
							this.$http.post('storeup/delete', delIds).then(res => {
								if (res.data.code == 0) {
									this.isStoreup = false;
									this.detail.storeupnum--
									this.$http.post('xianshangshangcheng/update', this.detail).then(res => {});
									this.$message({
										type: 'success',
										message: '鍙栨秷鎴愬姛!',
										duration: 1500,
									});
								}
							});
						}
					});
				}
			},
			getStoreupStatus(){
				if(localStorage.getItem("frontToken")) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '1', refid: this.detail.id, tablename: 'xianshangshangcheng', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isStoreup = true;
							this.storeupInfo = res.data.data.list[0];
						}
					});
				}
			},
			async thumbsupOrCrazily(type) {
				this.storeupParams.name = this.title;
				this.storeupParams.picture = this.detailBanner[0];
				this.storeupParams.refid = this.detail.id;
				this.storeupParams.type = String(type);
				await this.$http.post('storeup/add', this.storeupParams).then(res => {
					if (res.data.code == 0) {
						let detail = JSON.parse(JSON.stringify(this.detail))
						if (type == 21) detail.thumbsupnum = Number(detail.thumbsupnum) + 1;
						if (type == 22) detail.crazilynum = Number(detail.crazilynum) + 1;
						this.$http.post('xianshangshangcheng/update', detail).then(res => {
							this.detail = detail
						});
						this.getThumbsupOrCrazilyStatus();
						this.$message({
							type: 'success',
							message: '鎿嶄綔鎴愬姛!',
							duration: 1500,
						});
						
					}
				});
				
			},
			async cancelThumbsupOrCrazily(type) {
				let delIds = new Array();
				delIds.push(this.thumbsupOrCrazilyInfo.id);
				await this.$http.post('storeup/delete', delIds).then(res => {
					if (res.data.code == 0) {
						let detail = JSON.parse(JSON.stringify(this.detail))
						if (type == 21) detail.thumbsupnum -= 1;
						if (type == 22) detail.crazilynum -= 1;
						this.$http.post('xianshangshangcheng/update', detail).then(res => {
							this.detail = detail
						});
						this.isThumbsupnum = false;
						this.isCrazilynum = false;
						this.$message({
							type: 'success',
							message: '鍙栨秷鎴愬姛!',
							duration: 1500,
						});
					}
				});
				
			},
			getThumbsupOrCrazilyStatus() {
				if(localStorage.getItem("frontToken")) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '21', refid: this.detail.id, tablename: 'xianshangshangcheng', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isThumbsupnum = true;
							this.thumbsupOrCrazilyInfo = res.data.data.list[0];
						}
					});
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: '22', refid: this.detail.id, tablename: 'xianshangshangcheng', userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isCrazilynum = true;
							this.thumbsupOrCrazilyInfo = res.data.data.list[0];
						}
					});
				}
			},
			curChange(page) {
				this.getDiscussList(page);
			},
			prevClick(page) {
				this.getDiscussList(page);
			},
			nextClick(page) {
				this.getDiscussList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getDiscussList(1);
			},
			// 杩斿洖鎸夐挳
			backClick(){
				if(this.storeupType){
					history.back()
				}else{
					let params = {}
					if(this.centerType){
						params.centerType = 1
					}
					this.$router.push({path: '/index/xianshangshangcheng', query: params});
				}
			},
			// 涓嬭浇
			download(file ){
				if(!file) {
					this.$message({
						type: 'error',
						message: '鏂囦欢涓嶅瓨鍦?,
						duration: 1500,
					});
					return;
				}
				let arr = file.replace(new RegExp('upload/', "g"), "")
				axios.get(this.baseUrl + 'file/download?fileName=' + arr, {
					headers: {
						token: localStorage.getItem("frontToken")
					},
					responseType: "blob"
				}).then(({
					data
				}) => {
					const binaryData = [];
					binaryData.push(data);
					const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
						type: 'application/pdf;chartset=UTF-8'
					}))
					const a = document.createElement('a')
					a.href = objectUrl
					a.download = arr
					// a.click()
					// 涓嬮潰杩欎釜鍐欐硶鍏煎鐏嫄
					a.dispatchEvent(new MouseEvent('click', {
						bubbles: true,
						cancelable: true,
						view: window
					}))
					window.URL.revokeObjectURL(data)
				},err=>{
					axios.get((location.href.split(this.$config.name).length>1 ? location.href.split(this.$config.name)[0] :'') + this.$config.name + 'file/download?fileName=' + arr, {
						headers: {
							token: localStorage.getItem("frontToken")
						},
						responseType: "blob"
					}).then(({
						data
					}) => {
						const binaryData = [];
						binaryData.push(data);
						const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
							type: 'application/pdf;chartset=UTF-8'
						}))
						const a = document.createElement('a')
						a.href = objectUrl
						a.download = arr
						// a.click()
						// 涓嬮潰杩欎釜鍐欐硶鍏煎鐏嫄
						a.dispatchEvent(new MouseEvent('click', {
							bubbles: true,
							cancelable: true,
							view: window
						}))
						window.URL.revokeObjectURL(data)
					})
				})
			},
			getDiscussList(page,type=1) {
				this.$http.get('discussxianshangshangcheng/list', {
					params: {
						page, 
						limit: this.pageSize, 
						refid: this.detail.id,
						sort: 'istop',
						order: 'desc',
					}
				}).then(res => {
					if (res.data.code == 0) {
						for(let x in res.data.data.list) {
							res.data.data.list[x].content = res.data.data.list[x].content.replace(/img src/gi,"img style=\"width:30%;\" src");
						}
						this.infoList = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(type==1) {
							if(this.$route.query.discussId&&this.$route.query.discussId!=0) {
								this.$nextTick(()=>{
									document.getElementById('discuss' + this.$route.query.discussId).scrollIntoView({
										behavior: 'smooth'
									});
								})
							}
						}
					}
				});
			},
			comzanChange(row){
				if(row.tuserids){
					let arr = row.tuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							return true
						}
					}
				}
				return false
			},
			comzanClick(row){
				if(!this.userid){
					return false
				}
				if(!this.comzanChange(row)){
					row.thumbsupnum++
					if(row.tuserids){
						row.tuserids = row.tuserids + ',' + this.userid
					}else {
						row.tuserids = String(this.userid)
					}
					this.$http.post('discussxianshangshangcheng/update',row).then(rs=>{
						this.$message.success('鐐硅禐鎴愬姛')
					})
				}else {
					row.thumbsupnum--
					let arr = row.tuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							arr.splice(x,1)
						}
					}
					row.tuserids = arr.join(',')
					this.$http.post('discussxianshangshangcheng/update',row).then(rs=>{
						this.$message.success('鍙栨秷鎴愬姛')
					})
				}
			},
			comcaiChange(row){
				if(row.cuserids){
					let arr = row.cuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							return true
						}
					}
				}
				return false
			},
			comcaiClick(row){
				if(!this.userid){
					return false
				}
				if(!this.comcaiChange(row)){
					row.crazilynum++
					if(row.cuserids){
						row.cuserids = row.cuserids + ',' + this.userid
					}else {
						row.cuserids = String(this.userid)
					}
					this.$http.post('discussxianshangshangcheng/update',row).then(rs=>{
						this.$message.success('鐐硅俯鎴愬姛')
					})
				}else {
					row.crazilynum--
					let arr = row.cuserids.split(',')
					for(let x in arr){
						if(arr[x] == this.userid){
							arr.splice(x,1)
						}
					}
					row.cuserids = arr.join(',')
					this.$http.post('discussxianshangshangcheng/update',row).then(rs=>{
						this.$message.success('鍙栨秷鎴愬姛')
					})
				}
			},
			discussEnter(index){
				this.showIndex = index
			},
			discussLeave(){
				this.showIndex = -1
			},
			discussDel(id){
				this.$confirm('鏄惁鍒犻櫎姝よ瘎璁猴紵').then(_ => {
					this.$http.post('discussxianshangshangcheng/delete',[id]).then(res=>{
						if(res.data&&res.data.code==0){
							this.addDiscussNum(1)
							this.$message({
								type: 'success',
								message: '鍒犻櫎鎴愬姛!',
								duration: 1500,
								onClose: () => {
									this.getDiscussList(1);
								}
							});
						}
					})
				}).catch(_ => {});
			},
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$http.get('orders/list', {params: {page: 1, limit: 1, status: '宸插畬鎴?, goodid: this.detail.id, userid: Number(localStorage.getItem('frontUserid'))}}).then(res => {
							if (res.data.code == 0 && res.data.data.list.length == 0) {
								this.$message({
									type: 'error',
									message: '璇峰畬鎴愯鍗曞悗鍐嶈瘎璁?',
									duration: 1500
								});
								return false
							} else {
								this.addComments()
							}
						});
					} else {
						return false;
					}
				});
			},
			addComments(content=null,type=1) {
				let data = JSON.parse(JSON.stringify(this.form))
				data.refid = this.detail.id;
				data.avatarurl = localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'';
				if(type==2) {
					data.content = content
				}
				this.$http.post('discussxianshangshangcheng/add', data).then(rs2 => {
					if (rs2.data.code == 0) {
						if(type==1) {
							this.form.content = '';
						}
						this.addDiscussNum(2)
						this.getDiscussList(1,type);
						this.$message({
							type: 'success',
							message: '璇勮鎴愬姛!',
							duration: 1500,
						});
					}
				});
			},
			resetForm(formName) {
				this.$refs[formName].resetFields();
			},
			addDiscussNum(type){
				if(type==2){
					this.detail.discussnum++
				}else if(type==1){
					if(this.detail.discussnum!=0){
						this.detail.discussnum--
					}else{
						this.detail.discussnum = 0
					}
				}
				this.$http.post('xianshangshangcheng/update',this.detail).then(res=>{})
			},
			getCartList() {
				this.$http.get('cart/list', {params: {userid: Number(localStorage.getItem('frontUserid')), tablename: 'xianshangshangcheng', goodid: this.detail.id}}).then(res => {
					if (res.data.code == 0) {
						if (res.data.data.list.length > 0) {
							this.isInCart = true;
						} else {
							this.isInCart = false;
						}
					}
				});
			},
			addCart() {
				// 鍗曟璐拱闄愬埗
				if (this.detail.onelimittimes > 0 && this.detail.onelimittimes < this.buynumber) {
					this.$message.error(`姣忎汉鍗曟鍙兘璐拱${this.detail.onelimittimes}浠禶);
					return
				}
				// 搴撳瓨涓嶅
				if (this.detail.alllimittimes <= 0 ) {
					this.$message.error(`鍟嗗搧宸插敭缃刞);
					return
				}
				// 搴撳瓨闄愬埗
				if (this.detail.alllimittimes > 0 && this.detail.alllimittimes < this.buynumber) {
					this.$message.error(`搴撳瓨涓嶈冻`);
					return
				}
				if (this.isInCart) {
					this.$message.error('璇ュ晢鍝佸凡缁忔坊鍔犲埌璐墿杞?);
					return;
				}
		
				this.cart.buynumber = this.buynumber;
				this.cart.goodid = this.detail.id;
				this.cart.goodname = this.title;
				this.cart.tablename = this.tablename;
				this.cart.goodtype = this.detail.shangpinfenlei;
				this.cart.picture = this.detailBanner[0];
				this.cart.price = this.detail.price;
				this.cart.discountprice = this.detail.vipprice?this.detail.vipprice:this.detail.price;
				this.$http.post('cart/save', this.cart).then(res => {
					if (res.data.code === 0) {
						this.getCartList();
						this.$message({
							message: '娣诲姞鎴愬姛',
							type: 'success',
							duration: 1500,
						});
					} else {
						this.$message.error(res.data.msg);
					}
				});
			},
			//绔嬪嵆璐拱
			buyNow() {
				// 鍗曟璐拱闄愬埗
				if (this.detail.onelimittimes > 0 && this.detail.onelimittimes < this.buynumber) {
					this.$message.error(`姣忎汉鍗曟鍙兘璐拱${this.detail.onelimittimes}浠禶);
					return
				}
				// 搴撳瓨涓嶅
				if (this.detail.alllimittimes <= 0 ) {
					this.$message.error(`鍟嗗搧宸插敭缃刞);
					return
				}
				// 搴撳瓨闄愬埗
				if (this.detail.alllimittimes > 0 && this.detail.alllimittimes < this.buynumber) {
					this.$message.error(`搴撳瓨涓嶈冻`);
					return
				}
				// 淇濆瓨鍒皊torage涓紝鍦ㄧ‘璁や笅鍗曢〉闈腑鑾峰彇瑕佽喘涔扮殑鍟嗗搧
				localStorage.setItem('orderGoods', JSON.stringify([{
					tablename: this.tablename,
					goodid: this.detail.id,
					goodname: this.title,
					goodtype: this.detail.shangpinfenlei,

					picture:this.detailBanner[0],
					buynumber: this.buynumber,
					userid: Number(localStorage.getItem('frontUserid')),
					price: this.detail.price,
					discountprice: this.detail.vipprice ? this.detail.vipprice : this.detail.price
				}]));
				// 璺宠浆鍒扮‘璁や笅鍗曢〉闈?
				let query = {
					type: 1,
				}
				this.$router.push({path: '/index/shop-order/orderConfirm', query: query});
			},


			// 鏉冮檺鍒ゆ柇
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			// 淇敼
			editClick(){
				this.$router.push(`/index/xianshangshangchengAdd?type=edit&&id=${this.detail.id}`);
			},
			// 鍒犻櫎
			async delClick(){
				await this.$confirm('鏄惁鍒犻櫎姝ょ嚎涓婂晢鍩庯紵') .then(_ => {
					this.$http.post('xianshangshangcheng/delete', [this.detail.id]).then(async res => {
						if (res.data.code == 0) {
							this.$http.get('storeup/list',{params:{
								page: 1,
								limit: 100,
								refid: this.detail.id,
								tablename: 'xianshangshangcheng',
							}}).then(async obj=>{
								if(obj.data&&obj.data.code==0){
									let arr = []
									for(let x in obj.data.data.list){
										arr.push(obj.data.data.list[x].id)
									}
									if(arr.length){
										await this.$http.post('storeup/delete',arr).then(()=>{})
									}
									this.$message({
										type: 'success',
										message: '鍒犻櫎鎴愬姛!',
										duration: 1500,
										onClose: () => {
											history.back()
										}
									});
								}
							})
						}
					});
				}).catch(_ => {});
			},
			allchapterClick (){
				let params = {
					refid: this.detail.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/chapterxianshangshangcheng', query: params});
			},
			discussClick (){
				let params = {
					refid: this.detail.id
				}
				if(this.centerType){
					params.centerType = 1
				}
				this.$router.push({path: '/index/discussxianshangshangcheng', query: params});
			},
			onshelvesClick(){
				this.$confirm(`鏄惁${this.detail.onshelves==1?'涓嬫灦':'涓婃灦'}锛焋).then(async _ => {
					this.detail.onshelves = (this.detail.onshelves==1?0:1)
					await this.$http.post('xianshangshangcheng/update',this.detail).then(rs=>{
						this.$message.success('鎿嶄綔鎴愬姛锛?)
					})
				}).catch(_ => {});
			},
			// 鑾峰彇uuid
			getUUID() {
				return new Date().getTime();
			},
		},
		components: {
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.detail-preview {
		padding: 0 10%;
		margin: 10px auto;
		display: flex;
		width: 100%;
		position: relative;
		flex-wrap: wrap;
		.attr {
			padding: 0px;
			margin: 0 0 20px;
			background: none;
			flex: 1;
			display: flex;
			position: relative;
			order: 2;
			.info {
				border-radius: 8px;
				padding: 0 10px 10px 10px;
				margin: 0;
				background: #fff;
				flex: 1;
				.title-item {
					padding: 0 10px 10px;
					margin: 0 0 0px 0;
					background: none;
					display: flex;
					border-color: rgba(70, 172, 46,0.3);
					border-width: 0 0 1px;
					justify-content: space-between;
					align-items: center;
					border-style: solid;
					.detail-title {
						padding: 0;
						color: #333;
						font-weight: 600;
						font-size: 18px;
						border-color: #009899;
						border-width: 0 0 0 0px;
						line-height: 1;
						border-style: solid;
					}
					.colectBtn {
						cursor: pointer;
						border: 0px solid #eee;
						border-radius: 4px;
						padding: 5px 15px;
						background: none;
						.icon {
							color: #666;
							font-size: 16px;
						}
						.text {
							color: #000;
							font-size: 16px;
						}
					}
					.colectBtnActive {
						background: #46AC2E;
						border-color: #46AC2E;
						.icon {
							color: #fff;
							font-size: 16px;
						}
						.text {
							color: #fff;
							font-size: 16px;
						}
					}
					.colectBtn:hover {
						background: #46AC2E;
						border-color: #46AC2E;
						.icon {
							color: #fff;
						}
						.text {
							color: #fff;
						}
					}
					.colectBtnActive:hover {
						background: #46AC2E;
						border-color: #46AC2E;
						.icon {
							color: #fff;
						}
						.text {
							color: #fff;
						}
					}
				}
				.item {
					padding: 5px 10px;
					margin: 0 0 0px 0;
					background: none;
					display: flex;
					border-color: rgba(70, 172, 46,0.3);
					border-width: 0 0 1px;
					justify-content: spaceBetween;
					border-style: solid;
					.lable {
						padding: 0 10px 0 0;
						color: #333;
						white-space: nowrap;
						font-weight: 500;
						width: auto;
						font-size: 16px;
						line-height: 24px;
						text-align: right;
						height: auto;
					}
					.count-down {
						padding: 0 10px;
						color: #666;
						word-break: break-all;
						flex: 1;
						font-size: 14px;
						line-height: 24px;
						height: auto;
					}
					.text {
						padding: 0 10px;
						color: #666;
						word-break: break-all;
						flex: 1;
						font-size: 14px;
						line-height: 24px;
						height: auto;
					}
					.bold {
						font-weight: bold;
					}
					.link {
						cursor: pointer;
						text-decoration: underline;
					}
				}
				.item-price {
					.lable {
					}
					.text {
					}
				}
				.btn_box {
					padding: 10px 0;
					display: flex;
					flex-wrap: wrap;
				}
				.el-input-number {
					margin: 0 5px 0 0;
					display: inline-block;
					width: 180px;
					line-height: 38px;
					position: relative;
					.el-input-number__decrease:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled), .el-input-number__increase:hover:not(.is-disabled)~.el-input .el-input__inner:not(.is-disabled) {
					  border-color: none;
					}
					::v-deep .el-input-number__decrease {
						cursor: pointer;
						z-index: 1;
						display: flex;
						border-color: #DCDFE6;
						border-radius: 4px 0 0 4px;
						top: 1px;
						left: 1px;
						background: #f5f5f5;
						width: 40px;
						justify-content: center;
						border-width: 0 1px 0 0;
						align-items: center;
						position: absolute;
						border-style: solid;
						text-align: center;
						height: 38px;
					}
					
					::v-deep .el-input-number__decrease i {
						color: #666;
						font-size: 14px;
					}
				
					::v-deep .el-input-number__increase {
						cursor: pointer;
						z-index: 1;
						display: flex;
						border-color: #DCDFE6;
						right: 1px;
						border-radius: 0 4px 4px 0;
						top: 1px;
						background: #f5f5f5;
						width: 40px;
						justify-content: center;
						border-width: 0 0 0 1px;
						align-items: center;
						position: absolute;
						border-style: solid;
						text-align: center;
						height: 38px;
					}
					
					::v-deep .el-input-number__increase i {
						color: #666;
						font-size: 14px;
					}
					
					::v-deep .el-input .el-input__inner {
						border: 1px solid #DCDFE6;
						border-radius: 4px;
						padding: 0 40px;
						outline: none;
						color: #666;
						background: #FFF;
						display: inline-block;
						width: 100%;
						font-size: 14px;
						line-height: 40px;
						text-align: center;
						height: 40px;
					}
				}
				.addCartBtn {
					border: 1px solid #46AC2E;
					cursor: pointer;
					border-radius: 4px;
					padding: 0 10px;
					margin: 0 0 0 10px;
					outline: none;
					color: #fff;
					background: #46AC2E;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.addCartBtn:hover {
					opacity: 0.7;
				}
				.buyBtn {
					border: 1px solid #46AC2E;
					cursor: pointer;
					border-radius: 4px;
					padding: 0 10px;
					margin: 0 0 0 10px;
					outline: none;
					color: #fff;
					background: #46AC2E;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.buyBtn:hover {
					opacity: 0.7;
				}
				.editBtn {
					border: 1px solid #46AC2E;
					cursor: pointer;
					border-radius: 4px;
					padding: 0 10px;
					margin: 0 0 0 10px;
					outline: none;
					color: #fff;
					background: #46AC2E;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.editBtn:hover {
					opacity: 0.7;
				}
				.delBtn {
					border: 1px solid #46AC2E;
					cursor: pointer;
					border-radius: 4px;
					padding: 0 10px;
					margin: 0 0 0 10px;
					outline: none;
					color: #fff;
					background: #46AC2E;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.delBtn:hover {
					opacity: 0.7;
				}
			}
		}
		.el-carousel {
			margin: 0 20px 0 0;
			width: 480px;
			height: 480px;
			order: 1;
			::v-deep .el-carousel__indicator button {
				width: 0;
				height: 0;
				display: none;
			}
			::v-deep .el-carousel__container {
				.el-carousel__arrow--left {
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--left:hover {
					background: red;
				}
				.el-carousel__arrow--right {
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--right:hover {
					background: red;
				}
				.el-carousel__item {
					border-radius: 10px;
					width: 100%;
					height: 100%;
					img {
						object-fit: cover;
						width: 100%;
						height: 100%;
					}
				}
			}
		
			::v-deep .el-carousel__indicators {
				padding: 0;
				margin: 0;
				z-index: 2;
				position: absolute;
				list-style: none;
				li {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 0.4;
					transition: 0.3s;
					height: 12px;
				}
				li:hover {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 24px;
					opacity: 0.7;
					height: 12px;
				}
				li.is-active {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 24px;
					opacity: 1;
					height: 12px;
				}
			}
		}
		.zancai {
			padding: 0;
			margin: 0px auto 20px;
			background: none;
			display: flex;
			width: 100%;
			justify-content: center;
			order: 3;
			.zan {
				cursor: pointer;
				border: 1px solid #000000;
				border-radius: 4px;
				padding: 10px 40px;
				margin: 0 100px 0 0;
				background: none;
				display: flex;
				width: auto;
				justify-content: center;
				align-items: center;
				.icon {
					margin: 0 3px;
					color: #666;
					font-size: 14px;
				}
				.text {
					margin: 0 3px;
					color: #666;
					font-size: 16px;
				}
			}
			.zan:hover {
				background: #EABB3F;
				border-color: #EABB3F;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
			.zanActive {
				cursor: pointer;
				border-radius: 4px;
				padding: 10px 20px;
				margin: 0 20px 0 0;
				background: #ff6f3d;
				display: flex;
				width: auto;
				border-color: #ff6f3d;
				justify-content: center;
				align-items: center;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
			.zanActive:hover {
				background: #EABB3F;
				border-color: #EABB3F;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
			
			.cai {
				cursor: pointer;
				border: 1px solid #000000;
				border-radius: 4px;
				padding: 10px 40px;
				margin: 0;
				background: none;
				display: flex;
				width: auto;
				justify-content: center;
				align-items: center;
				.icon {
					margin: 0 3px;
					color: #666;
					font-size: 14px;
				}
				.text {
					margin: 0 3px;
					color: #666;
					font-size: 16px;
				}
			}
			.cai:hover {
				background: #ff6f3d;
				border-color: #ff6f3d;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
			.caiActive {
				background: #ff6f3d;
				border-color: #ff6f3d;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
			.caiActive:hover {
				background: #ff6f3d;
				border-color: #ff6f3d;
				.icon {
					color: #fff;
				}
				.text {
					color: #fff;
				}
			}
		}
		.detail-tabs {
			border: 0px solid #DCDFE6;
			box-shadow: none;
			padding: 0;
			margin: 20px auto;
			background: none;
			width: 100%;
			order: 5;
			& ::v-deep .el-tabs__header .el-tabs__nav-wrap {
				margin-bottom: 0;
			}
			::v-deep .el-tabs__header {
				padding: 0 20px;
				background: none;
				display: block;
				width: 100%;
				border-color: #707070;
				border-width: 1px 0 0;
				line-height: 1.5;
				border-style: solid;
				text-align: center;
			}
			
			::v-deep .el-tabs__header .el-tabs__item {
				border: 0;
				padding: 0 20px;
				margin: 0 30px 0 0;
				color: #000;
				font-weight: 500;
				display: inline-block;
				font-size: 18px;
				border-color: transparent;
				line-height: 40px;
				background: none;
				border-width: 2px 0 0 0;
				position: relative;
				border-style: solid;
				list-style: none;
				height: 40px;
			}
			
			::v-deep .el-tabs__header .el-tabs__item:hover {
				color: #46AC2E;
				background: linear-gradient( 180deg, #EEF8F2 0%, #FFFFFF 100%);
				border-color: #46AC2E;
				border-width: 2px 0 0 0;
				border-style: solid;
			}
			
			::v-deep .el-tabs__header .el-tabs__item.is-active {
				color: #46AC2E;
				background: linear-gradient( 180deg, #EEF8F2 0%, #FFFFFF 100%);
				border-color: #46AC2E;
			}
			
			::v-deep .el-tabs__content {
				padding: 15px 5px;
				color: #333;
				background: #fff;
				width: 100%;
				font-size: 16px;
			}
			.commentForm {
				box-shadow: none;
				padding: 0px;
				margin: 20px 0 20px;
				width: 100%;
				.item {
					display: block;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: auto;
					::v-deep .el-form-item__label {
						padding: 0 10px 0 0;
						color: #666;
						white-space: nowrap;
						width: 100%;
						font-size: 16px;
						line-height: 1;
						text-align: left;
					}
					.editor {
						border: 0px solid #ddd;
						border-radius: 4px;
						box-shadow: none;
						outline: none;
						margin: 30px 0 0 0;
						color: #333;
						width: 100%;
						clear: both;
						font-size: 14px;
						line-height: 32px;
						::v-deep .avatar-uploader {
							height: 0;
							line-height: 0;
						}
					}
					.editor ::v-deep.ql-toolbar {
						background: none;
					}
					.editor ::v-deep.ql-container {
						background: none;
						min-height: 180px;
					}
					.editor ::v-deep.ql-container .ql-blank::before {
						color: #999;
					}
				}
				.commentBtn {
					padding: 0;
					margin: 10px 0 0;
					width: 100%;
					text-align: left;
					height: auto;
					.submitBtn {
						border: 1px solid #46AC2E;
						cursor: pointer;
						border-radius: 40px  40px  40px  40px;
						padding: 0 40px;
						margin: 0 20px 0 0;
						outline: none;
						color: #fff;
						background: #46AC2E;
						width: auto;
						font-size: 15px;
						line-height: 40px;
						height: 40px;
					}
					.submitBtn:hover {
						opacity: 0.7;
					}
					.resetBtn {
						border: 1px solid #000;
						cursor: pointer;
						border-radius: 40px  40px  40px  40px;
						padding: 0 40px;
						margin: 0 20px 0 0;
						outline: none;
						color: #000;
						background: none;
						width: auto;
						font-size: 15px;
						line-height: 40px;
						height: 40px;
					}
					.resetBtn:hover {
						opacity: 0.7;
					}
				}
			}
			.comment-list {
				box-shadow: none;
				padding: 0px;
				margin: 30px 0 0;
				display: flex;
				width: 100%;
				justify-content: space-between;
				flex-wrap: wrap;
				.comment-item {
					padding: 8px 10px;
					box-shadow:  0px 3px 6px 1px rgba(0,0,0,0.16);
					margin: 0 0 20px;
					width: calc(33.33% - 10px);
					border-color: #eee;
					border-width: 1px;
					align-items: center;
					position: relative;
					border-style: solid;
					height: auto;
					.istop {
						cursor: pointer;
						box-shadow: none;
						top: 26px;
						background: none;
						position: absolute;
						right: 16px;
						.icon {
							color: #000;
							font-size: 24px;
						}
					}
					.user {
						padding: 10px;
						background: none;
						display: flex;
						width: 100%;
						align-items: center;
						height: auto;
						.el-image {
							border-radius: 100%;
							margin: 0 10px 0 0;
							object-fit: cover;
							width: 40px;
							height: 40px;
						}
						.name {
							color: #333;
							font-weight: 600;
							font-size: 16px;
						}
					}
					.comment-time {
						padding: 0 10px;
						color: #666;
						width: 100%;
						text-align: right;
					}
					.comment-content-box {
						border-radius: 4px;
						padding: 8px;
						box-shadow: none;
						margin: 10px 0px 0px;
						word-wrap: break-word;
						color: #888;
						background: none;
						font-size: 15px;
						line-height: 24px;
						.zancai-box {
							margin: 8px 0 0 0;
							display: none;
							width: 100%;
							justify-content: flex-end;
							align-items: center;
							height: 30px;
							.zan-item {
								display: flex;
								align-items: center;
								.icon {
								font-size: 14px;
								}
								.label {
								display: none;
								font-size: 14px;
								}
								.num {
								font-size: 14px;
								}
							}
							.zan-item.active {
								background: none;
								.icon {
								color: #ff0000;
								font-size: 14px;
								}
								.label {
								color: #ff0000;
								display: none;
								font-size: 14px;
								}
								.num {
								color: #ff0000;
								font-size: 14px;
								}
							}
							.zan-item:hover {
								opacity: 0.8;
								.icon {
								color: #ff000030;
								}
								.label {
								color: #ff000030;
								}
								.num {
								color: #ff000030;
								}
							}
							.cai-item {
								display: flex;
								align-items: center;
								.icon {
								font-size: 14px;
								}
								.label {
								display: none;
								font-size: 14px;
								}
								.num {
								font-size: 14px;
								}
							}
							.cai-item.active {
								background: none;
								.icon {
								color: #ff0000;
								font-size: 14px;
								}
								.label {
								color: #ff0000;
								display: none;
								font-size: 14px;
								}
								.num {
								color: #ff0000;
								font-size: 14px;
								}
							}
							.cai-item:hover {
								opacity: 0.8;
								.icon {
								color: #ff000030;
								}
								.label {
								color: #ff000030;
								}
								.num {
								color: #ff000030;
								}
							}
						}
						.comment-btn {
							margin: 8px 0 0 0;
							display: flex;
							width: 100%;
							justify-content: flex-end;
							align-items: center;
							height: 40px;
							.delBtn {
								border: 0;
								cursor: pointer;
								border-radius: 4px;
								padding: 0 20px;
								margin: 0 10px;
								outline: none;
								color: #fff;
								background: #ca3240;
								width: auto;
								font-size: 14px;
								line-height: 34px;
								height: 34px;
							}
						}
					}
				}
			}
		}
	}
</style>

