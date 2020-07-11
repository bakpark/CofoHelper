import Vue from 'vue'
import { TaskQueue } from './TaskQueue'

let queue = new TaskQueue(50, 5)

const instance = {
  get: function (url, params) {
    return queue.httpRequest(url, params)
  }
}
Vue.prototype.$axiosForCofo = instance

export default instance
