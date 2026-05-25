<template>
	<div class="center-preview">
		<div class="center-title">{{ title }}</div>
		<div class="center-info">
			<div class="center-info-title">涓汉淇℃伅</div>

			<div class="img-box" v-if="userTableName=='yonghu'">
				<img :src="sessionForm.touxiang?baseUrl + sessionForm.touxiang:require('@/assets/avator.png')">
			</div>
			<div class="info-item1" v-if="userTableName=='yonghu'">
				<span class="icon iconfont "></span>
				<div class="label">鐢ㄦ埛璐﹀彿锛?/div>
				<div class="text">{{sessionForm.yonghuzhanghao}}</div>
			</div>
			<div class="info-item2" v-if="userTableName=='yonghu'">
				<span class="icon iconfont "></span>
				<div class="label">鐢ㄦ埛濮撳悕锛?/div>
				<div class="text">{{sessionForm.yonghuxingming}}</div>
			</div>
			<div class="info-item3" v-if="userTableName=='yonghu'">
				<span class="icon iconfont "></span>
				<div class="label">鎬у埆锛?/div>
				<div class="text">{{sessionForm.xingbie}}</div>
			</div>
			<div class="info-item4" v-if="userTableName=='yonghu'">
				<span class="icon iconfont "></span>
				<div class="label">韬唤璇侊細</div>
				<div class="text">{{sessionForm.idcard}}</div>
			</div>
			<div class="info-item5" v-if="userTableName=='yonghu'">
				<span class="icon iconfont "></span>
				<div class="label">骞撮緞锛?/div>
				<div class="text">{{sessionForm.age}}</div>
			</div>
			<div class="info-item6" v-if="userTableName=='yonghu'">
				<span class="icon iconfont "></span>
				<div class="label">鎵嬫満鍙凤細</div>
				<div class="text">{{sessionForm.shoujihao}}</div>
			</div>
		
		</div>
	
		<div class="center-box">
			<div class="center-tab-view">
				<div class="center-tab" :class="activeName==title2?'is-active':''" @click="handleClick(title2)">{{title2}}</div>
				<div class="center-tab" :class="activeName=='淇敼瀵嗙爜'?'is-active':''" @click="handleClick('淇敼瀵嗙爜')">淇敼瀵嗙爜</div>
				<div class="center-tab" v-if="hasBack(item.menu,item.child[0].tableName)" v-for="(item,index) in menuList" :key="index" @mouseenter="centerTabEnter(item.child[0].tableName)" @mouseleave="centerTabEnter('')" @click="menuClick(item.child[0],item.child.length)">
					<template v-if="item.child.length==1">
						{{item.child[0].menu}}
					</template>
					<template v-else>
						{{item.menu}}
						<transition name="el-fade-in-linear">
							<div class="center-second-tab-view" v-if="showActive=='show' + item.child[0].tableName">
								<div class="center-second-tab" v-for="(items,indexs) in item.child" :key="indexs" @click="menuClick(items)">
									{{items.menu}}
								</div>
							</div>
						</transition>
					</template>
				</div>
				<div class="center-tab" :class="activeName=='鎴戠殑璁㈠崟'?'is-active':''" @click="handleClick('鎴戠殑璁㈠崟')">鎴戠殑璁㈠崟</div>
				<div class="center-tab" :class="activeName=='鎴戠殑鍙戝竷'?'is-active':''" @click="handleClick('鎴戠殑鍙戝竷')">鎴戠殑鍙戝竷</div>
				<div class="center-tab" :class="activeName=='鎴戠殑鍦板潃'?'is-active':''" @click="handleClick('鎴戠殑鍦板潃')">鎴戠殑鍦板潃</div>


			</div>
			<div class="center-content-box">
				<div class="center-content-view" v-show="activeName=='涓汉涓績'">
					<el-form class="center-preview-pv" ref="sessionForm" :model="sessionForm" :rules="rules" label-width="180px">
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="鐢ㄦ埛璐﹀彿" prop="yonghuzhanghao">
							<el-input v-model="sessionForm.yonghuzhanghao" placeholder="鐢ㄦ埛璐﹀彿" :disabled="ro.yonghuzhanghao"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="鐢ㄦ埛濮撳悕" prop="yonghuxingming">
							<el-input v-model="sessionForm.yonghuxingming" placeholder="鐢ㄦ埛濮撳悕" :disabled="ro.yonghuxingming"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="鎬у埆" prop="xingbie">
							<el-select filterable v-model="sessionForm.xingbie" placeholder="璇烽€夋嫨鎬у埆" :disabled="ro.xingbie">
								<el-option v-for="(item, index) in dynamicProp.xingbie" :key="index" :label="item" :value="item"></el-option>
							</el-select>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="韬唤璇? prop="idcard">
							<el-input @input="idcardChange" v-model="sessionForm.idcard" placeholder="韬唤璇? :disabled="ro.idcard"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="骞撮緞" prop="age">
							<el-input v-model="sessionForm.age" placeholder="骞撮緞" :disabled="ro.age ||true"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="鎵嬫満鍙? prop="shoujihao">
							<el-input v-model="sessionForm.shoujihao" placeholder="鎵嬫満鍙? :disabled="ro.shoujihao"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="澶村儚" prop="touxiang">
							<file-upload
								tip="鐐瑰嚮涓婁紶澶村儚"
								action="file/upload"
								:limit="1"
								:multiple="true"
								:fileUrls="sessionForm.touxiang?sessionForm.touxiang:''"
								@change="yonghutouxiangHandleAvatarSuccess"
								></file-upload>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="閭" prop="email">
							<el-input v-model="sessionForm.email" placeholder="閭" :disabled="ro.email"></el-input>
						</el-form-item>
						<el-form-item class="center-item" v-if="userTableName=='yonghu'" label="浣欓">
							<div class="balance-item">
								<el-input v-model="sessionForm.money" placeholder="浣欓" readonly></el-input>
								<div class="balanceBtn" @click="dialogFormVisibleMoney = true">
									<span class="icon iconfont icon-chujia13"></span>
									<span class="text">鐐规垜鍏呭€?/span>
								</div>
							</div>
						</el-form-item>
						<el-form-item class="center-btn-item">
							<div class="updateBtn" type="primary" @click="onSubmit('sessionForm')">
								<span class="icon iconfont icon-xiugai17"></span>
								<span class="text">淇濆瓨淇℃伅</span>
							</div>
							<div class="closeBtn" type="danger" @click="logout">
								<span class="icon iconfont icon-fanhui12"></span>
								<span class="text">閫€鍑虹櫥褰?/span>
							</div>
						</el-form-item>
					</el-form>
				</div>
				<div class="center-content-view" v-show="activeName=='淇敼瀵嗙爜'">
					<el-form v-if="psdType==1" class="center-preview-pv" ref="passwordForm" :model="passwordForm" :rules="passwordRules" label-width="180px">
						<el-form-item class="center-item" label="鍘熷瘑鐮? prop="password">
							<el-input type="password" v-model="passwordForm.password" placeholder="鍘熷瘑鐮?></el-input>
						</el-form-item>
						<el-form-item class="center-item" label="鏂板瘑鐮? prop="newpassword">
							<el-input type="password" v-model="passwordForm.newpassword" placeholder="鏂板瘑鐮?></el-input>
						</el-form-item>
						<el-form-item class="center-item" label="纭瀵嗙爜" prop="repassword">
							<el-input type="password" v-model="passwordForm.repassword" placeholder="纭瀵嗙爜"></el-input>
						</el-form-item>
						<el-form-item class="center-btn-item">
							<div class="updateBtn" type="primary" @click="updatePassword">
								<span class="icon iconfont icon-xiugai17"></span>
								<span class="text">淇敼瀵嗙爜</span>
							</div>
						</el-form-item>
					</el-form>
				</div>
			</div>
		</div>
		<el-dialog title="鐢ㄦ埛鍏呭€? :visible.sync="dialogFormVisibleMoney" width="726px" center>
			<el-form :model="chongzhiForm">
				<el-form-item label="鍏呭€奸噾棰? label-width="120px">
					<el-input type="number" v-model="chongzhiForm.money" autocomplete="off" placeholder="鍏呭€奸噾棰?></el-input>
				</el-form-item>
				<el-form-item label-width="120px">
					<el-radio-group v-model="chongzhiForm.radio">
						<el-radio style="margin-bottom: 30px" label="寰俊鏀粯">
							<el-image
								style="width: 60px; height: 60px;vertical-align: middle;"
								:src="require('@/assets/weixin.png')"
								fit="fill"></el-image>
							<span style="display: inline-block;margin-left: 10px">寰俊鏀粯</span>
						</el-radio>
						<el-radio label="鏀粯瀹濇敮浠?>
							<el-image
								style="width: 60px; height: 60px;vertical-align: middle;"
								:src="require('@/assets/zhifubao.png')"
								fit="fill"></el-image>
							<span style="display: inline-block;margin-left: 10px">鏀粯瀹濇敮浠?/span>
						</el-radio>
						<el-radio label="涓浗寤鸿閾惰鏀粯">
							<el-image
								style="width: 120px; height: 60px;vertical-align: middle;"
								:src="require('@/assets/jianshe.png')"
								fit="fill"></el-image>
						</el-radio>
						<el-radio label="涓浗鍐滀笟閾惰鏀粯">
							<el-image
								style="width: 126px; height: 60px;vertical-align: middle;"
								:src="require('@/assets/nongye.png')"
								fit="fill"></el-image>
						</el-radio>
						<el-radio label="涓浗閾惰鏀粯">
							<el-image
								style="width: 140px; height: 60px;vertical-align: middle;"
								:src="require('@/assets/zhongguo.png')"
								fit="fill"></el-image>
						</el-radio>
						<el-radio label="浜ら€氶摱琛屾敮浠?>
							<el-image
								style="width: 120px; height: 60px;vertical-align: middle;"
								:src="require('@/assets/jiaotong.png')"
								fit="fill"></el-image>
						</el-radio>
					</el-radio-group>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="dialogFormVisibleMoney = false">鍙?娑?/el-button>
				<el-button type="primary" @click="chongzhi">纭鍏呭€?/el-button>
				<el-button type="primary" @click="qrCodeClick(1)">浜岀淮鐮佸厖鍊?/el-button>
			</div>
		</el-dialog>
		<el-dialog title="鏀粯" :visible.sync="qrCodeVisible" width="30%">
			<img src="@/assets/QRcode.png" style="width: 100%"></img>
			<div>
				<el-button type="primary" style="margin-left: 10px;" @click="qrCodeSave">纭鏀粯</el-button>
				<el-button type="danger" style="margin-left: 10px;" @click="qrCodeVisible=false">鍙栨秷</el-button>
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import config from '@/config/config';
	import menu from '@/config/menu';
	import Vue from 'vue';
	export default {
		//鏁版嵁闆嗗悎
		data() {
			return {
				title: '涓汉涓績',
				title2: '涓汉涓績',
				showActive: '',
				activeName: '涓汉涓績',
				baseUrl: config.baseUrl,
				sessionForm: {},
				ro: {},
				passwordForm: {},
				psdType: '1',
				passwordRules: {
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
				rules: {},
				chongzhiForm: {
					money: '',
					radio: ''
				},
				menuList: [],
				disabled: false,
				dialogFormVisibleMoney: false,
				qrCodeVisible: false,
				qrCodeType: '',
				uploadUrl: config.baseUrl + 'file/upload',
				imageUrl: '',
				headers: {Token: localStorage.getItem('frontToken')},
				userTableName: localStorage.getItem('UserTableName'),
				dynamicProp: {},
			}
		},
		async created() {
			let menus = menu.list()
			for(let x in menus){
				if(menus[x].tableName == this.userTableName){
					for(let i in menus[x].backMenu){
						if(menus[x].backMenu[i].child&&menus[x].backMenu[i].child.length&&menus[x].backMenu[i].child[0].tableName.indexOf('exam')!=-1){
							menus[x].backMenu.splice(i,1)
						}
					}
					this.menuList = menus[x].backMenu
				}
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'yonghuzhanghao', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'mima', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'yonghuxingming', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'xingbie', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'idcard', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'age', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'shoujihao', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'touxiang', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'email', null);
			}
			if ('yonghu' == this.userTableName) {
				this.$set(this.sessionForm, 'money', null);
			}

			if ('yonghu' == this.userTableName) {
				if (this.rules['yonghuzhanghao']){
					this.rules['yonghuzhanghao'].push({ required: true, message: '璇疯緭鍏ョ敤鎴疯处鍙?, trigger: 'blur' })
				}else if(!this.rules['yonghuzhanghao']) {
					this.$set(this.rules, 'yonghuzhanghao', [{ required: true, message: '璇疯緭鍏ョ敤鎴疯处鍙?, trigger: 'blur' }]);
				}
				if (this.rules['mima']){
					this.rules['mima'].push({ required: true, message: '璇疯緭鍏ュ瘑鐮?, trigger: 'blur' })
				}else if(!this.rules['mima']) {
					this.$set(this.rules, 'mima', [{ required: true, message: '璇疯緭鍏ュ瘑鐮?, trigger: 'blur' }]);
				}
				if (this.rules['yonghuxingming']){
					this.rules['yonghuxingming'].push({ required: true, message: '璇疯緭鍏ョ敤鎴峰鍚?, trigger: 'blur' })
				}else if(!this.rules['yonghuxingming']) {
					this.$set(this.rules, 'yonghuxingming', [{ required: true, message: '璇疯緭鍏ョ敤鎴峰鍚?, trigger: 'blur' }]);
				}
				this.$set(this.rules, 'idcard', [{ required: false, validator: this.$validate.isIdCard, trigger: 'blur' }]);
				this.$set(this.rules, 'age', [{ required: false, validator: this.$validate.isIntNumer, trigger: 'blur' }]);
				this.$set(this.rules, 'shoujihao', [{ required: false, validator: this.$validate.isMobile, trigger: 'blur' }]);
				this.$set(this.rules, 'email', [{ required: false, validator: this.$validate.isEmail, trigger: 'blur' }]);
				this.$set(this.rules, 'money', [{ required: false, validator: this.$validate.isNumber, trigger: 'blur' }]);
				this.ro = {
					yonghuzhanghao: true,
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

			this.init();
			await this.$http.get(`${localStorage.getItem('UserTableName')}/session`, {emulateJSON: true}).then(async res => {
				if (res.data.code == 0) {
					this.sessionForm = res.data.data
				}
			});
		},
		//鏂规硶闆嗗悎
		methods: {
			init() {
				if ('yonghu' == this.userTableName) {
					this.dynamicProp.xingbie = '鐢?濂?.split(',');
				}
			},
			setSession(){
				localStorage.setItem('sessionForm',JSON.stringify(this.sessionForm))
			},
			onSubmit(formName) {
				if(`yonghu` == this.userTableName && this.sessionForm.touxiang!=null){
					this.sessionForm.touxiang = this.sessionForm.touxiang.replace(new RegExp(this.$config.baseUrl,"g"),"");
				}
				this.$refs[formName].validate((valid) => {
					if (valid) {
						this.$http.post(this.userTableName + '/update', this.sessionForm).then(res => {
							if (res.data.code == 0) {
								this.setSession()
								this.$message({
									message: '鏇存柊鎴愬姛',
									type: 'success',
									duration: 1500
								});
							}
						});
					} else {
						return false;
					}
				});
			},
			yonghutouxiangHandleAvatarSuccess(fileUrls) {
				this.sessionForm.touxiang = fileUrls;
			},
			chongzhi() {
				if (this.chongzhiForm.money == '') {
					this.$message({
						message: '璇疯緭鍏ュ厖鍊奸噾棰?,
						type: 'error',
						duration: 1500
					});
					return;
				}
				if (this.chongzhiForm.money <= 0) {
					this.$message({
						message: '璇疯緭鍏ユ纭殑鍏呭€奸噾棰?,
						type: 'error',
						duration: 1500
					});
					return;
				}
				if (this.chongzhiForm.radio == '') {
					this.$message({
						message: '璇烽€夋嫨鍏呭€兼柟寮?,
						type: 'error',
						duration: 1500
					});
					return;
				}
				if(!this.sessionForm.money) {
					this.sessionForm.money = parseFloat(this.chongzhiForm.money)
				}else{
					this.sessionForm.money = parseFloat(this.sessionForm.money) + parseFloat(this.chongzhiForm.money);
				}
				
				this.$http.post(this.userTableName + '/update', this.sessionForm).then(async res => {
					if (res.data.code == 0) {
						await this.$http.post('chargerecord/add',{
							username: localStorage.getItem("username"),
							role: localStorage.getItem("frontRole"),
							amount: parseFloat(this.chongzhiForm.money),
							userid: this.sessionForm.id
						}).then(rs=>{})
						this.setSession()
						this.$message({
							message: '鍏呭€兼垚鍔?,
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.dialogFormVisibleMoney = false;
							}
						});
					}
				});
			},
			qrCodeClick(type=1){
				if(type==1) {
					if (this.chongzhiForm.money == '') {
						this.$message({
							message: '璇疯緭鍏ュ厖鍊奸噾棰?,
							type: 'error',
							duration: 1500
						});
						return;
					}
					if (this.chongzhiForm.money <= 0) {
						this.$message({
							message: '璇疯緭鍏ユ纭殑鍏呭€奸噾棰?,
							type: 'error',
							duration: 1500
						});
						return;
					}
				}
				this.qrCodeType = type
				this.qrCodeVisible = true
			},
			qrCodeSave(){
				if(this.qrCodeType==1) {
					if(!this.sessionForm.money) {
						this.sessionForm.money = parseFloat(this.chongzhiForm.money)
					}else{
						this.sessionForm.money = parseFloat(this.sessionForm.money) + parseFloat(this.chongzhiForm.money);
					}
					
					this.$http.post(this.userTableName + '/update', this.sessionForm).then(async res => {
						if (res.data.code == 0) {
							await this.$http.post('chargerecord/add',{
								username: localStorage.getItem("username"),
								role: localStorage.getItem("frontRole"),
								amount: parseFloat(this.chongzhiForm.money),
								userid: this.sessionForm.id
							}).then(rs=>{})
							this.setSession()
							this.$message({
								message: '鍏呭€兼垚鍔?,
								type: 'success',
								duration: 1500,
								onClose: () => {
									this.chongzhiForm.money = ''
									this.dialogFormVisibleMoney = false;
									this.qrCodeVisible = false;
								}
							});
						}
					});
				}
			},
			handleClick(name) {
				switch(name) {
					case '涓汉涓績':
						this.activeName = name
						this.$router.push('/index/center');
						break;
					case '淇敼瀵嗙爜':
						this.activeName = name
						this.passwordForm = {
							password: '',
							newpassword: '',
							repassword: '',
						}
						this.psdType = '1'
						this.$forceUpdate()
						break;
					case '鎴戠殑璁㈠崟':
						this.$router.push('/index/shop-order/order');
						break;
					case '鎴戠殑鍙戝竷':
						this.$router.push('/index/myForumList');
						break;
					case '鎴戠殑鍦板潃':
						this.$router.push('/index/shop-address/list');
						break;
					case '鎴戠殑鐐硅禐':
						localStorage.setItem('storeupType', 21);
						this.$router.push('/index/storeup');
						break;
					case '鎴戠殑鏀惰棌':
						localStorage.setItem('storeupType', 1);
						this.$router.push('/index/storeup');
						break;
					case '鎴戠殑璇勮':
						localStorage.setItem('storeupType', 81);
						this.$router.push('/index/storeup');
						break;
				}

				this.title = event.target.outerText;
			},
			idcardChange(e) {
				if(this.$validate.isIdCard2(e)) {
					this.sessionForm.age = this.getAge(e)
				}else {
					this.sessionForm.age = ''
				}
			},
			async updatePassword(){
				this.$refs["passwordForm"].validate(async valid => {
					if (valid) {
						var password = "";
						this.passwordForm.newpassword = this.passwordForm.newpassword.trim()
						this.passwordForm.repassword = this.passwordForm.repassword.trim()
						if (this.sessionForm.mima) {
							password = this.sessionForm.mima;
						} else if (this.sessionForm.password) {
							password = this.sessionForm.password;
						}
						if (this.userTableName == 'yonghu') {
						}
						if (this.passwordForm.password != password) {
							this.$message.error("鍘熷瘑鐮侀敊璇?);
							return;
						}
						if (this.passwordForm.newpassword != this.passwordForm.repassword) {
							this.$message.error("涓ゆ瀵嗙爜杈撳叆涓嶄竴鑷?);
							return;
						}
						if (this.passwordForm.newpassword == this.passwordForm.password) {
							this.$message.error("鏂板瘑鐮佷笌鍘熷瘑鐮佺浉鍚岋紒");
							return;
						}
						this.sessionForm.password = this.passwordForm.newpassword;
						this.sessionForm.mima = this.passwordForm.newpassword;
						this.$http.post(`${this.userTableName}/update`,this.sessionForm).then(({data})=>{
							if (data && data.code === 0) {
								this.$message({
									message: "淇敼瀵嗙爜鎴愬姛,涓嬫鐧诲綍绯荤粺鐢熸晥",
									type: "success",
									duration: 1500,
									onClose: () => {
									}
								});
								this.setSession()
							} else {
								this.$message.error(data.msg);
							}
						});
					}
				})
			},
			logout() {
				localStorage.clear();
				Vue.http.headers.common['Token'] = "";
				this.$router.push('/index/home');
				this.activeIndex = '0'
				localStorage.setItem('keyPath', this.activeIndex)
				this.$forceUpdate()
				this.$message({
					message: '鐧诲嚭鎴愬姛',
					type: 'success',
					duration: 1500,
				});
			},
			hasBack(name,tablename){
				if(name=='璁㈠崟绠＄悊') {
					return false
				}
				if(name.indexOf('绔犺妭')!=-1&&tablename.substring(0,7)=='chapter') {
					return false
				}
				return true
			},
			menuClick(row,length=1) {
				if(length==1) {
					if(row.tableName=='storeup') {
						localStorage.setItem('storeupType', row.menuJump);
						this.$router.push('/index/storeup');
						return false
					}
					this.$router.push(`/index/${row.tableName}?centerType=1`);
				}
			},
			centerTabEnter(name){
				this.showActive = name?('show' + name):''
			},
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.center-preview {
		padding: 0 10%;
		margin: 10px auto 30px;
		background: none;
		display: flex;
		width: 100%;
		justify-content: flex-start;
		align-items: flex-start;
		position: relative;
		flex-wrap: wrap;
		.center-title {
			padding: 0;
			margin: 10px auto;
			color: #46AC2E;
			font-weight: 600;
			display: block;
			font-size: 30px;
			border-color: #e61f4d;
			line-height: 32px;
			background: url() no-repeat left center / 40px 100%;
			width: 100%;
			border-width: 0 0 0px;
			border-style: solid;
			text-align: center;
		}
		.center-info {
			border: 1px solid #46AC2E;
			padding: 10px 10px 10px 200px;
			margin: 20px 0px 0 0;
			color: #fff;
			display: flex;
			font-size: 15px;
			min-height: 200px;
			flex-wrap: wrap;
			border-radius: 26px;
			box-shadow: none;
			align-content: center;
			background: #46AC2E;
			width: 100%;
			align-items: center;
			position: relative;
			height: auto;
			order: 0;
			.center-info-title {
				color: #fff;
				font-weight: 600;
				font-size: 22px;
				border-color: #efefef;
				line-height: 40px;
				top: 10px;
				left: 20px;
				background: none;
				width: 120px;
				border-width: 0px 0;
				position: absolute;
				border-style: solid;
				text-align: center;
				height: 40px;
			}
			.img-box {
				top: 50px;
				left: 20px;
				width: auto;
				font-size: 0;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				position: absolute;
				border-style: solid;
				height: auto;
				img {
					border-radius: 10px;
					margin: 10px auto;
					object-fit: contain;
					display: block;
					width: 120px;
					border-color: #efefef;
					border-width: 0 0 0px 0;
					border-style: solid;
					height: 120px;
				}
			}
			.info-item1 {
				padding: 0 0px;
				display: inline-block;
				width: 30%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item2 {
				padding: 0 0px;
				display: inline-block;
				width: 30%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item3 {
				padding: 0 0px;
				display: inline-block;
				width: 30%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item4 {
				padding: 0 0px;
				display: inline-block;
				width: 30%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item5 {
				padding: 0 0px;
				display: inline-block;
				width: 30%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
			.info-item6 {
				padding: 0 0px;
				display: inline-block;
				width: 30%;
				border-color: #efefef;
				border-width: 0 0 0px 0;
				line-height: 40px;
				border-style: solid;
				height: auto;
				.icon {
					padding: 0 5px;
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.label {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
				.text {
					color: inherit;
					display: inline-block;
					font-size: inherit;
				}
			}
		}
		.center-box {
			border-radius: 10px;
			padding: 0;
			margin: 20px 0 0;
			background: none;
			flex: 1;
			width: calc(100% - 350px);
			.center-tab-view {
				padding: 13px 20px 0;
				background: #46AC2E;
				display: inline-block;
				width: 100%;
				min-height: 66px;
				border-color: #fff;
				border-width: 0px;
				line-height: 1.5;
				border-style: outset;
				text-align: center;
			}
			.center-tab-view .center-tab {
				border: 0;
				padding: 0 0px;
				margin: 0 10px 10px 0;
				color: #fff;
				font-weight: 500;
				display: inline-block;
				font-size: 16px;
				line-height: 40px;
				float: left;
				background: none;
				position: relative;
				list-style: none;
				min-width: 100px;
				height: 40px;
				.center-second-tab-view {
					padding: 0 10px;
					z-index: 999;
					background: #fff;
					width: 100%;
					position: relative;
					.center-second-tab {
						color: #666;
						width: 100%;
						font-size: 15px;
						border-color: #eee;
						border-width: 0 0 1px;
						border-style: solid;
					}
					.center-second-tab:hover {
						cursor: pointer;
						color: #CA1F27;
					}
				}
			}
			.center-tab-view .center-tab:hover {
				cursor: pointer;
				padding: 0 0px;
				color: #333;
				background: #fff;
				font-weight: 500;
				font-size: 16px;
				line-height: 40px;
				position: relative;
				text-align: center;
				height: 40px;
			}
			.center-tab-view .center-tab.is-active {
				cursor: pointer;
				padding: 0 0px;
				margin: 0 10px 0 0;
				color: #333;
				font-weight: 500;
				font-size: 16px;
				line-height: 40px;
				float: left;
				background: #fff;
				position: relative;
				text-align: center;
				min-width: 100px;
				height: 40px;
			}
			.center-content-box {
				padding: 30px 15% 30px 3%;
				overflow: hidden;
				background: none;
				width: 100%;
				clear: both;
			}
			.center-content-view {
				width: 100%;
			}
			.center-preview-pv {
				.center-item.el-form-item {
					padding: 0px;
					margin: 0 0 24px;
					background: none;
					::v-deep .el-form-item__label {
						padding: 0 10px 0 0;
						color: #666;
						white-space: nowrap;
						font-weight: 500;
						width: 180px;
						font-size: 16px;
						line-height: 40px;
						text-align: right;
					}
					.el-form-item__content {
						margin-left: 180px;
					}
					.el-input {
						width: 100%;
					}
					.el-input ::v-deep .el-input__inner {
						border: 1px solid #000;
						border-radius: 0px;
						padding: 0 12px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-input ::v-deep .el-input__inner[readonly="readonly"] {
						border: 0px solid #ddd;
						cursor: not-allowed;
						border-radius: 4px;
						padding: 0 12px;
						box-shadow: none;
						outline: none;
						color: rgba(85, 85, 127, 1.0);
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-select {
						width: 100%;
					}
					.el-select ::v-deep .el-input__inner {
						border: 1px solid #000;
						border-radius: 0px;
						padding: 0 10px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-select ::v-deep .is-disabled .el-input__inner {
						border: 0px solid #ddd;
						cursor: not-allowed;
						border-radius: 4px;
						padding: 0 10px;
						box-shadow: none;
						outline: none;
						color: #333;
						background: #eee;
						width: 100%;
						font-size: 16px;
						height: 40px;
					}
					.el-date-editor {
						width: 100%;
					}
					.el-date-editor ::v-deep .el-input__inner {
						border: 1px solid #000;
						border-radius: 0px;
						padding: 0 10px 0 30px;
						box-shadow: none;
						outline: none;
						color: #333;
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
						color: #333;
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
						font-size: 15px;
					}
					::v-deep .el-input__inner::placeholder {
						color: #123;
						font-size: 16px;
					}
					.editor {
						border: 0px solid #ddd;
						border-radius: 4px;
						box-shadow: none;
						outline: none;
						color: #333;
						width: 100%;
						font-size: 14px;
						line-height: 32px;
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
					.balance-item {
						display: block;
						.el-input {
							width: auto;
						}
						.el-input ::v-deep .el-input__inner {
							border: 1px solid #eee;
							border-radius: 0px;
							padding: 0 12px;
							box-shadow: none;
							outline: none;
							color: #333;
							background: #fff;
							display: inline-block;
							width: 200px;
							font-size: 16px;
							height: 40px;
						}
						.balanceBtn {
							border: 0px solid #eee;
							cursor: pointer;
							border-radius: 0px;
							padding: 0 15px;
							margin: 0 20px 0 10px;
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
						.balanceBtn:hover {
							opacity: 0.8;
							.icon {
							}
							.text {
							}
						}
					}
				}
				.center-btn-item {
					padding: 0;
					margin: 40px auto 0;
					background: none;
					width: 100%;
					.updateBtn {
						border: 0;
						cursor: pointer;
						border-radius: 0px;
						padding: 0 15px;
						margin: 0 20px 0 0;
						outline: none;
						background: #46AC2E;
						display: inline-block;
						width: auto;
						font-size: 16px;
						line-height: 40px;
						height: 40px;
						.icon {
							color: rgba(255, 255, 255, 1);
						}
						.text {
							color: rgba(255, 255, 255, 1);
						}
					}
					.updateBtn:hover {
						opacity: 0.7;
						.icon {
							color: #fff;
						}
						.text {
							color: #fff;
						}
					}
					.closeBtn {
						border: 0;
						cursor: pointer;
						border-radius: 0px;
						padding: 0 15px;
						margin: 0 20px 0 0;
						outline: none;
						background: #ddd;
						display: inline-block;
						width: auto;
						font-size: 16px;
						line-height: 40px;
						height: 40px;
						.icon {
							color: #333;
						}
						.text {
							color: #333;
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
				.el-date-editor.el-input {
					width: auto;
				}
			}
		}
	}
</style>

