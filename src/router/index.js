/* eslint-disable eqeqeq */
import Vue from 'vue'
import Router from 'vue-router'
import Contest from '@/views/Contest'
import Home from '@/views/Home'
import RealTimeSolve from '@/views/RealTimeSolve.vue'
import Login from '@/views/Login.vue'
import Join from '@/views/Join.vue'
import store from '@/store'
// import Problem from '@/components/Problem'

Vue.use(Router)

const preventNotLoggedIn = (to, from, next) => {
  if (store.state.isLoggedIn == false) {
    next('/login')
  } else {
    next()
  }
}
const preventLoggedIn = (to, from, next) => {
  if (store.state.isLoggedIn == true) {
    next('/')
  } else {
    next()
  }
}
export default new Router({
  routes: [{
    path: '/',
    name: 'Home',
    component: Home,
    beforeEnter: preventNotLoggedIn
  }, {
    path: '/realtime',
    name: 'RealTimeSolve',
    component: RealTimeSolve,
    beforeEnter: preventNotLoggedIn
  },
  {
    path: '/contest/:contestId/:index',
    name: 'Contest',
    component: Contest,
    beforeEnter: preventNotLoggedIn
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    beforeEnter: preventLoggedIn
  },
  {
    path: '/join',
    name: 'Join',
    component: Join,
    beforeEnter: preventLoggedIn
  }
  ]
})
