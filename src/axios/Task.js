import util from '../common/util'
import Axios from 'axios'

const axios = Axios.create({
  baseURL: 'http://codeforces.com/api/'
})
export class Task {
  constructor (url, params) {
    let t = this
    this.url = url
    this.params = params
    this.promise = new Promise((resolve, reject) => {
      t.resolve = resolve
      t.reject = reject
    })
  }
  pending (queue) {
    console.log('====== pending Task handle:', this.params.params.handle)
    if (this.aheadTask && !this.aheadTask.endTime) {
      util.wait(100).then(() => this.pending(queue))
      return
    }
    let curTime = new Date().getTime()
    let willTime = !this.aheadTask ? 0 : (this.aheadTask.endTime + queue._milliSec)
    console.log('willTime:', willTime % 100000, ' curTime:', curTime % 100000)
    util.wait(willTime - curTime).then(() => this.excute(queue))
  }
  excute (queue) {
    console.log('====== excute Task handle', this.params.params.handle)
    let t = this
    axios.get(t.url, t.params).then((response) => {
      t.endTime = new Date().getTime()
      let next = queue._dequeue()
      next && next.pending(queue)
      t.resolve(response)
    }).catch((err) => {
      t.endTime = new Date().getTime()
      t.reject(err)
    })
  }
}
