






































<template>
	<div class="addEdit-block">
		<el-form
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="180px"
		>
			<template >
				<el-form-item class="input" v-if="type!='info'"  label="涓枃鍚? prop="zhongwenming" >
					<el-input v-model="ruleForm.zhongwenming" placeholder="涓枃鍚? clearable  :readonly="ro.zhongwenming"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="涓枃鍚? prop="zhongwenming" >
					<el-input v-model="ruleForm.zhongwenming" placeholder="涓枃鍚? readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鑻辨枃鍚? prop="yingwenming" >
					<el-input v-model="ruleForm.yingwenming" placeholder="鑻辨枃鍚? clearable  :readonly="ro.yingwenming"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鑻辨枃鍚? prop="yingwenming" >
					<el-input v-model="ruleForm.yingwenming" placeholder="鑻辨枃鍚? readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="鍝佺鍒嗙被" prop="pinzhongfenlei" >
					<el-select :disabled="ro.pinzhongfenlei" v-model="ruleForm.pinzhongfenlei" placeholder="璇烽€夋嫨鍝佺鍒嗙被"  filterable>
						<el-option
							v-for="(item,index) in pinzhongfenleiOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="鍝佺鍒嗙被" prop="pinzhongfenlei" >
					<el-input v-model="ruleForm.pinzhongfenlei"
						placeholder="鍝佺鍒嗙被" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="绉嶅睘" prop="zhongshu" >
					<el-input v-model="ruleForm.zhongshu" placeholder="绉嶅睘" clearable  :readonly="ro.zhongshu"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="绉嶅睘" prop="zhongshu" >
					<el-input v-model="ruleForm.zhongshu" placeholder="绉嶅睘" readonly></el-input>
				</el-form-item>
				<el-form-item class="upload" v-if="type!='info' && !ro.guanshangyutupian" label="瑙傝祻楸煎浘鐗? prop="guanshangyutupian" >
					<file-upload
						tip="鐐瑰嚮涓婁紶瑙傝祻楸煎浘鐗?
						action="file/upload"
						:limit="3"
						:disabled="ro.guanshangyutupian"
						:multiple="true"
						:fileUrls="ruleForm.guanshangyutupian?ruleForm.guanshangyutupian:''"
						@change="guanshangyutupianUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item class="upload" v-else-if="ruleForm.guanshangyutupian" label="瑙傝祻楸煎浘鐗? prop="guanshangyutupian" >
					<img v-if="ruleForm.guanshangyutupian.substring(0,4)=='http'&&ruleForm.guanshangyutupian.split(',w').length>1" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.guanshangyutupian" width="100" height="100" @click="imgPreView(ruleForm.tupian)">
					<img v-else-if="ruleForm.guanshangyutupian.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.guanshangyutupian.split(',')[0]" width="100" height="100" @click="imgPreView(ruleForm.tupian.split(',')[0])">
					<img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.guanshangyutupian.split(',')" :src="$base.url+item" width="100" height="100" @click="imgPreView($base.url+item)">
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鏈€灏忔按浣? prop="zuixiaoshuiti" >
					<el-input v-model="ruleForm.zuixiaoshuiti" placeholder="鏈€灏忔按浣? clearable  :readonly="ro.zuixiaoshuiti"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鏈€灏忔按浣? prop="zuixiaoshuiti" >
					<el-input v-model="ruleForm.zuixiaoshuiti" placeholder="鏈€灏忔按浣? readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="楗插吇闅惧害" prop="siyangnandu" >
					<el-select :disabled="ro.siyangnandu" v-model="ruleForm.siyangnandu" placeholder="璇烽€夋嫨楗插吇闅惧害"  filterable>
						<el-option
							v-for="(item,index) in siyangnanduOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="楗插吇闅惧害" prop="siyangnandu" >
					<el-input v-model="ruleForm.siyangnandu"
						placeholder="楗插吇闅惧害" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鎴愪綋灏哄" prop="chengtichicun" >
					<el-input v-model="ruleForm.chengtichicun" placeholder="鎴愪綋灏哄" clearable  :readonly="ro.chengtichicun"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鎴愪綋灏哄" prop="chengtichicun" >
					<el-input v-model="ruleForm.chengtichicun" placeholder="鎴愪綋灏哄" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鐝婄憵缂稿吋瀹? prop="shanhugangjianrong" >
					<el-input v-model="ruleForm.shanhugangjianrong" placeholder="鐝婄憵缂稿吋瀹? clearable  :readonly="ro.shanhugangjianrong"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鐝婄憵缂稿吋瀹? prop="shanhugangjianrong" >
					<el-input v-model="ruleForm.shanhugangjianrong" placeholder="鐝婄憵缂稿吋瀹? readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="涓昏浜у湴" prop="zhuyaochandi" >
					<el-input v-model="ruleForm.zhuyaochandi" placeholder="涓昏浜у湴" clearable  :readonly="ro.zhuyaochandi"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="涓昏浜у湴" prop="zhuyaochandi" >
					<el-input v-model="ruleForm.zhuyaochandi" placeholder="涓昏浜у湴" readonly></el-input>
				</el-form-item>
			</template>
			<el-form-item class="textarea" v-if="type!='info'" label="椋熺墿瑕佹眰" prop="shiwuyaoqiu" >
				<el-input
					style="min-width: 200px; max-width: 600px;"
					type="textarea"
					:rows="8"
					placeholder="椋熺墿瑕佹眰"
					v-model="ruleForm.shiwuyaoqiu" >
				</el-input>
			</el-form-item>
			<el-form-item v-else-if="ruleForm.shiwuyaoqiu" label="椋熺墿瑕佹眰" prop="shiwuyaoqiu"  class="textBox">
				<span class="text">{{ruleForm.shiwuyaoqiu}}</span>
			</el-form-item>
			<el-form-item class="textarea" v-if="type!='info'" label="鎬ф儏" prop="xingqing" >
				<el-input
					style="min-width: 200px; max-width: 600px;"
					type="textarea"
					:rows="8"
					placeholder="鎬ф儏"
					v-model="ruleForm.xingqing" >
				</el-input>
			</el-form-item>
			<el-form-item v-else-if="ruleForm.xingqing" label="鎬ф儏" prop="xingqing"  class="textBox">
				<span class="text">{{ruleForm.xingqing}}</span>
			</el-form-item>
			<el-form-item class="textarea" v-if="type!='info'" label="瑕佹眰" prop="yaoqiu" >
				<el-input
					style="min-width: 200px; max-width: 600px;"
					type="textarea"
					:rows="8"
					placeholder="瑕佹眰"
					v-model="ruleForm.yaoqiu" >
				</el-input>
			</el-form-item>
			<el-form-item v-else-if="ruleForm.yaoqiu" label="瑕佹眰" prop="yaoqiu"  class="textBox">
				<span class="text">{{ruleForm.yaoqiu}}</span>
			</el-form-item>
			<el-form-item class="textarea" v-if="type!='info'" label="楗插吇鏂规硶" prop="siyangfangfa" >
				<el-input
					style="min-width: 200px; max-width: 600px;"
					type="textarea"
					:rows="8"
					placeholder="楗插吇鏂规硶"
					v-model="ruleForm.siyangfangfa" >
				</el-input>
			</el-form-item>
			<el-form-item v-else-if="ruleForm.siyangfangfa" label="楗插吇鏂规硶" prop="siyangfangfa"  class="textBox">
				<span class="text">{{ruleForm.siyangfangfa}}</span>
			</el-form-item>
			<el-form-item v-if="type!='info'" class="editorBox" label="鐢熺墿绠€浠? prop="shengwujianjie" >
				<editor 
					style="min-width: 200px; max-width: 600px;"
					v-model="ruleForm.shengwujianjie" 
					class="editor"
					myQuillEditor="shengwujianjie"
					action="file/upload">
				</editor>
			</el-form-item>
			<el-form-item v-else-if="ruleForm.shengwujianjie" label="鐢熺墿绠€浠? prop="shengwujianjie"  class="textBox">
				<span class="text ql-snow ql-editor" v-html="ruleForm.shengwujianjie"></span>
			</el-form-item>
			<el-form-item class="btn">
				<el-button class="btn3"  v-if="type!='info'" type="success" @click="onSubmit">
					<span class="icon iconfont icon-queren20"></span>
					鎻愪氦
				</el-button>
				<el-button class="btn4" v-if="type!='info'" type="success" @click="back()">
					<span class="icon iconfont icon-guanbi15"></span>
					鍙栨秷
				</el-button>
				<el-button class="btn5" v-if="type=='info'" type="success" @click="back()">
					<span class="icon iconfont icon-fanhui14"></span>
					杩斿洖
				</el-button>
			</el-form-item>
		</el-form>
    

	</div>
</template>
<script>
	import { 
		isIntNumer,
	} from "@/utils/validate";
	export default {
		data() {
			var validateIntNumber = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isIntNumer(value)) {
					callback(new Error("璇疯緭鍏ユ暣鏁?));
				} else {
					callback();
				}
			};
			return {
				id: '',
				type: '',
			
			
				ro:{
					zhongwenming : false,
					yingwenming : false,
					pinzhongfenlei : false,
					zhongshu : false,
					guanshangyutupian : false,
					zuixiaoshuiti : false,
					siyangnandu : false,
					chengtichicun : false,
					shanhugangjianrong : false,
					zhuyaochandi : false,
					shiwuyaoqiu : false,
					xingqing : false,
					yaoqiu : false,
					siyangfangfa : false,
					shengwujianjie : false,
					thumbsupnum : false,
					crazilynum : false,
					clicknum : false,
					storeupnum : false,
				},
			
				ruleForm: {
					zhongwenming: '',
					yingwenming: '',
					pinzhongfenlei: '',
					zhongshu: '',
					guanshangyutupian: '',
					zuixiaoshuiti: '',
					siyangnandu: '',
					chengtichicun: '',
					shanhugangjianrong: '',
					zhuyaochandi: '',
					shiwuyaoqiu: '',
					xingqing: '',
					yaoqiu: '',
					siyangfangfa: '',
					shengwujianjie: '',
					thumbsupnum: Number('0'),
					crazilynum: Number('0'),
					clicknum: Number('0'),
					storeupnum: Number('0'),
				},
				pinzhongfenleiOptions: [],
				siyangnanduOptions: [],

				rules: {
					zhongwenming: [
					],
					yingwenming: [
					],
					pinzhongfenlei: [
					],
					zhongshu: [
					],
					guanshangyutupian: [
					],
					zuixiaoshuiti: [
					],
					siyangnandu: [
					],
					chengtichicun: [
					],
					shanhugangjianrong: [
					],
					zhuyaochandi: [
					],
					shiwuyaoqiu: [
					],
					xingqing: [
					],
					yaoqiu: [
					],
					siyangfangfa: [
					],
					shengwujianjie: [
					],
					thumbsupnum: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					crazilynum: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					clicknum: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					storeupnum: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
				},
			};
		},
		props: ["parent"],
		computed: {
			sessionForm() {
				return JSON.parse(this.$storage.getObj('userForm'))
			},
			sessionTable() {
				return this.$storage.get('sessionTable')
			},



		},
		components: {
		},
		created() {
		},
		methods: {
			imgPreView(url){
				this.$parent.imgPreView(url)
			},
			// 涓嬭浇
			download(file){
				window.open(`${file}`)
			},
			// 鍒濆鍖?
			init(id,type ) {
				if (id) {
					this.id = id;
					this.type = type;
				}
				if(this.type=='info'||this.type=='else'||this.type=='msg'){
					this.info(id);
				}else if(this.type=='logistics'){
					for(let x in this.ro) {
						this.ro[x] = true
					}
					this.logistics=false;
					this.info(id);
				}else if(this.type=='cross'){
					var obj = this.$storage.getObj('crossObj');
					for (var o in obj){
						if(o=='zhongwenming'){
							this.ruleForm.zhongwenming = obj[o];
							this.ro.zhongwenming = true;
							continue;
						}
						if(o=='yingwenming'){
							this.ruleForm.yingwenming = obj[o];
							this.ro.yingwenming = true;
							continue;
						}
						if(o=='pinzhongfenlei'){
							this.ruleForm.pinzhongfenlei = obj[o];
							this.ro.pinzhongfenlei = true;
							continue;
						}
						if(o=='zhongshu'){
							this.ruleForm.zhongshu = obj[o];
							this.ro.zhongshu = true;
							continue;
						}
						if(o=='guanshangyutupian'){
							this.ruleForm.guanshangyutupian = obj[o];
							this.ro.guanshangyutupian = true;
							continue;
						}
						if(o=='zuixiaoshuiti'){
							this.ruleForm.zuixiaoshuiti = obj[o];
							this.ro.zuixiaoshuiti = true;
							continue;
						}
						if(o=='siyangnandu'){
							this.ruleForm.siyangnandu = obj[o];
							this.ro.siyangnandu = true;
							continue;
						}
						if(o=='chengtichicun'){
							this.ruleForm.chengtichicun = obj[o];
							this.ro.chengtichicun = true;
							continue;
						}
						if(o=='shanhugangjianrong'){
							this.ruleForm.shanhugangjianrong = obj[o];
							this.ro.shanhugangjianrong = true;
							continue;
						}
						if(o=='zhuyaochandi'){
							this.ruleForm.zhuyaochandi = obj[o];
							this.ro.zhuyaochandi = true;
							continue;
						}
						if(o=='shiwuyaoqiu'){
							this.ruleForm.shiwuyaoqiu = obj[o];
							this.ro.shiwuyaoqiu = true;
							continue;
						}
						if(o=='xingqing'){
							this.ruleForm.xingqing = obj[o];
							this.ro.xingqing = true;
							continue;
						}
						if(o=='yaoqiu'){
							this.ruleForm.yaoqiu = obj[o];
							this.ro.yaoqiu = true;
							continue;
						}
						if(o=='siyangfangfa'){
							this.ruleForm.siyangfangfa = obj[o];
							this.ro.siyangfangfa = true;
							continue;
						}
						if(o=='shengwujianjie'){
							this.ruleForm.shengwujianjie = obj[o];
							this.ro.shengwujianjie = true;
							continue;
						}
						if(o=='thumbsupnum'){
							this.ruleForm.thumbsupnum = obj[o];
							this.ro.thumbsupnum = true;
							continue;
						}
						if(o=='crazilynum'){
							this.ruleForm.crazilynum = obj[o];
							this.ro.crazilynum = true;
							continue;
						}
						if(o=='clicknum'){
							this.ruleForm.clicknum = obj[o];
							this.ro.clicknum = true;
							continue;
						}
						if(o=='storeupnum'){
							this.ruleForm.storeupnum = obj[o];
							this.ro.storeupnum = true;
							continue;
						}
					}
				}

				// 鑾峰彇鐢ㄦ埛淇℃伅
				this.$http({
					url: `${this.sessionTable}/session`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						var json = data.data;
					} else {
						this.$message.error(data.msg);
					}
				});
				this.$http({
					url: `option/pinzhongfenlei/pinzhongfenlei`,
					method: "get",
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.pinzhongfenleiOptions = data.data;
					} else {
						this.$message.error(data.msg);
					}
				});
				this.siyangnanduOptions = "绠€鍗?涓瓑,鍥伴毦".split(',')
			
			},
			// 澶氱骇鑱斿姩鍙傛暟

			async info(id) {
				await this.$http({
					url: `guanshangyubaike/info/${id}`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.ruleForm = data.data;
						//瑙ｅ喅鍓嶅彴涓婁紶鍥剧墖鍚庡彴涓嶆樉绀虹殑闂
						let reg=new RegExp('../../../upload','g')//g浠ｈ〃鍏ㄩ儴
						this.ruleForm.shengwujianjie = this.ruleForm.shengwujianjie.replace(reg,'../../../guanshangl0g62fht/upload');
					} else {
						this.$message.error(data.msg);
					}
				});
			},

			// 鎻愪氦
			async onSubmit() {
					await this.$refs["ruleForm"].validate(async valid => {
						if (valid) {
							if(this.ruleForm.guanshangyutupian!=null) {
								this.ruleForm.guanshangyutupian = this.ruleForm.guanshangyutupian.replace(new RegExp(this.$base.url,"g"),"");
							}
							var objcross = this.$storage.getObj('crossObj');
							if(!this.ruleForm.id) {
								delete this.ruleForm.userid
							}
							if(this.type=='cross'){
								var statusColumnName = this.$storage.get('statusColumnName');
								var statusColumnValue = this.$storage.get('statusColumnValue');
								if(statusColumnName!='') {
									var obj = this.$storage.getObj('crossObj');
									if(statusColumnName && !statusColumnName.startsWith("[")) {
										for (var o in obj){
											if(o==statusColumnName){
												obj[o] = statusColumnValue;
											}
										}
										var table = this.$storage.get('crossTable');
										await this.$http({
											url: `${table}/update`,
											method: "post",
											data: obj
										}).then(({ data }) => {});
									}
								}
							}
							await this.$http({
								url: `guanshangyubaike/${!this.ruleForm.id ? "save" : "update"}`,
								method: "post",
								data: this.ruleForm
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									this.$message({
										message: "鎿嶄綔鎴愬姛",
										type: "success",
										duration: 1500,
										onClose: () => {
											this.parent.showFlag = true;
											this.parent.addOrUpdateFlag = false;
											this.parent.guanshangyubaikeCrossAddOrUpdateFlag = false;
											this.parent.search();
										}
									});
								} else {
									this.$message.error(data.msg);
								}
							});
						}
					});
			},
			// 鑾峰彇uuid
			getUUID () {
				return new Date().getTime();
			},
			// 杩斿洖
			back() {
				this.parent.showFlag = true;
				this.parent.addOrUpdateFlag = false;
				this.parent.guanshangyubaikeCrossAddOrUpdateFlag = false;
			},
			guanshangyutupianUploadChange(fileUrls) {
				this.ruleForm.guanshangyutupian = fileUrls;
			},
		}
	};
</script>
<style lang="scss" scoped>
	.addEdit-block {
		padding: 10px;
	}
	.add-update-preview {
		border: 0px solid #e5e7eb;
		border-radius: 10px;
		padding: 50px 20% 30px 10%;
		box-shadow: none;
		background: #fff;
		display: flex;
		flex-wrap: wrap;
	}
	.amap-wrapper {
		width: 100%;
		height: 500px;
	}
	
	.search-box {
		position: absolute;
	}
	
	.el-date-editor.el-input {
		width: auto;
	}
	.add-update-preview ::v-deep .el-form-item {
		margin: 0 0 20px 0;
		width: 48%;
	}
	.add-update-preview .el-form-item ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item ::v-deep .el-form-item__content {
		margin: 0 0 0 80px;
		display: flex;
		align-items: center;
	}
	.add-update-preview ::v-deep .el-form-item.editorBox {
		margin: 0 0 20px 0;
		width: 100%;
	}
	.add-update-preview .el-form-item.editorBox ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		font-weight: 500;
		width: 180px;
		font-size: 14px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.editorBox ::v-deep .el-form-item__content {
		margin: 0;
		display: flex;
		align-items: center;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor {
		box-shadow: none;
		max-width: 100% !important;
		width: 100%;
		height: auto;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-toolbar {
		border: 1px solid #eee;
		border-radius: 4px 4px 0 0;
		background: none;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-container {
		border: 1px solid #eee;
		border-radius: 0 0 4px 4px;
		background: none;
		min-height: 200px;
		border-width: 0px 1px 1px;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-container .ql-blank::before {
		color: #999;
	}
	
	.add-update-preview ::v-deep .el-form-item.textBox {
		margin: 0 0 20px 0;
		width: 100%;
	}
	.add-update-preview .el-form-item.textBox ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.textBox ::v-deep .el-form-item__content {
		margin: 0;
		display: flex;
		align-items: center;
	}
	.add-update-preview ::v-deep.el-form-item.textBox span.text {
		padding: 0;
		color: #666;
		font-weight: 500;
		display: inline-block;
		font-size: 16px;
		line-height: 40px;
	}
	
	.add-update-preview .el-input {
		width: 100%;
	}
	.add-update-preview .el-input ::v-deep .el-input__inner {
		border: 1px solid #eee;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #333;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input ::v-deep .el-input__inner[readonly="readonly"] {
		border: 0px solid #ddd;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #999;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input-number {
		text-align: left;
		width: 100%;
	}
	.add-update-preview .el-input-number ::v-deep .el-input__inner {
		text-align: left;
		border: 1px solid #eee;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #333;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input-number ::v-deep .is-disabled .el-input__inner {
		text-align: left;
		border: 0px solid #ddd;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #999;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input-number ::v-deep .el-input-number__decrease {
		display: none;
	}
	.add-update-preview .el-input-number ::v-deep .el-input-number__increase {
		display: none;
	}
	.add-update-preview .el-select {
		width: 100%;
	}
	.add-update-preview .el-select ::v-deep .el-input__inner {
		border: 1px solid #eee;
		border-radius: 4px;
		padding: 0 10px;
		box-shadow: none;
		outline: none;
		color: #333;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-select ::v-deep .is-disabled .el-input__inner {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 10px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: #f5f5f5;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-date-editor {
		width: 100%;
	}
	.add-update-preview .el-date-editor ::v-deep .el-input__inner {
		border: 1px solid #eee;
		border-radius: 4px;
		padding: 0 10px 0 30px;
		box-shadow: none;
		outline: none;
		color: #333;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-date-editor ::v-deep .el-input__inner[readonly="readonly"] {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 10px 0 30px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: #f5f5f5;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .viewBtn {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #fff;
		background: #2dc7a5;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .viewBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .downBtn {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #fff;
		background: #2dc7a5;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .downBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .unBtn {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #000;
		background: #eee;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #000;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .unBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview ::v-deep .el-upload--picture-card {
		background: transparent;
		border: 0;
		border-radius: 0;
		width: auto;
		height: auto;
		line-height: initial;
		vertical-align: middle;
	}
	
	.add-update-preview ::v-deep .upload .upload-img {
		border: 1px solid #eee;
		cursor: pointer;
		border-radius: 6px;
		color: #999;
		width: 80px;
		font-size: 26px;
		line-height: 80px;
		text-align: center;
		height: 80px;
	}
	
	.add-update-preview ::v-deep .el-upload-list .el-upload-list__item {
		border: 1px solid #eee;
		cursor: pointer;
		border-radius: 6px;
		color: #999;
		width: 80px;
		font-size: 26px;
		line-height: 80px;
		text-align: center;
		height: 80px;
	}
	
	.add-update-preview ::v-deep .el-upload .el-icon-plus {
		border: 1px solid #eee;
		cursor: pointer;
		border-radius: 6px;
		color: #999;
		width: 80px;
		font-size: 26px;
		line-height: 80px;
		text-align: center;
		height: 80px;
	}
	.add-update-preview ::v-deep .el-upload__tip {
		color: #838fa1;
	}
	.add-update-preview ::v-deep .el-form-item.fileupload {
		margin: 0 0 20px 0;
		width: 48%;
	}
	.add-update-preview .el-form-item.fileupload ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.fileupload ::v-deep .el-form-item__content {
		margin: 0 0 0 80px;
		display: flex;
		align-items: center;
	}
	.add-update-preview .el-form-item.fileupload ::v-deep .el-upload-dragger {
		border: 1px solid #eee;
		cursor: pointer;
		background-color: #fff;
		border-radius: 6px;
		padding: 0px 20px 20px;
		overflow: hidden;
		width: auto;
		box-sizing: border-box;
		text-align: center;
		height: auto;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-icon-upload {
		margin: 0;
		color: #2dc7a5;
		font-size: 60px;
		line-height: 1;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-upload__text {
		color: #606266;
		textalign: center;
		fontsize: 14px;
		line-height: 1;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-upload__text em {
		fontstyle: normal;
		color: #000;
	}
	
	.add-update-preview .el-textarea ::v-deep .el-textarea__inner {
		border: 1px solid #eee;
		border-radius: 4px;
		padding: 12px;
		box-shadow: none;
		outline: none;
		color: #666;
		width: 100%;
		font-size: 16px;
		height: auto;
	}
	.add-update-preview .el-textarea ::v-deep .el-textarea__inner[readonly="readonly"] {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 12px;
		box-shadow: none;
		outline: none;
		color: #666;
		width: 100%;
		font-size: 16px;
		height: auto;
	}
	.add-update-preview ::v-deep .el-form-item.btn {
		padding: 0;
		margin: 10px auto 0;
		display: flex;
		width: 100%;
		justify-content: center;
		.btn1 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #2dc760;
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn1:hover {
			opacity: 0.8;
		}
		.btn2 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #2d96c7;
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn2:hover {
			opacity: 0.8;
		}
		.btn3 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #2dc7a3;
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn3:hover {
			opacity: 0.8;
		}
		.btn4 {
			border: 0px solid #eee;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #c72d55;
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn4:hover {
			opacity: 0.8;
		}
		.btn5 {
			border: 0px solid #eee;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #c7af2d;
			width: auto;
			font-size: 16px;
			min-width: 110px;
			height: 40px;
			.iconfont {
				margin: 0 2px;
				color: #fff;
				font-size: 16px;
				height: 40px;
			}
		}
		.btn5:hover {
			opacity: 0.8;
		}
	}
	.add-update-preview .el-form-item.btn ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.btn ::v-deep .el-form-item__content {
	}
</style>

