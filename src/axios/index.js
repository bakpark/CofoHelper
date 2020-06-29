import Vue from 'vue'
import Axios from 'axios'
import { TimingQueue } from './TimingQueue'
import util from '../common/util'

const axios = Axios.create({
  baseURL: 'http://codeforces.com/api/'
})
const queue = new TimingQueue(50, 5)

let instance = {
  get: function (...params) {
    return new Promise((resolve, reject) => {
      let delay = queue.getDelay()
      util.wait(delay).then(() => {
        axios.get(...params).then((response) => {
          resolve(response)
        }).catch((err) => {
          reject(err)
        })
      })
    })
  },
  post: function (...params) {
    return new Promise((resolve, reject) => {
      let delay = queue.getDelay()
      util.wait(delay).then(() => {
        axios.post(...params).then((response) => {
          resolve(response)
        }).catch((err) => {
          reject(err)
        })
      })
    })
  },
  put: function (...params) {
    return new Promise((resolve, reject) => {
      let delay = queue.getDelay()
      util.wait(delay).then(() => {
        axios.put(...params).then((response) => {
          resolve(response)
        }).catch((err) => {
          reject(err)
        })
      })
    })
  },
  delete: function (...params) {
    return new Promise((resolve, reject) => {
      let delay = queue.getDelay()
      util.wait(delay).then(() => {
        axios.delete(...params).then((response) => {
          resolve(response)
        }).catch((err) => {
          reject(err)
        })
      })
    })
  }
}

Vue.prototype.$axios = instance

export default instance
