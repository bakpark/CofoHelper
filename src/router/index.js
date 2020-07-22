/* eslint-disable eqeqeq */
import Vue from 'vue'
import Router from 'vue-router'
import SolvingView from '@/views/SolvingView'
import Home from '@/views/Home'
import RealTimeSolve from '@/views/RealTimeSolve.vue'
import Login from '@/views/Login.vue'
import Join from '@/views/Join.vue'
import store from '@/store'
import Groups from '@/views/Groups.vue'
import GroupList from '@/views/GroupList.vue'
import GroupCreate from '@/views/GroupCreate.vue'
import Problems from '@/views/Problems.vue'
import Contests from '@/views/Contests.vue'
import GroupMembers from '@/views/GroupMembers.vue'
import ContestCreate from '@/views/ContestCreate.vue'
import ContestList from '@/views/ContestList.vue'

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
    name: 'SolvingView',
    component: SolvingView,
    beforeEnter: preventNotLoggedIn
  }, {
    path: '/',
    name: 'Home',
    component: Home,
    beforeEnter: preventNotLoggedIn
  }, {
    path: '/groups/',
    name: 'Groups',
    component: Groups,
    beforeEnter: preventNotLoggedIn,
    children: [
      {
        path: 'list',
        name: 'GroupList',
        component: GroupList
      }, {
        path: 'create',
        name: 'GroupCreate',
        component: GroupCreate
      }
    ]
  }, {
    path: '/groups/:groupId/contests',
    name: 'Contests',
    component: Contests,
    beforeEnter: preventNotLoggedIn,
    children: [
      {
        path: 'list',
        name: 'ContestList',
        component: ContestList
      }, {
        path: 'create',
        name: 'ContestCreate',
        component: ContestCreate
      }, {
        path: 'members',
        name: 'GroupMembers',
        component: GroupMembers
      }, {
        path: ':contestId',
        name: 'Problems',
        component: Problems
      }
    ]
  // }, {
  //   path: '/groups/:groupId/contests/:contestId',
  //   name: 'Problems',
  //   component: Problems,
  //   beforeEnter: preventNotLoggedIn
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
