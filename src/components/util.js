import Axios from 'axios'
const readStaticFile = function (path) {
  console.log('read static file :', path)
  return new Promise((resolve, reject) => {
    fetch(path)
      .then(prms => {
        return prms.text()
      })
      .then(response => {
        resolve(response)
      })
      .catch(err => {
        console.error('Fail to read static file', err)
        reject(err)
      })
  })
}
const reRenderMathJax = function () {
  if (window.MathJax) {
    window.MathJax.Hub.Queue(['Typeset', window.MathJax.Hub], () =>
      console.log('reRender done')
    )
  } else {
    console.error(new Error('window MathJax is empty'))
  }
}
const wait = function (ms) {
  return new Promise((resolve, reject) => {
    setTimeout(resolve, ms)
  })
}
const transformUnixTime = function (unixTime) {
  // Create a new JavaScript Date object based on the timestamp
  // multiplied by 1000 so that the argument is in milliseconds, not seconds.
  var timeValue = new Date(unixTime * 1000)

  const today = new Date()
  const betweenTime = Math.floor((today.getTime() - timeValue.getTime()) / 1000 / 60)
  if (betweenTime < 1) return '방금전'
  if (betweenTime < 60) {
    return `${betweenTime}분전`
  }

  const betweenTimeHour = Math.floor(betweenTime / 60)
  if (betweenTimeHour < 24) {
    return `${betweenTimeHour}시간전`
  }

  const betweenTimeDay = Math.floor(betweenTime / 60 / 24)
  if (betweenTimeDay < 365) {
    return `${betweenTimeDay}일전`
  }

  return `${Math.floor(betweenTimeDay / 365)}년전`
}
const get = function (url) {
  return Axios.get('http://localhost:8080/' + url, {
    headers: {
      authorization: localStorage.getItem('authorization').toString()
    }
  })
}
const post = function (url, body) {
  return Axios.post('http://localhost:8080/' + url, body, {
    headers: {
      authorization: localStorage.getItem('authorization').toString()
    }
  })
}

export {
  readStaticFile,
  reRenderMathJax,
  wait,
  transformUnixTime,
  get,
  post
}
