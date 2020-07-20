<style scoped>
table .table-header {
  text-align: center;
  border-bottom: black solid 2px;
  font-weight: 700;
  font-size: 2em;
}
table {
  width: 100%;
  height: 100%;
  margin-right: 1em;
}
@media (min-width: 320px) and (max-width: 480px) {
  .submit-table {
  /* border: black solid 2px; */
  width: 100vw;
  padding: 0.5vw;
}
  th {
    font-size: 0.6em;
    text-align: center;
    border-bottom: black solid 1px;
  }
  td {
    font-size: 0.6em;
    padding: 0.1em;
    border-bottom: black solid 1px;
    text-align: center;
  }
}
@media (min-width: 1100px) {
  .submit-table {
  /* border: black solid 2px; */
  width: 30vw;
  padding: 0.5vw;
}
  th {
    font-size: 1.3em;
    text-align: center;
    border-bottom: black solid 1px;
  }
  td {
    font-size: 1em;
    padding: 0.3em;
    border-bottom: black solid 1px;
    text-align: center;
  }
}

.WRONG_ANSWER {
  color: red;
}
.OK {
  font-weight: 600;
  color: rgb(12, 156, 108);
}
</style>
<template>
  <div class="submit-table">
    <table>
      <tbody>
        <tr>
          <th class="table-header" colspan="4">{{ handle }}</th>
        </tr>
        <tr>
          <th>Problem</th>
          <th>SubmitId</th>
          <th>Verdict</th>
        </tr>
        <tr v-for="(item, idx) in submits" :key="idx">
          <td>{{ item.problem }}</td>
          <td>
            <a link :href="item.submissionLink">#{{ item.id }}</a>
          </td>
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
  name: 'SubmitTable',
  components: {},
  props: {
    handle: String
  },
  data () {
    return {
      submits: []
    }
  },
  /*****************************************************************
   ********************** computed, watch ***********************
   *****************************************************************/
  computed: {},
  watch: {},
  /*****************************************************************
   ************************** Life-Cycle ***************************
   *****************************************************************/
  created () {
    this.$api.user.status(this.handle, 1, 15).then(submits => {
      submits.forEach(obj => {
        let item = {
          handle: this.getHandle(obj),
          id: this.getSubmissionId(obj),
          problem: this.getProblem(obj),
          verdict: this.getVerdict(obj),
          submissionLink: this.getSubmissionLink(obj)
        }
        this.submits.push(item)
      })
    })
  },
  mounted () {},
  beforeDestroy () {},
  destroyed () {},
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
      return (
        'http://codeforces.com/contest/' +
        obj.problem.contestId +
        '/submission/' +
        obj.id
      )
    }
  }
}
</script>
