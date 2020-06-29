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
            <tr v-for="result in displayResults" :key="result.handle">
              <td>{{ result.handle }}</td>
              <td>#{{ result.submission }}</td>
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
  props: {},
  data () {
    return {
      contestId: '',
      results: {},
      doneCnt: 0,
      displayResults: {},
      problemIndex: 'A',
      contestInfos: []
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
    contestId (changed) {
      this.getResults(changed)
    },
    doneCnt () {
      let vm = this
      this.$store.state.members.forEach((handle) => {
        if (vm.displayResults[handle] || !vm.results[handle]) return false
        let handleObj = {
          handle: handle,
          submission: 'empty'
        }
        vm.results[handle].forEach((result) => {
          if (result.problemIndex !== vm.problemIndex) return false
          if (handleObj.verdict && handle.verdict === 'OK') return false
          if (result.verdict === 'OK' || handleObj.submission === 'empty') {
            handleObj = result
          }
        })
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
    this.contestId = this.$route.params.id
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
    getResults (contestId) {
      this.doneCnt = 0
      this.displayResults = {}
      this.results = {}
      let vm = this
      let waitMilliSeconds = 350
      let time = 0
      this.$store.state.members.forEach(handle => {
        util.wait((Math.floor(time / 1000) * 1000)).then(() => {
          vm.getResult(handle, contestId)
        })
        time += waitMilliSeconds
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
      return ret
    },
    async readContestInfoJson () {
      let jsonRef = 'static/json/contests.json'
      let jsonString = await util.readStaticFile(jsonRef)
      let jsonObj = await JSON.parse(jsonString)
      this.contestInfos = await jsonObj.info
    },
    buttonClick (...params) {
      console.log(params)
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
