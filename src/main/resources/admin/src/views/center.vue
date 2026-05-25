<template>
	<div :style='{"width":"100%","padding":"20px","fontSize":"14px","color":"#666","height":"auto"}'>
		<el-form
			:style='{"border":"0px solid #e5e7eb","padding":"50px 20% 30px 10%","boxShadow":"none","borderRadius":"10px","flexWrap":"wrap","background":"#fff","display":"flex"}'
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			label-width="180px"
		>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="鐢ㄦ埛璐﹀彿" prop="yonghuzhanghao">
					<el-input v-model="ruleForm.yonghuzhanghao" :readonly="ro.yonghuzhanghao" placeholder="鐢ㄦ埛璐﹀彿" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="鐢ㄦ埛濮撳悕" prop="yonghuxingming">
					<el-input v-model="ruleForm.yonghuxingming" :readonly="ro.yonghuxingming" placeholder="鐢ㄦ埛濮撳悕" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='yonghu'"  label="鎬у埆" prop="xingbie">
					<el-select filterable v-model="ruleForm.xingbie" :disabled="ro.xingbie" placeholder="璇烽€夋嫨鎬у埆">
						<el-option
							v-for="(item,index) in yonghuxingbieOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="韬唤璇? prop="idcard">
					<el-input @input="idcardChange" v-model="ruleForm.idcard" :readonly="ro.idcard" placeholder="韬唤璇? clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="骞撮緞" prop="age">
					<el-input v-model="ruleForm.age" :readonly="ro.age||true" placeholder="骞撮緞" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="鎵嬫満鍙? prop="shoujihao">
					<el-input v-model="ruleForm.shoujihao" :readonly="ro.shoujihao" placeholder="鎵嬫満鍙? clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='yonghu'" label="澶村儚" prop="touxiang">
					<file-upload
						tip="鐐瑰嚮涓婁紶澶村儚"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
						@change="yonghutouxiangUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="閭" prop="email">
					<el-input v-model="ruleForm.email" :readonly="ro.email" placeholder="閭" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='users'" label="鐢ㄦ埛鍚? prop="username">
					<el-input v-model="ruleForm.username" placeholder="鐢ㄦ埛鍚?></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='users'" label="澶村儚" prop="image">
					<file-upload
						tip="鐐瑰嚮涓婁紶澶村儚"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.image?ruleForm.image:''"
						@change="usersimageUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"100%","padding":"0","margin":"10px auto 0","justifyContent":"center","display":"flex"}'>
					<el-button class="btn3" type="primary" @click="onUpdateHandler">
						<span class="icon iconfont icon-queren20"></span>
						鎻愪氦
					</el-button>
				</el-form-item>
		</el-form>
	</div>
</template>
<script>
// 鏍￠獙寮曞叆
	import { 
		isNumber,
		isIntNumer,
		isEmail,
		isMobile,
		checkIdCard,
	} from "@/utils/validate";

	export default {
		data() {
			return {
				ruleForm: {},
				ro: {},
				flag: '',
				usersFlag: false,
				yonghuxingbieOptions: [],
			};
		},
		mounted() {
			var table = this.$storage.get("sessionTable");
			this.flag = table;
			this.$http({
				url: `${this.$storage.get("sessionTable")}/session`,
				method: "get"
			}).then(({ data }) => {
				if (data && data.code === 0) {
					if(table == 'yonghu') {
						this.ro = {
							yonghuzhanghao: false,
							mima: false,
							yonghuxingming: false,
							xingbie: false,
							idcard: false,
							age: false,
							shoujihao: false,
							touxiang: false,
							email: false,
							money: false,
						}
					}

					this.ruleForm = data.data;
				} else {
					this.$message.error(data.msg);
				}
			});
			this.yonghuxingbieOptions = "鐢?濂?.split(',')
		},
		methods: {
			yonghutouxiangUploadChange(fileUrls) {
				this.ruleForm.touxiang = fileUrls;
			},
			usersimageUploadChange(fileUrls) {
				this.ruleForm.image = fileUrls;
			},
			onUpdateHandler() {
				if((!this.ruleForm.yonghuzhanghao)&& 'yonghu'==this.flag){
					this.$message.error('鐢ㄦ埛璐﹀彿涓嶈兘涓虹┖');
					return
				}
				if((!this.ruleForm.mima)&& 'yonghu'==this.flag){
					this.$message.error('瀵嗙爜涓嶈兘涓虹┖');
					return
				}
				if((!this.ruleForm.yonghuxingming)&& 'yonghu'==this.flag){
					this.$message.error('鐢ㄦ埛濮撳悕涓嶈兘涓虹┖');
					return
				}
				if('yonghu' ==this.flag && this.ruleForm.idcard&&(!checkIdCard(this.ruleForm.idcard))){
					this.$message.error(`韬唤璇佸簲杈撳叆韬唤璇佹牸寮廯);
					return
				}
				if('yonghu' ==this.flag && this.ruleForm.age&&(!isIntNumer(this.ruleForm.age))){
					this.$message.error(`骞撮緞搴旇緭鍏ユ暣鏁癭);
					return
				}
				if('yonghu' ==this.flag && this.ruleForm.shoujihao&&(!isMobile(this.ruleForm.shoujihao))){
					this.$message.error(`鎵嬫満鍙峰簲杈撳叆鎵嬫満鏍煎紡`);
					return
				}
				if(this.ruleForm.touxiang!=null) {
					this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
				}
				if('yonghu' ==this.flag && this.ruleForm.email&&(!isEmail(this.ruleForm.email))){
					this.$message.error(`閭搴旇緭鍏ラ偖绠辨牸寮廯);
					return
				}
				if('yonghu' ==this.flag && this.ruleForm.money&&(!isNumber(this.ruleForm.money))){
					this.$message.error(`浣欓搴旇緭鍏ユ暟瀛梎);
					return
				}
				if('users'==this.flag && this.ruleForm.username.trim().length<1) {
					this.$message.error(`鐢ㄦ埛鍚嶄笉鑳戒负绌篳);
					return	
				}
				if(this.flag=='users'){
					this.ruleForm.image = this.ruleForm.image.replace(new RegExp(this.$base.url,"g"),"")
				}
				this.$http({
					url: `${this.$storage.get("sessionTable")}/update`,
					method: "post",
					data: this.ruleForm
				}).then(({ data }) => {
					if (data && data.code === 0) {
						if(this.flag=='users'){
							this.$storage.set('headportrait',this.ruleForm.image)
						}else {
							if(this.flag == 'yonghu') {
								this.$storage.set('headportrait',this.ruleForm.touxiang)
							}
						}
						this.$message({
							message: "淇敼淇℃伅鎴愬姛",
							type: "success",
							duration: 1500,
							onClose: () => {
								window.location.reload();
							}
						});
					} else {
						this.$message.error(data.msg);
					}
				});
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
	.el-date-editor.el-input {
		width: auto;
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
	
	.add-update-preview ::v-deep .el-upload--picture-card {
		background: transparent;
		border: 0;
		border-radius: 0;
		width: auto;
		height: auto;
		line-height: initial;
		vertical-align: middle;
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
	
	.add-update-preview .btn3 {
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
	
	.add-update-preview .btn3:hover {
				opacity: 0.8;
			}
	.editor>.avatar-uploader {
		line-height: 0;
		height: 0;
	}
</style>

