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
  }
}
