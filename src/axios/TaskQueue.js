import { Task } from './Task'
import util from '../common/util'

export class TaskQueue {
  constructor (sz, callPerSec, milliSec) {
    this._arr = []
    this._callPerSec = callPerSec
    this._mod = sz
    this._head = 0
    this._tail = 0
    this._milliSec = milliSec
    this.isRunning = false
  }
  httpRequest (url, params) {
    let task = new Task(url, params)
    this._enqueue(task)
    return task.promise
  }
  _dequeue () {
    if (this._isEmpty()) {
      return null
    }
    let item = this._arr[this._tail++]
    if (this._tail >= this._mod) this._tail -= this._mod
    return item
  }
  _enqueue (item) {
    this._arr[this._head] = item
    item.aheadTask = this._arr[util.getAheadIndex(this._head, this._callPerSec, this._mod)] || null
    this._head = (this._head + 1) % this._mod
    if (!this.isRunning) {
      this._run()
    }
  }
  _isEmpty () {
    return this._head === this._tail
  }
  _isFull () {
    return (this._full + 1) % this._mod === this._head
  }
  _run () {
    this.isRunning = true
    let task = this._dequeue()
    task.pending(this)
  }
  _stop () {
    this.isRunning = false
  }
}
