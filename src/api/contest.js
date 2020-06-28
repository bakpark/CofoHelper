import axios from '../axios'

export default {
  status: function (contestId, handle, from = 1, count = 10) {
    return new Promise((resolve, reject) => {
      if (!contestId) {
        console.error('API get FAILED : contest.status')
        reject(new Error('contestId must be required'))
      }
      axios.get(
        '/contest.status',
        {
          params: {
            contestId: contestId,
            handle: handle,
            from: from,
            count: count
          }
        }
      ).then((response) => {
        console.log('API get SUCCESS : contest.status')
        resolve(response.data.result)
      }).catch((err) => {
        console.error('API get FAILED : contest.status')
        reject(err)
      })
    })
  }
}
