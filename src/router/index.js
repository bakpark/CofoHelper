import Vue from 'vue'
import Router from 'vue-router'
import Test from '@/components/Test'
import Contest from '@/views/Contest'
import Home from '@/views/Home'
import RealTimeSolve from '@/views/RealTimeSolve.vue'
// import Problem from '@/components/Problem'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Home',
    component: Home
  }, {
    path: '/realtime',
    name: 'RealTimeSolve',
    component: RealTimeSolve
  }, {
    path: '/test',
    name: 'Test',
    component: Test
  },
  {
    path: '/contest/:contestId/:index',
    name: 'Contest',
    component: Contest
  }
  ]
})
