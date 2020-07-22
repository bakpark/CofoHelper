/* eslint-disable eqeqeq */
import Vue from 'vue'
import Vuex from 'vuex'
import axios from '@/axios_for_apiserver'
import router from '@/router'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLoggedIn: false,
    handle: '',
    startLoadComplete: false,
    rightRealOpen: false,
    readyToShow: '',
    invitations: [],
    groups: []
  },
  mutations: {
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
    },
    TOGGLE_RIGHT_REAL (state) {
      state.rightRealOpen = !state.rightRealOpen
    },
    GET_INVITATIONS (state, invitations) {
      state.invitations = invitations
    },
    GET_GROUPS (state, groups) {
      state.groups = groups
    }
  },
  actions: {
    JOIN (context, {handle, password}) {
      return axios
        .post('/users', { handle, password })
        .then(res => {
          if (res.status == 200) {
            router.push({name: 'Login'})
            alert('WELCOME! ' + handle +' login please')
          }
        })
        .catch(err => {
          alert(err)
        })
    },
    LOGIN (context, {handle, password}) {
      return axios
        .post('/users/login', { handle, password })
        .then(res => {
          if (res.status == 200) {
            localStorage.setItem('authorization', res.headers.authorization)
            context.dispatch('GET_USER_INFO')
          }
        })
        .catch(err => {
          alert(err)
        })
    },
    GET_USER_INFO (context, urlObj) {
      if (localStorage.getItem('authorization') == null) {
        return null
      }

      const authorization = localStorage.getItem('authorization').toString()
      return axios.post(`/users/user_info`, {}, {
        headers: {
          authorization
        }
      })
        .then(res => {
          if (res.status != 200) {
            context.commit('LOGIN_FAIL')
            return null
          }
          const handle = res.data.data
          context.commit('LOGIN_SUCCESS', { handle })
          router.push({path: urlObj == undefined ? '/' : urlObj.toUrl == undefined ? '/' : urlObj.toUrl})
        })
    },
    GET_INVITATIONS (context) {
      const authorization = localStorage.getItem('authorization').toString()
      return axios.get(`api/users/${context.state.handle}/invitations`, {
        headers: {
          authorization
        }
      })
        .then(res => {
          context.commit('GET_INVITATIONS', res.data.data)
          context.dispatch('GET_GROUPS')
        })
    },
    ACCEPT_INVITATION (context, invitationId) {
      const authorization = localStorage.getItem('authorization').toString()
      return axios.post(`api/invitations/${invitationId}/accept`, {}, {
        headers: {
          authorization
        }
      })
        .then(res => {
          context.dispatch('GET_INVITATIONS')
        })
    },
    NO_INVITATION (context, invitationId) {
      const authorization = localStorage.getItem('authorization').toString()
      return axios.delete(`api/invitations/${invitationId}`, {
        headers: {
          authorization
        }
      })
        .then(res => {
          context.dispatch('GET_INVITATIONS')
        })
    },
    GET_GROUPS (context) {
      return axios
        .get(`api/users/${context.state.handle}/groups`, {
          headers: {
            authorization: localStorage.getItem('authorization').toString()
          }
        })
        .then(res => {
          context.commit('GET_GROUPS', res.data.data)
        })
    }
  },
  modules: {}
})
