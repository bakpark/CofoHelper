<template>
  <div class="Problem">
    <div v-html="problemHtml"></div>
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
      problemHtml: ''
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
    problemHtml () {
      util.reRender()
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    let htmlRef = 'static/html/problems/' + this.$route.params.id + '.html'
    this.readProblemHtml(htmlRef)
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
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
