<template>
	<div :style='{"padding":"10px"}'>
		<el-form
			v-if="showType==1"
			:style='{"border":"0px solid #e5e7eb","padding":"50px 20% 30px 10%","boxShadow":"none","borderRadius":"10px","flexWrap":"wrap","background":"#fff","display":"flex"}'
			class="add-update-preview"
			ref="ruleForm"
			:rules="rules"
			:model="ruleForm"
			label-width="180px"
		>
			<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' label="鍘熷瘑鐮? prop="password">
				<el-input type="password" v-model="ruleForm.password" ></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' label="鏂板瘑鐮? prop="newpassword">
				<el-input type="password" v-model="ruleForm.newpassword" ></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' label="纭瀵嗙爜" prop="repassword">
				<el-input type="password" v-model="ruleForm.repassword" ></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"100%","padding":"0","margin":"10px auto 0","justifyContent":"center","display":"flex"}'>
				<el-button class="btn3" :style='{"border":"0","cursor":"pointer","padding":"0 24px","margin":"4px","outline":"none","color":"#fff","borderRadius":"4px","background":"#2dc7a3","width":"auto","fontSize":"16px","minWidth":"110px","height":"40px"}' type="primary" @click="onUpdateHandler">
					<span class="icon iconfont icon-queren20" :style='{"margin":"0 2px","fontSize":"16px","color":"#fff","height":"40px"}'></span>
					鎻愪氦
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
export default {
	data() {
		return {
			dialogVisible: false,
			ruleForm: {},
			payForm: {},
			user: {},
			rules: {
				password: [
					{
						required: true,
						message: "瀵嗙爜涓嶈兘涓虹┖",
						trigger: "blur"
					}
				],
				newpassword: [
					{
						required: true,
						message: "鏂板瘑鐮佷笉鑳戒负绌?,
						trigger: "blur"
					}
				],
				repassword: [
					{
						required: true,
						message: "纭瀵嗙爜涓嶈兘涓虹┖",
						trigger: "blur"
					}
				]
			},
			rules2: {
				password: [
					{
						required: true,
						message: "鏀粯瀵嗙爜涓嶈兘涓虹┖",
						trigger: "blur"
					}
				],
				newpassword: [
					{
						required: true,
						message: "鏂版敮浠樺瘑鐮佷笉鑳戒负绌?,
						trigger: "blur"
					}
				],
				repassword: [
					{
						required: true,
						message: "纭鏀粯瀵嗙爜涓嶈兘涓虹┖",
						trigger: "blur"
					}
				]
			},
			showType: '1'
		};
	},
	mounted() {
		this.$http({
			url: `${this.$storage.get("sessionTable")}/session`,
			method: "get"
		}).then(({ data }) => {
			if (data && data.code === 0) {
				this.user = data.data;
			} else {
				this.$message.error(data.msg);
			}
		});
	},
	methods: {
		onLogout() {
			this.$storage.remove("Token");
			this.$router.replace({ name: "login" });
		},
		// 淇敼瀵嗙爜
		async onUpdateHandler() {
			this.$refs["ruleForm"].validate(async valid => {
				if (valid) {
					var password = "";
					if (this.user.mima) {
						password = this.user.mima;
					} else if (this.user.password) {
						password = this.user.password;
					}
					if(this.$storage.get("sessionTable")=='users'){
						if (this.ruleForm.password != password) {
							this.$message.error("鍘熷瘑鐮侀敊璇?);
							return;
						}
						if (this.ruleForm.newpassword != this.ruleForm.repassword) {
							this.$message.error("涓ゆ瀵嗙爜杈撳叆涓嶄竴鑷?);
							return;
						}
						this.user.password = this.ruleForm.newpassword;
						this.user.mima = this.ruleForm.newpassword;
						this.$http({
							url: `${this.$storage.get("sessionTable")}/update`,
							method: "post",
							data: this.user
						}).then(({ data }) => {
							if (data && data.code === 0) {
								this.$message({
									message: "淇敼瀵嗙爜鎴愬姛,涓嬫鐧诲綍绯荤粺鐢熸晥",
									type: "success",
									duration: 1500,
									onClose: () => {
									}
								});
							} else {
								this.$message.error(data.msg);
							}
						});
						return false
					}
					if (this.ruleForm.password != password) {
						this.$message.error("鍘熷瘑鐮侀敊璇?);
						return;
					}
					if (this.ruleForm.newpassword != this.ruleForm.repassword) {
						this.$message.error("涓ゆ瀵嗙爜杈撳叆涓嶄竴鑷?);
						return;
					}
					if (this.ruleForm.newpassword == this.ruleForm.password) {
						this.$message.error("鏂板瘑鐮佷笌鍘熷瘑鐮佺浉鍚?);
						return;
					}
					this.user.password = this.ruleForm.newpassword;
					this.user.mima = this.ruleForm.newpassword;
					this.$http({
						url: `${this.$storage.get("sessionTable")}/update`,
						method: "post",
						data: this.user
					}).then(({ data }) => {
						if (data && data.code === 0) {
							this.$message({
								message: "淇敼瀵嗙爜鎴愬姛,涓嬫鐧诲綍绯荤粺鐢熸晥",
								type: "success",
								duration: 1500,
								onClose: () => {
								}
							});
						} else {
							this.$message.error(data.msg);
						}
					});
				}
			});
		}
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
			}
	
	.add-update-preview .btn3:hover {
				opacity: 0.8;
			}
</style>

