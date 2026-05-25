<template>
	<div :style='{"alignContent":"flex-start","padding":"0 10%","margin":"0px auto","alignItems":"flex-start","flexWrap":"wrap","background":"none","display":"flex","width":"100%","position":"relative"}'>
		<div class="section-title" :style='{"padding":"0","borderColor":"#e61f4d","margin":"10px auto","color":"#46AC2E","textAlign":"center","display":"block","background":"url() no-repeat left center / 40px 100%","borderWidth":"0 0 0px","width":"100%","lineHeight":"32px","fontSize":"30px","borderStyle":"solid","fontWeight":"600"}'>浜ゆ祦璁哄潧</div>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-fanhui01"></span>
				<span class="text">杩斿洖</span>
			</el-button>
		</div>
		<el-form class="add-update-preview" :model="form" :rules="rules" ref="form" label-width="200px">
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="标题" prop="title">
				<el-input v-model="form.title" placeholder="请输入标题"></el-input>
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="封面图" prop="cover">
				<file-upload
					tip="点击上传封面图"
					action="file/upload"
					:limit="3"
					:multiple="true"
					:fileUrls="form.cover?form.cover:''"
					@change="coverUploadChange"
					></file-upload>
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="类型" prop="isdone">
				<el-radio-group v-model="form.isdone">
					<el-radio label="开放">公开</el-radio>
					<el-radio label="关闭">私人</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="是否匿名" prop="isanon">
				<el-radio-group v-model="form.isanon">
					<el-radio :label="1">是</el-radio>
					<el-radio :label="0">否</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item :style='{"border":"1px solid #46AC2E","padding":"0","margin":"0 0 24px","background":"none"}' label="内容" prop="content">
				<editor
					myQuillEditor="content"
					:style='{"border":"0px solid #ddd","boxShadow":"none","outline":"none","margin":"30px 0 0 0","color":"#333","borderRadius":"4px","width":"100%","clear":"both","lineHeight":"32px","fontSize":"14px"}'
					v-model="form.content" 
					class="editor" 
					action="file/upload">
				</editor>
			</el-form-item>
			<el-form-item :style='{"width":"100%","padding":"0","margin":"20px auto","textAlign":"center"}'>
				<el-button :style='{"border":"1px solid #46AC2E","cursor":"pointer","padding":"0 15px","margin":"0 20px 0 0","outline":"none","borderRadius":"20px","background":"#46AC2E","display":"inline-block","width":"auto","lineHeight":"40px","fontSize":"14px","height":"40px"}' type="primary" @click="submitForm('form')">
					<span class="icon iconfont icon-xiugai17" :style='{"color":"#fff"}'></span>
					<span class="text" :style='{"color":"#fff","fontSize":"16px"}'>{{this.isEdit ? '淇敼' : '鍙戝竷甯栧瓙'}}</span>
				</el-button>
				<el-button :style='{"border":"1px solid #000","cursor":"pointer","padding":"0 15px","margin":"0 20px 0 0","outline":"none","borderRadius":"20px","background":"none","display":"inline-block","width":"auto","lineHeight":"40px","fontSize":"14px","height":"40px"}' @click="resetForm('form')">
					<span class="icon iconfont icon-shanchu8" :style='{"color":"#000","fontSize":"18px"}'></span>
					<span class="text" :style='{"color":"#000","fontSize":"16px"}'>閲嶇疆</span>
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	export default {
		//鏁版嵁闆嗗悎
		data() {
			return {
				form: {
					title: '',
					isdone: '开放',
					content: '',
					parentid: 0,
					userid: Number(localStorage.getItem('frontUserid')),
					username: localStorage.getItem('username'),
					toptime: '',
					cover: '',
					isanon: 0,
				},
				editorOption: {
					modules: {
						toolbar: [
							["bold", "italic", "underline", "strike"],
							["blockquote", "code-block"],
							[{ header: 1 }, { header: 2 }],
							[{ list: "ordered" }, { list: "bullet" }],
							[{ script: "sub" }, { script: "super" }],
							[{ indent: "-1" }, { indent: "+1" }],
							[{ direction: "rtl" }],
							[{ size: ["small", false, "large", "huge"] }],
							[{ header: [1, 2, 3, 4, 5, 6, false] }],
							[{ color: [] }, { background: [] }],
							[{ font: [] }],
							[{ align: [] }],
							["clean"],
							["image", "video"]
						]
					}
				},
				isEdit: false,
				rules: {
					title: [
						{ required: true, message: '请输入标题', trigger: 'blur' }
					],
					cover: [
						{ required: true, message: '璇蜂笂浼犲皝闈㈠浘', trigger: 'blur' }
					],
					content: [
						{ required: true, message: '请输入内容', trigger: 'blur' }
					],
				},
			}
		},
		created() {
			if (this.$route.query.id != undefined) {
				this.isEdit = true;
				this.$http.get('forum/detail/' + this.$route.query.id,{}).then(res=>{
					if(res.data.code==0){
						this.form = res.data.data
					}
				})
			}
		},
		//鏂规硶闆嗗悎
		methods: {
			// 杩斿洖鎸夐挳
			backClick(){
				history.back()
			},
			onEditorReady(editor) {
				editor.root.setAttribute('data-placeholder', "璇疯緭鍏ュ唴瀹?..");
			},
			coverUploadChange(fileUrls) {
				this.form.cover = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
			},
			submitForm(formName) {
				this.$refs[formName].validate((valid) => {
					if (valid) {
						if(!this.isEdit){
							this.form.toptime = this.getCurDateTime()
						}
						if(this.form.cover==''){
							this.$message.error('璇蜂笂浼犲皝闈㈠浘')
							return false
						}
						this.$http.post(`forum/${this.isEdit ? 'update' : 'add'}`, this.form).then(res => {
							if (res.data.code === 0) {
								this.$message({
									message: `${this.isEdit ? '淇敼' : '鍙戝竷'}鎴愬姛`,
									type: 'success',
									duration: 1500,
									onClose: () => {
										if (this.isEdit) {
											this.$router.push('/index/myForumList');
										} else {
											this.$router.push('/index/forum');
										}
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
				this.form = {
					title: '',
					isdone: '开放',
					content: '',
					parentid: 0,
					userid: Number(localStorage.getItem('frontUserid')),
					username: localStorage.getItem('username'),
					toptime: '',
					cover: '',
					isanon: 0
				}
			}
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
	.add-update-preview ::v-deep .el-upload__tip {
		color: #666;
		font-size: 16px;
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
		font-size: 14px;
		line-height: 1.8;
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
	.add-update-preview ::v-deep .el-upload__tip {
		color: #666;
		font-size: 16px;
	}
</style>

