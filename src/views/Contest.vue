<template>
  <div class="Contest">
    <div class="left-view">
      <div class="group-navigator-wrapper" v-for="group in groups" :key="group.groupId">
        <GroupNavigator :group="group"></GroupNavigator>
      </div>
    </div>
    <div class="center-view">
      <Problem :problemId="problemId"></Problem>
    </div>
    <div class="right-view">
      <div class="roundbox">
        <div class="roundbox-lt">&nbsp;</div>
        <div class="roundbox-rt">&nbsp;</div>
        <div class="caption titled">
          -> Submits {{ problemSplit[0] }} - {{ problemSplit[1] }}
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
    </div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
import util from '@/common/util.js'
import Problem from '@/components/Problem'
import GroupNavigator from '@/components/GroupNavigator'
export default {
  name: 'Contest',
  components: {
    Problem: Problem,
    GroupNavigator: GroupNavigator
  },
  props: {
  },
  data () {
    return {
      problemContestId: '', // 문제의 contest
      problemIndex: '', // 문제의 problemIndex
      problemId: '',
      contestName: '',
      problemName: '',
      problemSplit: [],
      contestInfos: [],
      results: {},
      displayResults: {},
      callHandle: '',
      groups: []
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
    $route (to, from) {
      this.contestId = to.params.contestId
      this.problemId = to.params.problemId
      this.getProblem(this.problemId).then(response => {
        let problemName = response.data.name
        let split = problemName.split('-')
        if (split[0] !== this.problemSplit[0]) {
          this.problemSplit = split
          this.problemContestId = split[0]
          this.problemIndex = split[1]
          return
        }
        if (split[1] !== this.problemSplit[1]) {
          this.problemSplit = split
          this.problemIndex = split[1]
          this.renewDisplayResults()
        }
      })
    },
    problemContestId (changed) {
      this.initAll()
      this.getResults(changed)
    },
    callHandle (changed) {
      this.bindDiplayResult(changed)
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    this.contestId = this.$route.params.contestId
    this.problemId = this.$route.params.problemId
    this.getProblem(this.problemId).then(response => {
      let problemName = response.data.name
      let split = problemName.split('-')
      this.problemSplit = split
      this.problemContestId = split[0]
      this.problemIndex = split[1]
    })
    this.getGroups()
  },
  mounted () {
  },
  beforeDestroy () {
  },
  destroyed () {
  },
  /*****************************************************************
   ********************** User-Defined Methods *********************
   *****************************************************************/
  methods: {
    initDisplay () {
      this.displayResults = {}
    },
    initAll () {
      this.results = {}
      this.initDisplay()
    },
    getResults (contestId) {
      let vm = this
      this.$store.state.members.forEach(handle => {
        vm.getResult(handle, contestId)
      })
    },
    getResult (handle, contestId) {
      let vm = this
      vm.$api.contest.status(contestId, handle, 1, 50).then(results => {
        results.forEach(result => {
          if (!vm.results[handle]) {
            vm.results[handle] = []
          }
          vm.results[handle].push(vm.extractInfo(result))
        })
        vm.callHandle = handle
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
    getVerdictStyle: util.getVerdictStyle,
    getDisplayResult (handle) {
      let handleObj = {
        handle: handle
      }
      let vm = this
      vm.results[handle] && vm.results[handle].forEach((result) => {
        if (result.problemIndex !== vm.problemIndex) return
        if (handleObj.verdict && handleObj.verdict === 'OK') return
        if (result.verdict === 'OK' || !handleObj.submission) {
          handleObj = result
        }
      })
      return handleObj
    },
    bindDiplayResult (handle) {
      let vm = this
      let handleObj = this.getDisplayResult(handle)
      this.displayResults = { ...vm.displayResults }
      this.displayResults[handle] = handleObj
    },
    renewDisplayResults () {
      let vm = this
      this.initDisplay()
      this.$store.state.members.forEach((handle) => {
        vm.bindDiplayResult(handle)
      })
    },
    getGroups () {
      let uri = 'api/users/' + this.$store.state.handle + '/groups'
      let headers = { authorization: localStorage.getItem('authorization').toString() }
      this.$axios.get(uri, {
        headers: headers
      }).then(response => {
        console.log('getGroups response:', response)
        this.groups = response.data.data
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
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.Contest{
  display: flex;
  flex-direction: row;
}
.left-view{
  width: 15%;
  margin-left: 3%;
  margin-top: 10em;
}
.center-view{
  margin-left: 5%;
  margin-right: 5%;
  width: 50%;
  margin-top: 3em;
}
.right-view{
  width: 23%;
  margin-right: 3%;
  margin-top: 10em;
}
div .group-navigator-wrapper{
  margin-bottom: 2em;
  border: dotted 2px black;
}
</style>
