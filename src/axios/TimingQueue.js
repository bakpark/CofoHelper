import util from '../common/util'
export class TimingQueue {
  constructor (maxSize, cntPerSec) {
    this._arr = []
    this._sz = maxSize
    this._k = cntPerSec
    this._i = 0
    this._sec = 1100
    this._interval = 100
  }
  getDelay () {
    const curMilliSec = util.getCurMilliSeconds()
    let aheadOneTime = this._arr[util.getAheadIndex(this._i, this._k, this._sz)] || (curMilliSec - this._interval)
    let aheadKTime = this._arr[util.getAheadIndex(this._i, 1, this._sz)] || (curMilliSec - this._sec)
    let willTime = Math.max(curMilliSec, aheadOneTime + this._interval, aheadKTime + this._sec)

    this._i = util.getBehindIndex(this._i, 1, this._sz)
    this._arr[this._i] = willTime

    return willTime - curMilliSec
  }
}
