import Vue from 'vue'

const api = {}
api.init = function (axios) {
  api.get = axios.get
}

const user = {
  status: function (handle, from = 1, count = 10) {
    return new Promise((resolve, reject) => {
      if (!handle) {
        console.error('API get FAILED : user.status')
        reject(new Error('HANDLE must be required'))
      }
      api.get(
        '/user.status',
        {
          params: {
            handle: handle,
            from: from,
            count: count
          }
        }
      ).then((response) => {
        console.log('API get SUCCESS : user.status')
        resolve(response.data.result)
      }).catch((err) => {
        console.error('API get FAILED : user.status')
        reject(err)
      })
    })
  }
}
api.user = user

Vue.prototype.$api = api
export default api
