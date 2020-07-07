<style scoped>
.total_container {
  display: flex;
}
table {
  border-collapse: collapse;
  text-align: left;
  line-height: 1.5;
  margin: 20px 10px;
}
table th {
  width: 150px;
  padding: 10px;
  font-weight: bold;
  vertical-align: top;
  border: 1px solid #ccc;
}
table td {
  width: 350px;
  padding: 10px;
  vertical-align: top;
  border: 1px solid #ccc;
}
</style>
<template>
  <div class="total_container">
    <table>
      <th>콘테스트 이름</th>
      <th>종료</th>
      <tr v-for="(contest) in contests" :key="contest.contestId">
        <td><router-link :to="`/groups/${groupId}/contests/${contest.contestId}`">{{contest.contestName}}</router-link> </td>
        <td>2020.2020</td>
      </tr>
    </table>
    <div>
      <h1>Contest 만들기</h1>
      <span>Contest 이름:</span><input type="text" v-model="newContestName">
      <button v-on:click="makeNewContest">만들기</button>
    </div>
  </div>
</template>
<script>
import { get, post } from '@/components/util.js'
export default {
  data () {
    return {
      groupId: this.$route.params.groupId,
      contests: [],
      newContestName: ''
    }
  },
  async created () {
    const res = await get(`api/groups/${this.$route.params.groupId}/contests`)
    this.contests = res.data.data
  },
  methods: {
    makeNewContest: async function () {
      await post(`api/groups/${this.groupId}/contests`, { contestName: this.newContestName })
      let res = await get(`api/groups/${this.groupId}/contests`)
      this.contests = res.data.data
    }
  }
}
</script>
