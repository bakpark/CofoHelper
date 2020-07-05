<template>
  <div class="Group-Navigator">
    <span>{{ group.groupName }}</span>
    <div v-for="item in contestInfos" :key="item.contestName">{{ item.contestName }}<br/>
      <button v-for="problem in item.problems" :key="problem.name" @click="buttonClick(item.contestId, problem.problemId)">{{ problem.name }}</button>
    </div>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
// import util from '@/common/util.js'
export default {
  name: 'GroupNavigator',
  components: {},
  props: {
    group: Object
  },
  data () {
    return {
      contests: [],
      contestInfos: []
    }
  },
  /*****************************************************************
   ********************** computed, watch ***********************
   *****************************************************************/
  computed: {},
  watch: {
  },
  /*****************************************************************
   ************************** Life-Cycle ***************************
   *****************************************************************/
  created () {
    let vm = this
    this.getContests().then(response => {
      this.contests = response.data
      for (let idx in this.contests) {
        let contest = vm.contests[idx]
        vm.getProblems(contest).then(response => {
          contest.problems = response.data
          vm.contestInfos.push(contest)
        })
      }
    })
  },
  mounted () {},
  updated () {},
  beforeDestroy () {},
  destroyed () {},
  /*****************************************************************
   ********************** User-Defined Methods *********************
   *****************************************************************/
  methods: {
    buttonClick (contestId, problemId) {
      this.$router.push('/contest/' + contestId + '/' + problemId)
    },
    getProblems (contest) {
      return new Promise((resolve, reject) => {
        let uri = 'api/contests/' + contest.contestId + '/problems'
        let headers = { authorization: localStorage.getItem('authorization').toString() }
        this.$axios.get(uri, {
          headers: headers
        }).then((response) => {
          resolve(response.data)
        }).catch((err) => {
          reject(err)
        })
      })
    },
    getContests () {
      return new Promise((resolve, reject) => {
        let groupId = this.group.groupId
        let uri = 'api/groups/' + groupId + '/contests'
        let headers = { authorization: localStorage.getItem('authorization').toString() }
        this.$axios.get(uri, {
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
</style>
