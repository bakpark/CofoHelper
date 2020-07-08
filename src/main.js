// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axiosForCofo from './axios_for_cofo'
import api from './api'
import store from './store'
import axios from './axios_for_apiserver'
import { Datetime } from 'vue-datetime'
import 'vue-datetime/dist/vue-datetime.css'

Vue.component('datetime', Datetime)

Vue.config.productionTip = false
const toUrl = window.location.href.split(window.location.host + '/#')[1]
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  axiosForCofo,
  axios,
  api,
  store,
  components: { App },
  template: '<App/>',
  async beforeCreate () {
    await store.dispatch('GET_USER_INFO', {toUrl})
    await store.dispatch('GET_INVITATIONS')
  }
})
