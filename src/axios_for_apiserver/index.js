import Vue from 'vue'
import Axois from 'axios'

console.log(process.env)
const axiosForApiServer = Axois.create({ baseURL: process.env.API_BASE_URL })
Vue.prototype.$axios = axiosForApiServer
export default axiosForApiServer
