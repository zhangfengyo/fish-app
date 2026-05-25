import VueRouter from 'vue-router'
import Layout from '../pages/Layout.vue'
import HomeLite from '../pages/HomeLite.vue'
import LoginLite from '../pages/LoginLite.vue'
import NewsListLite from '../pages/NewsListLite.vue'
import NewsDetailLite from '../pages/NewsDetailLite.vue'
import TeachingListLite from '../pages/TeachingListLite.vue'
import TeachingDetailLite from '../pages/TeachingDetailLite.vue'
import AquariumsLite from '../pages/AquariumsLite.vue'
import EncyclopediaLite from '../pages/EncyclopediaLite.vue'
import ShopLite from '../pages/ShopLite.vue'
import CartLite from '../pages/CartLite.vue'
import OrdersLite from '../pages/OrdersLite.vue'
import AddressLite from '../pages/AddressLite.vue'
import ForumLite from '../pages/ForumLite.vue'
import ForumEditorLite from '../pages/ForumEditorLite.vue'
import ForumDetailLite from '../pages/ForumDetailLite.vue'
import MyForumListLite from '../pages/MyForumListLite.vue'
import FavoritesLite from '../pages/FavoritesLite.vue'
import RemindersLite from '../pages/RemindersLite.vue'
import ProfileLite from '../pages/ProfileLite.vue'

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
			component: LoginLite,
		},
		{
			path: '/index',
			component: Layout,
			children: [
				{
					path: 'forum',
					component: ForumLite,
				},
				{
					path: 'forumAdd',
					component: ForumEditorLite,
				},
				{
					path: 'forumDetail',
					component: ForumDetailLite,
				},
				{
					path: 'myForumList',
					component: MyForumListLite,
				},
			],
		},
		{
			path: '/',
			component: Layout,
			children: [
				{
					path: 'home',
					component: HomeLite,
				},
				{
					path: 'news',
					component: NewsListLite,
				},
				{
					path: 'news/:id',
					component: NewsDetailLite,
					props: true,
				},
				{
					path: 'teachings',
					component: TeachingListLite,
				},
				{
					path: 'teachings/:id',
					component: TeachingDetailLite,
					props: true,
				},
				{
					path: 'aquariums',
					component: AquariumsLite,
				},
				{
					path: 'encyclopedia',
					component: EncyclopediaLite,
				},
				{
					path: 'shop',
					component: ShopLite,
				},
				{
					path: 'cart',
					component: CartLite,
				},
				{
					path: 'orders',
					component: OrdersLite,
				},
				{
					path: 'addresses',
					component: AddressLite,
				},
				{
					path: 'forum',
					component: ForumLite,
				},
				{
					path: 'forumAdd',
					component: ForumEditorLite,
				},
				{
					path: 'forumDetail',
					component: ForumDetailLite,
				},
				{
					path: 'myForumList',
					component: MyForumListLite,
				},
				{
					path: 'favorites',
					component: FavoritesLite,
				},
				{
					path: 'reminders',
					component: RemindersLite,
				},
				{
					path: 'profile',
					component: ProfileLite,
				},
			],
		},
	],
})
