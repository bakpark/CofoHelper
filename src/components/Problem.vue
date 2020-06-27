<template>
  <div class="Problem">
    <div v-html="problemHtml"></div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
// import Table from '@/components/testTable.vue'
export default {
  name: 'Problem',
  components: {
  },
  props: {},
  data () {
    return {
      problemHtml: '',
      configScriptEl: {}
    }
  },
  /*****************************************************************
  ********************** computed, watch ***********************
  *****************************************************************/
  computed: {
  },
  watch: {
    problemHtml () {
      this.$nextTick().then(() => {
        this.reRender()
      })
    }
  },
  /*****************************************************************
  ************************** Life-Cycle ***************************
  *****************************************************************/
  created () {
    console.log('================= problem created')
    let vm = this
    let htmlRef = 'static/html/' + this.$route.params.id + '.html'
    console.log(htmlRef)
    fetch(htmlRef).then((prms) => {
      return prms.text()
    }).then((response) => {
      vm.problemHtml = response
    }).catch((err) => {
      console.log('read html Fail', err)
    })
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
    reRender () {
      console.log(window.MathJax)
      if (window.MathJax) {
        console.log('rendering mathjax')
        window.MathJax.Hub.Queue(['Typeset', window.MathJax.Hub], () => console.log('done'))
      }
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
