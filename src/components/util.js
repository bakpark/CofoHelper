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
  reRender () {
    if (window.MathJax) {
      window.MathJax.Hub.Queue(['Typeset', window.MathJax.Hub], () => console.log('done'))
    } else {
      console.error(new Error('window MathJax is empty'))
    }
  }
}
