import Vue from 'vue'
import user from './user.js'
import contest from './contest.js'

const api = {}

api.user = user
api.contest = contest

Vue.prototype.$api = api
export default api
