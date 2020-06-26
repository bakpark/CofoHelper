import Vue from 'vue'
import Axios from 'axios'

// Vue.use(Axios)

const instance = Axios.create({
  baseURL: 'http://codeforces.com/api/'
})

Vue.prototype.$axios = instance

export default instance
