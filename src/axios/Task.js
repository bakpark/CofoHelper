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
    if (this.aheadTask && !this.aheadTask.endTime) {
      util.wait(100).then(() => this.pending(queue))
      return
    }
    this.next(queue)
    let curTime = new Date().getTime()
    let willTime = !this.aheadTask ? 0 : (this.aheadTask.endTime + 1000)
    console.log('willTime:', willTime % 100000, ' curTime:', curTime % 100000)
    util.wait(willTime - curTime).then(() => this.excute())
  }
  excute () {
    let t = this
    axios.get(t.url, t.params).then((response) => {
      t.endTime = new Date().getTime()
      t.resolve(response)
    }).catch((err) => {
      t.endTime = new Date().getTime()
      t.reject(err)
    })
  }
  next (queue) {
    let next = queue._dequeue()
    if (next) next.pending(queue)
    else queue._stop()
  }
}
