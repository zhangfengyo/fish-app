import VueRouter from 'vue-router'
import AdminLayout from '../views/AdminLayout.vue'
import AdminLogin from '../views/AdminLogin.vue'
import AdminHome from '../views/AdminHome.vue'
import NewsManager from '../views/NewsManager.vue'
import TeachingManager from '../views/TeachingManager.vue'
import AssistantManager from '../views/AssistantManager.vue'
import ShopManager from '../views/ShopManager.vue'
import OrderManager from '../views/OrderManager.vue'
import AquariumManager from '../views/AquariumManager.vue'
import EncyclopediaManager from '../views/EncyclopediaManager.vue'
import UserManager from '../views/UserManager.vue'
import FishCategoryManager from '../views/FishCategoryManager.vue'
import AquariumCategoryManager from '../views/AquariumCategoryManager.vue'
import ForumManager from '../views/ForumManager.vue'

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}

export default new VueRouter({
	mode: 'hash',
	routes: [
		{
			path: '/',
			redirect: '/home',
		},
		{
			path: '/login',
			component: AdminLogin,
		},
		{
			path: '/',
			component: AdminLayout,
			children: [
				{
					path: 'home',
					component: AdminHome,
				},
				{
					path: 'news',
					component: NewsManager,
				},
				{
					path: 'teachings',
					component: TeachingManager,
				},
				{
					path: 'assistant',
					component: AssistantManager,
				},
				{
					path: 'shop',
					component: ShopManager,
				},
				{
					path: 'orders',
					component: OrderManager,
				},
				{
					path: 'aquariums',
					component: AquariumManager,
				},
				{
					path: 'encyclopedia',
					component: EncyclopediaManager,
				},
				{
					path: 'members',
					component: UserManager,
				},
				{
					path: 'fish-categories',
					component: FishCategoryManager,
				},
				{
					path: 'aquarium-categories',
					component: AquariumCategoryManager,
				},
				{
					path: 'forum',
					component: ForumManager,
				},
			],
		},
	],
})
