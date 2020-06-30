import Vue from 'vue'
import { TaskQueue } from './TaskQueue'

let queue = new TaskQueue(50, 5, 1000)

const instance = {
  get: function (url, params) {
    return queue.httpRequest(url, params)
  }
  // ,
  // post: function (url, params) {
  //   return queue.requestPost(url, params)
  // }
}
Vue.prototype.$axios = instance

export default instance
