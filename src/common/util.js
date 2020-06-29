export default {
  readStaticFile: function (path) {
    console.log('read static file :', path)
    return new Promise((resolve, reject) => {
      fetch(path).then((prms) => {
        return prms.text()
      }).then((response) => {
        resolve(response)
      }).catch((err) => {
        console.error('Fail to read static file', err)
        reject(err)
      })
    })
  },
  reRenderMathJax () {
    if (window.MathJax) {
      window.MathJax.Hub.Queue(['Typeset', window.MathJax.Hub], () => console.log('reRender done'))
    } else {
      console.error(new Error('window MathJax is empty'))
    }
  },
  wait (ms) {
    return new Promise((resolve, reject) => {
      // console.log('========= in wait go setTimeout delay:', ms, 'now:', new Date().getTime() % 100000)
      setTimeout(resolve, ms)
    })
  },
  getAheadIndex (idx, aheadCnt, maxSize) {
    let ahead = idx + maxSize - aheadCnt
    return ahead >= maxSize ? ahead - maxSize : ahead
  },
  getBehindIndex (idx, behindCnt, maxSize) {
    let behind = idx + behindCnt
    return behind >= maxSize ? behind - maxSize : behind
  },
  getCurMilliSeconds () {
    return new Date().getTime()
  },
  getTimeString (milliSeconds) {
    let months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
    let d = new Date(milliSeconds)
    var hr = d.getHours()
    var min = d.getMinutes()
    if (min < 10) {
      min = '0' + min
    }
    if (hr < 10) {
      hr = '0' + hr
    }
    var date = d.getDate()
    var month = months[d.getMonth()]
    var year = d.getFullYear()
    return month + '/' + date + '/' + year + '\n' + hr + ':' + min
  },
  getVerdictStyle (verdict) {
    return !verdict ? '' : verdict === 'OK' ? 'verdict-accepted' : 'verdict-failed'
  }
}
