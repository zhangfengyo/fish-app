<template>
	<div class="home-content" :style="{'backgroundImage': indexBgUrl?`url($assetUrl(indexBgUrl))`:''}">
		<!-- title -->
		<div id="home-title" class="home-title animate__animated">
			<div class="titles" >
				<span>欢迎使用</span>
				{{this.$project.projectName}}
			</div>
		</div>
		<!-- statis -->
		<div class="statis-box">
			<div id="statis1" class="statis1 animate__animated" v-if="isAuth('orders','首页总数')">
				<div class="left">
					<span class="icon iconfont icon-zhangjie8"></span>
				</div>
				<div class="right">
					<div class="num">{{ordersCount}}</div>
					<div class="name">订单总数</div>
				</div>
			</div>
		</div>
		<!-- statis -->
		<div id="news-box" class="news-box animate__animated">
			<div class="news-title">
				观赏鱼资讯
			</div>
			<div class="news-list">
				<div class="news-item" v-for="(item,index) in newsList" :index="index" @click="newsDetailClick(item)">
					<div class="news-text">
						{{item.title}}
					</div>
					<div class="news-img-box">
						<img :src="item.picture?$assetUrl(item.picture.split(',')[0]):''" alt="">
					</div>
					<div class="news-desc">
						{{item.introduction}}
					</div>
					<div class="news-time">
						{{item.addtime}}
					</div>
				</div>
			</div>
		</div>
		<el-dialog :visible.sync="newsVisible" title="观赏鱼资讯" :append-to-body="true" width="60%">
			<div style="width: 100%;text-align: center;font-size: 20px;color: #000;padding: 0 0 20px;font-weight: bold;">{{newsDetail.title}}</div>
			<div style="width: 100%;">
				<div class="ql-snow ql-editor" v-html="newsDetail.content"></div>
			</div>
		</el-dialog>
	
		<!-- echarts -->
		<!-- 4 -->
		<div class="type4">
			<div class="echarts1 animate__animated" v-if="isAuth('orders','首页统计',2)">
				<el-select
					v-if="changeStatQuery(['users'])"
					v-model="orderschartQuery1.goodname" placeholder="商品名称" @change="ordersChat1" clearable>
					<el-option v-for="item in ordersChartOptions1" :label="item" :value="item"></el-option>
				</el-select>
				<div id="ordersChart1" style="width: 100%;height: 100%"></div>
			</div>
			<div class="echarts2 animate__animated" v-if="isAuth('orders','首页统计',2)">
				<el-select
					v-if="changeStatQuery(['users'])"
					v-model="orderschartQuery2.goodname" placeholder="商品名称" @change="ordersChat2" clearable>
					<el-option v-for="item in ordersChartOptions2" :label="item" :value="item"></el-option>
				</el-select>
				<div id="ordersChart2" style="width: 100%;height: 100%"></div>
			</div>
			<div class="echarts3 animate__animated" v-if="isAuth('orders','首页统计',2)">
				<el-select
					v-if="changeStatQuery(['users'])"
					v-model="orderschartQuery3.goodtype" placeholder="商品类型" @change="ordersChat3" clearable>
					<el-option v-for="item in ordersChartOptions3" :label="item" :value="item"></el-option>
				</el-select>
				<div id="ordersChart3" style="width: 100%;height: 100%"></div>
			</div>
			<div class="echarts4 animate__animated" v-if="isAuth('orders','首页统计',2)">
				<el-select
					v-if="changeStatQuery(['users'])"
					v-model="orderschartQuery4.goodtype" placeholder="商品类型" @change="ordersChat4" clearable>
					<el-option v-for="item in ordersChartOptions4" :label="item" :value="item"></el-option>
				</el-select>
				<div id="ordersChart4" style="width: 100%;height: 100%"></div>
			</div>
		</div>
		<!-- echarts -->
	</div>
</template>
<script>
import 'animate.css'
//4
import router from '@/router/router-static'
import * as echarts from 'echarts'
export default {
	data() {
		return {
			ordersCount: 0,
			orderschartQuery1: {},
			ordersChartOptions1: [],
			orderschartQuery2: {},
			ordersChartOptions2: [],
			orderschartQuery3: {},
			ordersChartOptions3: [],
			orderschartQuery4: {},
			ordersChartOptions4: [],
			line: {"backgroundColor":"transparent","yAxis":{"axisLabel":{"borderType":"solid","rotate":0,"padding":0,"shadowOffsetX":0,"margin":15,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"width":"","fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,0.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"xAxis":{"axisLabel":{"borderType":"solid","rotate":30,"padding":0,"shadowOffsetX":0,"margin":10,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"truncate","borderRadius":0,"borderWidth":0,"width":120,"interval":0,"fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":false},"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"color":["#2dc7a3","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"legend":{"shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":0,"itemGap":10,"backgroundColor":"transparent","orient":"vertical","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","borderRadius":0,"left":"right","width":"80%","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"series":{"showSymbol":true,"symbol":"emptyCircle","symbolSize":4},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"borderType":"solid","padding":0,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"}},
			bar: {"backgroundColor":"transparent","yAxis":{"axisLabel":{"borderType":"solid","rotate":0,"padding":0,"shadowOffsetX":0,"margin":12,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"width":"","fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,0.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"xAxis":{"axisLabel":{"borderType":"solid","rotate":30,"padding":0,"shadowOffsetX":0,"margin":10,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"truncate","borderRadius":0,"borderWidth":0,"width":120,"interval":0,"fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":false},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"color":["#2dc7a3","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"legend":{"shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":0,"itemGap":10,"backgroundColor":"transparent","orient":"vertical","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","borderRadius":0,"left":"right","width":"80%","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"grid":{"x":"25%","y":"15%","y2":"15%","x2":"5%"},"series":{"barWidth":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"colorBy":"data","barCategoryGap":"40%"},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"borderType":"solid","padding":0,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"},"base":{"animate":false,"interval":2000}},
			pie: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#2dc7a3","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"title":{"borderType":"solid","padding":[5,0,0,0],"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":14,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"},"legend":{"shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":[5,0,0,0],"itemGap":10,"backgroundColor":"transparent","orient":"horizontal","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":0,"borderRadius":0,"left":"right","width":"80%","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"series":{"itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"label":{"borderType":"solid","rotate":0,"padding":0,"textBorderWidth":0,"backgroundColor":"transparent","borderColor":"#666","color":"inherit","show":true,"textShadowColor":"transparent","distanceToLabelLine":5,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"fontSize":12,"lineHeight":18,"textShadowOffsetX":0,"position":"outside","textShadowOffsetY":0,"textBorderType":"solid","textBorderColor":"#666","textShadowBlur":0},"labelLine":{"show":true,"length":10,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"#000"},"length2":14,"smooth":false}}},
			funnel: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#6C63FF","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"title":{"borderType":"solid","padding":2,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#ccc","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"center","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#666","textShadowColor":"transparent","fontSize":14,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"#ccc","textShadowBlur":0},"shadowColor":"transparent"},"legend":{"padding":5,"itemGap":10,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#ccc","shadowOffsetY":0,"orient":"vertical","shadowBlur":0,"bottom":"auto","itemHeight":2,"show":true,"icon":"roundRect","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"top":"auto","borderRadius":0,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"left":"left","borderWidth":0,"width":"auto","itemWidth":2,"textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":20,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0},"shadowColor":"rgba(0,0,0,.3)","height":"auto"},"series":{"itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#000","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"label":{"borderType":"solid","rotate":0,"padding":0,"textBorderWidth":0,"backgroundColor":"transparent","borderColor":"#fff","color":"","show":true,"textShadowColor":"transparent","distanceToLabelLine":5,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"fontSize":12,"lineHeight":18,"textShadowOffsetX":0,"position":"outside","textShadowOffsetY":0,"textBorderType":"solid","textBorderColor":"#fff","textShadowBlur":0},"labelLine":{"show":true,"length":10,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"#000"},"length2":14,"smooth":false}}},
			boardBase: {"funnelNum":8,"lineNum":8,"radarNum":8,"gaugeNum":8,"barNum":8,"pieNum":8},
			gauge: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#fc8452","#91cc75","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"title":{"top":"top","left":"left","textStyle":{"fontSize":14,"lineHeight":24,"color":"#333","fontWeight":600}},"series":{"pointer":{"offsetCenter":[0,"10%"],"icon":"path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z","width":8,"length":"80%"},"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"opacity":0.5,"shadowBlur":1,"shadowColor":"#000"},"roundCap":true},"anchor":{"show":true,"itemStyle":{"color":"inherit"},"size":18,"showAbove":true},"emphasis":{"disabled":false},"progress":{"show":true,"roundCap":true,"overlap":true},"splitNumber":25,"detail":{"formatter":"{value}","backgroundColor":"inherit","color":"#fff","borderRadius":3,"width":20,"fontSize":12,"height":10},"title":{"fontSize":14},"animation":true}},
			radar: {"backgroundColor":"transparent","radar":{"shape":"circle","radius":"55%"},"color":["#5470c6","#91cc75","#fac858","#ee6666","#73c0de","#3ba272","#fc8452","#9a60b4","#ea7ccc"],"legend":{"shadowOffsetX":0,"borderColor":"#ccc","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":5,"itemGap":10,"backgroundColor":"transparent","orient":"horizontal","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","borderRadius":0,"left":"right","width":"auto","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"series":{},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"top":"bottom","left":"left"}},
			newsList: [],
			newsDetail: {},
			newsVisible: false,
			indexBgUrl: '',
			indexLogoUrl: '',
		};
	},
	mounted(){
		this.init();
		this.getordersCount();
		if(this.isAuth('orders','首页统计',2)){
			this.getordersgoodnameOptions1()
			this.ordersChat1();
		}
		if(this.isAuth('orders','首页统计',2)){
			this.getordersgoodnameOptions2()
			this.ordersChat2();
		}
		if(this.isAuth('orders','首页统计',2)){
			this.getordersgoodtypeOptions3()
			this.ordersChat3();
		}
		if(this.isAuth('orders','首页统计',2)){
			this.getordersgoodtypeOptions4()
			this.ordersChat4();
		}
		window.addEventListener('scroll', this.handleScroll)
		setTimeout(()=>{
			this.handleScroll()
		},100)
	},
	created() {
		this.$http.get('config/info?name=bIndexBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
		this.$http.get('config/info?name=bHomeLogo',).then(rs=>{this.indexLogoUrl = rs.data.data?rs.data.data.value:''})
	},
	computed: {
		sessionForm() {
			return JSON.parse(this.$storage.getObj('userForm'))
		},
		avatar(){
			return this.$storage.get('headportrait')?this.$storage.get('headportrait'):''
		},
	},
	methods:{
		handleScroll() {
			let arr = [
				{id:'home-title',css:'animate__'},
				{id:'statis1',css:'animate__'},
				{id:'news-box',css:'animate__'},
				{id:'ordersChart1',css:'animate__'},
				{id:'ordersChart2',css:'animate__'},
				{id:'ordersChart3',css:'animate__'},
				{id:'ordersChart4',css:'animate__'},
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
		// 统计图动画
		myChartInterval(type, xAxisData, seriesData, myChart) {
			this.$nextTick(() => {
				setInterval(() => {
					let xAxis = xAxisData.shift()
					xAxisData.push(xAxis)
					let series = seriesData.shift()
					seriesData.push(series)
				
					if (type == 1) {
						myChart.setOption({
							xAxis: [{
								data: xAxisData
							}],
							series: [{
								data: seriesData
							}]
						});
					}
					if (type == 2) {
						myChart.setOption({
							yAxis: [{
								data: xAxisData
							}],
							series: [{
								data: seriesData
							}]
						});
					}
				}, $template2.back.board.bar.base.interval);
			})
		},
		changeStatQuery(arr) {
			if(arr.length==1) {
				if(arr[0] == 'users'&&this.$storage.get("sessionTable")=='users') {
					return true
				}
			}
			let role = this.$storage.get('role')
			for(let x in arr) {
				if(arr[x] == role) {
					return true
				}
			}
			return false
		},
		init(){
			if(this.$storage.get('Token')){
				this.$http({
					url: `${this.$storage.get('sessionTable')}/session`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code != 0) {
						router.push({ name: 'login' })
					}
				});
			}else{
				router.push({ name: 'login' })
			}
			this.getNewsList()
		},
		getNewsList(){
			let params = {
				page: 1,
				limit: 8,
				sort: 'addtime',
				order: 'desc',
			}
			this.$http({
				url: `news/list`,
				method: "get",
				params: params
			}).then(res => {
				if(res.data&&res.data.code==0){
					this.newsList = res.data.data.list
				}
			});
		},
		newsDetailClick(row){
			this.newsDetail = row
			this.newsVisible = true
		},
		getordersCount() {
			this.$http({
				url: `orders/count`,
				method: "get"
			}).then(({
				data
			}) => {
				if (data && data.code == 0) {
					this.ordersCount = data.data
				}
			})
		},
		getordersgoodnameOptions1() {
			this.$http.get('option/orders/goodname',
			).then(rs=>{
				this.ordersChartOptions1 = rs.data.data
			})
		},
		ordersChat1(e=null) {
			if(this.changeStatQuery(['users'])) {
				document.getElementById('ordersChart1').setAttribute('style','width: 100%;height: calc(100% - 50px)')
			}
			this.$nextTick(()=>{
				var ordersChart1 = echarts.init(document.getElementById("ordersChart1"),'macarons');
				let params = {
				}
				if(this.orderschartQuery1.goodname) {
					params.conditionColumn = 'goodname'
					params.conditionValue = this.orderschartQuery1.goodname
				}
				this.$http({
					url: `orders/value/goodname/buynumber`,
					method: "get",
					params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let res = data.data||[];
						// 统计图设置对了吗
						let xAxis = [];
						let yAxis = [];
						let pArray = []
						res.sort((a,b)=>{
							return b.total - a.total
						})
						for(let i=0;i<res.length;i++){
							if(this.boardBase&&i==this.boardBase.barNum){
								break;
							}
							xAxis.push(res[i].goodname);
							yAxis.push(parseFloat((res[i].total)));
							pArray.push({
								value: parseFloat((res[i].total)),
								name: res[i].goodname
							})
						}
						var option = {};
						let titleObj = this.bar.title
						titleObj.text = '商品销量'
						
						const legendObj = this.bar.legend
						let tooltipObj = {trigger: 'item',formatter: '{b} : {c}'}
						tooltipObj = Object.assign(tooltipObj , this.bar.tooltip?this.bar.tooltip:{})
				
						let xAxisObj = this.bar.xAxis
						xAxisObj.type = 'category'
						xAxisObj.data = xAxis
						
						let yAxisObj = this.bar.yAxis
						yAxisObj.type = 'value'
						let seriesObj = {
							data: yAxis,
							type: 'bar',
						}
						seriesObj = Object.assign(seriesObj , this.bar.series)
						const gridObj = this.bar.grid
						
						option = {
							backgroundColor: this.bar.backgroundColor,
							color: this.bar.color,
							title: titleObj,
							legend: legendObj,
							grid: gridObj,
							tooltip: tooltipObj,
							xAxis: xAxisObj,
							yAxis: yAxisObj,
							series: [seriesObj]
						};
						// 使用刚指定的配置项和数据显示图表。
						ordersChart1.setOption(option);
				
						//根据窗口的大小变动图表
						window.onresize = function() {
							ordersChart1.resize();
						};
					}else{
						this.$message({
							message: data.msg,
							type: "warning",
							duration: 1500,
						})
					}
				});
			})
		},

		getordersgoodnameOptions2() {
			this.$http.get('option/orders/goodname',
			).then(rs=>{
				this.ordersChartOptions2 = rs.data.data
			})
		},
		ordersChat2(e=null) {
			if(this.changeStatQuery(['users'])) {
				document.getElementById('ordersChart2').setAttribute('style','width: 100%;height: calc(100% - 50px)')
			}
			this.$nextTick(()=>{

				var ordersChart2 = echarts.init(document.getElementById("ordersChart2"),'macarons');
				let params = {
				}
				if(this.orderschartQuery2.goodname) {
					params.conditionColumn = 'goodname'
					params.conditionValue = this.orderschartQuery2.goodname
				}
				this.$http({
					url: `orders/value/goodname/total`,
					method: "get",
					params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let res = data.data||[];
						// 统计图设置对了吗
						let xAxis = [];
						let yAxis = [];
						let pArray = []
						res.sort((a,b)=>{
							return b.total - a.total
						})
						for(let i=0;i<res.length;i++){
							if(this.boardBase&&i==this.boardBase.lineNum){
								break;
							}
							xAxis.push(res[i].goodname);
							yAxis.push(parseFloat((res[i].total)));
							pArray.push({
								value: parseFloat((res[i].total)),
								name: res[i].goodname
							})
						}
						var option = {};
						let titleObj = this.line.title
						titleObj.text = '商品销额'
						
						const legendObj = this.line.legend
						let tooltipObj = { trigger: 'item',formatter: '{b} : {c}'}
						tooltipObj = Object.assign(tooltipObj , this.line.tooltip?this.line.tooltip:{})
						
						let xAxisObj = this.line.xAxis
						xAxisObj.type = 'category'
						xAxisObj.data = xAxis
						
						let yAxisObj = this.line.yAxis
						yAxisObj.type = 'value'
						
						let seriesObj = {
							data: yAxis,
							type: 'line',
						}
						seriesObj = Object.assign(seriesObj , this.line.series)
						const gridObj = this.line.grid
						
						option = {
							backgroundColor: this.line.backgroundColor,
							color: this.line.color,
							title: titleObj,
							legend: legendObj,
							tooltip: tooltipObj,
							xAxis: xAxisObj,
							yAxis: yAxisObj,
							series: [seriesObj],
							grid: gridObj,
						};
						// 使用刚指定的配置项和数据显示图表。
						ordersChart2.setOption(option);
				
						//根据窗口的大小变动图表
						window.onresize = function() {
							ordersChart2.resize();
						};
					}else{
						this.$message({
							message: data.msg,
							type: "warning",
							duration: 1500,
						})
					}
				});
			})
		},

		getordersgoodtypeOptions3() {
			this.$http.get('option/orders/goodtype',
			).then(rs=>{
				this.ordersChartOptions3 = rs.data.data
			})
		},
		ordersChat3(e=null) {
			if(this.changeStatQuery(['users'])) {
				document.getElementById('ordersChart3').setAttribute('style','width: 100%;height: calc(100% - 50px)')
			}
			this.$nextTick(()=>{

				var ordersChart3 = echarts.init(document.getElementById("ordersChart3"),'macarons');
				let params = {
				}
				if(this.orderschartQuery3.goodtype) {
					params.conditionColumn = 'goodtype'
					params.conditionValue = this.orderschartQuery3.goodtype
				}
				this.$http({
					url: `orders/value/goodtype/buynumber`,
					method: "get",
					params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let res = data.data||[];
						// 统计图设置对了吗
						let xAxis = [];
						let yAxis = [];
						let pArray = []
						res.sort((a,b)=>{
							return b.total - a.total
						})
						for(let i=0;i<res.length;i++){
							if(this.boardBase&&i==this.boardBase.barNum){
								break;
							}
							xAxis.push(res[i].goodtype);
							yAxis.push(parseFloat((res[i].total)));
							pArray.push({
								value: parseFloat((res[i].total)),
								name: res[i].goodtype
							})
						}
						var option = {};
						let titleObj = this.bar.title
						titleObj.text = '商品分类销量'
						
						const legendObj = this.bar.legend
						let tooltipObj = {trigger: 'item',formatter: '{b} : {c}'}
						tooltipObj = Object.assign(tooltipObj , this.bar.tooltip?this.bar.tooltip:{})
				
						let xAxisObj = this.bar.xAxis
						xAxisObj.type = 'value'
						
						let yAxisObj = this.bar.yAxis
						yAxisObj.type = 'category'
						yAxisObj.data = xAxis
				
						let seriesObj = {
							data: yAxis,
							type: 'bar',
						}
						seriesObj = Object.assign(seriesObj , this.bar.series)
						const gridObj = this.bar.grid
						option = {
							backgroundColor: this.bar.backgroundColor,
							color: this.bar.color,
							title: titleObj,
							legend: legendObj,
							tooltip: tooltipObj,
							xAxis: xAxisObj,
							yAxis: yAxisObj,
							series: [seriesObj],
							grid: gridObj
						};
						// 使用刚指定的配置项和数据显示图表。
						ordersChart3.setOption(option);
						//根据窗口的大小变动图表
						window.onresize = function() {
							ordersChart3.resize();
						};
					}else{
						this.$message({
							message: data.msg,
							type: "warning",
							duration: 1500,
						})
					}
				});
			})
		},
		getordersgoodtypeOptions4() {
			this.$http.get('option/orders/goodtype',
			).then(rs=>{
				this.ordersChartOptions4 = rs.data.data
			})
		},
		ordersChat4(e=null) {
			if(this.changeStatQuery(['users'])) {
				document.getElementById('ordersChart4').setAttribute('style','width: 100%;height: calc(100% - 50px)')
			}
			this.$nextTick(()=>{

				var ordersChart4 = echarts.init(document.getElementById("ordersChart4"),'macarons');
				let params = {
				}
				if(this.orderschartQuery4.goodtype) {
					params.conditionColumn = 'goodtype'
					params.conditionValue = this.orderschartQuery4.goodtype
				}
				this.$http({
					url: `orders/value/goodtype/total`,
					method: "get",
					params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let res = data.data||[];
						// 统计图设置对了吗
						let xAxis = [];
						let yAxis = [];
						let pArray = []
						res.sort((a,b)=>{
							return b.total - a.total
						})
						for(let i=0;i<res.length;i++){
							if(this.boardBase&&i==this.boardBase.lineNum){
								break;
							}
							xAxis.push(res[i].goodtype);
							yAxis.push(parseFloat((res[i].total)));
							pArray.push({
								value: parseFloat((res[i].total)),
								name: res[i].goodtype
							})
						}
						var option = {};
						let titleObj = this.line.title
						titleObj.text = '商品分类销额'
						
						const legendObj = this.line.legend
						let tooltipObj = { trigger: 'item',formatter: '{b} : {c}'}
						tooltipObj = Object.assign(tooltipObj , this.line.tooltip?this.line.tooltip:{})
						
						let xAxisObj = this.line.xAxis
						xAxisObj.type = 'category'
						xAxisObj.data = xAxis
						
						let yAxisObj = this.line.yAxis
						yAxisObj.type = 'value'
						
						let seriesObj = {
							data: yAxis,
							type: 'line',
							smooth: true
						}
						seriesObj = Object.assign(seriesObj , this.line.series)
						const gridObj = this.line.grid
						option = {
							backgroundColor: this.line.backgroundColor,
							color: this.line.color,
							title: titleObj,
							legend: legendObj,
							tooltip: tooltipObj,
							xAxis: xAxisObj,
							yAxis: yAxisObj,
							series: [seriesObj],
							grid: gridObj
						};
						// 使用刚指定的配置项和数据显示图表。
						ordersChart4.setOption(option);

						//根据窗口的大小变动图表
						window.onresize = function() {
							ordersChart4.resize();
						};
					}else{
						this.$message({
							message: data.msg,
							type: "warning",
							duration: 1500,
						})
					}
				});
			})
		},
	}
};
</script>
<style lang="scss" scoped>
	.home-content {
		padding: 0 0 30px;
		background: url(http://codegen.caihongy.cn/20260124/7f0839b0814a41df8b351d0a809fdc6f.png) no-repeat center top / cover;
		display: flex;
		width: 100%;
		min-height: 100vh;
		flex-wrap: wrap;
		.home-title {
			border-radius: 4px;
			padding: 10px 0 0;
			box-shadow: 0 0px 0px #ddd;
			margin: 20px 0 0;
			background: none;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			transition: 0.3s;
			.titles {
				padding: 0;
				color: #333;
				font-weight: 500;
				font-size: 30px;
				line-height: 50px;
				span {
				}
			}
		}
		.home-title:hover {
			transform: translate3d(0, 0px, 0);
		}
		.statis-box {
			margin: 20px 0;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			flex-wrap: wrap;
			.statis1 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #2dc7a350;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #2dc7a3;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #2dc7a3;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #2dc7a3;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis1:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis2 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #01c0fe50;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #01c0fe;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #01c0fe;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #01c0fe;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis2:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis3 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #28d7cd50;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #28d7cd;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #28d7cd;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #28d7cd;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis3:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis4 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #38cb8650;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #38cb86;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #38cb86;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #38cb86;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis4:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis5 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #51bc3850;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #51bc38;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #51bc38;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #51bc38;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis5:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis6 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #01c0fe50;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #01c0fe;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #01c0fe;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #01c0fe;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis6:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis7 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #28d7cd50;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #28d7cd;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #28d7cd;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #28d7cd;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis7:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis8 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #38cb8650;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #38cb86;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #38cb86;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #38cb86;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis8:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis9 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #2dc7a350;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #2dc7a3;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #2dc7a3;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #2dc7a3;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis9:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
			.statis10 {
				border: 0px solid #eee;
				border-radius: 10px;
				box-shadow: none;
				padding: 10px;
				margin: 10px;
				flex-direction: column;
				background: none;
				display: flex;
				width: auto;
				align-items: center;
				transition: 0.3s;
				.left {
					border: 1px solid #01c0fe50;
					border-radius: 4px;
					background: #fff;
					display: flex;
					width: 100%;
					justify-content: center;
					align-items: center;
					height: 80px;
					order: 2;
					.iconfont {
						border-radius: 100%;
						padding: 0;
						color: #01c0fe;
						background: none;
						width: 50px;
						font-size: 42px;
						line-height: 50px;
						text-align: center;
						height: 50px;
					}
				}
				.right {
					flex-direction: column;
					flex: 1;
					display: flex;
					width: 160px;
					justify-content: center;
					align-items: center;
					order: 2;
					.num {
						margin: 5px 0;
						color: #01c0fe;
						font-weight: 600;
						font-size: 30px;
						line-height: 24px;
						height: 24px;
						order: 2;
					}
					.name {
						margin: 5px 0;
						color: #01c0fe;
						font-size: 14px;
						line-height: 24px;
						height: 24px;
					}
				}
			}
			.statis10:hover {
				transform: translate3d(0, -5px, 0);
				z-index: 1;
			}
		}
		.news-box {
			border: 1px solid #eee;
			padding: 0 20px 20px;
			margin: 10px;
			display: flex;
			transition: 0.3s;
			border-radius: 10px;
			flex-direction: column;
			background: #fff;
			flex: 1;
			width: calc(50% - 20px);
			justify-content: center;
			align-items: center;
			order: 11;
			.news-title {
				border-radius: 10px;
				padding: 0 10px;
				margin: 0 0 5px;
				color: #000;
				background: #2dc7a310;
				font-weight: 500;
				width: 100%;
				font-size: 18px;
				line-height: 60px;
				text-align: center;
			}
			.news-list {
				display: flex;
				width: 100%;
				justify-content: space-between;
				align-items: center;
				flex-wrap: wrap;
			}
			.news-item {
				border: 1px solid #2dc7a330;
				cursor: pointer;
				border-radius: 10px;
				padding: 10px;
				margin: 25px 0 0;
				background: #fff;
				display: flex;
				width: calc(50% - 20px);
				justify-content: space-between;
				align-items: center;
				flex-wrap: wrap;
				.news-text {
					overflow: hidden;
					color: #000;
					white-space: nowrap;
					font-weight: 600;
					width: 100%;
					font-size: 15px;
					line-height: 2;
					text-overflow: ellipsis;
					text-align: center;
				}
				.news-img-box {
					margin: 0 10px 0 0;
					overflow: hidden;
					display: none;
					width: auto;
					height: auto;
					img {
						object-fit: cover;
						width: 60px;
						height: 60px;
					}
				}
				.news-desc {
					overflow: hidden;
					color: #666;
					white-space: nowrap;
					flex: 1;
					width: 100%;
					text-overflow: ellipsis;
				}
				.news-time {
					color: #ccc;
					width: 100%;
					font-size: 12px;
					text-align: center;
				}
			}
		}
		.news-box:hover {
			box-shadow: 0 0px 0px #ddd;
			transform: translate3d(0, 0px, 0);
			z-index: 1;
			background: #fff;
		}
		// echarts4
		.type4 {
			padding: 0;
			align-content: flex-start;
			background: none;
			display: flex;
			width: 100%;
			justify-content: space-between;
			flex-wrap: wrap;
			height: auto;
			.echarts1 {
				border: 1px solid #eeeeee;
				border-radius: 10px;
				padding: 20px;
				box-shadow: none;
				margin: 10px;
				background: rgba(255,255,255,1);
				width: calc(50% - 20px);
				transition: 0.3s;
				height: 400px;
			}
			.echarts1:hover {
				box-shadow: 0 0px 0px #ddd;
				transform: translate3d(0, 0px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.echarts2 {
				border: 1px solid #eeeeee;
				border-radius: 10px;
				padding: 20px;
				box-shadow: none;
				margin: 10px;
				background: rgba(255,255,255,1);
				width: calc(50% - 20px);
				transition: 0.3s;
				height: 400px;
			}
			.echarts2:hover {
				box-shadow: 0 0px 0px #ddd;
				transform: translate3d(0, 0px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.echarts3 {
				border: 1px solid #eeeeee;
				border-radius: 10px;
				padding: 20px;
				box-shadow: none;
				margin: 10px;
				background: rgba(255,255,255,1);
				width: calc(50% - 20px);
				transition: 0.3s;
				height: 400px;
			}
			.echarts3:hover {
				box-shadow: 0 0px 0px #ddd;
				transform: translate3d(0, 0px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
			.echarts4 {
				border: 1px solid #eeeeee;
				border-radius: 10px;
				padding: 20px;
				box-shadow: none;
				margin: 10px;
				background: rgba(255,255,255,1);
				width: calc(50% - 20px);
				transition: 0.3s;
				height: 400px;
			}
			.echarts4:hover {
				box-shadow: 0 0px 0px #ddd;
				transform: translate3d(0, 0px, 0);
				z-index: 1;
				background: rgba(255,255,255,1);
			}
		}
	}
	
	.echarts-flag-2 {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		padding: 10px 20px;
		background: rebeccapurple;
	
		&>div {
			width: 32%;
			height: 300px;
			margin: 10px 0;
			background: rgba(255,255,255,.1);
			border-radius: 8px;
			padding: 10px 20px;
		}
	}
	.animate__animated {
		animation-fill-mode: none;
	}
</style>
