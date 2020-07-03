/* eslint-disable eqeqeq */
import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import router from '@/router'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    members: ['MochaLatte', 'bakpark', 'johny1', 'bonoky', 'sw1124.han', 'psykibaek'],
    contests: [1343],
    beforeContests: [1367, 1366, 1353, 1369],
    submits: {
      'MochaLatte': [],
      'bakpark': [],
      'johny1': [],
      'bonoky': [],
      'sw1124.han': [],
      'psykibaek': []
    },
    readyToShow: '',
    isLoggedIn: false,
    handle: '',
    startLoadComplete: false
  },
  mutations: {
    CHANGE_TABLE_DATA (state, [tableKey, data]) {
      state.submits[tableKey] = data
    },
    CHANGE_READY_STATUS (state, payload) {
      state.readyToShow = payload.status
      console.log('readyToShow:', state.readyToShow)
    },
    LOGIN_SUCCESS (state, { handle }) {
      state.isLoggedIn = true
      state.handle = handle
      state.startLoadComplete = true
    },
    LOGIN_FAIL (state) {
      state.isLoggedIn = false
      state.startLoadComplete = true
    }
  },
  actions: {
    JOIN (context, {handle, password}) {
      axios
        .post('http://localhost:8080/users', { handle, password })
        .then(res => {
          if (res.status == 200) {
            router.push({name: 'Login'})
          }
        })
    },
    LOGIN (context, {handle, password}) {
      axios
        .post('http://localhost:8080/users/login', { handle, password })
        .then(res => {
          if (res.status == 200) {
            localStorage.setItem('authorization', res.headers.authorization)
            context.dispatch('GET_USER_INFO')
          }
        })
    },
    GET_USER_INFO (context, {toUrl}) {
      if (localStorage.getItem('authorization') == null) {
        return null
      }

      const authorization = localStorage.getItem('authorization').toString()
      axios.post(`http://localhost:8080/users/user_info`, {}, {
        headers: {
          authorization
        }
      })
        .then(res => {
          if (res.status != 200) {
            context.commit('LOGIN_FAIL')
            return null
          }
          const { handle } = res.data.data
          context.commit('LOGIN_SUCCESS', { handle })
          router.push({path: toUrl == undefined ? '/' : toUrl})
        })
    }
  },
  modules: {}
})
