/* eslint-disable eqeqeq */
import util from '@/components/util.js'
import axiosForCofo from '@/axios_for_cofo'

export default {
  reRenderMathJax () {
    if (window.MathJax) {
      window.MathJax.Hub.Queue(['Typeset', window.MathJax.Hub], () => {})
    } else {
      console.error(new Error('window MathJax is empty'))
    }
  },
  wait (ms) {
    return new Promise((resolve, reject) => {
      if (ms <= 0) resolve()
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
  },
  getContestTableInfo: (submissions, members, contestId, problemIndexes) => {
    // submissionDic[1369]['A']['bakpark'] = [제출기록1, 제출기록2, ....];
    const submissionDic = {}
    submissionDic[contestId] = {}
    problemIndexes.forEach(problemIndex => {
      submissionDic[contestId][problemIndex] = {}
      members.forEach(member => {
        submissionDic[contestId][problemIndex][member] = []
      })
    })

    submissions.forEach(submission => {
      let contestId = submission.contestId
      let prIndex = submission.problem.index
      let member = submission.author.members[0].handle
      submissionDic[contestId][prIndex][member].push(submission)
    })

    let tempRows = {}
    members.forEach(member => {
      tempRows[member] = {
        name: member
      }
    })

    members.forEach(member => {
      problemIndexes.forEach(problemIndex => {
        tempRows[member][problemIndex] = {
          result: 'Empty',
          notes: '',
          submission_url: ''
        }

        let tmpSubmissions = submissionDic[contestId][problemIndex][member]
        // result
        if (tmpSubmissions.some(subm => subm.verdict === 'OK')) {
          tempRows[member][problemIndex].result = 'OK'
        } else if (tmpSubmissions.length === 0) {
          tempRows[member][problemIndex].result = ''
        } else {
          tempRows[member][problemIndex].result = tmpSubmissions.length - 1
        }

        // notes
        let notes = tmpSubmissions.map(subm => ({
          result: subm.verdict,
          time: util.transformUnixTime(subm.creationTimeSeconds * 1)
        }))
        tempRows[member][problemIndex].notes = notes

        // submission_url
        // 마지막 서브미션의 url을 보여줘야 함.
        if (tmpSubmissions.length !== 0) {
          let submissionUrl =
              'http://codeforces.com/contest/' +
              tmpSubmissions[0].problem.contestId +
              '/submission/' +
              tmpSubmissions[0].id
          tempRows[member][problemIndex].submission_url = submissionUrl
        }
      })
    })

    let contestInfo = {
      columns: ['name', ...problemIndexes],
      rows: Object.values(tempRows),
      contestId
    }
    console.log(44, contestInfo)
    return contestInfo
  },
  async getSubmissions (members) {
    let submissions = []
    for (const member of members) {
      let res = await axiosForCofo
        .get('/user.status', {
          params: {
            handle: member,
            from: 1,
            count: 50
          }
        })
      submissions = submissions.concat(res.data.result)
    }
    return submissions
  },
  filterSubmissions: (submissions, contestId, problemIndexes) => {
    return submissions.filter(submission => {
      return submission.contestId == contestId && problemIndexes.includes(submission.problem.index)
    })
  }
}
