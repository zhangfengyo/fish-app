<template>
	<div class="home-preview">
		<div v-if="recommendNewsList.length" id="animate_recommendnews" class="recommend-news animate__animated">
			<div class="news_title_box">
				<span class="news_title">Recommended News</span>
				<span class="news_subhead">{{ recommendPreference ? `Favorite: ${recommendPreference}` : 'RECOMMEND' }}</span>
			</div>
			<div class="recommend-grid">
				<div class="recommend-card animation-box" v-for="item in recommendNewsList" :key="item.id" @click="toDetail('newsDetail', item)">
					<div class="recommend-image">
						<img :src="$assetUrl(item.picture ? item.picture.split(',')[0] : '')">
					</div>
					<div class="recommend-content">
						<div class="recommend-tag">{{ item.pinzhongfenlei || 'General' }}</div>
						<div class="recommend-name">{{ item.title }}</div>
						<div class="recommend-desc">{{ item.introduction }}</div>
					</div>
				</div>
			</div>
		</div>




		<!-- 新闻资讯 -->
		<div id="animate_newsnews" class="news animate__animated">
			<div class="news_title_box">
				<span class="news_title">观赏鱼资讯</span>
				<span class="news_subhead">{{'news'.toUpperCase()}}</span>
			</div>
			<div class="list list29 index-pv1">
				<div class="list29-div">
					<div class="list-body-left">
						<div class="list-item1 animation-box" @click="toDetail('newsDetail', newsList[0])" v-if="newsList.length">
							<div class="img">
								<img :src="baseUrl + (newsList[0].picture?newsList[0].picture.split(',')[0]:'')">
							</div>
							<div class="infoBox">
								<div class="name">{{newsList[0].title}}</div>
								<div class="desc">{{newsList[0].introduction}}</div>
								<div class="time_item">
									<span class="icon iconfont icon-shijian21"></span>
									<span class="label">发布时间：</span>
									<span class="text">{{newsList[0].addtime.split(' ')[0]}}</span>
								</div>
								<div class="publisher_item">
									<span class="icon iconfont icon-geren16"></span>
									<span class="label">发布人：</span>
									<span class="text">{{newsList[0].name}}</span>
								</div>
							</div>
						</div>
						<div class="list-body">
							<div class="list-item animation-box" v-for="(item,index) in newsList" :key="index" @click="toDetail('newsDetail', item)" v-if="index>1&&index<(Number(3) + 2)">
								<div class="dian"></div>
								<div class="name">{{item.title}}</div>
								<div class="time-item">{{item.addtime.split(' ')[0]}}</div>
							</div>
						</div>
					</div>
					<div class="list-body-right">
						<div class="list-item1 animation-box" @click="toDetail('newsDetail', newsList[1])" v-if="newsList.length">
							<div class="img">
								<img :src="baseUrl + (newsList[1].picture?newsList[1].picture.split(',')[0]:'')">
							</div>
							<div class="infoBox">
								<div class="name">{{newsList[1].title}}</div>
								<div class="desc">{{newsList[1].introduction}}</div>
								<div class="time_item">
									<span class="icon iconfont icon-shijian21"></span>
									<span class="label">发布时间：</span>
									<span class="text">{{newsList[1].addtime.split(' ')[0]}}</span>
								</div>
								<div class="publisher_item">
									<span class="icon iconfont icon-geren16"></span>
									<span class="label">发布人：</span>
									<span class="text">{{newsList[1].name}}</span>
								</div>
							</div>
						</div>
						<div class="list-body">
							<div class="list-item animation-box" v-for="(item,index) in newsList" :key="index" @click="toDetail('newsDetail', item)" v-if="index>=(Number(3) + 2)&&index<(Number(3) + Number(3) + 2)">
								<div class="dian"></div>
								<div class="name">{{item.title}}</div>
								<div class="time-item">{{item.addtime.split(' ')[0]}}</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="moreBtn" @click="moreBtn('news')">
				<span class="text">查看更多</span>
				<i class="icon iconfont icon-jiantou25"></i>
			</div>
		</div>
		<!-- 新闻资讯 -->
		<!-- 特价商品 -->
		<div id="animate_listshuizuguanxinxi" class="lists animate__animated">
			<div class="list_title_box">
				<span class="list_title">水族馆信息展示</span>
				<span class="list_subhead">{{'shuizuguanxinxi'.toUpperCase()}} SHOW</span>
			</div>
			<div class="list list19 index-pv1">
				<div class="list-item" v-for="(item,index) in shuizuguanxinxiList" :key="index" v-if="index<Number(7)" @click="toDetail('shuizuguanxinxiDetail', item)">
					<img v-if="preHttp(item.changguantupian)" :src="item.changguantupian.split(',')[0]" alt="" />
					<img v-else :src="$assetUrl(item.changguantupian ? item.changguantupian.split(',')[0] : '')" alt="" />
					<div class="name">{{item.changguanmingcheng}}</div>
				</div>
			</div>
			<div class="moreBtn" @click="moreBtn('shuizuguanxinxi')">
				<span class="text">查看更多</span>
				<i class="icon iconfont icon-jiantou25"></i>
			</div>
	

		</div>
		<!-- 特价商品 -->
		<!-- 特价商品 -->
		<div id="animate_listsiyangjiaoxue" class="lists animate__animated">
			<div class="list_title_box">
				<span class="list_title">饲养教学展示</span>
				<span class="list_subhead">{{'siyangjiaoxue'.toUpperCase()}} SHOW</span>
			</div>
			<div class="list list19 index-pv1">
				<div class="list-item" v-for="(item,index) in siyangjiaoxueList" :key="index" v-if="index<Number(7)" @click="toDetail('siyangjiaoxueDetail', item)">
					<img v-if="preHttp(item.fengmian)" :src="item.fengmian.split(',')[0]" alt="" />
					<img v-else :src="baseUrl + (item.fengmian?item.fengmian.split(',')[0]:'')" alt="" />
					<div class="name">{{item.jiaoxuebiaoti}}</div>
					<div class="name">{{item.pinzhongfenlei}}</div>
				</div>
			</div>
			<div class="moreBtn" @click="moreBtn('siyangjiaoxue')">
				<span class="text">查看更多</span>
				<i class="icon iconfont icon-jiantou25"></i>
			</div>
	

		</div>
		<!-- 特价商品 -->
		<!-- 特价商品 -->
		<div id="animate_listxianshangshangcheng" class="lists animate__animated">
			<div class="list_title_box">
				<span class="list_title">线上商城展示</span>
				<span class="list_subhead">{{'xianshangshangcheng'.toUpperCase()}} SHOW</span>
			</div>
			<div class="list list19 index-pv1">
				<div class="list-item" v-for="(item,index) in xianshangshangchengList" :key="index" v-if="index<Number(7)" @click="toDetail('xianshangshangchengDetail', item)">
					<img v-if="preHttp(item.shangpintupian)" :src="item.shangpintupian.split(',')[0]" alt="" />
					<img v-else :src="$assetUrl(item.shangpintupian ? item.shangpintupian.split(',')[0] : '')" alt="" />
					<div class="name">{{item.shangpinmingcheng}}</div>
					<div class="name">{{item.shangpinfenlei}}</div>
				</div>
			</div>
			<div class="moreBtn" @click="moreBtn('xianshangshangcheng')">
				<span class="text">查看更多</span>
				<i class="icon iconfont icon-jiantou25"></i>
			</div>
	

		</div>
		<!-- 特价商品 -->
	</div>
</template>

<script>
import 'animate.css'
import Swiper from "swiper";

	export default {
		//数据集合
		data() {
			return {
				baseUrl: '',
				recommendNewsList: [],
				recommendPreference: '',
				newsList: [],

				shuizuguanxinxiList: [],
				siyangjiaoxueList: [],
				xianshangshangchengList: [],




			}
		},
		created() {
			this.baseUrl = this.$config.baseUrl;
			this.getRecommendNewsList();
			this.getNewsList();
			this.getList();
		},
		mounted() {
			window.addEventListener('scroll', this.handleScroll)
			setTimeout(()=>{
				this.handleScroll()
			},100)
			
			this.swiperChanges()
		},
		beforeDestroy() {
			window.removeEventListener('scroll', this.handleScroll)
		},
		computed: {
			username() {
				return localStorage.getItem('username')
			}
		},
		//方法集合
		methods: {
			swiperChanges() {
				setTimeout(()=>{
				},750)
			},

			async recommendIndexClick23(index, name) {
				this['recommendIndex23' + name] = index
				await this.getList()
			},

			handleScroll() {
				let arr = [
					{id:'about',css:'animate__'},
					{id:'system',css:'animate__'},
					{id:'animate_recommendnews',css:'animate__'},
					{id:'animate_listshuizuguanxinxi',css:'animate__'},
					{id:'animate_listsiyangjiaoxue',css:'animate__'},
					{id:'animate_listxianshangshangcheng',css:'animate__'},
					{id:'animate_newsnews',css:'animate__'},
				]
			
				for (let i in arr) {
					let doc = document.getElementById(arr[i].id)
					if (doc) {
						let top = doc.offsetTop
						let win_top = window.innerHeight + window.pageYOffset
						// console.log(top,win_top)
						if (win_top > top && doc.classList.value.indexOf(arr[i].css) < 0) {
							// console.log(doc)
							doc.classList.add(arr[i].css)
						}
					}
				}
			},
			preHttp(str) {
				return str && str.substr(0,4)=='http';
			},
			preHttp2(str) {
				return str && str.split(',w').length>1;
			},
			getNewsList() {
				let data = {
					page: 1,
					limit: 5,
					sort: 'addtime',
					order: 'desc'
				}
				this.$http.get('news/list', {params: data}).then(res => {
					if (res.data.code == 0) {
						this.newsList = res.data.data.list;
					
					}
				});
			},
			getRecommendNewsList() {
				this.$http.get('news/recommend', {params: {limit: 4}}).then(res => {
					if (res.data.code == 0 && res.data.data) {
						this.recommendNewsList = res.data.data.list || [];
						this.recommendPreference = res.data.data.preference || '';
					}
				});
			},
			getList() {
				let autoSortUrl = "";
				let data = {}
			
				data = {
					page: 1,
					limit: 7,
				}

				this.$http.get('shuizuguanxinxi/list', {params: data}).then(res => {
					if (res.data.code == 0) {
						this.shuizuguanxinxiList = res.data.data.list;
					}
				});
				data = {
					page: 1,
					limit: 7,
				}

				this.$http.get('siyangjiaoxue/list', {params: data}).then(res => {
					if (res.data.code == 0) {
						this.siyangjiaoxueList = res.data.data.list;
					}
				});
				data = {
					page: 1,
					limit: 7,
					onshelves: 1,
				}

				this.$http.get('xianshangshangcheng/list', {params: data}).then(res => {
					if (res.data.code == 0) {
						this.xianshangshangchengList = res.data.data.list;
					}
				});
			},
			toDetail(path, item) {
				this.$router.push({path: '/index/' + path, query: {id: item.id, storeupType: 1}});
			},
			moreBtn(path) {
				this.$router.push({path: '/index/' + path});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
		.home-preview {
		padding: 0;
		margin: 0 auto;
		overflow: hidden;
		display: flex;
		width: 100%;
		justify-content: space-between;
		flex-wrap: wrap;
		.news {
			padding: 0px 10% 0px;
			margin: 0 auto;
			background: none;
			width: 100%;
			position: relative;
			order: 10;
			.news_title_box {
				padding: 0 0 0 40px;
				margin: 30px 0 0px 0;
				background: url(http://codegen.caihongy.cn/20251211/bc94113c89904f5fbfb69bcfecad7022.png) no-repeat left ;
				display: block;
				width: 100%;
				justify-content: center;
				align-items: left;
				flex-wrap: wrap;
				height: auto;
				.news_title {
					padding: 0;
					color: #009740;
					background: none;
					font-weight: 600;
					display: block;
					letter-spacing: 10px;
					width: 100%;
					font-size: 30px;
					line-height: 1.5;
					text-align: left;
				}
				.news_subhead {
					margin: 0;
					color: #CBCBCB;
					font-weight: 500;
					display: none;
					width: 100%;
					font-size: 20px;
					line-height: 2;
					text-align: center;
				}
			}
			.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, -4px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
			}
			.index-pv1 .animation-box img:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
			.list29 {
				padding: 0;
				overflow: hidden;
				color: #666;
				background: none;
				clear: both;
				width: 100%;
				font-size: 12px;
				.list29-div {
					padding: 0;
					margin: 20px auto 0;
					overflow: hidden;
					background: none;
					width: 100%;
					clear: both;
					.list-body-left {
						width: 48%;
						float: left;
						.list-item1 {
							border: 1px solid #969696;
							cursor: pointer;
							padding: 0 0 20px;
							margin: 0 0 10px;
							display: flex;
							width: 100%;
							border-width: 0 0 1px;
							.img {
								display: block;
								width: auto;
								float: left;
								height: auto;
								img {
									margin: 0 20px 0 0;
									object-fit: cover;
									display: block;
									width: 210px;
									height: 158px;
								}
							}
							.infoBox {
								.name {
									margin: 0 0 10px;
									overflow: hidden;
									color: #181818;
									white-space: nowrap;
									font-size: 18px;
									line-height: 24px;
									text-overflow: ellipsis;
									transition: all .3s;
									height: 24px;
								}
								.desc {
									margin: 0 0 5px;
									overflow: hidden;
									color: #000;
									font-weight: 300;
									font-size: 14px;
									line-height: 24px;
									height: 72px;
								}
								.time_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.publisher_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.like_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.collect_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.view_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
							}
						}
						.list-body {
							line-height: 55px;
							.list-item {
								cursor: pointer;
								overflow: hidden;
								display: flex;
								width: 100%;
								line-height: 40px;
								justify-content: space-between;
								align-items: center;
								.dian {
									border-radius: 100%;
									margin: -1px 10px 0 0;
									background: #434343;
									display: inline-block;
									vertical-align: middle;
									width: 8px;
									position: relative;
									height: 8px;
								}
								.name {
									overflow: hidden;
									color: #181818;
									white-space: nowrap;
									flex: 1;
									display: block;
									font-size: 16px;
									text-overflow: ellipsis;
								}
								.time-item {
									color: #666;
									font-weight: 300;
									font-size: 14px;
									float: right;
								}
							}
						}
					}
					.list-body-right {
						width: 48%;
						float: right;
						.list-item1 {
							border: 1px solid #969696;
							cursor: pointer;
							padding: 0 0 20px;
							margin: 0 0 10px;
							display: flex;
							width: 100%;
							border-width: 0 0 1px;
							.img {
								display: block;
								width: auto;
								float: left;
								height: auto;
								img {
									margin: 0 20px 0 0;
									object-fit: cover;
									width: 210px;
									height: 158px;
								}
							}
							.infoBox {
								.name {
									margin: 0 0 10px;
									overflow: hidden;
									color: #181818;
									white-space: nowrap;
									font-size: 18px;
									line-height: 24px;
									text-overflow: ellipsis;
									transition: all .3s;
									height: 24px;
								}
								.desc {
									margin: 0 0 5px;
									overflow: hidden;
									color: #000;
									font-weight: 300;
									font-size: 14px;
									line-height: 24px;
									height: 72px;
								}
								.time_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.publisher_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.like_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.collect_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
								.view_item {
									padding: 0 10px 0 0;
									display: inline-block;
									.iconfont {
										margin: 0 2px 0 0;
										line-height: 1.5;
									}
									.label {
										line-height: 1.5;
									}
									.text {
										line-height: 1.5;
									}
								}
							}
						}
						.list-body {
							line-height: 55px;
							.list-item {
								cursor: pointer;
								overflow: hidden;
								display: flex;
								width: 100%;
								line-height: 40px;
								justify-content: space-between;
								align-items: center;
								.dian {
									border-radius: 100%;
									margin: -1px 10px 0 0;
									background: #434343;
									display: inline-block;
									vertical-align: middle;
									width: 8px;
									position: relative;
									height: 8px;
								}
								.name {
									overflow: hidden;
									color: #181818;
									white-space: nowrap;
									flex: 1;
									display: block;
									font-size: 16px;
									text-overflow: ellipsis;
								}
								.time-item {
									color: #666;
									font-weight: 300;
									font-size: 14px;
									float: right;
								}
							}
						}
					}
				}
			}
			.moreBtn {
				border: 0px solid #ddd;
				cursor: pointer;
				margin: 30px auto 0;
				display: none;
				line-height: 40px;
				right: 7%;
				border-radius: 0px;
				top: 10px;
				background: #6aac5a;
				width: 120px;
				justify-content: center;
				position: absolute;
				text-align: center;
				.text {
					margin: 0 5px 0 0;
					color: #fff;
					font-size: 16px;
					order: 2;
				}
				.icon {
					color: #999;
					background: none;
					display: none;
					width: 23px;
					font-size: 16px;
					height: 7px;
				}
			}
		}
		.recommend-news {
			padding: 20px 10% 0;
			margin: 0 auto;
			width: 100%;
			order: 8;
			.news_title_box {
				padding: 0 0 0 40px;
				margin: 20px 0 0;
				background: url(http://codegen.caihongy.cn/20251211/bc94113c89904f5fbfb69bcfecad7022.png) no-repeat left;
				width: 100%;
				.news_title {
					color: #0f6fa8;
					font-weight: 600;
					display: block;
					letter-spacing: 8px;
					font-size: 30px;
					line-height: 1.5;
				}
				.news_subhead {
					color: #6c92a8;
					font-size: 16px;
					line-height: 2;
				}
			}
			.recommend-grid {
				display: grid;
				grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
				gap: 20px;
				margin: 24px 0 10px;
			}
			.recommend-card {
				cursor: pointer;
				background: linear-gradient(180deg, #f3fbff 0%, #ffffff 100%);
				border: 1px solid #d7edf7;
				border-radius: 18px;
				overflow: hidden;
				box-shadow: 0 12px 30px rgba(15, 111, 168, 0.08);
			}
			.recommend-image {
				height: 180px;
				img {
					width: 100%;
					height: 100%;
					object-fit: cover;
					display: block;
				}
			}
			.recommend-content {
				padding: 16px;
			}
			.recommend-tag {
				display: inline-block;
				padding: 4px 10px;
				margin-bottom: 10px;
				border-radius: 999px;
				background: #dff3fb;
				color: #0f6fa8;
				font-size: 13px;
			}
			.recommend-name {
				color: #163246;
				font-weight: 600;
				font-size: 17px;
				line-height: 1.6;
				margin-bottom: 8px;
			}
			.recommend-desc {
				color: #5e7280;
				font-size: 14px;
				line-height: 1.7;
				height: 48px;
				overflow: hidden;
			}
		}
		.lists {
			padding: 20px 10% 20px;
			margin: 0 auto;
			background: none;
			width: 100%;
			position: relative;
			order: 4;
			.list_title_box {
				padding: 0 0 0 40px;
				margin: 20px auto;
				background: url(http://codegen.caihongy.cn/20251211/bc94113c89904f5fbfb69bcfecad7022.png) no-repeat left ;
				display: flex;
				width: 100%;
				justify-content: center;
				flex-wrap: wrap;
				.list_title {
					padding: 0;
					color: #009740;
					background: none;
					font-weight: 600;
					display: block;
					letter-spacing: 10px;
					width: 100%;
					font-size: 30px;
					line-height: 1.5;
					text-align: left;
				}
				.list_subhead {
					margin: 0;
					color: #CBCBCB;
					font-weight: 500;
					display: none;
					width: auto;
					font-size: 20px;
					line-height: 2;
					text-align: center;
				}
			}
			.index-pv1 .animation-box {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				z-index: initial;
			}
			
			.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, -5px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
			}
			
			.index-pv1 .animation-box img {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
			}
			
			.index-pv1 .animation-box img:hover {
				transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
			.list19 {
				padding: 0;
				margin: 20px auto 120px;
				transform: translateY(34px);
				max-width: 1200px;
				display: flex;
				justify-content: center;
				align-items: center;
				list-style: none;
				flex-wrap: wrap;
				.list-item {
					cursor: pointer;
					padding: 0.5em;
					margin: 65px 12px 25px;
					z-index: 1;
					max-width: 250px;
					flex: 0 1 250px;
					position: relative;
					text-align: center;
					height: 137px;
					img {
						z-index: -1;
						clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
						top: -50%;
						object-position: center;
						left: 0;
						object-fit: cover;
						display: block;
						width: 100%;
						position: absolute;
						height: 200%;
					}
					.name {
						z-index: 2;
						color: #fff;
						font-weight: 600;
						display: flex;
						font-size: 16px;
						transition: opacity 350ms;
						text-transform: uppercase;
						flex-direction: column;
						word-break: break-word;
						justify-content: center;
						position: relative;
						hyphens: auto;
						height: 100%;
					}
				}
				.list-item:hover {
					.name {
						opacity: 0;
					}
				}
				.list-item::before {
					transform: scale(1.055);
					z-index: -1;
					clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
					top: -50%;
					left: 0;
					background: #fff;
					display: block;
					width: 100%;
					position: absolute;
					content: "";
					height: 200%;
				}
				.list-item::after {
					z-index: 1;
					clip-path: polygon(50% 0%, 100% 25%, 100% 75%, 50% 100%, 0% 75%, 0% 25%);
					top: -50%;
					left: 0;
					background: #3ea0eb;
					display: block;
					width: 100%;
					position: absolute;
					opacity: 0.5;
					content: "";
					transition: opacity 350ms;
					height: 200%;
				}
				.list-item:hover::before {
					background: #72f88e;
				}
				.list-item:hover::after {
					opacity: 0;
				}
				@media (max-width:550px) {
					.list-item {
						margin: 81.25px 25px !important;
					}
				}
				
				@media (min-width:550px) and (max-width:825px) {
					.list-item:nth-child(3n) {
						margin-right: calc(50% - 125px) !important;
						margin-left: calc(50% - 125px) !important;
					}
				
					.honeycomb__placeholder:nth-child(3n + 5) {
						display: block !important;
					}
				}
				
				@media (min-width:825px) and (max-width:1100px) {
					.list-item:nth-child(5n + 4) {
						margin-left: calc(50% - 275px) !important;
					}
				
					.list-item:nth-child(5n + 5) {
						margin-right: calc(50% - 275px) !important;
					}
				
					.honeycomb__placeholder:nth-child(5n),
					.honeycomb__placeholder:nth-child(5n + 3) {
						display: block !important;
					}
				}
				
				@media (min-width:1100px) {
					.list-item:nth-child(7n + 5) {
						margin-left: calc(50% - 400px) !important;
					}
				
					.list-item:nth-child(7n + 7),
					.list-item:nth-child(7n + 5):nth-last-child(2) {
						margin-right: calc(50% - 400px) !important;
					}
				
					.honeycomb__placeholder:nth-child(7n + 7),
					.honeycomb__placeholder:nth-child(7n + 9),
					.honeycomb__placeholder:nth-child(7n + 11) {
						display: block !important;
					}
				}
			}
			.moreBtn {
				border: 0px solid #ddd;
				cursor: pointer;
				margin: 0px auto;
				display: block;
				line-height: 40px;
				right: 10%;
				border-radius: 0px;
				top: 50px;
				background: #6aac5a;
				width: 120px;
				justify-content: center;
				position: absolute;
				text-align: center;
				.text {
					margin: 0 5px 0 0;
					color: #fff;
					font-size: 16px;
					order: 2;
				}
				.icon {
					color: #999;
					background: none;
					display: none;
					width: 23px;
					font-size: 16px;
					height: 7px;
				}
			}
		}
	}
</style>
