<template>
  <div class="Standing">
    <div class="roundbox">
      <div class="roundbox-lt">&nbsp;</div>
      <div class="roundbox-rt">&nbsp;</div>
      <div class="caption titled">
        -> {{ group.groupName }}  {{ problemName }}
        <div class="top-links"></div>
      </div>
      <table class="rtable smaller">
        <tbody>
          <tr>
            <th>Handle</th>
            <th>Submission</th>
            <th>Time</th>
            <th>Language</th>
            <th>Verdict</th>
          </tr>
          <tr v-for="result in displayResults" :key="result.handle">
            <td>{{ result.handle }}</td>
            <td v-if="!result.submission"> empty </td>
            <td v-else><a link :href="result.submissionLink">#{{ result.submission }}</a></td>
            <td>{{ result.time }}</td>
            <td>{{ result.language }}</td>
            <td :class="getVerdictStyle(result.verdict)">{{ result.verdict }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="round-box">
      <div class="roundbox-lt">&nbsp;</div>
      <div class="roundbox-rt">&nbsp;</div>
      <div class="caption titled">
        Go to Submit! <br><a link :href="getSubmitLink()">>> Contest {{ problemContestId }}</a>
        <div class="top-links"></div>
      </div>
    </div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
import util from '@/common/util.js'
export default {
  name: 'Standing',
  components: {},
  props: {
    group: Object
  },
  data () {
    return {
      members: [],
      contestName: '',
      problemName: '',
      problemContestId: '',
      problemIndex: '',
      results: {}, // all submits in problemContestId,
      displayResults: {},
      callHandle: ''
    }
  },
  /*****************************************************************
   ********************** computed, watch ***********************
   *****************************************************************/
  computed: {},
  watch: {
    callHandle (changed) {
      this.bindDiplayResult(changed)
    },
    problemContestId (changed) {
      this.getMembers().then(() => {
        this.initAll()
        this.getResults(changed)
      })
    },
    $route (to, from) {
      if (to === from) {
        return
      }
      this.getProblem(to.params.problemId).then(res => {
        this.problemName = res.data.name
        this.problemIndex = this.problemName.split('-')[1]
        if (this.problemName.split('-')[0] === this.problemContestId) {
          this.justBindDisplayResults()
          return
        }
        this.problemContestId = this.problemName.split('-')[0]
      })
    }
  },
  /*****************************************************************
   ************************** Life-Cycle ***************************
   *****************************************************************/
  created () {
    this.contestId = this.$route.params.contestId
    this.problemId = this.$route.params.problemId
    this.getMembers().then(
      this.getProblem(this.problemId).then(res => {
        this.problemName = res.data.name
        this.problemContestId = this.problemName.split('-')[0]
        this.problemIndex = this.problemName.split('-')[1]
      })
    )
  },
  mounted () {},
  updated () {},
  beforeDestroy () {},
  destroyed () {},
  /*****************************************************************
   ********************** User-Defined Methods *********************
   *****************************************************************/
  methods: {
    getMembers () {
      return new Promise((resolve, reject) => {
        this.$axios.get(`api/groups/${this.group.groupId}/users`, {
          headers: { authorization: localStorage.getItem('authorization').toString() }
        }).then(res => {
          this.members = res.data.data
          resolve()
        }).catch(err => {
          console.error(err)
          reject(err)
        })
      })
    },
    getProblem (problemId) {
      let uri = 'api/problems'
      let params = { problemId: problemId }
      let headers = { authorization: localStorage.getItem('authorization').toString() }
      return new Promise((resolve, reject) => {
        this.$axios.get(uri, {
          params: params,
          headers: headers
        }).then(response => {
          resolve(response.data)
        }).catch(err => {
          reject(err)
        })
      })
    },
    getResults (contestId) {
      this.members.forEach(obj => {
        this.getResult(obj.handle, contestId)
      })
    },
    getResult (handle, contestId) {
      this.$api.contest.status(contestId, handle, 1, 50).then(results => {
        results.forEach(result => {
          if (!this.results[handle]) {
            this.results[handle] = []
          }
          this.results[handle].push(this.extractInfo(result))
        })
        this.callHandle = handle
      })
    },
    extractInfo (item) {
      let ret = {}
      ret.handle = item.author.members[0].handle
      ret.problemIndex = item.problem.index
      ret.submission = item.id
      ret.language = item.programmingLanguage
      ret.verdict = item.verdict
      ret.submissionLink = this.getSubmissionLink(item)
      ret.time = util.getTimeString(item.creationTimeSeconds * 1000)
      return ret
    },
    getSubmissionLink (obj) {
      return 'http://codeforces.com/contest/' + obj.problem.contestId + '/submission/' + obj.id
    },
    initAll () {
      this.results = {}
      this.initDisplay()
    },
    initDisplay () {
      this.displayResults = {}
    },
    getDisplayResult (handle) {
      let handleObj = { handle: handle }
      this.results[handle] && this.results[handle].forEach(result => {
        if (result.problemIndex !== this.problemIndex) return
        if (handleObj.verdict && handleObj.verdict === 'OK') return
        if (result.verdict === 'OK' || !handleObj.submission) {
          handleObj = result
        }
      })
      return handleObj
    },
    bindDiplayResult (handle) {
      let handleObj = this.getDisplayResult(handle)
      this.displayResults = { ...this.displayResults }
      this.displayResults[handle] = handleObj
    },
    justBindDisplayResults () {
      this.initDisplay()
      this.members.forEach(obj => {
        this.bindDiplayResult(obj.handle)
      })
    },
    getVerdictStyle: util.getVerdictStyle,
    getSubmitLink () {
      return 'https://codeforces.com/contest/' + this.problemContestId + '/submit'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
