<template>
	<div>

		<div class="container" :style="{'backgroundImage': indexBgUrl?`url(${$assetUrl(indexBgUrl)})`:''}">
			<el-form class='rgs-form animate__animated animate__' v-if="pageFlag=='register'" ref="registerForm" :model="registerForm" :rules="rules">
				<div class="rgs-form2">
					<div class="title">瑙傝祻楸肩鐞嗙郴缁熺殑璁捐涓庡疄鐜版敞鍐?/p></div>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="yonghuzhanghao">
						<div class="label" :class="changeRules('yonghuzhanghao')?'required':''">鐢ㄦ埛璐﹀彿锛?/div>
						<el-input v-model="registerForm.yonghuzhanghao" :readonly="ro.yonghuzhanghao" placeholder="璇疯緭鍏ョ敤鎴疯处鍙? />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="mima">
						<div class="label" :class="changeRules('mima')?'required':''">瀵嗙爜锛?/div>
						<el-input v-model="registerForm.mima" type="password" placeholder="璇疯緭鍏ュ瘑鐮?  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="mima2">
						<div class="label" :class="changeRules('mima')?'required':''">纭瀵嗙爜锛?/div>
						<el-input v-model="registerForm.mima2" type="password" placeholder="璇峰啀娆¤緭鍏ュ瘑鐮? />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="yonghuxingming">
						<div class="label" :class="changeRules('yonghuxingming')?'required':''">鐢ㄦ埛濮撳悕锛?/div>
						<el-input v-model="registerForm.yonghuxingming" :readonly="ro.yonghuxingming" placeholder="璇疯緭鍏ョ敤鎴峰鍚? />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="xingbie">
						<div class="label" :class="changeRules('xingbie')?'required':''">鎬у埆锛?/div>
						<el-select filterable v-model="registerForm.xingbie" placeholder="璇烽€夋嫨鎬у埆" :disabled="ro.xingbie">
							<el-option
								v-for="(item,index) in yonghuxingbieOptions"
								:key="index"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" class="el-email list-item">
						<div class="label required">韬唤璇侊細</div>
						<el-input @input="idcardChange" v-model="registerForm.idcard" placeholder="韬唤璇? :readonly="ro.idcard"></el-input>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" class="el-email list-item">
						<div class="label required">骞撮緞锛?/div>
						<el-input v-model="registerForm.age" placeholder="骞撮緞" :readonly="ro.age ||true"></el-input>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="shoujihao">
						<div class="label" :class="changeRules('shoujihao')?'required':''">鎵嬫満鍙凤細</div>
						<el-input v-model="registerForm.shoujihao" :readonly="ro.shoujihao" placeholder="璇疯緭鍏ユ墜鏈哄彿" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="touxiang">
						<div class="label" :class="changeRules('touxiang')?'required':''">澶村儚锛?/div>
						<file-upload
							tip="鐐瑰嚮涓婁紶澶村儚"
							action="file/upload"
							:limit="1"
							:multiple="true"
							:fileUrls="registerForm.touxiang?registerForm.touxiang:''"
							@change="yonghutouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" class="el-email list-item">
						<div class="label required">閭锛?/div>
						<div style="display: flex;flex: 1;">
							<input v-model="registerForm.email" type="text" id="email" name="email" placeholder="璇疯緭鍏ラ偖绠? autocomplete="off" class="emailInput">
							<button v-if="isEndFlag" @click="sendemailcode" id="sendemailcode" type="button" class="el-btn">鍙戦€侀獙璇佺爜</button>
							<button v-if="!isEndFlag" disabled="disabled" type="button" class="el-btn">{{count}}绉掑悗閲嶅彂</button>
						</div>
					</el-form-item>
					<el-form-item v-if="tableName=='yonghu'" class="el-email list-item">
						<div class="label required">楠岃瘉鐮侊細</div>
						<el-input v-model="emailcode" type="text" id="emailcode" name="emailcode" placeholder="璇疯緭鍏ラ獙璇佺爜" autocomplete="off" class="el-input" />
					</el-form-item>
					<div class="register-btn">
						<div class="register-btn1">
							<el-button class="register_btn" type="primary" @click="submitForm('registerForm')">娉ㄥ唽</el-button>
						</div>
						<div class="register-btn2">
							<router-link class="has_btn" to="/login">宸叉湁璐﹀彿锛岀洿鎺ョ櫥褰?/router-link>
						</div>
					</div>
				</div>
				<div class="idea1"></div>
				<div class="idea2"></div>
			</el-form>
		</div>
	</div>
</div>
</template>

<script>
	import 'animate.css';

export default {
    //鏁版嵁闆嗗悎
    data() {
		return {
            pageFlag : '',
			tableName: '',
			registerForm: {},
			forgetForm: {},
			rules: {},
			ro: {},
			requiredRules: {},
            yonghuxingbieOptions: [],
            emailcode:'',
			//鍊掕鏃?0s
			count: 60,
			//鍊掕鏃跺畾鏃跺櫒
			inter: null,
			//鍊掕缁撴潫鏍囪瘑
			isEndFlag: true,
			indexBgUrl: '',
		}
    },
	mounted() {
		if(this.$route.query.pageFlag=='register'){
			this.tableName = this.$route.query.role;
			if(this.tableName=='yonghu'){
				this.registerForm = {
					yonghuzhanghao: '',
					mima: '',
					mima2: '',
					yonghuxingming: '',
					xingbie: '',
					idcard: '',
					age: '',
					shoujihao: '',
					touxiang: '',
					email: '',
					money: '',
				}
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
			if ('yonghu' == this.tableName) {
				this.rules.yonghuzhanghao = [{ required: true, message: '璇疯緭鍏ョ敤鎴疯处鍙?, trigger: 'blur' }];
				this.requiredRules.yonghuzhanghao = [{ required: true, message: '璇疯緭鍏ョ敤鎴疯处鍙?, trigger: 'blur' }]
				this.rules.mima = [{ required: true, message: '璇疯緭鍏ュ瘑鐮?, trigger: 'blur' }];
				this.requiredRules.mima = [{ required: true, message: '璇疯緭鍏ュ瘑鐮?, trigger: 'blur' }]
				this.rules.yonghuxingming = [{ required: true, message: '璇疯緭鍏ョ敤鎴峰鍚?, trigger: 'blur' }];
				this.requiredRules.yonghuxingming = [{ required: true, message: '璇疯緭鍏ョ敤鎴峰鍚?, trigger: 'blur' }]
				this.yonghuxingbieOptions = "鐢?濂?.split(',');
				this.rules.idcard = [{ required: true, validator: this.$validate.isIdCard, trigger: 'blur' }];
				this.rules.age = [{ required: true, validator: this.$validate.isIntNumer, trigger: 'blur' }];
				this.rules.shoujihao = [{ required: true, validator: this.$validate.isMobile, trigger: 'blur' }];
				this.rules.email = [{ required: true, validator: this.$validate.isEmail, trigger: 'blur' }];
				this.rules.money = [{ required: true, validator: this.$validate.isNumber, trigger: 'blur' }];
			}
		}
	},
    created() {
		this.$http.get('config/info?name=fRegisterBackgroudImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
		this.pageFlag = this.$route.query.pageFlag;
    },
    //鏂规硶闆嗗悎
    methods: {
		changeRules(name){
			if(this.requiredRules[name]){
				return true
			}
			return false
		},
		// 鑾峰彇uuid
		getUUID () {
			return new Date().getTime();
		},
        // 涓嬩簩闅?		yonghutouxiangUploadChange(fileUrls) {
			this.registerForm.touxiang = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
		},

		// 澶氱骇鑱斿姩鍙傛暟

		sendemailcode() {
			if(!this.registerForm.email){
				this.$message.error(`閭涓嶈兘涓虹┖`);
				return
			}
			let emailF = this.$validate.isEmail2(this.registerForm.email);
			if(this.registerForm.email&&(!this.$validate.isEmail2(this.registerForm.email))){
				this.$message.error(`璇疯緭鍏ユ纭殑閭鏍煎紡`);
				return
			}
			this.isEndFlag = false;
			var _this = this;
			this.inter = window.setInterval(function() {
				_this.count = _this.count - 1;
				if (_this.count <= 0) {
					window.clearInterval(_this.inter);
					_this.isEndFlag = true;
					_this.count=60;
				}
			}, 1000);
			this.$http({
				url: `${this.tableName}/sendemail?email=`+this.registerForm.email,
				method: "get",
				params: {}
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message.success(`鍙戦€佹垚鍔焋);
				} else {
					this.$message.error(data.msg)
				}
			});
		},

		submitForm(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					var url=this.tableName+"/register";
					if(`yonghu` == this.tableName ){
						url=this.tableName+"/register?emailcode="+this.emailcode;
						if(this.registerForm.email&&(!this.$validate.isEmail2(this.registerForm.email))){
							this.$message.error(`璇疯緭鍏ユ纭殑閭鏍煎紡`);
							return
						}
					}
					if((!this.emailcode) && `yonghu` == this.tableName){
						this.$message.error(`楠岃瘉鐮佷笉鑳戒负绌篳);
						return
					}
					if(`yonghu` == this.tableName && this.registerForm.mima!=this.registerForm.mima2) {
						this.$message.error(`涓ゆ瀵嗙爜杈撳叆涓嶄竴鑷碻);
						return
					}
					this.$http.post(url, this.registerForm).then(res => {
						if (res.data.code === 0) {
							this.$message({
								message: '娉ㄥ唽鎴愬姛',
								type: 'success',
								duration: 1500,
								onClose: () => {
									this.$router.push('/login');
								}
							});
						} else {
							this.$message.error(res.data.msg);
						}
					});
				} else {
					return false;
				}
			});
		},
		resetForm(formName) {
			this.$refs[formName].resetFields();
		},
		idcardChange(e) {
			if(this.$validate.isIdCard2(e)) {
				this.registerForm.age = this.getAge(e)
			}else {
				this.registerForm.age = ''
			}
		},
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.container {
		background-attachment: fixed;
		background: url(http://codegen.caihongy.cn/20251211/b74b8dc545354f1fb594a616b5871d37.png) no-repeat left center / 50% 100%;
		display: flex;
		width: 100%;
		min-height: 100vh;
		justify-content: flex-end;
		align-items: center;
		position: relative;
		background: url(http://codegen.caihongy.cn/20251211/b74b8dc545354f1fb594a616b5871d37.png) no-repeat left center / 50% 100%;
		.rgs-form {
			border-radius: 60px;
			padding: 30px 100px;
			box-shadow: 0 0px 0px rgba(0, 0, 0, .5);
			margin: 0;
			z-index: 10;
			background: none;
			width: 55%;
			opacity: 1;
			height: auto;
			.rgs-form2 {
				width: 100%;
				.title {
					padding: 0 0 0 150px;
					margin: 0 0 0px 0;
					color: #46AC2E;
					font-weight: 600;
					font-size: 22px;
					border-color: #ff6f3c50;
					line-height: 2;
					text-shadow: none;
					background: none;
					width: 100%;
					border-width: 0;
					border-style: solid;
					text-align: center;
				}
				.subtitle {
					margin: 0 0 10px 0;
					text-shadow: 4px 4px 2px rgba(64, 158, 255, .5);
					color: rgba(64, 158, 255, 1);
					width: 100%;
					font-size: 20px;
					line-height: 44px;
					text-align: center;
				}
				.list-item {
					padding: 0;
					margin: 15px auto 0;
					width: 100%;
					border-color: #ff6f3c50;
					border-width: 0;
					position: relative;
					border-style: dashed;
					height: auto;
					::v-deep.el-form-item__content {
						padding: 0 0 0 150px;
						display: block;
						width: 100%;
						.label {
							padding: 0 5px 0 0;
							z-index: 9;
							color: #333;
							left: 0;
							width: 150px;
							font-size: 16px;
							line-height: 40px;
							position: absolute !important;
							text-align: right;
						}
						
						.required {
							position: relative;
						}
						.required::after{
							color: red;
							left: 140px;
							position: absolute;
							content: "*";
						}
						.el-input {
							flex: 1;
							width: 100%;
						}
						.el-input .el-input__inner {
							border: 1px solid #00000050;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-input .el-input__inner:focus {
							border: 1px solid #000000;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-input-number {
							flex: 1;
							width: 100%;
						}
						.el-input-number .el-input__inner {
							text-align: left;
							border: 1px solid #00000050;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-input-number .el-input-number__decrease {
							display: none;
						}
						.el-input-number .el-input-number__increase {
							display: none;
						}
						.el-select {
							flex: 1;
							width: 100%;
						}
						.el-select .el-input__inner {
							border: 1px solid #00000050;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-select .el-input__inner:focus {
							border: 1px solid #000000;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-date-editor {
							flex: 1;
							width: 100%;
						}
						.el-date-editor .el-input__inner {
							border: 1px solid #00000050;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-date-editor .el-input__inner:focus {
							border: 1px solid #000000;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-upload--picture-card {
							background: transparent;
							border: 0;
							border-radius: 0;
							width: auto;
							height: auto;
							line-height: initial;
							vertical-align: middle;
						}
						.upload .upload-img {
							border: 1px solid #00000050;
							cursor: pointer;
							border-radius: 0;
							color: #00000080;
							background: rgba(255,255,255,.5);
							object-fit: cover;
							width: 60px;
							font-size: 22px;
							line-height: 60px;
							text-align: center;
							height: 60px;
						}
						.el-upload-list .el-upload-list__item {
							border: 1px solid #00000050;
							cursor: pointer;
							border-radius: 0;
							color: #00000080;
							background: rgba(255,255,255,.5);
							object-fit: cover;
							width: 60px;
							font-size: 22px;
							line-height: 60px;
							text-align: center;
							height: 60px;
							font-size: 14px;
							line-height: 1.8;
						}
						.el-upload .el-icon-plus {
							border: 1px solid #00000050;
							cursor: pointer;
							border-radius: 0;
							color: #00000080;
							background: rgba(255,255,255,.5);
							object-fit: cover;
							width: 60px;
							font-size: 22px;
							line-height: 60px;
							text-align: center;
							height: 60px;
						}
						.el-upload__tip {
							margin: 0;
							color: #000000;
							font-size: 16px;
						}
						.emailInput {
							border: 1px solid #00000050;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.emailInput:focus {
							border: 1px solid #000000;
							border-radius: 0;
							padding: 0 30px;
							box-shadow: none;
							color: #000000;
							flex: 1;
							background: none;
							width: 100%;
							font-size: 16px;
							height: 50px;
						}
						.el-btn {
							border: 0;
							cursor: pointer;
							padding: 0 15px;
							margin: 0 0 5px;
							color: #fff;
							font-size: 15px;
							float: right;
							border-radius: 0px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							background: #058747;
							width: auto;
							height: 50px;
						}
						.el-btn:hover {
							opacity: 0.5;
						}
						
						.el-input__inner::placeholder {
							color: #999;
							font-size: 16px;
						}
						input::placeholder {
							color: #999;
							font-size: 16px;
						}
						.editor {
							margin: 0 0 15px;
							background: #fff;
							width: 100%;
							height: auto;
						}
						.editor .ql-toolbar {
							background: none;
						}
						.editor .ql-container {
							background: none;
						}
						.editor .ql-container .ql-blank::before {
							color: #999;
						}
					}
				}
				.register-btn {
					padding: 0 0 0 150px;
					margin: 10px 0 0;
					width: 100%;
				}
				.register-btn1 {
					margin: 0 0 20px 0;
					width: 100%;
					text-align: center;
				}
				.register-btn2 {
					width: 100%;
					text-align: center;
				}
				.register_btn {
					border: 1px solid #707070;
					cursor: pointer;
					padding: 0 24px;
					margin: 0 auto;
					color: #fff;
					display: inline-block;
					font-size: 20px;
					line-height: 50px;
					transition: all 0s;
					border-radius: 25px;
					outline: none;
					background: #058747;
					width: 100%;
					min-width: 150px;
					height: 50px;
				}
				.register_btn:hover {
					opacity: 0.8;
				}
				.has_btn {
					cursor: pointer;
					padding: 0;
					color: #000;
					text-decoration: none;
					display: inline-block;
					font-size: 16px;
				}
				.has_btn:hover {
					opacity: 0.8;
				}
			}
			.idea1 {
				background: red;
				display: none;
				width: 100%;
				height: 40px;
			}
			.idea2 {
				background: blue;
				display: none;
				width: 100%;
				height: 40px;
			}
		}
	}
	
	::-webkit-scrollbar {
		display: none;
	}
</style>

