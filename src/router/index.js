import Vue from 'vue'
import Router from 'vue-router'
import Test from '@/components/Test'
import Home from '@/views/Home'

Vue.use(Router)

export default new Router({
  routes: [{
    path: '/',
    name: 'Test',
    component: Test
  }, {
    path: '/home',
    name: 'Home',
    component: Home
  }]
})
