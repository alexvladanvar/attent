import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import Signup from './views/Signup.vue'
import Login from './views/Login.vue'
import Profile from './views/Profile.vue'
import Logout from './views/Logout.vue'

Vue.use(Router)

import store from './store'

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile,
      meta: { requiresAuth: true }
    },
    {
      path: '/signup',
      name: 'signup',
      component: Signup,
      meta: {
        requiresNotAuth: true
      }
    },
    {
      path: '/login',
      name: 'login',
      component: Login,
      meta: {
        requiresNotAuth: true
      }
    },
    {
      path: '/logout',
      name: 'logout',
      component: Logout
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () =>
        import(/* webpackChunkName: "about" */ './views/About.vue')
    }
  ]
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (!store.state.user) {
      return next({
        path: '/login'
      })
    }
  }

  if (to.matched.some(record => record.meta.requiresNotAuth)) {
    if (store.state.user) {
      return next({
        path: '/profile'
      })
    }
  }

  if (to.fullPath === '/' && store.state.user) {
    next('/profile')
    console.log('redirect')
  }
  next()
})

export default router
