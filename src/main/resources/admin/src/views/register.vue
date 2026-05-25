<template>
	<div>
		<div class="register-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<el-form v-if="pageFlag=='register'" ref="ruleForm" class="rgs-form animate__animated animate__" :model="ruleForm" :rules="rules">
				<div class="rgs-form2">
					<div class="title">瑙傝祻楸肩鐞嗙郴缁熺殑璁捐涓庡疄鐜?/div>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('yonghuzhanghao')?'required':''">鐢ㄦ埛璐﹀彿锛?/div>
						<el-input  v-model="ruleForm.yonghuzhanghao" :readonly="ro.yonghuzhanghao" autocomplete="off" placeholder="鐢ㄦ埛璐﹀彿"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('mima')?'required':''">瀵嗙爜锛?/div>
						<el-input  v-model="ruleForm.mima" :readonly="ro.mima" autocomplete="off" placeholder="瀵嗙爜"  type="password"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('mima')?'required':''">纭瀵嗙爜锛?/div>
						<el-input  v-model="ruleForm.mima2" autocomplete="off" placeholder="纭瀵嗙爜" type="password" :readonly="ro.mima" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('yonghuxingming')?'required':''">鐢ㄦ埛濮撳悕锛?/div>
						<el-input  v-model="ruleForm.yonghuxingming" :readonly="ro.yonghuxingming" autocomplete="off" placeholder="鐢ㄦ埛濮撳悕"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('xingbie')?'required':''">鎬у埆锛?/div>
						<el-select filterable v-model="ruleForm.xingbie" placeholder="璇烽€夋嫨鎬у埆" :disabled="ro.xingbie">
							<el-option
								v-for="(item,index) in yonghuxingbieOptions"
								v-bind:key="index"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('idcard')?'required':''">韬唤璇侊細</div>
						<el-input @input="idcardChange" v-model="ruleForm.idcard" :readonly="ro.idcard" autocomplete="off" placeholder="韬唤璇? type="text" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('age')?'required':''">骞撮緞锛?/div>
						<el-input v-model="ruleForm.age" :readonly="ro.age||true" autocomplete="off" placeholder="骞撮緞" type="text" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('shoujihao')?'required':''">鎵嬫満鍙凤細</div>
						<el-input  v-model="ruleForm.shoujihao" :readonly="ro.shoujihao" autocomplete="off" placeholder="鎵嬫満鍙?  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('touxiang')?'required':''">澶村儚锛?/div>
						<file-upload
							tip="鐐瑰嚮涓婁紶澶村儚"
							action="file/upload"
							:limit="3"
							:multiple="true"
							:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
							@change="yonghutouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item email" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('email')?'required':''">閭锛?/div>
						<div style="display: flex;flex: 1;">
							<input v-model="ruleForm.email" autocomplete="off" placeholder="閭"/>
							<button v-if="isEndFlag" type="success" class="register-code" @click="sendemailcode()">鍙戦€侀獙璇佺爜</button>
							<button v-if="!isEndFlag" type="success" class="register-code" disabled="disabled">{{SecondToDate}}</button>
						</div>
					</el-form-item>
					<el-form-item class="list-item email-code" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('email')?'required':''">楠岃瘉鐮侊細</div>
						<el-input  v-model="emailcode" autocomplete="off" placeholder="楠岃瘉鐮? />
					</el-form-item>
					<div class="register-btn">
						<div class="register-btn1">
							<button type="button" class="r-btn" @click="login()">娉ㄥ唽</button>
						</div>
						<div class="register-btn2">
							<div class="r-login" @click="close()">宸叉湁璐﹀彿锛岀洿鎺ョ櫥褰?/div>
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css'
export default {
	data() {
		return {
			ruleForm: {
			},
			forgetForm: {},
            pageFlag : '',
			tableName:"",
			rules: {},
			ro: {},
            emailcode:'',
			// 鍊掕鏃舵椂闂?			count: 60,
			// 鍊掕鏃跺畾鏃跺櫒
			inter: null,
			// 鍊掕鏃舵槸鍚︾粨鏉?			isEndFlag: true,
            yonghuxingbieOptions: [],
			indexBgUrl: '',
		};
	},
	computed: {
		SecondToDate: function() {
			var time = this.count;
			if (null != time && "" != time) {
				time = parseInt(time) + "绉掑悗閲嶅彂";
			}
			return time;
		}
	},
	mounted(){
		this.pageFlag = this.$route.query.pageFlag
		if(this.$route.query.pageFlag=='register'){
			
			let table = this.$storage.get("loginTable");
			this.tableName = table;
			if(this.tableName=='yonghu'){
				this.ruleForm = {
					yonghuzhanghao: '',
					mima: '',
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
				this.rules.yonghuzhanghao = [{ required: true, message: '璇疯緭鍏ョ敤鎴疯处鍙?, trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.mima = [{ required: true, message: '璇疯緭鍏ュ瘑鐮?, trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.yonghuxingming = [{ required: true, message: '璇疯緭鍏ョ敤鎴峰鍚?, trigger: 'blur' }]
			}
			this.yonghuxingbieOptions = "鐢?濂?.split(',')
		}
	},
	created() {
		this.$http.get('config/info?name=bRegisterBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
	},
	destroyed() {
		  	},
	methods: {
		changeRules(name){
			if(this.rules[name]){
				return true
			}
			return false
		},
		// 鑾峰彇uuid
		getUUID () {
			return new Date().getTime();
		},
		close(){
			this.$router.push({ path: "/login" });
		},
        yonghutouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },

        // 澶氱骇鑱斿姩鍙傛暟

		sendemailcode() {
			if(!this.ruleForm.email){
				this.$message.error(`閭涓嶈兘涓虹┖`);
				return
			}
			if(this.ruleForm.email&&(!this.$validate.isEmail(this.ruleForm.email))){
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
				url: `${this.tableName}/sendemail?email=`+this.ruleForm.email,
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

		// 娉ㄥ唽
		login() {
			var url=this.tableName+"/register";
			if((!this.ruleForm.yonghuzhanghao) && `yonghu` == this.tableName){
				this.$message.error(`鐢ㄦ埛璐﹀彿涓嶈兘涓虹┖`);
				return
			}
			if((!this.ruleForm.mima) && `yonghu` == this.tableName){
				this.$message.error(`瀵嗙爜涓嶈兘涓虹┖`);
				return
			}
			if((this.ruleForm.mima!=this.ruleForm.mima2) && `yonghu` == this.tableName){
				this.$message.error(`涓ゆ瀵嗙爜杈撳叆涓嶄竴鑷碻);
				return
			}
			if((!this.ruleForm.yonghuxingming) && `yonghu` == this.tableName){
				this.$message.error(`鐢ㄦ埛濮撳悕涓嶈兘涓虹┖`);
				return
			}
			if(`yonghu` == this.tableName && this.ruleForm.idcard &&(!this.$validate.checkIdCard(this.ruleForm.idcard))){
				this.$message.error(`韬唤璇佸簲杈撳叆韬唤璇佹牸寮廯);
				return
			}
			if(`yonghu` == this.tableName && this.ruleForm.age &&(!this.$validate.isIntNumer(this.ruleForm.age))){
				this.$message.error(`骞撮緞搴旇緭鍏ユ暣鏁癭);
				return
			}
			if(`yonghu` == this.tableName && this.ruleForm.shoujihao &&(!this.$validate.isMobile(this.ruleForm.shoujihao))){
				this.$message.error(`鎵嬫満鍙峰簲杈撳叆鎵嬫満鏍煎紡`);
				return
			}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
			if(`yonghu` == this.tableName && this.ruleForm.email &&(!this.$validate.isEmail(this.ruleForm.email))){
				this.$message.error(`閭搴旇緭鍏ラ偖浠舵牸寮廯);
				return
			}
			if(`yonghu` == this.tableName ){
				url=this.tableName+"/register?emailcode="+this.emailcode;
				if(this.ruleForm.email&&(!this.$validate.isEmail(this.ruleForm.email))){
					this.$message.error(`璇疯緭鍏ユ纭殑閭鏍煎紡`);
					return
				}
			}
			if((!this.emailcode) && `yonghu` == this.tableName){
				this.$message.error(`楠岃瘉鐮佷笉鑳戒负绌篳);
				return
			}
			this.$http({
				url: url,
				method: "post",
				data:this.ruleForm
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message({
						message: "娉ㄥ唽鎴愬姛",
						type: "success",
						duration: 1500,
						onClose: () => {
							this.$router.replace({ path: "/login" });
						}
					});
				} else {
					this.$message.error(data.msg);
				}
			});
		},
		idcardChange(e) {
			if(this.$validate.checkIdCard(e)) {
				this.ruleForm.age = this.getAge(e)
			}else {
				this.ruleForm.age = ''
			}
		},
	}
};
</script>

<style lang="scss" scoped>
.register-container {
	position: relative;
	background: url(http://codegen.caihongy.cn/20260126/46027c3659c94fe2a567a779cd10a4ca.jpg);
	background-repeat: no-repeat;
	background-size: cover !important;
	background: url(http://codegen.caihongy.cn/20260126/46027c3659c94fe2a567a779cd10a4ca.jpg);
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: center;
	align-items: center;
	background-position: top center;
	.rgs-form {
		.rgs-form2 {
		width: 100%;
		}
		padding: 0;
		margin: 20px 13% 20px auto;
		z-index: 1;
		display: flex;
		min-height: 500px;
		flex-wrap: wrap;
		border-radius: 16px;
		box-shadow: none;
		align-content: center;
		background: #fff;
		width: 500px;
		align-items: center;
		height: auto;
		.title {
			margin: 0 0 20px 0;
			text-shadow: none;
			color: #2dc7a3;
			font-weight: 600;
			width: auto;
			font-size: 18px;
			line-height: 1;
			text-align: center;
			order: -2;
		}
		.list-item {
			border: 1px solid #eeeeee;
			border-radius: 30px;
			padding: 0 0 0 130px;
			margin: 0 auto 15px;
			width: 100%;
			position: relative;
			height: auto;
			::v-deep .el-form-item__content {
				display: block;
			}
			.lable {
				padding: 0 10px 0 0;
				color: #333;
				left: -130px;
				width: 130px;
				font-size: 14px;
				line-height: 44px;
				position: absolute !important;
				text-align: right;
			}
			.el-input {
				width: 100%;
			}
			.el-input ::v-deep .el-input__inner {
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-input ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-input-number {
				width: 100%;
			}
			.el-input-number ::v-deep .el-input__inner {
				text-align: center;
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-input-number ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
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
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px;
				color: #666;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-select ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-date-editor {
				width: 100%;
			}
			.el-date-editor ::v-deep .el-input__inner {
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px 0 30px;
				color: #666;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-date-editor ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px 0 30px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-date-editor.el-input {
				width: 100%;
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
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0 0;
				color: #999;
				width: 60px;
				font-size: 24px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload-list .el-upload-list__item {
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0 0;
				color: #999;
				width: 60px;
				font-size: 24px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload .el-icon-plus {
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0 0;
				color: #999;
				width: 60px;
				font-size: 24px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload__tip {
				margin: 0;
				color: #838fa1;
				line-height: 24px;
			}
			::v-deep .el-input__inner::placeholder {
				color: #999;
				font-size: 14px;
			}
			.required {
				position: relative;
			}
			.required::after{
				color: red;
				left: 120px;
				position: absolute;
				content: "*";
			}
			.editor {
				border-radius: 10px;
				margin: 10px;
				width: auto;
				height: auto;
			}
			.editor>.avatar-uploader {
				line-height: 0;
				height: 0;
			}
		}
		.list-item.email {
			input {
				border: 0px solid #ddd;
				border-radius: 0px 0 0 0px;
				padding: 0 10px;
				box-shadow: none;
				margin: 0;
				outline: none;
				color: #606266;
				background: none;
				flex: 1;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			input:focus {
				border: none;
				border-radius: 0px 0 0 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				flex: 1;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			input::placeholder {
				color: #999;
				font-size: 14px;
			}
			button {
				border: 0;
				cursor: pointer;
				border-radius: 0 30px 30px 0;
				padding: 0;
				box-shadow: none;
				margin: 0;
				outline: none;
				color: #333;
				background: #eee;
				width: 110px;
				font-size: 15px;
				height: 44px;
			}
			button:hover {
				opacity: 0.8;
			}
		}
		.register-btn {
			margin: 0 auto;
			width: 100%;
		}
		.register-btn1 {
			width: 100%;
		}
		.register-btn2 {
			margin: 10px 0;
			width: 100%;
			text-align: center;
		}
		.r-btn {
			border: 0;
			cursor: pointer;
			padding: 0 10px;
			margin: 20px auto 5px;
			color: #fff;
			display: block;
			font-size: 16px;
			border-radius: 30px;
			box-shadow: none;
			outline: none;
			background: #2dc7a3;
			width: 100%;
			height: 40px;
		}
		.r-btn:hover {
			opacity: 0.8;
		}
		.r-login {
			cursor: pointer;
			padding: 0 10%;
			color: rgba(159, 159, 159, 1);
			display: inline-block;
			text-decoration: underline;
			font-size: 14px;
			line-height: 1;
		}
		.r-login:hover {
			opacity: 0.5;
		}
	}
}
	
	::-webkit-scrollbar {
	  display: none;
	}
</style>

