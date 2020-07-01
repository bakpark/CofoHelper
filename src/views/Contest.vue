<template>
  <div class="Contest">
    <div class="left-view">
      <div v-for="item in contestInfos" :key="item.contestId">
        {{ item.kind }} - {{ item.contestId }}<br>
        <button v-for="index in item.indexes" :key="index" @click="buttonClick(item.contestId, index)">{{ index }} </button>
      </div>
    </div>
    <div class="center-view">
      <!-- <Problem :contestId="contestId" :problemIndex="problemIndex"></Problem> -->
      <Problem :problemId="contestId+'-'+problemIndex"></Problem>
    </div>
    <div class="right-view">
      <div class="roundbox">
        <div class="roundbox-lt">&nbsp;</div>
        <div class="roundbox-rt">&nbsp;</div>
        <div class="caption titled">
          -> Standings {{ contestId }} - {{ problemIndex }}
          <div class="top-links"></div>
        </div>
        <table class="rtable smaller">
          <tbody v-if="!allResultsAvailable">
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
              <td :class = "getVerdictStyle(result.verdict)">{{ result.verdict }}</td>
            </tr>
            <tr><span>running... </span></tr>
          </tbody>
          <tbody v-else>
            <tr>
              <th>Handle</th>
              <th>Submission</th>
              <th>Time</th>
              <th>Language</th>
              <th>Verdict</th>
            </tr>
            <tr v-for="result in finalResults" :key="result.handle">
              <td>{{ result.handle }}</td>
              <td v-if="!result.submission"> empty </td>
              <td v-else><a link :href="result.submissionLink">#{{ result.submission }}</a></td>
              <td>{{ result.time }}</td>
              <td>{{ result.language }}</td>
              <td :class = "getVerdictStyle(result.verdict)">{{ result.verdict }}</td>
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
export default {
  name: 'Contest',
  components: {
    Problem: Problem
  },
  props: {
  },
  data () {
    return {
      contestId: '',
      problemIndex: '',
      contestInfos: [],
      doneCnt: 0,
      allResultsAvailable: false,
      results: {},
      displayResults: {},
      callHandle: ''
      // finalResults: [],
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
    finalResults () {
      console.log('=========computed allResultsAvailable,')
      if (this.allResultsAvailable) {
        let sortable = []
        for (let key in this.displayResults) {
          sortable.push(this.displayResults[key])
        }
        sortable.sort(function (a, b) {
          if (!a.submission) return 1
          else if (!b.submission) return -1
          else return Number(a.submission) - Number(b.submission)
        })
        return sortable
      }
      return []
    }
  },
  watch: {
    $route (to, from) {
      if (from.params.contestId !== to.params.contestId) {
        this.problemIndex = to.params.index
        this.contestId = to.params.contestId
        return
      }
      if (from.params.index !== to.params.index) {
        this.problemIndex = to.params.index
        this.renewDisplayResults()
      }
    },
    contestId (changed) {
      this.initAll()
      this.getResults(changed)
    },
    callHandle (changed) {
      this.bindDiplayResult(changed)
    }
    // ,
    // allResultsAvailable (changed) {
    //   console.log('=========watch allResultsAvailable,', changed)
    //   let sortable = []
    //   for (let key in this.displayResults) {
    //     sortable.push(this.displayResults[key])
    //   }
    //   sortable.sort(function (a, b) {
    //     if (!a.submission) return 1
    //     else if (!b.submission) return -1
    //     else return Number(a.submission) - Number(b.submission)
    //   })
    //   this.finalResults = sortable
    // }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    this.contestId = this.$route.params.contestId
    this.problemIndex = this.$route.params.index || 'A'
    this.readContestInfoJson()
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
      this.allResultsAvailable = false
      this.doneCnt = 0
      this.finalResults = []
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
    async readContestInfoJson () {
      let jsonRef = 'static/json/contests.json'
      let jsonString = await util.readStaticFile(jsonRef)
      let jsonObj = await JSON.parse(jsonString)
      this.contestInfos = await jsonObj.info
    },
    buttonClick (contestId, problemIndex) {
      if (this.contestId === contestId && this.problemIndex === problemIndex) return
      this.$router.push('/contest/' + contestId + '/' + problemIndex)
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
      vm.doneCnt++
      if (vm.doneCnt === 6) {
        vm.allResultsAvailable = true
      }
    },
    renewDisplayResults () {
      let vm = this
      this.initDisplay()
      this.$store.state.members.forEach((handle) => {
        vm.bindDiplayResult(handle)
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
</style>
