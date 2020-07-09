<style scoped>
table {
  margin-top: 10px;
  border-collapse: collapse;
  text-align: left;
  line-height: 1.5;
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
  <table>
    <th>그룹 이름</th>
    <tr v-for="group in groups" :key="group.groupId">
      <td>
        <router-link :to="`/groups/${group.groupId}/contests/list`">{{
          group.groupName
        }}</router-link>
      </td>
    </tr>
  </table>
</template>
<script>
import { get } from '@/components/util.js'
export default {
  data () {
    return {
      groups: []
    }
  },
  async created () {
    let res = await get(`api/users/${this.$store.state.handle}/groups`)
    this.groups = res.data.data
  }
}
</script>
