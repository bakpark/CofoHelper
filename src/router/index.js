import Vue from 'vue'
import Router from 'vue-router'
import Test from '@/components/Test'
import Problem from '@/components/Problem'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Test',
    component: Test
  },
  {
    path: '/problem/:id',
    name: 'Problem',
    component: Problem
  }]
})
