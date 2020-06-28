<template>
  <div class="Problem">
    <div class="left-view">
    </div>
    <div class="main-view">
      <div v-html="problemHtml"></div>
    </div>
    <div class="right-view">
      <div class="roundbox sidebox">
        <div class="roundbox-lt">&nbsp;</div>
        <div class="roundbox-rt">&nbsp;</div>
        <div class="caption titled">
          -> Standings
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
import util from '@/components/util.js'
export default {
  name: 'Problem',
  components: {
  },
  props: {},
  data () {
    return {
      problemHtml: '',
      prbmIdSplit: [],
      results: {},
      displayResults: {}
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
    problemHtml () {
      util.reRenderMathJax()
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    let htmlRef = 'static/html/problems/' + this.$route.params.id + '.html'
    this.readProblemHtml(htmlRef)
    this.prbmIdSplit = this.$route.params.id.split('-')
    this.getResults()
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
    async readProblemHtml (ref) {
      this.problemHtml = await util.readStaticFile(ref)
    },
    getResults () {
      let vm = this
      let waitMilliSeconds = 350
      let time = 0
      this.$store.state.members.forEach(handle => {
        util.wait((Math.floor(time / 1000) * 1000)).then(() => {
          vm.getResult(handle)
        })
        time += waitMilliSeconds
      })
    },
    getResult (handle) {
      let vm = this
      vm.$api.contest.status(vm.prbmIdSplit[0], handle, 1, 50).then(results => {
        results.forEach(result => {
          if (result.problem.index !== vm.prbmIdSplit[1]) return false
          if (!vm.displayResults[handle]) {
            vm.displayResults[handle] = vm.extractInfo(result)
          } else if (vm.displayResults[handle].verdict !== 'OK' && result.verdict === 'OK') {
            vm.displayResults[handle] = vm.extractInfo(result)
          }
          if (!vm.results[handle]) {
            vm.results[handle] = []
          }
          vm.results[handle].push(vm.extractInfo(result))
          vm.$forceUpdate()
        })
      })
    },
    extractInfo (item) {
      let ret = {}
      ret.handle = item.author.members[0].handle
      ret.submission = item.id
      ret.language = item.programmingLanguage
      ret.verdict = item.verdict
      return ret
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.Problem{
  display: flex;
  flex-direction: row;
}
.left-view{
  width: 20%;
  margin-left: 5%;
  margin-top: 10em;
}
.main-view{
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
