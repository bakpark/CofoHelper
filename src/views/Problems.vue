<style scoped>
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
    <div>
      <h1>{{contestName}}</h1>
      <table>
        <th>
          <td>문제이름</td>
        </th>
        <tr v-for="(problem) in problems" :key="'problem' + problem.problemId">
          <td>{{problem.name}}</td>
        </tr>
      </table>
      <div>
        <h1>Problem 만들기</h1>
        <span>Problem 이름:</span><input type="text" v-model="newProblemName">
        <button v-on:click="makeNewProblem">만들기</button>
      </div>
    </div>
</template>
<script>
import { get, post } from '@/components/util.js'
export default {
  data () {
    return {
      groupId: this.$route.params.groupId,
      contestId: this.$route.params.contestId,
      newProblemName: '',
      problems: [],
      contestName: ''
    }
  },
  async created () {
    let res = await get(`api/contests/${this.contestId}/problems`)
    this.problems = res.data.data
    res = await get(`api/contests/${this.$route.params.contestId}`)
    this.contestName = res.data.data.contestName
  },
  methods: {
    makeNewProblem: async function () {
      try {
        await post(`api/contests/${this.contestId}/problems`, { problemName: this.newProblemName })
      } catch (e) {
        alert(`존재하지 않는 문제 입니다.
<콘테스트번호-알파벳> 형식으로 작성해주세요. 
EX) 1367-A `)
        return null
      }

      let res = await get(`api/contests/${this.contestId}/problems`)
      this.problems = res.data.data
    }
  }
}
</script>
