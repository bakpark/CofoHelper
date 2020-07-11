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
    $route (to, from) {
      if (to.params.problemId !== from.params.problemId) {
        this.getProblemHtml(to.params.problemId)
      }
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    this.getProblemHtml(this.$route.params.problemId)
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
    async getProblemHtml (problemId) {
      let uri = 'api/problem_html'
      let headers = {
        Accept: 'text/html',
        authorization: localStorage.getItem('authorization').toString()
      }
      let params = { problemId: problemId }
      let response = await this.$axios.get(uri, {
        params: params,
        headers: headers
      })
      this.problemHtml = await response.data
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
