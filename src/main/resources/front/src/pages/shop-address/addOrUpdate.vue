<template>
	<div :style='{"width":"100%","padding":"20px 10% 40px","margin":"10px auto","position":"relative","background":"none"}'>
		<el-form class="add-update-preview" ref="form" :model="form" :rules="rules" label-width="200px">
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="鑱旂郴浜? prop="name">
				<el-input v-model="form.name" placeholder="鑱旂郴浜?></el-input>
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="鎵嬫満鍙风爜" prop="phone">
				<el-input v-model="form.phone" placeholder="鎵嬫満鍙风爜"></el-input>
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="榛樿鍦板潃">
				<el-radio-group v-model="form.isdefault">
					<el-radio label="鏄?></el-radio>
					<el-radio label="鍚?></el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="鍦板潃">
				<el-cascader v-model="value" :options="options" @change="handleChange" :props="{value:'label'}" />
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="璇︾粏鍦板潃">
				<el-input v-model="address" placeholder="璇︾粏鍦板潃"></el-input>
			</el-form-item>
			<el-form-item :style='{"width":"100%","padding":"0","margin":"20px auto","textAlign":"center"}'>
				<el-button class="submitBtn" type="primary" @click="onSubmit('form')">
					<span class="icon iconfont icon-xiugai17"></span>
					<span class="text">娣诲姞</span>
				</el-button>
				<el-button v-if="!isBuy" class="closeBtn" @click="goBack">
					<span class="icon iconfont icon-shanchu8"></span>
					<span class="text">鍙栨秷</span>
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	import { Loading } from 'element-ui';
	export default {
		//鏁版嵁闆嗗悎
		data() {
			return {
				form: {
					userid: Number(localStorage.getItem('frontUserid')),
					name: localStorage.getItem('username'),
					phone: '',
					isdefault: '鏄?,
					address: ''
				},
				rules: {
					name: [{ required: true, message: '璇疯緭鍏ヨ仈绯讳汉', trigger: 'blur' }],
					phone: [
						{ required: true, message: '璇疯緭鍏ユ墜鏈哄彿鐮?, trigger: 'blur' },
						{ required: true, validator: this.$validate.isMobile, trigger: 'blur' }
					],
					address: [{ required: true, message: '璇烽€夋嫨鏀惰揣鍦板潃', trigger: 'blur' }]
				},
				value: [],
				options: this.$config.cityList,
				address: '',
				isEdit: false,
				isBuy: false
			}
		},
		created() {
			if (this.$route.query.id != undefined) {
				this.isEdit = true;
				this.form = Object.assign({}, JSON.parse(this.$route.query.editObj));
				if(this.form.address) {
					let arr = this.form.address.split('-')
					this.value = [arr[0],arr[1],arr[2]]
					this.address = arr[3]
				}
			}
		},
		//鏂规硶闆嗗悎
		methods: {
			buyAdd(){
				this.isBuy = true
			},
			onSubmit(formName) {
				if(!this.value.length) {
					this.$message.error('璇烽€夋嫨鐪佸競鍖?)
					return false
				}
				if(!this.address) {
					this.$message.error('璇疯緭鍏ヨ缁嗗湴鍧€')
					return false
				}
				this.form.address = this.value.join('-') + '-' + this.address
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$http.post(`address/${this.isEdit ? 'update' : 'add'}`, this.form).then(res => {
							if (res.data.code == 0) {
								this.$message({
									message: `${this.isEdit ? '鏇存柊' : '娣诲姞'}鎴愬姛`,
									type: 'success',
									duration: 1500,
									onClose: () => {
										if(this.isBuy){
											this.$emit('change')
										}else{
											this.$router.go(-1);
									  }
									}
								});
							}
						});
					} else {
						return false;
					}
				});
			},
			getAddr() {
				if (this.address == '') {
					this.$message({
						message: '鍦板潃涓嶈兘涓虹┖',
						type: 'error',
						duration: 1500
					});
					return;
				}
				this.form.address = this.address;
				this.dialogVisible = false;
			},
			goBack() {
				this.$router.go(-1);
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.add-update-preview .el-form-item ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #333;
		white-space: nowrap;
		font-weight: 500;
		width: 200px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item ::v-deep .el-form-item__content {
		margin-left: 200px;
	}
	
	.add-update-preview .el-input ::v-deep .el-input__inner {
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
	
	.add-update-preview .el-select ::v-deep .el-input__inner {
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
	
	.add-update-preview .el-date-editor ::v-deep .el-input__inner {
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
		border-radius: 0px;
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
		border-radius: 0px;
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
		border-radius: 0px;
		color: #999;
		width: 80px;
		font-size: 26px;
		line-height: 80px;
		text-align: center;
		height: 80px;
	}
	
	.add-update-preview .el-textarea ::v-deep .el-textarea__inner {
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
    .map{
        height: 50vh;
    }
	.add-update-preview .submitBtn {
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
	.add-update-preview .submitBtn:hover {
		opacity: 0.7;
		.icon {
			color: #000;
		}
		.text {
			color: #000;
		}
	}
	.add-update-preview .closeBtn {
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
	.add-update-preview .closeBtn:hover {
		opacity: 0.7;
		.icon {
		}
		.text {
		}
	}
</style>

