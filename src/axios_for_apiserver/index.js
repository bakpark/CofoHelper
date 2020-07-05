import Vue from 'vue'
import Axois from 'axios'

const axiosForApiServer = Axois.create({ baseURL: 'http://localhost:8080/' })
Vue.prototype.$axios = axiosForApiServer
export default axiosForApiServer
