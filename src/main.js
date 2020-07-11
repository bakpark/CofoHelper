// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
/* eslint-disable no-new */
import Vue from 'vue'
import App from './App'
import router from './router'
import axiosForCofo from './axios_for_cofo'
import api from './api'
import store from './store'
import axios from './axios_for_apiserver'
import { Datetime } from 'vue-datetime'
import 'vue-datetime/dist/vue-datetime.css'
import VueMoment from 'vue-momentjs'
import moment from 'moment'

// datetime component
Vue.component('datetime', Datetime)
// moment: 시간처리 라이브러리
Vue.prototype.$moment = VueMoment
Vue.use(VueMoment, moment)

Vue.config.productionTip = false
const toUrl = window.location.href.split(window.location.host + '/#')[1]
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
