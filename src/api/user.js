import axios from '../axios_for_cofo'

export default {
  status: function (handle, from = 1, count = 10) {
    return new Promise((resolve, reject) => {
      if (!handle) {
        console.error('API get FAILED : user.status')
        reject(new Error('HANDLE must be required'))
      }
      axios.get(
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
