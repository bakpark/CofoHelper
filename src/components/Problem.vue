<template>
  <div class="Problem">
    <div v-html="problemHtml"></div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
import util from '@/common/util.js'
export default {
  name: 'Problem',
  components: {
  },
  props: {
    contestId: String,
    problemIndex: String
  },
  data () {
    return {
      problemId: '',
      problemHtml: ''
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    console.log('============== problem create')
    this.problemId = this.contestId + '-' + this.problemIndex
    let htmlRef = 'static/html/problems/' + this.problemId + '.html'
    this.readProblemHtml(htmlRef)
  },
  mounted () {
  },
  updated () {
    util.reRenderMathJax()
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
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
