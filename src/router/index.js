/* eslint-disable eqeqeq */
import Vue from 'vue'
import Router from 'vue-router'
import Contest from '@/views/Contest'
// import Home from '@/views/Home'
import RealTimeSolve from '@/views/RealTimeSolve.vue'
import Login from '@/views/Login.vue'
import Join from '@/views/Join.vue'
import store from '@/store'
import Groups from '@/views/Groups.vue'
import Group from '@/views/Group.vue'
import Problems from '@/views/Problems.vue'
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
    path: '/contest/:contestId/:problemId',
    name: 'Contest',
    component: Contest,
    beforeEnter: preventNotLoggedIn
  }, {
    path: '/',
    name: 'Home',
    component: Groups,
    beforeEnter: preventNotLoggedIn
  }, {
    path: '/groups/:groupId',
    name: 'Group',
    component: Group,
    beforeEnter: preventNotLoggedIn
  }, {
    path: '/groups/:groupId/contests/:contestId',
    name: 'Problems',
    component: Problems,
    beforeEnter: preventNotLoggedIn
  }, {
    path: '/realtime',
    name: 'RealTimeSolve',
    component: RealTimeSolve,
    beforeEnter: preventNotLoggedIn
  }, {
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
