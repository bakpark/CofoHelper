// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from './axios'
import api from './api'
import store from './store'

Vue.config.productionTip = false
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axios,
  api,
  store,
  components: { App },
  template: '<App/>',
  beforeCreate () {
    store.dispatch('GET_USER_INFO')
  }
})
