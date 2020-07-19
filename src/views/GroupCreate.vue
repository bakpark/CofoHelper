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
            <td>{{addedMember}}</td>
            <td><button v-on:click="removeMember(addedMember)">삭제</button></td>
          </tr>
        </table>
      </div>
    </div>

    <button v-on:click="makeNewGroup">만들기</button>
  </div>
</template>
<script>
/* eslint-disable eqeqeq */
import { get, post } from '@/components/util.js'
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
    addMember: async function () {
      let member = this.newGroupMember
      if (this.addedMembers.includes(member)) {
        alert('이미 추가된 멤버 입니다')
        return null
      }
      if (member == this.$store.state.handle) {
        alert('자기 자신은 추가할 수 없습니다')
        return null
      }
      let res = await get(`api/users/${member}`)
      if (res.data.data == false) {
        alert('해당 유저는 존재하지 않습니다')
        return null
      }
      this.addedMembers.push(member)
    },
    removeMember: function (member) {
      if (this.addedMembers.includes(member)) {
        this.addedMembers.remove(member)
      }
    },
    makeNewGroup: async function () {
      // 그룹을 만든다
      let grouppCreateRes = await post('api/groups', { groupName: this.newGroupName })
      // 실패시 return null
      if (grouppCreateRes.data.status != 200) { return null }

      // groupId를 받아와서 invitations를 차례차례 날린다
      for (let member of this.addedMembers) {
        await post('api/invitations', {
          groupId: grouppCreateRes.data.data.groupId,
          invitedHandle: member
        })
      }
      // 모두 끝나면 해당 그룹페이지로 옮긴다
      this.$router.push({
        name: 'ContestList',
        params: {
          groupId: grouppCreateRes.data.data.groupId
        }
      })
    }
  }
}
</script>
