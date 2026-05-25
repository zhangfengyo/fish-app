<template>
	<div class="add-edit-block">
		<el-form
			ref="ruleForm"
			class="add-update-preview"
			:model="ruleForm"
			:rules="rules"
			label-width="140px"
		>
			<el-form-item label="Title" prop="title">
				<el-input v-model="ruleForm.title" :readonly="type === 'info' || ro.title" placeholder="Enter title"></el-input>
			</el-form-item>

			<el-form-item label="Image" prop="picture">
				<template v-if="type !== 'info' && !ro.picture">
					<file-upload
						tip="Upload image"
						action="file/upload"
						:limit="3"
						:multiple="true"
						:disabled="ro.picture"
						:fileUrls="ruleForm.picture || ''"
						@change="pictureUploadChange"
					></file-upload>
				</template>
				<template v-else-if="ruleForm.picture">
					<img
						v-for="(item, index) in previewPictures"
						:key="index"
						class="upload-img"
						:src="item"
						@click="imgPreView(item)"
					/>
				</template>
			</el-form-item>

			<el-form-item label="Fish Category" prop="pinzhongfenlei">
				<template v-if="type !== 'info' && !ro.pinzhongfenlei">
					<el-select v-model="ruleForm.pinzhongfenlei" filterable placeholder="Choose category">
						<el-option
							v-for="(item, index) in pinzhongfenleiOptions"
							:key="index"
							:label="item"
							:value="item"
						></el-option>
					</el-select>
				</template>
				<template v-else>
					<el-input v-model="ruleForm.pinzhongfenlei" readonly></el-input>
				</template>
			</el-form-item>

			<el-form-item label="Publisher" prop="name">
				<el-input v-model="ruleForm.name" :readonly="type === 'info' || ro.name" placeholder="Enter publisher"></el-input>
			</el-form-item>

			<el-form-item class="full-item" label="Intro" prop="introduction">
				<template v-if="type !== 'info' && !ro.introduction">
					<el-input
						v-model="ruleForm.introduction"
						type="textarea"
						:rows="5"
						placeholder="Enter intro"
					></el-input>
				</template>
				<template v-else>
					<div class="text-box">{{ ruleForm.introduction }}</div>
				</template>
			</el-form-item>

			<el-form-item class="full-item" label="Content" prop="content">
				<template v-if="type !== 'info' && !ro.content">
					<editor
						v-model="ruleForm.content"
						class="editor"
						myQuillEditor="content"
						action="file/upload"
					></editor>
				</template>
				<template v-else>
					<div class="text-box ql-snow ql-editor" v-html="ruleForm.content"></div>
				</template>
			</el-form-item>

			<el-form-item class="full-item btn-row">
				<el-button v-if="type !== 'info'" type="primary" @click="onSubmit">Submit</el-button>
				<el-button v-if="type !== 'info'" @click="back">Cancel</el-button>
				<el-button v-if="type === 'info'" @click="back">Back</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
export default {
	props: ['parent'],
	data() {
		return {
			id: '',
			type: '',
			ro: {
				title: false,
				introduction: false,
				picture: false,
				pinzhongfenlei: false,
				content: false,
				name: false,
				headportrait: false,
			},
			ruleForm: {
				title: '',
				introduction: '',
				picture: '',
				pinzhongfenlei: '',
				content: '',
				name: 'admin',
				headportrait: '',
			},
			pinzhongfenleiOptions: [],
			rules: {
				title: [
					{ required: true, message: 'Title is required', trigger: 'blur' },
				],
				picture: [
					{ required: true, message: 'Image is required', trigger: 'change' },
				],
				pinzhongfenlei: [
					{ required: true, message: 'Fish category is required', trigger: 'change' },
				],
				content: [
					{ required: true, message: 'Content is required', trigger: 'blur' },
				],
			},
		}
	},
	computed: {
		previewPictures() {
			if (!this.ruleForm.picture) {
				return []
			}
			if (this.ruleForm.picture.substring(0, 4) === 'http') {
				return this.ruleForm.picture.split(',w').length > 1
					? [this.ruleForm.picture]
					: [this.ruleForm.picture.split(',')[0]]
			}
			return this.ruleForm.picture.split(',').filter(Boolean).map(item => this.$base.url + item)
		},
	},
	methods: {
		imgPreView(url) {
			this.parent.imgPreView(url)
		},
		init(id, type) {
			if (id) {
				this.id = id
				this.type = type
			}
			if (this.type === 'info' || this.type === 'else' || this.type === 'msg') {
				this.info(id)
			} else if (this.type === 'logistics') {
				Object.keys(this.ro).forEach(key => {
					this.ro[key] = true
				})
				this.info(id)
			} else if (this.type === 'cross') {
				const obj = this.$storage.getObj('crossObj')
				Object.keys(obj || {}).forEach(key => {
					if (Object.prototype.hasOwnProperty.call(this.ruleForm, key)) {
						this.ruleForm[key] = obj[key]
						if (Object.prototype.hasOwnProperty.call(this.ro, key)) {
							this.ro[key] = true
						}
					}
				})
				this.ruleForm.name = 'admin'
			}
			this.loadOptions()
		},
		loadOptions() {
			this.$http({
				url: 'option/pinzhongfenlei/pinzhongfenlei',
				method: 'get',
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.pinzhongfenleiOptions = data.data || []
				} else if (data) {
					this.$message.error(data.msg)
				}
			})
		},
		async info(id) {
			await this.$http({
				url: `news/info/${id}`,
				method: 'get',
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.ruleForm = Object.assign({}, this.ruleForm, data.data)
					if (this.ruleForm.content) {
						const reg = new RegExp('../../../upload', 'g')
						this.ruleForm.content = this.ruleForm.content.replace(reg, '../../../guanshangl0g62fht/upload')
					}
				} else if (data) {
					this.$message.error(data.msg)
				}
			})
		},
		async onSubmit() {
			await this.$refs.ruleForm.validate(async valid => {
				if (!valid) {
					return false
				}
				const payload = Object.assign({}, this.ruleForm)
				if (payload.picture != null) {
					payload.picture = payload.picture.replace(new RegExp(this.$base.url, 'g'), '')
				}
				if (payload.headportrait != null) {
					payload.headportrait = payload.headportrait.replace(new RegExp(this.$base.url, 'g'), '')
				}
				if (!payload.id) {
					delete payload.userid
				}
				if (this.type === 'cross') {
					const statusColumnName = this.$storage.get('statusColumnName')
					const statusColumnValue = this.$storage.get('statusColumnValue')
					if (statusColumnName && !statusColumnName.startsWith('[')) {
						const obj = this.$storage.getObj('crossObj')
						Object.keys(obj || {}).forEach(key => {
							if (key === statusColumnName) {
								obj[key] = statusColumnValue
							}
						})
						const table = this.$storage.get('crossTable')
						await this.$http({
							url: `${table}/update`,
							method: 'post',
							data: obj,
						}).then(() => {})
					}
				}
				await this.$http({
					url: `news/${!payload.id ? 'save' : 'update'}`,
					method: 'post',
					data: payload,
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.$message({
							message: 'Saved',
							type: 'success',
							duration: 1500,
							onClose: () => {
								this.parent.showFlag = true
								this.parent.addOrUpdateFlag = false
								this.parent.newsCrossAddOrUpdateFlag = false
								this.parent.search()
							},
						})
					} else if (data) {
						this.$message.error(data.msg)
					}
				})
			})
		},
		back() {
			this.parent.showFlag = true
			this.parent.addOrUpdateFlag = false
			this.parent.newsCrossAddOrUpdateFlag = false
		},
		pictureUploadChange(fileUrls) {
			this.ruleForm.picture = fileUrls
		},
	},
}
</script>

<style lang="scss" scoped>
.add-edit-block {
	padding: 10px;
}

.add-update-preview {
	border-radius: 10px;
	padding: 30px 8%;
	background: #fff;
	display: flex;
	flex-wrap: wrap;
}

.add-update-preview ::v-deep .el-form-item {
	width: 48%;
	margin: 0 0 20px;
}

.add-update-preview ::v-deep .el-form-item__content {
	margin-left: 140px !important;
}

.add-update-preview ::v-deep .el-input,
.add-update-preview ::v-deep .el-select {
	width: 100%;
}

.full-item {
	width: 100% !important;
}

.upload-img {
	width: 100px;
	height: 100px;
	object-fit: cover;
	border-radius: 6px;
	margin-right: 12px;
	cursor: pointer;
	border: 1px solid #e5e7eb;
}

.text-box {
	width: 100%;
	min-height: 40px;
	line-height: 1.8;
	color: #666;
	word-break: break-all;
}

.editor {
	width: 100%;
}

.editor ::v-deep .ql-toolbar {
	border: 1px solid #e5e7eb;
	border-radius: 4px 4px 0 0;
}

.editor ::v-deep .ql-container {
	border: 1px solid #e5e7eb;
	border-top: 0;
	min-height: 220px;
	border-radius: 0 0 4px 4px;
}

.btn-row ::v-deep .el-form-item__content {
	margin-left: 0 !important;
	display: flex;
	justify-content: center;
}
</style>

