import util from '../common/util'
export class TimingQueue {
  constructor (maxSize, cntPerSec) {
    this._arr = []
    this._sz = maxSize
    this._k = cntPerSec
    this._i = 0
    this._sec = 2000
  }
  getDelay () {
    const curMilliSec = util.getCurMilliSeconds()
    let aheadKTime = this._arr[util.getAheadIndex(this._i, this._k, this._sz)] || (curMilliSec - this._sec)
    let willTime = aheadKTime + this._sec

    this._arr[this._i] = willTime
    this._i = (this._i + 1) % this._sz
    // for delay test
    // console.log('========delay:', willTime - curMilliSec)
    return willTime - curMilliSec
  }
}
