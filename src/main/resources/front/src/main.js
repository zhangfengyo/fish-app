import Vue from 'vue'
import VueRouter from 'vue-router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import App from './App.vue'
import router from './router/router'

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(ElementUI)

const localeStore = Vue.observable({
	value: localStorage.getItem('globalLocale') || 'zh-CN',
})

const fishLabels = {
	red_dragon: { 'zh-CN': '血红龙鱼', 'en-US': 'Red Dragon' },
	gold_dragon: { 'zh-CN': '金龙鱼', 'en-US': 'Golden Dragon' },
	moonlight_fish: { 'zh-CN': '月光鱼', 'en-US': 'Moonlight Fish' },
	molly_fish: { 'zh-CN': '玛丽鱼', 'en-US': 'Molly Fish' },
	tiger_barb: { 'zh-CN': '虎皮鱼', 'en-US': 'Tiger Barb' },
	guppy: { 'zh-CN': '孔雀鱼', 'en-US': 'Guppy' },
	fortune_fish: { 'zh-CN': '招财鱼', 'en-US': 'Fortune Fish' },
	luohan_fish: { 'zh-CN': '罗汉鱼', 'en-US': 'Luohan Fish' },
	general: { 'zh-CN': '综合推荐', 'en-US': 'General' },
	teaching: { 'zh-CN': '教学内容', 'en-US': 'Teaching' },
}

const api = axios.create({
	baseURL: '/guanshangl0g62fht',
	timeout: 15000,
})

api.interceptors.request.use(config => {
	const token = localStorage.getItem('frontToken')
	if (token) {
		config.headers.Token = token
	}
	return config
})

api.interceptors.response.use(
	response => response,
	error => {
		const status = error.response && error.response.status
		const data = error.response && error.response.data
		if (status === 401 || (data && data.code === 401)) {
			localStorage.removeItem('frontToken')
			localStorage.removeItem('frontUserid')
			localStorage.removeItem('frontUsername')
			router.replace('/login').catch(() => {})
		}
		return Promise.reject(error)
	}
)

Vue.prototype.$api = api
Vue.prototype.$locale = localeStore
Vue.prototype.$setLocale = function(locale) {
	localeStore.value = locale
	localStorage.setItem('globalLocale', locale)
}
Vue.prototype.$t = function(messages) {
	if (messages == null) {
		return ''
	}
	if (typeof messages === 'string') {
		return messages
	}
	return messages[localeStore.value] || messages['zh-CN'] || messages['en-US'] || ''
}
Vue.prototype.$fishLabel = function(value) {
	if (!value) {
		return ''
	}
	const item = fishLabels[value]
	if (item) {
		return item[localeStore.value] || item['en-US']
	}
	return value
}
Vue.prototype.$assetUrl = function(path) {
	if (!path) {
		return ''
	}
	if (/^https?:\/\//.test(path)) {
		return path
	}
	const normalized = path
		.split(',')[0]
		.trim()
		.replace(/\\/g, '/')
		.replace(/^\//, '')
		.replace(/^(upload\/)+/, '')
	return `/guanshangl0g62fht/upload/${normalized}`
}

new Vue({
	router,
	render: h => h(App),
}).$mount('#app')
