<template>
	<div class="assistant-page">
		<section class="hero">
			<div>
				<div class="eyebrow">{{ $t({ 'zh-CN': '智能助手运营台', 'en-US': 'Assistant Console' }) }}</div>
				<h1>{{ $t({ 'zh-CN': '智能助手配置与对话记录', 'en-US': 'Assistant Settings & Conversation Records' }) }}</h1>
				<p>{{ $t({ 'zh-CN': '在这里统一维护助手提示词、模型连接配置，并查看用户与助手的历史对话。', 'en-US': 'Manage prompts, model connection settings, and assistant conversations here.' }) }}</p>
			</div>
			<div class="hero-stats">
				<div class="hero-card">
					<span class="hero-label">{{ $t({ 'zh-CN': '对话记录', 'en-US': 'Records' }) }}</span>
					<strong>{{ records.length }}</strong>
				</div>
				<div class="hero-card">
					<span class="hero-label">{{ $t({ 'zh-CN': '待回复', 'en-US': 'Pending' }) }}</span>
					<strong>{{ pendingCount }}</strong>
				</div>
				<div class="hero-card">
					<span class="hero-label">{{ $t({ 'zh-CN': '已回复', 'en-US': 'Replied' }) }}</span>
					<strong>{{ repliedCount }}</strong>
				</div>
			</div>
		</section>

		<el-tabs v-model="activeTab" class="assistant-tabs">
			<el-tab-pane :label="$t({ 'zh-CN': '助手配置', 'en-US': 'Settings' })" name="settings">
				<div class="grid-2">
					<section class="panel">
						<h2 class="panel-title">{{ $t({ 'zh-CN': '助手话术配置', 'en-US': 'Assistant Prompt Settings' }) }}</h2>
						<el-form label-width="130px" class="settings-form">
							<el-form-item :label="$t({ 'zh-CN': '助手名称', 'en-US': 'Assistant Name' })">
								<el-input v-model="settingsForm.assistantName" />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '欢迎标题', 'en-US': 'Welcome Title' })">
								<el-input v-model="settingsForm.welcomeTitle" />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '欢迎说明', 'en-US': 'Welcome Description' })">
								<el-input v-model="settingsForm.welcomeDescription" type="textarea" :rows="3" />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '快捷问题一', 'en-US': 'Quick Prompt 1' })">
								<el-input v-model="settingsForm.quickPromptOne" />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '快捷问题二', 'en-US': 'Quick Prompt 2' })">
								<el-input v-model="settingsForm.quickPromptTwo" />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '回复风格', 'en-US': 'Reply Style' })">
								<el-input v-model="settingsForm.replyStyle" />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '系统提示词', 'en-US': 'System Prompt' })">
								<el-input v-model="settingsForm.systemPrompt" type="textarea" :rows="6" />
							</el-form-item>
							<el-form-item>
								<el-button @click="loadSettings">{{ $t({ 'zh-CN': '重置', 'en-US': 'Reset' }) }}</el-button>
								<el-button type="primary" :loading="savingSettings" @click="saveSettings">{{ $t({ 'zh-CN': '保存助手配置', 'en-US': 'Save Prompt Settings' }) }}</el-button>
							</el-form-item>
						</el-form>
					</section>

					<section class="panel">
						<h2 class="panel-title">{{ $t({ 'zh-CN': '模型连接配置', 'en-US': 'Model Connection Settings' }) }}</h2>
						<el-form label-width="120px" class="settings-form">
							<el-form-item :label="$t({ 'zh-CN': 'API URL', 'en-US': 'API URL' })">
								<el-input v-model="modelForm.apiUrl" />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': 'API Key', 'en-US': 'API Key' })">
								<el-input v-model="modelForm.apiKey" show-password />
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '模型名称', 'en-US': 'Model Name' })">
								<el-select
									v-model="modelForm.modelName"
									filterable
									allow-create
									default-first-option
									clearable
									class="full-width"
									:loading="loadingModels"
									:placeholder="$t({ 'zh-CN': '可手动输入，或先获取模型列表', 'en-US': 'Type manually or fetch models first' })"
								>
									<el-option
										v-for="item in availableModels"
										:key="item"
										:label="item"
										:value="item"
									/>
								</el-select>
								<div class="form-tip">
									{{ $t({ 'zh-CN': '这里保存的是管理员填写的 URL、API Key 和模型名，智能助手运行时将直接读取这份配置。', 'en-US': 'The assistant reads the admin-saved URL, API key, and model name directly at runtime.' }) }}
								</div>
							</el-form-item>
							<el-form-item :label="$t({ 'zh-CN': '测试问题', 'en-US': 'Test Prompt' })">
								<el-input v-model="modelForm.testPrompt" type="textarea" :rows="4" />
							</el-form-item>
							<el-form-item>
								<el-button @click="loadModelConfig">{{ $t({ 'zh-CN': '重置', 'en-US': 'Reset' }) }}</el-button>
								<el-button type="info" :loading="loadingModels" @click="fetchAvailableModels">{{ $t({ 'zh-CN': '获取可用模型', 'en-US': 'Fetch Models' }) }}</el-button>
								<el-button type="primary" :loading="savingModel" @click="saveModelConfig">{{ $t({ 'zh-CN': '保存模型配置', 'en-US': 'Save Model Settings' }) }}</el-button>
								<el-button type="success" :loading="testingModel" @click="testModelConfig">{{ $t({ 'zh-CN': '测试模型', 'en-US': 'Test Model' }) }}</el-button>
							</el-form-item>
						</el-form>

						<div class="test-box" v-if="testResult">
							<div class="test-title">{{ $t({ 'zh-CN': '测试结果', 'en-US': 'Test Result' }) }}</div>
							<div class="test-content">{{ testResult }}</div>
						</div>
					</section>
				</div>
			</el-tab-pane>

			<el-tab-pane :label="$t({ 'zh-CN': '对话记录', 'en-US': 'Conversation Records' })" name="records">
				<section class="panel">
					<div class="record-toolbar">
						<el-input
							v-model="searchText"
							clearable
							class="record-search"
							:placeholder="$t({ 'zh-CN': '搜索用户或问题内容', 'en-US': 'Search user or message' })"
						/>
						<el-select v-model="replyFilter" clearable :placeholder="$t({ 'zh-CN': '回复状态', 'en-US': 'Reply Status' })">
							<el-option :label="$t({ 'zh-CN': '已回复', 'en-US': 'Replied' })" :value="0" />
							<el-option :label="$t({ 'zh-CN': '待回复', 'en-US': 'Pending' })" :value="1" />
						</el-select>
						<el-button @click="loadRecords">{{ $t({ 'zh-CN': '刷新', 'en-US': 'Refresh' }) }}</el-button>
					</div>

					<el-table :data="filteredRecords" border v-loading="loadingRecords" class="record-table">
						<el-table-column prop="uname" :label="$t({ 'zh-CN': '用户', 'en-US': 'User' })" width="140" />
						<el-table-column :label="$t({ 'zh-CN': '问题', 'en-US': 'Question' })" min-width="260">
							<template slot-scope="{ row }">
								<div class="cell-block">{{ row.ask || '-' }}</div>
							</template>
						</el-table-column>
						<el-table-column :label="$t({ 'zh-CN': '回复', 'en-US': 'Reply' })" min-width="320">
							<template slot-scope="{ row }">
								<div class="cell-block">{{ row.reply || '-' }}</div>
							</template>
						</el-table-column>
						<el-table-column :label="$t({ 'zh-CN': '状态', 'en-US': 'Status' })" width="110">
							<template slot-scope="{ row }">
								<el-tag :type="Number(row.isreply) === 1 ? 'warning' : 'success'">
									{{ Number(row.isreply) === 1 ? $t({ 'zh-CN': '待回复', 'en-US': 'Pending' }) : $t({ 'zh-CN': '已回复', 'en-US': 'Replied' }) }}
								</el-tag>
							</template>
						</el-table-column>
						<el-table-column prop="addtime" :label="$t({ 'zh-CN': '时间', 'en-US': 'Time' })" width="170" />
						<el-table-column :label="$t({ 'zh-CN': '操作', 'en-US': 'Actions' })" width="170">
							<template slot-scope="{ row }">
								<el-button type="text" @click="viewRecord(row)">{{ $t({ 'zh-CN': '查看', 'en-US': 'View' }) }}</el-button>
								<el-button type="text" class="danger-action" @click="deleteRecord(row)">{{ $t({ 'zh-CN': '删除', 'en-US': 'Delete' }) }}</el-button>
							</template>
						</el-table-column>
					</el-table>
				</section>
			</el-tab-pane>
		</el-tabs>

		<el-dialog :visible.sync="recordVisible" width="620px" :append-to-body="true">
			<div slot="title" class="record-title">{{ $t({ 'zh-CN': '对话详情', 'en-US': 'Conversation Detail' }) }}</div>
			<div class="record-detail" v-if="currentRecord">
				<div class="detail-item">
					<span class="detail-label">{{ $t({ 'zh-CN': '用户', 'en-US': 'User' }) }}</span>
					<div class="detail-value">{{ currentRecord.uname || '-' }}</div>
				</div>
				<div class="detail-item">
					<span class="detail-label">{{ $t({ 'zh-CN': '问题', 'en-US': 'Question' }) }}</span>
					<div class="detail-value detail-long">{{ currentRecord.ask || '-' }}</div>
				</div>
				<div class="detail-item">
					<span class="detail-label">{{ $t({ 'zh-CN': '回复', 'en-US': 'Reply' }) }}</span>
					<div class="detail-value detail-long">{{ currentRecord.reply || '-' }}</div>
				</div>
				<div class="detail-item">
					<span class="detail-label">{{ $t({ 'zh-CN': '时间', 'en-US': 'Time' }) }}</span>
					<div class="detail-value">{{ currentRecord.addtime || '-' }}</div>
				</div>
			</div>
		</el-dialog>
	</div>
</template>

<script>
const defaultSettings = () => ({
	assistantName: '观赏鱼助手Pro',
	welcomeTitle: '你好，我是你的观赏鱼顾问。',
	welcomeDescription: '我会用更稳定的中文，回答饲养、鱼种和系统使用问题。',
	quickPromptOne: '红龙鱼适合什么水温？',
	quickPromptTwo: '新手养鱼要先准备什么？',
	replyStyle: '专业、温和、偏观赏鱼顾问口吻',
	systemPrompt: '遇到不确定的问题时，请明确说明仅供参考，并尽量给出分点建议。',
})

const defaultModelSettings = () => ({
	apiUrl: 'https://ark.cn-beijing.volces.com/api/v3/chat/completions',
	modelName: 'doubao-seed-2-0-code-preview-260215',
	apiKey: '',
	testPrompt: '请用中文回复：模型连接正常。',
})

export default {
	data() {
		return {
			activeTab: 'settings',
			savingSettings: false,
			savingModel: false,
			testingModel: false,
			loadingModels: false,
			loadingRecords: false,
			settingsForm: defaultSettings(),
			modelForm: defaultModelSettings(),
			availableModels: [],
			testResult: '',
			records: [],
			searchText: '',
			replyFilter: '',
			recordVisible: false,
			currentRecord: null,
		}
	},
	computed: {
		filteredRecords() {
			return this.records.filter(item => {
				const hitText = !this.searchText
					|| (item.uname || '').includes(this.searchText)
					|| (item.ask || '').includes(this.searchText)
					|| (item.reply || '').includes(this.searchText)
				const hitReply = this.replyFilter === '' || this.replyFilter === null || Number(item.isreply) === Number(this.replyFilter)
				return hitText && hitReply
			})
		},
		pendingCount() {
			return this.records.filter(item => Number(item.isreply) === 1).length
		},
		repliedCount() {
			return this.records.filter(item => Number(item.isreply) !== 1).length
		},
	},
	async created() {
		await Promise.all([this.loadSettings(), this.loadModelConfig(), this.loadRecords()])
	},
	methods: {
		async loadSettings() {
			const res = await this.$api.get('/assistant/config')
			this.settingsForm = Object.assign(defaultSettings(), (res.data && res.data.data) || {})
		},
		async saveSettings() {
			this.savingSettings = true
			try {
				const res = await this.$api.post('/assistant/config/save', this.settingsForm)
				this.settingsForm = Object.assign(defaultSettings(), (res.data && res.data.data) || this.settingsForm)
				this.$message.success(this.$t({ 'zh-CN': '助手配置已保存。', 'en-US': 'Assistant settings saved.' }))
			} finally {
				this.savingSettings = false
			}
		},
		async loadModelConfig() {
			const res = await this.$api.get('/assistant/model-config')
			this.modelForm = Object.assign(defaultModelSettings(), (res.data && res.data.data) || {})
			this.syncModelOption(this.modelForm.modelName)
		},
		async saveModelConfig() {
			this.savingModel = true
			try {
				const res = await this.$api.post('/assistant/model-config/save', this.modelForm)
				this.modelForm = Object.assign(defaultModelSettings(), (res.data && res.data.data) || this.modelForm)
				this.syncModelOption(this.modelForm.modelName)
				this.$message.success(this.$t({ 'zh-CN': '模型配置已保存。', 'en-US': 'Model settings saved.' }))
			} finally {
				this.savingModel = false
			}
		},
		async fetchAvailableModels() {
			if (!this.modelForm.apiUrl || !this.modelForm.apiKey) {
				this.$message.warning(this.$t({ 'zh-CN': '请先填写 API URL 和 API Key。', 'en-US': 'Please fill in API URL and API key first.' }))
				return
			}
			this.loadingModels = true
			try {
				const res = await this.$api.post('/assistant/model-config/models', this.modelForm, { timeout: 45000 })
				const list = (res.data && res.data.data) || []
				this.availableModels = Array.isArray(list) ? list.slice() : []
				if (this.availableModels.length > 0) {
					if (!this.modelForm.modelName || !this.availableModels.includes(this.modelForm.modelName)) {
						this.modelForm.modelName = this.availableModels[0]
					}
					this.$message.success(this.$t({ 'zh-CN': '已获取可用模型列表。', 'en-US': 'Available models loaded.' }))
				} else {
					this.$message.warning(this.$t({ 'zh-CN': '接口可访问，但未返回模型列表。', 'en-US': 'The endpoint is reachable but returned no models.' }))
				}
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '获取模型失败，请检查 URL、Key 和模型服务是否支持 /models 接口。', 'en-US': 'Failed to fetch models. Check the URL, key, and whether the provider supports /models.' }))
			} finally {
				this.loadingModels = false
			}
		},
		async testModelConfig() {
			this.testingModel = true
			this.testResult = ''
			try {
				const res = await this.$api.post('/assistant/model-config/test', this.modelForm, { timeout: 45000 })
				this.testResult = (res.data && res.data.data) || this.$t({ 'zh-CN': '模型测试完成，但未返回内容。', 'en-US': 'Model test finished without a response.' })
				this.$message.success(this.$t({ 'zh-CN': '模型测试完成。', 'en-US': 'Model test completed.' }))
			} catch (e) {
				this.testResult = this.$t({ 'zh-CN': '模型测试失败，请检查 API URL、模型名和 Key。', 'en-US': 'Model test failed. Please check API URL, model name, and key.' })
				this.$message.error(this.testResult)
			} finally {
				this.testingModel = false
			}
		},
		async loadRecords() {
			this.loadingRecords = true
			try {
				const res = await this.$api.get('/chat/page', {
					params: {
						page: 1,
						limit: 500,
						sort: 'id',
						order: 'desc',
					},
				})
				this.records = (res.data && res.data.data && res.data.data.list) || []
			} finally {
				this.loadingRecords = false
			}
		},
		syncModelOption(modelName) {
			if (!modelName) {
				return
			}
			if (!this.availableModels.includes(modelName)) {
				this.availableModels = [modelName].concat(this.availableModels)
			}
		},
		viewRecord(row) {
			this.currentRecord = Object.assign({}, row)
			this.recordVisible = true
		},
		async deleteRecord(row) {
			await this.$confirm(
				this.$t({ 'zh-CN': '确定删除这条对话记录吗？', 'en-US': 'Delete this conversation record?' }),
				this.$t({ 'zh-CN': '提示', 'en-US': 'Confirm' }),
				{ type: 'warning' }
			)
			await this.$api.post('/chat/delete', [row.id])
			this.$message.success(this.$t({ 'zh-CN': '删除成功。', 'en-US': 'Deleted.' }))
			await this.loadRecords()
		},
	},
}
</script>

<style scoped>
.assistant-page {
	display: grid;
	gap: 22px;
}

.hero {
	display: grid;
	grid-template-columns: 1.5fr 1fr;
	gap: 18px;
	padding: 28px;
	border-radius: 28px;
	background: linear-gradient(135deg, rgba(7, 53, 83, 0.96) 0%, rgba(17, 113, 165, 0.94) 54%, rgba(95, 190, 235, 0.88) 100%);
	box-shadow: 0 28px 60px rgba(15, 111, 168, 0.16);
	color: #fff;
}

.eyebrow {
	display: inline-flex;
	padding: 6px 12px;
	border-radius: 999px;
	background: rgba(255, 255, 255, 0.14);
	border: 1px solid rgba(255, 255, 255, 0.18);
	font-size: 12px;
	font-weight: 700;
	letter-spacing: 0.8px;
	margin-bottom: 16px;
}

.hero h1 {
	margin: 0 0 10px;
	font-size: 34px;
}

.hero p {
	margin: 0;
	line-height: 1.8;
	color: rgba(236, 247, 255, 0.86);
}

.hero-stats {
	display: grid;
	grid-template-columns: repeat(3, minmax(0, 1fr));
	gap: 14px;
	align-content: start;
}

.hero-card {
	padding: 18px;
	border-radius: 20px;
	background: rgba(255, 255, 255, 0.12);
	border: 1px solid rgba(255, 255, 255, 0.16);
}

.hero-label {
	display: block;
	font-size: 12px;
	color: rgba(236, 247, 255, 0.76);
	margin-bottom: 8px;
}

.hero-card strong {
	font-size: 28px;
}

.assistant-tabs :deep(.el-tabs__header) {
	margin-bottom: 18px;
}

.grid-2 {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 18px;
}

.panel {
	padding: 22px;
	background: #fff;
	border-radius: 24px;
	border: 1px solid rgba(15, 111, 168, 0.08);
	box-shadow: 0 18px 40px rgba(15, 111, 168, 0.08);
}

.panel-title {
	margin: 0 0 18px;
	font-size: 22px;
	color: #163246;
}

.full-width {
	width: 100%;
}

.form-tip {
	margin-top: 8px;
	font-size: 12px;
	line-height: 1.6;
	color: #6c7f8d;
}

.record-toolbar {
	display: flex;
	gap: 12px;
	align-items: center;
	margin-bottom: 18px;
}

.record-search {
	max-width: 280px;
}

.record-table :deep(.cell) {
	line-height: 1.7;
}

.cell-block {
	white-space: pre-wrap;
	word-break: break-word;
	color: #365062;
}

.danger-action {
	color: #e35b5b;
}

.record-title {
	font-size: 18px;
	font-weight: 800;
	color: #163246;
}

.record-detail {
	display: grid;
	gap: 16px;
}

.detail-item {
	display: grid;
	gap: 8px;
}

.detail-label {
	font-size: 12px;
	font-weight: 700;
	color: #6c7f8d;
}

.detail-value {
	padding: 12px 14px;
	border-radius: 16px;
	background: #f5fbff;
	color: #163246;
}

.detail-long {
	white-space: pre-wrap;
	line-height: 1.8;
	word-break: break-word;
}

.test-box {
	margin-top: 18px;
	padding: 16px;
	border-radius: 18px;
	background: #f5fbff;
	border: 1px solid rgba(15, 111, 168, 0.08);
}

.test-title {
	font-size: 14px;
	font-weight: 700;
	color: #163246;
	margin-bottom: 8px;
}

.test-content {
	color: #365062;
	white-space: pre-wrap;
	word-break: break-word;
	line-height: 1.8;
}

@media (max-width: 1080px) {
	.hero {
		grid-template-columns: 1fr;
	}
	.hero-stats {
		grid-template-columns: repeat(3, minmax(0, 1fr));
	}
	.grid-2 {
		grid-template-columns: 1fr;
	}
	.record-toolbar {
		flex-wrap: wrap;
	}
}
</style>
