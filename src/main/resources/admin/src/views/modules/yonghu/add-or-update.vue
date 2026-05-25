




















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
				<el-form-item class="input" v-if="type!='info'"  label="鐢ㄦ埛璐﹀彿" prop="yonghuzhanghao" >
					<el-input v-model="ruleForm.yonghuzhanghao" placeholder="鐢ㄦ埛璐﹀彿" clearable  :readonly="ro.yonghuzhanghao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鐢ㄦ埛璐﹀彿" prop="yonghuzhanghao" >
					<el-input v-model="ruleForm.yonghuzhanghao" placeholder="鐢ㄦ埛璐﹀彿" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="瀵嗙爜" prop="mima" >
					<el-input v-model="ruleForm.mima" placeholder="瀵嗙爜" clearable  :readonly="ro.mima"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="瀵嗙爜" prop="mima" >
					<el-input v-model="ruleForm.mima" placeholder="瀵嗙爜" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鐢ㄦ埛濮撳悕" prop="yonghuxingming" >
					<el-input v-model="ruleForm.yonghuxingming" placeholder="鐢ㄦ埛濮撳悕" clearable  :readonly="ro.yonghuxingming"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鐢ㄦ埛濮撳悕" prop="yonghuxingming" >
					<el-input v-model="ruleForm.yonghuxingming" placeholder="鐢ㄦ埛濮撳悕" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="鎬у埆" prop="xingbie" >
					<el-select :disabled="ro.xingbie" v-model="ruleForm.xingbie" placeholder="璇烽€夋嫨鎬у埆"  filterable>
						<el-option
							v-for="(item,index) in xingbieOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="鎬у埆" prop="xingbie" >
					<el-input v-model="ruleForm.xingbie"
						placeholder="鎬у埆" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="韬唤璇? prop="idcard" >
					<el-input @input="idcardChange" v-model="ruleForm.idcard" placeholder="韬唤璇? clearable :readonly="ro.idcard"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="韬唤璇? prop="idcard" >
					<el-input v-model="ruleForm.idcard" placeholder="韬唤璇? readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="骞撮緞" prop="age" >
					<el-input v-model="ruleForm.age" placeholder="骞撮緞" clearable :readonly="ro.age ||true"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="骞撮緞" prop="age" >
					<el-input v-model="ruleForm.age" placeholder="骞撮緞" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鎵嬫満鍙? prop="shoujihao" >
					<el-input v-model="ruleForm.shoujihao" placeholder="鎵嬫満鍙? clearable  :readonly="ro.shoujihao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鎵嬫満鍙? prop="shoujihao" >
					<el-input v-model="ruleForm.shoujihao" placeholder="鎵嬫満鍙? readonly></el-input>
				</el-form-item>
				<el-form-item class="upload" v-if="type!='info' && !ro.touxiang" label="澶村儚" prop="touxiang" >
					<file-upload
						tip="鐐瑰嚮涓婁紶澶村儚"
						action="file/upload"
						:limit="3"
						:disabled="ro.touxiang"
						:multiple="true"
						:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
						@change="touxiangUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item class="upload" v-else-if="ruleForm.touxiang" label="澶村儚" prop="touxiang" >
					<img v-if="ruleForm.touxiang.substring(0,4)=='http'&&ruleForm.touxiang.split(',w').length>1" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.touxiang" width="100" height="100" @click="imgPreView(ruleForm.tupian)">
					<img v-else-if="ruleForm.touxiang.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.touxiang.split(',')[0]" width="100" height="100" @click="imgPreView(ruleForm.tupian.split(',')[0])">
					<img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.touxiang.split(',')" :src="$base.url+item" width="100" height="100" @click="imgPreView($base.url+item)">
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="閭" prop="email" >
					<el-input v-model="ruleForm.email" placeholder="閭" clearable  :readonly="ro.email"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="閭" prop="email" >
					<el-input v-model="ruleForm.email" placeholder="閭" readonly></el-input>
				</el-form-item>
			</template>
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
		isNumber,
		isIntNumer,
		isEmail,
		isMobile,
		checkIdCard,
	} from "@/utils/validate";
	export default {
		data() {
			var validateIdCard = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!checkIdCard(value)) {
					callback(new Error("璇疯緭鍏ユ纭殑韬唤璇佸彿鐮?));
				} else {
					callback();
				}
			};
			var validateMobile = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isMobile(value)) {
					callback(new Error("璇疯緭鍏ユ纭殑鎵嬫満鍙风爜"));
				} else {
					callback();
				}
			};
			var validateEmail = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isEmail(value)) {
					callback(new Error("璇疯緭鍏ユ纭殑閭鍦板潃"));
				} else {
					callback();
				}
			};
			var validateNumber = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isNumber(value)) {
					callback(new Error("璇疯緭鍏ユ暟瀛?));
				} else {
					callback();
				}
			};
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
					yonghuzhanghao : false,
					mima : false,
					yonghuxingming : false,
					xingbie : false,
					idcard : false,
					age : false,
					shoujihao : false,
					touxiang : false,
					email : false,
					money : false,
				},
			
				ruleForm: {
					yonghuzhanghao: '',
					mima: '',
					yonghuxingming: '',
					xingbie: '',
					idcard: '',
					age: '',
					shoujihao: '',
					touxiang: '',
					email: '',
					money: '0',
				},
				xingbieOptions: [],

				rules: {
					yonghuzhanghao: [
						{ required: true, message: '鐢ㄦ埛璐﹀彿涓嶈兘涓虹┖', trigger: 'blur' },
					],
					mima: [
						{ required: true, message: '瀵嗙爜涓嶈兘涓虹┖', trigger: 'blur' },
					],
					yonghuxingming: [
						{ required: true, message: '鐢ㄦ埛濮撳悕涓嶈兘涓虹┖', trigger: 'blur' },
					],
					xingbie: [
					],
					idcard: [
						{ validator: validateIdCard, trigger: 'blur' },
					],
					age: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					shoujihao: [
						{ validator: validateMobile, trigger: 'blur' },
					],
					touxiang: [
					],
					email: [
						{ validator: validateEmail, trigger: 'blur' },
					],
					money: [
						{ validator: validateNumber, trigger: 'blur' },
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
						if(o=='yonghuzhanghao'){
							this.ruleForm.yonghuzhanghao = obj[o];
							this.ro.yonghuzhanghao = true;
							continue;
						}
						if(o=='mima'){
							this.ruleForm.mima = obj[o];
							this.ro.mima = true;
							continue;
						}
						if(o=='yonghuxingming'){
							this.ruleForm.yonghuxingming = obj[o];
							this.ro.yonghuxingming = true;
							continue;
						}
						if(o=='xingbie'){
							this.ruleForm.xingbie = obj[o];
							this.ro.xingbie = true;
							continue;
						}
						if(o=='idcard'){
							this.ruleForm.idcard = obj[o];
							this.ro.idcard = true;
							continue;
						}
						if(o=='age'){
							this.ruleForm.age = obj[o];
							this.ro.age = true;
							continue;
						}
						if(o=='shoujihao'){
							this.ruleForm.shoujihao = obj[o];
							this.ro.shoujihao = true;
							continue;
						}
						if(o=='touxiang'){
							this.ruleForm.touxiang = obj[o];
							this.ro.touxiang = true;
							continue;
						}
						if(o=='email'){
							this.ruleForm.email = obj[o];
							this.ro.email = true;
							continue;
						}
						if(o=='money'){
							this.ruleForm.money = obj[o];
							this.ro.money = true;
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
				this.xingbieOptions = "鐢?濂?.split(',')
			
			},
			// 澶氱骇鑱斿姩鍙傛暟

			async info(id) {
				await this.$http({
					url: `yonghu/info/${id}`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.ruleForm = data.data;
						//瑙ｅ喅鍓嶅彴涓婁紶鍥剧墖鍚庡彴涓嶆樉绀虹殑闂
						let reg=new RegExp('../../../upload','g')//g浠ｈ〃鍏ㄩ儴
					} else {
						this.$message.error(data.msg);
					}
				});
			},

			// 鎻愪氦
			async onSubmit() {
					await this.$refs["ruleForm"].validate(async valid => {
						if (valid) {
							if(this.ruleForm.touxiang!=null) {
								this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
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
								url: `yonghu/${!this.ruleForm.id ? "save" : "update"}`,
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
											this.parent.yonghuCrossAddOrUpdateFlag = false;
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
				this.parent.yonghuCrossAddOrUpdateFlag = false;
			},
			touxiangUploadChange(fileUrls) {
				this.ruleForm.touxiang = fileUrls;
			},
			idcardChange(e) {
				if(checkIdCard(e)) {
					this.ruleForm.age = this.getAge(e)
				}else {
					this.ruleForm.age = ''
				}
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

