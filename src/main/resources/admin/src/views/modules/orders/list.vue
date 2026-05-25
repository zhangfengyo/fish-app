  
<template>
	<div class="main-content" :style='{"width":"100%","padding":"20px","fontSize":"14px","color":"#666","height":"auto"}'>
		<!-- 鍒楄〃椤?-->
		<template v-if="showFlag ">
			<el-form class="center-form-pv" :style='{"width":"100%","padding":"0","margin":"0 0 0px","flexWrap":"wrap","display":"flex"}' :inline="true" :model="searchForm">
				<el-row :style='{"border":"0px solid #e5e7eb","padding":"0","boxShadow":"none","margin":"0 10px 5px 0","borderRadius":"0","alignItems":"center","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto","position":"relative"}' >
					<div  :style='{"border":"1px solid #ddd","width":"auto","padding":"0 10px","margin":"0 5px 0px 0","borderRadius":"4px","display":"flex"}'>
						<label :style='{"margin":"0 10px 0 0","whiteSpace":"nowrap","color":"inherit","display":"inline-block","lineHeight":"36px","fontSize":"inherit","fontWeight":"500","height":"36px"}' class="item-label">璁㈠崟缂栧彿</label>
						<el-input v-model="searchForm.orderid" placeholder="璁㈠崟缂栧彿" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<el-button class="search" type="success" @click="search()">
						<span class="icon iconfont icon-fangdajing02" :style='{"margin":"0 0px","fontSize":"inherit","color":"#fff","height":"40px"}'></span>
						鏌ヨ
					</el-button>
				</el-row>

				<el-row class="actions" :style='{"border":"0px solid #e5e7eb","padding":"0","margin":"0 0 5px","borderRadius":"0","flexWrap":"wrap","background":"#fff","display":"flex","width":"auto"}'>
					<el-button class="btn18" type="success" @click="refreshClick()">
						鍒锋柊
					</el-button>
					<el-button class="add" v-if="isAuth('orders'+'/'+orderStatus,'鏂板')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia1" :style='{"margin":"0 2px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
						鏂板
					</el-button>
					<el-button class="del" v-if="isAuth('orders'+'/'+orderStatus,'鍒犻櫎')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鎵归噺鍒犻櫎
					</el-button>


					<download-excel v-if="isAuth('orders'+'/'+orderStatus,'瀵煎嚭')" class="export-excel-wrapper" :fetch="getAllList" :fields="json_fields" name="璁㈠崟.xlsx">
						<!-- 瀵煎嚭excel -->
						<el-button class="btn18" type="success">
							<span class="icon iconfont icon-xiazai7" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
							瀵煎嚭
						</el-button>
					</download-excel>
					<el-button class="btn18" v-if="isAuth('orders'+'/'+orderStatus,'鍙戣揣')" type="success" @click="updateHandler(null)">
						<span class="icon iconfont icon-zhifudingjin" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鎵归噺鍙戣揣
					</el-button>
					<el-button class="btn18" v-if="isAuth('orders'+'/'+orderStatus,'纭鏀惰揣')" @click="updateHandler2(null)">
						<span class="icon iconfont icon-zhifudingjin" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鎵归噺鏀惰揣
					</el-button>
					<el-button class="btn18" v-if="isAuth('orders'+'/'+orderStatus,'鍟嗗搧閿€閲?)" type="success" @click="chartDialogShow1">
						<span class="icon iconfont icon-xihuan" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鍟嗗搧閿€閲?
					</el-button>
					<el-button class="btn18" v-if="isAuth('orders'+'/'+orderStatus,'鍟嗗搧閿€棰?)" type="success" @click="chartDialogShow2">
						<span class="icon iconfont icon-xihuan" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鍟嗗搧閿€棰?
					</el-button>
					<el-button class="btn18" v-if="isAuth('orders'+'/'+orderStatus,'鍟嗗搧鍒嗙被閿€閲?)" type="success" @click="chartDialogShow3">
						<span class="icon iconfont icon-xihuan" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鍟嗗搧鍒嗙被閿€閲?
					</el-button>
					<el-button class="btn18" v-if="isAuth('orders'+'/'+orderStatus,'鍟嗗搧鍒嗙被閿€棰?)" type="success" @click="chartDialogShow4">
						<span class="icon iconfont icon-xihuan" :style='{"margin":"0 2px","fontSize":"16px","color":"inherit","display":"none","height":"40px"}'></span>
						鍟嗗搧鍒嗙被閿€棰?
					</el-button>
				</el-row>
			</el-form>
			<div :style='{"width":"100%","padding":"10px 0px 20px","boxShadow":"none","borderRadius":"0 0 4px 4px","background":"#fff"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#eee","borderRadius":"0","borderWidth":"1px 1px 0 1px","background":"#fff","width":"100%","fontSize":"inherit","borderStyle":"solid"}' 
					:border='false'
					v-if="isAuth('orders'+'/'+orderStatus,'鏌ョ湅')"
					:data="dataList"
					v-loading="dataListLoading"
					@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="搴忓彿" type="index" width="50" />
					<el-table-column :resizable='true' :sortable='true'
												prop="orderid"
						label="璁㈠崟缂栧彿">
						<template slot-scope="scope">
							{{scope.row.orderid}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="goodname"
						label="鍟嗗搧鍚嶇О">
						<template slot-scope="scope">
							{{scope.row.goodname}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="goodtype"
						label="鍟嗗搧绫诲瀷">
						<template slot-scope="scope">
							{{scope.row.goodtype}}
						</template>
					</el-table-column>
					<el-table-column  :resizable='true' prop="picture" width="200" label="鍥剧墖">
						<template slot-scope="scope">
							<div v-if="scope.row.picture">
								<img v-if="scope.row.picture.substring(0,4)=='http'&&scope.row.picture.split(',w').length>1" :src="scope.row.picture" width="100" height="100" style="object-fit: cover" @click="imgPreView(scope.row.picture)">
								<img v-else-if="scope.row.picture.substring(0,4)=='http'" :src="scope.row.picture.split(',')[0]" width="100" height="100" style="object-fit: cover" @click="imgPreView(scope.row.picture.split(',')[0])">
								<img v-else :src="$base.url+scope.row.picture.split(',')[0]" width="100" height="100" style="object-fit: cover" @click="imgPreView($base.url+scope.row.picture.split(',')[0])">
							</div>
							<div v-else>鏃犲浘鐗?/div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="buynumber"
						label="璐拱鏁伴噺">
						<template slot-scope="scope">
							{{scope.row.buynumber}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="price"
						label="鍗曚环">
						<template slot-scope="scope">
							{{scope.row.price}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="total"
						label="鎬讳环">
						<template slot-scope="scope">
							{{scope.row.total}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="status" :formatter="orderStatusFormatter"
						label="鐘舵€?>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="address"
						label="鍦板潃">
						<template slot-scope="scope">
							{{scope.row.address}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="tel"
						label="鐢佃瘽">
						<template slot-scope="scope">
							{{scope.row.tel}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="consignee"
						label="鏀惰揣浜?>
						<template slot-scope="scope">
							{{scope.row.consignee}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="remark"
						label="澶囨敞">
						<template slot-scope="scope">
							{{scope.row.remark}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'
												prop="addtime"
						label="鍒涘缓鏃堕棿">
						<template slot-scope="scope">
							{{scope.row.addtime}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="sfsh" label="閫€璐у鏍? v-if="changeReturnGoods()">
						<template slot-scope="scope">
							<el-tag type="success" v-if="scope.row.sfsh=='鏄?">宸查€氳繃</el-tag>
							<el-tag type="warning" v-if="scope.row.sfsh=='寰呭鏍?">寰呭鏍?/el-tag>
							<el-tag type="danger" v-if="scope.row.sfsh=='鍚?">鏈€氳繃</el-tag>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="shhf" label="瀹℃牳鍥炲" show-overflow-tooltip v-if="changeReturnGoods()">
						<template slot-scope="scope">
							<div style="white-space: nowrap;">{{scope.row.shhf}}</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="returnreason" label="閫€璐у師鍥? show-overflow-tooltip v-if="changeReturnGoods()">
						<template slot-scope="scope">
							<div style="white-space: nowrap;">{{scope.row.returnreason}}</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="addtime" label="涓嬪崟鏃堕棿">
						<template slot-scope="scope">
							{{scope.row.addtime}}
						</template>
					</el-table-column>
					<el-table-column width="300" label="鎿嶄綔">
						<template slot-scope="scope">
							<el-button class="edit" v-if=" isAuth('orders'+'/'+orderStatus,'淇敼') " type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai13" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								缂栬緫
							</el-button>

							<el-button class="btn8" v-if="isAuth('orders'+'/'+orderStatus,'鐗╂祦')&&scope.row.id" type="success" @click="logisticsUpdate(scope.row.id)">
								<span class="icon iconfont icon-wuliu8" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								鐗╂祦
							</el-button>
							<el-button class="btn8" v-if="isAuth('orders'+'/'+orderStatus,'鍙戣揣')&&scope.row.id" type="success" @click="updateHandler(scope.row)">
								<span class="icon iconfont icon-fahuo16" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								鍙戣揣
							</el-button>
							<el-button class="btn8" v-if="isAuth('orders'+'/'+orderStatus,'纭鏀惰揣')&&scope.row.id" type="success" @click="updateHandler2(scope.row)">
								<span class="icon iconfont icon-queren12" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								纭鏀惰揣
							</el-button>
							<el-button class="btn8" v-if="isAuth('orders'+'/'+orderStatus,'閫€璐у鏍?)&&scope.row.sfsh=='寰呭鏍?" type="success" @click="returnSh(scope.row)">
								<span class="icon iconfont icon-zhangjie7" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								閫€璐у鏍?
							</el-button>
							<el-button class="btn8" v-if="isAuth('orders'+'/'+orderStatus,'浜岀淮鐮?)" type="success" @click="qrcodeClick(scope.row)">
								<span class="icon iconfont icon-zhangjie7" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								浜岀淮鐮?
							</el-button>



							<el-button class="del" v-if="isAuth('orders'+'/'+orderStatus,'鍒犻櫎')" type="primary" @click="deleteHandler(scope.row.id)">
								<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 0px","fontSize":"14px","color":"inherit","display":"none","height":"40px"}'></span>
								鍒犻櫎
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				background
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:layout="layouts.join()"
				:total="totalPage"
				prev-text="< "
				next-text="> "
				:hide-on-single-page="false"
				:style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"#333","display":"flex","width":"100%","fontWeight":"500","justifyContent":"flex-end"}'
			></el-pagination>
		</template>
		<!-- 娣诲姞/淇敼椤甸潰  灏嗙埗缁勪欢鐨剆earch鏂规硶浼犻€掔粰瀛愮粍浠?->
		<add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>


		<el-dialog title="閫€璐у鏍? :visible.sync="returnShVisible" width="50%">
			<el-form ref="returnShForm" :model="returnShForm" label-width="80px" :rules="returnShRules">
				<el-form-item label="閫€璐х悊鐢? prop="returnreason">
					{{returnShForm.returnreason}}
				</el-form-item>
				<el-form-item label="瀹℃牳鐘舵€? prop="sfsh">
					<el-select v-model="returnShForm.sfsh" placeholder="瀹℃牳鐘舵€?>
						<el-option label="閫氳繃" value="鏄?></el-option>
						<el-option label="涓嶉€氳繃" value="鍚?></el-option>
						<el-option label="寰呭鏍? value="寰呭鏍?></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="鍐呭" prop="shhf">
					<el-input type="textarea" :rows="8" v-model="returnShForm.shhf"></el-input>
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="returnShVisible=false">鍙?娑?/el-button>
				<el-button type="primary" @click="returnShSave">纭?瀹?/el-button>
			</span>
		</el-dialog>


		<el-dialog title="浜岀淮鐮? :visible.sync="codeVisible" width="490px">
			<div id="qrcode"></div>
		</el-dialog>
		<el-dialog
			class="chartDialog"
			:visible.sync="chartVisiable1"
			width="800">
			<div v-if="changeStatQuery(['users'])" style="display: flex;width: 100%;align-items: center;margin: 0 0 20px;">
				<div style="padding: 0 10px;">鍟嗗搧鍚嶇О</div>
				<el-select v-model="chartQuery1.goodname" placeholder="璇烽€夋嫨" @change="chartDialog1" clearable>
					<el-option v-for="item in goodnameChartOptions1" :label="item" :value="item"></el-option>
				</el-select>
			</div>
			<div id="buynumberChart1" style="width:100%;height:600px;"></div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="chartVisiable1 = false">杩斿洖</el-button>
			</span>
		</el-dialog>
		<el-dialog
			class="chartDialog"
			:visible.sync="chartVisiable2"
			width="800">
			<div v-if="changeStatQuery(['users'])" style="display: flex;width: 100%;align-items: center;margin: 0 0 20px;">
				<div style="padding: 0 10px;">鍟嗗搧鍚嶇О</div>
				<el-select v-model="chartQuery2.goodname" placeholder="璇烽€夋嫨" @change="chartDialog2" clearable>
					<el-option v-for="item in goodnameChartOptions2" :label="item" :value="item"></el-option>
				</el-select>
			</div>
			<div id="totalChart2" style="width:100%;height:600px;"></div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="chartVisiable2 = false">杩斿洖</el-button>
			</span>
		</el-dialog>
		<el-dialog
			class="chartDialog"
			:visible.sync="chartVisiable3"
			width="800">
			<div v-if="changeStatQuery(['users'])" style="display: flex;width: 100%;align-items: center;margin: 0 0 20px;">
				<div style="padding: 0 10px;">鍟嗗搧绫诲瀷</div>
				<el-select v-model="chartQuery3.goodtype" placeholder="璇烽€夋嫨" @change="chartDialog3" clearable>
					<el-option v-for="item in goodtypeChartOptions3" :label="item" :value="item"></el-option>
				</el-select>
			</div>
			<div id="buynumberChart3" style="width:100%;height:600px;"></div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="chartVisiable3 = false">杩斿洖</el-button>
			</span>
		</el-dialog>
		<el-dialog
			class="chartDialog"
			:visible.sync="chartVisiable4"
			width="800">
			<div v-if="changeStatQuery(['users'])" style="display: flex;width: 100%;align-items: center;margin: 0 0 20px;">
				<div style="padding: 0 10px;">鍟嗗搧绫诲瀷</div>
				<el-select v-model="chartQuery4.goodtype" placeholder="璇烽€夋嫨" @change="chartDialog4" clearable>
					<el-option v-for="item in goodtypeChartOptions4" :label="item" :value="item"></el-option>
				</el-select>
			</div>
			<div id="totalChart4" style="width:100%;height:600px;"></div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="chartVisiable4 = false">杩斿洖</el-button>
			</span>
		</el-dialog>

		<el-dialog title="棰勮鍥? :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
	</div>
</template>

<script>
	import * as echarts from 'echarts'
	import chinaJson from "@/components/echarts/china.json";
	import axios from 'axios';
	import AddOrUpdate from "./add-or-update";
	import QRCode from 'qrcode2';
	import {
		Loading
	} from 'element-ui';
	export default {
		data() {
			return {
				indexQueryCondition: '',
				searchForm: {
					key: ""
				},
				form:{},
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				line: {"backgroundColor":"transparent","yAxis":{"axisLabel":{"borderType":"solid","rotate":0,"padding":0,"shadowOffsetX":0,"margin":15,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"width":"","fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,0.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"xAxis":{"axisLabel":{"borderType":"solid","rotate":30,"padding":0,"shadowOffsetX":0,"margin":10,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"truncate","borderRadius":0,"borderWidth":0,"width":120,"interval":0,"fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":false},"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"color":["#2dc7a3","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"legend":{"shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":0,"itemGap":10,"backgroundColor":"transparent","orient":"vertical","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","borderRadius":0,"left":"right","width":"80%","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"series":{"showSymbol":true,"symbol":"emptyCircle","symbolSize":4},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"borderType":"solid","padding":0,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"}},
				bar: {"backgroundColor":"transparent","yAxis":{"axisLabel":{"borderType":"solid","rotate":0,"padding":0,"shadowOffsetX":0,"margin":12,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"width":"","fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,0.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"xAxis":{"axisLabel":{"borderType":"solid","rotate":30,"padding":0,"shadowOffsetX":0,"margin":10,"backgroundColor":"transparent","borderColor":"#000","shadowOffsetY":0,"color":"#333","shadowBlur":0,"show":true,"inside":false,"ellipsis":"...","overflow":"truncate","borderRadius":0,"borderWidth":0,"width":120,"interval":0,"fontSize":12,"lineHeight":24,"shadowColor":"transparent","fontWeight":"normal","height":""},"axisTick":{"show":true,"length":5,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"inside":false},"splitLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":false},"minInterval":1,"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"cap":"butt","color":"#333","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"rgba(0,0,0,.5)"},"show":true},"splitArea":{"show":false,"areaStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"rgba(25,25,25,.3)","opacity":1,"shadowBlur":10,"shadowColor":"rgba(0,0,0,.5)"}}},"color":["#2dc7a3","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"legend":{"shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":0,"itemGap":10,"backgroundColor":"transparent","orient":"vertical","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","borderRadius":0,"left":"right","width":"80%","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"grid":{"x":"25%","y":"15%","y2":"15%","x2":"5%"},"series":{"barWidth":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"colorBy":"data","barCategoryGap":"40%"},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"borderType":"solid","padding":0,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"},"base":{"animate":false,"interval":2000}},
				pie: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#2dc7a3","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"title":{"borderType":"solid","padding":[5,0,0,0],"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"left","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#333","textShadowColor":"transparent","fontSize":14,"lineHeight":14,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":600,"textBorderColor":"#666","textShadowBlur":0},"shadowColor":"transparent"},"legend":{"shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":[5,0,0,0],"itemGap":10,"backgroundColor":"transparent","orient":"horizontal","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":0,"borderRadius":0,"left":"right","width":"80%","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"series":{"itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#666","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"label":{"borderType":"solid","rotate":0,"padding":0,"textBorderWidth":0,"backgroundColor":"transparent","borderColor":"#666","color":"inherit","show":true,"textShadowColor":"transparent","distanceToLabelLine":5,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"fontSize":12,"lineHeight":18,"textShadowOffsetX":0,"position":"outside","textShadowOffsetY":0,"textBorderType":"solid","textBorderColor":"#666","textShadowBlur":0},"labelLine":{"show":true,"length":10,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"#666","shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"#000"},"length2":14,"smooth":false}}},
				funnel: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#6C63FF","#FF6584","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"title":{"borderType":"solid","padding":2,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#ccc","shadowOffsetY":0,"shadowBlur":0,"bottom":"auto","show":true,"right":"auto","top":"auto","borderRadius":0,"left":"center","borderWidth":0,"textStyle":{"textBorderWidth":0,"color":"#666","textShadowColor":"transparent","fontSize":14,"lineHeight":12,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"#ccc","textShadowBlur":0},"shadowColor":"transparent"},"legend":{"padding":5,"itemGap":10,"shadowOffsetX":0,"backgroundColor":"transparent","borderColor":"#ccc","shadowOffsetY":0,"orient":"vertical","shadowBlur":0,"bottom":"auto","itemHeight":2,"show":true,"icon":"roundRect","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"top":"auto","borderRadius":0,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"left":"left","borderWidth":0,"width":"auto","itemWidth":2,"textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":20,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0},"shadowColor":"rgba(0,0,0,.3)","height":"auto"},"series":{"itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"#000","shadowOffsetY":0,"color":"","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"#000"},"label":{"borderType":"solid","rotate":0,"padding":0,"textBorderWidth":0,"backgroundColor":"transparent","borderColor":"#fff","color":"","show":true,"textShadowColor":"transparent","distanceToLabelLine":5,"ellipsis":"...","overflow":"none","borderRadius":0,"borderWidth":0,"fontSize":12,"lineHeight":18,"textShadowOffsetX":0,"position":"outside","textShadowOffsetY":0,"textBorderType":"solid","textBorderColor":"#fff","textShadowBlur":0},"labelLine":{"show":true,"length":10,"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"shadowBlur":0,"width":1,"type":"solid","opacity":1,"shadowColor":"#000"},"length2":14,"smooth":false}}},
				boardBase: {"funnelNum":8,"lineNum":8,"radarNum":8,"gaugeNum":8,"barNum":8,"pieNum":8},
				gauge: {"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"backgroundColor":"transparent","color":["#fc8452","#91cc75","#fac858","#ee6666","#73c0de","#3ba272","#1f614f","#9a60b4","#ea7ccc"],"title":{"top":"top","left":"left","textStyle":{"fontSize":14,"lineHeight":24,"color":"#333","fontWeight":600}},"series":{"pointer":{"offsetCenter":[0,"10%"],"icon":"path://M2.9,0.7L2.9,0.7c1.4,0,2.6,1.2,2.6,2.6v115c0,1.4-1.2,2.6-2.6,2.6l0,0c-1.4,0-2.6-1.2-2.6-2.6V3.3C0.3,1.9,1.4,0.7,2.9,0.7z","width":8,"length":"80%"},"axisLine":{"lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"opacity":0.5,"shadowBlur":1,"shadowColor":"#000"},"roundCap":true},"anchor":{"show":true,"itemStyle":{"color":"inherit"},"size":18,"showAbove":true},"emphasis":{"disabled":false},"progress":{"show":true,"roundCap":true,"overlap":true},"splitNumber":25,"detail":{"formatter":"{value}","backgroundColor":"inherit","color":"#fff","borderRadius":3,"width":20,"fontSize":12,"height":10},"title":{"fontSize":14},"animation":true}},
				radar: {"backgroundColor":"transparent","radar":{"shape":"circle","radius":"55%"},"color":["#5470c6","#91cc75","#fac858","#ee6666","#73c0de","#3ba272","#fc8452","#9a60b4","#ea7ccc"],"legend":{"shadowOffsetX":0,"borderColor":"#ccc","shadowOffsetY":0,"shadowBlur":0,"itemHeight":4,"show":true,"icon":"roundRect","type":"scroll","top":"auto","lineStyle":{"shadowOffsetX":0,"shadowOffsetY":0,"color":"inherit","shadowBlur":0,"width":"auto","type":"inherit","opacity":1,"shadowColor":"transparent"},"borderWidth":0,"itemWidth":4,"shadowColor":"rgba(0,0,0,.3)","height":"auto","padding":5,"itemGap":10,"backgroundColor":"transparent","orient":"horizontal","bottom":"auto","itemStyle":{"borderType":"solid","shadowOffsetX":0,"borderColor":"inherit","shadowOffsetY":0,"color":"inherit","shadowBlur":0,"borderWidth":0,"opacity":1,"shadowColor":"transparent"},"right":"auto","borderRadius":0,"left":"right","width":"auto","textStyle":{"textBorderWidth":0,"color":"inherit","textShadowColor":"transparent","ellipsis":"...","overflow":"none","fontSize":12,"lineHeight":24,"textShadowOffsetX":0,"textShadowOffsetY":0,"textBorderType":"solid","fontWeight":500,"textBorderColor":"transparent","textShadowBlur":0}},"series":{},"tooltip":{"backgroundColor":"#123","textStyle":{"color":"#fff"}},"title":{"top":"bottom","left":"left"}},
				goodnameChartOptions1: [],
				chartQuery1: {},
				chartVisiable1: false,
				goodnameChartOptions2: [],
				chartQuery2: {},
				chartVisiable2: false,
				goodtypeChartOptions3: [],
				chartQuery3: {},
				chartVisiable3: false,
				goodtypeChartOptions4: [],
				chartQuery4: {},
				chartVisiable4: false,
				codeVisible: false,
				returnShVisible: false,
				returnShForm: {},
				returnShRules:{
					sfsh:[{ required: true, message: '瀹℃牳鐘舵€佷笉鑳戒负绌?, trigger: 'blur' },],
					shhf:[{ required: true, message: '瀹℃牳鍥炲涓嶈兘涓虹┖', trigger: 'blur' },],
				},
				addOrUpdateFlag:false,
				layouts: ["total","prev","pager","next","sizes","jumper"],
				orderStatus: this.$route.params.status,
//瀵煎嚭excel
				json_fields: {
					"涓嬪崟鏃堕棿": "addtime",
					"璁㈠崟缂栧彿": "orderid",    //甯歌瀛楁
					"鍟嗗搧id": "goodid",    //甯歌瀛楁
					"鍟嗗搧鍚嶇О": "goodname",    //甯歌瀛楁
					"鍟嗗搧绫诲瀷": "goodtype",    //甯歌瀛楁
					"鍥剧墖": "picture",    //甯歌瀛楁
					"璐拱鏁伴噺": "buynumber",    //甯歌瀛楁
					"鍗曚环": "price",    //甯歌瀛楁
					"鎬讳环": "total",    //甯歌瀛楁
					"鏀粯绫诲瀷": "type",    //甯歌瀛楁
					"璁㈠崟鐘舵€?: "status",    //甯歌瀛楁
					"鍦板潃": "address",    //甯歌瀛楁
					"鐢佃瘽": "tel",    //甯歌瀛楁
					"鏀惰揣浜?: "consignee",    //甯歌瀛楁
					"澶囨敞": "remark",    //甯歌瀛楁
					"鍒涘缓鏃堕棿": "addtime",    //甯歌瀛楁
					"鐢ㄦ埛id": "userid",    //甯歌瀛楁
					"鍟嗗搧琛ㄥ悕": "tablename",    //甯歌瀛楁
					"鐗╂祦": "logistics",    //甯歌瀛楁
					"鏄惁瀹℃牳": "sfsh",    //甯歌瀛楁
					"瀹℃牳鍥炲": "shhf",    //甯歌瀛楁
					"鐢ㄦ埛瑙掕壊": "role",    //甯歌瀛楁
					"閫€璐у師鍥?: "returnreason",    //甯歌瀛楁
				},
				json_meta: [
					[
						{
							" key ": " charset ",
							" value ": " utf- 8 "
						}
					]
				],
				previewImg: '',
				previewVisible: false,
			};
		},
		created() {
			if(this.statType) {
				return false
			}
			this.init();
			this.getDataList();
		},
		mounted() {
		},
		watch: {
			//鐩戝惉璁㈠崟琛ㄥ弬鏁版槸鍚﹀彉鍖栵紝浠庤€岃皟鍙栨帴鍙?			'$route' (to, from) { //鐩戝惉璺敱鏄惁鍙樺寲
				if(this.$route.params.status){//鍒ゆ柇鐘舵€佹槸鍚︽湁鍊?				//璋冩暟鎹?					this.orderStatus=this.$route.params.status;
					this.getDataList();
				}
			},
		},
		filters: {
			htmlfilter: function (val) {
				return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
			}
		},
		computed: {
			tablename(){
				return this.$storage.get('sessionTable')
			},
			role(){
				return this.$storage.get('role')
			},
		},
		components: {
			AddOrUpdate,
		},
		methods: {
			queryChange(arr){
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
				
			},
			orderStatusFormatter: function(row, column) {
				var temp = row.status
				if(row.status=='宸查€€娆?&&row.sfsh!=''){
					temp = '宸查€€璐?
				}
				return temp
			},
			orderTypeFormatter: function(row, column) {
				var temp = ''
			  // 澶勭悊閫昏緫 淇濆瓨鎺ュ彛 鏁版嵁锛岃繘琛屽尮閰?name
				switch (row.type-0) {
					case 1:
						temp = '浣欓'
						break
					case 2:
						temp = '绉垎'
						break
					case 3:
						temp = '鎷煎洟'
						break
				}
				return temp
			},
			qrcodeClick(row) {
				this.codeVisible = true
				this.$nextTick(()=>{
					if(document.getElementById('qrcode').innerHTML!=null) {
						document.getElementById('qrcode').innerHTML = ''
					}
					var qrcode = new QRCode(document.getElementById('qrcode'), {
						text: row.orderid,
						width: 450,
						height: 450,
						colorDark : "#000000",
						colorLight : "#ffffff",
						correctLevel : QRCode.CorrectLevel.H
					});
				})
			},
			updateHandler(row=null) {
				if(row==null) {
					if(!this.dataListSelections.length) {
						this.$message.error('璇烽€変腑鏁版嵁');
						return false
					}
					this.$confirm(`纭畾杩涜鎵归噺鍙戣揣鎿嶄綔?`, "鎻愮ず", {
						confirmButtonText: "纭畾",
						cancelButtonText: "鍙栨秷",
						type: "warning"
					}).then(async() => {
						for(let x in this.dataListSelections) {
							this.dataListSelections[x].status = "宸插彂璐?;
							await this.$http({
								url: `orders/update`,
								method: "post",
								data: this.dataListSelections[x]
							}).then(({ data }) => {
								if (data && data.code === 0) {
									
								} else {
									this.$message.error(data.msg);
								}
							});
						}
						this.$message({
							message: "鎿嶄綔鎴愬姛",
							type: "success",
							duration: 1500,
							onClose: () => {
								this.search();
							}
						});
					});
					return false
				}
				this.$confirm(`纭畾杩涜鍙戣揣鎿嶄綔?`, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(() => {
					row.status = "宸插彂璐?;
					this.$http({
						url: `orders/update`,
						method: "post",
						data: row
					}).then(({ data }) => {
						if (data && data.code === 0) {
							this.$message({
								message: "鎿嶄綔鎴愬姛",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.search();
								}
							});
						} else {
							this.$message.error(data.msg);
						}
					});
				});
			},
			updateHandler2(row=null) {
				if(row==null) {
					if(!this.dataListSelections.length) {
						this.$message.error('璇烽€変腑鏁版嵁');
						return false
					}
					this.$confirm(`纭畾杩涜鎵归噺鏀惰揣鎿嶄綔?`, "鎻愮ず", {
						confirmButtonText: "纭畾",
						cancelButtonText: "鍙栨秷",
						type: "warning"
					}).then(async() => {
						for(let x in this.dataListSelections) {
							this.dataListSelections[x].status = "宸插畬鎴?;
							await this.$http({
								url: `orders/update`,
								method: "post",
								data: this.dataListSelections[x]
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									await this.$http({
										url: `${this.dataListSelections[x].tablename}/info/${this.dataListSelections[x].goodid}`,
										method: 'get'
									}).then(async rs=>{
										if(rs.data.data.sales||rs.data.data.sales==0) {
											rs.data.data.sales = Number((Number(rs.data.data.sales) + Number(this.dataListSelections[x].buynumber)).toFixed(0))
											await this.$http({
												url: `${this.dataListSelections[x].tablename}/update`,
												method: "post",
												data: rs.data.data
											}).then(rs2=>{})
										}
									})
								} else {
									this.$message.error(data.msg);
								}
							});
						}
						this.$message({
							message: "鎿嶄綔鎴愬姛",
							type: "success",
							duration: 1500,
							onClose: () => {
								this.search();
							}
						});
					});
					return false
				}
				this.$confirm(`纭畾杩涜鏀惰揣鎿嶄綔?`, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(() => {
					row.status = "宸插畬鎴?;
					this.$http({
						url: `orders/update`,
						method: "post",
						data: row
					}).then(async ({ data }) => {
						if (data && data.code === 0) {
							await this.$http({
								url: `${row.tablename}/info/${row.goodid}`,
								method: 'get'
							}).then(async rs=>{
								if(rs.data.data.sales||rs.data.data.sales==0) {
									rs.data.data.sales = Number((Number(rs.data.data.sales) + Number(row.buynumber)).toFixed(0))
									await this.$http({
										url: `${row.tablename}/update`,
										method: "post",
										data: rs.data.data
									}).then(rs2=>{})
								}
							})
							this.$message({
								message: "鎿嶄綔鎴愬姛",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.search();
								}
							});
						} else {
							this.$message.error(data.msg);
						}
					});
				});
			},
			//鐗╂祦
			logisticsUpdate(id,type) {
				this.showFlag = false;
				this.addOrUpdateFlag = true;
				this.crossAddOrUpdateFlag = false;
				if(type!='info'){
					type = 'logistics';
				}
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,type);
				});
			},
			// 閫€璐у鏍?
			returnSh(row){
				this.returnShForm = JSON.parse(JSON.stringify(row))
				this.returnShVisible = true
			},
			// 閫€璐у鏍镐繚瀛?
			returnShSave(){
				this.$refs["returnShForm"].validate(valid => {
					if(valid){
						if(this.returnShForm.sfsh=="鏄?){
							this.returnShForm.status = '宸查€€娆?
							this.$http({
								url: `${this.returnShForm.tablename}/info/${this.returnShForm.goodid}`,
								method: 'get',
							}).then(res=>{
								if(res.data&&res.data.code==0){
									let good = res.data.data
									// 鍟嗗搧瀛樺湪搴撳瓨銆傚姞鍥炲簱瀛?
									if(good.alllimittimes){
										good.alllimittimes = parseInt(good.alllimittimes) + parseInt(this.returnShForm.buynumber)
										this.$http({
											url: `${this.returnShForm.tablename}/update`,
											method: 'post',
											data: good
										}).then(obj=>{})
									}
									this.$http({
										url: `${this.returnShForm.role}/info/${this.returnShForm.userid}`,
										method: 'get',
									}).then(res1=>{
										if(res1.data&&res1.data.code==0){
											let user = res1.data.data
											// 濡傛灉鏄Н鍒嗗厬鎹?鍔犲洖绉垎 
											if(this.returnShForm.type==2){
												user.jf = (Number(user.jf) + Number(this.returnShForm.total)).toFixed(2)
												this.$http({
													url: `${this.returnShForm.role}/update`,
													method: 'post',
													data: user
												}).then(obj=>{})
											}
											// 濡傛灉鏄叾浠栧舰寮?鏈夌Н鍒嗗噺鍘荤Н鍒嗐€備綑棰濆姞鍥炰綑棰?
											else{
												// 鍟嗗搧瀛樺湪绉垎銆傚姞鍥炵Н鍒?
												if(good.jf){
													user.jf = (Number(user.jf) - Number(this.returnShForm.total)).toFixed(2)
												}
												if(user.jf<0) {
													user.jf = 0
												}
												// 鍔犲洖浣欓
												user.money = (Number(user.money) + Number(this.returnShForm.total)).toFixed(2)
												this.$http({
													url: `${this.returnShForm.role}/update`,
													method: 'post',
													data: user
												}).then(obj=>{})
											}
											this.$http({
												url: 'orders/update',
												method: 'post',
												data: this.returnShForm
											}).then(res=>{
												if(res.data&&res.data.code==0){
													this.$message({
														message: "瀹℃牳鎴愬姛",
														type: "success",
														duration: 1500,
														onClose: () => {
															this.search()
															this.returnShVisible = false
														}
													})
												}
											})
										}
									})
								}
							})
						}else if(this.returnShForm.sfsh=='鍚?){
							this.$http({
								url: 'orders/update',
								method: 'post',
								data: this.returnShForm
							}).then(res=>{
								if(res.data&&res.data.code==0){
									this.$message({
										message: "瀹℃牳鎴愬姛",
										type: "success",
										duration: 1500,
										onClose: () => {
											this.search()
											this.returnShVisible = false
										}
									})
								}
							})
						}
					}
				})
			},
			changeStatQuery(arr) {
				if(arr.length==1) {
					if(arr[0] == 'users'&&this.$storage.get("sessionTable")=='users') {
						return true
					}
				}
				for(let x in arr) {
					if(arr[x] == this.role) {
						return true
					}
				}
				return false
			},
			// 璇嶄簯
			wordclouds(wordcloudData,echartsId) {
				let wordcloud = {"maskImage":"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAA28AAAJHCAYAAAAHR24TAAAACXBIWXMAAA7EAAAOxAGVKw4bAAAGsmlUWHRYTUw6Y29tLmFkb2JlLnhtcAAAAAAAPD94cGFja2V0IGJlZ2luPSLvu78iIGlkPSJXNU0wTXBDZWhpSHpyZVN6TlRjemtjOWQiPz4gPHg6eG1wbWV0YSB4bWxuczp4PSJhZG9iZTpuczptZXRhLyIgeDp4bXB0az0iQWRvYmUgWE1QIENvcmUgNy4xLWMwMDAgMTE2LjM5YWJhZjcsIDIwMjIvMDIvMjUtMjE6NTc6MjEgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6ZGM9Imh0dHA6Ly9wdXJsLm9yZy9kYy9lbGVtZW50cy8xLjEvIiB4bWxuczpwaG90b3Nob3A9Imh0dHA6Ly9ucy5hZG9iZS5jb20vcGhvdG9zaG9wLzEuMC8iIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdEV2dD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlRXZlbnQjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCAyMi41IChXaW5kb3dzKSIgeG1wOkNyZWF0ZURhdGU9IjIwMjMtMTEtMjVUMTc6MTg6NDErMDg6MDAiIHhtcDpNb2RpZnlEYXRlPSIyMDIzLTExLTI4VDA5OjEwOjEyKzA4OjAwIiB4bXA6TWV0YWRhdGFEYXRlPSIyMDIzLTExLTI4VDA5OjEwOjEyKzA4OjAwIiBkYzpmb3JtYXQ9ImltYWdlL3BuZyIgcGhvdG9zaG9wOkNvbG9yTW9kZT0iMyIgcGhvdG9zaG9wOklDQ1Byb2ZpbGU9InNSR0IgSUVDNjE5NjYtMi4xIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjZmNzMwYTI4LTQ5NjUtYzU0Mi05ZTcxLTkwYjg3NTlmMDliNiIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDpmZmJmODcwOC03ZWIwLTQxNGYtYTU3ZS1mNWVmY2QyNDNmN2QiIHhtcE1NOk9yaWdpbmFsRG9jdW1lbnRJRD0ieG1wLmRpZDpmZmJmODcwOC03ZWIwLTQxNGYtYTU3ZS1mNWVmY2QyNDNmN2QiPiA8eG1wTU06SGlzdG9yeT4gPHJkZjpTZXE+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJjcmVhdGVkIiBzdEV2dDppbnN0YW5jZUlEPSJ4bXAuaWlkOmZmYmY4NzA4LTdlYjAtNDE0Zi1hNTdlLWY1ZWZjZDI0M2Y3ZCIgc3RFdnQ6d2hlbj0iMjAyMy0xMS0yNVQxNzoxODo0MSswODowMCIgc3RFdnQ6c29mdHdhcmVBZ2VudD0iQWRvYmUgUGhvdG9zaG9wIDIyLjUgKFdpbmRvd3MpIi8+IDxyZGY6bGkgc3RFdnQ6YWN0aW9uPSJzYXZlZCIgc3RFdnQ6aW5zdGFuY2VJRD0ieG1wLmlpZDo2YTQ4Y2ZmZS1iZjkyLWU5NGUtOWU1NC03YTk0ZjE4ZmM2ZTQiIHN0RXZ0OndoZW49IjIwMjMtMTEtMjVUMTc6MjQ6MzErMDg6MDAiIHN0RXZ0OnNvZnR3YXJlQWdlbnQ9IkFkb2JlIFBob3Rvc2hvcCAyMi41IChXaW5kb3dzKSIgc3RFdnQ6Y2hhbmdlZD0iLyIvPiA8cmRmOmxpIHN0RXZ0OmFjdGlvbj0ic2F2ZWQiIHN0RXZ0Omluc3RhbmNlSUQ9InhtcC5paWQ6NmY3MzBhMjgtNDk2NS1jNTQyLTllNzEtOTBiODc1OWYwOWI2IiBzdEV2dDp3aGVuPSIyMDIzLTExLTI4VDA5OjEwOjEyKzA4OjAwIiBzdEV2dDpzb2Z0d2FyZUFnZW50PSJBZG9iZSBQaG90b3Nob3AgMjIuNSAoV2luZG93cykiIHN0RXZ0OmNoYW5nZWQ9Ii8iLz4gPC9yZGY6U2VxPiA8L3htcE1NOkhpc3Rvcnk+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/2R4ggAAMvBJREFUeJzt3XmUpndd5v93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","option":{"tooltip":{"show":false},"backgroundColor":"transparent","series":[{"sizeRange":[9,32],"layoutAnimation":true,"shape":"circle","data":[{"name":"鑺遍笩甯傚満","value":1446},{"name":"姹借溅","value":928},{"name":"瑙嗛","value":906},{"name":"鐢佃","value":825},{"name":"Lover Boy 88","value":514},{"name":"鍔ㄦ极","value":486},{"name":"闊充箰","value":53},{"name":"鐩存挱","value":163},{"name":"骞挎挱鐢靛彴","value":86},{"name":"鎴忔洸鏇茶壓","value":17},{"name":"婕斿嚭绁ㄥ姟","value":6},{"name":"缁欓檶鐢熺殑浣犲惉","value":1},{"name":"璧勮","value":1437},{"name":"鍟嗕笟璐㈢粡","value":422},{"name":"濞变箰鍏崷","value":353},{"name":"鍐涗簨","value":331},{"name":"绉戞妧璧勮","value":313},{"name":"绀句細鏃舵斂","value":307},{"name":"鏃跺皻","value":43},{"name":"缃戠粶濂囬椈","value":15},{"name":"鏃呮父鍑鸿","value":438},{"name":"鏅偣绫诲瀷","value":957},{"name":"鍥藉唴娓?,"value":927},{"name":"杩滈€斿嚭琛屾柟寮?,"value":908},{"name":"閰掑簵","value":693},{"name":"鍏虫敞鏅偣","value":611},{"name":"鏃呮父缃戠珯鍋忓ソ","value":512},{"name":"鍑哄浗娓?,"value":382},{"name":"浜ら€氱エ鍔?,"value":312},{"name":"鏃呮父鏂瑰紡","value":187},{"name":"鏃呮父涓婚","value":163},{"name":"娓境鍙?,"value":104},{"name":"鏈湴鍛ㄨ竟娓?,"value":3},{"name":"灏忓崠瀹?,"value":1331},{"name":"鍏ㄦ棩鍒跺鏍?,"value":941},{"name":"鍩虹鏁欒偛绉戠洰","value":585},{"name":"鑰冭瘯鍩硅","value":473},{"name":"璇█瀛︿範","value":358},{"name":"鐣欏","value":246},{"name":"K12璇剧▼鍩硅","value":207},{"name":"鑹烘湳鍩硅","value":194},{"name":"鎶€鑳藉煿璁?,"value":104},{"name":"IT鍩硅","value":87},{"name":"楂樼瓑鏁欒偛涓撲笟","value":63},{"name":"瀹舵暀","value":48},{"name":"浣撹偛鍩硅","value":23},{"name":"鑱屽満鍩硅","value":5},{"name":"閲戣瀺璐㈢粡","value":1328},{"name":"閾惰","value":765},{"name":"鑲＄エ","value":452},{"name":"淇濋櫓","value":415},{"name":"璐锋","value":253},{"name":"鍩洪噾","value":211},{"name":"淇＄敤鍗?,"value":180},{"name":"澶栨眹","value":138},{"name":"P2P","value":116},{"name":"璐甸噾灞?,"value":98},{"name":"鍊哄埜","value":93},{"name":"缃戠粶鐞嗚储","value":92},{"name":"淇℃墭","value":90},{"name":"寰佷俊","value":76},{"name":"鏈熻揣","value":76},{"name":"鍏Н閲?,"value":40},{"name":"閾惰鐞嗚储","value":36},{"name":"閾惰涓氬姟","value":30},{"name":"鍏稿綋","value":7},{"name":"娴峰缃笟","value":1},{"name":"姹借溅","value":1309},{"name":"姹借溅妗ｆ","value":965},{"name":"姹借溅鍝佺墝","value":900},{"name":"姹借溅杞﹀瀷","value":727},{"name":"璐溅闃舵","value":461},{"name":"浜屾墜杞?,"value":309},{"name":"姹借溅缇庡","value":260},{"name":"鏂拌兘婧愭苯杞?,"value":173},{"name":"姹借溅缁翠慨","value":155},{"name":"绉熻溅鏈嶅姟","value":136},{"name":"杞﹀睍","value":121},{"name":"杩濈珷鏌ヨ","value":76},{"name":"姹借溅鏀硅","value":62},{"name":"姹借溅鐢ㄥ搧","value":37},{"name":"璺喌鏌ヨ","value":32},{"name":"姹借溅淇濋櫓","value":28},{"name":"闄┚浠ｉ┚","value":4},{"name":"缃戠粶璐墿","value":1275},{"name":"鍋氭垜鐨勭尗","value":1088},{"name":"鍙兂瑕佷綘鐭ラ亾","value":907},{"name":"鍥㈣喘","value":837},{"name":"姣斾环","value":201},{"name":"娴锋窐","value":195},{"name":"绉诲姩APP璐墿","value":179},{"name":"鏀粯鏂瑰紡","value":119},{"name":"浠ｈ喘","value":43},{"name":"浣撹偛鍋ヨ韩","value":1234},{"name":"浣撹偛璧涗簨椤圭洰","value":802},{"name":"杩愬姩椤圭洰","value":405},{"name":"浣撹偛绫昏禌浜?,"value":337},{"name":"鍋ヨ韩椤圭洰","value":199},{"name":"鍋ヨ韩鎴垮仴韬?,"value":78},{"name":"杩愬姩鍋ヨ韩","value":77},{"name":"瀹跺涵鍋ヨ韩","value":36},{"name":"鍋ヨ韩鍣ㄦ","value":29},{"name":"鍔炲叕瀹ゅ仴韬?,"value":3},{"name":"鍟嗗姟鏈嶅姟","value":1201},{"name":"娉曞緥鍜ㄨ","value":508},{"name":"鍖栧伐鏉愭枡","value":147},{"name":"骞垮憡鏈嶅姟","value":125},{"name":"浼氳瀹¤","value":115},{"name":"浜哄憳鎷涜仒","value":101},{"name":"鍗板埛鎵撳嵃","value":66},{"name":"鐭ヨ瘑浜ф潈","value":32},{"name":"缈昏瘧","value":22},{"name":"瀹夊叏瀹変繚","value":9},{"name":"鍏叧鏈嶅姟","value":8},{"name":"鍟嗘梾鏈嶅姟","value":2},{"name":"灞曚細鏈嶅姟","value":2},{"name":"鐗硅缁忚惀","value":1},{"name":"浼戦棽鐖卞ソ","value":1169},{"name":"鏀惰棌","value":412},{"name":"鎽勫奖","value":393},{"name":"娓╂硥","value":230},{"name":"鍗氬僵褰╃エ","value":211},{"name":"缇庢湳","value":207},{"name":"涔︽硶","value":139},{"name":"DIY鎵嬪伐","value":75},{"name":"鑸炶箞","value":23},{"name":"閽撻奔","value":21},{"name":"妫嬬墝妗屾父","value":17},{"name":"KTV","value":6},{"name":"瀵嗗","value":5},{"name":"閲囨憳","value":4},{"name":"鐢电帺","value":1},{"name":"鐪熶汉CS","value":1},{"name":"杞拌洞","value":1},{"name":"瀹剁數鏁扮爜","value":1111},{"name":"鎵嬫満","value":885},{"name":"鐢佃剳","value":543},{"name":"澶у鐢?,"value":321},{"name":"瀹剁數鍏虫敞鍝佺墝","value":253},{"name":"缃戠粶璁惧","value":162},{"name":"鎽勫奖鍣ㄦ潗","value":149},{"name":"褰遍煶璁惧","value":133},{"name":"鍔炲叕鏁扮爜璁惧","value":113},{"name":"鐢熸椿鐢靛櫒","value":67},{"name":"鍘ㄦ埧鐢靛櫒","value":54},{"name":"鏅鸿兘璁惧","value":45},{"name":"涓汉鎶ょ悊鐢靛櫒","value":22},{"name":"鏈嶉グ闉嬪寘","value":1047},{"name":"鏈嶈","value":566},{"name":"楗板搧","value":289},{"name":"闉?,"value":184},{"name":"绠卞寘","value":168},{"name":"濂緢鍝?,"value":137},{"name":"姣嶅┐浜插瓙","value":1041},{"name":"瀛曞┐淇濆仴","value":505},{"name":"姣嶅┐绀惧尯","value":299},{"name":"鏃╂暀","value":103},{"name":"濂剁矇杈呴","value":66},{"name":"绔ヨ溅绔ュ簥","value":41},{"name":"鍏虫敞鍝佺墝","value":271},{"name":"瀹濆疂鐜╀箰","value":30},{"name":"姣嶅┐鎶ょ悊鏈嶅姟","value":25},{"name":"绾稿翱瑁ゆ箍宸?,"value":16},{"name":"濡堝鐢ㄥ搧","value":15},{"name":"瀹濆疂璧峰悕","value":12},{"name":"绔ヨ绔ラ瀷","value":9},{"name":"鑳庢暀","value":8},{"name":"瀹濆疂瀹夊叏","value":1},{"name":"瀹濆疂娲楁姢鐢ㄥ搧","value":1},{"name":"杞欢搴旂敤","value":1018},{"name":"绯荤粺宸ュ叿","value":896},{"name":"鐞嗚储璐墿","value":440},{"name":"鐢熸椿瀹炵敤","value":365},{"name":"褰遍煶鍥惧儚","value":256},{"name":"绀句氦閫氳","value":214},{"name":"鎵嬫満缇庡寲","value":39},{"name":"鍔炲叕瀛︿範","value":28},{"name":"搴旂敤甯傚満","value":23},{"name":"姣嶅┐鑲插効","value":14},{"name":"娓告垙","value":946},{"name":"鎵嬫満娓告垙","value":565},{"name":"PC娓告垙","value":353},{"name":"缃戦〉娓告垙","value":254},{"name":"娓告垙鏈?,"value":188},{"name":"妯℃嫙杈呭姪","value":166},{"name":"涓姢缇庡","value":942},{"name":"鎶よ偆鍝?,"value":177},{"name":"褰╁","value":133},{"name":"缇庡彂","value":80},{"name":"棣欐按","value":50},{"name":"涓汉鎶ょ悊","value":46},{"name":"缇庣敳","value":26},{"name":"SPA缇庝綋","value":21},{"name":"鑺遍笩钀屽疇","value":914},{"name":"缁挎鑺卞崏","value":311},{"name":"鐙?,"value":257},{"name":"鍏朵粬瀹犵墿","value":131},{"name":"姘存棌","value":125},{"name":"鐚?,"value":122},{"name":"鍔ㄧ墿","value":81},{"name":"楦?,"value":67},{"name":"瀹犵墿鐢ㄥ搧","value":41},{"name":"瀹犵墿鏈嶅姟","value":26},{"name":"涔︾睄闃呰","value":913},{"name":"缃戠粶灏忚","value":483},{"name":"鍏虫敞涔︾睄","value":128},{"name":"鏂囧","value":105},{"name":"鎶ュ垔鏉傚織","value":77},{"name":"浜烘枃绀剧","value":22},{"name":"寤烘潗瀹跺眳","value":907},{"name":"瑁呬慨寤烘潗","value":644},{"name":"瀹跺叿","value":273},{"name":"瀹跺眳椋庢牸","value":187},{"name":"瀹跺眳瀹惰鍏虫敞鍝佺墝","value":140},{"name":"瀹剁汉","value":107},{"name":"鍘ㄥ叿","value":47},{"name":"鐏叿","value":43},{"name":"瀹跺眳楗板搧","value":29},{"name":"瀹跺眳鏃ュ父鐢ㄥ搧","value":10},{"name":"鐢熸椿鏈嶅姟","value":883},{"name":"鐗╂祦閰嶉€?,"value":536},{"name":"瀹舵斂鏈嶅姟","value":108},{"name":"鎽勫奖鏈嶅姟","value":49},{"name":"鎼鏈嶅姟","value":38},{"name":"鐗╀笟缁翠慨","value":37},{"name":"濠氬簡鏈嶅姟","value":24},{"name":"浜屾墜鍥炴敹","value":24},{"name":"椴滆姳閰嶉€?,"value":3},{"name":"缁翠慨鏈嶅姟","value":3},{"name":"娈¤懍鏈嶅姟","value":1},{"name":"姹傝亴鍒涗笟","value":874},{"name":"鍒涗笟","value":363},{"name":"鐩爣鑱屼綅","value":162},{"name":"鐩爣琛屼笟","value":50},{"name":"鍏艰亴","value":21},{"name":"鏈熸湜骞磋柂","value":20},{"name":"瀹炰範","value":16},{"name":"闆囦富绫诲瀷","value":10},{"name":"鏄熷骇杩愬娍","value":789},{"name":"鏄熷骇","value":316},{"name":"绠楀懡","value":303},{"name":"瑙ｆⅵ","value":196},{"name":"椋庢按","value":93},{"name":"闈㈢浉鍒嗘瀽","value":47},{"name":"鎵嬬浉","value":32},{"name":"鍏泭","value":90}],"keepAspect":false,"type":"wordCloud","rotationRange":[-90,90],"gridSize":8,"shrinkToFit":false,"top":"center","left":"center","width":"80%","emphasis":{"focus":"self","textStyle":{"textShadowColor":"#333","textShadowBlur":0}},"drawOutOfBound":false,"rotationStep":45,"textStyle":{"color":"function(){return\"rgb(\"+[Math.round(250*Math.random()),Math.round(130*Math.random()),Math.round(80*Math.random())].join(\",\")+\")\"}","fontWeight":500,"fontFamily":"sans-serif"},"height":"80%","maskImage":{}}]}}
				wordcloud = JSON.parse(JSON.stringify(wordcloud), (k, v) => {
				  if(typeof v == 'string' && v.indexOf('function') > -1){
					return eval("(function(){return "+v+" })()")
				  }
				  return v;
				})
				wordcloud.option.series[0].data=wordcloudData;
				
				this.myChart0 = echarts.init(document.getElementById(echartsId));
				let myChart = this.myChart0
				let img = wordcloud.maskImage
			
				if (img) {
					var maskImage = new Image();
					maskImage.src = img
					maskImage.onload = function() {
						wordcloud.option.series[0].maskImage = maskImage
						myChart.clear()
						myChart.setOption(wordcloud.option)
					}
				} else {
					delete wordcloud.option.series[0].maskImage
					myChart.clear()
					myChart.setOption(wordcloud.option)
				}
			},
			chartDialogShow1() {
				this.chartVisiable1 = true
				this.$http.get('option/orders/goodname',
				).then(rs=>{
					this.goodnameChartOptions1 = rs.data.data
				})
				this.chartDialog1()
			},
			// 缁熻鎺ュ彛1
			chartDialog1() {
				this.$nextTick(()=>{
					var buynumberChart1 = echarts.init(document.getElementById("buynumberChart1"),'macarons');
					let params = {}
					if(this.chartQuery1.goodname) {
						params.conditionColumn = 'goodname'
						params.conditionValue = this.chartQuery1.goodname
					}
					this.$http({
						url: "orders/value/goodname/buynumber",
						method: "get",
						params
					}).then(({data})=>{
						if (data && data.code === 0) {
							let res = data.data;
							let xAxis = [];
							let yAxis = [];
							let pArray = []
							for(let i=0;i<res.length;i++){
								if(this.boardBase&&i==this.boardBase.barNum){
									break;
								}
								// 缁熻鍥捐缃浜嗗悧
								xAxis.push(res[i].goodname);
								yAxis.push(parseFloat((res[i].total)));
								pArray.push({
									value: parseFloat((res[i].total)),
									name: res[i].goodname
								})
							}
							var option = {};
							let titleObj = this.bar.title
							titleObj.text = '鍟嗗搧閿€閲?
							
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
							// 浣跨敤鍒氭寚瀹氱殑閰嶇疆椤瑰拰鏁版嵁鏄剧ず鍥捐〃銆?
							buynumberChart1.setOption(option);
							  //鏍规嵁绐楀彛鐨勫ぇ灏忓彉鍔ㄥ浘琛?
							window.onresize = function() {
								buynumberChart1.resize();
							};
						}
					})
				})
			},
			chartDialogShow2() {
				this.chartVisiable2 = true
				this.$http.get('option/orders/goodname',
				).then(rs=>{
					this.goodnameChartOptions2 = rs.data.data
				})
				this.chartDialog2()
			},
			// 缁熻鎺ュ彛2
			chartDialog2() {
				this.$nextTick(()=>{
					var totalChart2 = echarts.init(document.getElementById("totalChart2"),'macarons');
					let params = {}
					if(this.chartQuery2.goodname) {
						params.conditionColumn = 'goodname'
						params.conditionValue = this.chartQuery2.goodname
					}
					this.$http({
						url: "orders/value/goodname/total",
						method: "get",
						params
					}).then(({data})=>{
						if (data && data.code === 0) {
							let res = data.data;
							let xAxis = [];
							let yAxis = [];
							let pArray = []
							for(let i=0;i<res.length;i++){
								if(this.boardBase&&i==this.boardBase.lineNum){
									break;
								}
								// 缁熻鍥捐缃浜嗗悧
								xAxis.push(res[i].goodname);
								yAxis.push(parseFloat((res[i].total)));
								pArray.push({
									value: parseFloat((res[i].total)),
									name: res[i].goodname
								})
							}
							var option = {};
							let titleObj = this.line.title
							titleObj.text = '鍟嗗搧閿€棰?
							
							const legendObj = this.line.legend
							let tooltipObj = { trigger: 'item',formatter: '{b} : {c}'}
							tooltipObj = Object.assign(tooltipObj , this.line.tooltip?this.line.tooltip:{})
							let xAxisObj = this.line.xAxis
							xAxisObj.type = 'category'
							
							xAxisObj.data = xAxis
							
							let yAxisObj = this.line.yAxis
							yAxisObj.type = 'value'
							const gridObj = this.line.grid
							let seriesObj = {
								data: yAxis,
								type: 'line',
							}
							seriesObj = Object.assign(seriesObj , this.line.series)
							option = {
								backgroundColor: this.line.backgroundColor,
								color: this.line.color,
								title: titleObj,
								legend: legendObj,
								grid: gridObj,
								tooltip: tooltipObj,
								xAxis: xAxisObj,
								yAxis: yAxisObj,
								series: [seriesObj]
							};
							// 浣跨敤鍒氭寚瀹氱殑閰嶇疆椤瑰拰鏁版嵁鏄剧ず鍥捐〃銆?
							totalChart2.setOption(option);
							  //鏍规嵁绐楀彛鐨勫ぇ灏忓彉鍔ㄥ浘琛?
							window.onresize = function() {
								totalChart2.resize();
							};
						}
					})
				})
			},
			chartDialogShow3() {
				this.chartVisiable3 = true
				this.$http.get('option/orders/goodtype',
				).then(rs=>{
					this.goodtypeChartOptions3 = rs.data.data
				})
				this.chartDialog3()
			},
			// 缁熻鎺ュ彛3
			chartDialog3() {
				this.$nextTick(()=>{
					var buynumberChart3 = echarts.init(document.getElementById("buynumberChart3"),'macarons');
					let params = {}
					if(this.chartQuery3.goodtype) {
						params.conditionColumn = 'goodtype'
						params.conditionValue = this.chartQuery3.goodtype
					}
					this.$http({
						url: "orders/value/goodtype/buynumber",
						method: "get",
						params
					}).then(({data})=>{
						if (data && data.code === 0) {
							let res = data.data;
							let xAxis = [];
							let yAxis = [];
							let pArray = []
							for(let i=0;i<res.length;i++){
								if(this.boardBase&&i==this.boardBase.barNum){
									break;
								}
								// 缁熻鍥捐缃浜嗗悧
								xAxis.push(res[i].goodtype);
								yAxis.push(parseFloat((res[i].total)));
								pArray.push({
									value: parseFloat((res[i].total)),
									name: res[i].goodtype
								})
							}
							var option = {};
							let titleObj = this.bar.title
							titleObj.text = '鍟嗗搧鍒嗙被閿€閲?
							
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
								grid: gridObj,
								tooltip: tooltipObj,
								xAxis: xAxisObj,
								yAxis: yAxisObj,
								series: [seriesObj]
							};
							// 浣跨敤鍒氭寚瀹氱殑閰嶇疆椤瑰拰鏁版嵁鏄剧ず鍥捐〃銆?
							buynumberChart3.setOption(option);
							  //鏍规嵁绐楀彛鐨勫ぇ灏忓彉鍔ㄥ浘琛?
							window.onresize = function() {
								buynumberChart3.resize();
							};
						}
					})
				})
			},
			chartDialogShow4() {
				this.chartVisiable4 = true
				this.$http.get('option/orders/goodtype',
				).then(rs=>{
					this.goodtypeChartOptions4 = rs.data.data
				})
				this.chartDialog4()
			},
			// 缁熻鎺ュ彛4
			chartDialog4() {
				this.$nextTick(()=>{
					var totalChart4 = echarts.init(document.getElementById("totalChart4"),'macarons');
					let params = {}
					if(this.chartQuery4.goodtype) {
						params.conditionColumn = 'goodtype'
						params.conditionValue = this.chartQuery4.goodtype
					}
					this.$http({
						url: "orders/value/goodtype/total",
						method: "get",
						params
					}).then(({data})=>{
						if (data && data.code === 0) {
							let res = data.data;
							let xAxis = [];
							let yAxis = [];
							let pArray = []
							for(let i=0;i<res.length;i++){
								if(this.boardBase&&i==this.boardBase.lineNum){
									break;
								}
								// 缁熻鍥捐缃浜嗗悧
								xAxis.push(res[i].goodtype);
								yAxis.push(parseFloat((res[i].total)));
								pArray.push({
									value: parseFloat((res[i].total)),
									name: res[i].goodtype
								})
							}
							var option = {};
							let titleObj = this.line.title
							titleObj.text = '鍟嗗搧鍒嗙被閿€棰?
							
							const legendObj = this.line.legend
							let tooltipObj = { trigger: 'item',formatter: '{b} : {c}'}
							tooltipObj = Object.assign(tooltipObj , this.line.tooltip?this.line.tooltip:{})
							let xAxisObj = this.line.xAxis
							xAxisObj.type = 'category'
							
							xAxisObj.data = xAxis
							
							let yAxisObj = this.line.yAxis
							yAxisObj.type = 'value'
							const gridObj = this.line.grid
							let seriesObj = {
								data: yAxis,
								type: 'line',
								smooth: true
							}
							seriesObj = Object.assign(seriesObj , this.line.series)
							option = {
								backgroundColor: this.line.backgroundColor,
								color: this.line.color,
								title: titleObj,
								legend: legendObj,
								grid: gridObj,
								tooltip: tooltipObj,
								xAxis: xAxisObj,
								yAxis: yAxisObj,
								series: [seriesObj]
							};
							// 浣跨敤鍒氭寚瀹氱殑閰嶇疆椤瑰拰鏁版嵁鏄剧ず鍥捐〃銆?
							totalChart4.setOption(option);
							  //鏍规嵁绐楀彛鐨勫ぇ灏忓彉鍔ㄥ浘琛?
							window.onresize = function() {
								totalChart4.resize();
							};
						}
					})
				})
			},
			init () {
			},
			search() {
				this.pageIndex = 1;
				this.getDataList();
			},

			// 鑾峰彇鏁版嵁鍒楄〃
			getDataList() {
				this.dataListLoading = true;
				let params = {
					page: this.pageIndex,
					limit: this.pageSize,
					sort: 'id',
					order: 'desc',
					status: this.$route.params.status,
				}
				if(this.searchForm.orderid!='' && this.searchForm.orderid!=undefined){
					params['orderid'] = '%' + this.searchForm.orderid + '%'
				}
				let user = JSON.parse(this.$storage.getObj('userForm'))
				this.$http({
					url: "orders/page",
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.dataList = data.data.list;
						this.totalPage = data.data.total;
					} else {
						this.dataList = [];
						this.totalPage = 0;
					}
					this.dataListLoading = false;
				});
			},
			// 姣忛〉鏁?			sizeChangeHandle(val) {
				this.pageSize = val;
				this.pageIndex = 1;
				this.getDataList();
			},
			// 褰撳墠椤?			currentChangeHandle(val) {
				this.pageIndex = val;
				this.getDataList();
			},
			// 澶氶€?			selectionChangeHandler(val) {
				this.dataListSelections = val;
			},
			// 娣诲姞/淇敼
			addOrUpdateHandler(id,type) {
				this.showFlag = false;
				this.addOrUpdateFlag = true;
				this.crossAddOrUpdateFlag = false;
				if(type!='info'&&type!='msg'){
					type = 'else';
				}
				this.$nextTick(() => {
					this.$refs.addOrUpdate.init(id,type );
				});
			},
			async getAllList() {
				return new Promise(async(resolve, reject) => {
					let params = {
						limit: 10000
					}
					let user = JSON.parse(this.$storage.getObj('userForm'))
					await this.$http({
						url: 'orders/page',
						method: 'get',
						params: params
					}).then(rs => {
						resolve(rs.data.data.list)
					})
				})
			
			},
			// 鍒犻櫎
			async deleteHandler(id ) {
				var ids = id? [Number(id)]: this.dataListSelections.map(item => {
					return Number(item.id);
				});
				await this.$confirm(`纭畾杩涜[${id ? "鍒犻櫎" : "鎵归噺鍒犻櫎"}]鎿嶄綔?`, "鎻愮ず", {
					confirmButtonText: "纭畾",
					cancelButtonText: "鍙栨秷",
					type: "warning"
				}).then(async () => {
					await this.$http({
						url: "orders/delete",
						method: "post",
						data: ids
					}).then(async ({ data }) => {
						if (data && data.code === 0) {
							this.$message({
								message: "鎿嶄綔鎴愬姛",
								type: "success",
								duration: 1500,
								onClose: () => {
									this.search();
								}
							});
			
						} else {
							this.$message.error(data.msg);
						}
					});
				});
			},


		}

	};
</script>
<style lang="scss" scoped>
	//瀵煎嚭excel
	.export-excel-wrapper{
		display: inline-block;
	}
	
	.center-form-pv {
		.el-date-editor.el-input {
			width: auto;
		}
	}
	
	.el-input {
		width: auto;
	}
	
	// form
	.center-form-pv .el-input {
		width: auto;
	}
	.center-form-pv .el-input ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	.center-form-pv .el-select {
		width: auto;
	}
	.center-form-pv .el-select ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	.center-form-pv .el-date-editor {
		width: auto;
	}
	
	.center-form-pv .el-date-editor ::v-deep .el-input__inner {
		border: 0px solid #ddd;
		border-radius: 4px;
		padding: 0 5px 0 25px;
		box-shadow: none;
		outline: none;
		color: inherit;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	
	.center-form-pv .search {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px 0 8px;
		outline: none;
		margin: 0 0 0 3px;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		height: 36px;
	}
	
	.center-form-pv .search:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .add {
		border: 0px solid #6c63ff80;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #31c72c;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .add:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .del {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #bd3124;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .del:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .statis {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #27ba65;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .statis:hover {
		opacity: 0.8;
	}
	
	.center-form-pv .actions .btn18 {
		border: 0px solid #d1d5db;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 10px;
		margin: 4px 8px 4px 0;
		outline: none;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		min-width: 60px;
		height: 36px;
	}
	
	.center-form-pv .actions .btn18:hover {
		opacity: 0.8;
	}
	
	// table
	.el-table ::v-deep .el-table__header-wrapper thead {
		color: #999;
		font-weight: 500;
		width: 100%;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr {
		background: #f2f2f2;
	}
	
	.el-table ::v-deep .el-table__header-wrapper thead tr th {
		padding: 3px 0;
		background: none;
		border-color: #ddd;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: center;
	}

	.el-table ::v-deep .el-table__header-wrapper thead tr th .cell {
		padding: 0 0 0 5px;
		word-wrap: normal;
		color: #333;
		white-space: normal;
		font-weight: bold;
		display: flex;
		vertical-align: middle;
		font-size: 14px;
		line-height: 24px;
		text-overflow: ellipsis;
		word-break: break-all;
		width: 100%;
		justify-content: flex-start;
		align-items: center;
		position: relative;
		min-width: 110px;
	}

	.el-table ::v-deep .el-table__body-wrapper {
		position: relative;
	}
	.el-table ::v-deep .el-table__body-wrapper tbody {
		width: 100%;
	}

	.el-table ::v-deep .el-table__body-wrapper tbody tr {
		background: #fff;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
		
	.el-table ::v-deep .el-table__body-wrapper tbody tr:hover td {
		padding: 4px 0;
		color: #2dc7a3;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td {
		padding: 4px 0;
		color: #555;
		background: none;
		border-color: #eee;
		border-width: 0 1px 1px 0;
		border-style: solid;
		text-align: left;
	}

	.el-table ::v-deep .el-table__body-wrapper tbody tr td .cell {
		padding: 0 0 0 5px;
		overflow: hidden;
		word-break: break-all;
		white-space: normal;
		font-size: inherit;
		line-height: 24px;
		text-overflow: ellipsis;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2dc7a3;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: 3;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .view:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add {
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .add:hover {
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2d9dc6;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: -1;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .edit:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 8px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #c72d46;
		width: auto;
		font-size: inherit;
		height: 32px;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .del:hover {
		opacity: 0.8;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8 {
		border: 0px solid #165dff50;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 5px;
		margin: 0 5px 5px 0;
		outline: none;
		color: #fff;
		background: #2dc75f;
		width: auto;
		font-size: inherit;
		height: 32px;
		order: 5;
	}
	
	.el-table ::v-deep .el-table__body-wrapper tbody tr td .btn8:hover {
		opacity: 0.8;
	}
	
	// pagination
	.main-content .el-pagination ::v-deep .el-pagination__total {
		margin: 0 auto 0 0;
		color: #666;
		font-weight: 400;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-prev {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-next {
		border: none;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #666;
		background: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		min-width: 35px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-prev:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .btn-next:disabled {
		border: none;
		cursor: not-allowed;
		border-radius: 2px;
		padding: 0;
		margin: 0 5px;
		color: #C0C4CC;
		background: #f4f4f5;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}

	.main-content .el-pagination ::v-deep .el-pager {
		padding: 0;
		margin: 0;
		display: inline-block;
		vertical-align: top;
	}

	.main-content .el-pagination ::v-deep .el-pager .number {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #666;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #fff;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pager .number:hover {
		cursor: pointer;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #2dc7a3;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pager .number.active {
		cursor: default;
		padding: 0 4px;
		margin: 0 5px;
		color: #fff;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		border-radius: 2px;
		background: #2dc7a3;
		text-align: center;
		min-width: 30px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes {
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input {
		margin: 0 5px;
		width: 100px;
		position: relative;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 25px 0 8px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input span.el-input__suffix {
		top: 0;
		position: absolute;
		right: 0;
		height: 100%;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
		cursor: pointer;
		color: #C0C4CC;
		width: 25px;
		font-size: 15px;
		line-height: 28px;
		text-align: center;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump {
		margin: 0 0 0 24px;
		color: #606266;
		display: inline-block;
		vertical-align: top;
		font-size: 15px;
		line-height: 28px;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input {
		border-radius: 3px;
		padding: 0 2px;
		margin: 0 2px;
		display: inline-block;
		width: 50px;
		font-size: 15px;
		line-height: 18px;
		position: relative;
		text-align: center;
		height: 28px;
	}
	
	.main-content .el-pagination ::v-deep .el-pagination__jump .el-input .el-input__inner {
		border: 1px solid #DCDFE6;
		cursor: pointer;
		padding: 0 3px;
		color: #606266;
		display: inline-block;
		font-size: 15px;
		line-height: 28px;
		border-radius: 3px;
		outline: 0;
		background: #FFF;
		width: 100%;
		text-align: center;
		height: 28px;
	}
	
	// list one
	.one .list1-view {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-view:hover {
		opacity: 0.8;
	}
	
	.one .list1-edit {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f60;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-edit:hover {
		opacity: 0.8;
	}
	
	.one .list1-del {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #f00;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-del:hover {
		opacity: 0.8;
	}
	
	.one .list1-btn8 {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 0px;
		margin: 0 5px 0 0;
		outline: none;
		color: #333;
		background: none;
		width: auto;
		font-size: 14px;
		height: 32px;
	}
	
	.one .list1-btn8:hover {
		opacity: 0.8;
	}
	
	.main-content .el-table .el-switch {
		display: inline-flex;
		vertical-align: middle;
		line-height: 30px;
		position: relative;
		align-items: center;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--left {
		cursor: pointer;
		margin: 0 10px 0 0;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__label--right {
		cursor: pointer;
		margin: 0 0 0 10px;
		color: #333;
		font-weight: 500;
		display: none;
		vertical-align: middle;
		font-size: 16px;
		transition: .2s;
		height: 30px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core {
		border: 1px solid #ff8b00;
		cursor: pointer;
		border-radius: 15px;
		margin: 0;
		outline: 0;
		background: #ff8b00;
		display: inline-block;
		width: 36px;
		box-sizing: border-box;
		transition: border-color .3s,background-color .3s;
		height: 18px;
	}
	.main-content .el-table .el-switch ::v-deep .el-switch__core::after {
		border-radius: 100%;
		top: 1px;
		left: 2px;
		background: #FFF;
		width: 14px;
		position: absolute;
		transition: all .3s;
		height: 14px;
	}
	.main-content .el-table .el-switch.is-checked ::v-deep .el-switch__core::after {
		margin: 0 0 0 -16px;
		left: 100%;
	}
	
	.main-content .el-table .el-rate ::v-deep .el-rate__item {
		cursor: pointer;
		display: inline-block;
		vertical-align: middle;
		font-size: 0;
		position: relative;
	}
	.main-content .el-table .el-rate ::v-deep .el-rate__item .el-rate__icon {
		margin: 0 3px;
		display: inline-block;
		font-size: 18px;
		position: relative;
		transition: .3s;
	}

	.chartDialog ::v-deep .el-dialog {
		background: #fff;
	}
</style>

