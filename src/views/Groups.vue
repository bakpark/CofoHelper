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
      <th>그룹 이름</th>
      <tr v-for="group in groups" :key="group.groupId">
        <td>
          <router-link :to="`/groups/${group.groupId}`">{{
            group.groupName
          }}</router-link>
        </td>
      </tr>
    </table>
    <div>
      <h1>그룹 만들기</h1>
      <span>그룹이름:</span><input type="text" v-model="newGroupName">
      <button v-on:click="makeNewGroup">만들기</button>
    </div>
  </div>
</template>
<script>
import { get, post } from '@/components/util.js'
export default {
  data () {
    return {
      groups: [],
      newGroupName: ''
    }
  },
  methods: {
    makeNewGroup: async function () {
      await post('api/groups', { groupName: this.newGroupName })
      let res = await get(`api/users/${this.$store.state.handle}/groups`)
      this.groups = res.data.data
    }
  },
  async created () {
    let res = await get(`api/users/${this.$store.state.handle}/groups`)
    this.groups = res.data.data
  }
}
</script>
