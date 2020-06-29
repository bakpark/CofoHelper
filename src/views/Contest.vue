<template>
  <div class="Contest">
    <div class="left-view">
      <div v-for="item in contestInfos" :key="item.contestId">
        {{ item.kind }} - {{ item.contestId }}<br>
        <button v-for="index in item.indexes" :key="index" @click="buttonClick(item.contestId, index)">{{ index }} </button>
      </div>
    </div>
    <div class="center-view">
      <Problem :contestId="contestId" :problemIndex="problemIndex"></Problem>
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
          <tbody>
            <tr>
              <th>Handle</th>
              <th>Submission</th>
              <th>Language</th>
              <th>Verdict</th>
            </tr>
            <!-- <tr v-for="result in displayResults" :key="result.handle"> -->
            <tr v-for="result in finalResults" :key="result.handle">
              <td>{{ result.handle }}</td>
              <td v-if="!result.submission"> empty </td>
              <td v-else><a link :href="result.submissionLink">#{{ result.submission }}</a></td>
              <td>{{ result.language }}</td>
              <td>{{ result.verdict }}</td>
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
      results: {},
      doneCnt: 0,
      displayResults: {},
      contestInfos: []
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
    finalResults () {
      let sortable = []
      for (let key in this.displayResults) {
        sortable.push(this.displayResults[key])
      }
      sortable.sort(function (a, b) {
        if (!a.submission) return 1
        else if (!b.submission) return -1
        else return Number(a.submission) - Number(b.submission)
      })
      if (this.doneCnt === 6) {
        console.log(this.results)
      }
      return sortable
    }
  },
  watch: {
    contestId (changed) {
      this.getResults(changed)
    },
    doneCnt () {
      let vm = this
      this.$store.state.members.forEach((handle) => {
        if (vm.displayResults[handle] && vm.displayResults[handle].submission) return false
        let handleObj = {
          handle: handle
        }
        if (vm.results[handle]) {
          vm.results[handle].forEach((result) => {
            if (result.problemIndex !== vm.problemIndex) return false
            if (handleObj.verdict && handle.verdict === 'OK') return false
            if (result.verdict === 'OK' || !handleObj.submission) {
              handleObj = result
            }
          })
        }
        vm.displayResults = { ...vm.displayResults }
        vm.displayResults[handle] = handleObj
      })
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    console.log('============== contest create')
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
    initApiResults () {
      this.doneCnt = 0
      this.displayResults = {}
      this.results = {}
    },
    getResults (contestId) {
      this.initApiResults()
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
        vm.doneCnt++
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
      return ret
    },
    async readContestInfoJson () {
      let jsonRef = 'static/json/contests.json'
      let jsonString = await util.readStaticFile(jsonRef)
      let jsonObj = await JSON.parse(jsonString)
      this.contestInfos = await jsonObj.info
    },
    buttonClick (contestId, problemIndex) {
      this.$router.push('/contest/' + contestId + '/' + problemIndex)
      this.$router.go('/contest/' + contestId + '/' + problemIndex)
    },
    getSubmissionLink (obj) {
      return 'http://codeforces.com/contest/' + obj.problem.contestId + '/submission/' + obj.id
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
  width: 20%;
  margin-left: 5%;
  margin-top: 10em;
}
.center-view{
  margin-left: 5%;
  margin-right: 5%;
  width: 40%;
  margin-top: 3em;
}
.right-view{
  width: 20%;
  margin-right: 5%;
  margin-top: 10em;
}
</style>
