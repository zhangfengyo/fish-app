






































<template>
	<div class="add-update-preview">
		<el-form
			class="add-update-form"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="200px"
			>
			<el-form-item class="add-item" label="涓枃鍚? prop="zhongwenming">
				<el-input v-model="ruleForm.zhongwenming" 
					placeholder="涓枃鍚? clearable :readonly="ro.zhongwenming"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="鑻辨枃鍚? prop="yingwenming">
				<el-input v-model="ruleForm.yingwenming" 
					placeholder="鑻辨枃鍚? clearable :readonly="ro.yingwenming"></el-input>
			</el-form-item>
			<el-form-item class="add-item"  label="鍝佺鍒嗙被" prop="pinzhongfenlei">
				<el-select v-model="ruleForm.pinzhongfenlei" placeholder="璇烽€夋嫨鍝佺鍒嗙被" :disabled="ro.pinzhongfenlei"  filterable>
					<el-option
						v-for="(item,index) in pinzhongfenleiOptions"
						:key="index"
						:label="item"
						:value="item">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item class="add-item" label="绉嶅睘" prop="zhongshu">
				<el-input v-model="ruleForm.zhongshu" 
					placeholder="绉嶅睘" clearable :readonly="ro.zhongshu"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="瑙傝祻楸煎浘鐗? v-if="type!='cross' || (type=='cross' && !ro.guanshangyutupian)" prop="guanshangyutupian">
				<file-upload
					tip="鐐瑰嚮涓婁紶瑙傝祻楸煎浘鐗?
					action="file/upload"
					:limit="3"
					:multiple="true"
					:disabled="ro.guanshangyutupian"
					:fileUrls="ruleForm.guanshangyutupian?ruleForm.guanshangyutupian:''"
					@change="guanshangyutupianUploadChange"
					></file-upload>
			</el-form-item>
			<el-form-item class="add-item" v-else label="瑙傝祻楸煎浘鐗? prop="guanshangyutupian">
				<img v-if="ruleForm.guanshangyutupian.substring(0,4)=='http'" class="upload-img" v-bind:key="index" :src="ruleForm.guanshangyutupian.split(',')[0]">
				<img v-else class="upload-img" v-bind:key="index" v-for="(item,index) in ruleForm.guanshangyutupian.split(',')" :src="baseUrl+item">
			</el-form-item>
			<el-form-item class="add-item" label="鏈€灏忔按浣? prop="zuixiaoshuiti">
				<el-input v-model="ruleForm.zuixiaoshuiti" 
					placeholder="鏈€灏忔按浣? clearable :readonly="ro.zuixiaoshuiti"></el-input>
			</el-form-item>
			<el-form-item class="add-item"  label="楗插吇闅惧害" prop="siyangnandu">
				<el-select v-model="ruleForm.siyangnandu" placeholder="璇烽€夋嫨楗插吇闅惧害" :disabled="ro.siyangnandu"  filterable>
					<el-option
						v-for="(item,index) in siyangnanduOptions"
						:key="index"
						:label="item"
						:value="item">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item class="add-item" label="鎴愪綋灏哄" prop="chengtichicun">
				<el-input v-model="ruleForm.chengtichicun" 
					placeholder="鎴愪綋灏哄" clearable :readonly="ro.chengtichicun"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="鐝婄憵缂稿吋瀹? prop="shanhugangjianrong">
				<el-input v-model="ruleForm.shanhugangjianrong" 
					placeholder="鐝婄憵缂稿吋瀹? clearable :readonly="ro.shanhugangjianrong"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="涓昏浜у湴" prop="zhuyaochandi">
				<el-input v-model="ruleForm.zhuyaochandi" 
					placeholder="涓昏浜у湴" clearable :readonly="ro.zhuyaochandi"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="椋熺墿瑕佹眰" prop="shiwuyaoqiu">
				<el-input
					type="textarea"
					:rows="8"
					:disabled="ro.shiwuyaoqiu"
					placeholder="椋熺墿瑕佹眰"
					v-model="ruleForm.shiwuyaoqiu">
					</el-input>
			</el-form-item>
			<el-form-item class="add-item" label="鎬ф儏" prop="xingqing">
				<el-input
					type="textarea"
					:rows="8"
					:disabled="ro.xingqing"
					placeholder="鎬ф儏"
					v-model="ruleForm.xingqing">
					</el-input>
			</el-form-item>
			<el-form-item class="add-item" label="瑕佹眰" prop="yaoqiu">
				<el-input
					type="textarea"
					:rows="8"
					:disabled="ro.yaoqiu"
					placeholder="瑕佹眰"
					v-model="ruleForm.yaoqiu">
					</el-input>
			</el-form-item>
			<el-form-item class="add-item" label="楗插吇鏂规硶" prop="siyangfangfa">
				<el-input
					type="textarea"
					:rows="8"
					:disabled="ro.siyangfangfa"
					placeholder="楗插吇鏂规硶"
					v-model="ruleForm.siyangfangfa">
					</el-input>
			</el-form-item>
			<el-form-item class="add-item" label="鐢熺墿绠€浠? prop="shengwujianjie">
				<editor 
					v-model="ruleForm.shengwujianjie" 
					class="editor" 
					myQuillEditor="shengwujianjie"
					action="file/upload">
				</editor>
			</el-form-item>

			<el-form-item class="add-btn-item">
				<el-button class="submitBtn"  type="primary" @click="onSubmit(null)">
					<span class="icon iconfont icon-xiugai17"></span>
					<span class="text">鎻愪氦淇℃伅</span>
				</el-button>
				<el-button class="closeBtn" @click="back()">
					<span class="icon iconfont icon-shanchu8"></span>
					<span class="text">鍙栨秷</span>
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				id: '',
				baseUrl: '',
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
				type: '',
				userTableName: localStorage.getItem('UserTableName'),
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
					thumbsupnum: '',
					crazilynum: '',
					clicknum: '',
					storeupnum: '',
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
						{ validator: this.$validate.isIntNumer, trigger: 'blur' },
					],
					crazilynum: [
						{ validator: this.$validate.isIntNumer, trigger: 'blur' },
					],
					clicknum: [
						{ validator: this.$validate.isIntNumer, trigger: 'blur' },
					],
					storeupnum: [
						{ validator: this.$validate.isIntNumer, trigger: 'blur' },
					],
				},
				centerType: false,
			};
		},
		computed: {
			sessionForm() {
				return JSON.parse(localStorage.getItem('sessionForm'))
			},



		},
		components: {
		},
		created() {
			if(this.$route.query.centerType){
				this.centerType = true
			}
			//this.bg();
			let type = this.$route.query.type ? this.$route.query.type : '';
			this.init(type);
			this.baseUrl = this.$config.baseUrl;
		},
		methods: {
			getMakeZero(s) {
				return s < 10 ? '0' + s : s;
			},
			// 涓嬭浇
			download(file ){
				window.open(`${file}`)
			},
			// 鍒濆鍖?
			init(type) {
				this.type = type;
				if(type=='cross'){
					var obj = JSON.parse(localStorage.getItem('crossObj'));
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
							this.ruleForm.guanshangyutupian = obj[o]?obj[o].split(",")[0]:'';
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
				}else if(type=='edit'){
					this.info()
				}
				// 鑾峰彇鐢ㄦ埛淇℃伅
				this.$http.get(this.userTableName + '/session', {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						var json = res.data.data;
					}
				});
				this.$http.get('option/pinzhongfenlei/pinzhongfenlei', {}).then(res => {
					if (res.data.code == 0) {
						this.pinzhongfenleiOptions = res.data.data;
					}
				});
				this.siyangnanduOptions = "绠€鍗?涓瓑,鍥伴毦".split(',')

				if (localStorage.getItem('raffleType') && localStorage.getItem('raffleType') != null) {
					localStorage.removeItem('raffleType')
					setTimeout(() => {
						this.onSubmit(null)
					}, 300)
				}
			},

			// 澶氱骇鑱斿姩鍙傛暟
			// 澶氱骇鑱斿姩鍙傛暟
			async info() {
				await this.$http.get(`guanshangyubaike/detail/${this.$route.query.id}`, {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						this.ruleForm = res.data.data;
					}
				});
			},
			// 鎻愪氦
			async onSubmit(subMitType=null) {
				await this.$refs["ruleForm"].validate(async valid => {
					if(valid) {
						if(!this.ruleForm.id) {
							delete this.ruleForm.userid
						}
						if(this.type=='cross'){
							var statusColumnName = localStorage.getItem('statusColumnName');
							var statusColumnValue = localStorage.getItem('statusColumnValue');
							if(statusColumnName && statusColumnName!='') {
								var obj = JSON.parse(localStorage.getItem('crossObj'));
								if(!statusColumnName.startsWith("[")) {
									for (var o in obj){
										if(o==statusColumnName){
											obj[o] = statusColumnValue;
										}
									}
									var table = localStorage.getItem('crossTable');
									await this.$http.post(table+'/update', obj).then(res => {});
								}
							}
						}

						await this.$http.post(`guanshangyubaike/${this.ruleForm.id?'update':this.centerType?'save':'add'}`, this.ruleForm).then(async res => {
							if (res.data.code == 0) {
								await this.$message({
									message: '鎿嶄綔鎴愬姛',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.$router.go(-1);
										
									}
								});
							} else {
								this.$message({
									message: res.data.msg,
									type: 'error',
									duration: 1500
								});
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
				this.$router.go(-1);
			},
			guanshangyutupianUploadChange(fileUrls) {
				this.ruleForm.guanshangyutupian = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
			},
		}
	};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.add-update-preview {
		padding: 20px 10% 40px;
		margin: 10px auto;
		background: none;
		width: 100%;
		position: relative;
		.add-update-form {
			border: 0px solid #eee;
			border-radius: 10px;
			padding: 40px 10% 20px 10%;
			background: none;
			width: 100%;
			position: relative;
			.add-item.el-form-item {
				border: 1px solid #46AC2E;
				padding: 0;
				margin: 0 0 24px;
				background: none;
				::v-deep .el-form-item__label {
					padding: 0 10px 0 0;
					color: #333;
					white-space: nowrap;
					font-weight: 500;
					width: 200px;
					font-size: 16px;
					line-height: 40px;
					text-align: right;
				}
				::v-deep .el-form-item__content {
					margin-left: 200px;
				}
				.el-input {
					width: 100%;
				}
				.el-input ::v-deep .el-input__inner {
					border: 0 solid #000;
					border-radius: 0px;
					padding: 0 12px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-input ::v-deep .el-input__inner[readonly="readonly"] {
					border: 0;
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
				.el-input-number ::v-deep .el-input__inner {
					text-align: left;
					border: 0 solid #000;
					border-radius: 0px;
					padding: 0 12px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-input-number ::v-deep .is-disabled .el-input__inner {
					text-align: left;
					border: 0;
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
				.el-input-number ::v-deep .el-input-number__decrease {
					display: none;
				}
				.el-input-number ::v-deep .el-input-number__increase {
					display: none;
				}
				.el-select {
					width: 100%;
				}
				.el-select ::v-deep .el-input__inner {
					border: 0 solid #000;
					border-radius: 0px;
					padding: 0 10px;
					box-shadow: none;
					outline: none;
					color: rgba(64, 158, 255, 1);
					width: 100%;
					font-size: 14px;
					height: 40px;
				}
				.el-select ::v-deep .is-disabled .el-input__inner {
					border: 0;
					cursor: not-allowed;
					border-radius: 4px;
					padding: 0 10px;
					box-shadow: none;
					outline: none;
					color: #999;
					background: #eee;
					width: 100%;
					font-size: 14px;
					height: 40px;
				}
				.el-date-editor {
					width: 100%;
				}
				.el-date-editor ::v-deep .el-input__inner {
					border: 0 solid #000;
					border-radius: 0px;
					padding: 0 10px 0 30px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				.el-date-editor ::v-deep .el-input__inner[readonly="readonly"] {
					border: 0;
					cursor: not-allowed;
					border-radius: 4px;
					padding: 0 10px 0 30px;
					box-shadow: none;
					outline: none;
					color: #999;
					background: #eee;
					width: 100%;
					font-size: 16px;
					height: 40px;
				}
				::v-deep .el-upload--picture-card {
					background: transparent;
					border: 0;
					border-radius: 0;
					width: auto;
					height: auto;
					line-height: initial;
					vertical-align: middle;
				}
				::v-deep .upload .upload-img {
					border: 1px solid #eee;
					cursor: pointer;
					border-radius: 0px;
					color: #999;
					width: 80px;
					font-size: 26px;
					line-height: 80px;
					text-align: center;
					height: 80px;
				}
				::v-deep .el-upload-list .el-upload-list__item {
					border: 1px solid #eee;
					cursor: pointer;
					border-radius: 0px;
					color: #999;
					width: 80px;
					font-size: 26px;
					line-height: 80px;
					text-align: center;
					height: 80px;
					font-size: 14px;
					line-height: 1.8;
				}
				::v-deep .el-upload .el-icon-plus {
					border: 1px solid #eee;
					cursor: pointer;
					border-radius: 0px;
					color: #999;
					width: 80px;
					font-size: 26px;
					line-height: 80px;
					text-align: center;
					height: 80px;
				}
				::v-deep .el-upload__tip {
					color: #666;
					font-size: 16px;
				}
				.el-textarea ::v-deep .el-textarea__inner {
					border: 0 solid #000;
					border-radius: 0px;
					padding: 12px;
					box-shadow: none;
					outline: none;
					color: #666;
					width: 100%;
					font-size: 16px;
					height: auto;
				}
				.el-textarea ::v-deep .el-textarea__inner[readonly="readonly"] {
					border: 0;
					cursor: not-allowed;
					border-radius: 4px;
					padding: 12px;
					box-shadow: none;
					outline: none;
					color: #999;
					width: 100%;
					font-size: 16px;
					height: auto;
				}
				::v-deep .el-input__inner::placeholder {
					color: #123;
					font-size: 16px;
				}
				::v-deep textarea::placeholder {
					color: #123;
					font-size: 16px;
				}
				.editor {
					background-color: #fff;
					border-radius: 0;
					padding: 0;
					box-shadow: none;
					margin: 0;
					width: 100%;
					border-color: #ccc;
					border-width: 0;
					border-style: solid;
					height: auto;
				}
				.editor ::v-deep.ql-toolbar {
					border: 1px solid #eee;
					background: none;
					border-width: 1px 1px 0;
				}
				.editor ::v-deep.ql-container {
					border: 1px solid #eee;
					background: none;
					min-height: 180px;
				}
				.editor ::v-deep.ql-container .ql-blank::before {
					color: #999;
				}
				.upload-img {
					object-fit: cover;
					width: 120px;
					height: 120px;
				}
				.viewBtn {
					border: 0;
					cursor: pointer;
					padding: 0 20px;
					margin: 0;
					color: #fff;
					display: inline-block;
					font-size: 14px;
					line-height: 34px;
					border-radius: 4px;
					outline: none;
					background: #46AC2E;
					width: auto;
					height: 34px;
				}
				.viewBtn:hover {
					opacity: 0.7;
				}
				.unviewBtn {
					border: 0;
					cursor: pointer;
					padding: 0 20px;
					margin: 0;
					color: #666;
					display: inline-block;
					font-size: 14px;
					line-height: 34px;
					border-radius: 4px;
					outline: none;
					background: #ddd;
					width: auto;
					height: 34px;
				}
				.unviewBtn:hover {
					opacity: 0.8;
				}
			}
			.add-btn-item {
				padding: 0;
				margin: 20px auto;
				width: 100%;
				text-align: center;
				.submitBtn {
					border: 1px solid #46AC2E;
					cursor: pointer;
					border-radius: 20px;
					padding: 0 15px;
					margin: 0 20px 0 0;
					outline: none;
					background: #46AC2E;
					display: inline-block;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
					.icon {
						color: #fff;
					}
					.text {
						color: #fff;
						font-size: 16px;
					}
				}
				.submitBtn:hover {
					opacity: 0.7;
					.icon {
						color: #000;
					}
					.text {
						color: #000;
					}
				}
				.closeBtn {
					border: 1px solid #000;
					cursor: pointer;
					border-radius: 20px;
					padding: 0 15px;
					margin: 0 20px 0 0;
					outline: none;
					background: none;
					display: inline-block;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
					.icon {
						color: #000;
						font-size: 18px;
					}
					.text {
						color: #000;
						font-size: 16px;
					}
				}
				.closeBtn:hover {
					opacity: 0.7;
					.icon {
					}
					.text {
					}
				}
			}
		}
	}
	.el-date-editor.el-input {
		width: auto;
	}
</style>

