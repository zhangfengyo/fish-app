
















<template>
	<div class="add-update-preview">
		<el-form
			class="add-update-form"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="200px"
			>
			<el-form-item class="add-item" label="鏁欏鏍囬" prop="jiaoxuebiaoti">
				<el-input v-model="ruleForm.jiaoxuebiaoti" 
					placeholder="鏁欏鏍囬" clearable :readonly="ro.jiaoxuebiaoti"></el-input>
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
			<el-form-item class="add-item" label="灏侀潰" v-if="type!='cross' || (type=='cross' && !ro.fengmian)" prop="fengmian">
				<file-upload
					tip="鐐瑰嚮涓婁紶灏侀潰"
					action="file/upload"
					:limit="3"
					:multiple="true"
					:disabled="ro.fengmian"
					:fileUrls="ruleForm.fengmian?ruleForm.fengmian:''"
					@change="fengmianUploadChange"
					></file-upload>
			</el-form-item>
			<el-form-item class="add-item" v-else label="灏侀潰" prop="fengmian">
				<img v-if="ruleForm.fengmian.substring(0,4)=='http'" class="upload-img" v-bind:key="index" :src="ruleForm.fengmian.split(',')[0]">
				<img v-else class="upload-img" v-bind:key="index" v-for="(item,index) in ruleForm.fengmian.split(',')" :src="baseUrl+item">
			</el-form-item>
			<el-form-item class="add-item" label="鏁欏瑙嗛" v-if="type!='cross' || (type=='cross' && !ro.jiaoxueshipin)" prop="jiaoxueshipin">
				<file-upload
					tip="鐐瑰嚮涓婁紶鏁欏瑙嗛"
					action="file/upload"
					:limit="1"
					:type="2"
					:multiple="true"
					:disabled="ro.jiaoxueshipin"
					:fileUrls="ruleForm.jiaoxueshipin?ruleForm.jiaoxueshipin:''"
					@change="jiaoxueshipinUploadChange"
					></file-upload>
			</el-form-item>
			<el-form-item class="add-item" v-else label="鏁欏瑙嗛" prop="jiaoxueshipin">
				<div class="viewBtn" v-if="ruleForm.jiaoxueshipin" @click="download(baseUrl + ruleForm.jiaoxueshipin )" type="success">棰勮</div>
				<div class="unviewBtn" v-else disabled>鏆傛棤</div>
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
			<el-form-item class="add-item" label="楸肩梾娌荤枟" prop="yubingzhiliao">
				<el-input
					type="textarea"
					:rows="8"
					:disabled="ro.yubingzhiliao"
					placeholder="楸肩梾娌荤枟"
					v-model="ruleForm.yubingzhiliao">
					</el-input>
			</el-form-item>
			<el-form-item class="add-item" label="鏁欏璇︽儏" prop="jiaoxuexiangqing">
				<editor 
					v-model="ruleForm.jiaoxuexiangqing" 
					class="editor" 
					myQuillEditor="jiaoxuexiangqing"
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
					jiaoxuebiaoti : false,
					pinzhongfenlei : false,
					fengmian : false,
					jiaoxueshipin : false,
					siyangfangfa : false,
					yubingzhiliao : false,
					jiaoxuexiangqing : false,
					storeupnum : false,
				},
				type: '',
				userTableName: localStorage.getItem('UserTableName'),
				ruleForm: {
					jiaoxuebiaoti: '',
					pinzhongfenlei: '',
					fengmian: '',
					jiaoxueshipin: '',
					siyangfangfa: '',
					yubingzhiliao: '',
					jiaoxuexiangqing: '',
					storeupnum: '',
				},
				pinzhongfenleiOptions: [],

				rules: {
					jiaoxuebiaoti: [
					],
					pinzhongfenlei: [
					],
					fengmian: [
					],
					jiaoxueshipin: [
					],
					siyangfangfa: [
					],
					yubingzhiliao: [
					],
					jiaoxuexiangqing: [
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
						if(o=='jiaoxuebiaoti'){
							this.ruleForm.jiaoxuebiaoti = obj[o];
							this.ro.jiaoxuebiaoti = true;
							continue;
						}
						if(o=='pinzhongfenlei'){
							this.ruleForm.pinzhongfenlei = obj[o];
							this.ro.pinzhongfenlei = true;
							continue;
						}
						if(o=='fengmian'){
							this.ruleForm.fengmian = obj[o]?obj[o].split(",")[0]:'';
							this.ro.fengmian = true;
							continue;
						}
						if(o=='jiaoxueshipin'){
							this.ruleForm.jiaoxueshipin = obj[o];
							this.ro.jiaoxueshipin = true;
							continue;
						}
						if(o=='siyangfangfa'){
							this.ruleForm.siyangfangfa = obj[o];
							this.ro.siyangfangfa = true;
							continue;
						}
						if(o=='yubingzhiliao'){
							this.ruleForm.yubingzhiliao = obj[o];
							this.ro.yubingzhiliao = true;
							continue;
						}
						if(o=='jiaoxuexiangqing'){
							this.ruleForm.jiaoxuexiangqing = obj[o];
							this.ro.jiaoxuexiangqing = true;
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
				await this.$http.get(`siyangjiaoxue/detail/${this.$route.query.id}`, {emulateJSON: true}).then(res => {
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

						await this.$http.post(`siyangjiaoxue/${this.ruleForm.id?'update':this.centerType?'save':'add'}`, this.ruleForm).then(async res => {
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
			fengmianUploadChange(fileUrls) {
				this.ruleForm.fengmian = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
			},
			jiaoxueshipinUploadChange(fileUrls) {
				this.ruleForm.jiaoxueshipin = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
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

