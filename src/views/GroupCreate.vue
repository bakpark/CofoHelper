<style scoped>
.body {
  display: flex;
  justify-content: center;
}
.left_view{
  width: 30vw;
}
.left_view > div{
  margin-top: 10px;
}
table {
  margin-top: 10px;
  border-collapse: collapse;
  text-align: left;
  line-height: 1.2;
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
    <div class="body">
      <div class="left_view">
        <h1>그룹 만들기</h1>
        <div>
          <span>그룹이름</span><input type="text" v-model="newGroupName" />
        </div>
        <div>
          <span>그룹설명</span>
          <input type="text" v-model="newGroupDescription" />
        </div>
        <div>
          <span>그룹멤버</span>
          <input type="text" v-model="newGroupMember" />
          <button v-on:click="addMember">추가</button>
        </div>
      </div>
      <div class="right_view">
        <h1>그룹 멤버</h1>
        <table>
          <th>아이디</th>
          <th>삭제</th>
          <tr v-for="(addedMember, index) in addedMembers" :key="index">
            <td>{{addedMember.name}}</td>
            <td><button v-on:click="removeMember">삭제</button></td>
          </tr>
        </table>
      </div>
    </div>

    <button v-on:click="makeNewGroup">만들기</button>
  </div>
</template>
<script>
/* eslint-disable eqeqeq */
import { post } from '@/components/util.js'
export default {
  data () {
    return {
      newGroupName: '',
      newGroupDescription: '',
      newGroupMember: '',
      addedMembers: []
    }
  },
  methods: {
    addMember: function () {},
    removeMember: function () {},
    makeNewGroup: async function () {
      let res = await post('api/groups', { groupName: this.newGroupName })
      if (res.data.status == 200) {
        this.$router.push({
          name: 'ContestList',
          params: {
            groupId: res.data.data.groupId
          }
        })
      }
    }
  }
}
</script>
