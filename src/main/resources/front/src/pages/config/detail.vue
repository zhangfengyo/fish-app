<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'/'">
				<el-breadcrumb-item class="item1" to="/"><a>棣栭〉</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/config?centerType=' + (centerType?'1':'0')"><a>{{item.name}}</a></el-breadcrumb-item>
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
						</div>
					</div>
					<div class="item">
						<div class="lable">鍚嶇О</div>
						<div class="text "  >{{detail.name}}</div>
					</div>
					<div class="item">
						<div class="lable">url</div>
						<div class="text "  >{{detail.url}}</div>
					</div>
					<div class="item">
						<div class="lable">绫诲瀷</div>
						<div class="text "  >{{detail.type}}</div>
					</div>
					<div class="btn_box">
						<el-button class="editBtn" v-if="btnAuth('config','淇敼')" @click="editClick">淇敼</el-button>
						<el-button class="delBtn" v-if="btnAuth('config','鍒犻櫎')" @click="delClick">鍒犻櫎</el-button>
					</div>
				</div>
			</div>
		
			<el-carousel v-if="detailBanner.length" trigger="click" indicator-position="inside" arrow="always" type="default" direction="horizontal" height="480px" :autoplay="false" :interval="3000" :loop="true">
				<el-carousel-item v-for="item in detailBanner" :key="item.id">
					<img :preview-src-list="[item]" v-if="item.substr(0,4)=='http'" :src="item" class="image">
					<img :preview-src-list="[baseUrl + item]" v-else :src="baseUrl + item" class="image">
				</el-carousel-item>
			</el-carousel>



			<el-tabs id="tabsBox" class="detail-tabs" v-model="activeName" type="border-card" v-if="tabsNum>0" >
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
				tablename: 'config',
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '閰嶇疆绠＄悊'
					}
				],
				title: '',
				detailBanner: [],
				userid: Number(localStorage.getItem('frontUserid')),
				id: 0,
				detail: {},
				tabsNum: 0,
				activeName: '1',
				buynumber: 1,
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
						this.$forceUpdate();
						if(localStorage.getItem('frontToken')){
						}

					}
				});
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
					this.$router.push({path: '/index/config', query: params});
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
				this.$router.push(`/index/configAdd?type=edit&&id=${this.detail.id}`);
			},
			// 鍒犻櫎
			async delClick(){
				await this.$confirm('鏄惁鍒犻櫎姝ら厤缃鐞嗭紵') .then(_ => {
					this.$http.post('config/delete', [this.detail.id]).then(async res => {
						if (res.data.code == 0) {
							this.$message({
								type: 'success',
								message: '鍒犻櫎鎴愬姛!',
								duration: 1500,
								onClose: () => {
									history.back()
								}
							});
						}
					});
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
		}
	}
</style>

