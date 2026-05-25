












































<template>
	<div class="addEdit-block">
		<el-form
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="180px"
		>
			<template v-show="logistics">
				<el-form-item class="input" v-if="type!='info'" label="璁㈠崟缂栧彿" prop="orderid" >
					<el-input v-model="ruleForm.orderid" placeholder="璁㈠崟缂栧彿" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.orderid" label="璁㈠崟缂栧彿" prop="orderid" >
					<el-input v-model="ruleForm.orderid" placeholder="璁㈠崟缂栧彿" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鍟嗗搧鍚嶇О" prop="goodname" >
					<el-input v-model="ruleForm.goodname" placeholder="鍟嗗搧鍚嶇О" clearable  :readonly="ro.goodname"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鍟嗗搧鍚嶇О" prop="goodname" >
					<el-input v-model="ruleForm.goodname" placeholder="鍟嗗搧鍚嶇О" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鍟嗗搧绫诲瀷" prop="goodtype" >
					<el-input v-model="ruleForm.goodtype" placeholder="鍟嗗搧绫诲瀷" clearable  :readonly="ro.goodtype"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鍟嗗搧绫诲瀷" prop="goodtype" >
					<el-input v-model="ruleForm.goodtype" placeholder="鍟嗗搧绫诲瀷" readonly></el-input>
				</el-form-item>
				<el-form-item class="upload" v-if="type!='info' && !ro.picture" label="鍥剧墖" prop="picture" >
					<file-upload
						tip="鐐瑰嚮涓婁紶鍥剧墖"
						action="file/upload"
						:limit="3"
						:disabled="ro.picture"
						:multiple="true"
						:fileUrls="ruleForm.picture?ruleForm.picture:''"
						@change="pictureUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item class="upload" v-else-if="ruleForm.picture" label="鍥剧墖" prop="picture" >
					<img v-if="ruleForm.picture.substring(0,4)=='http'&&ruleForm.picture.split(',w').length>1" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.picture" width="100" height="100" @click="imgPreView(ruleForm.tupian)">
					<img v-else-if="ruleForm.picture.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.picture.split(',')[0]" width="100" height="100" @click="imgPreView(ruleForm.tupian.split(',')[0])">
					<img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.picture.split(',')" :src="$base.url+item" width="100" height="100" @click="imgPreView($base.url+item)">
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="璐拱鏁伴噺" prop="buynumber" >
					<el-input v-model.number="ruleForm.buynumber" placeholder="璐拱鏁伴噺" clearable  :readonly="ro.buynumber"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="璐拱鏁伴噺" prop="buynumber" >
					<el-input v-model="ruleForm.buynumber" placeholder="璐拱鏁伴噺" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鍗曚环" prop="price" >
					<el-input-number v-model="ruleForm.price" placeholder="鍗曚环" :disabled="ro.price"></el-input-number>
				</el-form-item>
				<el-form-item v-else class="input" label="鍗曚环" prop="price" >
					<el-input v-model="ruleForm.price" placeholder="鍗曚环" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鎬讳环" prop="total" >
					<el-input-number v-model="ruleForm.total" placeholder="鎬讳环" :disabled="ro.total"></el-input-number>
				</el-form-item>
				<el-form-item v-else class="input" label="鎬讳环" prop="total" >
					<el-input v-model="ruleForm.total" placeholder="鎬讳环" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="鏀粯绫诲瀷" prop="type" >
					<el-select :disabled="ro.type" v-model.number="ruleForm.type" placeholder="璇烽€夋嫨鏀粯绫诲瀷"  filterable>
						<el-option
							v-for="(item,index) in typeOptions"
							v-bind:key="index"
							:label="item"
							:value="index+1">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="鏀粯绫诲瀷" prop="type" >
					<el-input v-model="ruleForm.type-0==1?'浣欓':'绉垎'"
						placeholder="鏀粯绫诲瀷" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="璁㈠崟鐘舵€? prop="status" >
					<el-input v-model="ruleForm.status" placeholder="璁㈠崟鐘舵€? clearable  :readonly="ro.status"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="璁㈠崟鐘舵€? prop="status" >
					<el-input v-model="ruleForm.status" placeholder="璁㈠崟鐘舵€? readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鍦板潃" prop="address" >
					<el-input v-model="ruleForm.address" placeholder="鍦板潃" clearable  :readonly="ro.address"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鍦板潃" prop="address" >
					<el-input v-model="ruleForm.address" placeholder="鍦板潃" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鐢佃瘽" prop="tel" >
					<el-input v-model="ruleForm.tel" placeholder="鐢佃瘽" clearable  :readonly="ro.tel"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鐢佃瘽" prop="tel" >
					<el-input v-model="ruleForm.tel" placeholder="鐢佃瘽" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鏀惰揣浜? prop="consignee" >
					<el-input v-model="ruleForm.consignee" placeholder="鏀惰揣浜? clearable  :readonly="ro.consignee"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鏀惰揣浜? prop="consignee" >
					<el-input v-model="ruleForm.consignee" placeholder="鏀惰揣浜? readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="澶囨敞" prop="remark" >
					<el-input v-model="ruleForm.remark" placeholder="澶囨敞" clearable  :readonly="ro.remark"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="澶囨敞" prop="remark" >
					<el-input v-model="ruleForm.remark" placeholder="澶囨敞" readonly></el-input>
				</el-form-item>
				<el-form-item class="date" v-if="type!='info'" label="鍒涘缓鏃堕棿" prop="addtime" >
					<el-date-picker
						value-format="yyyy-MM-dd HH:mm:ss"
						v-model="ruleForm.addtime" 
						type="datetime"
						:readonly="ro.addtime"
						placeholder="鍒涘缓鏃堕棿"
					></el-date-picker>
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.addtime" label="鍒涘缓鏃堕棿" prop="addtime" >
					<el-input v-model="ruleForm.addtime" placeholder="鍒涘缓鏃堕棿" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="鐢ㄦ埛瑙掕壊" prop="role" >
					<el-input v-model="ruleForm.role" placeholder="鐢ㄦ埛瑙掕壊" clearable  :readonly="ro.role"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="鐢ㄦ埛瑙掕壊" prop="role" >
					<el-input v-model="ruleForm.role" placeholder="鐢ㄦ埛瑙掕壊" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="閫€璐у師鍥? prop="returnreason" >
					<el-input v-model="ruleForm.returnreason" placeholder="閫€璐у師鍥? clearable  :readonly="ro.returnreason"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="閫€璐у師鍥? prop="returnreason" >
					<el-input v-model="ruleForm.returnreason" placeholder="閫€璐у師鍥? readonly></el-input>
				</el-form-item>
			</template>
			<el-form-item v-if="type=='info'" class="input" label="涓嬪崟鏃堕棿" prop="addtime">
				<el-input v-model="ruleForm.addtime" placeholder="涓嬪崟鏃堕棿" readonly></el-input>
			</el-form-item>
			<el-form-item v-if="type!='info'" class="editorBox" label="鐗╂祦" prop="logistics" >
				<editor 
					style="min-width: 200px; max-width: 600px;"
					v-model="ruleForm.logistics" 
					class="editor"
					myQuillEditor="logistics"
					action="file/upload">
				</editor>
			</el-form-item>
			<el-form-item v-else-if="ruleForm.logistics" label="鐗╂祦" prop="logistics"  class="textBox">
				<span class="text ql-snow ql-editor" v-html="ruleForm.logistics"></span>
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
		isNumber,
		isIntNumer,
	} from "@/utils/validate";
	export default {
		data() {
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
				logistics: true,
				id: '',
				type: '',
			
			
				ro:{
					orderid : false,
					goodid : false,
					goodname : false,
					goodtype : false,
					picture : false,
					buynumber : false,
					price : false,
					total : false,
					type : false,
					status : false,
					address : false,
					tel : false,
					consignee : false,
					remark : false,
					addtime : false,
					userid : false,
					tablename : false,
					logistics : false,
					sfsh : false,
					shhf : false,
					role : false,
					returnreason : false,
				},
			
				ruleForm: {
					orderid: this.getUUID(),
					goodid: '',
					goodname: '',
					goodtype: '',
					picture: '',
					buynumber: '',
					price: '',
					total: '',
					type: '',
					status: '',
					address: '',
					tel: '',
					consignee: '',
					remark: '',
					addtime: '',
					userid: '',
					tablename: 'xianshangshangcheng',
					logistics: '',
					sfsh: '',
					shhf: '',
					role: '',
					returnreason: '',
				},
				typeOptions: [],

				rules: {
					orderid: [
						{ required: true, message: '璁㈠崟缂栧彿涓嶈兘涓虹┖', trigger: 'blur' },
					],
					goodid: [
						{ required: true, message: '鍟嗗搧id涓嶈兘涓虹┖', trigger: 'blur' },
					],
					goodname: [
					],
					goodtype: [
					],
					picture: [
						{ required: true, message: '鍥剧墖涓嶈兘涓虹┖', trigger: 'blur' },
					],
					buynumber: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					price: [
						{ validator: validateNumber, trigger: 'blur' },
					],
					total: [
						{ validator: validateNumber, trigger: 'blur' },
					],
					type: [
						{ validator: validateIntNumber, trigger: 'blur' },
					],
					status: [
					],
					address: [
					],
					tel: [
					],
					consignee: [
					],
					remark: [
					],
					addtime: [
					],
					userid: [
					],
					tablename: [
					],
					logistics: [
					],
					sfsh: [
					],
					shhf: [
					],
					role: [
					],
					returnreason: [
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
						if(o=='orderid'){
							this.ruleForm.orderid = obj[o];
							this.ro.orderid = true;
							continue;
						}
						if(o=='goodid'){
							this.ruleForm.goodid = obj[o];
							this.ro.goodid = true;
							continue;
						}
						if(o=='goodname'){
							this.ruleForm.goodname = obj[o];
							this.ro.goodname = true;
							continue;
						}
						if(o=='goodtype'){
							this.ruleForm.goodtype = obj[o];
							this.ro.goodtype = true;
							continue;
						}
						if(o=='picture'){
							this.ruleForm.picture = obj[o];
							this.ro.picture = true;
							continue;
						}
						if(o=='buynumber'){
							this.ruleForm.buynumber = obj[o];
							this.ro.buynumber = true;
							continue;
						}
						if(o=='price'){
							this.ruleForm.price = obj[o];
							this.ro.price = true;
							continue;
						}
						if(o=='total'){
							this.ruleForm.total = obj[o];
							this.ro.total = true;
							continue;
						}
						if(o=='type'){
							this.ruleForm.type = obj[o];
							this.ro.type = true;
							continue;
						}
						if(o=='status'){
							this.ruleForm.status = obj[o];
							this.ro.status = true;
							continue;
						}
						if(o=='address'){
							this.ruleForm.address = obj[o];
							this.ro.address = true;
							continue;
						}
						if(o=='tel'){
							this.ruleForm.tel = obj[o];
							this.ro.tel = true;
							continue;
						}
						if(o=='consignee'){
							this.ruleForm.consignee = obj[o];
							this.ro.consignee = true;
							continue;
						}
						if(o=='remark'){
							this.ruleForm.remark = obj[o];
							this.ro.remark = true;
							continue;
						}
						if(o=='addtime'){
							this.ruleForm.addtime = obj[o];
							this.ro.addtime = true;
							continue;
						}
						if(o=='userid'){
							this.ruleForm.userid = obj[o];
							this.ro.userid = true;
							continue;
						}
						if(o=='tablename'){
							this.ruleForm.tablename = obj[o];
							this.ro.tablename = true;
							continue;
						}
						if(o=='logistics'){
							this.ruleForm.logistics = obj[o];
							this.ro.logistics = true;
							continue;
						}
						if(o=='role'){
							this.ruleForm.role = obj[o];
							this.ro.role = true;
							continue;
						}
						if(o=='returnreason'){
							this.ruleForm.returnreason = obj[o];
							this.ro.returnreason = true;
							continue;
						}
					}
				}

				this.typeOptions = "鐜伴噾,绉垎".split(',')
			
			},
			// 澶氱骇鑱斿姩鍙傛暟

			async info(id) {
				await this.$http({
					url: `orders/info/${id}`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.ruleForm = data.data;
						//瑙ｅ喅鍓嶅彴涓婁紶鍥剧墖鍚庡彴涓嶆樉绀虹殑闂
						let reg=new RegExp('../../../upload','g')//g浠ｈ〃鍏ㄩ儴
						this.ruleForm.logistics = this.ruleForm.logistics.replace(reg,'../../../guanshangl0g62fht/upload');
					} else {
						this.$message.error(data.msg);
					}
				});
			},

			// 鎻愪氦
			async onSubmit() {
					await this.$refs["ruleForm"].validate(async valid => {
						if (valid) {
							if(this.ruleForm.orderid) {
								this.ruleForm.orderid = String(this.ruleForm.orderid)
							}
							if(this.ruleForm.picture!=null) {
								this.ruleForm.picture = this.ruleForm.picture.replace(new RegExp(this.$base.url,"g"),"");
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
								url: `orders/${!this.ruleForm.id ? "save" : "update"}`,
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
											this.parent.ordersCrossAddOrUpdateFlag = false;
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
				this.parent.ordersCrossAddOrUpdateFlag = false;
			},
			pictureUploadChange(fileUrls) {
				this.ruleForm.picture = fileUrls;
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

