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
    problemId: String
  },
  data () {
    return {
      problemHtml: ''
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
    problemId (changed) {
      this.readProblemHtml(changed)
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    this.readProblemHtml(this.problemId)
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
    async readProblemHtml (problemId) {
      let htmlRef = 'static/html/problems/' + problemId + '.html'
      this.problemHtml = await util.readStaticFile(htmlRef)
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
