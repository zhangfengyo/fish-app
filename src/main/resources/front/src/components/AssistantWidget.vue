<template>
	<div class="assistant-widget">
		<button
			class="assistant-launcher"
			type="button"
			data-testid="assistant-launcher"
			aria-label="assistant-launcher"
			@click="openAssistant"
		>
			<span class="assistant-launcher-icon">AI</span>
			<span class="assistant-launcher-text">{{ $t({ 'zh-CN': '智能助手', 'en-US': 'Assistant' }) }}</span>
		</button>

		<el-dialog
			:visible.sync="visible"
			:append-to-body="true"
			custom-class="assistant-dialog"
			width="500px"
			:before-close="closeAssistant"
		>
			<div slot="title" class="assistant-title-bar">
				<div class="assistant-title">
					<div class="assistant-title-main">{{ assistantConfig.assistantName }}</div>
					<div class="assistant-title-sub">{{ $t({ 'zh-CN': '简易版助手，当前不含知识库检索', 'en-US': 'Simple assistant without RAG for now' }) }}</div>
				</div>
				<el-button
					class="assistant-setting-trigger"
					size="mini"
					icon="el-icon-setting"
					@click="openSettings"
				>
					{{ $t({ 'zh-CN': '设置', 'en-US': 'Settings' }) }}
				</el-button>
			</div>

			<div class="assistant-shell">
				<div ref="messagePane" class="assistant-messages">
					<template v-if="messages.length">
						<div v-for="item in messages" :key="item.id" class="message-row">
							<div v-if="item.ask" class="bubble-row bubble-row-user">
								<div class="bubble bubble-user">
									<div class="bubble-meta">{{ displayTime(item.addtime) }}</div>
									<div class="bubble-text">{{ item.ask }}</div>
								</div>
								<img class="avatar" :src="userAvatar" alt="user" />
							</div>
							<div v-if="item.reply" class="bubble-row bubble-row-assistant">
								<img class="avatar" :src="assistantAvatar" alt="assistant" />
								<div class="bubble bubble-assistant">
									<div class="bubble-meta">{{ displayTime(item.addtime) }}</div>
									<div class="bubble-text">{{ item.reply }}</div>
								</div>
							</div>
						</div>
					</template>
					<div v-else class="assistant-empty">
						<div class="assistant-empty-title">{{ assistantConfig.welcomeTitle }}</div>
						<div class="assistant-empty-desc">{{ assistantConfig.welcomeDescription }}</div>
					</div>
					<div v-if="loading || streamingReply" class="bubble-row bubble-row-assistant">
						<img class="avatar" :src="assistantAvatar" alt="assistant" />
						<div class="bubble bubble-assistant" :class="{ 'bubble-loading': loading && !streamingReply }">
							<div class="bubble-meta">{{ streamingMeta }}</div>
							<div v-if="streamingReply" class="bubble-text">
								{{ streamingReply }}<span v-if="loading" class="streaming-cursor"></span>
							</div>
							<template v-else>
								<span></span>
								<span></span>
								<span></span>
							</template>
						</div>
					</div>
				</div>

				<div class="assistant-quick">
					<button type="button" @click="fillQuickPrompt(assistantConfig.quickPromptOne)">{{ assistantConfig.quickPromptOne }}</button>
					<button type="button" @click="fillQuickPrompt(assistantConfig.quickPromptTwo)">{{ assistantConfig.quickPromptTwo }}</button>
				</div>

				<div class="assistant-compose">
					<el-input
						v-model="form.ask"
						type="textarea"
						:rows="3"
						resize="none"
						:placeholder="$t({ 'zh-CN': '请输入你的问题，例如：红龙鱼适合什么水温？', 'en-US': 'Ask a question, e.g. What water temperature suits red dragon fish?' })"
						@keydown.enter.native="handleEnter"
					/>
					<div class="assistant-compose-actions">
						<el-button @click="closeAssistant">{{ $t({ 'zh-CN': '关闭', 'en-US': 'Close' }) }}</el-button>
						<el-button
							type="primary"
							:loading="loading"
							data-testid="assistant-send"
							@click="sendMessage"
						>
							{{ $t({ 'zh-CN': '发送', 'en-US': 'Send' }) }}
						</el-button>
					</div>
				</div>
			</div>
		</el-dialog>

		<el-dialog
			:visible.sync="settingsVisible"
			:append-to-body="true"
			width="580px"
			custom-class="assistant-settings-dialog"
		>
			<div slot="title" class="assistant-settings-title">
				{{ $t({ 'zh-CN': '智能助手设置', 'en-US': 'Assistant Settings' }) }}
			</div>
			<el-form label-width="120px" class="assistant-settings-form">
				<el-form-item :label="$t({ 'zh-CN': '助手名称', 'en-US': 'Assistant name' })">
					<el-input v-model="settingsForm.assistantName" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '欢迎标题', 'en-US': 'Welcome title' })">
					<el-input v-model="settingsForm.welcomeTitle" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '欢迎说明', 'en-US': 'Welcome description' })">
					<el-input v-model="settingsForm.welcomeDescription" type="textarea" :rows="3" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '快捷问句一', 'en-US': 'Quick prompt one' })">
					<el-input v-model="settingsForm.quickPromptOne" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '快捷问句二', 'en-US': 'Quick prompt two' })">
					<el-input v-model="settingsForm.quickPromptTwo" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '回复风格', 'en-US': 'Reply style' })">
					<el-input v-model="settingsForm.replyStyle" />
				</el-form-item>
				<el-form-item :label="$t({ 'zh-CN': '系统提示词', 'en-US': 'System prompt' })">
					<el-input v-model="settingsForm.systemPrompt" type="textarea" :rows="6" />
				</el-form-item>
			</el-form>
			<span slot="footer" class="assistant-settings-actions">
				<el-button @click="settingsVisible = false">{{ $t({ 'zh-CN': '取消', 'en-US': 'Cancel' }) }}</el-button>
				<el-button type="primary" :loading="settingsSaving" @click="saveSettings">{{ $t({ 'zh-CN': '保存配置', 'en-US': 'Save settings' }) }}</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
const defaultConfig = () => ({
	assistantName: '观赏鱼助手',
	welcomeTitle: '你好，我是观赏鱼助手。',
	welcomeDescription: '你可以问我饲养建议、鱼种差异、系统使用方法。',
	quickPromptOne: '龙鱼和金枪鱼的饲养区别是什么？',
	quickPromptTwo: '新手第一次养观赏鱼要注意什么？',
	replyStyle: '专业、稳重、偏中文口语',
	systemPrompt: '回答时优先给出结论，再给出2到4条实用建议。',
})

export default {
	data() {
		return {
			visible: false,
			settingsVisible: false,
			loading: false,
			settingsSaving: false,
			messages: [],
			streamingReply: '',
			streamingMeta: '',
			streamAbortController: null,
			form: {
				ask: '',
			},
			assistantConfig: defaultConfig(),
			settingsForm: defaultConfig(),
		}
	},
	computed: {
		isLoggedIn() {
			return !!localStorage.getItem('frontToken')
		},
		userId() {
			return Number(localStorage.getItem('frontUserid') || 0)
		},
		userName() {
			return localStorage.getItem('frontUsername') || 'User'
		},
		userAvatar() {
			const avatar = localStorage.getItem('frontHeadportrait')
			return avatar ? this.$assetUrl(avatar) : require('@/assets/avator.png')
		},
		assistantAvatar() {
			return require('@/assets/AI.png')
		},
	},
	async created() {
		await this.loadAssistantConfig()
	},
	methods: {
		async loadAssistantConfig() {
			try {
				const res = await this.$api.get('/assistant/config')
				const data = res.data && res.data.data ? res.data.data : defaultConfig()
				this.assistantConfig = Object.assign(defaultConfig(), data)
				this.settingsForm = Object.assign({}, this.assistantConfig)
			} catch (e) {
				this.assistantConfig = defaultConfig()
				this.settingsForm = Object.assign({}, this.assistantConfig)
			}
		},
		async openAssistant() {
			if (!this.isLoggedIn) {
				this.$message.warning(this.$t({ 'zh-CN': '请先登录后再使用智能助手。', 'en-US': 'Please log in before using the assistant.' }))
				this.$router.push('/login')
				return
			}
			await this.loadAssistantConfig()
			this.visible = true
			await this.loadMessages()
			this.scrollToBottom()
		},
		closeAssistant() {
			this.abortStreaming()
			this.visible = false
			this.loading = false
		},
		openSettings() {
			this.settingsForm = Object.assign({}, this.assistantConfig)
			this.settingsVisible = true
		},
		async saveSettings() {
			this.settingsSaving = true
			try {
				const payload = Object.assign({}, this.settingsForm)
				const res = await this.$api.post('/assistant/config/save', payload)
				const data = res.data && res.data.data ? res.data.data : payload
				this.assistantConfig = Object.assign(defaultConfig(), data)
				this.settingsForm = Object.assign({}, this.assistantConfig)
				this.settingsVisible = false
				this.$message.success(this.$t({ 'zh-CN': '助手配置已保存。', 'en-US': 'Assistant settings saved.' }))
			} catch (e) {
				this.$message.error(this.$t({ 'zh-CN': '保存配置失败，请稍后重试。', 'en-US': 'Failed to save settings. Please try again later.' }))
			} finally {
				this.settingsSaving = false
			}
		},
		fillQuickPrompt(text) {
			this.form.ask = text
		},
		handleEnter(event) {
			if (event.shiftKey) {
				return
			}
			event.preventDefault()
			this.sendMessage()
		},
		displayTime(value) {
			return value || ''
		},
		buildAssistantSystemPrompt() {
			const assistantName = this.assistantConfig.assistantName || '观赏鱼助手'
			const replyStyle = this.assistantConfig.replyStyle || '专业、稳重、偏中文口语'
			const extraPrompt = this.assistantConfig.systemPrompt || ''
			return [
				`你是${assistantName}，一个观赏鱼饲养专家。`,
				`你的回复风格是：${replyStyle}。`,
				'你可以回答关于观赏鱼品种、饲养方法、水质管理、疾病防治等问题。',
				'如果不确定，请诚实说明，不要编造信息。',
				'回复尽量简洁实用。',
				'优先使用中文回复。',
				extraPrompt,
			].filter(Boolean).join('')
		},
		async loadMessages() {
			const res = await this.$api.get('/chat/list', {
				params: {
					userid: this.userId,
					sort: 'addtime',
					order: 'asc',
					page: 1,
					limit: 200,
				},
			})
			this.messages = (res.data.data && res.data.data.list) || []
			this.$nextTick(() => this.scrollToBottom())
		},
		scrollToBottom() {
			this.$nextTick(() => {
				const pane = this.$refs.messagePane
				if (pane) {
					pane.scrollTop = pane.scrollHeight
				}
			})
		},
		async persistReply(reply) {
			await this.$api.post('/chat/save', {
				reply,
				userid: this.userId,
				type: 1,
			})
		},
		abortStreaming() {
			if (this.streamAbortController) {
				this.streamAbortController.abort()
				this.streamAbortController = null
			}
		},
		resetStreamingState() {
			this.abortStreaming()
			this.streamingReply = ''
			this.streamingMeta = ''
		},
		buildStreamUrl() {
			return `${this.$api.defaults.baseURL}/assistant/askai/stream`
		},
		appendSseChunk(chunk) {
			if (!chunk || chunk === '[DONE]') {
				return
			}
			this.streamingReply += chunk
			this.scrollToBottom()
		},
		consumeSseBuffer(buffer, flush = false) {
			const normalized = buffer.replace(/\r\n/g, '\n')
			const segments = normalized.split('\n\n')
			const remainder = flush ? '' : segments.pop()
			segments.forEach(segment => {
				const payload = segment
					.split('\n')
					.filter(line => line.startsWith('data:'))
					.map(line => line.slice(5).replace(/^ /, ''))
					.join('\n')
				this.appendSseChunk(payload)
			})
			return remainder
		},
		async streamAssistantReply(ask) {
			const token = localStorage.getItem('frontToken') || ''
			const controller = new AbortController()
			this.streamAbortController = controller
			this.streamingReply = ''
			this.streamingMeta = new Date().toLocaleString()

			const response = await fetch(this.buildStreamUrl(), {
				method: 'POST',
				headers: {
					Accept: 'text/event-stream',
					'Content-Type': 'application/json',
					...(token ? { Token: token } : {}),
				},
				body: JSON.stringify({ ask, systemPrompt: this.buildAssistantSystemPrompt() }),
				credentials: 'same-origin',
				signal: controller.signal,
			})

			if (!response.body) {
				throw new Error('Streaming response is not supported by this browser')
			}

			const reader = response.body.getReader()
			const decoder = new TextDecoder('utf-8')
			let buffer = ''

			while (true) {
				const { value, done } = await reader.read()
				if (done) {
					break
				}
				buffer += decoder.decode(value, { stream: true })
				buffer = this.consumeSseBuffer(buffer)
			}

			buffer += decoder.decode()
			this.consumeSseBuffer(buffer, true)
			return this.streamingReply
		},
		async sendMessage() {
			const ask = (this.form.ask || '').trim()
			if (!ask || this.loading) {
				return
			}
			const payload = {
				ask,
				type: 1,
				uname: this.userName,
				uimage: localStorage.getItem('frontHeadportrait') || '',
				userid: this.userId,
			}
			this.loading = true
			this.form.ask = ''
			this.streamingReply = ''
			this.streamingMeta = ''
			let askSaved = false
			try {
				const askRes = await this.$api.post('/chat/add', payload)
				askSaved = askRes.data && askRes.data.code === 0
				await this.loadMessages()

				const streamedReply = await this.streamAssistantReply(ask)
				const reply = streamedReply && streamedReply.trim()
					? streamedReply
					: this.$t({
						'zh-CN': '助手暂时没有返回响应，请稍后再试。',
						'en-US': 'The assistant did not return a response. Please try again later.',
					})

				if (askSaved) {
					await this.persistReply(reply)
				}
			} catch (e) {
				const aborted = e && e.name === 'AbortError'
				if (askSaved) {
					await this.persistReply(
						(this.streamingReply && this.streamingReply.trim()) || this.$t({
							'zh-CN': '助手暂时无法响应，您的问题已记录，请稍后再试。',
							'en-US': 'The assistant is temporarily unavailable. Your question has been recorded. Please try again later.',
						})
					).catch(() => {})
				}
				if (!aborted) {
					this.$message.warning(this.$t({ 'zh-CN': '助手请求失败，请稍后重试。', 'en-US': 'Assistant request failed. Please try again later.' }))
				}
			} finally {
				this.resetStreamingState()
				await this.loadMessages()
				this.loading = false
			}
		},
	},
}
</script>

<style scoped>
.assistant-launcher {
	position: fixed;
	right: 28px;
	bottom: 28px;
	z-index: 1000;
	display: inline-flex;
	align-items: center;
	gap: 10px;
	padding: 14px 18px;
	border: 0;
	border-radius: 999px;
	background: linear-gradient(135deg, #0f6fa8 0%, #1f8fcb 100%);
	color: #fff;
	box-shadow: 0 20px 40px rgba(15, 111, 168, 0.22);
	cursor: pointer;
}

.assistant-launcher-icon {
	width: 32px;
	height: 32px;
	display: grid;
	place-items: center;
	border-radius: 999px;
	background: rgba(255, 255, 255, 0.16);
	font-weight: 800;
}

.assistant-launcher-text {
	font-size: 14px;
	font-weight: 700;
}

.assistant-title-bar {
	display: flex;
	align-items: flex-start;
	justify-content: space-between;
	gap: 12px;
}

.assistant-title {
	display: flex;
	flex-direction: column;
	gap: 4px;
}

.assistant-title-main {
	font-size: 18px;
	font-weight: 800;
	color: #163246;
}

.assistant-title-sub {
	font-size: 12px;
	color: #6c7f8d;
}

.assistant-setting-trigger {
	border-radius: 999px;
}

.assistant-shell {
	display: grid;
	gap: 14px;
}

.assistant-messages {
	height: 420px;
	overflow-y: auto;
	padding: 6px 4px 6px 0;
}

.message-row {
	display: grid;
	gap: 10px;
}

.bubble-row {
	display: flex;
	gap: 10px;
	margin-bottom: 12px;
}

.bubble-row-user {
	justify-content: flex-end;
}

.bubble-row-assistant {
	justify-content: flex-start;
}

.avatar {
	width: 32px;
	height: 32px;
	border-radius: 999px;
	flex: 0 0 32px;
}

.bubble {
	max-width: 78%;
	padding: 12px 14px;
	border-radius: 18px;
	box-shadow: 0 12px 30px rgba(15, 111, 168, 0.08);
}

.bubble-user {
	background: linear-gradient(135deg, #0f6fa8 0%, #1f8fcb 100%);
	color: #fff;
	border-bottom-right-radius: 6px;
}

.bubble-assistant {
	background: #f5fbff;
	color: #163246;
	border: 1px solid rgba(15, 111, 168, 0.08);
	border-bottom-left-radius: 6px;
}

.bubble-meta {
	font-size: 11px;
	opacity: 0.72;
	margin-bottom: 6px;
}

.bubble-text {
	white-space: pre-wrap;
	line-height: 1.7;
	word-break: break-word;
}

.assistant-empty {
	padding: 22px 18px;
	border-radius: 20px;
	background: #f5fbff;
	border: 1px dashed rgba(15, 111, 168, 0.16);
}

.assistant-empty-title {
	font-size: 16px;
	font-weight: 700;
	color: #163246;
	margin-bottom: 8px;
}

.assistant-empty-desc {
	font-size: 13px;
	color: #6c7f8d;
	line-height: 1.8;
}

.assistant-quick {
	display: flex;
	flex-wrap: wrap;
	gap: 8px;
}

.assistant-quick button {
	border: 0;
	border-radius: 999px;
	padding: 8px 12px;
	background: #e8f5fb;
	color: #0f6fa8;
	font-size: 12px;
	font-weight: 700;
	cursor: pointer;
}

.assistant-compose {
	display: grid;
	gap: 10px;
}

.assistant-compose-actions {
	display: flex;
	justify-content: flex-end;
	gap: 10px;
}

.assistant-settings-title {
	font-size: 18px;
	font-weight: 800;
	color: #163246;
}

.assistant-settings-form {
	padding-top: 4px;
}

.assistant-settings-actions {
	display: flex;
	justify-content: flex-end;
	gap: 10px;
}

.bubble-loading {
	display: inline-flex;
	align-items: center;
	gap: 6px;
	min-width: 68px;
}

.bubble-loading span {
	width: 8px;
	height: 8px;
	border-radius: 999px;
	background: #0f6fa8;
	opacity: 0.35;
	animation: blink 1.2s infinite;
}

.bubble-loading span:nth-child(2) {
	animation-delay: 0.15s;
}

.bubble-loading span:nth-child(3) {
	animation-delay: 0.3s;
}

@keyframes blink {
	0%, 80%, 100% { opacity: 0.2; transform: translateY(0); }
	40% { opacity: 1; transform: translateY(-2px); }
}

@media (max-width: 720px) {
	.assistant-launcher {
		right: 16px;
		bottom: 16px;
		padding: 12px 14px;
	}
	.assistant-launcher-text {
		display: none;
	}
	.bubble {
		max-width: 86%;
	}
	.assistant-title-bar {
		flex-direction: column;
		align-items: stretch;
	}
}
.streaming-cursor {
	display: inline-block;
	width: 8px;
	height: 1em;
	margin-left: 2px;
	border-radius: 999px;
	background: currentColor;
	vertical-align: middle;
	animation: assistantCursorBlink 1s steps(1) infinite;
}

@keyframes assistantCursorBlink {
	0%,
	50% {
		opacity: 1;
	}
	50.01%,
	100% {
		opacity: 0;
	}
}
</style>
