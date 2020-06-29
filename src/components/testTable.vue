<template>
  <div class="Table">
    <table>
      <tbody>
        <tr><th colspan="4" style="text-align: center;border: black solid 2px;font-weight: 700;">{{ tableKey }}</th></tr>
        <tr>
          <th>Handle</th>
          <th>Problem</th>
          <th>SubmitId</th>
          <th>Verdict</th>
        </tr>
        <tr v-for="(item, idx) in dataList" :key="idx">
          <td>{{ item.handle }}</td>
          <td><router-link :to="getProblemLink(item.problem)">{{ item.problem }}</router-link></td>
          <td><a link :href="item.submissionLink">#{{ item.id }}</a></td>
          <td :class="item.verdict">{{ item.verdict }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
/*****************************************************************
 ************************** import *******************************
 *****************************************************************/
export default {
  name: 'Table',
  components: {
  },
  props: {
    tableKey: String
  },
  data () {
    return {
      dataList: []
    }
  },
  /*****************************************************************
   ********************** computed, watch ***********************
   *****************************************************************/
  computed: {
    rawList () {
      return this.$store.state.submits[this.tableKey]
    }
  },
  watch: {
    rawList () {
      let vm = this
      console.log(vm.rawList)
      let retList = []
      vm.rawList.forEach(obj => {
        let tmp = {
          'handle': vm.getHandle(obj),
          'id': vm.getSubmissionId(obj),
          'problem': vm.getProblem(obj),
          'verdict': vm.getVerdict(obj),
          'submissionLink': vm.getSubmissionLink(obj)
        }
        retList.push(tmp)
      })
      vm.dataList = retList
    }
  },
  /*****************************************************************
   ************************** Life-Cycle ***************************
   *****************************************************************/
  created () {
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
    getHandle (obj) {
      return obj.author.members[0].handle
    },
    getSubmissionId (obj) {
      return obj.id
    },
    getProblem (obj) {
      let prb = obj.problem
      return prb.contestId + '-' + prb.index
    },
    getVerdict (obj) {
      return obj.verdict
    },
    getSubmissionLink (obj) {
      return 'http://codeforces.com/contest/' + obj.problem.contestId + '/submission/' + obj.id
    },
    getProblemLink (str) {
      let strArr = str.split('-')
      return '/contest/' + strArr[0] + '/' + strArr[1]
    }
  }
}
</script>

<style scoped>
table{
  border: black solid 2px;
  width: 30%;
  margin: 3%;
}
th{
  font-size: 4px;
  text-align: center;
  border-bottom: black solid 1px;
}
td{
  font-size: 3px;
  padding: 0.5px;
  border-bottom: black solid 1px;
  text-align: center;
}
.WRONG_ANSWER{
  color: red;
}
.OK{
  font-weight: 600;
  color: rgb(12, 156, 108);
}
</style>
