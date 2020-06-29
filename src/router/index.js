import Vue from 'vue'
import Router from 'vue-router'
import Test from '@/components/Test'
import Contest from '@/views/Contest'
import Home from '@/views/Home'
// import Problem from '@/components/Problem'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Test',
    component: Test
  },
  {
    path: '/contest/:contestId/:index',
    name: 'Contest',
    component: Contest
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  }]
})
